package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    protected abstract void saveResume(SK searchKey, Resume r);

    protected abstract Resume getResume(SK searchKey);

    protected abstract void deleteStorage(SK searchKey);

    protected abstract void updateResume(SK searchKey, Resume resume);

    protected abstract SK findSearchKey(String uuid);

    protected abstract boolean isSearchKeyExist(SK searchKey);

    protected abstract List<Resume> getList();

    public void save(Resume r) {
        LOG.info("Save " + r);
        SK searchKey = getNotExistedSearchKey(r.getUuid());
        saveResume(searchKey, r);
    }

    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        SK searchKey = getExistedSearchKey(uuid);
        return getResume(searchKey);
    }

    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        SK searchKey = getExistedSearchKey(uuid);
        deleteStorage(searchKey);
    }

    public void update(Resume r) {
        LOG.info("Update " + r);
        SK searchKey = getExistedSearchKey(r.getUuid());
        updateResume(searchKey, r);
    }

    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        List<Resume> listResume = getList();
        Collections.sort(listResume);
        return listResume;
    }

    private SK getExistedSearchKey(String uuid) {
        SK searchKey = findSearchKey(uuid);
        if (!isSearchKeyExist(searchKey)) {
            LOG.warning("Resume " + uuid + " not exist");
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private SK getNotExistedSearchKey(String uuid) {
        SK searchKey = findSearchKey(uuid);
        if (isSearchKeyExist(searchKey)) {
            LOG.warning("Resume " + uuid + " already exist");
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }
}