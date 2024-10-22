package com.cafe.cafe_management_backend.serviceImplement;

import com.cafe.cafe_management_backend.POJO.User;
import com.cafe.cafe_management_backend.constants.CafeConstants;
import com.cafe.cafe_management_backend.dao.UserDao;
import com.cafe.cafe_management_backend.service.UserService;
import com.cafe.cafe_management_backend.utils.CafeUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class UserServiceImplement implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImplement.class);

    @Autowired
    private UserDao userDao;


    @Override
    public ResponseEntity<String> signUp(Map<String, String> reqMap) {
        try{
            log.info("Inside signup {}",reqMap);
            if(validateSignUpMap((reqMap))){
                User user = userDao.findByEmailId(reqMap.get("email"));
                if(Objects.isNull(user)){
                    userDao.save(getUserFromMap(reqMap));
                    return CafeUtils.getResponseEntity("Successfully registered",HttpStatus.OK);
                }
                else {
                    return CafeUtils.getResponseEntity("Email already exits",HttpStatus.BAD_REQUEST);
                }
            }
            else {
                return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return  CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.BAD_REQUEST);

    }

    private boolean validateSignUpMap(Map<String,String> reqMap){
         if(reqMap.containsKey("name") && reqMap.containsKey("contactNumber") &&
                reqMap.containsKey("email") && reqMap.containsKey("password")){
             return true;
         }

         return false;
    }

    private User getUserFromMap(Map<String,String> reqMap){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashPassword = passwordEncoder.encode(reqMap.get("password"));
        //passwordEncoder.matches("hashPass","passInDb");//verify Password

        User user = new User();
        user.setName(reqMap.get("name"));
        user.setContactNumber(reqMap.get("contactNumber"));
        user.setEmail(reqMap.get("email"));

        user.setPassword(hashPassword);

        user.setStatus("false");
        user.setRole("user");

        return user;
    }
}
