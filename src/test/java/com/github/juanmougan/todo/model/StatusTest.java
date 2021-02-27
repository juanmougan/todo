package com.github.juanmougan.todo.model;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


class StatusTest {

  @Test
  public void givenDone_whenTransitionToCreated_thenThrowException() {
    // GIVEN a final status
    Status done = Status.DONE;
    // WHEN transition THEN an Exception is thrown
    assertThrows(UnsupportedOperationException.class, () -> {
      //noinspection ResultOfMethodCallIgnored
      done.transitionTo(Status.CREATED);
    });
  }

  @Test
  public void givenCreated_whenTransitionToDone_thenTransition() {
    Status created = Status.CREATED;
    Status newStatus = created.transitionTo(Status.DONE);
    Assertions.assertThat(Status.DONE).isEqualTo(newStatus);

  }
}