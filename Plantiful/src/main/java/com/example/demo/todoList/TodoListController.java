package com.example.demo.todoList;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/todolist")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class TodoListController {
	
	private final TodoListService service;
	
	@GetMapping("/{email}")
	public Map getText(@PathVariable String email) {
		Map map = new HashMap();
		TodoListDto dto = service.getTodoList(email);
		map.put("dto", dto);
		return map;
	}
	
	@PostMapping("")
	public Map saveText(TodoListDto dto) {
		Map map = new HashMap();
		TodoListDto savedDto = service.saveTodoList(dto);
		map.put("dto", savedDto);
		return map;
	}
}
