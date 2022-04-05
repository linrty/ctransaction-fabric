package com.linrty.fabric.model;

import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

import java.util.Objects;

/**
 * @PackageName: com.linrty.fabric.model
 * @ClassName: Work
 * @Description:
 * @author: Linrty
 * @date: 2022/4/4 19:17
 */
@DataType
public class Work {

    @Property
    private final Long workId;

    @Property
    private final Long userId;

    @Property
    private final String title;

    @Property
    private final String fileUrl;


    public Work(Long workId, Long userId, String title, String fileUrl) {
        this.workId = workId;
        this.userId = userId;
        this.title = title;
        this.fileUrl = fileUrl;
    }

    public Long getWorkId() {
        return workId;
    }


    public Long getUserId() {
        return userId;
    }


    public String getTitle() {
        return title;
    }



    public String getFileUrl() {
        return fileUrl;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Work work = (Work) o;
        return Objects.equals(workId, work.workId) &&
                Objects.equals(userId, work.userId) &&
                Objects.equals(title, work.title) &&
                Objects.equals(fileUrl, work.fileUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(workId, userId, title, fileUrl);
    }
}
