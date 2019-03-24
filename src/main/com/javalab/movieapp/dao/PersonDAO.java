package com.javalab.movieapp.dao;

import com.javalab.movieapp.entities.Person;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonDAO implements AbstractDAO<Long, Person> {

    public static final String PERSON_NAME_ORIGINAL_COLUMN = "person_name_original";
    public static final String PERSON_SURNAME_ORIGINAL_COLUMN = "person_surname_original";
    public static final String PERSON_BIRTH_DATE_COLUMN = "person_birth_date";
    public static final String PERSON_IMAGE_COLUMN = "person_image";
    public static final String PERSON_ID_COLUMN = "person_id";
    public static final String PERSON_NAME_TRANSLATED_COLUMN = "person_name_translated";
    public static final String PERSON_SURNAME_TRANSLATED_COLUMN = "person_surname_translated";
    public static final String PERSON_ROLE_ID_COLUMN = "person_role_id";

    public static final String FIND_ALL_PEOPLE_SQL_QUERY = "SELECT p.person_id, p.person_birth_date, p.person_surname_original, p.person_name_original, p.person_image, (SELECT pi.person_surname_translated FROM person_info pi WHERE pi.person_id = p.person_id AND pi.language_id = ?) AS person_surname_translated, (SELECT pi.person_name_translated FROM person_info pi WHERE pi.person_id = p.person_id AND pi.language_id = ?) AS person_name_translated FROM person p;";
    public static final String FIND_PERSON_BY_ID_SQL_QUERY = "SELECT  p.person_id, p.person_birth_date, p.person_surname_original, p.person_name_original, p.person_image, pi.person_surname_translated, pi.person_name_translated FROM person p JOIN person_info pi USING (person_id) WHERE person_id = ? AND pi.language_id = ?;";
    public static final String DELETE_PERSON_BY_ID_SQL_QUERY = "DELETE FROM person WHERE person_id = ?;";
    public static final String ADD_PERSON_SQL_QUERY = "INSERT INTO person(person_name_original, person_surname_original, person_birth_date, person_image) VALUES (?, ?, ?, ?);";
    public static final String UPDATE_PERSON_SQL_QUERY = "UPDATE person SET person_name_original = ?, person_surname_original = ?, person_birth_date = ?, person_image = ? WHERE person_id = ?;";
    public static final String FIND_MOVIE_CREW_SQL_QUERY = "SELECT p.person_id, p.person_birth_date, p.person_surname_original, p.person_name_original, p.person_image, pi.person_surname_translated, pi.person_name_translated, mp.person_role_id FROM person p JOIN person_info pi USING (person_id) JOIN movie_person mp USING (person_id) WHERE movie_id = ? AND pi.language_id = ? ORDER BY mp.person_role_id;";
    public static final String ADD_PERSON_TO_MOVIE_CREW_SQL_QUERY = "INSERT INTO movie_person (movie_id, person_id, person_role_id) VALUES (?, (SELECT p.person_id FROM person p WHERE p.person_name_original = ? AND p.person_surname_original = ?), ?);";
    public static final String DELETE_PERSON_FROM_MOVIE_CREW_SQL_QUERY = "DELETE FROM movie_person WHERE movie_id = ? AND person_id = ?;";
    public static final String ADD_PERSON_INFO_SQL_QUERY = "INSERT INTO person_info (person_id, language_id, person_name_translated,  person_surname_translated) VALUES ((SELECT p.person_id FROM person p WHERE p.person_name_original = ? AND p.person_surname_original = ?), ?, ?, ?);";
    public static final String UPDATE_PERSON_INFO_SQL_QUERY = "UPDATE person_info SET person_name_translated =?, person_surname_translated = ? WHERE person_id = (SELECT p.person_id FROM person p WHERE p.person_name_original = ? AND p.person_surname_original = ?) AND language_id = ?;";

    private final ConnectionPool CONNECTION_POOL = ConnectionPool.getInstance();


    public List<Person> findMovieCrew(Long movieId, Long languageId) throws SQLException, IOException {
        List<Person> movieCrew = new ArrayList<>();
        Connection cn = CONNECTION_POOL.takeConnection();
        try (PreparedStatement st = cn.prepareStatement(FIND_MOVIE_CREW_SQL_QUERY)) {
            st.setLong(1, movieId);
            st.setLong(2, languageId);
            try (ResultSet resultSet = st.executeQuery()) {
                while (resultSet.next()) {
                    Person person = new Person();
                    setPersonParameters(person, resultSet);
                    person.setRoleId(resultSet.getInt(PERSON_ROLE_ID_COLUMN));
                    movieCrew.add(person);
                }
            }
        } finally {
            CONNECTION_POOL.releaseConnection(cn);
        }
        return movieCrew;
    }

    public boolean addPersonToMovieCrew(long movieId, Person person) throws SQLException {
        Connection cn = CONNECTION_POOL.takeConnection();
        try (PreparedStatement st = cn.prepareStatement(ADD_PERSON_TO_MOVIE_CREW_SQL_QUERY)) {
            st.setLong(1, movieId);
            st.setString(2, person.getOriginalName());
            st.setString(3, person.getOriginalSurname());
            st.setInt(4, person.getRoleId());
            st.executeUpdate();
        }
        return true;
    }

    public boolean deletePersonFromMovieCrew(long movieId, long personId) throws SQLException {
        Connection cn = CONNECTION_POOL.takeConnection();
        try (PreparedStatement st = cn.prepareStatement(DELETE_PERSON_FROM_MOVIE_CREW_SQL_QUERY)) {
            st.setLong(1, movieId);
            st.setLong(2, personId);
            st.executeUpdate();
        }
        return true;
    }

    public boolean addPersonInfo(Person person, long languageId) throws SQLException {
        Connection cn = CONNECTION_POOL.takeConnection();
        try (PreparedStatement st = cn.prepareStatement(ADD_PERSON_INFO_SQL_QUERY)) {
            st.setString(1, person.getOriginalName());
            st.setString(2, person.getOriginalSurname());
            st.setLong(3, languageId);
            st.setString(4, person.getTranslatedName());
            st.setString(5, person.getTranslatedSurname());
            st.executeUpdate();
        } finally {
            CONNECTION_POOL.releaseConnection(cn);
        }
        return true;
    }

    public boolean updatePersonInfo(Person person, long languageId) throws SQLException {
        Connection cn = CONNECTION_POOL.takeConnection();
        try (PreparedStatement st = cn.prepareStatement(UPDATE_PERSON_INFO_SQL_QUERY)) {
            st.setString(1, person.getTranslatedName());
            st.setString(2, person.getTranslatedSurname());
            st.setString(3, person.getOriginalName());
            st.setString(4, person.getOriginalSurname());
            st.setLong(5, languageId);
            st.executeUpdate();
        } finally {
            CONNECTION_POOL.releaseConnection(cn);
        }
        return true;
    }

    @Override
    public List<Person> findAll(long languageId) throws SQLException, IOException {
        List<Person> people = new ArrayList<>();
        Connection cn = CONNECTION_POOL.takeConnection();
        try (PreparedStatement st = cn.prepareStatement(FIND_ALL_PEOPLE_SQL_QUERY)) {
            st.setLong(1, languageId);
            st.setLong(2, languageId);
            try (ResultSet resultSet =
                         st.executeQuery()) {
                while (resultSet.next()) {
                    Person person = new Person();
                    setPersonParameters(person, resultSet);
                    people.add(person);
                }
            }
        } finally {
            CONNECTION_POOL.releaseConnection(cn);
        }
        return people;
    }

    private void setPersonParameters(Person person, ResultSet resultSet) throws SQLException, IOException {
        person.setId(resultSet.getLong(PERSON_ID_COLUMN));
        person.setOriginalName(resultSet.getString(PERSON_NAME_ORIGINAL_COLUMN));
        person.setOriginalSurname(resultSet.getString(PERSON_SURNAME_ORIGINAL_COLUMN));
        person.setBirthDate((resultSet.getDate(PERSON_BIRTH_DATE_COLUMN)).toLocalDate());
        person.setImage(resultSet.getBinaryStream(PERSON_IMAGE_COLUMN));
        byte[] imageByteArray = IOUtils.toByteArray(person.getImage());
        person.setImageBase64(new Base64().encodeToString(imageByteArray));
        person.setTranslatedName(resultSet.getString(PERSON_NAME_TRANSLATED_COLUMN));
        person.setTranslatedSurname(resultSet.getString(PERSON_SURNAME_TRANSLATED_COLUMN));
    }

    @Override
    public Person findEntityById(Long personId, long languageId) throws SQLException, IOException {
        Person person = new Person();
        Connection cn = CONNECTION_POOL.takeConnection();
        try (PreparedStatement st = cn.prepareStatement(FIND_PERSON_BY_ID_SQL_QUERY)) {
            st.setLong(1, personId);
            st.setLong(2, languageId);
            try (ResultSet resultSet =
                         st.executeQuery()) {
                while (resultSet.next()) {
                    setPersonParameters(person, resultSet);
                }
            }
        } finally {
            CONNECTION_POOL.releaseConnection(cn);
        }
        return person;
    }

    @Override
    public boolean delete(Long id) throws SQLException {
        boolean isDeleted = delete(id, DELETE_PERSON_BY_ID_SQL_QUERY);
        return isDeleted;
    }

    @Override
    public boolean create(Person entity) throws SQLException {
        Connection cn = CONNECTION_POOL.takeConnection();
        try (PreparedStatement st = cn.prepareStatement(ADD_PERSON_SQL_QUERY)) {
            setPersonParameters(entity, st);
            st.executeUpdate();
        } finally {
            CONNECTION_POOL.releaseConnection(cn);
        }
        return true;
    }

    @Override
    public boolean update(Person entity) throws SQLException {
        Connection cn = CONNECTION_POOL.takeConnection();
        try (PreparedStatement st = cn.prepareStatement(UPDATE_PERSON_SQL_QUERY)) {
            setPersonParameters(entity, st);
            st.setLong(5, entity.getId());
            st.executeUpdate();
        } finally {
            CONNECTION_POOL.releaseConnection(cn);
        }
        return true;
    }

    private void setPersonParameters(Person entity, PreparedStatement st) throws SQLException {
        st.setString(1, entity.getOriginalName());
        st.setString(2, entity.getOriginalSurname());
        st.setDate(3, Date.valueOf(entity.getBirthDate()));
        st.setBinaryStream(4, entity.getImage());
    }
}
