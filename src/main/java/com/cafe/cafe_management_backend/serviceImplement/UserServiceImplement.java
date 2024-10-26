package com.cafe.cafe_management_backend.serviceImplement;

import com.cafe.cafe_management_backend.JWT.CustomerUsersDetailsService;
import com.cafe.cafe_management_backend.JWT.JwtFilter;
import com.cafe.cafe_management_backend.JWT.JwtUtil;
import com.cafe.cafe_management_backend.POJO.User;
import com.cafe.cafe_management_backend.constants.CafeConstants;
import com.cafe.cafe_management_backend.dao.UserDao;
import com.cafe.cafe_management_backend.service.UserService;
import com.cafe.cafe_management_backend.utils.CafeUtils;
import com.cafe.cafe_management_backend.wrapper.UserWrapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class UserServiceImplement implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImplement.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomerUsersDetailsService customerUsersDetailsService;

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private JwtFilter jwtFilter;


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

    @Override
    public ResponseEntity<String> login(Map<String, String> req) {
        log.info("Inside login");
        try {
//            Authentication auth =authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(req.get("email"),req.get("password")));
            UserDetails userDetails = customerUsersDetailsService.loadUserByUsername(req.get("email"));

//            if(auth.isAuthenticated()){
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                if ( passwordEncoder.matches(req.get("password"), userDetails.getPassword())){

                if(customerUsersDetailsService.getUserDetail().getStatus().equalsIgnoreCase("true")){
                    return new ResponseEntity<String>("{\"token\":\""+jwtUtil.generateToken(
                            customerUsersDetailsService.getUserDetail().getEmail()
                            ,customerUsersDetailsService.getUserDetail().getRole())+"\"}",HttpStatus.OK);
                }else {
                    return new ResponseEntity<String>("{\"message\":\""+"Wait for admin approval"+"\"}",HttpStatus.BAD_REQUEST);
                }
            }
        } catch (Exception e) {
            log.error("{}",e);
        }

        return new ResponseEntity<String>("{\"message\":\""+"Bad credentials"+"\"}",HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUser() {
        log.info("Inside getAllUser");
        try{
            if(jwtFilter.isAdmin()){
                return new ResponseEntity<>(userDao.getAllUser(),HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(new ArrayList<>(),HttpStatus.UNAUTHORIZED);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
