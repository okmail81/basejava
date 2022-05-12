package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage<I extends Number> extends AbstractStorage<Integer> {
    protected static final int STORAGE_LIMIT = 10000;

    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int storageSize = 0;

    protected abstract void saveToArray(Integer searchKey, Resume r);

    protected abstract void deleteFromArray(Integer searchKey);

    protected abstract Integer getSearchKey(String uuid);

    @Override
    protected void doSave(Resume r, Integer searchKey) {
        checkStorageLimit(r);
        saveToArray(searchKey, r);
        storageSize++;
    }

    private void checkStorageLimit(Resume r) {
        if (STORAGE_LIMIT == storageSize) {
            throw new StorageException("Превышено число сохраненных резюме", r.getUuid());
        }
    }

    @Override
    protected void doDelete(Integer searchKey) {
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

    @Override
    protected Resume doGet(Integer searchKey) {
        return storage[searchKey];
    }

    @Override
    protected List<Resume> doCopyAll() {
        return Arrays.asList(Arrays.copyOf(storage, size()));
    }

    @Override
    protected void doUpdate(Resume resume, Integer searchKey) {
        storage[searchKey] = resume;
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return searchKey >= 0;
    }
}