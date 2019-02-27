package com.hanweb.jmp.cms.entity.cols;

import java.io.Serializable;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;

import com.hanweb.jmp.constant.Tables;

/**
 * 栏目字段实体 
 */
@Table(name = Tables.COLFIELD)
public class ColField implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Id
	@Column(type = ColumnType.INT, length = 11)
	private Integer iid;

	/**
	 * 数据库字段名称
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String fieldName;
	
	/**
	 * 所属网站ID，与网站表做关联
	 */
	@Column(type = ColumnType.INT, update = false, length = 11)
	private Integer siteId;
	
	/**
	 * 字段类型  ,key的值必须大于100
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer fieldKey; 

	/**
	 * 字段类型  1：子栏目展现方式   2：信息布局  3：信息列表展现方式  4：信息内容展现方式  5：互动类型
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer fieldType; 
	
	/**
	 * 列表显示
	 */
	@Column(type = ColumnType.INT, length = 1)
	private Integer showList;

	public Integer getIid() {
		return iid;
	}

	public void setIid(Integer iid) {
		this.iid = iid;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public Integer getFieldType() {
		return fieldType;
	}

	public void setFieldType(Integer fieldType) {
		this.fieldType = fieldType;
	}

	public Integer getShowList() {
		return showList;
	}

	public void setShowList(Integer showList) {
		this.showList = showList;
	}

	public Integer getFieldKey() {
		return fieldKey;
	}

	public void setFieldKey(Integer fieldKey) {
		this.fieldKey = fieldKey;
	}

}