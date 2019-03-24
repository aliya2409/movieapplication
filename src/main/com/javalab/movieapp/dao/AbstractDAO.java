package com.javalab.movieapp.dao;

import com.javalab.movieapp.entities.BaseEntity;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public interface AbstractDAO<K, T extends BaseEntity> {

    List<T> findAll(long languageId) throws SQLException, IOException;

    T findEntityById(K id, long languageId) throws SQLException, IOException;

    boolean delete(K id) throws SQLException;

    boolean create(T entity) throws SQLException;

    boolean update(T entity)throws SQLException;

    default boolean delete(long id, String sqlQuery) throws SQLException {
        Connection cn = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement st = cn.prepareStatement(sqlQuery)) {
            st.setLong(1, id);
            st.executeUpdate();
        } finally {
            ConnectionPool.getInstance().releaseConnection(cn);
        }
        return true;
    }

    default boolean executeUpdateWithTwoId(long firstId, long secondId, String sqlQuery) throws SQLException {
        Connection cn =  ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement st = cn.prepareStatement(sqlQuery)) {
            st.setLong(1, firstId);
            st.setLong(2, secondId);
            st.executeUpdate();
        } finally {
            ConnectionPool.getInstance().releaseConnection(cn);
        }
        return true;
    }
}
