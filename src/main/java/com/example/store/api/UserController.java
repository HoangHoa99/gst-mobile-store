package com.example.store.api;

import com.example.store.dto.request.UserLoginRequest;
import com.example.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(userService.checkLoginUser(request));
    }
}
