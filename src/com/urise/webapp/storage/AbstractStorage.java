package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public void save(Resume r) {
        int resumeIndex = findIndex(r.toString());
        if (resumeIndex < 0) {
            saveResume(resumeIndex, r);
        } else {
            throw new ExistStorageException(r.getUuid());
        }
    }

    public Resume get(String uuid) {
        int resumeIndex = findIndex(uuid);
        if (resumeIndex < 0) {
            throw new NotExistStorageException(uuid);
        }
        return getResume(resumeIndex);
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
        updateResume(resumeIndex, resume);
    }

    protected abstract void saveResume(int resumeIndex, Resume r);

    protected abstract Resume getResume(int resumeIndex);

    protected abstract void deleteStorage(int resumeIndex);

    protected abstract void updateResume(int resumeIndex, Resume resume);

    protected abstract int findIndex(String toString);
}