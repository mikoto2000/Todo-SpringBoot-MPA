package dev.mikoto2000.todo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.mikoto2000.todo.entity.TodoEntity;
import dev.mikoto2000.todo.model.Todo;
import dev.mikoto2000.todo.repository.TodoMapper;

@ExtendWith(MockitoExtension.class)
public class TodoServiceTest {

    @Mock
    private TodoMapper todoMapper;

    @InjectMocks
    private TodoService todoService;

    private String testEmail;
    private List<TodoEntity> testTodoEntities;

    @BeforeEach
    public void setup() {
        testEmail = "test@example.com";
        
        // テスト用データを作成
        testTodoEntities = new ArrayList<>();
        testTodoEntities.add(new TodoEntity(1L, "Task 1", false));
        testTodoEntities.add(new TodoEntity(2L, "Task 2", true));
        testTodoEntities.add(new TodoEntity(3L, "Task 3", false));
    }

    @Test
    public void testGetTodos() {
        // モックの設定
        when(todoMapper.findByEmail(eq(testEmail), eq(0), eq(5)))
                .thenReturn(testTodoEntities);

        // サービス実行
        List<Todo> todos = todoService.getTodos(testEmail, 0, 5);

        // 検証
        assertEquals(3, todos.size());
        assertEquals(1L, todos.get(0).getId());
        assertEquals("Task 1", todos.get(0).getTitle());
        assertEquals(false, todos.get(0).isDone());
        
        assertEquals(2L, todos.get(1).getId());
        assertEquals("Task 2", todos.get(1).getTitle());
        assertEquals(true, todos.get(1).isDone());
        
        // TodoMapperのメソッドが正しく呼ばれたことを検証
        verify(todoMapper, times(1)).findByEmail(testEmail, 0, 5);
    }

    @Test
    public void testGetTodosWithPagination() {
        // ページネーションのテスト
        when(todoMapper.findByEmail(eq(testEmail), eq(10), eq(5)))
                .thenReturn(testTodoEntities.subList(0, 1));

        // 2ページ目（ページサイズ5）
        List<Todo> todos = todoService.getTodos(testEmail, 2, 5);

        // 検証
        assertEquals(1, todos.size());
        assertEquals(1L, todos.get(0).getId());
        
        // TodoMapperのメソッドが正しいオフセットで呼ばれたことを検証
        verify(todoMapper, times(1)).findByEmail(testEmail, 10, 5);
    }

    @Test
    public void testAddTodo() {
        String title = "New Task";
        
        // サービス実行
        todoService.addTodo(testEmail, title);
        
        // TodoMapperのinsertメソッドが正しく呼ばれたことを検証
        verify(todoMapper, times(1)).insert(testEmail, title);
    }

    @Test
    public void testDeleteTodo() {
        long todoId = 1L;
        
        // サービス実行
        todoService.deleteTodo(todoId);
        
        // TodoMapperのdeleteメソッドが正しく呼ばれたことを検証
        verify(todoMapper, times(1)).delete(todoId);
    }

    @Test
    public void testUpdateTodo() {
        long todoId = 2L;
        boolean done = true;
        
        // サービス実行
        todoService.updateTodo(todoId, done);
        
        // TodoMapperのupdateメソッドが正しく呼ばれたことを検証
        verify(todoMapper, times(1)).update(todoId, done);
    }
}