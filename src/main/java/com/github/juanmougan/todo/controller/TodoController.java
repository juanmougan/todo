package com.github.juanmougan.todo.controller;

import com.github.juanmougan.todo.model.Status;
import com.github.juanmougan.todo.model.Todo;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodoController {

  @GetMapping(Endpoints.TODOS_LIST)
  public List<Todo> getAll() {
    return List.of(
        Todo.builder()
            .createdAt(LocalDateTime.now())
            .status(Status.CREATED)
            .title("Practice Java")
            .build(),
        Todo.builder()
            .createdAt(LocalDateTime.now().minusDays(1))
            .status(Status.DONE)
            .title("Go to the supermarket")
            .build()
    );
  }

}
