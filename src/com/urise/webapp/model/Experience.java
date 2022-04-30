package com.urise.webapp.model;

import java.time.YearMonth;

public class Experience {
    private final String company;
    private final YearMonth startDate;
    private final YearMonth endDate;
    private final String position;
    private final String description;

    public Experience(String company, YearMonth startDate, YearMonth endDate, String position, String description) {
        this.company = company;
        this.startDate = startDate;
        this.endDate = endDate;
        this.position = position;
        this.description = description;
    }

    @Override
    public String toString() {
        return "company=" + company + '\n' +
                "startDate=" + startDate +
                " endDate=" + endDate + '\n' +
                (position == null ? "" : "position=" + position + '\n') +
                "description=" + description + '\n';
    }
}
