package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;

    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int storageSize = 0;

    protected void saveResume(Object searchKey, Resume r) {
        checkStorageLimit(r);
        saveToArray(searchKey, r);
        storageSize++;
    }

    protected void deleteStorage(Object searchKey) {
        deleteFromArray(searchKey);
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

    protected Resume getResume(Object searchKey) {
        return storage[(Integer) searchKey];
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, storageSize);
    }

    protected void updateResume(Object searchKey, Resume resume) {
        storage[(Integer) searchKey] = resume;
    }

    protected boolean isSearchKeyExist(Object searchKey) {
        return (Integer) searchKey >= 0;
    }

    protected void checkStorageLimit(Resume r) {
        if (STORAGE_LIMIT == storageSize) {
            throw new StorageException("Превышено число сохраненных резюме", r.getUuid());
        }
    }

    protected abstract void saveToArray(Object searchKey, Resume r);

    protected abstract void deleteFromArray(Object searchKey);

    protected abstract Object findSearchKey(String uuid);
}