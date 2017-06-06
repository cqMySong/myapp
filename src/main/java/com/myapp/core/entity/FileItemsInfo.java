package com.myapp.core.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.myapp.core.base.entity.CoreBaseEntryInfo;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年6月3日 
 * @system:
 *
 *-----------MySong---------------
 */
@Entity
@Table(name="t_base_attachFileItems")
public class FileItemsInfo extends CoreBaseEntryInfo<AttachFileInfo> {
	private BigDecimal size;//当前文件片的大小
	private Date loadDate;
	private String itemFileName;//文件片名
	
	@Column(name = "fsize")
	public BigDecimal getSize() {
		return size;
	}
	public void setSize(BigDecimal size) {
		this.size = size;
	}
	@Column(name = "fLoadDate")
	public Date getLoadDate() {
		return loadDate;
	}
	public void setLoadDate(Date loadDate) {
		this.loadDate = loadDate;
	}
	@Column(name = "fItemFileName")
	public String getItemFileName() {
		return itemFileName;
	}
	public void setItemFileName(String itemFileName) {
		this.itemFileName = itemFileName;
	}
	
}
