package com.urise.webapp;

import com.urise.webapp.model.SectionType;

public class TestSingleton {

    public static void main(String[] args) {
         for (SectionType type : SectionType.values()) {
            System.out.println(type.getTitle());
        }
    }
}
