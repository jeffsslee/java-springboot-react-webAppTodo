package com.jeff.todo.service;

import com.jeff.todo.model.TodoEntity;
import com.jeff.todo.persistance.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TodoService {

  private final TodoRepository todoRepository;

  public String testService(){
    TodoEntity todoEntity = TodoEntity.builder().title("My First Todo List").build();
    todoRepository.save(todoEntity);
    TodoEntity savedTodoEntity = todoRepository.findById(todoEntity.getId()).get();
    return savedTodoEntity.getTitle();
  }

  public List<TodoEntity> create(final TodoEntity todoEntity){
    validateEntity(todoEntity);

    todoRepository.save(todoEntity);
    log.info("Entity is saved : Id - {}", todoEntity.getId());

    return todoRepository.findByUserId(todoEntity.getUserId());
  }

  public List<TodoEntity> retrieve(final String userId){
    return todoRepository.findByUserId(userId);
  }

  public List<TodoEntity> update(final TodoEntity todoEntity){
    validateEntity(todoEntity);

    Optional<TodoEntity> optionalTodoEntity = todoRepository.findById(todoEntity.getId());
    optionalTodoEntity.ifPresent(e ->
    {
      e.setTitle(todoEntity.getTitle());
      e.setDone(todoEntity.isDone());

      todoRepository.save(e);
    });

    return todoRepository.findByUserId(todoEntity.getUserId());
  }

  public List<TodoEntity> delete(final TodoEntity todoEntity){
    validateEntity(todoEntity);

    try {
      todoRepository.delete(todoEntity);
    } catch (Exception e) {
      log.error("Error occurs while deleting the entity : ", todoEntity.getId(), e.getMessage());
      throw new RuntimeException("Error occurs while deleting the entity : " +  todoEntity.getId());
    }

    return todoRepository.findByUserId(todoEntity.getUserId());
  }

  private static void validateEntity(final TodoEntity todoEntity) {
    if(todoEntity == null){
      log.warn("Entity cannot be null!");
      // Exception... To protect abnormal programing stream; To continue normal programing operation!
      throw new RuntimeException("Entity cannot be null!");
    }

    if(todoEntity.getUserId() == null){
      log.warn("Unknown user!");
      throw new RuntimeException("Unknown user!");
    }
  }
}
