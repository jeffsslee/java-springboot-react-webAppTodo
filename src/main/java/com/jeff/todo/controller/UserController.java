package com.jeff.todo.controller;

import com.jeff.todo.dto.ResponseDTO;
import com.jeff.todo.dto.UserDTO;
import com.jeff.todo.model.UserEntity;
import com.jeff.todo.security.TokenProvider;
import com.jeff.todo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/auth")
public class UserController {

  private final UserService userService;
  private final TokenProvider tokenProvider;
  private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
    try {
      if (userDTO == null || userDTO.getPassword() == null) {
        throw new RuntimeException("Invalid password value");
      }

      UserEntity userEntity = UserEntity.builder()
          .username(userDTO.getUsername())
          .password(passwordEncoder.encode(userDTO.getPassword()))
          .build();
      UserEntity registeredUserEntity = userService.create(userEntity);
      // To prepare UserDTO for reply through ResponseDTO & ResponseEntity
      UserDTO userDTOResponse = UserDTO.builder()
          .id(registeredUserEntity.getId())
          .username(registeredUserEntity.getUsername())
          .build();

      return ResponseEntity.ok().body(userDTOResponse);
    } catch (RuntimeException e) {
      ResponseDTO<Object> responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
      return ResponseEntity.badRequest().body(responseDTO);
    }
  }

  @PostMapping("/signin")
  public ResponseEntity<?> authenticate(@RequestBody UserDTO userDTO) {

    UserEntity userEntity = userService.getByCredentials(userDTO.getUsername(), userDTO.getPassword(), passwordEncoder);
    log.info("userEntity : {}", userEntity);
    if (userEntity != null) {
      final String token = tokenProvider.create(userEntity);
      UserDTO userDTOresponse = UserDTO.builder()
          .username(userEntity.getUsername())
          .id(userEntity.getId())
          .token(token)
          .build();

      return ResponseEntity.ok().body(userDTOresponse);
    } else {
      ResponseDTO<Object> responseDTO = ResponseDTO.builder()
          .error("Login Failure!")
          .build();
      return ResponseEntity.badRequest().body(responseDTO);
    }
  }
}