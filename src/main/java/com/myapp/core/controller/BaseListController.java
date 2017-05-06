package com.myapp.core.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myapp.core.base.controller.BaseController;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.base.setting.SystemConstant;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.WebUtil;

/**
 * 
 * -----------MySong--------------- ©MySong基础框架搭建
 * 
 * @author mySong @date 2017年3月12日
 * @system:
 *
 *          -----------MySong---------------
 */
public abstract class BaseListController extends BaseController {
	private Integer curPage;
	private Integer pageSize;
	private String id;

	public abstract AbstractBaseService getService();

	public abstract String querySQL();

	@ResponseBody
	@RequestMapping("/list")
	public WebDataModel toList() {
		try {
			onLoad();
			data = getService().toPageQuery(getCurPage(), getPageSize(),
					querySQL(), executeQueryParams().toArray());
		} catch (Exception e) {
			e.printStackTrace();
			setExceptionMesg(e.getMessage());
		}
		return ajaxModel();
	}

	public List executeQueryParams() {
		List params = new ArrayList();
		return params;
	}

	@ResponseBody
	@RequestMapping("/remove")
	public WebDataModel toRemove() {
		try {
			onLoad();
			String cur_id = getId();
			if(!BaseUtil.isEmpty(cur_id)){
				getService().deleteEntity(cur_id);
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

	public String getId() {
		return WebUtil.UUID_ReplaceID(id);
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getCurPage() {
		if (curPage == null)
			curPage = SystemConstant.DEF_PAGE_BEG;
		return curPage;
	}

	public void setCurPage(Integer curPage) {
		this.curPage = curPage;
	}

	public Integer getPageSize() {
		if (pageSize == null)
			pageSize = SystemConstant.DEF_PAGE_SIZE;
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

}
