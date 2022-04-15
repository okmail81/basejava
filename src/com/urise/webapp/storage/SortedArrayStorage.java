package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void saveToArray(Object searchKey, Resume r) {
        int resumeIndex = (Integer) searchKey;
        int insertIndex = -resumeIndex - 1;
        System.arraycopy(storage, insertIndex, storage, insertIndex + 1, storageSize - insertIndex);
        storage[insertIndex] = r;
    }

    @Override
    protected void deleteFromArray(Object searchKey) {
        int resumeIndex = (Integer) searchKey;
        if (storageSize - 1 - resumeIndex >= 0) {
            System.arraycopy(storage, resumeIndex + 1, storage, resumeIndex, storageSize - 1 - resumeIndex);
        }
    }

    @Override
    protected Object findSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, storageSize, searchKey);
    }
}