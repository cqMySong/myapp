package com.myapp.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;

import com.myapp.core.entity.UserInfo;
import com.myapp.core.uuid.SysObjectType;
import com.myapp.core.uuid.SysUuid;
import com.myapp.core.uuid.UuidUtils;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年6月2日 
 * @system:
 *
 *-----------MySong---------------
 */
public class FileToCRCUtil {
	public static String getCRC32(String fileUri) {
		CRC32 crc32 = new CRC32();
		FileInputStream fileinputstream = null;
		CheckedInputStream checkedinputstream = null;
		String crc = null;
		try {
			fileinputstream = new FileInputStream(new File(fileUri));
			checkedinputstream = new CheckedInputStream(fileinputstream, crc32);
			while (checkedinputstream.read() != -1) {
			}
			crc = Long.toHexString(crc32.getValue()).toUpperCase();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fileinputstream != null) {
				try {
					fileinputstream.close();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			}
			if (checkedinputstream != null) {
				try {
					checkedinputstream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return crc;
	}


	public static void main(String[] args) {
//		String uri = "D:\\ETFMY100PHFUNDBulletin20130325.txt";
//		System.out.println(getCRC32(uri));
		for(int i=0;i<10;i++){
			CRC32 crc32 = new CRC32();
			String cn = UserInfo.class.getName()+"_"+i;
			System.out.println(cn);
			crc32.update(cn.getBytes());
			System.out.println(crc32.getValue());
		}
		
//		String bostype = UuidUtils.toHexString(crc32.getValue(), 8);
//		System.out.println(bostype);
//		for(int i=0;i<10;i++){
//			SysUuid uid = SysUuid.create(SysObjectType.create(bostype));
//			System.out.println(uid.toString());
//			SysObjectType bostype_1 = SysUuid.getBOSObjectType(uid.toString(), true);
//			System.out.println(bostype_1.toString()+"  "+bostype_1.toInteger());
//		}
	}
}
