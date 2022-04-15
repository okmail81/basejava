package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void saveToArray(int resumeIndex, Resume r) {
        int insertIndex = -resumeIndex - 1;
        System.arraycopy(storage, insertIndex, storage, insertIndex + 1, storageSize - insertIndex);
        storage[insertIndex] = r;
    }

    @Override
    protected void deleteFromArray(int resumeIndex) {
        if (storageSize - 1 - resumeIndex >= 0) {
            System.arraycopy(storage, resumeIndex + 1, storage, resumeIndex, storageSize - 1 - resumeIndex);
        }
    }

    @Override
    protected String findIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Integer.toString(Arrays.binarySearch(storage, 0, storageSize, searchKey));
    }
}