package com.hanweb.jmp.sys.entity.field;

import java.io.Serializable; 

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;

import com.hanweb.jmp.constant.Tables;

/**
 * 字段实体 
 *
 */
@Table(name = Tables.FIELD)
public class Field implements Serializable {

	private static final long serialVersionUID = 8020206996027596982L;

	/**
	 * 主键
	 */
	@Id
	@Column(type = ColumnType.INT, length = 11)
	private Integer iid;

	/**
	 * 字段显示名称
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String name;
	
	/**
	 * 数据库字段名称
	 */
	@Column(type = ColumnType.VARCHAR, update = false, length = 255)
	private String fieldName;

	/**
	 * jget同步字段名称
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String jgetName;
	 
	/**
	 * 所属网站ID，与网站表做关联
	 */
	@Column(type = ColumnType.INT, update = false, length = 11)
	private Integer siteId;
	
	/**
	 * 排序id
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer orderId;

	/**
	 * 字段类型   0从属于一个网站的自定义字段    1全局自定义字段   2系统默认字段
	 */
	@Column(type = ColumnType.INT, length = 1)
	private Integer fieldType=0; 
	
	/**
	 * 字段长度
	 */
	@Column(type = ColumnType.INT, update = false, length = 11)
	private Integer fieldLength;
	
	/**
	 * 列表显示
	 */
	@Column(type = ColumnType.INT, length = 1)
	private Integer showList=0; 
	 
	public Integer getIid() {
		return iid;
	}

	public void setIid(Integer iid) {
		this.iid = iid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getJgetName() {
		return jgetName;
	}

	public void setJgetName(String jgetName) {
		this.jgetName = jgetName;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	} 

	public Integer getFieldType() {
		return fieldType;
	}

	public void setFieldType(Integer fieldType) {
		this.fieldType = fieldType;
	} 
	
	public Integer getFieldLength() {
		return fieldLength;
	}

	public void setFieldLength(Integer fieldLength) {
		this.fieldLength = fieldLength;
	}

	public Integer getShowList() {
		return showList;
	}

	public void setShowList(Integer showList) {
		this.showList = showList;
	} 
	
}