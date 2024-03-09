package com.jeff.todo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "username")})
public class UserEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;
  //Email or any username
  @Column(nullable = false)
  private String username;
  // password is nullable ? : When SSO, user may do login in other family site, and jump to this site only with id, username (except password).
  private String password;
  private String role;
  private String authProvider;
}
