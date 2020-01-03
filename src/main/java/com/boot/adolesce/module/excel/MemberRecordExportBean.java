package com.boot.adolesce.module.excel;

import lombok.Data;

import java.util.Date;

@Data
public class MemberRecordExportBean {
	private static final long serialVersionUID = 1L;
	private String clientName;
	private String serialNo;
	private Date transferTime;
	private String cardNo;
	private String bankName;
}