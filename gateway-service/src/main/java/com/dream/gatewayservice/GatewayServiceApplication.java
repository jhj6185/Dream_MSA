package com.dream.gatewayservice;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication(exclude = ReactiveUserDetailsServiceAutoConfiguration.class)
public class GatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayServiceApplication.class, args);
	}
	
//	@RequestMapping(value={"/{path:[a-z-]+}"}, method=RequestMethod.GET)
//    public ResponseEntity<Void> index(@PathVariable String path) throws Exception {
//		String access_token = "";
//		System.out.println("들어와지나 userServiced입니다"+path); //path잘 받아짐
//		
//	      //access_token 존재확인후 없을시 로그인창으로 이동
//	      if (access_token == null || access_token == "") {
//	         System.out.println("token 없는것으로 확인됩니다.");
//	         //response.sendRedirect("http://localhost:8480/login");
//	         return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("http://localhost:8000/user/service")).build();
//	      }
//	      
//	      //sendridirect 같은 방식으로 path로 넘겨주던지 해보자.
//	      //return "http://localhost:8000/user/menu";
//		return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("http://localhost:8000/"+path)).build();
//	    }

}
