/*******************************************************************************
 * @(#)UUIDUtils.java 2019年09月20日 10:36
 * Copyright 2019 明医众禾科技（北京）有限责任公司. All rights reserved.
 *******************************************************************************/
package com.boot.common.utils;

/**
 * <b>Application name：</b> UUIDUtils.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2019 明医众禾科技（北京）有限责任公司 版权所有。<br>
 * <b>Company：</b> 明医众禾科技（北京）有限责任公司 <br>
 * <b>@Date：</b> 2019年09月20日 10:36 <br>
 * <b>@author：</b> <a href="mailto:lwd@miyzh.com"> liuWeidong </a> <br>
 * <b>@version：</b>V2.0.0 <br>
 */
public class UUIDUtils {
    public static synchronized String getUUID() {
        return String.valueOf(SnowFlakeIdUtils.getSnowFlakeId());
    }
}