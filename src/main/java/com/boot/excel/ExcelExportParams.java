package com.boot.excel;

import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.Lists;
import lombok.Data;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Data
public class ExcelExportParams {
    /**
     * 文件名称
     */
    private String fileName = "数据列表";
    /**
     * 文件后缀
     */
    private String fileSuffix = ".xlsx";
    /**
     * response
     */
    private HttpServletResponse httpServletResponse;
    /**
     * sheets
     */
    private List<ExcelExportSheet> sheets;

    public ExcelExportParams() {
    }

    public ExcelExportParams(HttpServletResponse httpServletResponse) {
        this.httpServletResponse = httpServletResponse;
    }

    public ExcelExportParams(ExcelExportSheet sheet, HttpServletResponse httpServletResponse) {
        this.sheets = Lists.newArrayList(sheet);
        this.httpServletResponse = httpServletResponse;
    }

    public ExcelExportParams(List<ExcelExportSheet> sheets, HttpServletResponse httpServletResponse) {
        this.sheets = sheets;
        this.httpServletResponse = httpServletResponse;
    }

    public ExcelExportParams addSheet(ExcelExportSheet sheet) {
        if(CollUtil.isEmpty(sheets)){
            this.sheets = new ArrayList<>();
        }
        this.sheets.add(sheet);
        return this;
    }

    public ExcelExportParams addSheet(List dataList,String[] headerName,String[] headerKey,String sheetName) {
        if(CollUtil.isEmpty(sheets)){
            this.sheets = new ArrayList<>();
        }
        ExcelExportSheet sheet = new ExcelExportSheet(dataList,headerName,headerKey,sheetName);
        this.sheets.add(sheet);
        return this;
    }

    @Data
    public static class ExcelExportSheet {
        public ExcelExportSheet() {
        }
        public ExcelExportSheet(List dataList,String[] headerName,String[] headerKey,String sheetName) {
            this.dataList = dataList;
            this.headerName = headerName;
            this.headerKey = headerKey;
            this.sheetName = sheetName;
        }
        /**
         * 行头名称
         */
        private String[] headerName;
        /**
         * 行头Key
         */
        private String[] headerKey;
        /**
         * sheet名称
         */
        private String sheetName = "sheet";
        /**
         * 日期格式
         */
        private String dateFormart = "yyyy-MM-dd";
        /**
         * 导出数据
         */
        private List dataList;

    }
}
