package com.myapp.core.license;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.myapp.core.entity.UserInfo;
import com.myapp.core.uuid.SysUuid;
import com.myapp.core.uuid.UuidUtils;

import sun.misc.BASE64Encoder;

/**
 * -----------MySong--------------- ©MySong基础框架搭建
 * 
 * @author mySong @date 2018年1月24日
 * @system:
 *
 *          -----------MySong---------------
 */
public class SystemTool {
	/**
	 * 获取当前操作系统名称. return 操作系统名称 例如:windows xp,linux 等.
	 */
	public static String getOSName() {
		return System.getProperty("os.name").toLowerCase();
	}

	/**
	 * 生成加过密的机器码 机器码组成 ：mac物理地址 ，本机cpu 序列号 C硬盘序列号
	 * 
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	public static String getMachineCode() throws IOException,
			NoSuchAlgorithmException {
		String str4 = getMAC();// mac物理地址
		String str5 = getCpuNumber();// 本机cpu 序列号
		String str6 = getSerialNumber("C");// C硬盘序列号
		String MachineCode = str4 + str5 + str6;
		String md5 = EncoderByMd5(MachineCode);// md5加密
		System.out.println("MachineCode = " + MachineCode);
		return md5;
	}

	/**
	 * 对机器码进行MD5加密
	 * 
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */

	public static String EncoderByMd5(String str)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		// 确定计算方法
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		BASE64Encoder base64en = new BASE64Encoder();
		// 加密后的字符串
		String newstr = base64en.encode(md5.digest(str.getBytes("utf-8")));
		return newstr;
	}

	/**
	 * 验证机器码
	 * 
	 * @param str
	 *            数据源值
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public static Boolean ver_MacineCode(String str)
			throws NoSuchAlgorithmException, IOException {
		if (str.equals(getMachineCode())) {
			return true;
		}
		return false;
	}

	/**
	 * mac物理地址
	 * 
	 * @return
	 */
	public static String getMAC() {
		String os = getOSName();
		String mac = "";
		// System.out.println("OS Type:" + os);
		if (os.startsWith("windows")) {
			// 本地是windows
			mac = getWindowsMACAddress();
			// System.out.println("MAC Address:" + mac);
		} else {
			// 本地是非windows系统 一般就是unix
			mac = getUnixMACAddress();
			// System.out.println(mac);
		}
		return mac;
	}

	/**
	 * 获取unix网卡的mac地址. 非windows的系统默认调用本方法获取.如果有特殊系统请继续扩充新的取mac地址方法.
	 * 
	 * @return mac地址
	 */
	public static String getUnixMACAddress() {
		String mac = null;
		BufferedReader bufferedReader = null;
		Process process = null;
		try {
			process = Runtime.getRuntime().exec("ifconfig eth0");// linux下的命令，一般取eth0作为本地主网卡
																	// 显示信息中包含有mac地址信息
			bufferedReader = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			String line = null;
			int index = -1;
			while ((line = bufferedReader.readLine()) != null) {
				index = line.toLowerCase().indexOf("hwaddr");// 寻找标示字符串[hwaddr]
				if (index >= 0) {// 找到了
					mac = line.substring(index + "hwaddr".length() + 1).trim();// 取出mac地址并去除2边空格
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			bufferedReader = null;
			process = null;
		}

		return mac;
	}

	/**
	 * 获取widnows网卡的mac地址. 用模式匹配方式查找MAC地址，与操作系统的语言无关
	 * 
	 * @return mac地址
	 */
	public static String getWindowsMACAddress() {
		Pattern macPattern = Pattern.compile(
				".*((:?[0-9a-f]{2}[-:]){5}[0-9a-f]{2}).*",
				Pattern.CASE_INSENSITIVE);
		String mac = null;
		try {
			Process pro = Runtime.getRuntime().exec("cmd.exe /c ipconfig/all");

			InputStream is = pro.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String message = br.readLine();

			int index = -1;
			while (message != null) {
				Matcher matcher = macPattern.matcher(message);
				if (matcher.matches()) {
					mac = matcher.group(1).trim();
					break;
				}
				message = br.readLine();
			}
			br.close();
			pro.destroy();
		} catch (IOException e) {
			System.out.println("Can‘t get mac address!");
			return null;
		}
		return mac;
	}

	/**
	 * @return 本机主机名
	 */
	public static String getHostName() {
		InetAddress ia = null;
		try {
			ia = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (ia == null) {
			return "some error..";
		} else
			return ia.getHostName();
	}

	/**
	 * @return 本机IP 地址
	 */
	public static String getIPAddress() {
		InetAddress ia = null;
		try {
			ia = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (ia == null) {
			return "some error..";
		} else
			return ia.getHostAddress();
	}

	/**
	 * @return 本机cpu 序列号
	 * @throws IOException
	 */
	public static String getCpuNumber() throws IOException {
		Process process = Runtime.getRuntime().exec(
				new String[] { "wmic", "cpu", "get", "ProcessorId" });
		process.getOutputStream().close();
		Scanner sc = new Scanner(process.getInputStream());
		String property = sc.next();
		String serial = sc.next();
		// System.out.println(property + ": " + serial);
		return serial;
	}

	/**
	 * 获取硬盘序列号
	 * 
	 * @param drive
	 * @return
	 */

	public static String getSerialNumber(String drive) {
		String result = "";
		try {
			File file = File.createTempFile("realhowto", ".vbs");
			file.deleteOnExit();
			FileWriter fw = new java.io.FileWriter(file);

			String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\n"
					+ "Set colDrives = objFSO.Drives\n"
					+ "Set objDrive = colDrives.item(\""
					+ drive
					+ "\")\n"
					+ "Wscript.Echo objDrive.SerialNumber"; // see note
			fw.write(vbs);
			fw.close();
			Process p = Runtime.getRuntime().exec(
					"cscript //NoLogo " + file.getPath());
			BufferedReader input = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String line;
			while ((line = input.readLine()) != null) {
				result += line;
			}
			input.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.trim();
	}

	/**
	 * 测试用的main方法.
	 * 
	 * @param argc
	 *            运行参数.
	 * @throws Exception
	 */
	public static void main(String[] argc) throws Exception {
		String machId = getMachineCode();
		System.out.println(machId);
	}

}
