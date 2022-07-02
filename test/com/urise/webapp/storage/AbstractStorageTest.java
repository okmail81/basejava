package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static com.urise.webapp.ResumeTestData.fillResume;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class AbstractStorageTest {
    protected final static File STORAGE_DIR = new File("D:\\basejava\\storage");
    protected Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    private static final Resume R1;
    private static final Resume R2;
    private static final Resume R3;
    private static final Resume R4;


    static {
        R1 = fillResume(UUID_1, "Name1");
        R2 = fillResume(UUID_2, "Name2");
        R3 = fillResume(UUID_3, "Name3");
        R4 = fillResume(UUID_4, "Name4");
    }

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    void setUp() {
        storage.clear();
        storage.save(R1);
        storage.save(R2);
        storage.save(R3);
    }

    @Test
    void save() {
        storage.save(R4);
        assertSize(4);
        assertGet(R4);
    }

    @Test
    void saveExist() {
        Resume newResume = fillResume(UUID_3, "Name3");
        assertThrows(ExistStorageException.class, () -> storage.save(newResume));
    }

    @Test
    void delete() {
        storage.delete(UUID_3);
        assertSize(2);
        assertThrows(NotExistStorageException.class, () -> storage.get(UUID_3));
    }

    @Test
    void deleteNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.delete(UUID_4));
    }

    @Test
    void clear() {
        storage.clear();
        assertSize(0);
    }

    @Test
    void size() {
        assertSize(3);
    }

    @Test
    void get() {
        assertGet(R1);
        assertGet(R2);
        assertGet(R3);
    }

    @Test()
    public void getNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.get("dummy"));
    }

    @Test
    void getAllSorted() {
        List<Resume> list = storage.getAllSorted();
        assertEquals(3, list.size());
        assertEquals(list, Arrays.asList(R1, R2, R3));
    }

    @Test
    void update() {
        Resume newResume = fillResume(UUID_1, "New Name");
        storage.update(newResume);
        assertEquals(newResume, storage.get(UUID_1));
    }

    @Test
    void updateNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.get(UUID_4));
    }

    private void assertGet(Resume r) {
        assertEquals(r, storage.get(r.getUuid()));
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }
}