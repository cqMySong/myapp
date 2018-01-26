package com.myapp.core.license;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2018年1月23日 
 * @system:
 *
 *-----------MySong---------------
 */
public class RSATester {
    static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDFvuSQtRPC+fkShcMwDHc/m" +
    		"3hwp6zrJHBRla6wZgu7XfT3Is65MdQnZFtmtMxdsltjVS4maR6MCZn8Z1aEzNRgImpX53IT3rIC+snmE" +
    		"Hk+wyoYRjXwIRes8Q6OU1fflcfbNJ7FZVbdv6GIknhM0Pr+x8aTPpOsui1PvVeEvUZF1wIDAQAB";
    static String privateKey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAMW+5JC1E8L5" +
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

    
    
    public static void main(String[] args) throws Exception {
//        test();
//    	encrypt();
//       String file = encrypt();
       String file = "d:/lic/licensetem.data";
       decrypt(file);
    }

    static void test() throws Exception {
    	Map<String, Object> keyMap = RSAUtil.genKeyPair();
        publicKey = RSAUtil.getPublicKey(keyMap);
        privateKey = RSAUtil.getPrivateKey(keyMap);

        System.err.println("公钥加密——私钥解密");
        String source = "这是一行没有任何意义的文字，你看完了等于没看，不是吗？";
        System.out.println("\r加密前文字：\r\n" + source);
        Map mp = new HashMap();
        byte[] data = source.getBytes();
        byte[] encodedData = RSAUtil.encryptByPublicKey(data, publicKey);
        System.out.println("publicKey = "+publicKey);
        System.out.println("加密后文字：\r\n" + new String(encodedData));
        byte[] decodedData = RSAUtil.decryptByPrivateKey(encodedData, privateKey);
        System.out.println("privateKey = "+privateKey);
        String target = new String(decodedData);
        System.out.println("解密后文字: \r\n" + target);
        
    }
    

    static String encrypt() throws Exception {
//        System.err.println("私钥加密——公钥解密");
        long begt = new Date().getTime();
        String source = "{user:张三,beg:2041111001,end:12435666,sn:123223535346346,ip:1243535,version:1.1}";
        System.out.println("原文字：\r\n" + source);
        byte[] data = source.getBytes();
        byte[] encodedData = RSAUtil.encryptByPrivateKey(data, privateKey);
        System.out.println("加密后：\r\n" + new String(encodedData));
        String licensePath = "d:/lic/licensetem.data";
        Base64Utils.byteArrayToFile(encodedData, licensePath);
//        byte[] decodedData = RSAUtils.decryptByPublicKey(encodedData, publicKey);
        long engt = new Date().getTime();
        System.out.println(engt-begt);
        return licensePath;
    }
    private static void decrypt(String file) throws Exception{
    	 String licensePath1 = file;
         byte[] encodedData1 = Base64Utils.fileToByte(licensePath1);
         try{
        	 byte[] decodedData1 = RSAUtil.decryptByPublicKey(encodedData1, publicKey);
             String target = new String(decodedData1);
             System.out.println("解密后: \r\n" + target);
         }catch(Exception e){
        	 System.out.println("解密失败");
         }
        
         
         
         System.err.println("私钥签名——公钥验证签名");
         String sign = RSAUtil.sign(encodedData1, privateKey);
         System.err.println("签名:\r" + sign);
         boolean status = RSAUtil.verify(encodedData1, publicKey, sign);
         System.err.println("验证结果:\r" + status);
    }
    
    
}


