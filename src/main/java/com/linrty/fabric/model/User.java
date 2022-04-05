package com.linrty.fabric.model;

import com.alibaba.fastjson.JSON;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

import java.util.Objects;

/**
 * 帐户对象
 *
 * @author Linrty
 * @version 1.0
 * @date 2022/2/8
 */
@DataType
public class User {
    @Property
    private final String userId;

    @Property
    private final String name;

    public User(final String userId, final String name) {
        this.userId = userId;
        this.name = name;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }
        User other = (User) obj;
        return Objects.deepEquals(
                new String[] {getUserId(), getName()},
                new String[] {other.getUserId(), other.getName()});
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getName());
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }
}
