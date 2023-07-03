package com.example.demo.concert;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Concert {
	private String id;
	private String name; 
	private String poster;
	private String[] styurl;
	private String startDate; 
	private String endDate; 
	private String loc; 
	private String cast;
	private String crew; 
	private String runTime; 
	private String age; 
	private String producer;
	private String price; 
	private String state; 
	private boolean openrun;
}
