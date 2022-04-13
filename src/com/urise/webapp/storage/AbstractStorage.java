package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public void clear() {
        clearStorage();
    }

    public void save(Resume r) {
        checkStorageLimit(r);
        int resumeIndex = findIndex(r.toString());
        if (resumeIndex < 0) {
            saveStorage(resumeIndex, r);
        } else {
            throw new ExistStorageException(r.getUuid());
        }
    }

    public Resume get(String uuid) {
        int resumeIndex = findIndex(uuid);
        if (resumeIndex < 0) {
            throw new NotExistStorageException(uuid);
        }
        return getStorage(resumeIndex);
    }

    public void delete(String uuid) {
        int resumeIndex = findIndex(uuid);
        if (resumeIndex < 0) {
            throw new NotExistStorageException(uuid);
        }
        deleteStorage(resumeIndex);
    }

    public void update(Resume resume) {
        int resumeIndex = findIndex(resume.toString());
        if (resumeIndex < 0) {
            throw new NotExistStorageException(resume.getUuid());
        }
        storageUpdate(resumeIndex, resume);
    }

    public Resume[] getAll() {
        return storageGetAll();
    }

    public int size() {
        return storageSize();
    }

    protected abstract void clearStorage();

    protected abstract void saveStorage(int resumeIndex, Resume r);

    protected abstract Resume getStorage(int resumeIndex);

    protected abstract void deleteStorage(int resumeIndex);

    protected abstract void storageUpdate(int resumeIndex, Resume resume);

    protected abstract Resume[] storageGetAll();

    protected abstract int storageSize();

    protected abstract int findIndex(String toString);

    protected abstract void checkStorageLimit(Resume r);
}