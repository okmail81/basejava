package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {

    private final Map<String, Resume> storage = new HashMap();

    protected void saveResume(String resumeIndex, Resume r) {
        storage.put(r.getUuid(), r);
    }

    protected void deleteStorage(String resumeIndex) {
        storage.remove(resumeIndex);
    }

    public void clear() {
        storage.clear();
    }

    public int size() {
        return storage.size();
    }

    protected Resume getResume(String resumeIndex) {
        return storage.get(resumeIndex);
    }

    public Resume[] getAll() {
        return storage.values().toArray(new Resume[size()]);
    }

    protected void updateResume(String resumeIndex, Resume r) {
        storage.replace(resumeIndex, r);
    }

    protected void existStorage(String resumeIndex, Resume r) {
        if (!resumeIndex.equals("")) {
            throw new ExistStorageException(r.getUuid());
        }
    }

    protected void nonExistStorage(String resumeIndex, String uuid) {
        if (resumeIndex.equals("")) {
            throw new NotExistStorageException(uuid);
        }
    }

    protected String findIndex(String uuid) {
        for (Map.Entry<String, Resume> entry : storage.entrySet()) {
            if (entry.getKey().equals(uuid)) {
                return entry.getKey();
            }
        }
        return "";
    }
}