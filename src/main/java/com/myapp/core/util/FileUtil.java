package com.myapp.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import org.apache.commons.io.IOUtils;


/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年6月3日 
 * @system:
 * 文件转换处理工具
 *-----------MySong---------------
 */
public class FileUtil {
	
	public static byte[] readFileToByteArray(File file) throws IOException{
		byte[] fileByts = null;
		InputStream in = null;
		try{
			in = new FileInputStream(file);
	        fileByts = IOUtils.toByteArray(in);
	        IOUtils.closeQuietly(in);
		}catch(IOException e){
			if(in!=null) IOUtils.closeQuietly(in);
			throw new IOException(e.getMessage());
		}
        return fileByts;
	}
	
	public static byte[] file2Byte(File file){
		byte b[] = null;
		try{
           FileInputStream fis = null;
           fis = new FileInputStream(file);
           FileChannel fc = fis.getChannel();
           long size = fc.size();
           b = new byte[(new Long(size)).intValue()];
           MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_ONLY, 0L, size);
           mbb.get(b);
           mbb.clear();
           fc.close();
           fis.close();
       }catch(IOException e){
           e.printStackTrace();
       }
       return b;
	}
}
