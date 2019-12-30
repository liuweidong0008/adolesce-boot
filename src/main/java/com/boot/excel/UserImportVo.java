package com.boot.excel;

import com.boot.common.utils.excel.imports.ExcelImportBaseBo;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class UserImportVo extends ExcelImportBaseBo {
	private String id;
	private String name;
	private String password;
	private String sex;
	private String email;
	private String role;
	private int age;
	private BigDecimal saraly;
	private Date birthDay;
	private long hobby;
	private long flag;
	private String phone;
	private String userNum;
}
