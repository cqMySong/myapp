package com.myapp.core.license;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import com.myapp.core.entity.UserInfo;
import com.myapp.core.util.DateUtil;
import com.myapp.entity.ec.basedata.ProjectInfo;

/**
 * -----------MySong--------------- 
 * ©MySong基础框架搭建
 * @author mySong @date 2018年1月23日
 * @system:
 *  license接口实现类
 *  
 *-----------MySong---------------
 */
public class LicenseHelper  {
	public static String License_privateKey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAMW+5JC1E8L5" +
    		"+RKFwzAMdz+beHCnrOskcFGVrrBmC7td9Pcizrkx1CdkW2a0zF2yW2NVLiZpHowJmfxnVoTM1GAialfnch" +
    		"PesgL6yeYQeT7DKhhGNfAhF6zxDo5TV9+Vx9s0nsVlVt2/oYiSeEzQ+v7HxpM+k6y6LU+9V4S9RkXXAgMBAAEC" +
    		"gYEArMxsRTvHAgmYqqPy9ejex9me/Kq5g117PP/VAFacB+ZJ6zAif1jYsHeVG4IAWBxEmd/UjUIs" +
    		"rGizhAn1Y+Tt6265QrKhMMu99eDv+lxZ6eLB3Jh6znCfZW02o/aQ2Fufj6ZgOHuYavoXY/qCFAsg" +
    		"7Gox968QHVaa6ieNJ0XeAXECQQDljHh7wRQo1bq7AeYGNYdGqpCxYGuIG/SXk0II/naINC2t3Tkt" +
    		"p8+/AMCSjHlGMKX6kQ4cFNLg4+VlndBLFTD/AkEA3IhBIRpwPeauc0KLA9lBSLnplUHFgaeGY3xI" +
    		"/ZR/NZpQlEddwXsFEaBWoO9i9gCRMRjQO5rqyZBu95kq5UGTKQJBALvQwUvQYKRAq7YTCZgO+A4T" +
    		"xKu8zn7hELFsZV1ihq1OtAlLtlrqsQoAnY/Kq/eIGmX3hWnoh9pF3Q3Vn/Wu8xUCQGuD5jTMXfJf" +
    		"16cLxxh+P8zEDrQc17ypPuSbwD4YupgYfrZ7wKLGZic8w188tkN27VJBZavZkCPqzZ7axsX0XVkC" +
    		"QQCefHNoQKQQDm0fMfwlZVfxGEdLz84ouwHw9maiYK8yA/z9NPSSKu1aT+GyQ/wYXaV+pV3WEufEUwdNDd4TbApY";

	public static String License_publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDFvuSQtRPC+fkShcMwDHc/m" +
		"3hwp6zrJHBRla6wZgu7XfT3Is65MdQnZFtmtMxdsltjVS4maR6MCZn8Z1aEzNRgImpX53IT3rIC+snmE" +
		"Hk+wyoYRjXwIRes8Q6OU1fflcfbNJ7FZVbdv6GIknhM0Pr+x8aTPpOsui1PvVeEvUZF1wIDAQAB";
	
	public static LicenseInfo initLicenseData(String machineCode){
		LicenseInfo ltInfo = new LicenseInfo("重庆中科控股公司", "ZKKG");
		ltInfo.setBegDate(DateUtil.addDay(new Date(), -1));
		ltInfo.setEndDate(DateUtil.addDay(new Date(), 15));
		ltInfo.setType(LicenseType.RELEASE);
		ltInfo.setAppName("现场施工项目");
		ltInfo.setMachineCode(machineCode);
		ltInfo.addModels(new ModelItemInfo(UserInfo.class.getName(), "用户", 5,"系统允许建立最大的用户数"));
		ltInfo.addModels(new ModelItemInfo(ProjectInfo.class.getName(), "工程项目", 5,"系统允许建立的最大工程项目数"));
		return ltInfo;
	}
	
	public static byte[] encryLicenseData(LicenseInfo lcInfo) throws Exception{
		ByteArrayOutputStream bo = new ByteArrayOutputStream();  
		ObjectOutputStream oo = new ObjectOutputStream(bo);  
		oo.writeObject(lcInfo);  
		byte[] encryData = bo.toByteArray();  
		bo.close();  
		oo.close();  
		return AESUtil.encrypt(encryData, lcInfo.getMachineCode());
	}
	
	public static void genLicenseFile(String filePath,String machineCode) throws Exception{
		byte[] encryData = encryLicenseData(initLicenseData(machineCode));
		if(encryData!=null){
			byte[] encodedData = RSAUtil.encryptByPrivateKey(encryData, License_privateKey);
			Base64Utils.byteArrayToFile(encodedData, filePath);
		}
	}
	
	public static LicenseInfo getLicenseData(byte[] decodedData,String password) throws Exception{
		try{
			byte[] decodedLicenseData = AESUtil.decrypt(decodedData, password);
			ByteArrayInputStream bi = new ByteArrayInputStream(decodedLicenseData);
			ObjectInputStream oi = new ObjectInputStream(bi);
			Object licObj = oi.readObject();
		   	bi.close();
		    oi.close();
		    if(licObj!=null&&licObj instanceof LicenseInfo){
		    	return (LicenseInfo)licObj;
		    }
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("license数据解密失败，非法的许可!");
		}
		return null;
	}
	
	public static LicenseInfo decryptLicense(File lic) throws Exception{
		if(lic!=null&&lic.exists()){
			byte[] encodedData = Base64Utils.fileToByte(lic);
			String sign = RSAUtil.sign(encodedData, License_privateKey);//数字签名
			if(RSAUtil.verify(encodedData, License_publicKey, sign)){//验证公钥和私钥是否匹配而已
				byte[] decodedData = null;
				try{
					decodedData = RSAUtil.decryptByPublicKey(encodedData, License_publicKey);//是加密处理的license内容
				}catch(Exception e){
					e.printStackTrace();
					throw new Exception("license解密失败，非法的许可!");
				}
				//对license内容进行数据解密
				if(decodedData!=null){
					LicenseInfo licInfo = getLicenseData(decodedData,SystemTool.getMachineCode());
					if(licInfo==null) throw new Exception("license数据为空，非法的许可!");
					return licInfo;
				}
			}else{
				throw new Exception("license 内容被篡改过，非法的许可!");
			}
		}else{
			throw new Exception("许可文件不存在!");
		}
		return null;
	}
	
	public static void main(String[] args)  {
		try{
			genLicenseFile("d:/lic/license.data",SystemTool.getMachineCode());
			decryptLicense(new File("d:/lic/license.data"));
		}catch(Exception e){
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		
	}
}
