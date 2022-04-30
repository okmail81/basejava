package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class BulletedListSection extends AbstractSection {
    private final List<String> list = new ArrayList<>();

    public void setAchievement(String achievement) {
        list.add(achievement);
    }

    @Override
    public String toString() {
        return String.join("\n", list);
    }
}
