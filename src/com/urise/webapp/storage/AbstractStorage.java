package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public void save(Resume r) {
        Object searchKey = findSearchKey(r.getUuid());
        existStorage(searchKey, r);
        saveResume(searchKey, r);
    }

    public Resume get(String uuid) {
        Object searchKey = findSearchKey(uuid);
        nonExistStorage(searchKey, uuid);
        return getResume(searchKey);
    }

    public void delete(String uuid) {
        Object searchKey = findSearchKey(uuid);
        nonExistStorage(searchKey, uuid);
        deleteStorage(searchKey);
    }

    public void update(Resume r) {
        Object searchKey = findSearchKey(r.getUuid());
        nonExistStorage(searchKey, r.getUuid());
        updateResume(searchKey, r);
    }

    protected void existStorage(Object searchKey, Resume r) {
        if (isSearchKeyExist(searchKey)) {
            throw new ExistStorageException(r.getUuid());
        }
    }

    protected void nonExistStorage(Object searchKey, String uuid) {
        if (!isSearchKeyExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
    }

    protected abstract void saveResume(Object searchKey, Resume r);

    protected abstract Resume getResume(Object searchKey);

    protected abstract void deleteStorage(Object searchKey);

    protected abstract void updateResume(Object searchKey, Resume resume);

    protected abstract Object findSearchKey(String uuid);

    protected abstract boolean isSearchKeyExist(Object searchKey);
}