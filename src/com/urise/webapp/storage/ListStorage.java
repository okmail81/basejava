package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    private final List<Resume> storage = new ArrayList<>();

    protected void saveResume(String resumeIndex, Resume r) {
        storage.add(r);
    }

    protected void deleteStorage(String resumeIndex) {
        storage.remove(Integer.parseInt(resumeIndex));
    }

    public void clear() {
        storage.clear();
    }

    public int size() {
        return storage.size();
    }

    protected Resume getResume(String resumeIndex) {
        return storage.get(Integer.parseInt(resumeIndex));
    }

    public Resume[] getAll() {
        return storage.toArray(new Resume[size()]);
    }

    protected void updateResume(String resumeIndex, Resume resume) {
        storage.set(Integer.parseInt(resumeIndex), resume);
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

    protected String findIndex(String uuid) {
        int i = 0;
        for (Resume r : storage) {
            if (r.getUuid().equals(uuid)) {
                return Integer.toString(i);
            }
            i++;
        }
        return Integer.toString(-1);
    }
}