package com.myapp.core.license;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.SecureRandom;
import java.util.Date;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.myapp.core.entity.UserInfo;
import com.myapp.core.util.DateUtil;
import com.myapp.entity.ec.basedata.ProjectInfo;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2018年1月23日 
 * @system:
 *
 *-----------MySong---------------
 */
public class AESUtil {
	/**
	 * 加密
	 * @param byteContent 需要加密的内容
	 * @param password 加密密码
	 * @return
	 * @throws Exception 
	 */
	public static byte[] encrypt(byte[] byteContent, String password) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		kgen.init(128, new SecureRandom(password.getBytes()));
		SecretKey secretKey = kgen.generateKey();
		byte[] enCodeFormat = secretKey.getEncoded();
		SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
		Cipher cipher = Cipher.getInstance("AES");// 创建密码器
		cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
		byte[] result = cipher.doFinal(byteContent);
		return result; // 加密
	}
    /**解密
     * @param content  待解密内容
     * @param password 解密密钥
     * @return
     * @throws Exception 
     */
	public static byte[] decrypt(byte[] content, String password)
			throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		kgen.init(128, new SecureRandom(password.getBytes()));
		SecretKey secretKey = kgen.generateKey();
		byte[] enCodeFormat = secretKey.getEncoded();
		SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
		Cipher cipher = Cipher.getInstance("AES");// 创建密码器
		cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
		byte[] result = cipher.doFinal(content);
		return result; // 加密
	}
	
	 public static void main(String[] args) throws Exception {
		LicenseInfo ltInfo = new LicenseInfo("重庆中科控股公司", "ZKKG");
		ltInfo.setBegDate(new Date());
		ltInfo.setEndDate(DateUtil.addDay(new Date(), 30));
		ltInfo.setType(LicenseType.RELEASE);
		ltInfo.addModels(new ModelItemInfo(UserInfo.class.getName(), "用户", 5));
		ltInfo.addModels(new ModelItemInfo(ProjectInfo.class.getName(), "工程项目",5));

		System.out.println(ltInfo.toJosnString());
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		ObjectOutputStream oo = new ObjectOutputStream(bo);
		oo.writeObject(ltInfo);
		byte[] bytes = bo.toByteArray();
		bo.close();
		oo.close();
		String password = "12142425";
		byte[] encryData = encrypt(bytes, password);

		byte[] decryptData = decrypt(encryData, password);

		ByteArrayInputStream bi = new ByteArrayInputStream(decryptData);
		ObjectInputStream oi = new ObjectInputStream(bi);
		Object obj = oi.readObject();
		bi.close();
		oi.close();

		System.out.println(obj.toString());
	  
	 }

}
