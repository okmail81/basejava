package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class ArrayLists extends Sections{
    private List list = new ArrayList<>();

    public void setAchievement(String achievement) {
        list.add(achievement);
    }

    @Override
    public String toString() {
       return list.toString();
    }

    @Override
    public void printAll() {
        for (Object o : list) {
            String element = (String) o;
            System.out.println("-" + element);
        }
    }
}
