package com.urise.webapp.storage;

import com.urise.webapp.Config;

class SqlStorageTest extends AbstractStorageTest {

    public SqlStorageTest() {
        super(new SqlStorage(Config.get().getUrl(), Config.get().getUser(), Config.get().getPassword()));
    }
}