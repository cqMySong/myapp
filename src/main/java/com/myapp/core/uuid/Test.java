package com.myapp.core.uuid;

import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;

public class Test {
	public static String Bytes2HexString(byte[] b) {
		String ret = "";
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			ret += hex.toUpperCase();
		}
		return ret;
	}

	public static byte uniteBytes(byte src0, byte src1) {
		byte _b0 = Byte.decode("0x" + new String(new byte[] { src0 }))
				.byteValue();
		_b0 = (byte) (_b0 << 4);
		byte _b1 = Byte.decode("0x" + new String(new byte[] { src1 }))
				.byteValue();
		byte ret = (byte) (_b0 ^ _b1);
		return ret;
	}

	public static byte[] HexString2Bytes(String src) {
		byte[] ret = new byte[8];
		byte[] tmp = src.getBytes();
		for (int i = 0; i < 8; i++) {
			ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
		}
		return ret;
	}

	public static void main(String[] args) {
		String table = "user";
//		System.out.println(Bytes2HexString(HexString2Bytes(table)));
		// String boseType =BOSObjectType.create(table);// "3FAF8D12";
		try {
//			System.out.println(Long.parseLong("USER",16));
			for(int i=10000;i<10100;i++){
				String type = UuidUtils.toHexString(i, 8).toUpperCase();
				System.out.println(type);//00034ACC
				System.out.println(SysUuid.create(SysObjectType.create(type)));
			}
			
//			System.out.println(Uuid.randomUUID());
			SysUuid uid = SysUuid.create(SysObjectType.create("05F5E15F"));
			System.out.println(uid);
			String id = "jkr6NVxvSb2A4GRtlqaTzD+vjRI=";
			System.out.println(SysUuid.read(uid.toString()).getType());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
