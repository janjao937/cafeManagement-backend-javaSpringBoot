package com.cafe.cafe_management_backend.JWT;

import com.cafe.cafe_management_backend.dao.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Slf4j
@Service
public class CustomerUsersDetailsService implements UserDetailsService {

    @Autowired
    UserDao userDao;

    private com.cafe.cafe_management_backend.POJO.User userDetail;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("Inside loadUserByUserName {}",email);
        userDetail = userDao.findByEmailId(email);
        if(!Objects.isNull(userDetail)){
            return new User(userDetail.getName(),userDetail.getPassword(),new ArrayList<>());
        }else {
            throw new UsernameNotFoundException("== User not found ==");
        }
    }

    public com.cafe.cafe_management_backend.POJO.User getUserDetail(){
//        com.cafe.cafe_management_backend.POJO.User user = userDetail;
//        user.setPassword(null);
//
        return userDetail;
    }


}
