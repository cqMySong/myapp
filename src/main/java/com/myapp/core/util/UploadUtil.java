package com.myapp.core.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import com.myapp.core.entity.AttachFileInfo;
import com.myapp.core.entity.FileItemsInfo;
import com.myapp.core.enums.StoreageTypeEnum;
import com.myapp.core.service.base.AttachFileService;
import com.myapp.core.uuid.SysObjectType;
import com.myapp.core.uuid.SysUuid;

public class UploadUtil {
	public static String UPLOAD_DEFPATH = "D://uploadPath"; //
	public static String FTP_REC_PATH = "attachFile"; //
	
	public static Map toUpLoadFile(AttachFileService attachServer,Map params,String uploadPath) throws IOException{
		boolean status = true;
		String mesg = "";
		AttachFileInfo attachFileInfo = null;
		BufferedOutputStream outputStream = null;
		String upPath = BaseUtil.isEmpty(uploadPath)?UPLOAD_DEFPATH:uploadPath;
			try {
				if(params.size()>0){
					Object fmd5 = params.get("md5");
					Object fitem = params.get("file");
					String sourceBid = WebUtil.UUID_ReplaceID(toString(params.get("sourceBillID")));
					if(!BaseUtil.isEmpty(fmd5)&&fitem!=null&&fitem instanceof MultipartFile&&!BaseUtil.isEmpty(sourceBid)){
						attachFileInfo = attachServer.getAttachFileByMd5(fmd5.toString(),sourceBid);
						MultipartFile fileItem = (MultipartFile) fitem;
						if(attachFileInfo==null){
							attachFileInfo = new AttachFileInfo();
							String fmortSize = toString(params.get("formatSize")).toUpperCase();
							if(!BaseUtil.isEmpty(fmortSize)){
								attachFileInfo.setFmortSize(fmortSize);
							}
							String size = toString(params.get("fileSize")).toUpperCase();
							if(!BaseUtil.isEmpty(size)){
								attachFileInfo.setSize(new BigDecimal(size));
							}
							attachFileInfo.setFileName(URLDecoder.decode(toString(params.get("fileName")),"UTF-8"));
							attachFileInfo.setMd5(fmd5.toString());
							attachFileInfo.setStorageType(StoreageTypeEnum.APP);
							attachFileInfo.setFtpId("");
							if(!BaseUtil.isEmpty(sourceBid)){
								attachFileInfo.setSourceBillId(sourceBid);
								SysObjectType billType = SysUuid.getBOSObjectType(sourceBid, true);
								String bosType = "";
								if(billType!=null){
									bosType = billType.toString();
									upPath = upPath+"/"+bosType;
								}
								attachFileInfo.setBillType(bosType);
							}
							attachFileInfo.setUploadDate(new Date());
							attachFileInfo.setPath(upPath);
							attachFileInfo.setComplete(false);
						}
						String file_suffix = attachFileInfo.getFileName();
						if(!BaseUtil.isEmpty(file_suffix)&&file_suffix.indexOf(".")>0){
							file_suffix = file_suffix.substring(file_suffix.lastIndexOf("."));
						}
						if(BaseUtil.isEmpty(attachFileInfo.getFile())){
							SysUuid uid = SysUuid.create(SysObjectType.create(attachFileInfo.getBillType()));
							attachFileInfo.setFile(WebUtil.UUID2String(uid.toString())+file_suffix);
						}
						
						FileItemsInfo fileItemInfo = new FileItemsInfo();
						fileItemInfo.setParent(attachFileInfo);
						fileItemInfo.setSize(toBigDecimal(fileItem.getSize()));
						fileItemInfo.setLoadDate(new Date());
						fileItemInfo.setSeq(attachFileInfo.getItems().size()+1);
						fileItemInfo.setItemFileName(fileItemInfo.getSeq()+"_"+attachFileInfo.getFile());
						
						attachFileInfo.getItems().add(fileItemInfo);
						attachFileInfo.setLastUpdateDate(new Date());
						attachFileInfo.setLoadedSize(attachFileInfo.getLoadedSize().add(fileItemInfo.getSize()));
						
						BigDecimal remain = attachFileInfo.getSize().subtract(attachFileInfo.getLoadedSize());
						Set<FileItemsInfo> rfiCc = attachFileInfo.getItems();
						File up = new File(attachFileInfo.getPath());
						if (!up.exists() ){
							up.mkdirs();
						}
						if(remain.compareTo(BigDecimal.ZERO)>0){
							File savedFile = new File(attachFileInfo.getPath(), fileItemInfo.getItemFileName());
							File fp = savedFile.getParentFile();
							if(!fp.exists()){
								fp.mkdirs();
							}
							outputStream = new BufferedOutputStream(new FileOutputStream(new File(attachFileInfo.getPath(),fileItemInfo.getItemFileName())));
							IOUtils.copy( fileItem.getInputStream(), outputStream); 
						}else{
							
							List fileNames = new ArrayList();
							for(FileItemsInfo curItemInfo:rfiCc){
								fileNames.add(curItemInfo.getItemFileName());
							}
							
							outputStream = new BufferedOutputStream(new FileOutputStream(new File(attachFileInfo.getPath(),attachFileInfo.getFile())));
							for(int i=0;i<fileNames.size()-1;i++){
								String fname = (String) fileNames.get(i);
								File tempFile = new File(attachFileInfo.getPath(),fname );
								if(tempFile.isFile()&&tempFile.exists()){
									byte[] bytes = FileUtil.readFileToByteArray(tempFile);
									outputStream.write( bytes );
									outputStream.flush();
									tempFile.delete();
								}
							}
							outputStream.write(fileItem.getBytes());
							outputStream.flush();
							outputStream.close();
							attachFileInfo.setComplete(true);
						}
						
					}else{
						status = false;
						mesg = "相关信息为空不能完成上传操作";
					}
				}
			} catch ( Exception e ) {
				e.printStackTrace();
				status = false;
				mesg = "上传失败:"+e.getMessage();
			} finally {
				try {
					if (outputStream != null) outputStream.close();
				} catch ( IOException e ) {
					e.printStackTrace();
				}
			}
		Map retMap = new HashMap();
		retMap.put("status", status);
		retMap.put("mesg", mesg);
		retMap.put("attachInfo", attachFileInfo);
		return retMap;
	}
	
	public static BigDecimal toBigDecimal(Object obj){
		BigDecimal bigDecimal = BigDecimal.ZERO;
		if(null != obj){
			if(obj instanceof BigDecimal){
				bigDecimal = (BigDecimal)obj;
			}else{
				bigDecimal = new BigDecimal(obj.toString().trim());
			}
		}
		return bigDecimal;
	}
	
	public static String toString(Object obj){
		if(BaseUtil.isEmpty(obj)) return "";
		try {
			return new String(obj.toString().getBytes("iso8859-1"), "gb2312");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
		return obj.toString();
	}
	
}
