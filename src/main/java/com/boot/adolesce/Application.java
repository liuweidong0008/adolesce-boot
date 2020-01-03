package com.boot.adolesce;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;
import java.nio.charset.Charset;

/**
 * 启动类
 */
//@ComponentScan(basePackages = {"com.yideb", "com.myzh", "com.miyzh"})
//@ImportResource(locations="classpath*:context/*.xml")

//复合注解，包括@ComponentScan、@SpringBootConfiguration、@EnableAutoConfiguration。
//@ComponentScan:扫描当前包及其子包下被@Component、@Controller、@Service、@Repository注解标记的类并纳入到spring容器中进行管理。是以前的<context:component-scan>
//@SpringBootConfiguration:继承自@Configuration，二者功能也一致，标注当前类是配置类，并会将当前类内声明的一个或多个以@Bean注解标记的方法的实例纳入到srping容器中，并且实例名就是方法名。
//@EnableAutoConfiguration:意思就是Springboot根据你添加的jar包来配置你项目的默认配置
@SpringBootApplication
//Servlet、Filter、Listener可以直接通过 @WebServlet、@WebFilter、@WebListener 注解自动注册，无需其他代码。
@ServletComponentScan
//开启定时
@EnableScheduling
public class Application extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * springboot项目，若打包成war包，使用外置的tomcat启动 需：
     * 1、需要继承 org.springframework.boot.context.web.SpringBootServletInitializer类
     * 2、然后重写configure(SpringApplicationBuilder application)方法1
     *
     * @param builder
     * @return
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Application.class);
    }

    /**
     * Java代码形式定义文件上传大小限制
     *
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //当以下配置与yml中server:multipart配置共存时，使用以下java配置
        // 单个文件数据大小
        // 后检查此条件
        DataSize size = DataSize.ofMegabytes(2);
        factory.setMaxFileSize(size);
        /// 总上传文件数据大小
        //先检查此条件
        size = DataSize.ofMegabytes(10);
        factory.setMaxRequestSize(size);
        return factory.createMultipartConfig();
    }

    /**
     * Tomcat配置
     */
    @Bean
    public TomcatServletWebServerFactory tomcatEmbedded() {
        TomcatServletWebServerFactory tomcatFactory = new TomcatServletWebServerFactory();
        //tomcat端口,当与yml文件中server:port 配置共存时，使用yml中配置
        tomcatFactory.setPort(8082);
        //设置编码为UTF-8
        tomcatFactory.setUriEncoding(Charset.forName("UTF-8"));
        //Tomcat large file upload connection reset 问题解决
        //tomcatFactory.addConnectorCustomizers(new MyTomcatConnectorCustomizer());
        tomcatFactory.addConnectorCustomizers((TomcatConnectorCustomizer) connector -> {
            if ((connector.getProtocolHandler() instanceof AbstractHttp11Protocol<?>)) {
                ((AbstractHttp11Protocol<?>) connector.getProtocolHandler()).setMaxSwallowSize(-1);
            }
        });
        return tomcatFactory;
    }

    class MyTomcatConnectorCustomizer implements TomcatConnectorCustomizer {
        @Override
        public void customize(Connector connector) {
            Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
            //设置最大连接数
            protocol.setMaxConnections(2000);
            //设置最大线程数
            protocol.setMaxThreads(2000);
            protocol.setConnectionTimeout(30000);
            //解决Tomcat large file upload connection reset问题
            protocol.setMaxSwallowSize(-1);
        }
    }
}