package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.model.StartMessage;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
public class StartController {

	private Logger logger = LoggerFactory.getLogger(StartController.class);

	@Autowired
	private Environment environment;

	@Autowired
	private StartMessage message;

	@GetMapping("start-service")
	public StartMessage getStart() {
		StartMessage startMessage = new StartMessage(message.getStart(), message.getEnd());
		startMessage.setEnvironment(environment.getProperty("local.server.port"));
		return startMessage;
	}

	/**
	 * Retry annotation returns fallback response after the configured no. of API
	 * call attempts failed
	 * 
	 * Note: Return type of @Retry annotated method must match the return type of
	 * fallBackMethod else no fall back method found exception
	 * 
	 * @return
	 */
	@GetMapping("start-service-retry")
	@Retry(name = "custom-config", fallbackMethod = "serviceFallBack")
	public String getStartServiceByResilienceRetry() {
		logger.info("Dummy API call");
		ResponseEntity<String> response = new RestTemplate().getForEntity("http://localhost:9090", String.class);
		return response.getBody();
	}

	/**
	 * Circuit Breaker annotation returns fallback response after the failure rate
	 * threshold for API call has met
	 * 
	 * Note: Return type of @CircuitBreaker annotation method must match the return
	 * type of fallBackMethod else no fall back method found exception
	 * 
	 * @return
	 */
	@GetMapping("start-service-circuit-breaker")
	@CircuitBreaker(name = "custom-config", fallbackMethod = "serviceFallBack")
	public String getStartServiceByResilienceCircuitBreaker() {
		logger.info("Dummy API call");
		ResponseEntity<String> response = new RestTemplate().getForEntity("http://localhost:9090", String.class);
		return response.getBody();
	}

	/**
	 * RateLimiter annotation returns fallback response after the configured no. of
	 * API call attempts failed within a specified time duration
	 * 
	 * Note: Return type of @RateLimiter annotation method must match the return
	 * type of fallBackMethod else no fall back method found exception
	 * 
	 * @return
	 */
	@GetMapping("start-service-rate-limiter")
	@RateLimiter(name = "custom-config", fallbackMethod = "serviceFallBack")
	public String getStartServiceByResilienceRateLimiter() {
		logger.info("Dummy API call");
		ResponseEntity<String> response = new RestTemplate().getForEntity("http://localhost:9090", String.class);
		return response.getBody();
	}

	/**
	 * BulkHead annotation allows us to specify the number of maximum no. of
	 * concurrent calls
	 * 
	 * Note: Return type of @BulkHead annotation method must match the return type
	 * of fallBackMethod else no fall back method found exception
	 * 
	 * @return
	 */
	@GetMapping("start-service-bulk-head")
	@Bulkhead(name = "custom-config", fallbackMethod = "serviceFallBack")
	public String getStartServiceByResilienceBulkHead() {
		logger.info("Dummy API call");
		ResponseEntity<String> response = new RestTemplate().getForEntity("http://localhost:9090", String.class);
		return response.getBody();
	}

	/**
	 * When circuit breaker is in open state, instead for while label error page
	 * This method will return default fallback response
	 * 
	 * It needs to catch an exception/throwable as parameter
	 * 
	 * This method is value for the fallbackMethod parameter in resilience4j
	 * annotation
	 * 
	 * @return
	 */
	public String serviceFallBack(Exception exception) {
		return "Fall back response: start-service is not available";
	}
}
