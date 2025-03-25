package dev.mikoto2000.todo.controller;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import dev.mikoto2000.todo.configuration.SecurityConfiguration;
import dev.mikoto2000.todo.model.Todo;
import dev.mikoto2000.todo.service.TodoService;

@WebMvcTest(controllers = IndexController.class)
@Import(SecurityConfiguration.class)
public class IndexControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoService todoService;

    @Test
    public void testIndexPage() throws Exception {
        // テストデータの準備
        String email = "test@example.com";
        List<Todo> todos = List.of(
                new Todo(1L, "Todo 1", false),
                new Todo(2L, "Todo 2", true)
        );

        // TodoService のモック設定
        when(todoService.getTodos(eq(email), anyInt(), anyInt())).thenReturn(todos);

        // テスト実行
        mockMvc.perform(get("/")
                .param("page", "0")
                .param("size", "5")
                .with(oidcLogin().idToken(token -> token.claim("email", email))))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("todos"))
                .andExpect(model().attribute("page", 0))
                .andExpect(model().attribute("size", 5));

        // TodoService のメソッド呼び出しを検証
        verify(todoService, times(1)).getTodos(eq(email), eq(0), eq(5));
    }

    @Test
    public void testNegativePageIndex() throws Exception {
        // テストデータの準備
        String email = "test@example.com";
        List<Todo> todos = List.of(new Todo(1L, "Todo 1", false));

        // TodoService のモック設定
        when(todoService.getTodos(eq(email), eq(0), anyInt())).thenReturn(todos);

        // テスト実行 - 負のページ番号
        mockMvc.perform(get("/")
                .param("page", "-1")
                .param("size", "5")
                .with(oidcLogin().idToken(token -> token.claim("email", email))))
                .andExpect(status().isOk())
                .andExpect(model().attribute("page", 0));

        // TodoService のメソッド呼び出しを検証
        verify(todoService, times(1)).getTodos(eq(email), eq(0), eq(5));
    }

    @Test
    public void testAddTodo() throws Exception {
        // テストデータの準備
        String email = "test@example.com";
        String title = "New Todo";

        // テスト実行
        mockMvc.perform(post("/addTodo")
                .param("title", title)
                .param("page", "1")
                .param("size", "10")
                .with(csrf())
                .with(oidcLogin().idToken(token -> token.claim("email", email))))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/?page=1&size=10"));

        // TodoService のメソッド呼び出しを検証
        verify(todoService, times(1)).addTodo(eq(email), eq(title));
    }

    @Test
    public void testDeleteTodo() throws Exception {
        // テストデータの準備
        long todoId = 1L;

        // テスト実行
        mockMvc.perform(post("/deleteTodo")
                .param("id", String.valueOf(todoId))
                .param("page", "1")
                .param("size", "10")
                .with(csrf())
                .with(oidcLogin()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/?page=1&size=10"));

        // TodoService のメソッド呼び出しを検証
        verify(todoService, times(1)).deleteTodo(eq(todoId));
    }

    @Test
    public void testUpdateTodo() throws Exception {
        // テストデータの準備
        long todoId = 1L;
        boolean done = true;

        // テスト実行
        mockMvc.perform(post("/updateTodo")
                .param("id", String.valueOf(todoId))
                .param("done", String.valueOf(done))
                .param("page", "1")
                .param("size", "10")
                .with(csrf())
                .with(oidcLogin()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/?page=1&size=10"));

        // TodoService のメソッド呼び出しを検証
        verify(todoService, times(1)).updateTodo(eq(todoId), eq(done));
    }
}