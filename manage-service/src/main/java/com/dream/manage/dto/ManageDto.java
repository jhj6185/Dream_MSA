package com.dream.manage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ManageDto {
//	private int proId;
	private String proName;
	private double proLimit;
	private String description;
	private int term;
}
