package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int storageSize = 0;

    public void clear() {
        if (storageSize != 0) {
            Arrays.fill(storage, 0, storageSize, null);
            storageSize = 0;
        }
    }

    public void save(Resume r) {
        if (storage.length == storageSize) {
            System.out.println("Превышено число сохраненных резюме");
            return;
        }
        if (getResumeIndex(toString(), false) == -1) {
            storage[storageSize] = r;
            storageSize++;
        }
    }

    public Resume get(String uuid) {
        int resumeIndex = getResumeIndex(uuid, true);
        if (resumeIndex >= 0) {
            return storage[resumeIndex];
        } else {
            return null;
        }
    }

    public void delete(String uuid) {
        int resumeIndex = getResumeIndex(uuid, true);
        if (resumeIndex >= 0) {
            storage[resumeIndex].setUuid(storage[storageSize - 1].getUuid());
            storage[storageSize - 1] = null;
            storageSize--;
        }
    }

    public void update(Resume resume, String uuid) {
        if (getResumeIndex(resume.toString(), true) >= 0) {
            resume.setUuid(uuid);
        }
    }

    private int getResumeIndex(String uuid, boolean showMessage) {
        for (int i = 0; i < storageSize; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        if (showMessage) {
            System.out.println("Резюме " + uuid + " не найдено");
        }
        return -1;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, storageSize);
    }

    public int size() {
        return storageSize;
    }
}