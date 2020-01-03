package com.boot.adolesce.module.excel;

import lombok.Data;

import java.util.Date;

/**
 * Application name： MemberRecordExportBean.java
 * Application describing：导出Bean
 * Copyright： Copyright &copy; 2019 明医众禾科技（北京）有限责任公司 版权所有。
 * Company： 明医众禾科技（北京）有限责任公司
 * @Date： 2019年011月09日 13:55
 * @author： <area href="mailto:liuwd@miyzh.com"> LiuWeidong </area>
 * @version：V2.0.0
 */
@Data
public class MemberRecordExportBean {
	private static final long serialVersionUID = 1L;
	private String clientName;
	private String serialNo;
	private Date transferTime;
	private String cardNo;
	private String bankName;
}