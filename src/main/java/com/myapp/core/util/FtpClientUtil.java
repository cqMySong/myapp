package com.myapp.core.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年6月3日 
 * @system:
 * FTP 常用方法
 *-----------MySong---------------
 */
public class FtpClientUtil {
	public static String FTP_WEB_ROOTPATH = "webAttach/";
	
	private String host;
	private int port = 21;
	private String username;
	private String password;
	private boolean binaryTransfer = true;
	private String encoding = "GBK";
	private int clientTimeout = 10000;//10秒连接后超时
	private List<String> listFileNames = new ArrayList<String>();
	private FTPClient ftpClient;
	
	public static void main(String[] args){
		try {
			FtpClientUtil ftp = new FtpClientUtil("127.0.0.1", 21, "webApp", "myApp");
			
			ftp.deleteFtpFile("webAttach/5F4F7267/yZdbEy7BRsGz%2FZwcMIjW9V9Pcmc%3D.dll");
//			File locFil = new File("d:\\test.java");
//			System.out.println(ftp.uploadToRemote("BEST/Test//11/22/33/", "我的文12件.java", new FileInputStream(locFil)));	;
//			ftp.downloadFile("/BEST/我的文12件.java", "d:/down/11/22/33/我改名的132.java");
//			ftp.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public FtpClientUtil(String host,int port,String username,String password) throws Exception{
		this.host = host;
		this.port = port;
		this.username = username;
		this.password = password;
		initConfigFtpClient();
	}
	
	private void initConfigFtpClient() throws Exception {
		ftpClient = new FTPClient();
		try{
			ftpClient.setControlEncoding(encoding); 
			ftpClient.setConnectTimeout(clientTimeout);
			ftpClient.connect(host, port);
		}catch(ConnectException ex){
			throw new Exception("ftp连接错误，请检查端口和主机地址!");
		}
		int reply = ftpClient.getReplyCode();
		if (FTPReply.isPositiveCompletion(reply)) {
			boolean login = ftpClient.login(username, password);
			if(!login) throw new Exception("ftp用户名密码错误，登录失败!");
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
		} else {
			ftpClient.disconnect();
		}
		ftpClient.enterLocalPassiveMode();
	}
	
	public void disconnect() throws IOException {
		ftpClient.logout();
		if (ftpClient.isConnected()) {
			ftpClient.disconnect();
			ftpClient = null;
		}
	}
	
	public boolean isConnected(){
		return ftpClient.isConnected();
	}

	public boolean deleteRemoteFiles(String[] delFiles)
			throws  IOException {
		 List<String> list=listNames();
		 for(String filename:delFiles){
			 for(Iterator<String> it=list.iterator();it.hasNext();){
				 String filepath=it.next();
				 if(filepath.contains(filename)){
					boolean result= ftpClient.deleteFile(filepath);
					if(!result){
						return result;
					}
				 }
			 }
		 }
		return true;
	}

	public List<String> listNames() throws 
			IOException {
		return listNames(null);
	}

	public List<String> listNames(String remotePath)
			throws  IOException {
		return listNames(remotePath, true);
	}

	public List<String> listNames(String remotePath,
			boolean containSubdirectory) throws  IOException {
		if (null == remotePath) {
			remotePath = "." + File.separator;
		}
		FTPFile[] files = ftpClient.listFiles(remotePath);
		if (files.length < 3) {
			return listFileNames;
		}
		for (FTPFile file : files) {
			if (!file.getName().equals(".") && !file.getName().equals("..")) {
				if (file.isFile()) {
					listFileNames.add("." + File.separator + file.getName());
					listNames2(remotePath + file.getName() + File.separator,containSubdirectory);
				}
			}
		}
		return listFileNames;
	}
	private void listNames2(String remotePath,
			boolean containSubdirectory) throws SocketException, IOException {
			ftpClient.changeWorkingDirectory(remotePath);
			FTPFile[] files = ftpClient.listFiles(remotePath);
			for (FTPFile file : files) {
				if (!file.equals(".") && !file.equals("..")) {
					if (file.isFile()) {
						listFileNames.add(remotePath + file.getName());
					}
					if (file.isDirectory() && (!".".equals(file.getName()))
							&& (!"..".equals(file.getName()))) {
						String path = remotePath + file.getName()+ File.separator;
						listNames2(path, containSubdirectory);
					}
				}
			}
	}

	public boolean uploadToRemote(String remotePath, String fileName,
			InputStream localInputStream) throws IOException{
		remotePath=remotePath+File.separator;
		if(!remotePath.endsWith("/")){
			remotePath +="/";
		}
		int reply = ftpClient.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
			ftpClient.disconnect();
		}
		String[] paths =  remotePath.split("/");
		String thisPath = "";
		for(int i=0;i<paths.length;i++){
			thisPath +=paths[i]+"/";
			if(!ftpClient.changeWorkingDirectory(remotePath)){
				ftpClient.makeDirectory(thisPath);
			}
		}
		ftpClient.changeWorkingDirectory(remotePath);
		String toFileName = fileName;
		boolean result = ftpClient.storeFile(toFileName, localInputStream);
		localInputStream.close();
		return result;
	}

	public boolean downloadFile(String remoteFile, String localFile) throws IOException{
		boolean result = true;
		int lastIndex = -1;
		String remotePath = "";
		String fileName = remoteFile;
		if((lastIndex = remoteFile.lastIndexOf("/")) > 0||(lastIndex = remoteFile.lastIndexOf("//")) > 0  
			||(lastIndex = remoteFile.lastIndexOf("\\")) > 0||(lastIndex = remoteFile.lastIndexOf(File.separator)) > 0
				){
			remotePath = remoteFile.substring(0, lastIndex);
			fileName = remoteFile.substring(lastIndex+1, remoteFile.length());
		}
		ftpClient.changeWorkingDirectory(remotePath);
		File file = new File(localFile);
		if(!file.exists()){
			String fp = file.getParent();
			new File(fp).mkdirs();
		}
		OutputStream out = new FileOutputStream(file);
		result = ftpClient.retrieveFile(fileName, out); 
		out.close();
		return result;
	}
	
	public boolean downloadToLocal(String remotePath, String localPath)
			throws IOException, IOException {
		return downloadToLocal(remotePath, localPath, null);
	}

	public boolean downloadToLocal(String remotePath, String localPath,
			String[] fileNames) throws IOException,
			IOException {
		remotePath=remotePath+File.separator;
		localPath=localPath+File.separator;
		ftpClient.changeWorkingDirectory(remotePath);
		FTPFile[] ftpList =ftpClient.listFiles(remotePath);
		boolean result = true;
		if (null == fileNames) {
			for (FTPFile f : ftpList) {
				if (f.getSize() > 0) {
					File file = new File(localPath);
					file.mkdirs();
					OutputStream out = new FileOutputStream(localPath + f.getName());
					result = ftpClient.retrieveFile(f.getName(), out); 
					out.close();
					if (!result) break;
				}
			}
		} else {
			for (String fileName : fileNames) {
				File file = new File(localPath);
				file.mkdirs();
				OutputStream out = new FileOutputStream(localPath+ File.separator + fileName);
				result = ftpClient.retrieveFile(fileName, out); 
				out.close();
				if (!result) break;
			}
		}
		return result;
	}

	public int getRemoteFileSize(String fileName) throws IOException {
		int size = 0;
		FTPFile[] ftpList = ftpClient.listFiles();
		for (FTPFile f : ftpList) {
			if (f.getName().equalsIgnoreCase(fileName)) {
				size = (int) f.getSize();
			}
		}
		return size;
	}

	public boolean downloadToLocal2(String filename, String localPath)
			throws Exception {
		 List<String> list=listNames();
		 OutputStream out;
		 try{
			 for(Iterator<String> it=list.iterator();it.hasNext();){
				 String filepath=it.next();
				 if(filepath.contains(filename)){
					 String remoteFilePath=filepath.substring(1, filepath.length());
					 File file=new File(localPath+remoteFilePath);
					 new File(file.getParent()).mkdirs();
					 out= new FileOutputStream(localPath+remoteFilePath);
					 ftpClient.retrieveFile(filepath, out); 
					 out.close(); 
				 }
			 }
			 return true;
		 }catch (Exception e) {
			 return false;
		}
	}
	public boolean mkdirs(String remoteDir) throws SocketException, IOException{
		String[] dirs=remoteDir.split("/");
		String remotePath=".";
		for(String dir:dirs){
			if(!dir.equals(".")&&null!=dir){
				remotePath=remotePath+File.separator+dir+File.separator;
				boolean result = ftpClient.makeDirectory(remotePath);
				if(!result) break;
			}
		}
		return true;
	}
	
	public boolean deleteFtpFile(String remoteFile) throws IOException{
		boolean fg = ftpClient.deleteFile(remoteFile);
		return fg;
	}
	
	public InputStream downFtpFile(String remoteFile) throws IOException{
		return ftpClient.retrieveFileStream(remoteFile);
	}

}
