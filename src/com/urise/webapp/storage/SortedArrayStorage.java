package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    protected void saveResume(int resumeIndex, Resume r) {
        int insertIndex = -resumeIndex - 1;
        System.arraycopy(storage, insertIndex, storage, insertIndex + 1, storageSize - insertIndex);
        storage[insertIndex] = r;
    }

    protected void deleteResume(int resumeIndex) {
        if (storageSize - 1 - resumeIndex >= 0) {
            System.arraycopy(storage, resumeIndex + 1, storage, resumeIndex, storageSize - 1 - resumeIndex);
        }
    }

    protected int findIndex(String uuid, boolean isShowMessage) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        int index = Arrays.binarySearch(storage, 0, storageSize, searchKey);
        if (isShowMessage && index < 0) {
            System.out.println("Резюме " + uuid + " не найдено");
        }
        return index;
    }
}