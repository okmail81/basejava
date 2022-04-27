package com.urise.webapp.model;

public class StringType extends Sections{

    String information;

    public void setInformation(String personalInformation) {
        this.information = personalInformation;
    }

    @Override
    public void printAll() {
        System.out.println(information);
    }
}
