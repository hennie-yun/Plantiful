package com.example.demo.chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSockectConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		// stomp 접속 주소 URL => /ws-stomp
		registry.addEndpoint("/ws-stomp").withSockJS();
	}
	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		// 메세지를 받을 때 메세지를 구독하는 요청 URL 
		registry.enableSimpleBroker("/sub");
		// 메세지를 보낼 때 메세지를 발행하는 요청 ULR
		registry.setApplicationDestinationPrefixes("/pub");
	}
}
