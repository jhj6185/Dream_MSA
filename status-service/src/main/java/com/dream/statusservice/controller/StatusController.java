package com.dream.statusservice.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.security.RolesAllowed;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dream.statusservice.dto.Datadto;
import com.dream.statusservice.dto.StatusDto;
import com.dream.statusservice.service.StatusService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class StatusController {
	private final StatusService statusService;
	
	@RolesAllowed({ "ADMIN" })
	@GetMapping("/wait")
	public String wait(Model model) throws Exception {
		List<StatusDto> list = statusService.getStatusList();
		model.addAttribute("list", list);
		return "statusList";
	}
	
	@RolesAllowed({ "ADMIN" })
	@PostMapping({"/update"})
	@ResponseBody
	public void update(@RequestParam Map<String, Object> data) throws JsonProcessingException {
		String json = data.get("data").toString();
		System.out.println("json = " + json);
		ObjectMapper mapper = new ObjectMapper();
		List<Datadto> datas = mapper.readValue(json, new TypeReference<ArrayList<Datadto>>() {
		});
		for (Datadto data1 : datas) {
			log.info("data1 = " + data1.toString());
			statusService.getUpdate(data1);
		}
	}
}