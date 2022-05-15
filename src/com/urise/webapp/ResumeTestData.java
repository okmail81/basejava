package com.urise.webapp;

import com.urise.webapp.model.*;
import com.urise.webapp.util.DateUtil;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class ResumeTestData {
    public static void main(String[] args) {

        Resume resume = fillResume("", "Григорий Кислин");

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

    public static Resume fillResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);

        //контакты
        resume.addContact(ContactType.PHONE, "+7(921) 855-0482");
        resume.addContact(ContactType.SKYPE, "grigory.kislin");
        resume.addContact(ContactType.EMAIL, "gkislin@yandex.ru");

        //Личные качества
        TextSection personal = new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");
        resume.addSection(SectionType.PERSONAL, personal);

        //Позиция
        TextSection objective = new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        resume.addSection(SectionType.OBJECTIVE, objective);

        //Достижения
        List<String> achievements = new ArrayList<>();

        achievements.add("Организация команды и успешная реализация Java проектов для сторонних заказчиков: приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный Spring Boot + Vaadin проект для комплексных DIY смет");
        achievements.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 3500 выпускников");
        achievements.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk. ");
        BulletedListSection achievement = new BulletedListSection(achievements);
        resume.addSection(SectionType.ACHIEVEMENT, achievement);

        //Квалификация
        List<String> qualifications = new ArrayList<>();

        qualifications.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualifications.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce ");
        qualifications.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, SQLite, MS SQL, HSQLDB");
        BulletedListSection qualification = new BulletedListSection(qualifications);
        resume.addSection(SectionType.QUALIFICATIONS, qualification);

        //Опыт работы
        List<Organization> organizations = new ArrayList<>();

        List<Experience> experienceList = new ArrayList<>();
        Experience experience = new Experience(DateUtil.of(2013, Month.of(10)), DateUtil.of(2022, Month.of(5)), "Автор проекта", "Создание, организация и проведение Java онлайн проектов и стажировок.");
        experienceList.add(experience);
        Organization organization = new Organization("Java Online Projects", "http://javaops.ru/", experienceList);
        organizations.add(organization);

        experienceList = new ArrayList<>();
        experience = new Experience(DateUtil.of(2014, Month.of(10)), DateUtil.of(2016, Month.of(1)), "Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");
        experienceList.add(experience);
        organization = new Organization("Wrike", "https://www.wrike.com/", experienceList);
        organizations.add(organization);

        OrganizationSection organizationSection = new OrganizationSection(organizations);
        resume.addSection(SectionType.EXPERIENCE, organizationSection);

        //Образование
        List<Organization> educations = new ArrayList<>();

        experienceList = new ArrayList<>();
        experience = new Experience(DateUtil.of(2013, Month.of(3)), DateUtil.of(2013, Month.of(5)), "Functional Programming Principles in Scala' by Martin Odersky");
        experienceList.add(experience);
        Organization education = new Organization("Coursera", "https://www.coursera.org/course/progfun", experienceList);
        educations.add(education);

        experienceList = new ArrayList<>();
        experience = new Experience(DateUtil.of(2011, Month.of(3)), DateUtil.of(2011, Month.of(4)), "Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.'");
        experienceList.add(experience);
        education = new Organization("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366", experienceList);
        educations.add(education);

        experienceList = new ArrayList<>();
        experience = new Experience(DateUtil.of(1993, Month.of(9)), DateUtil.of(1996, Month.of(7)), "Аспирантура (программист С, С++)");
        experienceList.add(experience);
        experience = new Experience(DateUtil.of(1987, Month.of(9)), DateUtil.of(1993, Month.of(7)), "Инженер (программист Fortran, C)");
        experienceList.add(experience);
        education = new Organization("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", "http://www.ifmo.ru/", experienceList);
        educations.add(education);

        OrganizationSection educationSection = new OrganizationSection(educations);
        resume.addSection(SectionType.EDUCATION, educationSection);

        return resume;
    }
}