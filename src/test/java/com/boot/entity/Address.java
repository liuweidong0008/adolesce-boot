/*******************************************************************************
 * @(#)Address.java 2019年10月12日 17:41
 * Copyright 2019 明医众禾科技（北京）有限责任公司. All rights reserved.
 *******************************************************************************/
package com.boot.entity;

import lombok.Data;

import java.util.List;

/**
 * <b>Application name：</b> Address.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2019 明医众禾科技（北京）有限责任公司 版权所有。<br>
 * <b>Company：</b> 明医众禾科技（北京）有限责任公司 <br>
 * <b>@Date：</b> 2019年10月12日 17:41 <br>
 * <b>@author：</b> <a href="mailto:lwd@miyzh.com"> liuWeidong </a> <br>
 * <b>@version：</b>V2.0.0 <br>
 */

@Data
public class Address {
    private String code;
    private String parentCode;
    private List<Address> childAddress;

    public Address(String code,String parentCode) {
        this.code = code;
        this.parentCode = parentCode;
    }

    @Override
    public String toString() {
        return "Address{" +
                "code='" + code + '\'' +
                ", parentCode='" + parentCode + '\'' +
                ", childAddress=\n" + childAddress +
                '}'+"\n";
    }
}