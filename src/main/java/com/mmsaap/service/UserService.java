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
    private  UserRepository userRepository;

    public UserService(UserRepository userRepository){
    this.userRepository = userRepository;
}
public boolean verifyLogin(LoginDto loginDto){
    Optional<User> opUser = userRepository.findByUsername(loginDto.getUsername());
    if(opUser.isPresent()){
        User user = opUser.get();
        if (BCrypt.checkpw(loginDto.getPassword(), user.getPassword())){
            return true;
        }

    }else {
        return false;

    }
    return false;
}



}

