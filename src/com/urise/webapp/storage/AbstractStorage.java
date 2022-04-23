package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Collections;
import java.util.List;

public abstract class AbstractStorage implements Storage {

    protected abstract void saveResume(Object searchKey, Resume r);

    protected abstract Resume getResume(Object searchKey);

    protected abstract void deleteStorage(Object searchKey);

    protected abstract void updateResume(Object searchKey, Resume resume);

    protected abstract Object findSearchKey(String uuid);

    protected abstract boolean isSearchKeyExist(Object searchKey);

    protected abstract List<Resume> getList();

    public void save(Resume r) {
        Object searchKey = getNotExistedSearchKey(r);
        saveResume(searchKey, r);
    }

    public Resume get(String uuid) {
        Object searchKey = getExistedSearchKey(uuid);
        return getResume(searchKey);
    }

    public void delete(String uuid) {
        Object searchKey = getExistedSearchKey(uuid);
        deleteStorage(searchKey);
    }

    public void update(Resume r) {
        Object searchKey = getExistedSearchKey(r.getUuid());
        updateResume(searchKey, r);
    }

    public List<Resume> getAllSorted() {
        List<Resume> listResume = getList();
        Collections.sort(listResume);
        return listResume;
    }

    private Object getExistedSearchKey(String uuid) {
        Object searchKey = findSearchKey(uuid);
        if (!isSearchKeyExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private Object getNotExistedSearchKey(Resume r) {
        Object searchKey = findSearchKey(r.getUuid());
        if (isSearchKeyExist(searchKey)) {
            throw new ExistStorageException(r.getUuid());
        }
        return searchKey;
    }
}