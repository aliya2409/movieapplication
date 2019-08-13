CREATE TABLE language (
  language_id number(19) NOT NULL,
  language_name varchar2(50) NOT NULL,
  PRIMARY KEY (language_id),
  CONSTRAINT language_name UNIQUE  (language_name)
);
CREATE TABLE genre (
  genre_id number(19) NOT NULL,
  language_id number(19) NOT NULL,
  genre_name varchar2(50) NOT NULL,
  PRIMARY KEY (genre_id,language_id),
  CONSTRAINT genre_name_UNIQUE UNIQUE  (genre_name),
  CONSTRAINT fk_genre_language_language_id FOREIGN KEY (language_id) REFERENCES language (language_id) ON DELETE CASCADE
);
CREATE SEQUENCE genre_seq START WITH 11 INCREMENT BY 1;
CREATE SEQUENCE language_seq START WITH 9 INCREMENT BY 1;
CREATE INDEX fk_genre_language_idx ON genre (language_id);
CREATE TABLE users (
  user_id number(19) NOT NULL,
  user_login varchar2(50) NOT NULL,
  user_password varchar2(255) NOT NULL,
  user_mail varchar2(50) NOT NULL,
  user_birth_date date NOT NULL,
  user_role_id number(10) NOT NULL,
  PRIMARY KEY (user_id),
  CONSTRAINT user_login UNIQUE  (user_login),
  CONSTRAINT user_mail UNIQUE  (user_mail),
  CONSTRAINT fk_user_user_role FOREIGN KEY (user_role_id) REFERENCES user_role (user_role_id) ON DELETE CASCADE
);
CREATE SEQUENCE user_seq START WITH 12 INCREMENT BY 1;
CREATE INDEX fk_user_user_role_idx ON users (user_role_id);
CREATE TABLE person (
  person_id number(19) NOT NULL,
  person_birth_date date NOT NULL,
  person_surname_original varchar2(50) NOT NULL,
  person_name_original varchar2(50) NOT NULL,
  person_image blob NOT NULL,
  PRIMARY KEY (person_id),
  CONSTRAINT unique_index UNIQUE  (person_name_original,person_surname_original)
);
CREATE SEQUENCE person_seq START WITH 16 INCREMENT BY 1;
CREATE TABLE person_info (
  person_id number(19) NOT NULL,
  language_id number(19) NOT NULL,
  person_surname_translated varchar2(50) NOT NULL,
  person_name_translated varchar2(50) NOT NULL,
  PRIMARY KEY (person_id,language_id),
  CONSTRAINT fk_person_info_language FOREIGN KEY (language_id) REFERENCES language (language_id) ON DELETE CASCADE ,
  CONSTRAINT fk_person_info_person FOREIGN KEY (person_id) REFERENCES person (person_id) ON DELETE CASCADE
);
CREATE INDEX fk_person_info_language_idx ON person_info (language_id);
CREATE TABLE person_role (
  person_role_id number(10) NOT NULL,
  person_role_name varchar2(50) NOT NULL,
  PRIMARY KEY (person_role_id)
);
CREATE TABLE movie (
  movie_id number(19) NOT NULL,
  movie_title_original varchar2(255) NOT NULL,
  movie_duration timestamp(0) NOT NULL,
  movie_budget number(19) NOT NULL,
  movie_release_date date NOT NULL,
  movie_image blob NOT NULL,
  movie_rating number(3,1) DEFAULT NULL,
  PRIMARY KEY (movie_id)
);
CREATE SEQUENCE movie_seq START WITH 6 INCREMENT BY 1;
CREATE TABLE movie_genre (
  movie_id number(19) NOT NULL,
  genre_id number(19) NOT NULL,
  PRIMARY KEY (movie_id,genre_id),
  CONSTRAINT fk_movie_genre_movie FOREIGN KEY (movie_id) REFERENCES movie (movie_id) ON DELETE CASCADE
);
CREATE INDEX fk_movie_genre_movie_idx ON movie_genre (movie_id);
CREATE INDEX fk_movie_genre_genre_idx ON movie_genre (genre_id);
CREATE TABLE movie_info (
  movie_id number(19) NOT NULL,
  language_id number(19) NOT NULL,
  movie_title_translated varchar2(255) NOT NULL,
  movie_description clob NOT NULL,
  PRIMARY KEY (movie_id,language_id),
  CONSTRAINT fk_movie_info_language FOREIGN KEY (language_id) REFERENCES language (language_id) ON DELETE CASCADE ,
  CONSTRAINT fk_movie_info_movie FOREIGN KEY (movie_id) REFERENCES movie (movie_id) ON DELETE CASCADE
);
CREATE INDEX fk_movie_info_movie_idx ON movie_info (movie_id);
CREATE INDEX fk_movie_info_language_idx ON movie_info (language_id);
CREATE TABLE movie_person (
  movie_id number(19) NOT NULL,
  person_id number(19) NOT NULL,
  person_role_id number(10) NOT NULL,
  PRIMARY KEY (movie_id,person_id),
  CONSTRAINT fk_movie_person_movie FOREIGN KEY (movie_id) REFERENCES movie (movie_id) ON DELETE CASCADE ,
  CONSTRAINT fk_movie_person_person FOREIGN KEY (person_id) REFERENCES person (person_id) ON DELETE CASCADE ,
  CONSTRAINT fk_movie_person_role FOREIGN KEY (person_role_id) REFERENCES person_role (person_role_id) ON DELETE CASCADE
);
CREATE INDEX fk_movie_person_movie_idx ON movie_person (movie_id);
CREATE INDEX fk_movie_person_person_idx ON movie_person (person_id);
CREATE INDEX fk_movie_person_role_idx ON movie_person (person_role_id);
CREATE TABLE movie_rate (
  user_id number(19) NOT NULL,
  movie_id number(19) NOT NULL,
  is_liked number(3) DEFAULT '0' NOT NULL,
  movie_rate number(10) DEFAULT NULL,
  PRIMARY KEY (user_id,movie_id),
  CONSTRAINT fk_movie_rate_movie FOREIGN KEY (movie_id) REFERENCES movie (movie_id) ON DELETE CASCADE ,
  CONSTRAINT fk_movie_rate_user FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE
);
CREATE INDEX fk_movie_rates_movie_idx ON movie_rate (movie_id);
CREATE TABLE user_role (
  user_role_id number(10) NOT NULL,
  user_role_name varchar2(50) NOT NULL,
  PRIMARY KEY (user_role_id)
);