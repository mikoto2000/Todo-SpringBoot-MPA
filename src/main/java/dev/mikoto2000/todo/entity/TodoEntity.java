package dev.mikoto2000.todo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * TodoEntity
 */
@Data
@AllArgsConstructor
public class TodoEntity {
  private long id;
  private String title;
  private boolean done;
}

