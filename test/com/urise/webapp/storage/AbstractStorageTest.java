package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractStorageTest {
    protected Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1, UUID_1));
        storage.save(new Resume(UUID_2, UUID_2));
        storage.save(new Resume(UUID_3, UUID_3));
    }

    @Test
    void save() {
        Resume newResume = new Resume(UUID_4, UUID_4);
        storage.save(newResume);
        assertEquals(newResume, storage.get(UUID_4));
        assertEquals(4, storage.size());
    }

    @Test
    void saveExist() {
        Resume newResume = new Resume(UUID_3, UUID_3);
        assertThrows(ExistStorageException.class, () -> storage.save(newResume));
    }

    @Test
    void delete() {
        storage.delete(UUID_3);
        assertEquals(2, storage.size());
        assertThrows(NotExistStorageException.class, () -> storage.get(UUID_3));
    }

    @Test
    void deleteNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.delete(UUID_4));
    }

    @Test
    void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    void size() {
        assertEquals(3, storage.size());
    }

    @Test
    void get() {
        Resume r = storage.get(UUID_1);
        assertEquals(new Resume(UUID_1, UUID_1), r);
    }

    @Test()
    public void getNotExist() {
        String uuid = "dummy";
        assertThrows(NotExistStorageException.class, () -> storage.get(uuid));
    }

    @Test
    void getAllSorted() {
        List<Resume> newResumeList = new ArrayList<>();
        newResumeList.add(new Resume(UUID_1, UUID_1));
        newResumeList.add(new Resume(UUID_2, UUID_2));
        newResumeList.add(new Resume(UUID_3, UUID_3));
        assertEquals(newResumeList, storage.getAllSorted());
    }

    @Test
    void update() {
        Resume r = new Resume(UUID_1, UUID_1);
        storage.update(r);
        assertSame(r, storage.get(UUID_1));
    }

    @Test
    void updateNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.get(UUID_4));
    }
}