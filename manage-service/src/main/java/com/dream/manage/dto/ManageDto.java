package com.dream.manage.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class ManageDto {
	private int proId;
	private String proName;
	private double proLimit;
	private String description;
	private int term;
}
