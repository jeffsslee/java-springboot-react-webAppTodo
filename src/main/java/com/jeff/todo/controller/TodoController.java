package com.jeff.todo.controller;

import com.jeff.todo.dto.ResponseDTO;
import com.jeff.todo.dto.TodoDTO;
import com.jeff.todo.model.TodoEntity;
import com.jeff.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/todo")
public class TodoController {

  private final TodoService todoService;

  @GetMapping("/test")
  public ResponseEntity<?> testTodo(){
    String result = todoService.
        testService();
    List<String > list = new ArrayList<>();
    list.add(result);
    ResponseDTO<String> responseDTO = ResponseDTO.<String>builder().data(list).build();
    return ResponseEntity.ok().body(responseDTO);
  }

  @PostMapping
  public ResponseEntity<?> createTodo(@RequestBody TodoDTO todoDTO){

    try {
      String tempUserId = "tempUser";
      TodoEntity todoEntity = TodoDTO.toEntity(todoDTO);
      todoEntity.setId(null);
      todoEntity.setUserId(tempUserId);

      // TodoEntity List
      List<TodoEntity> todoEntityList = todoService.create(todoEntity);
      // TodoDTO List : for returning to presentational layer
//    List<TodoDTO> todoDTOList = todoEntityList.stream().map((e) -> new TodoDTO(e)).toList();
      List<TodoDTO> todoDTOList = todoEntityList.stream().map(TodoDTO::new).toList();
      // To add todoDTOList to ResponseDTO
      ResponseDTO<TodoDTO> responseDTO = ResponseDTO
          .<TodoDTO>builder()
          .data(todoDTOList)
          .build();

      return ResponseEntity.ok().body(responseDTO);
    } catch (Exception e) {
      ResponseDTO<?> responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
      return ResponseEntity.badRequest().body(responseDTO);
    }
  }

  @GetMapping
  public ResponseEntity<?> retrieveTodoList(){
    String tempUserId = "tempUser";
    List<TodoEntity> todoEntityList = todoService.retrieve(tempUserId);
    List<TodoDTO> todoDTOList = todoEntityList.stream().map(TodoDTO::new).toList();
    // To add todoDTOList to ResponseDTO
    ResponseDTO<TodoDTO> responseDTO = ResponseDTO
        .<TodoDTO>builder()
        .data(todoDTOList)
        .build();
    return ResponseEntity.ok().body(responseDTO);
  }

  @PutMapping
  public ResponseEntity<?> updateTodo(@RequestBody TodoDTO todoDTO){
    String tempUserId = "tempUser";

    TodoEntity todoEntity = TodoDTO.toEntity(todoDTO);
    todoEntity.setUserId(tempUserId);

    List<TodoEntity> todoEntityList = todoService.update(todoEntity);
    List<TodoDTO> todoDTOList = todoEntityList.stream().map(TodoDTO::new).toList();

    ResponseDTO<TodoDTO> responseDTO = ResponseDTO.<TodoDTO>builder().data(todoDTOList).build();

    return ResponseEntity.ok().body(responseDTO);
  }

  @DeleteMapping
  public ResponseEntity<?> deleteTodo(@RequestBody TodoDTO todoDTO){
    try {
      String tempUserId = "tempUser";
      TodoEntity todoEntity = TodoDTO.toEntity(todoDTO);
      todoEntity.setUserId(tempUserId);
      List<TodoEntity> todoEntityList = todoService.delete(todoEntity);
      List<TodoDTO> todoDTOList = todoEntityList.stream().map(TodoDTO::new).toList();
      ResponseDTO<TodoDTO> responseDTO = ResponseDTO.<TodoDTO>builder().data(todoDTOList).build();
      return ResponseEntity.ok().body(responseDTO);
    } catch (Exception e) {
      ResponseDTO<?> responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
      return ResponseEntity.badRequest().body(responseDTO);
    }
  }

}
