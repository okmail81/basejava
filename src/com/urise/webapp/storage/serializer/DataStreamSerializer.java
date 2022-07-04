package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {
    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            writeWithExeption(contacts.entrySet(), dos, new Consumer<Map.Entry<ContactType, String>>() {
                @Override
                public void accept(Map.Entry<ContactType, String> entry) throws IOException {
                    dos.writeUTF(entry.getKey().name());
                    dos.writeUTF(entry.getValue());
                }
            });
            Map<SectionType, Section> sections = r.getSections();
            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
                Section section = entry.getValue();
                SectionType entryKey = entry.getKey();
                switch (entryKey) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(section.toString());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writeAchievementQualification((ListSection) section, dos);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        writeExperienceOrganization((OrganizationSection) section, dos);
                        break;
                }
            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            resume.addSection(SectionType.PERSONAL, new TextSection(dis.readUTF()));
            resume.addSection(SectionType.OBJECTIVE, new TextSection(dis.readUTF()));
            readAchivementQualification(dis, resume, SectionType.ACHIEVEMENT);
            readAchivementQualification(dis, resume, SectionType.QUALIFICATIONS);

            readExperienceOrganization(dis, resume, SectionType.EXPERIENCE);

            readExperienceOrganization(dis, resume, SectionType.EDUCATION);
            return resume;
        }
    }

    @FunctionalInterface
    interface Consumer<T> {
        void accept(T t) throws IOException;
    }

    private <T> void writeWithExeption(Collection<T> collection, DataOutputStream dos, Consumer<T> action) throws IOException {
        dos.writeInt(collection.size());
        for (T t : collection) {
            action.accept(t);
        }
    }

    private void writeAchievementQualification(ListSection section, DataOutputStream dos) throws IOException {
        List<String> achievement = section.getItems();
        writeWithExeption(achievement, dos, new Consumer<>() {
            @Override
            public void accept(String item) throws IOException {
                dos.writeUTF(item);
            }
        });
    }

    private void writeExperienceOrganization(OrganizationSection section, DataOutputStream dos) throws IOException {
        List<Organization> experienses = section.getOrganizations();
        writeWithExeption(experienses, dos, new Consumer<>() {
            @Override
            public void accept(Organization item) throws IOException {
                dos.writeUTF(item.getHomePage().getName());
                dos.writeUTF(item.getHomePage().getUrl());
                List<Organization.Position> positions = item.getPositions();
                writeWithExeption(positions, dos, new Consumer<>() {
                    @Override
                    public void accept(Organization.Position position) throws IOException {
                        writeDate(dos, position.getStartDate());
                        writeDate(dos, position.getEndDate());
                        dos.writeUTF(position.getTitle());
                        dos.writeUTF(position.getDescription());
                    }
                });
            }
        });
    }

    private void writeDate(DataOutputStream dos, LocalDate position) throws IOException {
        dos.writeInt(position.getYear());
        dos.writeInt(position.getMonth().getValue());
    }

    private void readAchivementQualification(DataInputStream dis, Resume resume, SectionType achievement) throws IOException {
        int achievementSize = dis.readInt();
        List<String> achievementList = new ArrayList<>();
        for (int i = 0; i < achievementSize; i++) {
            achievementList.add(dis.readUTF());
        }
        resume.addSection(achievement, new ListSection(achievementList));
    }

    private void readExperienceOrganization(DataInputStream dis, Resume resume, SectionType experience) throws IOException {
        int experienseSize = dis.readInt();
        List<Organization> organizationList = new ArrayList<>();
        for (int i = 0; i < experienseSize; i++) {
            Link homePage = new Link(dis.readUTF(), dis.readUTF());
            int positionSize = dis.readInt();
            List<Organization.Position> positionsList = new ArrayList<>();
            for (int j = 0; j < positionSize; j++) {
                positionsList.add(new Organization.Position(dis.readInt(), Month.of(dis.readInt()), dis.readInt(), Month.of(dis.readInt()), dis.readUTF(), dis.readUTF()));
            }
            organizationList.add(new Organization(homePage, positionsList));
        }
        resume.addSection(experience, new OrganizationSection(organizationList));
    }
}