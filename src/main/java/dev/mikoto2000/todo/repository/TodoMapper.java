package dev.mikoto2000.todo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import dev.mikoto2000.todo.entity.TodoEntity;

/**
 * TodoMapper
 */
@Mapper
public interface TodoMapper {
  @Select("select id,title,done from todo where email = #{email}")
  List<TodoEntity> findByEmail(String email);
}
