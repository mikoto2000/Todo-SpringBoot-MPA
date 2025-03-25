package dev.mikoto2000.todo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import dev.mikoto2000.todo.model.Todo;

/**
 * TodoService
 */
@Service
public class TodoService {
  /**
   * Constructor
   */
  public TodoService() {
  }

  /**
   * getTodos
   */
  public List<Todo> getTodos() {
    return List.of(
        new Todo(1, "todo1", false),
        new Todo(2, "todo2", true),
        new Todo(3, "todo3", false));
  }
}
