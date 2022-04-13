package com.urise.webapp;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.ListStorage;

public class MainTestCollectionStorage {
    static final ListStorage COLLECTION_STORAGE = new ListStorage();

    public static void main(String[] args) {
        final Resume r1 = new Resume();
        final Resume r2 = new Resume();
        final Resume r3 = new Resume();
        final Resume r4 = new Resume();

        COLLECTION_STORAGE.save(r1);
        COLLECTION_STORAGE.save(r2);
        COLLECTION_STORAGE.save(r3);
        COLLECTION_STORAGE.save(r4);

        System.out.println("Get r1: " + COLLECTION_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + COLLECTION_STORAGE.size());

        printAll();
        COLLECTION_STORAGE.delete(r1.getUuid());
        printAll();

        COLLECTION_STORAGE.update(r4);
        printAll();

        COLLECTION_STORAGE.clear();
        printAll();

        System.out.println("Size: " + COLLECTION_STORAGE.size());
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : COLLECTION_STORAGE.getAll()) {
            System.out.println(r);
        }
    }
}