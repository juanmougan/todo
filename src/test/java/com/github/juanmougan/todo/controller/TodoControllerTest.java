package com.github.juanmougan.todo.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.github.juanmougan.todo.model.Status;
import com.github.juanmougan.todo.model.Todo;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
class TodoControllerTest {

  private final List<Todo> expectedTodos = List.of(
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
}
