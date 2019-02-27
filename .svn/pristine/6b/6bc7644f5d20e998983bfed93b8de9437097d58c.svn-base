package com.hanweb.jmp.cms.entity.cols;

import java.io.Serializable;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;

import com.hanweb.jmp.constant.Tables;

/**
 * 栏目和任务对应关系实体
 * @author hq
 */
@Table(name = Tables.COLRELATION)
public class ColRelation implements Serializable {

	private static final long serialVersionUID = -5510079678174744759L;

	/**
	 * 主键
	 */
	@Id
	@Column(type = ColumnType.INT, length = 11)
	private Integer iid;
	
	/**
	 * 栏目id
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer colId;
	
	/**
	 * 栏目名称
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String colName;
	
	/**
	 * 任务id
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer taskId;
	
	/**
	 * 任务名称
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String taskName;

	public Integer getIid() {
		return iid;
	}

	public void setIid(Integer iid) {
		this.iid = iid;
	}

	public Integer getColId() {
		return colId;
	}

	public void setColId(Integer colId) {
		this.colId = colId;
	}

	public String getColName() {
		return colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

}