package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public class ArrayStorage extends AbstractArrayStorage<Integer> {

    @Override
    protected void saveToArray(Integer searchKey, Resume r) {
        storage[storageSize] = r;
    }

    @Override
    protected void deleteFromArray(Integer searchKey) {
        storage[searchKey] = storage[storageSize - 1];
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < storageSize; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }
}