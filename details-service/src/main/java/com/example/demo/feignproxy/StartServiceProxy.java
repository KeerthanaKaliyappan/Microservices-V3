package com.example.demo.feignproxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.StartMessage;

/**
 * Removed url param from FeignClient to load balance between different server
 * port, Eureka Naming server uses Spring Cloud Loadbalancer
 */
@FeignClient(name = "start-service")
public interface StartServiceProxy {

	@GetMapping("/start-service")
	public StartMessage getStartMessage();
}
