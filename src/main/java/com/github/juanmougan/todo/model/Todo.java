package com.github.juanmougan.todo.model;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Todo {

  private String title;
  private LocalDateTime createdAt;
  private Status status;
}
