package com.liu.hdfsUtils;

import java.io.Serializable;

public class HdfsFile implements Serializable{
	private static final long serialVersionUID = 7409226077281897763L;
	
	private String fileName;
	private boolean isFile;
	private String sizeString=" ";
	private String dateString;
	private String filePath;
	
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public boolean isFile() {
		return isFile;
	}
	public void setFile(boolean isFile) {
		this.isFile = isFile;
	}
	public String getSizeString() {
		return sizeString;
	}
	public void setSizeString(String sizeString) {
		this.sizeString = sizeString;
	}
	public String getDateString() {
		return dateString;
	}
	public void setDateString(String dateString) {
		this.dateString = dateString;
	}
	@Override
	public String toString() {
		return "HdfsFile [fileName=" + fileName + ", isFile=" + isFile + ", sizeString=" + sizeString + ", dateString="
				+ dateString + ", filePath=" + filePath + "]";
	}
}
