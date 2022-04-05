package com.linrty.fabric;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.linrty.fabric.model.Block;
import com.linrty.fabric.model.User;
import com.linrty.fabric.model.Work;

/**
 * @PackageName: com.linrty.fabric
 * @ClassName: Test
 * @Description: TODO
 * @author: Linrty
 * @date: 2022/4/4 19:59
 */
public class Test {
    public static void main(String[] args) {
        String s1 = JSON.toJSONString(new Block<User>(456L,1,new User("789","sldjf")));
        String s2 = JSON.toJSONString(new Block<Work>(444L,2,new Work(88L,66L,"sdkafh ","sdafasd5556565")));
        System.out.println(s1);
        System.out.println(s2);
        Block block = JSON.parseObject(s1,Block.class);
        Block<User> blockUser = null;
        if (block.getDataType() == 1){
            blockUser = JSON.parseObject(s1,new TypeReference<Block<User>>(){});
        }
        System.out.println(blockUser.getData().toString());
    }
}
