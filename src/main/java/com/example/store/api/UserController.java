package com.example.store.api;

import com.example.store.dto.request.UserLoginRequest;
import com.example.store.dto.response.UserLoginResponse;
import com.example.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("login")
    public UserLoginResponse login(@Valid @RequestBody UserLoginRequest request){
        return userService.checkLoginUser(request);
    }
}
