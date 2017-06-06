package com.myapp.core.service.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.myapp.core.entity.AttachFileInfo;
import com.myapp.core.util.BaseUtil;
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
	
}
