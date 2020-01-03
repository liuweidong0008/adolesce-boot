/*******************************************************************************
 * @(#)ExcelExport.java 2019年12月18日 17:22
 * Copyright 2019 明医众禾科技（北京）有限责任公司. All rights reserved.
 *******************************************************************************/
package com.boot.module.excel;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
@Controller
@RequestMapping("/excel")
public class ExcelController {
    @Resource
    private ExcelService excelService;

    /**
     * 导出方式一
     * @param request
     * @param response
     */
    @GetMapping(value = "/export/style1")
    public void exportStyle1(HttpServletRequest request, HttpServletResponse response){
        this.excelService.exportStyle1(response);
    }

    /**
     * 导出方式二
     * @param request
     * @param response
     */
    @GetMapping(value = "/export/style2")
    public void exportStyle2(HttpServletRequest request, HttpServletResponse response){
        this.excelService.exportStyle2(response);
    }

    /**
     * 导出方式三
     * @param request
     * @param response
     */
    @GetMapping(value = "/export/style3")
    public void exportStyle3(HttpServletRequest request, HttpServletResponse response){
        this.excelService.exportStyle3(response);
    }

    /**
     * 导出方式四
     * @param request
     * @param response
     */
    @GetMapping(value = "/export/style4")
    public void exportStyle4(HttpServletRequest request, HttpServletResponse response){
        this.excelService.exportStyle4(response);
    }

    /**
     * 跳转至上传页面
     *
     * @return
     */
    @GetMapping({"importPage"})
    public String importStyle1() {
        return "excel/import/uploadPage";
    }

    /**
     * 导入方式一
     * @param file
     * @param redirectAttributes
     */
    @PostMapping(value = "/import/style1")
    public String importStyle1(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws Exception {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:uploadStatus";
        }
        this.excelService.importStyle1(file,redirectAttributes);
        return "redirect:uploadStatus";
    }

}