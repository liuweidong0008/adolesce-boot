/*******************************************************************************
 * @(#)MyParam.java 2019年12月09日 21:17
 * Copyright 2019 明医众禾科技（北京）有限责任公司. All rights reserved.
 *******************************************************************************/
package com.boot.adolesce.module.commonbean;

import com.boot.adolesce.module.excel.UserImportVo;
import lombok.Data;

import java.util.List;

/**
 * <b>Application name：</b> MyParam.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2019 明医众禾科技（北京）有限责任公司 版权所有。<br>
 * <b>Company：</b> 明医众禾科技（北京）有限责任公司 <br>
 * <b>@Date：</b> 2019年12月09日 21:17 <br>
 * <b>@author：</b> <a href="mailto:lwd@miyzh.com"> liuWeidong </a> <br>
 * <b>@version：</b>V2.0.0 <br>
 */

@Data
public class MyParam {
    private String name;
    private String address;
    private Integer age;
    private String ip;

    private UserImportVo userImportVo;
    private List<UserImportVo> userImportVos;
}