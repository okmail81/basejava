package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage<String> {

    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected void doSave(Resume r, String searchKey) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected void doDelete(String searchKey) {
        storage.remove(searchKey);
    }

    public void clear() {
        storage.clear();
    }

    public int size() {
        return storage.size();
    }

    @Override
    protected Resume doGet(String searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected List<Resume> doCopyAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    protected void doUpdate(Resume r, String searchKey) {
        storage.replace(searchKey, r);
    }

    @Override
    protected boolean isExist(String searchKey) {
        return storage.containsKey(searchKey);
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }
}