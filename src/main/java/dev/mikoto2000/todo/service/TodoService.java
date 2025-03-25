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
  public List<Todo> getTodos(String email, int page, int size) {

    // ユーザーの Todo を取得
    var todos = todoMapper.findByEmail(email, page * size, size);

    // Todo に変換して返却
    return todos.stream()
        .map(e -> new Todo(e.getId(), e.getTitle(), e.isDone()))
        .toList();
  }

  /**
   * addTodo
   */
  public void addTodo(String email, String title) {
    // Todo を追加
    todoMapper.insert(email, title);
  }

  /**
   * deleteTodo
   */
  public void deleteTodo(long id) {
    // Todo を削除
    todoMapper.delete(id);
  }

  /**
   * updateTodo
   */
  public void updateTodo(long id, boolean done) {
    // Todo を更新
    todoMapper.update(id, done);
  }
}
