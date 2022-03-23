package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int storageSize = 0;

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
        if (getResume(toString(), false) == null) {
            storage[storageSize] = r;
            storageSize++;
        }
    }

    public Resume get(String uuid) {
        return getResume(uuid, true);
    }

    public void delete(String uuid) {
        Resume resume = getResume(uuid, true);
        if (resume != null) {
            resume.setUuid(storage[storageSize - 1].getUuid());
            storage[storageSize - 1] = null;
            storageSize--;
        }
    }

    public void update(Resume resume, String uuid) {
        if (getResume(resume.toString(), true) != null) {
            resume.setUuid(uuid);
        }
    }

    private Resume getResume(String uuid, boolean showMessage) {
        for (int i = 0; i < storageSize; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return storage[i];
            }
        }
        if (showMessage) {
            System.out.println("Резюме " + uuid + " не найдено");
        }
        return null;
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
