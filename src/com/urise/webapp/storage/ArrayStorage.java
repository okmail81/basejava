package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void saveResume(int resumeIndex, Resume r) {
        storage[storageSize] = r;
    }

    @Override
    protected void deleteResume(int resumeIndex) {
        storage[resumeIndex] = storage[storageSize - 1];
    }

    @Override
    protected int findIndex(String uuid, boolean isShowMessage) {
        for (int i = 0; i < storageSize; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        if (isShowMessage) {
            System.out.println("Резюме " + uuid + " не найдено");
        }
        return -1;
    }
}