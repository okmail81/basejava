package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    private final List<Resume> storage = new ArrayList<>();

    protected void saveResume(int resumeIndex, Resume r) {
        storage.add(r);
    }

    protected void deleteStorage(int resumeIndex) {
        storage.remove(resumeIndex);
    }

    public void clear() {
        storage.clear();
    }

    public int size() {
        return storage.size();
    }

    protected Resume getResume(int resumeIndex) {
        return storage.get(resumeIndex);
    }

    public Resume[] getAll() {
        return storage.toArray(new Resume[size()]);
    }

    protected void updateResume(int resumeIndex, Resume resume) {
        storage.set(resumeIndex, resume);
    }

    protected int findIndex(String uuid) {
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