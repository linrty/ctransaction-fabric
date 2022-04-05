package com.linrty.fabric;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.linrty.fabric.model.Block;
import com.linrty.fabric.model.User;
import com.linrty.fabric.model.Work;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Default;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.shim.ChaincodeException;
import org.hyperledger.fabric.shim.ChaincodeStub;
import org.hyperledger.fabric.shim.ledger.KeyModification;
import org.hyperledger.fabric.shim.ledger.KeyValue;
import org.hyperledger.fabric.shim.ledger.QueryResultsIterator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 智能合约
 *
 * @author Linrty
 * @version 1.0
 * @date 2022/2/8
 *
 */
@Contract(name = "mycc")
@Default
public class MyAssetChaincode implements ContractInterface {
    public MyAssetChaincode() {}

    /**
     *
     */
    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public void init(final Context ctx) {
        addUser(ctx, "1", "zlt",100D);
        addUser(ctx, "2", "admin",200D);
        addUser(ctx, "3", "guest",300D);
    }

    /**
     *
     */
    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public String getHistory(final Context ctx, final String userId) {
        Map<String, String> userHistory = new HashMap<>();
        ChaincodeStub stub = ctx.getStub();
        QueryResultsIterator<KeyModification> iterator = stub.getHistoryForKey(userId);
        for (KeyModification result: iterator) {
            userHistory.put(result.getTxId(), result.getStringValue());
        }
        return JSON.toJSONString(userHistory);
    }

    /**
     *
     */
    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public String addUser(final Context ctx, final String userId, final String name, final double money) {
        ChaincodeStub stub = ctx.getStub();
        User user = new User(userId, name);
        String userJson = JSON.toJSONString(user);
        stub.putStringState(userId, userJson);
        return stub.getTxId();
    }

    /**
     *
     */
    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public User getUser(final Context ctx, final String userId) {
        ChaincodeStub stub = ctx.getStub();
        String userJSON = stub.getStringState(userId);
        if (userJSON == null || userJSON.isEmpty()) {
            String errorMessage = String.format("User %s does not exist", userId);
            throw new ChaincodeException(errorMessage);
        }
        return JSON.parseObject(userJSON, User.class);
    }

    /**
     *
     */
    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public String queryAll(final Context ctx) {
        ChaincodeStub stub = ctx.getStub();
        List<User> userList = new ArrayList<>();
        QueryResultsIterator<KeyValue> results = stub.getStateByRange("", "");
        for (KeyValue result: results) {
            User user = JSON.parseObject(result.getStringValue(), User.class);
            System.out.println(user);
            userList.add(user);
        }
        return JSON.toJSONString(userList);
    }




    public boolean addBLock(final Context ctx,final String blockJson){
        ChaincodeStub stub = ctx.getStub();
        Block block = JSON.parseObject(blockJson,Block.class);
        stub.putStringState(block.getBlockId().toString(), blockJson);
        return true;
    }

    public boolean transferWork(final Context ctx,final String blockJson,final String userId){
        ChaincodeStub stub = ctx.getStub();
        Block<Work> block = JSON.parseObject(blockJson,new TypeReference<Block<Work>>(){});
        // 创建一个新的区块，id和之前一样，这样来进行交易过程
        Block<Work> workBlock = new Block<Work>(block.getBlockId(),block.getDataType(),new Work(block.getData().getWorkId(),Long.parseLong(userId),block.getData().getTitle(),block.getData().getFileUrl()));
        stub.putStringState(block.getBlockId().toString(),JSON.toJSONString(workBlock));
        return true;
    }

    public String getAllWorks(final Context ctx){
        ChaincodeStub stub = ctx.getStub();
        List<Block<Work>> workList = new ArrayList<>();
        QueryResultsIterator<KeyValue> results = stub.getStateByRange("", "");
        for (KeyValue result: results) {
            Block block = JSON.parseObject(result.getStringValue(), Block.class);
            if (block.getDataType() == 2){
                Block blockWork = JSON.parseObject(result.getStringValue(),new TypeReference<Block<Work>>(){});
                workList.add(blockWork);
            }
        }
        return JSON.toJSONString(workList);
    }

    /**
     * 转账
     * @param sourceId 源用户id
     * @param targetId 目标用户id
     * @param money 金额
     */
    /*@Transaction(intent = Transaction.TYPE.SUBMIT)
    public String transfer(final Context ctx, final String sourceId, final String targetId, final double money) {
        ChaincodeStub stub = ctx.getStub();
        User sourceUser = getUser(ctx, sourceId);
        User targetUser = getUser(ctx, targetId);
        if (sourceUser.getMoney() < money) {
            String errorMessage = String.format("The balance of user %s is insufficient", sourceId);
            throw new ChaincodeException(errorMessage);
        }
        User newSourceUser = new User(sourceUser.getUserId(), sourceUser.getName(), sourceUser.getMoney() - money);
        User newTargetUser = new User(targetUser.getUserId(), targetUser.getName(), targetUser.getMoney() + money);
        stub.putStringState(sourceId, JSON.toJSONString(newSourceUser));
        stub.putStringState(targetId, JSON.toJSONString(newTargetUser));
        return stub.getTxId();
    }*/
}
