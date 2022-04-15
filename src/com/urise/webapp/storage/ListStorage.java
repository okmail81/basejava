package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    private final List<Resume> storage = new ArrayList<>();

    protected void saveResume(Object searchKey, Resume r) {
        storage.add(r);
    }

    protected void deleteStorage(Object searchKey) {
        int resumeIndex = (Integer) searchKey;
        storage.remove(resumeIndex);
    }

    public void clear() {
        storage.clear();
    }

    public int size() {
        return storage.size();
    }

    protected Resume getResume(Object searchKey) {
        return storage.get((Integer) searchKey);
    }

    public Resume[] getAll() {
        return storage.toArray(new Resume[size()]);
    }

    protected void updateResume(Object searchKey, Resume resume) {
        storage.set((Integer) searchKey, resume);
    }

    protected boolean isSearchKeyExist(Object searchKey) {
        return (Integer) searchKey >= 0;
    }

    protected Object findSearchKey(String uuid) {
        int i = 0;
        for (Resume r : storage) {
            if (r.getUuid().equals(uuid)) {
                return i;
            }
            i++;
        }
        return -1;
    }
}