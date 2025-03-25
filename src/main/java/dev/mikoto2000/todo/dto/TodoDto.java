package dev.mikoto2000.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * TodoDto
 */
@Data
@AllArgsConstructor
public class TodoDto {
  private long id;
  private String title;
  private boolean done;
}
