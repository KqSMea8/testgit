package com.hanweb.jmp.sys.entity.log;

import java.io.Serializable;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;

import com.hanweb.jmp.constant.Tables;

/**
 * 日志配置表操作日志实体
 * @author hq
 */
@Table(name = Tables.LOGCONFIG)
public class LogConfig implements Serializable {

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 1569324184206561041L;

	/**
	 * 主键
	 */
	@Id
	@Column(type = ColumnType.INT, length = 11)
	private Integer iid;

	/**
	 * 模块id
	 */
	@Column(type = ColumnType.INT, length = 5)
	private Integer moduleId;

	/**
	 * 模块名字
	 */
	@Column(type = ColumnType.VARCHAR, length = 100)
	private String moduleName;

	/**
	 * 功能id
	 */
	@Column(type = ColumnType.INT, length = 5)
	private Integer funcId;

	/**
	 * 功能名字
	 */
	@Column(type = ColumnType.VARCHAR, length = 100)
	private String funcName;

	/**
	 * 是否记录日志
	 */
	@Column(type = ColumnType.INT, length = 1)
	private Integer isLog;

	/**
	 * 排序id
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer orderId;

	public Integer getIid() {
		return iid;
	}

	public void setIid(Integer iid) {
		this.iid = iid;
	}

	public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public Integer getFuncId() {
		return funcId;
	}

	public void setFuncId(Integer funcId) {
		this.funcId = funcId;
	}

	public String getFuncName() {
		return funcName;
	}

	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}

	public Integer getIsLog() {
		return isLog;
	}

	public void setIsLog(Integer isLog) {
		this.isLog = isLog;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

}