package com.mmsaap.service;

import com.mmsaap.entity.User;
import com.mmsaap.payload.LoginDto;
import com.mmsaap.repository.UserRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Getter
@Setter
@Service
public class UserService {
    private final UserRepository userRepository;
    private final JWTService jwtService;

    // Constructor to inject both dependencies
    public UserService(UserRepository userRepository, JWTService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }



    public String verifyLogin(LoginDto loginDto){
    Optional<User> opUser = userRepository.findByUsername(loginDto.getUsername());
    if(opUser.isPresent()){
        User user = opUser.get();
        if (BCrypt.checkpw(loginDto.getPassword(), user.getPassword())){
            String token =jwtService.generateToken(user.getUsername());
            return token;
        }

    }else {
        return null;

    }
    return null;
}



}

