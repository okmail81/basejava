package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void saveToArray(int resumeIndex, Resume r) {
        storage[storageSize] = r;
    }

    @Override
    protected void deleteFromArray(int resumeIndex) {
        storage[resumeIndex] = storage[storageSize - 1];
    }

    @Override
    protected String findIndex(String uuid) {
        for (int i = 0; i < storageSize; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return Integer.toString(i);
            }
        }
        return Integer.toString(-1);
    }
}