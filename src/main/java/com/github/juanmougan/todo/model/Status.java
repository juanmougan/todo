package com.github.juanmougan.todo.model;

import java.util.Collections;
import java.util.Set;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Status {
  CREATED(Collections.emptySet()) {
    @Override
    public Status transitionTo(Status newStatus) {
      return newStatus;
    }
  }, DONE(Set.of(CREATED)) {
    @Override
    public Status transitionTo(Status newStatus) {
      if (DONE.illegalTransitions.contains(newStatus)) {
        throw new UnsupportedOperationException("Can't transition from a final status");
      }
      return this;
    }
  };

  private final Set<Status> illegalTransitions;

  public abstract Status transitionTo(Status newStatus);
}
