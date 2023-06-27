package com.example.demo.todoList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoListDao extends JpaRepository<TodoList, String>{

}
