package com.linrty.fabric.model;

import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

import java.util.Objects;

/**
 * @PackageName: com.linrty.fabric.model
 * @ClassName: Block
 * @Description:
 * @author: Linrty
 * @date: 2022/4/4 19:38
 */
@DataType
public class Block<T> {

    /**
     *
     */
    @Property
    private final Long blockId;

    /**
     *
     */
    @Property
    private final int dataType;


    /**
     *
     */
    @Property
    private final T data;

    public Block(Long blockId, int dataType, T data) {
        this.blockId = blockId;
        this.dataType = dataType;
        this.data = data;
    }

    public Long getBlockId() {
        return blockId;
    }

    public int getDataType() {
        return dataType;
    }

    public T getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Block<?> block = (Block<?>) o;
        return dataType == block.dataType && Objects.equals(blockId, block.blockId) && Objects.equals(data, block.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(blockId, dataType, data);
    }


}
