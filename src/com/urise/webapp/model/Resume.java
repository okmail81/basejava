package com.urise.webapp.model;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;

    private final String fullName;

    private final Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
    private final Map<SectionType, AbstractSection> sections = new EnumMap<>(SectionType.class);

    public Resume(String fullName) {
        this("", fullName);
    }

    public Resume(String uuid, String fullName) {
        if (uuid.isEmpty()) {
            uuid = UUID.randomUUID().toString();
        }
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public AbstractSection getSection(SectionType type) {
        return sections.get(type);
    }

    public String getContact(ContactType type) {
        return contacts.get(type);
    }

    public void setContactInformation(ContactType contactType, String contact) {
        contacts.put(contactType, contact);
    }

    public void setSection(SectionType sectionType, AbstractSection section) {
        sections.put(sectionType, section);
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        if (!uuid.equals(resume.uuid)) return false;
        if (!fullName.equals(resume.fullName)) return false;
        if (!contacts.equals(resume.contacts)) return false;
        return sections.equals(resume.sections);
    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + fullName.hashCode();
        result = 31 * result + contacts.hashCode();
        result = 31 * result + sections.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return uuid + '(' + fullName + ')';
    }

    @Override
    public int compareTo(Resume o) {
        int compareFullName = fullName.compareTo(o.fullName);
        return compareFullName != 0 ? compareFullName : uuid.compareTo(o.uuid);
    }
}