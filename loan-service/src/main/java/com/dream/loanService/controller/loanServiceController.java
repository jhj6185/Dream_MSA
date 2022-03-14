package com.dream.loanService.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.dream.loanService.domain.Message;
import com.dream.loanService.domain.loanProductVO;
import com.dream.loanService.service.loanService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class loanServiceController {
	private final loanService service;
	
	@GetMapping("/apply")
	public String apply(@RequestParam(value = "proNo")int proNO, Model model) {
		System.out.println("대출 신청 하자" + proNO + "      3");
		
		loanProductVO proVO = new loanProductVO();
//		proVO.setProNo(proNO);
//		proVO.setProName("대출");
//		proVO.setProLimit(400000);
//		proVO.setDescription("대출 상품 잉ㅂ니다");
//		proVO.setTerm("2023-12-31");
		
		
		proVO = service.bringLoan(proNO);
		
		System.out.println(proVO.getProName() + "==============================");
		
		model.addAttribute("proVO", proVO);
		
		return "apply";
	}
	

	// kafka producer
		@Autowired
		private final KafkaTemplate<String, Message> kafkaTemplate;

		@Value(value = "${kafka.topic_name}")
		private String kafkaTopicName;

		String status = "";

		@RequestMapping(value="/kafka", method = RequestMethod.POST)
		public ResponseEntity<String> sendMessage(@RequestBody Message message) {
			log.info("메세지 전동 된다. {}", message);
			
//			kafkaTemplate.send(kafkaTopicName, message);

			ListenableFuture<SendResult<String, Message>> future = this.kafkaTemplate.send(kafkaTopicName, message);

			future.addCallback(new ListenableFutureCallback<SendResult<String, Message>>() {

				@Override
				public void onSuccess(SendResult<String, Message> result) {
					status = "Message send successfully, 메시지가 성공적으로 전송 됨.";
					log.info("메시지가 성공적으로 전송됨. successfully sent message = {}, with offset = {}", message,
							result.getRecordMetadata().offset());
				}

				@Override
				public void onFailure(Throwable ex) {
					log.info("Failed to send message = {}, error = {}", message, ex.getMessage());
					status = "Message sending failed = 메시지 전송 실패...";
				}
			});
			
			return ResponseEntity.ok(status);
		}
	
}
