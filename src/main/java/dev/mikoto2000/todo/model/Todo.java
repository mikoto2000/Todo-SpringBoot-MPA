package dev.mikoto2000.todo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * TodoDto
 */
@Data
@AllArgsConstructor
public class Todo {
  private long id;
  private String title;
  private boolean done;
}
