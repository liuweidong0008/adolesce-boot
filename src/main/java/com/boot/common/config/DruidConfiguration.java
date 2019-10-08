package com.boot.common.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.boot.common.properties.DruidDataSourceProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@MapperScan({ "com.boot.**.mapper*" })
@EnableConfigurationProperties({ DruidDataSourceProperties.class })
public class DruidConfiguration {

	@Autowired
	private DruidDataSourceProperties properties;

	@Bean
	@ConditionalOnMissingBean
	public DataSource druidDataSource() {
		DruidDataSource druidDataSource = new DruidDataSource();
		druidDataSource.setDriverClassName(this.properties.getDriverClassName());
		druidDataSource.setUrl(this.properties.getUrl());
		druidDataSource.setUsername(this.properties.getUsername());
		druidDataSource.setPassword(this.properties.getPassword());
		druidDataSource.setInitialSize(this.properties.getInitialSize());
		druidDataSource.setMinIdle(this.properties.getMinIdle());
		druidDataSource.setMaxActive(this.properties.getMaxActive());
		druidDataSource.setMaxWait(this.properties.getMaxWait());
		druidDataSource.setTimeBetweenEvictionRunsMillis(this.properties.getTimeBetweenEvictionRunsMillis());
		druidDataSource.setMinEvictableIdleTimeMillis(this.properties.getMinEvictableIdleTimeMillis());
		druidDataSource.setValidationQuery(this.properties.getValidationQuery());
		druidDataSource.setTestWhileIdle(this.properties.isTestWhileIdle());
		druidDataSource.setTestOnBorrow(this.properties.isTestOnBorrow());
		druidDataSource.setTestOnReturn(this.properties.isTestOnReturn());
		druidDataSource.setPoolPreparedStatements(this.properties.isPoolPreparedStatements());
		druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(
				this.properties.getMaxPoolPreparedStatementPerConnectionSize());
		druidDataSource.setConnectionProperties(this.properties.getConnectionProperties());
		try {
			druidDataSource.setFilters(this.properties.getFilters());
			druidDataSource.init();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return druidDataSource;
	}

	// @Bean
	// @ConditionalOnMissingBean //当bean不存在时创建 @ConditionalOnxxx系列
	// public ServletRegistrationBean druidServlet() {
	//
	// ServletRegistrationBean servletRegistrationBean = new
	// ServletRegistrationBean(new StatViewServlet(), "/druid/*");
	//
	// //添加初始化参数：initParams
	//
	// //白名单：
	// //servletRegistrationBean.addInitParameter("allow","127.0.0.1");
	// //IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not permitted
	// to view this page.
	// //servletRegistrationBean.addInitParameter("deny","192.168.1.73");
	// //登录查看信息的账号密码.
	// servletRegistrationBean.addInitParameter("loginUsername", "admin");
	// servletRegistrationBean.addInitParameter("loginPassword", "admin");
	// //是否能够重置数据.
	// servletRegistrationBean.addInitParameter("resetEnable", "true");
	// return servletRegistrationBean;
	//
	// }
	//
	// @Bean
	// @ConditionalOnMissingBean
	// public FilterRegistrationBean filterRegistrationBean() {
	// FilterRegistrationBean filterRegistrationBean = new
	// FilterRegistrationBean();
	// filterRegistrationBean.setFilter(new WebStatFilter());
	// filterRegistrationBean.addUrlPatterns("/*");
	// filterRegistrationBean.addInitParameter("exclusions",
	// "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
	// return filterRegistrationBean;
	// }
}