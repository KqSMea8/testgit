package com.hanweb.jmp.util;

import java.io.File;

import com.hanweb.common.util.SpringUtil;
import com.hanweb.common.util.file.IFileUtil;
import com.hanweb.common.util.file.LocalFileUtil;

public class HadoopUtil {

	/**
	 * 获得信息表名
	 * 
	 * @param siteid 网站Id
	 * @return String
	 */
	public static void fileUpload(File sourceFile, String desPath){
		IFileUtil fileUtil = (IFileUtil )SpringUtil.getBean("FileUtil"); 
		if(fileUtil.getImplClazz() != LocalFileUtil.class) {
        	if(desPath!=null && desPath.startsWith("/")){
        		desPath=desPath.substring(1);
        	}
			fileUtil.moveFile(sourceFile, fileUtil.getAbsolutePath(desPath));
		}
	}
}
