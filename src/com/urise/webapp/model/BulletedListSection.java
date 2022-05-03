package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class BulletedListSection extends AbstractSection {
    private final List<String> items;

    public BulletedListSection(List<String> items) {
        Objects.requireNonNull(items, "items must not be null");
        this.items = items;
    }

    public List<String> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return String.join("\n", items);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BulletedListSection that = (BulletedListSection) o;

        return items.equals(that.items);
    }

    @Override
    public int hashCode() {
        return items.hashCode();
    }
}
