package com.myapp.core.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.myapp.core.base.entity.CoreInfo;
import com.myapp.core.enums.StoreageTypeEnum;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年6月3日 
 * @system:
 *
 *-----------MySong---------------
 */
@Entity
@Table(name="t_base_attachFile")
public class AttachFileInfo extends CoreInfo {
	private String fileName;//上传的原始文件名
	private String path;//文件路径
	private String md5;
	private BigDecimal size;//文件的字节总大小
	private BigDecimal loadedSize;//文件的已经上传的字节大小，用于上传进度
	private String fmortSize;//显示文件的大小  xxxKB
	private StoreageTypeEnum storageType;
	private String ftpId;
	private String remark;
	private String billType;
	private String sourceBillId;
	private String sourceBillEntryId;
	private Set<FileItemsInfo> items;
	private Boolean complete ;
	private String file;//真实保存在磁盘上的文件名，不包括路径
	private Date uploadDate;
	private Date lastUpdateDate;
	
	@Column(name="fFileName")
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	@Column(name="fPath")
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	@Column(name="fMd5")
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	@Column(name="fStoreageType")
	@Type(type="myEnum",parameters={@Parameter(name="enumClass",value="com.myapp.core.enums.StoreageTypeEnum")})
	public StoreageTypeEnum getStorageType() {
		return storageType;
	}
	public void setStorageType(StoreageTypeEnum storageType) {
		this.storageType = storageType;
	}
	@Column(name="fsize")
	public BigDecimal getSize() {
		return size;
	}
	public void setSize(BigDecimal size) {
		this.size = size;
	}
	@Column(name="fdisplaySize")
	public String getFmortSize() {
		return fmortSize;
	}
	public void setFmortSize(String fmortSize) {
		this.fmortSize = fmortSize;
	}
	@Column(name="fftpId")
	public String getFtpId() {
		return ftpId;
	}
	public void setFtpId(String ftpId) {
		this.ftpId = ftpId;
	}
	@Column(name="fremark")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name="fbillType")
	public String getBillType() {
		return billType;
	}
	public void setBillType(String billType) {
		this.billType = billType;
	}
	@Column(name="fsourceBillId")
	public String getSourceBillId() {
		return sourceBillId;
	}
	public void setSourceBillId(String sourceBillId) {
		this.sourceBillId = sourceBillId;
	}
	@Column(name="fsourceBillEntryId")
	public String getSourceBillEntryId() {
		return sourceBillEntryId;
	}
	public void setSourceBillEntryId(String sourceBillEntryId) {
		this.sourceBillEntryId = sourceBillEntryId;
	}
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="parent")
	@OrderBy("seq ASC")
	public Set<FileItemsInfo> getItems() {
		if(items==null) items = new HashSet<FileItemsInfo>();
		return items;
	}
	public void setItems(Set<FileItemsInfo> items) {
		this.items = items;
	}
	@Column(name="fisComplete",length=2)
	public Boolean getComplete() {
		return complete;
	}
	public void setComplete(Boolean complete) {
		this.complete = complete;
	}
	@Column(name="fFile")
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	@Column(name="fuploadDate")
	public Date getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
	@Column(name="flastUpdateDate")
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	@Column(name="floadedSize")
	public BigDecimal getLoadedSize() {
		if(loadedSize==null) loadedSize = BigDecimal.ZERO;
		return loadedSize;
	}
	public void setLoadedSize(BigDecimal loadedSize) {
		this.loadedSize = loadedSize;
	}
	
	
}
