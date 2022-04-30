package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ExperienceSection extends AbstractSection{
    private final List<Experience> list = new ArrayList<>();

    public void setAchievement(Experience experience) {
        list.add(experience);
    }

    @Override
    public String toString() {
         return list.stream().map(Object::toString).collect(Collectors.joining("\n"));
    }
}
