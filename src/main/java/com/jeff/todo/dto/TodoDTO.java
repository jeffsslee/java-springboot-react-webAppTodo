package com.jeff.todo.dto;

import com.jeff.todo.model.TodoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class TodoDTO {
  private String id;
  private String title;
  private boolean done;

  // To change Entity into DTO
  public TodoDTO(final TodoEntity entity) {
    this.id = entity.getId();
    this.title = entity.getTitle();
    this.done = entity.isDone();
  }

  // To change DTO to Entity
  public static TodoEntity toEntity(final TodoDTO dto){
    return TodoEntity.builder()
        .id(dto.getId())
        .title(dto.getTitle())
        .done(dto.isDone())
        .build();
  }
}
