package com.example.demo.entity;

public class StartMessage {

	private String start;
	private String end;
	private String environment;

	// Needed to resolve rest template call exception
	public StartMessage() {

	}

	public StartMessage(String start, String end, String environment) {
		super();
		this.start = start;
		this.end = end;
		this.environment = environment;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

}
