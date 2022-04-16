package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected abstract void saveResume(Object searchKey, Resume r);

    protected abstract Resume getResume(Object searchKey);

    protected abstract void deleteStorage(Object searchKey);

    protected abstract void updateResume(Object searchKey, Resume resume);

    protected abstract Object findSearchKey(String uuid);

    protected abstract boolean isSearchKeyExist(Object searchKey);

    public void save(Resume r) {
        Object searchKey = getExistedSearchKey(r);
        saveResume(searchKey, r);
    }

    public Resume get(String uuid) {
        Object searchKey = getNotExistedSearchKey(uuid);
        return getResume(searchKey);
    }

    public void delete(String uuid) {
        Object searchKey = getNotExistedSearchKey(uuid);
        deleteStorage(searchKey);
    }

    public void update(Resume r) {
        Object searchKey = getNotExistedSearchKey(r.getUuid());
        updateResume(searchKey, r);
    }

    private Object getExistedSearchKey(Resume r) {
        Object searchKey = findSearchKey(r.getUuid());
        if (isSearchKeyExist(searchKey)) {
            throw new ExistStorageException(r.getUuid());
        }
        return searchKey;
    }

    private Object getNotExistedSearchKey(String uuid) {
        Object searchKey = findSearchKey(uuid);
        if (!isSearchKeyExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }
}