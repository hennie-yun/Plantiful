package com.example.demo.todoList;

import org.springframework.stereotype.Service;

import com.example.demo.member.Member;
import com.example.demo.member.MemberDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TodoListService {
	
	private final TodoListDao dao;
	private final MemberDao memDao;

	public TodoListDto getTodoList(String email) {
		TodoList todo = dao.findById(email).orElse(null);
		if(todo != null) {
			TodoListDto dto = new TodoListDto(todo.getEmail(), todo.getMember(), todo.getText());
			return dto;
		} else {
			TodoListDto dto = new TodoListDto(email, new Member(email, "", "", "", 0, ""), "");
			return dto;
		}
	}
	
	public TodoListDto saveTodoList(TodoListDto dto) {
		Member member = memDao.findById(dto.getEmail()).orElse(null);
		if(member != null) dto.setMember(member);
		else member = new Member(dto.getEmail(), "", "", "", 0, "");
		TodoList todo = new TodoList(dto.getEmail(), dto.getMember(), dto.getText());
		System.out.println("todo : "+todo.getText());
		TodoList savedTodo = dao.save(todo);
		System.out.println("savedTodo : "+savedTodo.getText());
		TodoListDto newDto = new TodoListDto(savedTodo.getEmail(), savedTodo.getMember(), savedTodo.getText());
		return newDto;
	}
}
