package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;

    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int storageSize = 0;

    protected void saveResume(int resumeIndex, Resume r) {
        checkStorageLimit(r);
        saveToArray(resumeIndex, r);
        storageSize++;
    }

    protected void deleteStorage(int resumeIndex) {
        deleteFromArray(resumeIndex);
        storage[storageSize - 1] = null;
        storageSize--;
    }

    public void clear() {
        if (storageSize != 0) {
            Arrays.fill(storage, 0, storageSize, null);
            storageSize = 0;
        }
    }

    public int size() {
        return storageSize;
    }

    protected Resume getResume(int resumeIndex) {
        return storage[resumeIndex];
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, storageSize);
    }

    protected void updateResume(int resumeIndex, Resume resume) {
        storage[resumeIndex] = resume;
    }

    protected void checkStorageLimit(Resume r) {
        if (STORAGE_LIMIT == storageSize) {
            throw new StorageException("Превышено число сохраненных резюме", r.getUuid());
        }
    }

    protected abstract void saveToArray(int resumeIndex, Resume r);

    protected abstract void deleteFromArray(int resumeIndex);

    protected abstract int findIndex(String uuid);
}