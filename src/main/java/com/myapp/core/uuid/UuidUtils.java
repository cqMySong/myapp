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

import com.myapp.core.util.BaseUtil;

public class UuidUtils {
	private static final char[] hexDigits = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	public static String getEntityType(String strName){
		if(BaseUtil.isEmpty(strName)) return null;
		return toHexString(byteArrayToLong(strName.getBytes()),8);
	}
	
	public static long byteArrayToLong(byte[] byteArray) {
		byte[] a = new byte[8];
		int i = a.length - 1, j = byteArray.length - 1;
		for (; i >= 0; i--, j--) {// 从b的尾部(即int值的低位)开始copy数据
			if (j >= 0){
				a[i] = byteArray[j];	
			}else{
				a[i] = 0;// 如果b.length不足4,则将高位补0
			}
		}
		// 注意此处和byte数组转换成int的区别在于，下面的转换中要将先将数组中的元素转换成long型再做移位操作，
		// 若直接做位移操作将得不到正确结果，因为Java默认操作数字时，若不加声明会将数字作为int型来对待，此处必须注意。
		long v0 = (long) (a[0] & 0xff) << 56;// &0xff将byte值无差异转成int,避免Java自动类型提升后,会保留高位的符号位
		long v1 = (long) (a[1] & 0xff) << 48;
		long v2 = (long) (a[2] & 0xff) << 40;
		long v3 = (long) (a[3] & 0xff) << 32;
		long v4 = (long) (a[4] & 0xff) << 24;
		long v5 = (long) (a[5] & 0xff) << 16;
		long v6 = (long) (a[6] & 0xff) << 8;
		long v7 = (long) (a[7] & 0xff);
		return v0 + v1 + v2 + v3 + v4 + v5 + v6 + v7;
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
}
