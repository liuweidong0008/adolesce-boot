/*******************************************************************************
 * @(#)InvoiceService.java 2019年11月19日 14:22
 * Copyright 2019 明医众禾科技（北京）有限责任公司. All rights reserved.
 *******************************************************************************/
package com.boot.excel;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.boot.common.utils.excel.ExcelHelper;
import com.boot.common.utils.excel.ExcelPoiUtil;
import com.boot.gdMemberRecord.entity.MemberRecord;
import com.boot.gdMemberRecord.service.IMemberRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Application name： InvoiceService.java
 * Application describing：财务发票服务层
 * Copyright： Copyright &copy; 2019 明医众禾科技（北京）有限责任公司 版权所有。
 * Company： 明医众禾科技（北京）有限责任公司
 * @Date： 2019年011月09日 13:55
 * @author： <area href="mailto:liuwd@miyzh.com"> LiuWeidong </area>
 * @version：V2.0.0
 */
@Service
public class ExcelExportService {
    @Resource
    private IMemberRecordService memberRecordServiceImpl;

    /**
     * 导出方式一
     * @param response
     */
    public void style1(HttpServletResponse response){
        try {
            List<MemberRecord> memberRecords = this.getUsers();

            String[] headerName1 = { "流水号", "姓名","银行卡号","银行名称","创建时间"};
            String[] headerKey1 = { "serialNo","clientName", "cardNo", "bankName", "transferTime"};

            String[] headerName2 = { "流水号", "姓名","创建时间"};
            String[] headerKey2 = { "serialNo","clientName", "transferTime"};

            ExcelExportParams exportParams = new ExcelExportParams(response);
            exportParams.addSheet(memberRecords,headerName1,headerKey1,"sheet1")
                        .addSheet(memberRecords,headerName2,headerKey2,"sheet2");
            exportParams.setFileName("交易流水");
            ExcelHelper.export(exportParams);
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    /**
     * 导出方式二
     * @param response
     */
    public void style2(HttpServletResponse response){
        List<MemberRecord> memberRecords = this.getUsers();
        try {
            ExcelPoiUtil excelUtil = new ExcelPoiUtil("线上应收结算订单","memberRecord.xml");
            excelUtil.addDataList(memberRecords);
            excelUtil.addDataList(memberRecords);
            excelUtil.export(response);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    private List<MemberRecord> getUsers() {
        Wrapper<MemberRecord> recordQueryWrapper = new EntityWrapper<MemberRecord>();
        List<MemberRecord> records = this.memberRecordServiceImpl.selectList(recordQueryWrapper);
        return records;
    }
}
