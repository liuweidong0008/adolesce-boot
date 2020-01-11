/*******************************************************************************
 * @(#)InvoiceService.java 2019年11月19日 14:22
 * Copyright 2019 明医众禾科技（北京）有限责任公司. All rights reserved.
 *******************************************************************************/
package com.boot.adolesce.module.excel;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.StyleSet;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.boot.adolesce.framework.utils.excel.export.ExcelExportHelper;
import com.boot.adolesce.framework.utils.excel.export.ExcelExportParams;
import com.boot.adolesce.framework.utils.excel.imports.ExcelImportBaseBo;
import com.boot.adolesce.framework.utils.excel.imports.ExcelImportHelper;
import com.boot.adolesce.module.commonbean.MyParam;
import com.boot.adolesce.module.gdMemberRecord.entity.MemberRecord;
import com.boot.adolesce.module.gdMemberRecord.service.IMemberRecordService;
import com.boot.adolesce.module.user.entity.User;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Application name： InvoiceService.java
 * Application describing：Excel服务层
 * Copyright： Copyright &copy; 2019 明医众禾科技（北京）有限责任公司 版权所有。
 * Company： 明医众禾科技（北京）有限责任公司
 * @Date： 2019年011月09日 13:55
 * @author： <area href="mailto:liuwd@miyzh.com"> LiuWeidong </area>
 * @version：V2.0.0
 */
@Service
public class ExcelService {
    @Resource
    private IMemberRecordService memberRecordServiceImpl;

    /**
     * 导出方式一（代码指定sheet header）
     * @param response
     */
    public void exportStyle1(HttpServletResponse response){
        try {
            List<MemberRecord> memberRecords = this.getUsers(false);
            List<MemberRecord> memberRecordIns = this.getUsers(true);

            String[] headerName1 = { "流水号", "姓名","银行卡号","银行名称","交易时间","参数地址","导出姓名"};
            String[] headerKey1 = { "serialNo","clientName", "cardNo", "bankName", "transferTime","myParam.address","myParam.userImportVo.name"};

            String[] headerName2 = { "流水号", "姓名","创建时间"};
            String[] headerKey2 = { "serialNo","clientName", "transferTime"};

            ExcelExportParams exportParams = ExcelExportParams.create(response)
                    .addSheet("sheet1",headerName1,headerKey1,memberRecords, ExcelExportHelper.YMD_HMS)
                    .addSheet("sheet2",headerName2,headerKey2,memberRecordIns);
            exportParams.setFileName("交易流水");

            ExcelExportHelper.export(exportParams);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 导出方式二（xml配置sheet header）
     * @param response
     */
    public void exportStyle2(HttpServletResponse response){
        List<MemberRecord> memberRecords = this.getUsers(false);
        List<MemberRecord> memberRecordIns = this.getUsers(true);
        try {
            ExcelExportParams exportParams = ExcelExportParams.create(response,"memberRecord");
            exportParams.addDataList("sheet1",memberRecords);
            exportParams.addDataList("sheet2",memberRecordIns, ExcelExportHelper.YMD_HMS);
            exportParams.setFileName("交易流水");
            ExcelExportHelper.export(exportParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 导出方式三（配置Excel导出模板方式）
     * @param response
     */
    public void exportStyle3(HttpServletResponse response){
        List<MemberRecord> memberRecords = this.getUsers(false);
        List<MemberRecord> memberRecordIns = this.getUsers(true);
        try {
            //方式一：sheet名称由模板决定
            Map<String,List<?>> beanParams = new HashMap<>();
            beanParams.put("dataList1",memberRecords);
            beanParams.put("dataList2",memberRecordIns);
            ExcelExportHelper.export(beanParams,"memberRecordTemp.xlsx","充值记录表1.xlsx",response);

            //方式二：sheet名称可代码指定
            /*//模板sheet名称
            String[] tempSheetNames = {"Sheet1","Sheet2"};
            //自定义sheet名称
            String[] sheetNames = {"页面一","页面二"};
            //导出数据，添加数据与sheet顺序对应
            List<List<?>> dataLists = new ArrayList<>();
            dataLists.add(memberRecords);
            dataLists.add(memberRecordIns);
            ExcelHelper.export(tempSheetNames,sheetNames,dataLists,"memberRecordTemp.xlsx",
                    "充值记录表.xlsx",response);*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 导出方式四（Hutool）
     * @param response
     */
    public void exportStyle4(HttpServletResponse response){
        List<MemberRecordExportBean> beans = this.getBeans(false);
        List<MemberRecordExportBean> beanIns = this.getBeans(true);
        String[] headerName1 = { "流水号", "姓名","银行卡号","银行名称","交易时间"};
        String[] headerKey1 = { "serialNo","clientName", "cardNo", "bankName", "transferTime"};

        String[] headerName2 = { "流水号", "姓名","交易时间"};
        String[] headerKey2 = { "serialNo","clientName", "transferTime"};
        try {
            // 通过工具类创建writer
            //xls
            //ExcelWriter writer = ExcelUtil.getWriter();
            //xlsx
            ExcelWriter writer = ExcelUtil.getWriter(true);

            writer.setSheet("sheet1");
            for(int i = 0; i<headerKey1.length; i++){
                writer.addHeaderAlias(headerKey1[i],headerName1[i]);
                writer.setColumnWidth(i,24);
            }
            this.setStyle(writer);
            //合并单元格后的标题行，使用默认标题样式
            writer.merge(4, "充值记录表");
            // 一次性写出内容，使用默认样式，强制输出标题
            writer.write(beans, true);


            writer.setSheet("sheet2");
            for(int i = 0; i<headerKey2.length; i++){
                writer.addHeaderAlias(headerKey2[i],headerName2[i]);
                writer.setColumnWidth(i,24);
            }
            this.setStyle(writer);
            //合并单元格后的标题行，使用默认标题样式
            writer.merge(4, "充值记录表2");
            // 一次性写出内容，使用默认样式，强制输出标题
            writer.write(beans, true);

            ExcelExportHelper.outputFile(response,"充值记录表.xlsx",writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setStyle(ExcelWriter writer) {
        writer.setDefaultRowHeight(32);
        Font headerCellFont = writer.createFont();
        headerCellFont.setFontName("宋体");
        headerCellFont.setFontHeightInPoints((short)14);
        headerCellFont.setBold(true);

        Font dataCellFont = writer.createFont();
        headerCellFont.setFontName("宋体");
        headerCellFont.setFontHeightInPoints((short)11);

        StyleSet style = writer.getStyleSet();
        style.getHeadCellStyle().setFont(headerCellFont);
        style.getCellStyle().setFont(dataCellFont);
    }

    private List<MemberRecord> getUsers(boolean in) {
        Wrapper<MemberRecord> recordQueryWrapper = new EntityWrapper<MemberRecord>();
        if(in){
            recordQueryWrapper.in("serial_no","KXD1491534271296521,KXD151022561733751,801828121515491393957641");
        }
        List<MemberRecord> records = this.memberRecordServiceImpl.selectList(recordQueryWrapper);

        int n = 0;
        for(MemberRecord record : records){
            MyParam myParam = new MyParam();
            UserImportVo importVo = new UserImportVo();
            importVo.setName("刘威东"+ n++);
            importVo.setAge(n);
            myParam.setUserImportVo(importVo);
            myParam.setUserImportVos(CollUtil.newArrayList(importVo));
            myParam.setAddress("湖南长沙"+n);
            record.setMyParam(myParam);
        }
        return records;
    }

    private List<MemberRecordExportBean> getBeans(boolean in) {
        Wrapper<MemberRecord> recordQueryWrapper = new EntityWrapper<MemberRecord>();
        if(in){
            recordQueryWrapper.in("serial_no","KXD1491534271296521,KXD151022561733751,801828121515491393957641");
        }
        List<MemberRecord> records = this.memberRecordServiceImpl.selectList(recordQueryWrapper);
        List<MemberRecordExportBean> beans = records.stream().map(record -> {
            MemberRecordExportBean bean = new MemberRecordExportBean();
            BeanUtil.copyProperties(record,bean);
            return bean;
        }).collect(Collectors.toList());
        return beans;
    }

    /**
     * 导入方式一
     * @param fileItem
     * @param redirectAttributes
     */
    public void importStyle1(MultipartFile fileItem, RedirectAttributes redirectAttributes) throws Exception {
        //创建一个Excel导入帮助类实例
        ExcelImportHelper importHelper = new ExcelImportHelper();
        //读取sheet1信息,并保存在List中
        List<ExcelImportBaseBo> resultList = importHelper.importExcel("userImportConfiger", fileItem, 0);
        //将导入数据保存到数据库表
        this.saveImportData(resultList,redirectAttributes);
    }

    private void saveImportData(List<ExcelImportBaseBo> resultList,RedirectAttributes redirectAttributes) {
        UserImportVo userImportVo;
        Map<String,String> dataMap = new HashMap<String,String>();
        //文件数据校验
        if(CollUtil.isNotEmpty(resultList)){
            for(ExcelImportBaseBo bo : resultList){
                //基本校验导入数据的格式
                if( bo.hasErrors()){
                    redirectAttributes.addFlashAttribute("message", "第"+bo.getDataLine()+"行："+bo.getAllErrors());
                    return;
                }
                //业务校验有无相同工号数据
                userImportVo = (UserImportVo)bo;
                String data = dataMap.get(userImportVo.getUserNum());
                if(Objects.nonNull(data)){
                    redirectAttributes.addFlashAttribute("message",
                            "第【"+ userImportVo.getDataLine()+"】行身份证与第【"+data+"】行工号重复,请确认！\n工号：【"+ userImportVo.getUserNum()+"】");
                    return;
                }
                dataMap.put(userImportVo.getUserNum(), userImportVo.getDataLine()+"");
            }
        } else{
            redirectAttributes.addFlashAttribute("message", "excel中填写的信息为空,请填写相关数据!");
            return;
        }
        //数据入库
        for(int i = 0; i < resultList.size(); i++){
            //获取表格数据，保存到对象中
            userImportVo = (UserImportVo)resultList.get(i);
            User user = new User();
            BeanUtils.copyProperties(userImportVo, user);
            user.setPassword("123456");
        }
        redirectAttributes.addFlashAttribute("message","数据导入成功!");
    }
}
