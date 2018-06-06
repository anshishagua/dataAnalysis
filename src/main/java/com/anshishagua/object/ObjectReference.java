package com.anshishagua.object;

import com.anshishagua.constants.ObjectType;

/**
 * User: lixiao
 * Date: 2018/6/6
 * Time: 下午3:08
 */

public class ObjectReference {
    private long id;
    private long objectId;
    private ObjectType objectType;
    private String objectName;
    private long refObjectId;
    private ObjectType refObjectType;
    private String refObjectName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getObjectId() {
        return objectId;
    }

    public void setObjectId(long objectId) {
        this.objectId = objectId;
    }

    public ObjectType getObjectType() {
        return objectType;
    }

    public void setObjectType(ObjectType objectType) {
        this.objectType = objectType;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public long getRefObjectId() {
        return refObjectId;
    }

    public void setRefObjectId(long refObjectId) {
        this.refObjectId = refObjectId;
    }

    public ObjectType getRefObjectType() {
        return refObjectType;
    }

    public void setRefObjectType(ObjectType refObjectType) {
        this.refObjectType = refObjectType;
    }

    public String getRefObjectName() {
        return refObjectName;
    }

    public void setRefObjectName(String refObjectName) {
        this.refObjectName = refObjectName;
    }
}
