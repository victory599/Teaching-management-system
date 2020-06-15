package com.liu.hdfsUtils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.PageContext;

import com.jspsmart.upload.File;
import com.jspsmart.upload.Files;
import com.jspsmart.upload.SmartUpload;

public class FileUploadUtil {
	
	private long SINGLESIZE = 10 * 1024 * 1024 * 1024 * 1024;		// 允许上传的单个文件大小
	private String PATH = "img\\";		// 保存上传文件的临时目录

	// 将需要上传的文件先保存到本地临时目录下：..\tomcat\webapps\PATH
	public Map<String, String> uploadFile(PageContext pContext) throws Exception{
		Map<String, String> result = new HashMap<String, String>();
		
		SmartUpload su = new SmartUpload();
		
		su.initialize(pContext);	// 初始化
		su.setTotalMaxFileSize(1*SINGLESIZE);
		su.setMaxFileSize(SINGLESIZE);
		su.setCharset("utf8");	// 设置编码
		
		su.upload();	// 开始处理上传，由smartUpload处理请求中的参数
		
		// 在hdfs上保存该文件的路径
		String currentPath = su.getRequest().getParameter("currentPath");
		result.put("currentPath", currentPath);
		
		// 本地存储在tomcat上
		String tomcatroot = pContext.getRequest().getRealPath("/");
		java.io.File tomcatFile = new java.io.File(tomcatroot);
		tomcatroot = tomcatFile.getParent();
		
		Files files = su.getFiles();	// com.jspsmart.upload.Files
		if(files != null && files.getCount()>0) {
			for(int i = 0; i < files.getCount(); i++) {
				File file = files.getFile(i);	// com.jspsmart.upload.File
				
				String fieldName = file.getFieldName();		// 表单中文件字段名
				String fileName = file.getFileName();		// 文件名，如：1.png
				
				String newFileName = PATH + fileName;
				
				// 保存文件（这里最终的保存路径：realPath）
				file.saveAs(tomcatroot + "\\" + newFileName, SmartUpload.SAVE_PHYSICAL);
				
				result.put(fieldName, newFileName);
				result.put("realPath", tomcatroot + "\\" + newFileName);
				result.put("fileName", file.getFileName());
			}
		}
		return result;
	}
}
