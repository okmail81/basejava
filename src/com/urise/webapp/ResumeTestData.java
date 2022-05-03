package com.urise.webapp;

import com.urise.webapp.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ResumeTestData {
    public static void main(String[] args) {
        List<String> achievements = new ArrayList<>();
        List<String> qualifications = new ArrayList<>();
        List<Organization> organizations = new ArrayList<>();
        List<Organization> educations = new ArrayList<>();

        Resume resume = new Resume("Григорий Кислин");

        //контакты
        resume.setContactInformation(ContactType.PHONE, "+7(921) 855-0482");
        resume.setContactInformation(ContactType.SKYPE, "grigory.kislin");
        resume.setContactInformation(ContactType.EMAIL, "gkislin@yandex.ru");

        //Личные качества
        TextSection personal = new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");
        resume.setSection(SectionType.PERSONAL, personal);

        //Позиция
        TextSection objective = new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        resume.setSection(SectionType.OBJECTIVE, objective);

        //Достижения
        achievements.add("Организация команды и успешная реализация Java проектов для сторонних заказчиков: приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный Spring Boot + Vaadin проект для комплексных DIY смет");
        achievements.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 3500 выпускников");
        achievements.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk. ");
        BulletedListSection achievement = new BulletedListSection(achievements);
        resume.setSection(SectionType.ACHIEVEMENT, achievement);

        //Квалификация
        qualifications.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualifications.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce ");
        qualifications.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, SQLite, MS SQL, HSQLDB");
        BulletedListSection qualification = new BulletedListSection(qualifications);
        resume.setSection(SectionType.QUALIFICATIONS, qualification);

        //Опыт работы
        String company = "Java Online Projects";
        String url = "http://javaops.ru/";
        LocalDate startDate = LocalDate.of(2013, 10, 1);
        LocalDate endDate = LocalDate.of(2022, 5, 1);
        String position = "Автор проекта";
        String description = "Создание, организация и проведение Java онлайн проектов и стажировок.";
        Organization organization = new Organization(company, url, startDate, endDate, position, description);
        organizations.add(organization);

        company = "Wrike";
        url = "https://www.wrike.com/";
        startDate = LocalDate.of(2014, 10, 1);
        endDate = LocalDate.of(2016, 1, 1);
        position = "Старший разработчик (backend)";
        description = "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.";
        organization = new Organization(company, url, startDate, endDate, position, description);
        organizations.add(organization);

        OrganizationSection organizationSection = new OrganizationSection(organizations);
        resume.setSection(SectionType.EXPERIENCE, organizationSection);

        //Образование
        company = "Coursera";
        url = "https://www.coursera.org/course/progfun";
        startDate = LocalDate.of(2013, 3, 1);
        endDate = LocalDate.of(2013, 5, 1);
        position = "Functional Programming Principles in Scala' by Martin Odersky";
        Organization education = new Organization(company, url, startDate, endDate, position);
        educations.add(education);

        company = "Luxoft";
        url = "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366";
        startDate = LocalDate.of(2011, 3, 1);
        endDate = LocalDate.of(2011, 4, 1);
        position = "Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.'";
        education = new Organization(company, url, startDate, endDate, position);
        educations.add(education);

        OrganizationSection educationSection = new OrganizationSection(educations);
        resume.setSection(SectionType.EDUCATION, educationSection);

        for (ContactType type : ContactType.values()) {
            String contact = resume.getContact(type);
            if (contact != null) {
                System.out.println(type.getTitle() + ": " + contact);
            }
        }

        for (SectionType type : SectionType.values()) {
            AbstractSection section = resume.getSection(type);
            if (section != null) {
                System.out.println("\n" + type.getTitle() + ":");
                System.out.println(section);
            }
        }
    }
}