/*******************************************************************************
 * @(#)Column.java 2019年08月29日 10:42
 * Copyright 2019 明医众禾科技（北京）有限责任公司. All rights reserved.
 *******************************************************************************/
package com.boot.common.utils.excel;

import lombok.Data;

import java.util.List;

/**
 * <b>Application name：</b> Column.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2019 明医众禾科技（北京）有限责任公司 版权所有。<br>
 * <b>Company：</b> 明医众禾科技（北京）有限责任公司 <br>
 * <b>Date：</b> 2019年08月29日 10:42 <br>
 * <b>@author：</b> <area href="mailto:limz@miyzh.com"> 李明哲 </area> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Data
public class Column {

    private String title;

    private String dataKey;

    private String type;

    private String value;

    private String required;

    private List<Rule> rules;

    private String alias;

    public static String TYPE_DATE = "date";

    public static String TYPE_STRING = "string";

    public static String TYPE_DM = "dm";

    public static String TYPE_INTEGER = "integer";

    public static String TYPE_DIR = "dir";
}