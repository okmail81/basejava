package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage<Resume> {

    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected void saveResume(Resume resume, Resume r) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected void deleteStorage(Resume resume) {
        storage.remove(resume.getUuid());
    }

    public void clear() {
        storage.clear();
    }

    public int size() {
        return storage.size();
    }

    @Override
    protected Resume getResume(Resume resume) {
        return resume;
    }

    @Override
    protected List<Resume> getList() {
        return new ArrayList<>(storage.values());
    }

    @Override
    protected void updateResume(Resume resume, Resume r) {
        storage.replace(resume.getUuid(), r);
    }

    @Override
    protected boolean isSearchKeyExist(Resume searchKey) {
        return searchKey != null;
    }

    @Override
    protected Resume findSearchKey(String uuid) {
        return storage.get(uuid);
    }
}