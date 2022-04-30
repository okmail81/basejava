package com.urise.webapp.model;

public class StringType extends AbstractSection {

    String information;

    public void setInformation(String personalInformation) {
        this.information = personalInformation;
    }

    @Override
    public String toString() {
        return information;
    }
}
