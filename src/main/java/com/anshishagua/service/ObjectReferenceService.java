package com.anshishagua.service;

import com.anshishagua.mybatis.mapper.ObjectReferenceMapper;
import com.anshishagua.object.ObjectReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * User: lixiao
 * Date: 2018/6/6
 * Time: 下午3:23
 */

@Service
public class ObjectReferenceService {
    @Autowired
    private ObjectReferenceMapper objectReferenceMapper;

    public void addObjectReference(ObjectReference objectReference) {
        Objects.requireNonNull(objectReference);

        objectReferenceMapper.insert(objectReference);
    }

    public void addObjectReferences(List<ObjectReference> list) {
        Objects.requireNonNull(list);

        objectReferenceMapper.insertBatch(list);
    }

    public List<ObjectReference> getByObjectId(long objectId) {
        return objectReferenceMapper.getByObjectId(objectId);
    }
}