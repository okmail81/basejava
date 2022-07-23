package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.process("DELETE FROM resume", PreparedStatement::execute, "", "");
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.process("SELECT * FROM resume r WHERE r.uuid =?", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        }, "", "");
    }

    @Override
    public void update(Resume r) {
        sqlHelper.process("UPDATE resume SET uuid = ?, full_name = ? WHERE uuid=?", ps -> {
            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());
            ps.setString(3, r.getUuid());
            ps.execute();
            return null;
        }, "", "");
    }

    @Override
    public void save(Resume r) {
        sqlHelper.process("INSERT INTO resume (uuid, full_name) VALUES (?,?)", ps -> {
            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());
            ps.execute();
            return null;
        }, "ExistStorageException", r.getUuid());
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.process("DELETE FROM resume r WHERE r.uuid=?", ps -> {
            ps.setString(1, uuid);
            int result = ps.executeUpdate();
            if (result == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        }, "", "");
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.process("SELECT * FROM resume", ps -> {
            ps.execute();
            ResultSet rs = ps.executeQuery();
            List<Resume> resumeList = new ArrayList<>();
            while (rs.next()) {
                resumeList.add(new Resume(rs.getString("uuid").trim(), rs.getString("full_name").trim()));
            }
            return resumeList;
        }, "", "");
    }

    @Override
    public int size() {
        return sqlHelper.process("SELECT count(*) FROM resume", ps -> {
            ps.execute();
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("count");
            } else
                return 0;
        }, "", "");
    }
}