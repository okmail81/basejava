package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage {

    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected void saveResume(Object searchKey, Resume r) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected void deleteStorage(Object searchKey) {
        storage.remove((String) searchKey);
    }

    public void clear() {
        storage.clear();
    }

    public int size() {
        return storage.size();
    }

    @Override
    protected Resume getResume(Object searchKey) {
        return storage.get((String) searchKey);
    }

    @Override
    protected List<Resume> getList() {
        return new ArrayList(storage.values());
    }

    @Override
    protected void updateResume(Object searchKey, Resume r) {
        storage.replace((String) searchKey, r);
    }

    @Override
    protected boolean isSearchKeyExist(Object searchKey) {
        return searchKey != null;
    }

    @Override
    protected Object findSearchKey(String uuid) {
        if (storage.get(uuid) == null) {
            return null;
        } else {
            return uuid;
        }
    }
}