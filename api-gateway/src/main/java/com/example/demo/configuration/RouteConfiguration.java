package com.example.demo.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfiguration{
	
	@Bean
	public RouteLocator configCustomRoutes(RouteLocatorBuilder routeLocatorBuilder) {
		return routeLocatorBuilder.routes()
				.route(r -> r.path("/start-service/**").uri("lb://start-service"))
				.route(r -> r.path("/mid-service/**").uri("lb://mid-service"))
				.route(r -> r.path("/car-service/**").uri("lb://details-service"))
				.build();
	}

}
