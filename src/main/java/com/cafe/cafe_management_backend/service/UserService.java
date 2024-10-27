package com.cafe.cafe_management_backend.service;

import com.cafe.cafe_management_backend.wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface UserService {

    public ResponseEntity<String> signUp(Map<String, String> req);
    public ResponseEntity<String> login(Map<String,String> req);
    public ResponseEntity<List<UserWrapper>> getAllUser();
    public ResponseEntity<String> update(Map<String,String> requestMap);
}
