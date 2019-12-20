/*******************************************************************************
 * @(#)ExcelExport.java 2019年12月18日 17:22
 * Copyright 2019 明医众禾科技（北京）有限责任公司. All rights reserved.
 *******************************************************************************/
package com.boot.excel;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <b>Application name：</b> ExcelExport.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2019 明医众禾科技（北京）有限责任公司 版权所有。<br>
 * <b>Company：</b> 明医众禾科技（北京）有限责任公司 <br>
 * <b>@Date：</b> 2019年12月18日 17:22 <br>
 * <b>@author：</b> <a href="mailto:lwd@miyzh.com"> liuWeidong </a> <br>
 * <b>@version：</b>V2.0.0 <br>
 */

@Slf4j
@RestController
@RequestMapping("/excel")
public class ExcelController {
    @Resource
    private ExcelExportService excelExportService;

    /**
     * 方式一
     * @param request
     * @param response
     */
    @GetMapping(value = "/export/style1")
    public void style1(HttpServletRequest request, HttpServletResponse response){
        this.excelExportService.style1(response);
    }

    /**
     * 方式二
     * @param request
     * @param response
     */
    @GetMapping(value = "/export/style2")
    public void style2(HttpServletRequest request, HttpServletResponse response){
        this.excelExportService.style2(response);
    }
}