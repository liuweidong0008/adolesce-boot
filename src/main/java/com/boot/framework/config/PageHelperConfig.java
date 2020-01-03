package com.boot.framework.config;

import com.github.pagehelper.PageHelper;
import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * 配置mybatis的分页插件pageHelper
 *
 */
@Configuration
public class PageHelperConfig {
	@Bean
	public PageHelper pageHelper() {
		PageHelper pageHelper = new PageHelper();
		Properties properties = new Properties();
		properties.setProperty("dialect", "mysql"); //配置mysql数据库的方言
		properties.setProperty("offsetAsPageNum", "true");
		properties.setProperty("rowBoundsWithCount", "true");
		properties.setProperty("pageSizeZero", "true");
		properties.setProperty("reasonable", "true");
		properties.setProperty("supportMethodsArguments", "false");
		properties.setProperty("returnPageInfo", "none");
		pageHelper.setProperties(properties);
		return pageHelper;
	}
}