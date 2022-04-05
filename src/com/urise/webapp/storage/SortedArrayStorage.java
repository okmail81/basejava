package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void saveResume(int resumeIndex, Resume r) {
        int insertIndex = -resumeIndex - 1;
        System.arraycopy(storage, insertIndex, storage, insertIndex + 1, storageSize - insertIndex);
        storage[insertIndex] = r;
    }

    @Override
    protected void deleteResume(int resumeIndex) {
        if (storageSize - 1 - resumeIndex >= 0) {
            System.arraycopy(storage, resumeIndex + 1, storage, resumeIndex, storageSize - 1 - resumeIndex);
        }
    }

    @Override
    protected int findIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        int index = Arrays.binarySearch(storage, 0, storageSize, searchKey);
          return index;
    }
}