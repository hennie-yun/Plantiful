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
	private String[] styurls;
	private String startDate; 
	private String endDate; 
	private String cast;
	private String crew; 
	private String runTime; 
	private String age; 
	private String loc; 
	private String producer;
	private String price; 
	private String sty;
	private String genre;
	private String state; 
	private boolean openrun;
	private String locId;
	private String concertTime;

	public Concert(String id, String name, String poster, String startDate, String endDate, String loc, String genre, 
			String state, boolean openrun) {
		this.id = id;
		this.name = name;
		this.poster = poster;
		this.startDate = startDate;
		this.endDate = endDate;
		this.loc = loc;
		this.state = state;
		this.openrun = openrun;
	}
}
