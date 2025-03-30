package dev.mikoto2000.todo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.constraints.Size;

import dev.mikoto2000.todo.dto.TodoDto;
import dev.mikoto2000.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Controller
@Slf4j
@Validated
public class IndexController {

  /**
   * TodoService
   */
  private final TodoService todoService;

  @GetMapping("/")
  public String index(
      @AuthenticationPrincipal OidcUser user,
      Model model,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "5") int size) {

    if (page < 0) {
      page = 0;
    }

    // Todo の取得
    var todos = todoService.getTodos(user.getEmail(), page, size);

    // TodoDto に変換
    var todoDtos = todos.stream()
        .map(e -> new TodoDto(e.getId(), e.getTitle(), e.isDone()))
        .toList();

    // モデルに attribute をセット
    model.addAttribute("user", user);
    model.addAttribute("todos", todoDtos);
    model.addAttribute("page", page);
    model.addAttribute("size", size);
    // Set empty validationError attribute if not present
    if (!model.containsAttribute("validationError")) {
      model.addAttribute("validationError", "");
    }

    return "index";
  }

  @PostMapping("/addTodo")
  public String add(
      @AuthenticationPrincipal OidcUser user,
      @RequestParam @Size(max = 100, message="タイトルは100文字以下で入力してください") String title,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "5") int size) {

    // Todo の追加
    todoService.addTodo(user.getEmail(), title);

    return "redirect:/" + "?page=" + page + "&size=" + size;
  }

  @PostMapping("/deleteTodo")
  public String delete(@RequestParam long id,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "5") int size) {

    // Todo の削除
    todoService.deleteTodo(id);

    return "redirect:/" + "?page=" + page + "&size=" + size;
  }

  @PostMapping("/updateTodo")
  public String update(
      @RequestParam long id,
      @RequestParam boolean done,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "5") int size) {

    // Todo の更新
    todoService.updateTodo(id, done);

    return "redirect:/" + "?page=" + page + "&size=" + size;
  }
}
