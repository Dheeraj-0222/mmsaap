package com.mmsaap.controller;


import com.mmsaap.entity.User;
import com.mmsaap.payload.LoginDto;
import com.mmsaap.repository.UserRepository;
import com.mmsaap.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")

public class AuthController {
    private UserRepository userRepository;
    private UserService userService;

    public AuthController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }
    @PostMapping("/sign-up")
    public ResponseEntity<?>createUser(
            @RequestBody User user
    ){
        Optional<User> opUsername = userRepository.findByUsername(user.getUsername());
        if(opUsername.isPresent()){
            return new ResponseEntity("Username already exists", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Optional<User> opEmail = userRepository.findByEmail(user.getEmail());
        if(opEmail.isPresent()){
            return new ResponseEntity("Email already exists", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Optional<User> opMobile = userRepository.findByMobile(user.getMobile());
        if(opMobile.isPresent()){
            return new ResponseEntity("Mobile already exists", HttpStatus.INTERNAL_SERVER_ERROR);
        }


        user.setPassword(BCrypt.hashpw(user.getPassword(),BCrypt.gensalt(10))); // password encryption using BCrypt
        User savedUser = userRepository.save(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public String login(@RequestBody LoginDto loginDto){
        boolean val = userService.verifyLogin(loginDto);
        if(val){
            return "Login Successful";
        }
        return "bhkkk yaha se";
    }

}
