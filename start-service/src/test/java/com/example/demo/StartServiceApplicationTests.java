package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.controller.StartController;
import com.example.demo.model.StartMessage;
import com.mysql.cj.protocol.Message;

@SpringBootTest
class StartServiceApplicationTests {

	@Test
	void contextLoads() {
	}
	
//	@Test
//	void getStartTest() {
//		StartController startController = new StartController();
//		StartMessage message = new StartMessage();
//		message.setStart(message.getStart());
//		message.setEnd(message.getEnd());
//		message.setEnvironment("8000");
//		assertNotNull(startController.getStart());
//	}

}
