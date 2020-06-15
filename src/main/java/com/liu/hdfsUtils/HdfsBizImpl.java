package com.liu.hdfsUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.hadoop.fs.FileContext;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;

public class HdfsBizImpl {
	
	/**
	 * 	给一个路径，到hdfs中查询这个路径下所有文件
	 * @param path
	 * @return
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public List<HdfsFile> getFileList(String path) throws IOException {
		
		Path root = new Path(path);
		// 当前路径下的文件集合
		FileStatus[] fs = HdfsDao.getFileSystem().listStatus(root);
		
		List<HdfsFile> list = new ArrayList<>();
		for(FileStatus lfs : fs) {
			// 封装文件信息
			HdfsFile hf = new HdfsFile();
			
			Path p = lfs.getPath();
			String fileName = p.getName();  // 目录下所有文件名
			hf.setFileName(fileName);
			
			boolean b = lfs.isFile();   // 判断是否文件（也可判断是否目录）
			hf.setFile(b);
			
			// 文件大小
			String sizeString = null;
			if(b) {
				Long len = lfs.getLen();
				sizeString = formatSize(len);
				hf.setSizeString(sizeString);
			}
			
			// 格式化时间
			long mt = lfs.getModificationTime();
			Date d = new Date(mt);
			DateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
			String dateString = df.format(d);
			hf.setDateString(dateString);
			
			hf.setFilePath(lfs.getPath().toString());
			
			list.add(hf);
		}
		return list;
	}
	
	/**
	 * 	规范文件大小的单位
	 * @param len
	 * @return
	 */
	private String formatSize(long len) {
		String result = "";
		if(len/1024/1024/1024/1024 > 0) {
			result = len/1024/1024/1024/1024 + "T";
		}else if(len/1024/1024/1024 > 0) {
			result = len/1024/1024/1024 + "G";
		}else if(len/1024/1024 > 0) {
			result = len/1024/1024 + "M";
		}else if(len/1024 > 0) {
			result = len/1024 + "K";
		}else {
			result = len + "B";
		}
		return result;
	}

	/**
	 * 	更改文件名
	 * @param oldName
	 * @param newName
	 * @param currentPath
	 * @return
	 * @throws IOException
	 */
	public boolean rename(String oldName, String newName, String currentPath) throws IOException {
		Path src = new Path(currentPath + "/" + oldName);
		Path dst = new Path(currentPath + "/" + newName);
		
		boolean result = HdfsDao.getFileSystem().rename(src, dst);
		return result;
	}
	
	/**
	 * 	删除文件
	 * @param oldName
	 * @param currentPath
	 * @return 
	 * @throws IOException 
	 */
	public boolean delFile(String oldName, String currentPath) throws IOException {
		Path f = new Path(currentPath + "/" + oldName);
		
		boolean result = HdfsDao.getFileSystem().delete(f, true);
		return result;
	}

	/**
	 * 	新建文件
	 * @param path
	 * @param newName
	 * @return
	 * @throws IOException
	 */
	public boolean mkdir(String path, String newName) throws IOException {
		// 初始页上path="/"，判断一下进行处理
		if("/".equals(path)) {
			path = "";
		}
		Path f = new Path(path + "/" + newName);
		return HdfsDao.getFileSystem().mkdirs(f);
	}

	/**
	 * 文件上传
	 * @param map
	 * @throws IOException
	 */
	public boolean uploadFile(Map<String, String> map) {
		String realPath = map.get("realPath");
		String currentPath = map.get("currentPath");
		String fileName = map.get("fileName");
		
		if(!"/".equals(currentPath)) {
			currentPath += "/";
		}
		
		// 1.调用fileSystem上传文件到hadoop
		Path src = new Path(realPath);
		Path dst = new Path(currentPath + fileName);
		try {
			HdfsDao.getFileSystem().copyFromLocalFile(src, dst);
			// 2.删除当前本机上的文件
			File f = new File(realPath);
			if(f.exists()) {
				f.delete();
			}
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 移动文件
	 * @param currentPath
	 * @param oldName
	 * @param newName
	 * @throws IOException
	 */
	public void moveTo(String currentPath, String oldName, String newName) throws IOException {
		if(!"/".equals(currentPath)) {
			currentPath = currentPath + "/" + oldName;
		}else {
			currentPath += oldName;
		}
		
		Path src = new Path(currentPath);
		
		String t = "";
		if(newName.equals("/")) {
			t = newName + oldName;
		}else {
			t = newName + "/" + oldName;
		}
		
		Path dst = new Path(t);
		
		FileContext.getFileContext().util().copy(src, dst, true, true);  // copy(src, dst, deleteSource, overwrite)
	}
	
	/**
	 * 复制文件
	 * @param currentPath
	 * @param oldName
	 * @param newName
	 * @throws IOException
	 */
	public void copyTo(String currentPath, String oldName, String newName) throws IOException {
		if(!"/".equals(currentPath)) {
			currentPath = currentPath + "/" + oldName;
		}else {
			currentPath += oldName;
		}
		
		Path src = new Path(currentPath);
		
		String t = "";
		if(newName.equals("/")) {
			t = newName + oldName;
		}else {
			t = newName + "/" + oldName;
		}
		
		Path dst = new Path(t);
		
		FileContext.getFileContext().util().copy(src, dst, false, true);  // copy(src, dst, deleteSource, overwrite)
	}
	
	/**
	 * 筛选文件
	 * @param type
	 * @param currentPath 
	 * @param map 
	 * @return
	 * @throws FileNotFoundException
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	public List<HdfsFile> select(String type, String currentPath, Map<String, Set<String>> map) throws FileNotFoundException, IllegalArgumentException, IOException{
		RemoteIterator<LocatedFileStatus> ri = HdfsDao.getFileSystem().listFiles(new Path(currentPath), true);
		List<HdfsFile> list = new ArrayList<>();
		
		while(ri.hasNext()) {
			LocatedFileStatus lfs = ri.next();
			String fileName = lfs.getPath().getName();
			
			if(map != null) {
				if("0".equals(type)) {
					addList(list, lfs);
				}else {
					Set<String> set = map.get(type);   // 对应type文件的后缀名
					for(String ext : set) {
						if(fileName.endsWith(ext)) {
							addList(list, lfs);
						}
					}
				}
			}
		}
		return list;
	}

	/**
	 * List添加HdfsFile对象
	 * @param list
	 * @param lfs
	 */
	private void addList(List<HdfsFile> list, LocatedFileStatus lfs) {
		HdfsFile hf = new HdfsFile();
		
		String fileName = lfs.getPath().getName();
		hf.setFileName(fileName);
		
		boolean b = lfs.isFile();
		hf.setFile(b);
		
		String sizeString = null;
		if(b) {
			long len = lfs.getLen();
			sizeString = formatSize(len);
			hf.setSizeString(sizeString);
		}
		
		long mt = lfs.getModificationTime();
		Date d = new Date(mt);
		DateFormat df = new SimpleDateFormat("yyyy年MM月dd日  HH:mm:ss");
		String dateString = df.format(d);
		hf.setDateString(dateString);
		
		hf.setFilePath(lfs.getPath().toString());
		
		list.add(hf);
	}

	/**
	 * 	下载文件
	 * @param currentPath
	 * @param fileName
	 * @param local
	 * @return
	 * @throws Exception
	 */
	public String downloadFile(String currentPath, String fileName, String local) throws Exception {
		String src = "";
		if(currentPath.equals("/")) {
			src = currentPath + fileName;
		}else {
			src = currentPath + "/" + fileName;
		}
		
		// 在临时文件目录下保存该文件
		String dst = local + fileName;
		
		Path p = new Path(src);
		// 如果不是文件则报错（暂不支持目录下载）
		if(!HdfsDao.getFileSystem().isFile(p)) {
			throw new Exception(src + "is not a valid file");
		}
		
		/*
		 * 	注意：
		 * 	如果调用copyToLocalFile(Path src, Path dst)，可能会报空指针异常
		 * 	可能是windows的兼容性问题，则调用4参数的重载方法
		 */
		HdfsDao.getFileSystem().copyToLocalFile(false, p, new Path(dst), true);
		return dst;
	}
}
