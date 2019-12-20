/*******************************************************************************
 * @(#)ExcelPoiUtil.java 2019年07月24日 14:42
 * Copyright 2019 明医众禾科技（北京）有限责任公司. All rights reserved.
 *******************************************************************************/
package com.boot.common.utils.excel;

import cn.hutool.core.collection.CollUtil;
import com.boot.common.utils.ReflectUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dom4j.DocumentException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.boot.common.utils.excel.ExcelHelper.getCellStyle;
import static org.springframework.util.ObjectUtils.isEmpty;

/**
 * <b>Application name：</b> ExcelPoiUtil.java <br>
 * <b>Application describing：基于 POI 实现的excel导出工具,目前仅支持 .xlsx 后缀格式 </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2019 明医众禾科技（北京）有限责任公司 版权所有。<br>
 * <b>Company：</b> 明医众禾科技（北京）有限责任公司 <br>
 * <b>Date：</b> 2019年07月24日 14:42 <br>
 * <b>@author：</b> <area href="mailto:limz@miyzh.com"> 李明哲 </area> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Slf4j
public class ExcelPoiUtil {
    private String fileName;
    private String fileSuffix = ".xlsx";
    private List<ExcelSheet> sheetDataList;
    private Workbook workbook;
    private Config config;

    public static final String XLSX = ".xlsx";
    public static final String XLS = ".xls";

    /**
     * 构造器
     *
     * @param fileName
     * @param sheetData
     */
    public ExcelPoiUtil(String fileName, ExcelSheet sheetData) {
        this.fileName = fileName;
        this.sheetDataList = Collections.singletonList(sheetData);
        this.workbook = StringUtils.equals(XLSX, fileSuffix)?new XSSFWorkbook():new HSSFWorkbook();
    }

    /**
     * 构造器
     *
     * @param fileName
     * @param sheetDataList
     */
    public ExcelPoiUtil(String fileName, List<ExcelSheet> sheetDataList) {
        this.fileName = fileName;
        this.sheetDataList = sheetDataList;
        this.workbook = StringUtils.equals(XLSX, fileSuffix)?new XSSFWorkbook():new HSSFWorkbook();
    }

    /**
     * 构造器
     *
     * @param fileName 文件名称
     * @param configName 配置名称
     */

    public ExcelPoiUtil(String fileName, String configName) throws DocumentException {
        this.fileName = fileName;
        this.config = new ReadConfig().readConfig(configName);
        this.workbook = StringUtils.equals(XLSX, fileSuffix)?new XSSFWorkbook():new HSSFWorkbook();
    }

    /**
     * 添加数据集合(添加的顺序与配置文件中的sheet配置顺序匹配)
     * @param dataList
     * @return
     */
    public ExcelPoiUtil addDataList(List<?> dataList) {
        if(CollUtil.isEmpty(sheetDataList)){
            this.sheetDataList = new ArrayList<>();
        }
        ExcelSheet excelSheet = new ExcelSheet();
        Sheet sheet = this.config.getSheetLst().get(this.sheetDataList.size());
        sheet.getColumns().forEach(c -> excelSheet.addHeader(c.getTitle(), c.getDataKey()));
        excelSheet.setName(sheet.getName());
        excelSheet.setDataList(dataList);
        this.sheetDataList.add(excelSheet);
        return this;
    }

    /**
     * 填充表格
     */
    private void fillWorkBook() {
        CellStyle headCellStyle = getCellStyle(workbook,true);
        CellStyle dataCellStyle = getCellStyle(workbook,false);
        this.sheetDataList.forEach(sheetData -> {
            org.apache.poi.ss.usermodel.Sheet sheet = this.workbook.createSheet(sheetData.getName());
            int columnSize = sheetData.getHeaders().size();
            Row headRow = sheet.createRow(0);
            headRow.setHeight((short) 658);
            sheetData.setName(sheetData.getName());
            int headCellIndex = 0;
            Cell headCell;
            for (int i = 0; i < columnSize; i++) {
                sheet.setColumnWidth(i, 6251);
                ExcelHeader header = sheetData.getHeaders().get(i);
                headCell = headRow.createCell(headCellIndex++);
                headCell.setCellStyle(headCellStyle);
                headCell.setCellValue(header.getTitle());
            }
            if (isEmpty(sheetData.dataList)) {
                return;
            }
            Row row;
            for (int i = 0, length = sheetData.dataList.size(); i < length; i++) {
                Object t = sheetData.dataList.get(i);
                row = sheet.createRow(i + 1);
                row.setHeight((short) 458);
                for (int j = 0; j < columnSize; j++) {
                    ExcelHeader header = sheetData.getHeaders().get(j);
                    Cell itemCell = row.createCell(j);
                    itemCell.setCellStyle(dataCellStyle);
                    itemCell.setCellValue(fmtString(getObject(t, header.code)));
                }
            }
        });
    }

    private static final String SPLIT = ".";

    /**
     * 取值
     *
     * @param t
     * @param code
     * @return
     */
    private String getObject(Object t, String code) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Object obj = null;
        try {
            if (!code.contains(SPLIT)) {
                if (t instanceof Map) {
                    Map map = (Map) t;
                    obj = map.get(code);
                } else {
                    obj = ReflectUtils.getValueByFieldName(t, code);
                }
            } else if (t instanceof Map && ((Map) t).containsKey(code)) {
                obj = ((Map) t).get(code);
            } else {
                String[] split = code.split(SPLIT);
                obj = t;
                for (int i = 0; i < split.length && obj != null; i++) {
                    String field = split[i];
                    if (obj instanceof Map) {
                        Map map = (Map) obj;
                        obj = map.get(field);
                    } else {
                        obj = ReflectUtils.getValueByFieldName(t, field);
                    }
                    if (obj == null) {
                        break;
                    }
                }
            }
        } catch (Exception e) {
            log.error("", e);
        }
        if (isEmpty(obj)) {
            return "";
        }
        if (obj instanceof Date) {
            return sdf.format((Date) obj);
        }
        return obj.toString();
    }

    /**
     * 格式化
     *
     * @param str
     * @return
     */
    private String fmtString(String str) {
        if (StringUtils.isBlank(str)) {
            return "";
        }
        return str;
    }

    /**
     * 导出
     *
     * @param response
     * @throws IOException
     */
    public void export(HttpServletResponse response) throws IOException {
        fillWorkBook();
        response.reset();
        response.setContentType("application/vnd.ms-excel");
        String excelFileName = fileName + fileSuffix;
        response.addHeader("Content-Disposition", "attachment;filename="
                + URLEncoder.encode(excelFileName, "UTF-8"));
        try (Workbook wb = workbook;
             OutputStream os = response.getOutputStream()) {
            wb.write(os);
            os.flush();
        }
    }

    @Data
    public static class ExcelHeader {
        private String title;
        private String code;

        ExcelHeader(String title, String code) {
            this.title = title;
            this.code = code;
        }
    }

    @Data
    public static class ExcelSheet {
        private String name;
        private List<ExcelHeader> headers;
        private List<?> dataList;

        public ExcelSheet() {
            this.name = "Sheet1";
            this.headers = new ArrayList<>();
        }

        public ExcelSheet(String name) {
            this.name = name;
            this.headers = new ArrayList<>();
        }

        public ExcelSheet(String name, List<ExcelHeader> headerList) {
            this.name = name;
            this.headers = headerList;
        }

        public ExcelSheet(String name, List<ExcelHeader> headerList, List<?> dataList) {
            this.name = name;
            this.headers = headerList;
            this.dataList = dataList;
        }

        public void addHeader(String title, String code) {
            if (headers == null) {
                headers = new ArrayList<>();
            }
            headers.add(new ExcelHeader(title, code));
        }
    }
}