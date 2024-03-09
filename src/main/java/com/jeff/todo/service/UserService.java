package com.jeff.todo.service;

import com.jeff.todo.model.UserEntity;
import com.jeff.todo.persistance.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

  private final UserRepository userRepository;

  public UserEntity create(final UserEntity userEntity){
    if(userEntity == null || userEntity.getUsername() == null){
      throw new RuntimeException("Invalid Arguments");
    }

    final String username = userEntity.getUsername();
    if(userRepository.existsByUsername(username)){
      log.warn("Username already exists {}", username);
      throw new RuntimeException("Username already exists");
    }
    return userRepository.save(userEntity);
  }

  public UserEntity getByCredentials(final String username, final String password, final PasswordEncoder encoder){

    final UserEntity originalUser = userRepository.findByUsername(username);

    if(originalUser != null && encoder.matches(password, originalUser.getPassword())){
      return originalUser;
    }

//    return userRepository.findByUsernameAndPassword(username, password);
    return null;
  }
}

