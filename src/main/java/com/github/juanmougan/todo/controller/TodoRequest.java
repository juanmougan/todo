package com.github.juanmougan.todo.controller;

import com.github.juanmougan.todo.model.Status;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TodoRequest {

  private String title;
  private Status status;
}
