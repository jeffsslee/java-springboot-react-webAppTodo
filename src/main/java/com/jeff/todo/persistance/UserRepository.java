package com.jeff.todo.persistance;

import com.jeff.todo.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

  UserEntity findByUsername(String username);
  Boolean existsByUsername(String username);
  UserEntity findByUsernameAndPassword(String username, String password);
}
