package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ObjectStreamPathStorage extends AbstractPathStorage{
    protected ObjectStreamPathStorage(File directory, StorageSerilization storageSerilization) {
        super(directory.getAbsolutePath(), storageSerilization);
    }

    @Override
    protected void doWrite(Resume r, OutputStream os) throws IOException {
        storageSerilization.doWrite(r, os);
    }

    @Override
    protected Resume doRead(InputStream is) throws IOException {
        return storageSerilization.doRead(is);
    }
}
