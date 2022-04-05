package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public abstract class AbstractArrayStorageTest {
    private final Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    @BeforeEach
    void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Test
    void save() {
        Resume newResume = new Resume(UUID_4);
        storage.save(newResume);
        Assertions.assertEquals(newResume, storage.get(UUID_4));
    }

    @Test
    void delete() {
        storage.delete(UUID_3);
        Throwable thrown = Assertions.assertThrows(NotExistStorageException.class, () -> storage.get(UUID_3));
        Assertions.assertEquals("Резюме uuid3 не найдено", thrown.getMessage());
    }

    @Test
    void clear() {
        storage.clear();
        Assertions.assertEquals(0, storage.size());
    }

    @Test
    void size() {
        Assertions.assertEquals(3, storage.size());
    }

    @Test
    void get() {
        Resume r = storage.get(UUID_1);
        Assertions.assertEquals(r, new Resume(UUID_1));
    }

    @Test
    void getAll() {
        Assertions.assertEquals(storage.getAll().length, storage.size());
    }

    @Test
    void update() {
        Resume r = new Resume(UUID_1);
        storage.update(new Resume(UUID_1));
        Assertions.assertEquals(r, storage.get(UUID_1));
    }

    @Test()
    public void getNotExist() {
        Throwable thrown = Assertions.assertThrows(NotExistStorageException.class, () -> storage.get("dummy"));
        Assertions.assertEquals("Резюме dummy не найдено", thrown.getMessage());
    }
}