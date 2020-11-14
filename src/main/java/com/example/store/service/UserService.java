package com.example.store.service;

import com.example.store.dao.custom.CustomUserDetail;
import com.example.store.dao.custom.JwtGenerator;
import com.example.store.dao.entity.User;
import com.example.store.dto.request.UserLoginRequest;
import com.example.store.dto.response.UserLoginResponse;
import com.example.store.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtGenerator jwtGenerator;

    public UserLoginResponse checkLoginUser(UserLoginRequest request){
        User user = userRepository.findByUsernameAndPassword(request.getUsername(), request.getPassword());
        if (user == null) {
            throw new UsernameNotFoundException(request.getUsername());
        }
        return new UserLoginResponse(user.getName(), jwtGenerator.returnToken(user));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new CustomUserDetail(user);
    }
}
