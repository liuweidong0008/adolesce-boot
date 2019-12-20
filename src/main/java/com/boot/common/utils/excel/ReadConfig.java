/*******************************************************************************
 * @(#)ReadConfig.java 2019年08月29日 10:42
 * Copyright 2019 明医众禾科技（北京）有限责任公司. All rights reserved.
 *******************************************************************************/
package com.boot.common.utils.excel;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * <b>Application name：</b> ReadConfig.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2019 明医众禾科技（北京）有限责任公司 版权所有。<br>
 * <b>Company：</b> 明医众禾科技（北京）有限责任公司 <br>
 * <b>Date：</b> 2019年08月29日 10:42 <br>
 * <b>@author：</b> <area href="mailto:limz@miyzh.com"> 李明哲 </area> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
public class ReadConfig {


    public Config readConfig(String configName) throws DocumentException {

        List<Sheet> sheetLst = Lists.newArrayList();
        SAXReader reader = new SAXReader();
        URL base = this.getClass().getResource("/");
        File file = new File(base.getFile().replace("%20", " ") + "/config/" + configName);
        Document document = reader.read(file);
        Element root = document.getRootElement();
        List<Node> sheets = root.selectNodes("sheets/sheet");
        for (Node sheet : sheets) {
            Sheet s = new Sheet();
            s.setName(((Element) sheet).attributeValue("name"));
            s.setCategoryType(((Element) sheet).attributeValue("categoryType"));
            List<Node> columns = sheet.selectNodes("columns/column");
            if (columns == null || columns.size() == 0) {
                continue;
            }
            List<Column> columnLst = Lists.newArrayList();
            for (Node column : columns) {
                Column c = new Column();
                c.setTitle(((Element) column).attributeValue("title"));
                c.setDataKey(((Element) column).attributeValue("dataKey"));
                c.setType(((Element) column).attributeValue("type"));
                c.setValue(((Element) column).attributeValue("value"));
                columnLst.add(c);

                List<Node> rules = column.selectNodes("rules/rule");
                if (rules == null || rules.size() == 0) {
                    continue;
                }
                List<Rule> ruleLst = Lists.newArrayList();

                for (Node rule : rules) {
                    Rule r = new Rule();
                    r.setName(((Element) rule).attributeValue("name"));
                    r.setValue(((Element) rule).attributeValue("value"));
                    ruleLst.add(r);
                }
                c.setRules(ruleLst);

            }
            s.setColumns(columnLst);
            sheetLst.add(s);
        }

        List<Node> dms = root.selectNodes("dms/dm");
        Map<String, List<Dm>> dmMap = Maps.newHashMap();
        if (dms != null) {
            for (Node dm : dms) {
                List<Node> lst = dm.selectNodes("item");
                String dmName = ((Element) dm).attributeValue("name");
                List<Dm> dmLst = Lists.newArrayList();
                for (Node item : lst) {
                    Dm dmObj = new Dm();
                    dmObj.setName(((Element) item).attributeValue("name"));
                    dmObj.setValue(((Element) item).attributeValue("value"));
                    dmLst.add(dmObj);
                }
                dmMap.put(dmName, dmLst);
            }
        }
        Config config = new Config();
        config.setSheetLst(sheetLst);
        config.setDmMap(dmMap);
        return config;
    }

    public static void main(String[] args) throws DocumentException {
		ReadConfig rc = new ReadConfig();
		Config lst = rc.readConfig("financeWithdrawRecordDetail.xml");
		System.out.println(lst);
    }
}