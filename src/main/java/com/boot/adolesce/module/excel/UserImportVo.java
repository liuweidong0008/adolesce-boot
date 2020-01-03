package com.boot.adolesce.module.excel;

import com.boot.adolesce.framework.utils.excel.imports.ExcelImportBaseBo;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Application name： UserImportVo.java
 * Application describing：导入Vo
 * Copyright： Copyright &copy; 2019 明医众禾科技（北京）有限责任公司 版权所有。
 * Company： 明医众禾科技（北京）有限责任公司
 * @Date： 2019年011月09日 13:55
 * @author： <area href="mailto:liuwd@miyzh.com"> LiuWeidong </area>
 * @version：V2.0.0
 */
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
