package com.urise.webapp;

import com.urise.webapp.model.*;

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
        ArrayLists achievement = new ArrayLists();
        achievement.setAchievement("Организация команды и успешная реализация Java проектов для сторонних заказчиков: приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный Spring Boot + Vaadin проект для комплексных DIY смет");
        achievement.setAchievement("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 3500 выпускников");
        achievement.setAchievement("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk. ");
        resume.setSection(SectionType.ACHIEVEMENT, achievement);

        //Квалификация
        ArrayLists qualifications = new ArrayLists();
        qualifications.setAchievement("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualifications.setAchievement("Version control: Subversion, Git, Mercury, ClearCase, Perforce ");
        qualifications.setAchievement("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, SQLite, MS SQL, HSQLDB");
        resume.setSection(SectionType.QUALIFICATIONS, qualifications);

        //Опыт работы
        ArrayLists experience = new ArrayLists();
        experience.setAchievement("""
                Java Online Projects. 10/2013 - Сейчас
                Автор проекта.
                Создание, организация и проведение Java онлайн проектов и стажировок.
                """);
        experience.setAchievement("""
                Wrike. 10/2014 - 01/2016
                Старший разработчик (backend)
                Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.
                """);
        experience.setAchievement("""
                RIT Center. 04/2012 - 10/2014
                Java архитектор
                Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python
                """);
        resume.setSection(SectionType.EXPERIENCE, experience);

        //Образование
        ArrayLists education = new ArrayLists();
        education.setAchievement("""
                Coursera
                03/2013 - 05/2013
                'Functional Programming Principles in Scala' by Martin Odersky
                """);
        education.setAchievement("""
                Luxoft
                03/2011 - 04/2011
                Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.'
                """);
        education.setAchievement("""
                Siemens AG
                01/2005 - 04/2005
                3 месяца обучения мобильным IN сетям (Берлин)
                """);
        resume.setSection(SectionType.EDUCATION, education);

        for (ContactType type : ContactType.values()) {
            String contact = resume.getContact(type);
            if (contact != null) {
                System.out.println(type.getTitle() + ": " + contact);
            }
        }

        for (SectionType type : SectionType.values()) {
            Sections section = resume.getSection(type);
            if (section != null) {
                System.out.println("\n" + type.getTitle() + ":");
                section.printAll();
            }
        }
    }
}
