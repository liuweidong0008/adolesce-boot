/*******************************************************************************
 * @(#)RequestParamsTrim.java 2019年12月10日 15:29
 * Copyright 2019 明医众禾科技（北京）有限责任公司. All rights reserved.
 *******************************************************************************/
package com.boot.common.annotation;

import java.lang.annotation.*;

/**
 * <b>Application name：</b> RequestParamsTrim.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2019 明医众禾科技（北京）有限责任公司 版权所有。<br>
 * <b>Company：</b> 明医众禾科技（北京）有限责任公司 <br>
 * <b>@Date：</b> 2019年12月10日 15:29 <br>
 * <b>@author：</b> <a href="mailto:lwd@miyzh.com"> liuWeidong </a> <br>
 * <b>@version：</b>V2.0.0 <br>
 */

@Target({ElementType.PARAMETER})  //作用范围
@Retention(RetentionPolicy.RUNTIME)  //生效时期
@Documented  //文档化
public @interface RequestParamsTrim {

}