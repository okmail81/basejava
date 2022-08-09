package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.*;
import com.urise.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM section");
        sqlHelper.execute("DELETE FROM resume");
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.transactionalExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("" +
                            "    SELECT * FROM resume r " +
                            " LEFT JOIN contact c " +
                            "        ON r.uuid = c.resume_uuid " +
                            "     WHERE r.uuid =? ")) {
                        ps.setString(1, uuid);
                        ResultSet rs = ps.executeQuery();
                        if (!rs.next()) {
                            throw new NotExistStorageException(uuid);
                        }
                        Resume r = new Resume(uuid, rs.getString("full_name"));
                        addContacts(rs, r);
                        addResumeSections(conn, r);
                        return r;
                    }
                }
        );
    }

    @Override
    public void update(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                        ps.setString(1, r.getFullName());
                        ps.setString(2, r.getUuid());
                        if (ps.executeUpdate() == 0) {
                            throw new NotExistStorageException(r.getUuid());
                        }
                        deleteContacts(conn, r);
                        deleteSections(conn, r.getUuid());
                        insertContacts(conn, r);
                        insertSections(conn, r);
                        return null;
                    }
                }
        );
    }

    @Override
    public void save(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                        ps.setString(1, r.getUuid());
                        ps.setString(2, r.getFullName());
                        ps.execute();
                    }
                    insertContacts(conn, r);
                    insertSections(conn, r);
                    return null;
                }
        );
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.transactionalExecute(conn -> {
            deleteSections(conn, uuid);
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM resume WHERE uuid=?")) {
                ps.setString(1, uuid);
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(uuid);
                }
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume r ORDER BY full_name,uuid")) {
                ResultSet rs = ps.executeQuery();
                List<Resume> resumes = new ArrayList<>();
                while (rs.next()) {
                    Resume r = new Resume(rs.getString("uuid"), rs.getString("full_name"));
                    addResumeContacts(conn, r);
                    addResumeSections(conn, r);
                    resumes.add(r);
                }
                return resumes;
            }
        });
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT count(*) FROM resume", st -> {
            ResultSet rs = st.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }

    private void insertContacts(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void insertSections(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, Section> e : r.getSections().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue().toString());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void deleteContacts(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM contact WHERE resume_uuid=?")) {
            ps.setString(1, r.getUuid());
            ps.execute();
        }
    }

    private void deleteSections(Connection conn, String uuid) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM section WHERE resume_uuid=?")) {
            ps.setString(1, uuid);
            ps.execute();
        }
    }

    private void addResumeContacts(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM contact WHERE resume_uuid=?")) {
            ps.setString(1, r.getUuid());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                addContacts(rs, r);
            }
        }
    }

    private void addResumeSections(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM section WHERE resume_uuid=?")) {
            ps.setString(1, r.getUuid());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                addSections(rs, r);
            }
        }
    }

    private void addContacts(ResultSet rs, Resume r) throws SQLException {
        do {
            String value = rs.getString("value");
            ContactType type = ContactType.valueOf(rs.getString("type"));
            if (value != null) {
                r.addContact(type, value);
            }
        } while (rs.next());
    }

    private void addSections(ResultSet rs, Resume r) throws SQLException {
        do {
            String value = rs.getString("value");
            SectionType type = SectionType.valueOf(rs.getString("type"));
            if (value != null) {
                switch (type) {
                    case PERSONAL:
                    case OBJECTIVE:
                        r.addSection(type, new TextSection(value));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        List<String> elements = new ArrayList<>();
                        String[] arr = value.split("\n");
                        Collections.addAll(elements, arr);
                        r.addSection(type, new ListSection(elements));
                }
            }
        }
        while (rs.next());
    }
}