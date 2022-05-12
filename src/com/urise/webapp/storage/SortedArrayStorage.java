package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage<Integer> {

    private static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getUuid);

    @Override
    protected void saveToArray(Integer searchKey, Resume r) {
        int resumeIndex = searchKey;
        int insertIndex = -resumeIndex - 1;
        System.arraycopy(storage, insertIndex, storage, insertIndex + 1, storageSize - insertIndex);
        storage[insertIndex] = r;
    }

    @Override
    protected void deleteFromArray(Integer searchKey) {
        int resumeIndex = searchKey;
        if (storageSize - 1 - resumeIndex >= 0) {
            System.arraycopy(storage, resumeIndex + 1, storage, resumeIndex, storageSize - 1 - resumeIndex);
        }
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid, "");
        return Arrays.binarySearch(storage, 0, storageSize, searchKey, RESUME_COMPARATOR);
    }
}