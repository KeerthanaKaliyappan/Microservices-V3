package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.MidMessage;

@RestController
public class MidController {
	
	@Autowired
	private MidMessage message;
	
	@GetMapping("mid-service")
	public MidMessage getStart(){
		return new MidMessage(message.getStart(), message.getEnd());
	}

}
