package com.dream.manage.service;

import org.springframework.stereotype.Service;

import com.dream.manage.dto.ManageDto;
import com.dream.manage.mapper.ManageMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ManageServiceImpl implements ManageService{
	private final ManageMapper mapper;

	@Override
	public void register(ManageDto dto) {
		// TODO Auto-generated method stub
		mapper.register(dto);
	}
}
