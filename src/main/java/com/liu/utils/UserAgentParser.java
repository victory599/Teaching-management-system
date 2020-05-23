package com.liu.utils;

import lombok.Data;

// 解析浏览器 User-Agent，判断是pc访问还是mobil访问
@Data
public class UserAgentParser {
    private String url;
    private String platform;

    public UserAgentParser(String url) {
        this.url = url;
        if (this.url.indexOf("Android") != -1 || this.url.indexOf("iPhone") != -1) {
            this.setPlatform("mobile");
        } else {
            this.setPlatform("pc");
        }
    }
}
