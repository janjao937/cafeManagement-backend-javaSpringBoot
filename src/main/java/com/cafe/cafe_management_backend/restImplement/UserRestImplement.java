package com.cafe.cafe_management_backend.restImplement;

import com.cafe.cafe_management_backend.constants.CafeConstants;
import com.cafe.cafe_management_backend.rest.UserRest;
import com.cafe.cafe_management_backend.service.UserService;
import com.cafe.cafe_management_backend.utils.CafeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserRestImplement implements UserRest {

    @Autowired
    UserService userService;

    @Override
    public ResponseEntity<String> singup(Map<String, String> requestMap) {
        try {
            return userService.signUp(requestMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @Override
    public ResponseEntity<String> login(Map<String, String> requsetMap) {
        try {
            return userService.login(requsetMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
