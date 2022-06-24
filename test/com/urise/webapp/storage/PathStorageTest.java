package com.urise.webapp.storage;

import com.urise.webapp.storage.serialization.ObjectStream;

class PathStorageTest extends AbstractStorageTest {

    public PathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new ObjectStream()));
    }
}