package dev.mikoto2000.todo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import dev.mikoto2000.todo.model.Todo;
import dev.mikoto2000.todo.repository.TodoMapper;
import lombok.RequiredArgsConstructor;

/**
 * TodoService
 */
@RequiredArgsConstructor
@Service
public class TodoService {

  /**
  * TodoMapper
  */
  private final TodoMapper todoMapper;

  /**
   * getTodos
   */
  public List<Todo> getTodos(String email) {

    // ユーザーの Todo を取得
    var todos = todoMapper.findByEmail(email);

    // Todo に変換して返却
    return todos.stream()
        .map(e -> new Todo(e.getId(), e.getTitle(), e.isDone()))
        .toList();
  }
}
