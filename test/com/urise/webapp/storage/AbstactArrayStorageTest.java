package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class AbstactArrayStorageTest extends AbstractStorageTest {
    private final Storage storage;

    public AbstactArrayStorageTest(Storage storage) {
        super(storage);
        this.storage = storage;
    }

    @Test
    public void saveOverflow() {
        assertThrows(StorageException.class, () -> {
            for (int i = 4; i <= AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume("user1"));
            }
            storage.save(new Resume("user1"));
        });
    }
}