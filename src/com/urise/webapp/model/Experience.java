package com.urise.webapp.model;

import java.time.LocalDate;
import java.util.Objects;

public class Experience {
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String title;
    private final String description;

    public Experience(LocalDate startDate, LocalDate endDate, String title) {
        this(startDate, endDate, title, null);
    }

    public Experience(LocalDate startDate, LocalDate endDate, String title, String description) {
        Objects.requireNonNull(startDate, "startDate must not be null");
        Objects.requireNonNull(endDate, "endDate must not be null");
        Objects.requireNonNull(title, "title must not be null");
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.description = description;
    }

    @Override
    public String toString() {
        return "startDate=" + startDate +
                " endDate=" + endDate +
                " title= " + title +
                (description == null ? "" : "\ndescription= " + description);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Experience experience = (Experience) o;

        if (!startDate.equals(experience.startDate)) return false;
        if (!endDate.equals(experience.endDate)) return false;
        if (!title.equals(experience.title)) return false;
        return description != null ? description.equals(experience.description) : experience.description == null;
    }

    @Override
    public int hashCode() {
        int result = startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}