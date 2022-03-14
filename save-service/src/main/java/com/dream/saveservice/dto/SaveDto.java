package com.dream.saveservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveDto {
	private int proNo;
	private String userId;
	private double orderPrice;
	private String orderDate;
	private String endDate;
}