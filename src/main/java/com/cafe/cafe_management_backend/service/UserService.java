package com.cafe.cafe_management_backend.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface UserService {

    public ResponseEntity<String> signUp(Map<String, String> req);
    public ResponseEntity<String> login(Map<String,String> req);
}
