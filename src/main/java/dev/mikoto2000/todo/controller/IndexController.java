package dev.mikoto2000.todo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dev.mikoto2000.todo.dto.TodoDto;
import dev.mikoto2000.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Controller
@Slf4j
public class IndexController {

  /**
   * TodoService
   */
  private final TodoService todoService;

  @GetMapping("/")
  public String index(
      @AuthenticationPrincipal OidcUser user,
      Model model) {

    // Todo の取得
    var todos = todoService.getTodos(user.getEmail());

    // TodoDto に変換
    var todoDtos = todos.stream()
        .map(e -> new TodoDto(e.getId(), e.getTitle(), e.isDone()))
        .toList();

    // モデルに attribute をセット
    model.addAttribute("user", user);
    model.addAttribute("todos", todoDtos);

    return "index";
  }

  @PostMapping("/addTodo")
  public String add(
      @AuthenticationPrincipal OidcUser user,
      @RequestParam String title) {

    // Todo の追加
    todoService.addTodo(user.getEmail(), title);

    return "redirect:/";
  }

  @PostMapping("/deleteTodo")
  public String delete(@RequestParam long id) {

    // Todo の削除
    todoService.deleteTodo(id);

    return "redirect:/";
  }
}
