//package com.dream.gatewayservice.config;
//
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class FilterConfig {
//	@Bean
//	public RouteLocator gatewayRouts(RouteLocatorBuilder builder) {
//		return builder.routes()
//			.route(r -> r.path("/list/**")
//				.filters( f -> f.addRequestHeader("first-request", "first-request-header")
//				.addResponseHeader("first-response", "first-response-header"))
//				.uri("http://localhost:8007/"))
//			.route(r -> r.path("/second-service/**")
//				.filters( f -> f.addRequestHeader("second-request", "second-request-header")
//				.addResponseHeader("second-response", "second-response-header"))
//				.uri("http://localhost:8002/"))
//				.build();
//		//header에 key value 값으로 보내줄 수 있고,
//		//각 서비스의 @RequestHeader("key")로 header에 넣은 값 받을 수 있음
//	}
//}
