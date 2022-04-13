package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    private final List<Resume> collection = new ArrayList<>();

    protected void saveStorage(int resumeIndex, Resume r) {
        collection.add(r);
    }

    protected void deleteStorage(int resumeIndex) {
        collection.remove(resumeIndex);
    }

    protected void clearStorage() {
        collection.clear();
    }

    protected int storageSize() {
        return collection.size();
    }

    protected Resume getStorage(int resumeIndex) {
        return collection.get(resumeIndex);
    }

    protected Resume[] storageGetAll() {
        return collection.toArray(new Resume[size()]);
    }

    protected void storageUpdate(int resumeIndex, Resume resume) {
        collection.set(resumeIndex, resume);
    }

    protected void checkStorageLimit(Resume r) {
    }

    protected int findIndex(String uuid) {
        int i = 0;
        for (Resume r : collection) {
            if (r.getUuid().equals(uuid)) {
                return i;
            }
            i++;
        }
        return -1;
    }
}