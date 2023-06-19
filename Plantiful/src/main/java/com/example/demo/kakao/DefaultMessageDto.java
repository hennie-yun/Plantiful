package com.example.demo.kakao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DefaultMessageDto {
	private String objType;
	private String text;
	private String webUrl;
	private String mobileUrl;
	private String btnTitle;
	
}
