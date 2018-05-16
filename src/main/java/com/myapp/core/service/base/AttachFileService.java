package com.myapp.core.service.base;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.myapp.core.entity.AttachFileInfo;
import com.myapp.core.entity.FtpServerInfo;
import com.myapp.core.enums.StoreageTypeEnum;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.DocConvertUtil;
import com.myapp.core.util.FtpClientUtil;
import com.myapp.core.util.UploadUtil;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年6月3日 
 * @system:
 *
 *-----------MySong---------------
 */
@Service("attachFileService")
public class AttachFileService extends BaseInterfaceService<AttachFileInfo> {
	@Resource
	public FtpServerService ftpServerService;
	
	/*依据文件的md5和原单据id判断是否存在文件，便于后续的断点续传**/
	public AttachFileInfo getAttachFileByMd5(String md5,String sourceBid){
		if(BaseUtil.isEmpty(md5)&&BaseUtil.isEmpty(sourceBid)) return null;
		String hql = " from AttachFileInfo where md5=? and sourceBillId=? and complete=?";
		List params = new ArrayList();
		params.add(md5);
		params.add(sourceBid);
		params.add(false);
		return getEntity(hql, params.toArray());
	}
	
	public File downLoadFile2Dir(AttachFileInfo afInfo,String targetDir) throws Exception{
		if(afInfo==null) return null;
		File targetFile = new File(targetDir);
		if(targetFile.exists()) return targetFile;
		StoreageTypeEnum ste = afInfo.getStorageType();
		String file = afInfo.getPath()+"/"+afInfo.getFile();//此文件位置为最初的目录位置
		InputStream in = null;
		OutputStream out = null; 
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try{
			if(StoreageTypeEnum.FTP.equals(ste)){
				String ftpId = afInfo.getFtpId();
				if(!BaseUtil.isEmpty(ftpId)){
					FtpServerInfo ftpInfo = (FtpServerInfo) ftpServerService.getEntity(ftpId);
					if(ftpInfo!=null){
						FtpClientUtil ftp = ftpServerService.getMyFtpClient(ftpInfo);
						if(ftp!=null){
							DocConvertUtil.checkFilePath(targetFile.getParentFile());
							ftp.downloadFile(file, targetDir);
						}
					}
				}
			}else if(StoreageTypeEnum.APP.equals(ste)){
				File serverFile = new File(file);
				if(serverFile.exists()){
					in = new FileInputStream(serverFile);  
					if(in!=null){
						DocConvertUtil.checkFilePath(targetFile.getParentFile());
						bis = new BufferedInputStream(in);
						FileOutputStream fileOut = new FileOutputStream(targetFile);  
						bos = new BufferedOutputStream(fileOut);
						byte[] buf = new byte[4096];  
						int length = bis.read(buf);  
						//保存文件  
						while(length != -1){
							 bos.write(buf, 0, length);  
							 length = bis.read(buf);  
						}  
					}
				}
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception("文档下载出错:"+e.getMessage());
		}finally{
			try {
				if(bos!=null) bos.close();  
				if(bis!=null) bis.close();
				if(out!=null){
					out.flush();
					out.close();
				}
				if(in!=null)in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return new File(targetDir);
	}
}
