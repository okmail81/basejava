package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.*;
import com.urise.webapp.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static com.urise.webapp.util.DateUtil.NOW;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        if (request.getParameter("submit") == null) {
            return;
        }
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        if (!isNotEmpty(fullName)) {
            response.sendRedirect("resume");
            return;
        }
        Resume r;
        try {
            r = storage.get(uuid);
            r.setFullName(fullName);
        } catch (NotExistStorageException e) {
            r = new Resume(fullName);
            storage.save(r);
        }
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (isNotEmpty(value)) {
                r.addContact(type, value);
            } else {
                r.getContacts().remove(type);
            }
        }
        for (SectionType type : SectionType.values()) {
            String[] values = request.getParameterValues(type.name());
            if (values.length < 1) {
                r.getSections().remove(type);
            } else {
                switch (type) {
                    case PERSONAL:
                    case OBJECTIVE:
                        if (isNotEmpty(values[0])) {
                            r.addSection(type, new TextSection(values[0]));
                        }
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        if (isNotEmpty(values[0])) {
                            r.addSection(type, new ListSection(values[0].split("\\n")));
                        }
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Organization> organization = new ArrayList<>();
                        String[] urls = request.getParameterValues(type.name() + "homePageNameUrl");
                        for (int i = 0; i < values.length; i++) {
                            if (isNotEmpty(values[i])) {
                                List<Organization.Position> positions = new ArrayList<>();
                                String[] parametersStartDate = request.getParameterValues(type.name() + "startDate" + i);
                                String[] parametersEndDate = request.getParameterValues(type.name() + "endDate" + i);
                                String[] parametersPositionTitle = request.getParameterValues(type.name() + "positionTitle" + i);
                                String[] parametersPositionDescription = request.getParameterValues(type.name() + "positionDescription" + i);
                                for (int j = 0; j < parametersStartDate.length; j++) {
                                    if (isNotEmpty(parametersPositionTitle[j])) {
                                        String parameterDescription = "";
                                        if (type == SectionType.EXPERIENCE) {
                                            parameterDescription = parametersPositionDescription[j];
                                        }
                                        LocalDate endDate = (parametersEndDate[j] == "") ? NOW : getLocalDate(parametersEndDate[j]);
                                        positions.add(new Organization.Position(getLocalDate(parametersStartDate[j]),
                                                endDate,
                                                parametersPositionTitle[j], parameterDescription));
                                    }
                                }
                                organization.add(new Organization(new Link(values[i], urls[i]), positions));
                            }
                        }
                        if (organization.size() != 0) {
                            r.addSection(type, new OrganizationSection(organization));
                        }
                        break;
                }
            }
        }
        storage.update(r);
        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
                r = storage.get(uuid);
                break;
            case "edit":
                r = storage.get(uuid);
                for (SectionType type : SectionType.values()) {
                    Section section = r.getSection(type);
                    switch (type) {
                        case PERSONAL:
                        case OBJECTIVE:
                            if (section == null) {
                                r.addSection(type, new TextSection(""));
                            }
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS:
                            if (section == null) {
                                r.addSection(type, new ListSection(""));
                            }
                            break;
                        case EXPERIENCE:
                        case EDUCATION:
                            OrganizationSection experienceSection = (OrganizationSection) r.getSection(type);
                            List<Organization> organizations = addBlankOrganization(experienceSection);
                            r.addSection(type, new OrganizationSection(organizations));
                            break;
                    }
                }
                break;
            case "add":
                r = new Resume("");
                for (SectionType type : SectionType.values()) {
                    switch (type) {
                        case PERSONAL:
                        case OBJECTIVE:
                            r.addSection(type, new TextSection(""));
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS:
                            r.addSection(type, new ListSection(""));
                            break;
                        case EXPERIENCE:
                        case EDUCATION:
                            r.addSection(type,
                                    new OrganizationSection(
                                            new Organization("", "",
                                                    new Organization.Position(2022, Month.JANUARY, "", ""))));
                            break;
                    }
                }
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }

    private static boolean isNotEmpty(String value) {
        return value != null && value.trim().length() != 0;
    }

    private LocalDate getLocalDate(String date) {
        return LocalDate.parse(date);
    }

    private static List<Organization> addBlankOrganization(OrganizationSection experienceSection) {
        List<Organization> organizations = new ArrayList<>();
        if (experienceSection != null) {
            List<Organization> organizationList = experienceSection.getOrganizations();
            for (Organization organization:organizationList) {
                List<Organization.Position> position = organization.getPositions();
                if (position.isEmpty()) {
                    organizations.add(new Organization(organization.getHomePage().getName(), organization.getHomePage().getUrl(), new Organization.Position(2022, Month.JANUARY, "", "")));
                } else {
                    organizations.add(organization);
                }
            }
        }
        organizations.add(new Organization("", "",
                new Organization.Position(2022, Month.JANUARY, "", "")));
        return organizations;
    }
}