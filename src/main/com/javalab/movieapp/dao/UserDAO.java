package com.javalab.movieapp.dao;

import com.javalab.movieapp.entities.User;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements AbstractDAO<Long, User> {

    public static final String USER_ID_COLUMN = "user_id";
    public static final String USER_LOGIN_COLUMN = "user_login";
    public static final String USER_BIRTH_DATE_COLUMN = "user_birth_date";
    public static final String USER_MAIL_COLUMN = "user_mail";
    public static final String USER_PASSWORD_COLUMN = "user_password";
    public static final String USER_ROLE_ID_COLUMN = "user_role_id";

    public static final String FIND_ALL_USERS_SQL_QUERY = "SELECT user_id, user_login, user_password, user_mail, user_birth_date, user_role_id FROM user;";
    public static final String FIND_USER_BY_ID_SQL_QUERY = "SELECT user_id, user_login, user_password, user_mail, user_birth_date, user_role_id FROM user WHERE user_id = ?;";
    public static final String DELETE_USER_BY_ID_SQL_QUERY = "DELETE FROM user WHERE user_id = ?;";
    public static final String ADD_USER_SQL_QUERY = "INSERT INTO user (user_login, user_password, user_mail, user_birth_date, user_role_id) VALUES (?, ?, ?, ?, ?);";
    public static final String UPDATE_USER_SQL_QUERY = "UPDATE user SET user_login = ?, user_password = ?, user_mail = ?, user_birth_date = ?, user_role_id = ? WHERE user_id = ?;";
    public static final String FIND_USER_BY_EMAIL_AND_PASSWORD_SQL_QUERY = "SELECT user_id, user_login, user_password, user_mail, user_birth_date, user_role_id FROM  user WHERE user_mail = ? AND user_password = ?;";
    public static final String CHANGE_PASSWORD_SQL_QUERY = "UPDATE user SET user_password = ? WHERE user_id = ?;";
    public static final String CHANGE_USER_INFO_SQL_QUERY = "UPDATE user SET user_login = ?, user_birth_date = ? WHERE user_id = ?;";
    public static final String FIND_USER_BY_EMAIL_SQL_QUERY = "SELECT user_id, user_login, user_password, user_mail, user_birth_date, user_role_id FROM user WHERE user_mail = ?;" ;

    private final ConnectionPool CONNECTION_POOL = ConnectionPool.getInstance();


    public List<User> findAll() throws SQLException {
        List<User> users = new ArrayList<>();
        Connection cn = CONNECTION_POOL.takeConnection();
        try (Statement st = cn.createStatement()) {
            try (ResultSet resultSet =
                         st.executeQuery(FIND_ALL_USERS_SQL_QUERY)) {
                while (resultSet.next()) {
                    User user = new User();
                    setUserCharacteristics(user, resultSet);
                    users.add(user);
                }
            }
        } finally {
            CONNECTION_POOL.releaseConnection(cn);
        }
        return users;

    }

    public User findUser(String email, String password) throws SQLException {
        User user = new User();
        Connection cn =  ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement st = cn.prepareStatement(FIND_USER_BY_EMAIL_AND_PASSWORD_SQL_QUERY)) {
            st.setString(1, email);
            st.setString(2, password);
            try (ResultSet resultSet =
                         st.executeQuery()) {
                while (resultSet.next()) {
                   setUserCharacteristics(user, resultSet);
                }
            }
        } finally {
            CONNECTION_POOL.releaseConnection(cn);
        }
        return user;
    }

    private void setUserCharacteristics(User user, ResultSet resultSet) throws SQLException {
        user.setId(resultSet.getLong(USER_ID_COLUMN));
        user.setLogin(resultSet.getString(USER_LOGIN_COLUMN));
        user.setBirthDate((resultSet.getDate(USER_BIRTH_DATE_COLUMN).toLocalDate()));
        user.setEmail(resultSet.getString(USER_MAIL_COLUMN));
        user.setPassword(resultSet.getString(USER_PASSWORD_COLUMN));
        user.setRoleId(resultSet.getInt(USER_ROLE_ID_COLUMN));
    }

    public User findEntityById(Long id) throws SQLException {
        User user = new User();
        Connection cn = CONNECTION_POOL.takeConnection();
        try (PreparedStatement st = cn.prepareStatement(FIND_USER_BY_ID_SQL_QUERY)) {
            st.setLong(1, id);
            try (ResultSet resultSet =
                         st.executeQuery()) {
                while (resultSet.next()) {
                    setUserCharacteristics(user, resultSet);
                }
            }
        } finally {
            CONNECTION_POOL.releaseConnection(cn);
        }
        return user;
    }

    public User findUserByEmail(String email) throws SQLException {
        User user = new User();
        Connection cn = CONNECTION_POOL.takeConnection();
        try (PreparedStatement st = cn.prepareStatement(FIND_USER_BY_EMAIL_SQL_QUERY)) {
            st.setString(1, email);
            try (ResultSet resultSet =
                         st.executeQuery()) {
                while (resultSet.next()) {
                    setUserCharacteristics(user, resultSet);
                }
            }
        } finally {
            CONNECTION_POOL.releaseConnection(cn);
        }
        return user;
    }

    public boolean changePassword(Long id, String password) throws SQLException {
        Connection cn = CONNECTION_POOL.takeConnection();
        try (PreparedStatement st = cn.prepareStatement(CHANGE_PASSWORD_SQL_QUERY)) {
            st.setString(1, password);
            st.setLong(2, id);
            st.executeUpdate();
        } finally {
            CONNECTION_POOL.releaseConnection(cn);
        }
        return true;
    }

    public boolean changeUserInfo(Long id, String login, LocalDate birthDate) throws SQLException {
        Connection cn = CONNECTION_POOL.takeConnection();
        try (PreparedStatement st = cn.prepareStatement(CHANGE_USER_INFO_SQL_QUERY)) {
            st.setString(1, login);
            st.setDate(2, Date.valueOf(birthDate));
            st.setLong(3, id);
            st.executeUpdate();
        } finally {
            CONNECTION_POOL.releaseConnection(cn);
        }
        return true;
    }

    public static java.sql.Date asDate(LocalDate localDate) {
        return (Date) Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    @Override
    public boolean delete(Long id) throws SQLException {
        boolean isDeleted = delete(id, DELETE_USER_BY_ID_SQL_QUERY);
        return isDeleted;
    }

    @Override
    public boolean create(User entity) throws SQLException {
        Connection cn = CONNECTION_POOL.takeConnection();
        try (PreparedStatement st = cn.prepareStatement(ADD_USER_SQL_QUERY)) {
            setUserCharacteristics(entity, st);
            st.executeUpdate();
        } finally {
            CONNECTION_POOL.releaseConnection(cn);
        }
        return true;
    }

    @Override
    public boolean update(User entity) throws SQLException {
        Connection cn = CONNECTION_POOL.takeConnection();
        try (PreparedStatement st = cn.prepareStatement(UPDATE_USER_SQL_QUERY)) {
            setUserCharacteristics(entity, st);
            st.setLong(6, entity.getId());
            st.executeUpdate();
        } finally {
            CONNECTION_POOL.releaseConnection(cn);
        }
        return true;
    }

    private void setUserCharacteristics(User entity, PreparedStatement st) throws SQLException {
        st.setString(1, entity.getLogin());
        st.setString(2, entity.getPassword());
        st.setString(3, entity.getEmail());
        st.setDate(4, Date.valueOf(entity.getBirthDate()));
        st.setInt(5, entity.getRoleId());
    }

    @Override
    public List<User> findAll(long languageId) {
        return null;
    }

    @Override
    public User findEntityById(Long id, long languageId) {
        return null;
    }
}
