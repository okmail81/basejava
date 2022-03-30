package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 100000;

    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int storageSize = 0;

    public void save(Resume r) {
        if (STORAGE_LIMIT == storageSize) {
            System.out.println("Превышено число сохраненных резюме");
            return;
        }
        int resumeIndex = findIndex(r.toString(), false);
        if (resumeIndex < 0) {
            saveResume(resumeIndex, r);
            storageSize++;
        }
    }

    public void delete(String uuid) {
        int resumeIndex = findIndex(uuid, true);
        if (resumeIndex >= 0) {
            deleteResume(resumeIndex);
            storage[storageSize - 1] = null;
            storageSize--;
        }
    }

    public void clear() {
        if (storageSize != 0) {
            Arrays.fill(storage, 0, storageSize, null);
            storageSize = 0;
        }
    }

    public int size() {
        return storageSize;
    }

    public Resume get(String uuid) {
        int resumeIndex = findIndex(uuid, true);
        if (resumeIndex >= 0) {
            return storage[resumeIndex];
        }
        return null;
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, storageSize);
    }

    public void update(Resume resume) {
        int resumeIndex = findIndex(resume.toString(), true);
        if (resumeIndex >= 0) {
            storage[resumeIndex] = resume;
        }
    }

    protected abstract void saveResume(int resumeIndex, Resume r);

    protected abstract void deleteResume(int resumeIndex);

    protected abstract int findIndex(String uuid, boolean b);
}