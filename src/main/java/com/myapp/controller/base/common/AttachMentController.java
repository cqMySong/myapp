package com.myapp.controller.base.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.AuthorAnn;
import com.myapp.core.base.controller.BaseController;
import com.myapp.core.base.setting.SystemConstant;
import com.myapp.core.entity.AttachFileInfo;
import com.myapp.core.entity.FtpServerInfo;
import com.myapp.core.enums.FileType;
import com.myapp.core.enums.StoreageTypeEnum;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.service.base.AttachFileService;
import com.myapp.core.service.base.FtpServerService;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.DocConvertUtil;
import com.myapp.core.util.FileUtil;
import com.myapp.core.util.FtpClientUtil;
import com.myapp.core.util.UploadUtil;
import com.myapp.core.util.WebUtil;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年6月3日 
 * @system:
 *
 *-----------MySong---------------
 */

@Controller
@RequestMapping("/base/attach")
public class AttachMentController extends BaseController {
	private Integer curPage;
	private Integer pageSize;
	@Resource
	public AttachFileService attachFileService;
	@Resource
	public FtpServerService ftpServerService;
	
	public String getAttachDir(){
		String attachDir = UploadUtil.UPLOAD_DEFPATH;
		Object attDirObj = request.getServletContext().getAttribute("attachDir");
		if(attDirObj!=null){
			attachDir = attDirObj.toString();
		}
		return attachDir;
	}
	@AuthorAnn(doPermission=false)
	@RequestMapping(value = "/upload", method = RequestMethod.POST)  
	public String upload(@RequestBody MultipartFile file
            ,@RequestParam String fileName,@RequestParam String md5
            ,@RequestParam String sourceBillID,@RequestParam String formatSize
            ,@RequestParam String fileSize){
		init();
		try {
			Map params = new HashMap();
			params.put("md5", md5);
			params.put("fileName", fileName);
			params.put("file", file);
			params.put("sourceBillID", sourceBillID);
			params.put("formatSize", formatSize);
			params.put("fileSize", fileSize);
			Map retMap = UploadUtil.toUpLoadFile(attachFileService, params, getAttachDir());
			boolean isOk = (boolean) retMap.get("status");
			if(isOk){
				AttachFileInfo atFile = (AttachFileInfo) retMap.get("attachInfo");
				if(atFile!=null&&atFile.getItems().size()>0){
					if(BaseUtil.isEmpty(atFile.getId())){
						atFile.setUploader(getCurUser());
					}
					if(atFile.getComplete()){
						String hql = "from FtpServerInfo where enabled=?";
						List ftpparams = new ArrayList();
						ftpparams.add(true);
						FtpServerInfo ftpInfo = ftpServerService.getEntity(hql, ftpparams.toArray());
						if(ftpInfo!=null){
							FtpClientUtil ftp = ftpServerService.getMyFtpClient(ftpInfo);
							if(ftp!=null){
								boolean isUp = ftpServerService.uploadFile(ftp, atFile);
								if(isUp){
									atFile.setFtpId(ftpInfo.getId());
									atFile.setStorageType(StoreageTypeEnum.FTP);
									String localFile = atFile.getPath()+"/"+atFile.getFile();
									atFile.setPath(FtpClientUtil.FTP_WEB_ROOTPATH+atFile.getBillType());
									//删除本地文件
									File temFile = new File(localFile);
									if(temFile.exists()) temFile.delete();
								}
							}
						}
					}
					attachFileService.saveEntity(atFile);
				}else{
					this.statusCode = STATUSCODE_ERROR;
					this.statusMesg = "文件上传失败！";
				}
			}else{
				this.statusCode = STATUSCODE_ERROR;
				this.statusMesg = (String) retMap.get("mesg");
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.statusCode = STATUSCODE_EXCEPTION;
			this.statusMesg = e.getMessage();
		}
		JSONObject json = new JSONObject();
		json.put("status", true);
		json.put("errMesg", "");
		try {
			response.getWriter().write(json.toJSONString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "base/upload";
	}
	
	public String getOperate(){
		String operate = WebUtil.UUID_ReplaceID(getParamterByRequest("operate"));
		if(BaseUtil.isNotEmpty(operate)){
			if(operate.equalsIgnoreCase("view")||operate.equalsIgnoreCase("edit")){
				return operate.toLowerCase();
			}
		}
		return "view";
	}
	@AuthorAnn(doPermission=false)
	@RequestMapping("/toAttach")
	public ModelAndView toAttach(){
		Map params = new HashMap();
		params.put("billId", getSourceBillId());
		params.put("billEntryId", getSourceBillEntryId());
		params.put("operate", getOperate());
		return toPage("base/attach", params);
	}
	
	@AuthorAnn(doPermission=false)
	@RequestMapping(value="/list")
	@ResponseBody
	public WebDataModel toList() {
		try {
			init();
			Criteria query = attachFileService.initQueryCriteria();
			ProjectionList projectList= Projections.projectionList();
			projectList.add(Projections.property("id").as("id"));
			projectList.add(Projections.property("fileName").as("fileName"));
			projectList.add(Projections.property("fmortSize").as("fmortSize"));
			projectList.add(Projections.property("uploadDate").as("uploadDate"));
			projectList.add(Projections.property("storageType").as("storageType"));
			projectList.add(Projections.property("complete").as("complete"));
			executeQueryParams(query);
			data = attachFileService.toPageQuery(query, projectList, getCurPage(), getPageSize());
		} catch (Exception e) {
			e.printStackTrace();
			setExceptionMesg(e.getMessage());
		}
		return ajaxModel();
	}
	public void executeQueryParams(Criteria query) {
		String serach = request.getParameter("search");
		if(!BaseUtil.isEmpty(serach)){
			Map searchMap = JSONObject.parseObject(serach, new HashMap().getClass());
			Object objBillId =searchMap.get("billId") ;
			if(!BaseUtil.isEmpty(objBillId)){
				query.add(Restrictions.eq("sourceBillId", objBillId.toString()));
			}
			Object obj_field = searchMap.get("key");
			Object obj_val = searchMap.get("value");
			Object obj_type = searchMap.get("type");
			if(obj_field!=null){
				if(obj_val==null){
				}else{
					obj_val = obj_val.toString();
					query.add(Restrictions.like(obj_field.toString(), "%"+obj_val+"%"));
				}
			}
		}
	}
	public Integer getPageSize() {
		String pgsize = request.getParameter("pageSize");
		if(!BaseUtil.isEmpty(pgsize)){
			pageSize = Integer.valueOf(pgsize);
		}
		if (pageSize == null)
			pageSize = SystemConstant.DEF_PAGE_SIZE;
		return pageSize;
	}
	public Integer getCurPage() {
		String cpage = request.getParameter("curPage");
		if(!BaseUtil.isEmpty(cpage)){
			curPage = Integer.valueOf(cpage);
		}
		if (curPage == null)
			curPage = SystemConstant.DEF_PAGE_BEG;
		return curPage;
	}
	
	private String getSourceBillId(){
		return WebUtil.UUID_ReplaceID(getParamterByRequest("billId"));
	}
	
	private String getSourceBillEntryId(){
		return WebUtil.UUID_ReplaceID(getParamterByRequest("billEntryId"));
	}
	
	private String getBillId(){
		return WebUtil.UUID_ReplaceID(getParamterByRequest("id"));
	}
	
	@AuthorAnn(doPermission=false)
	@ResponseBody
	@RequestMapping(value="/remove",method=RequestMethod.POST)
	public WebDataModel toRemove() {
		try {
			init();
			String billId = getBillId();
			if(!BaseUtil.isEmpty(billId)){
				if(billId.indexOf(",")<0){
					deleteEntity(billId);
				}else{
					String[] billIds = billId.split(",");
					for(int i=0;i<billIds.length;i++){
						deleteEntity(billIds[i]);
					}
				}
				setInfoMesg("数据删除成功!");
			}else{
				setErrorMesg("单据id为空，无法完成删除操作!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			setExceptionMesg(e.getMessage());
		}
		return ajaxModel();
	}
	
	private void deleteEntity(String entityId) throws Exception{
		if(BaseUtil.isEmpty(entityId)) return;
		AttachFileInfo afInfo = (AttachFileInfo) attachFileService.getEntity(entityId);
		StoreageTypeEnum ste = afInfo.getStorageType();
		String file = afInfo.getPath()+"/"+afInfo.getFile();
		if(StoreageTypeEnum.FTP.equals(ste)){
			String ftpId = afInfo.getFtpId();
			if(!BaseUtil.isEmpty(ftpId)){
				FtpServerInfo ftpInfo = (FtpServerInfo) ftpServerService.getEntity(ftpId);
				if(ftpInfo!=null){
					FtpClientUtil ftp = ftpServerService.getMyFtpClient(ftpInfo);
					if(ftp!=null){
						ftp.deleteFtpFile(file);
						ftp.disconnect();
					}
				}
			}
		}else if(StoreageTypeEnum.APP.equals(ste)){
			//删除本地文件
			File temFile = new File(file);
			if(temFile.exists()) temFile.delete();
		}
		attachFileService.deleteEntity(entityId);
	}
	
	@ResponseBody
	@RequestMapping(value="/view",method=RequestMethod.POST)
	@AuthorAnn(doLongin=false,doPermission=false)
	public WebDataModel view(){
		init();
		Map dataMap = new HashMap();
		String billId = getBillId();
		if(!BaseUtil.isEmpty(billId)){
			AttachFileInfo afInfo = (AttachFileInfo) attachFileService.getEntity(billId);
			if(afInfo.getComplete()){
				FileType ftype = DocConvertUtil.getFileType(afInfo.getFile());
				if(ftype!=null){
					dataMap.put("fileName", afInfo.getFileName());
					dataMap.put("fileSize", afInfo.getFmortSize());
					
					ServletContext ctx = request.getServletContext();
					String onlineDir = ctx.getRealPath("/")+"onlineview";//硬盘上保存的主目录
					String srcFile = afInfo.getFile();
					srcFile = srcFile.replaceAll("%", "");
					String viewUrlRoot = request.getContextPath()+"/onlineview";
					try{
						if((FileType.DOC.equals(ftype)
								||FileType.EXCEL.equals(ftype)||FileType.PPT.equals(ftype)
								||FileType.PDF.equals(ftype))){
							String fname = DocConvertUtil.getFileNamePrefx(srcFile);
							String viewUrlPath = viewUrlRoot+"/html/"+fname;
							File viewFile = new File(onlineDir+"/html/"+fname,"index.html");
							if(!viewFile.exists()){//文件还未转换过的
								String targetDir = onlineDir+"/"+srcFile;
								File targetFile = attachFileService.downLoadFile2Dir(afInfo,targetDir);
								if(targetFile!=null&&targetFile.exists()){
									try{
										//文档转换
										targetFile = new File(onlineDir+"/"+srcFile);
										DocConvertUtil.checkFilePath(targetFile.getParentFile());
										if(targetFile.exists()){
											DocConvertUtil.pdf2Html(DocConvertUtil.documnt2Pdf(targetFile, null), onlineDir+"/html");
										}
									}catch (Exception e) {
										e.printStackTrace();
										setExceptionMesg("文档转换出错:"+e.getMessage());
									}
								}
							}
							viewFile = new File(onlineDir+"/html/"+fname,"index.html");
							if(viewFile.exists()){
								dataMap.put("viewUrl",viewUrlPath+"/"+"index.html");
								dataMap.put("fileType", "html");
							}else{
								setErrorMesg("未找到对应转换的文件!");
							}
						}else if(FileType.IMG.equals(ftype)){
							String targetDir = onlineDir+"/images/"+srcFile;//图片保存的目录位置
							File targetFile = attachFileService.downLoadFile2Dir(afInfo,targetDir);
							if(targetFile!=null&&targetFile.exists()){
								dataMap.put("viewUrl",viewUrlRoot+"/images/"+srcFile);
								dataMap.put("fileType", "image");
							}else{
								setErrorMesg("未找到对应转换的文件!");
							}
						}else{
							setErrorMesg("此文件("+afInfo.getFileName()+")不支持在线查看!");
						}
						
					}catch (Exception e) {
						e.printStackTrace();
						setExceptionMesg("文档下载出错:"+e.getMessage());
					}
				}
			}else{
				setErrorMesg("附件信息数据未完整，不允许在线查看!");
			}
		}else{
			setErrorMesg("无文件信息!");
		}
		this.data = dataMap;
		return ajaxModel();
	}
	
	@AuthorAnn(doPermission=false)
	@RequestMapping(value="/down",produces= MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public void downFile(HttpServletRequest request,HttpServletResponse response){
		String id = (String)request.getParameter("id");
		ServletOutputStream output = null;
		try {
			if(!BaseUtil.isEmpty(id)){
				id = WebUtil.UUID_ReplaceID(id);
				response.setContentType("application/octet-stream");// 定义输出类型 
				AttachFileInfo afInfo = (AttachFileInfo) attachFileService.getEntity(id);
				byte[] bte = null;
				String fileName = afInfo.getFileName();
		        String headerStr="attachment;filename=" + new String(fileName.getBytes("gb2312"),"iso8859-1");
				response.setHeader("Content-disposition",headerStr);
				
				output = response.getOutputStream();
				StoreageTypeEnum ste = afInfo.getStorageType();
				String file = afInfo.getPath()+"/"+afInfo.getFile();
				if(StoreageTypeEnum.FTP.equals(ste)){
					String ftpId = afInfo.getFtpId();
					if(!BaseUtil.isEmpty(ftpId)){
						FtpServerInfo ftpInfo = (FtpServerInfo) ftpServerService.getEntity(ftpId);
						if(ftpInfo!=null){
							FtpClientUtil ftp = ftpServerService.getMyFtpClient(ftpInfo);
							if(ftp!=null){
								InputStream in = ftp.downFtpFile(file);
								if(in!=null){
									IOUtils.copy(in, output); 
									bte = null;
								}
							}
						}
					}
				}else if(StoreageTypeEnum.APP.equals(ste)){
					File serverFile = new File(file);
					if(serverFile.exists()){
						bte = FileUtil.file2Byte(serverFile);
					}
				}
				if(bte!=null){
					output.write(bte);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				output.flush();
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@AuthorAnn(doPermission=false)
	@ResponseBody
	@RequestMapping(value="/initFile",method=RequestMethod.POST)
	public Map initFileInfo(String md5,String sourceId){
		BigDecimal loaded = BigDecimal.ZERO;
		BigDecimal offset = BigDecimal.ZERO;
		if(!BaseUtil.isEmpty(md5)&&!BaseUtil.isEmpty(sourceId)){
			sourceId = WebUtil.UUID_ReplaceID(sourceId);
			String hql = " from AttachFileInfo where md5=? and sourceBillId =? and complete=?";
			Object[] params = new Object[]{md5,sourceId,Boolean.FALSE};
			AttachFileInfo atfInfo = attachFileService.getEntity(hql, params);
			if(atfInfo!=null){
				BigDecimal totalSize = atfInfo.getSize();
				loaded = atfInfo.getLoadedSize();
				offset = totalSize.subtract(loaded);
			}
		}
		Map mp = new HashMap();
		mp.put("loaded", loaded);
		mp.put("offset", offset);
		return mp;
	}
}
