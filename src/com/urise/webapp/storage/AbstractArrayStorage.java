package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;

    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int storageSize = 0;

    protected void saveResume(String resumeIndex, Resume r) {
        checkStorageLimit(r);
        saveToArray(Integer.parseInt(resumeIndex), r);
        storageSize++;
    }

    protected void deleteStorage(String resumeIndex) {
        deleteFromArray(Integer.parseInt(resumeIndex));
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

    protected Resume getResume(String resumeIndex) {
        return storage[Integer.parseInt(resumeIndex)];
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, storageSize);
    }

    protected void updateResume(String resumeIndex, Resume resume) {
        storage[Integer.parseInt(resumeIndex)] = resume;
    }

    protected void checkStorageLimit(Resume r) {
        if (STORAGE_LIMIT == storageSize) {
            throw new StorageException("Превышено число сохраненных резюме", r.getUuid());
        }
    }

    protected void existStorage(String resumeIndex, Resume r) {
        if (Integer.parseInt(resumeIndex) > 0) {
            throw new ExistStorageException(r.getUuid());
        }
    }

    protected void nonExistStorage(String resumeIndex, String uuid) {
        if (Integer.parseInt(resumeIndex) < 0) {
            throw new NotExistStorageException(uuid);
        }
    }

    protected abstract void saveToArray(int resumeIndex, Resume r);

    protected abstract void deleteFromArray(int resumeIndex);

    protected abstract String findIndex(String uuid);
}