package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {

    private final List<Resume> storage = new ArrayList<>();

    @Override
    protected void doSave(Resume r, Integer searchKey) {
        storage.add(r);
    }

    @Override
    protected void doDelete(Integer searchKey) {
        int resumeIndex = searchKey;
        storage.remove(resumeIndex);
    }

    public void clear() {
        storage.clear();
    }

    public int size() {
        return storage.size();
    }

    @Override
    protected Resume doGet(Integer searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected List<Resume> doCopyAll() {
        return storage;
    }

    @Override
    protected void doUpdate(Resume resume, Integer searchKey) {
        storage.set(searchKey, resume);
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return searchKey != null;
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }
}