package com.github.juanmougan.todo.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.github.juanmougan.todo.model.Status;
import com.github.juanmougan.todo.model.Todo;
import java.time.LocalDateTime;
import java.util.List;
import org.assertj.core.internal.Dates;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
class TodoControllerTest {

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void givenTodos_whenGetAll_thenListAllTodos() throws Exception {
    // WHEN get all
    MvcResult getAllResult = this.mockMvc.perform(get(Endpoints.TODOS_LIST))
        .andExpect(status().isOk())
        .andReturn();
    String responseAsString = getAllResult.getResponse().getContentAsString();
    CollectionType javaType = objectMapper.getTypeFactory()
        .constructCollectionType(List.class, Todo.class);
    List<Todo> todosList = objectMapper.readValue(responseAsString, javaType);

    // THEN all are returned
    assertThat(todosList).extracting(Todo::getTitle)
        .containsExactlyInAnyOrder("Practice Java", "Go to the supermarket");

  }

  @Test
  public void givenTodoRequest_whenSubmit_thenGetTheSavedValue() throws Exception {
    // GIVEN a Todo request
    LocalDateTime requestTime = LocalDateTime.now();
    TodoRequest request = TodoRequest.builder()
        .title("Use TDD ;)")
        .status(Status.CREATED)
        .build();
    // WHEN submit it
    MvcResult postReturn = this.mockMvc.perform(post(Endpoints.TODOS))
        .andExpect(status().isCreated())
        .andReturn();
    String contentAsString = postReturn.getResponse().getContentAsString();
    Todo newTodo = objectMapper.readValue(contentAsString, Todo.class);
    // THEN verify we get it back
    assertThat(newTodo).extracting(Todo::getTitle).isEqualTo("Use TDD ;)");
    assertThat(newTodo).extracting(Todo::getStatus).isEqualTo(Status.CREATED);
  }
}
