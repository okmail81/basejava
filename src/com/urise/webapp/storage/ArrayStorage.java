package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void saveToArray(Object searchKey, Resume r) {
        storage[storageSize] = r;
    }

    @Override
    protected void deleteFromArray(Object searchKey) {
        storage[(Integer) searchKey] = storage[storageSize - 1];
    }

    @Override
    protected Object findSearchKey(String uuid) {
        for (int i = 0; i < storageSize; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }
}