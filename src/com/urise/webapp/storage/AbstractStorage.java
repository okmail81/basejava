package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public void save(Resume r) {
        String resumeIndex = findIndex(r.toString());
        existStorage(resumeIndex, r);
        saveResume(resumeIndex, r);
    }

    public Resume get(String uuid) {
        String resumeIndex = findIndex(uuid);
        nonExistStorage(resumeIndex, uuid);
        return getResume(resumeIndex);
    }

    public void delete(String uuid) {
        String resumeIndex = findIndex(uuid);
        nonExistStorage(resumeIndex, uuid);
        deleteStorage(resumeIndex);
    }

    public void update(Resume resume) {
        String resumeIndex = findIndex(resume.toString());
        nonExistStorage(resumeIndex, resume.getUuid());
        updateResume(resumeIndex, resume);
    }

    protected abstract void saveResume(String resumeIndex, Resume r);

    protected abstract Resume getResume(String resumeIndex);

    protected abstract void deleteStorage(String resumeIndex);

    protected abstract void updateResume(String resumeIndex, Resume resume);

    protected abstract String findIndex(String toString);

    protected abstract void existStorage(String resumeIndex, Resume r);

    protected abstract void nonExistStorage(String resumeIndex, String uuid);
}