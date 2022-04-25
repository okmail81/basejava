package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage<String> {

    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected void saveResume(String searchKey, Resume r) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected void deleteStorage(String searchKey) {
        storage.remove(searchKey);
    }

    public void clear() {
        storage.clear();
    }

    public int size() {
        return storage.size();
    }

    @Override
    protected Resume getResume(String searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected List<Resume> getList() {
        return new ArrayList<>(storage.values());
    }

    @Override
    protected void updateResume(String searchKey, Resume r) {
        storage.replace(searchKey, r);
    }

    @Override
    protected boolean isSearchKeyExist(String searchKey) {
        return storage.containsKey(searchKey);
    }

    @Override
    protected String findSearchKey(String uuid) {
        return uuid;
    }
}