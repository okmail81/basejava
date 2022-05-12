package com.urise.webapp.model;

public class Organization {
    private final Link homePage;
    private final Periods periods;

    public Organization(String name, String url, Periods periods) {
        this.homePage = new Link(name, url);
        this.periods = periods;
    }

    @Override
    public String toString() {
        return "\ncompany=" + homePage + '\n' +
                periods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!homePage.equals(that.homePage)) return false;
        return periods.equals(that.periods);
    }

    @Override
    public int hashCode() {
        int result = homePage.hashCode();
        result = 31 * result + periods.hashCode();
        return result;
    }
}