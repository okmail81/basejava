package com.urise.webapp.model;

public enum ContactType {
    PHONE("Телефон"),
    SKYPE("Skype"),
    EMAIL("Электронная почта"),
    LINKENID("Профиль linkedin"),
    STACKOVERFLOW("Профиль Stackoverflow");

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
