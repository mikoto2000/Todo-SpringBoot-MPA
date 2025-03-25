package dev.mikoto2000.todo.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import dev.mikoto2000.todo.dto.TodoDto;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class IndexController {

  @GetMapping("/")
  public String index(
      @AuthenticationPrincipal OidcUser user,
      Model model) {

    model.addAttribute("user", user);
    model.addAttribute("todos", List.of(
          new TodoDto(1, "todo1", false),
          new TodoDto(2, "todo2", true),
          new TodoDto(3, "todo3", false)
          ));

    return "index";
  }
}
