package com.javalab.movieapp.dao.inf;

import com.javalab.movieapp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Dauren_Altynbekov on 18-Apr-19.
 */
@Repository
public interface UserDao extends JpaRepository<User, Long> {
}
