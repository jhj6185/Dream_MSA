package com.dream.saveservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dream.saveservice.dto.SaveDto;
import com.dream.saveservice.mapper.SaveMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SaveServiceImpl implements SaveService{
	
	private final SaveMapper mapper;
	@Override
	public void save(SaveDto dto) {
		// TODO Auto-generated method stub
		mapper.save(dto);
	}
	@Override
	public List<SaveDto> select(String userId) {
		// TODO Auto-generated method stub
		return mapper.select(userId);
	}

}
