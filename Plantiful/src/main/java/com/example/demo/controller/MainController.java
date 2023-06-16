package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.chat.service.ChatServiceMain;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {
	private final ChatServiceMain chatServiceMain;
	
	/**
	  * 채팅 리스트 화면 "/" 로 요청이 들어오면 전체 채팅룸 리스트를 담아서 return
	 */
	@GetMapping("/chat")
	public String goChatRoom(Model model, HttpSession session) {
		model.addAttribute("list", chatServiceMain.findAllRoom());
		String loginId = (String)session.getAttribute("loginId");
		if(loginId != null) {
			model.addAttribute("user", loginId);
		}
		return "roomlist.html";
	}
}
