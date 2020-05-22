package com.liu.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*
配置数据源和Druid的监控统计功能
 */
@Configuration
public class DruidConfiguration {
    // 获取容器中数据源配置信息，创建数据源对象
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource getDatasource(){
        return new DruidDataSource();
    }

    // springboot配置Servlet，加入druid页面配置
    @Bean
    public ServletRegistrationBean<StatViewServlet> druidServlet(){
        // druid监控，StatViewServlet：提供监控信息展示的html页面、提供监控信息的JSON API
        ServletRegistrationBean<StatViewServlet> bean = new ServletRegistrationBean<>(new StatViewServlet(),"/druid/*");

        Map<String, String> initParams = new HashMap<>();

        // 用户名和密码
        initParams.put("loginUsername", "root");
        initParams.put("loginPassword", "a");
        // 设置ip白名单
        initParams.put("allow", "");
        // 设置ip黑名单。注意：deny优先级高于allow，如果配置了同一个ip，以deny配置为主
        initParams.put("deny", "192.168.10.125");

        bean.setInitParameters(initParams);
        return bean;
    }

    // springboot配置Filter，实现druid监控操作
    @Bean
    public FilterRegistrationBean<WebStatFilter> webStatFilter(){
        FilterRegistrationBean<WebStatFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new WebStatFilter());

        Map<String,String> initParams = new HashMap<>();

        // 排除名单
        initParams.put("exclusions", "*.js,*.css,/druid/*");
        bean.setInitParameters(initParams);

        // 对所有请求进行监控处理
        bean.setUrlPatterns(Arrays.asList("/*"));
        return bean;
    }
}
