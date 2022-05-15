package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.urise.webapp.ResumeTestData.fillResume;
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
        storage.save(fillResume(UUID_1, UUID_1));
        storage.save(fillResume(UUID_2, UUID_2));
        storage.save(fillResume(UUID_3, UUID_3));
    }

    @Test
    void save() {
        Resume newResume = fillResume(UUID_4, UUID_4);
        storage.save(newResume);
        assertEquals(newResume, storage.get(UUID_4));
        assertEquals(4, storage.size());
    }

    @Test
    void saveExist() {
        Resume newResume = fillResume(UUID_3, UUID_3);
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
        assertEquals(fillResume(UUID_1, UUID_1), r);
    }

    @Test()
    public void getNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.get("dummy"));
    }

    @Test
    void getAllSorted() {
        List<Resume> expectedList = new ArrayList<>();
        expectedList.add(fillResume(UUID_1, UUID_1));
        expectedList.add(fillResume(UUID_2, UUID_2));
        expectedList.add(fillResume(UUID_3, UUID_3));
        assertEquals(expectedList, storage.getAllSorted());
    }

    @Test
    void update() {
        Resume r = fillResume(UUID_1, UUID_1);
        storage.update(r);
        assertSame(r, storage.get(UUID_1));
    }

    @Test
    void updateNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.get(UUID_4));
    }
}