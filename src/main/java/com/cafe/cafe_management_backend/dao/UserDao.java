package com.cafe.cafe_management_backend.dao;

import com.cafe.cafe_management_backend.POJO.User;
import com.cafe.cafe_management_backend.wrapper.UserWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserDao extends JpaRepository<User,Integer> {

    User findByEmailId(@Param("email") String email);
    List<UserWrapper> getAllUser();
}
