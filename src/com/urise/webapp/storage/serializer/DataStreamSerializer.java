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
            writeWithException(contacts.entrySet(), dos, entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });
            Map<SectionType, Section> sections = r.getSections();
            writeWithException(sections.entrySet(), dos, entry -> {
                Section section = entry.getValue();
                SectionType entryKey = entry.getKey();
                dos.writeUTF(entryKey.name());
                switch (entryKey) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(((TextSection) section).getContent());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writeWithException(((ListSection) section).getItems(), dos, dos::writeUTF);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        writeWithException(((OrganizationSection) section).getOrganizations(), dos, item -> {
                            Link homePage = item.getHomePage();
                            if (homePage == null) {
                                dos.writeUTF("");
                                dos.writeUTF("");
                            } else {
                                dos.writeUTF(homePage.getName());
                                dos.writeUTF(homePage.getUrl());
                            }
                            writeWithException(item.getPositions(), dos, position -> {
                                writeDate(dos, position.getStartDate());
                                writeDate(dos, position.getEndDate());
                                dos.writeUTF(position.getTitle());
                                String description = position.getDescription();
                                if (description == null) {
                                    dos.writeUTF("");
                                } else {
                                    dos.writeUTF(position.getDescription());
                                }
                            });
                        });
                        break;
                }
            });
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            readWithException(dis, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            readWithException(dis, () -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        resume.addSection(sectionType, new TextSection(dis.readUTF()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        List<String> achievementList = readList(list -> readWithException(dis, () -> list.add(dis.readUTF())));
                        resume.addSection(sectionType, new ListSection(achievementList));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Organization> organizationList = readList(list -> readWithException(dis, () -> {
                            Link homePage = new Link(dis.readUTF(), dis.readUTF());
                            List<Organization.Position> positionsList = readList(positionList -> readWithException(dis, () -> positionList.add(new Organization.Position(dis.readInt(), Month.of(dis.readInt()), dis.readInt(), Month.of(dis.readInt()), dis.readUTF(), dis.readUTF()))));
                            list.add(new Organization(homePage, positionsList));
                        }));

                        resume.addSection(sectionType, new OrganizationSection(organizationList));
                        break;
                }
            });
            return resume;
        }
    }

    @FunctionalInterface
    interface Consumer<T> {
        void accept(T t) throws IOException;
    }

    private <T> void writeWithException(Collection<T> collection, DataOutputStream dos, Consumer<T> action) throws IOException {
        dos.writeInt(collection.size());
        for (T t : collection) {
            action.accept(t);
        }
    }

    @FunctionalInterface
    interface Reader {
        void read() throws IOException;
    }

    private void readWithException(DataInputStream dis, Reader reader) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            reader.read();
        }
    }

    @FunctionalInterface
    interface ListReader<T> {
        void readList(List<T> list) throws IOException;
    }

    private <T> List readList(ListReader<T> listReader) throws IOException {
        List<T> list = new ArrayList<>();
        listReader.readList(list);
        return list;
    }


    private void writeDate(DataOutputStream dos, LocalDate position) throws IOException {
        dos.writeInt(position.getYear());
        dos.writeInt(position.getMonth().getValue());
    }
}