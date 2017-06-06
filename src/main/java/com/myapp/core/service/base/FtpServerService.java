package com.myapp.core.service.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.myapp.core.entity.AttachFileInfo;
import com.myapp.core.entity.FtpServerInfo;
import com.myapp.core.util.FtpClientUtil;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年6月3日 
 * @system:
 *
 *-----------MySong---------------
 */
@Service("ftpServerService")
public class FtpServerService extends BaseInterfaceService<FtpServerInfo> {
	
	public FtpClientUtil getMyFtpClient() throws Exception{
		String hql = "from FtpServerInfo where enabled=?";
		List params = new ArrayList();
		params.add(true);
		return getMyFtpClient(getBaseService().getEntity(hql, params.toArray()));
	}
	
	public FtpClientUtil getMyFtpClient(FtpServerInfo ftpServer) throws Exception{
		if(ftpServer==null) return null;
		return new FtpClientUtil(ftpServer.getHost(),ftpServer.getPort(),ftpServer.getUserName(),ftpServer.getPassword());
	}
	
	public boolean uploadFile(FtpClientUtil ftp,AttachFileInfo attachFile) throws FileNotFoundException, IOException{
		boolean isSuccess = false;
		if(ftp!=null&&ftp.isConnected()&&attachFile!=null){
			String fileName = attachFile.getFile();
			File localFile = new File(attachFile.getPath()+"/"+fileName);
			if(localFile.exists()){
				String remotePath = FtpClientUtil.FTP_WEB_ROOTPATH+attachFile.getBillType();
				isSuccess = ftp.uploadToRemote(remotePath, fileName, new FileInputStream(localFile));
			}
		}
		return isSuccess;
	}
}
