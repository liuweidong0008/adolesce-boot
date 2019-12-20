package com.boot.common.utils.excel;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.boot.excel.ExcelExportParams;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Excel工具类
 */
public class ExcelHelper {
    public static final String XLSX = ".xlsx";
    public static final String XLS = ".xls";

    /**
     * 导出Excel
     * @param params 导出参数
     * @return
     */
    public static void export(ExcelExportParams params) throws Exception {
        Workbook workbook = createExcel(params);
        String fileName = new StringBuilder(params.getFileName()).append(params.getFileSuffix()).toString();
        outputFile(params.getHttpServletResponse(),fileName,workbook);
    }

    /**
     * 创建Excel
     * @param params 参数
     * @return
     */
    public static Workbook createExcel(ExcelExportParams params) throws Exception {
        if(CollUtil.isEmpty(params.getSheets())){
            throw new Exception("Excel创建：缺少sheet相关参数配置");
        }
        boolean isXlsx = StringUtils.equals(XLSX,params.getFileSuffix());
        Workbook wb = isXlsx ? new XSSFWorkbook():new HSSFWorkbook();
        params.getSheets().stream().forEach(sheet -> createSheet(wb,sheet));
        if(CollUtil.isEmpty(wb.sheetIterator())){
            throw new Exception("Excel创建：缺少headerKey相关参数配置");
        }
        return wb;
    }

    /**
     * 创建Sheet
     * @param wb
     * @param exportSheet
     */
    private static void createSheet(Workbook wb,ExcelExportParams.ExcelExportSheet exportSheet) {
        String[] headerName = exportSheet.getHeaderName();
        String[] headerKey = exportSheet.getHeaderKey();
        String sheetName = exportSheet.getSheetName();
        List<Object> dataList = exportSheet.getDataList();
        if (Objects.isNull(headerKey) || headerKey.length <= 0) {
            return;
        }
        Sheet sheet = wb.createSheet(sheetName);
        CellStyle headCellStyle = getCellStyle(wb,true);
        CellStyle dataCellStyle = getCellStyle(wb,false);

        Row row = sheet.createRow(0);
        row.setHeight((short) 658);
        Cell cell;
        if (Objects.nonNull(headerName) && headerName.length > 0) {
            for (int i = 0; i < headerName.length; i++) {
                cell = row.createCell(i);
                cell.setCellStyle(headCellStyle);
                cell.setCellValue(headerName[i]);
            }
        }else{
            for (int j = 0; j < headerKey.length; j++) {
                cell = row.createCell(j);
                cell.setCellStyle(headCellStyle);
                cell.setCellValue(headerKey[j]);
            }
        }
        int n = 0;
        if (CollUtil.isNotEmpty(dataList)) {
            for (Object obj: dataList) {
                Field[] fields = obj.getClass().getDeclaredFields();
                Map<String,Field> fieldMap = Arrays.stream(fields).collect(Collectors.toMap(Field::getName, Function.identity()));
                row = sheet.createRow(n + 1);
                row.setHeight((short) 458);
                for (int j = 0; j < headerKey.length; j++) {
                    Field field = fieldMap.get(headerKey[j]);
                    if(Objects.nonNull(field)){
                        field.setAccessible(true);
                        cell = row.createCell(j);
                        cell.setCellType(CellType.STRING);
                        cell.setCellStyle(dataCellStyle);
                        Object fieldValue = null;
                        try {
                            fieldValue = field.get(obj);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        if (Objects.isNull(fieldValue)) {
                            cell.setCellValue("");
                            continue;
                        }
                        if (fieldValue instanceof Date) {
                            Date dateTemp = (Date)fieldValue;
                            cell.setCellValue(DateUtil.format(dateTemp, exportSheet.getDateFormart()));
                            continue;
                        }
                        cell.setCellValue(fieldValue.toString());
                    }
                }
                n++;
            }
        }
        for (int i = 0; i < headerKey.length; i++) {
            sheet.setColumnWidth(i, 6251);
        }
    }

    /**
     * 单元格样式
     * @param wb
     * @return
     */
    public static CellStyle getCellStyle(Workbook wb, boolean isHead) {
        CellStyle cellStyle = wb.createCellStyle();
        //设置单元格垂直水平居中
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        //设置字体
        Font dataCellFont = wb.createFont();
        dataCellFont.setFontName("宋体");
        dataCellFont.setFontHeightInPoints((short)(isHead?14:11));
        if(isHead){
            dataCellFont.setBold(true);
        }
        cellStyle.setFont(dataCellFont);
        return cellStyle;
    }

    /**
     * 输出文件
     * @param response
     * @param fileName 文件名
     * @param wb
     * @throws IOException
     */
    private static void outputFile(HttpServletResponse response,String fileName,Workbook wb) throws Exception {
        if(Objects.isNull(wb) || Objects.isNull(response)){
            throw new Exception("Excel输出：缺少相关参数");
        }
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition",
                "attachment;filename=" + new String(fileName.getBytes("gb2312"), "ISO-8859-1"));
        //response.addHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
        OutputStream ouputStream = response.getOutputStream();
        ouputStream.flush();
        wb.write(ouputStream);
        ouputStream.close();
    }
}