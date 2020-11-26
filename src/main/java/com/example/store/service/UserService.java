package com.example.store.service;

import com.example.store.dao.custom.JwtGenerator;
import com.example.store.dao.entity.User;
import com.example.store.dto.request.UserLoginRequest;
import com.example.store.dto.response.UserLoginResponse;
import com.example.store.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtGenerator jwtGenerator;

    public UserLoginResponse checkLoginUser(UserLoginRequest request){
        User user = userRepository.findByUsernameAndPassword(request.getUsername(), request.getPassword());
        if (user == null) {
            throw new NullPointerException();
        }
        return new UserLoginResponse(user.getName(), jwtGenerator.returnToken(user));
    }
}
