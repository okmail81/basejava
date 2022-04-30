package com.urise.webapp;

import com.urise.webapp.model.*;

import java.time.YearMonth;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = new Resume("Григорий Кислин");

        //контакты
        resume.setContactInformation(ContactType.PHONE, "+7(921) 855-0482");
        resume.setContactInformation(ContactType.SKYPE, "grigory.kislin");
        resume.setContactInformation(ContactType.EMAIL, "gkislin@yandex.ru");

        //Личные качества
        StringType personal = new StringType();
        personal.setInformation("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");
        resume.setSection(SectionType.PERSONAL, personal);

        //Позиция
        StringType objective = new StringType();
        objective.setInformation("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        resume.setSection(SectionType.OBJECTIVE, objective);

        //Достижения
        BulletedListSection achievement = new BulletedListSection();
        achievement.setAchievement("Организация команды и успешная реализация Java проектов для сторонних заказчиков: приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный Spring Boot + Vaadin проект для комплексных DIY смет");
        achievement.setAchievement("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 3500 выпускников");
        achievement.setAchievement("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk. ");
        resume.setSection(SectionType.ACHIEVEMENT, achievement);

        //Квалификация
        BulletedListSection qualifications = new BulletedListSection();
        qualifications.setAchievement("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualifications.setAchievement("Version control: Subversion, Git, Mercury, ClearCase, Perforce ");
        qualifications.setAchievement("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, SQLite, MS SQL, HSQLDB");
        resume.setSection(SectionType.QUALIFICATIONS, qualifications);

        //Опыт работы
        ExperienceSection experienceSection = new ExperienceSection();
        String company = "Java Online Projects";
        YearMonth startDate = YearMonth.of(2013, 10);
        YearMonth endDate = YearMonth.of(2013, 10);
        String position = "Автор проекта";
        String description = "Создание, организация и проведение Java онлайн проектов и стажировок.";
        Experience experience = new Experience(company, startDate, endDate, position, description);
        experienceSection.setAchievement(experience);

        company = "Wrike";
        startDate = YearMonth.of(2014, 10);
        endDate = YearMonth.of(2016, 1);
        position = "Старший разработчик (backend)";
        description = "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.";
        experience = new Experience(company, startDate, endDate, position, description);
        experienceSection.setAchievement(experience);

        resume.setSection(SectionType.EXPERIENCE, experienceSection);

        //Образование
        position = null;

        ExperienceSection educationSection = new ExperienceSection();
        company = "Coursera";
        startDate = YearMonth.of(2013, 3);
        endDate = YearMonth.of(2013, 5);
        description = "Functional Programming Principles in Scala' by Martin Odersky";
        experience = new Experience(company, startDate, endDate, position, description);
        educationSection.setAchievement(experience);

        company = "Java Online Projects";
        startDate = YearMonth.of(2011, 3);
        endDate = YearMonth.of(2011, 4);
        description = "Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.'";
        experience = new Experience(company, startDate, endDate, position, description);
        educationSection.setAchievement(experience);

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
