package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {

    private final Map<String, Resume> storage = new HashMap();

    protected void saveResume(Object searchKey, Resume r) {
        storage.put(r.getUuid(), r);
    }

    protected void deleteStorage(Object searchKey) {
        storage.remove((String) searchKey);
    }

    public void clear() {
        storage.clear();
    }

    public int size() {
        return storage.size();
    }

    protected Resume getResume(Object searchKey) {
        return storage.get((String) searchKey);
    }

    public Resume[] getAll() {
        return storage.values().toArray(new Resume[size()]);
    }

    protected void updateResume(Object searchKey, Resume r) {
        storage.replace((String) searchKey, r);
    }

    protected boolean isSearchKeyExist(Object searchKey) {
        return searchKey != "";
    }

    protected Object findSearchKey(String uuid) {
        if (storage.get(uuid) == null) {
            return "";
        } else {
            return uuid;
        }
    }
}