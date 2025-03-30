package dev.mikoto2000.todo.advice.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

  /**
   * バリデーションエラー処理
   */
  @ExceptionHandler(ConstraintViolationException.class)
  public String handleConstraintViolationException(
      ConstraintViolationException e, RedirectAttributes redirectAttributes) {

    log.debug("バリデーションエラー発生: {}", e.getMessage());

    // エラーメッセージを取得
    String errorMessage = e.getConstraintViolations().stream()
        .map(violation -> violation.getMessage())
        .findFirst()
        .orElse("入力内容に誤りがあります");

    // リダイレクト先にエラーメッセージを渡す
    redirectAttributes.addFlashAttribute("validationError", errorMessage);

    return "redirect:/";
  }
}
