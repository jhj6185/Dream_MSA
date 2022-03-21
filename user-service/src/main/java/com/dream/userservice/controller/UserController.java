package com.dream.userservice.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class UserController {
	
	@RequestMapping(value="/", method = RequestMethod.GET)
	public String home() {
		System.out.println("controller stasrt");
		
		return "index";
	}
	
	@GetMapping("/loanList")
	public String loanList() {
		return "loanList";
	}
	
	@GetMapping("/startup")
	public String startup() {
		return "startup";
	}
	
	@GetMapping("/info")
    public String info(@Value("${server.port}") String port) {
        return "User info에 오신것을 환영합니다 Port: {" + port + "}";
    }
	
	 @GetMapping(path = "/service")//service로 접근하면 일단 무조건 로그인으로 보내줌
	   public void user_service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   
	      log.info("log : 들어와지나 userServiced입니다.");
	      System.out.println("들어와지나 userServiced입니다");
//	      if (id_token == null || id_token == "") {
//	         login(request, response);
//	         //response.sendRedirect("http://localhost:8480/login");
//	         
//	      }
	      
	      //access_token 존재확인
//	      if (access_token == null || id_token == "") {
//	         login(request, response);
//	         //response.sendRedirect("http://localhost:8480/login");
//	      }

	      login(request, response);
	      
	   }

	   @GetMapping(path = "/login") //로그인에서는 keycloak으로 로그인하는데, token을 받을 수 있는 url로 보내줌
	   public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		   //todo make keycloak login url
		   System.out.println("login창으로 이동");
	      try {//redirect URL 알아보기
	    	  System.out.println("login창1으로 이동");
	         response.sendRedirect("http://192.168.1.54:8080/auth/realms/MSA/protocol/openid-connect/auth?response_type=code&client_id=memberService&redirect_uri=http://localhost:8000/user/auth&scope=openid&nonce=asb3");
	      }
	      catch (IOException e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	         System.out.println("login오류창으로 이동");
	      }
	      
	      return;
	   }
	  
//	   @GetMapping(path="/menu")
//	   public String menu(Model model) {
//		   
//		   return "loanlist"; //view로 페이지를 이동하되, hashMap도 넘기는 방법을 고안
//	   }
	   
	   @GetMapping(path = "/auth") //로그인 성공시 받을 수 있는 url : keycloak에서 설정한 redirect url

	   public ResponseEntity<Void> auth(HttpServletRequest request, HttpServletResponse response,Model model, HttpSession session) 
		throws ServletException, IOException {
	      //to do token save
		   System.out.println("auth들어왓습니다잉???");
	      String code = request.getParameter("code");
	      String query = "code=" + URLEncoder.encode(code,"UTF-8");
	      query += "&client_id=" + "memberService";
	      query += "&client_secret=" + "RePeZAfKZ9XFiFc2Z5LzzFstYeduPQSd";
	      query += "&redirect_uri=" + "http://localhost:8000/user/auth";
	      query += "&grant_type=authorization_code";
	      
	      HashMap<String, String> tokenJson = getHttpConnection("http://localhost:8080/auth/realms/MSA/protocol/openid-connect/token", query);
	      System.out.println("tokenJson 먹었습니다~~~~~~~~~~");
	      model.addAttribute("list", tokenJson);
	      session.setAttribute("sessionList", tokenJson);
	      return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("http://localhost:8000/menu/menu")).build();

	   }
	   
	   private HashMap<String, String> getHttpConnection(String uri, String param) throws ServletException, IOException {
	      URL url = new URL(uri);
	      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	      conn.setRequestMethod("POST");
	      conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	      conn.setRequestProperty("Accept", "application/json"); //응답 형식 유형 설정
	      conn.setDoOutput(true); //콘텐츠를 보내는 데 연결이 사용되는지 확인
	      try (OutputStream stream = conn.getOutputStream()) {
	      try (BufferedWriter wd = new BufferedWriter(new OutputStreamWriter(stream))) {
	      wd.write(param);//param은 /auth에서 날린 parameter들
	      }
	      }
	      int responseCode = conn.getResponseCode();
	      System.out.println(responseCode);
	      String line;
	      StringBuffer buffer = new StringBuffer();
	      try (InputStream stream = conn.getInputStream()) {
	      try (BufferedReader rd = new BufferedReader(new InputStreamReader(stream))) {
	      while ((line = rd.readLine()) != null) {
	      buffer.append(line);
	      buffer.append('\r');
	      }
	      }
	      } catch (Throwable e) {
	      e.printStackTrace();
	      }
	      
	      //json파싱시작
	      JSONObject jObject = new JSONObject(buffer.toString());
	      String access_token= jObject.getString("access_token");
	      String id_token= jObject.getString("id_token");
	      
	      System.out.println("access_token==============="+access_token);
	      System.out.println("id_token==============="+id_token);
	      
	      //jwt decode
	      String[] access_chunks = access_token.split("\\."); //body부분 header부분 나눠
	      String[] id_chunks = access_token.split("\\."); //body부분 header부분 나눠
	      
	      Base64.Decoder decoder = Base64.getUrlDecoder();
	      
	      String access_payload = new String(decoder.decode(access_chunks[1]));
	      String id_payload = new String(decoder.decode(id_chunks[1]));
	      
	      System.out.println("access_payload============"+access_payload);
	      System.out.println("id_payload================"+id_payload);
	      
	      //json 형식으로 decode 된 token을 json파싱
	      JSONObject aObject = new JSONObject(access_payload);
	      String username= aObject.getString("preferred_username"); //username = 유저 아이디
	      String scope= aObject.getString("scope");
	      
	      System.out.println("preferred_username========="+username);
	      System.out.println("scope========="+scope);
	      //userId, scope를 role로할지,,? hasRole로 차단하던지 아니면 각 서비스별로 확인시키던지?
	      
	      HashMap<String,String> map = new HashMap<>();
	      map.put("username", username);
	      map.put("scope", scope);
	      
	      return map; //buffer에 json형태를 다 문자열로 바꿔서 view에 보여주고있다

	      }
}
