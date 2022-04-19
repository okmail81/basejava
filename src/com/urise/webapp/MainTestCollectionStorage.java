package com.urise.webapp;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.MapUuidStorage;

public class MainTestCollectionStorage {
    static final MapUuidStorage COLLECTION_STORAGE = new MapUuidStorage();

    public static void main(String[] args) {
        final Resume r1 = new Resume("Кандидат1");
        final Resume r2 = new Resume("Кандидат2");
        final Resume r3 = new Resume("Кандидат3");
        final Resume r4 = new Resume("Кандидат4");

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
        for (Resume r : COLLECTION_STORAGE.getAllSorted()) {
            System.out.println(r + " " + r.getFullName());
        }
    }
}