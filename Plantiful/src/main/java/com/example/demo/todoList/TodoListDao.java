package com.example.demo.todoList;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoListDao extends JpaRepository<TodoList, String>{

}
