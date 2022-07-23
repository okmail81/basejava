package com.urise.webapp.sql;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

public class SqlHelper {
    public final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @FunctionalInterface
    public interface queryProcessor<T> {
        T process(PreparedStatement preparedStatement) throws SQLException;
    }

    public <T> T process(String sql, queryProcessor<T> processor, String exception, String uuid) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            return processor.process(ps);
        } catch (SQLException e) {
            if (Objects.equals(exception, "ExistStorageException")) {
                throw new ExistStorageException(uuid);
            } else {
                throw new StorageException(e);
            }
        }
    }
}