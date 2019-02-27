package com.hanweb.jmp.sys.entity.log;

import java.io.Serializable;
import java.util.Date;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;

import com.hanweb.jmp.constant.Tables;

/**
 * 接口日志实体
 * @author qzq
 */ 
@Table(name = Tables.INTERFACE_LOG)
public class InterfaceLog implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2788013023867896028L;

	/**
	 * 主键
	 */
	@Id
	@Column(type = ColumnType.INT, length = 11, update = false)
	private Integer iid;
	
	/**
	 * 网站id
	 */
	@Column(type = ColumnType.INT, length = 11, update = false)
	private Integer siteId;
	
	/**
	 * 客户端类型
	 */
	@Column(type = ColumnType.INT, length = 11, update = false)
	private Integer clientType;
	
	/**
	 * 接口名字
	 */
	@Column(type = ColumnType.VARCHAR, length = 255, update = false)
	private String name;
	
	/**
	 * 接口编号
	 */
	@Column(type = ColumnType.VARCHAR, length = 255, update = false)
	private String ino;
	
	/**
	 * 操作时间
	 */
	@Column(type = ColumnType.DATETIME, update = false)
	private Date oprTime;
	
	/**
	 * 手机唯一码
	 */
	@Column(type = ColumnType.VARCHAR, length = 255, update = false)
	private String uuid;
	
	/**
	 * 手机唯一码(加密后)
	 */ 
	private String uuidmd5;
	
	/**
	 * 版本号
	 */
	@Column(type = ColumnType.VARCHAR, length = 255, update = false)
	private String version;
	
	/**
	 * 导航id
	 */
	@Column(type = ColumnType.INT, length = 11, update = false)
	private Integer chanId;
	
	/**
	 * 栏目id
	 */
	@Column(type = ColumnType.INT, length = 11, update = false)
	private Integer colId;
	
	/**
	 * 信息id
	 */
	@Column(type = ColumnType.INT, length = 11, update = false)
	private Integer infoId;
	
	/**
	 * 时段
	 */
	@Column(type = ColumnType.INT, length = 11, update = false)
	private Integer hour;
	
	/**
	 * 接口名字
	 */
	private String interfaceName;
	
	/**
	 * 每个接口被调用的次数
	 */
	private Integer num;

	/**
	 * 访问客户端的个数
	 */
	private Integer amount ;

	
	public Integer getIid() {
		return iid;
	}

	public void setIid(Integer iid) {
		this.iid = iid;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public Integer getClientType() {
		return clientType;
	}

	public void setClientType(Integer clientType) {
		this.clientType = clientType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIno() {
		return ino;
	}

	public void setIno(String ino) {
		this.ino = ino;
	}

	public Date getOprTime() {
		return oprTime;
	}

	public void setOprTime(Date oprTime) {
		this.oprTime = oprTime;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	
	public String getUuidmd5() {
		return uuidmd5;
	}

	public void setUuidmd5(String uuidmd5) {
		this.uuidmd5 = uuidmd5;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Integer getChanId() {
		return chanId;
	}

	public void setChanId(Integer chanId) {
		this.chanId = chanId;
	}

	public Integer getColId() {
		return colId;
	}

	public void setColId(Integer colId) {
		this.colId = colId;
	}

	public Integer getInfoId() {
		return infoId;
	}

	public void setInfoId(Integer infoId) {
		this.infoId = infoId;
	}

	public Integer getHour() {
		return hour;
	}

	public void setHour(Integer hour) {
		this.hour = hour;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

}