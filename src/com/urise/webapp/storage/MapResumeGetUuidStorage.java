package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeGetUuidStorage extends AbstractStorage {

    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected void saveResume(Object resume, Resume r) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected void deleteStorage(Object resume) {
        storage.remove(((Resume) resume).getUuid());
    }

    public void clear() {
        storage.clear();
    }

    public int size() {
        return storage.size();
    }

    @Override
    protected Resume getResume(Object resume) {
        return storage.get(((Resume) resume).getUuid());
    }

    @Override
    protected List<Resume> getList() {
        return new ArrayList<>(storage.values());
    }

    @Override
    protected void updateResume(Object resume, Resume r) {
        storage.replace(((Resume) resume).getUuid(), r);
    }

    @Override
    protected boolean isSearchKeyExist(Object searchKey) {
        return searchKey != null;
    }

    @Override
    protected Object findSearchKey(String uuid) {
        return storage.get(uuid);
    }
}