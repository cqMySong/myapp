package com.myapp.core.uuid;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.CRC32;

import com.myapp.core.entity.UserInfo;
import com.myapp.core.util.BaseUtil;

public class UuidUtils {
	private static final char[] hexDigits = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	public static String getEntityType(String strName){
		if(BaseUtil.isEmpty(strName)) return null;
		return toHexString(getStringLong(strName),8);
	}
	public static long getStringLong(String strName){
		if(BaseUtil.isEmpty(strName)) return 0l;
		CRC32 crc32 = new CRC32();
		crc32.update(strName.getBytes());
		return crc32.getValue();
	}
	
	public static String toHexString(long x, int chars) {
		char[] buf = new char[chars];
		int charPos = chars;
		while (true) {
			charPos--;
			if (charPos < 0)
				break;
			buf[charPos] = hexDigits[((int) (x & 0xF))];
			x >>>= 4;
		}
		return new String(buf);
	}

	public static String byteArrayToString(byte[] ba) {
		int length = ba.length;
		char[] buf = new char[length * 2];
		int i = 0;
		for (int j = 0; i < length;) {
			int k = ba[(i++)];
			buf[(j++)] = HEX_DIGITS[(k >>> 4 & 0xF)];
			buf[(j++)] = HEX_DIGITS[(k & 0xF)];
		}
		return new String(buf);
	}

	public static String headCharUpperCase(String str) {
		if ((str == null) || ("".equals(str)))
			return str;
		String headChar = str.substring(0, 1).toUpperCase();
		if (str.length() == 1)
			return headChar;
		return headChar + str.substring(1, str.length());
	}
	public static String headCharLowerCase(String str) {
		if ((str == null) || ("".equals(str)))
			return str;
		String headChar = str.substring(0, 1).toLowerCase();
		if (str.length() == 1)
			return headChar;
		return headChar + str.substring(1, str.length());
	}
	public static String objToString(Object obj) {
		try {
			ByteArrayOutputStream byteOS = new ByteArrayOutputStream(2048);
			ObjectOutputStream objectOS = new ObjectOutputStream(byteOS);
			objectOS.writeObject(obj);
			objectOS.flush();
			return Base64Encoder.byteArrayToBase64(byteOS.toByteArray());
		} catch (Exception ex) {
		}
		return null;
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
