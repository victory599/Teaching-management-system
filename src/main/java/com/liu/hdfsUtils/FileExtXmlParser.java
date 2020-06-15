package com.liu.hdfsUtils;

import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class FileExtXmlParser {
	
	public Map<String, Set<String>> parseXml(String fileName) throws DocumentException{
		SAXReader sr = new SAXReader();
		
		// 查找本类文件的字节码位置，获取资源
		InputStream is = FileExtXmlParser.class.getClassLoader().getResourceAsStream(fileName);
		
		Document doc = sr.read(is);
		
		Element root = doc.getRootElement();
		Iterator<Element> its = root.elementIterator();
		
		Map<String, Set<String>> map = new HashMap<String, Set<String>>();
		
		while(its.hasNext()) {
			Element ele = its.next();
			
			if("item".equals(ele.getQName().getName())) {
				String fileType = ele.attribute("fileType").getValue();
				String extString = ele.getText();	// 后缀
				
				String[] exts = extString.split(",");
				
				Set<String> extSet = new HashSet<String>();
				for(String s : exts) {
					extSet.add(s);
				}
				map.put(fileType, extSet);
			}
		}
		return map;
	}
}