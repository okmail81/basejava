package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 100000;

    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int storageSize = 0;

    protected void saveStorage(int resumeIndex, Resume r) {
        saveResume(resumeIndex, r);
        storageSize++;
    }

    protected void deleteStorage(int resumeIndex) {
        deleteResume(resumeIndex);
        storage[storageSize - 1] = null;
        storageSize--;
    }

    protected void clearStorage() {
        if (storageSize != 0) {
            Arrays.fill(storage, 0, storageSize, null);
            storageSize = 0;
        }
    }

    protected int storageSize() {
        return storageSize;
    }

    protected Resume getStorage(int resumeIndex) {
        return storage[resumeIndex];
    }

    protected Resume[] storageGetAll() {
        return Arrays.copyOf(storage, storageSize);
    }

    protected void storageUpdate(int resumeIndex, Resume resume) {
        storage[resumeIndex] = resume;
    }

    protected void checkStorageLimit(Resume r) {
        if (STORAGE_LIMIT == storageSize) {
            throw new StorageException("Превышено число сохраненных резюме", r.getUuid());
        }
    }

    protected abstract void saveResume(int resumeIndex, Resume r);

    protected abstract void deleteResume(int resumeIndex);

    protected abstract int findIndex(String uuid);
}