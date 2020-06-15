package com.liu.hdfsUtils;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;

public class HdfsDao {
	// 配置信息
	private static Configuration conf;
	
	static {
		System.getProperty("HADOOP_USER_NAME", "root");
		conf = new Configuration();
	}
	
	// 生成一个文件系统客户端操作对象
	public static FileSystem getFileSystem() throws IOException {
		return FileSystem.get(conf);
	}
}
