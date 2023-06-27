package com.example.demo.naver;

import java.math.BigInteger;
import java.security.SecureRandom;

import org.springframework.stereotype.Service;

@Service
public class NaverService {
	public String generateRandomState() {
		  SecureRandom random = new SecureRandom();
	        String state = new BigInteger(130, random).toString(32);
	        System.out.println(state);
	        return state;
	}
	
	
}
