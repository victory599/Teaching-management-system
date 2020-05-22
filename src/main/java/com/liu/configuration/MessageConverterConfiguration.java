package com.liu.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;

import java.nio.charset.Charset;

@Configuration
public class MessageConverterConfiguration {
    // 字符串消息转换器，重新设置编码格式
    @Bean
    public HttpMessageConverter<String> httpMessageConverter() {
        return new StringHttpMessageConverter(Charset.forName("UTF-8"));
    }
}