package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractArrayStorageTest {
    private final Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    void save() {
        Resume newResume = new Resume(UUID_4);
        storage.save(newResume);
        assertEquals(4, storage.size());
    }

    @Test
    void saveExist() {
        Resume newResume = new Resume(UUID_4);
        storage.save(newResume);
        Throwable thrown = assertThrows(ExistStorageException.class, () -> storage.save(newResume));
        assertEquals("Резюме " + UUID_4 + " уже существует", thrown.getMessage());
    }

    @Test
    void delete() {
        storage.delete(UUID_3);
        assertEquals(2, storage.size());
    }

    @Test
    void deleteNotExist() {
        storage.delete(UUID_3);
        Throwable thrown = assertThrows(NotExistStorageException.class, () -> storage.get(UUID_3));
        assertEquals("Резюме " + UUID_3 + " не найдено", thrown.getMessage());
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
        assertEquals(new Resume(UUID_1), r);
    }

    @Test()
    public void getNotExist() {
        String resume = "dummy";
        Throwable thrown = assertThrows(NotExistStorageException.class, () -> storage.get(resume));
        assertEquals("Резюме " + resume + " не найдено", thrown.getMessage());
    }

    @Test
    void getAll() {
        Resume[] newStorage = storage.getAll();
        assertArrayEquals(storage.getAll(), newStorage);
    }

    @Test
    void update() {
        Resume r = new Resume(UUID_1);
        storage.update(r);
        assertSame(r, storage.get(UUID_1));
    }
}