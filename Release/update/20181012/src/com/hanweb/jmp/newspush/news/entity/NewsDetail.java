package com.hanweb.jmp.newspush.news.entity;

import java.io.Serializable;
import java.util.Date;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Index;
import com.hanweb.common.annotation.Indexes;
import com.hanweb.common.annotation.Table;
import com.hanweb.jmp.constant.Tables;


/**
 * 消息详情实体
 * 
 * @author Wangjw
 * 
 */
@Table(name = Tables.INFODETAIL)
@Indexes({
    @Index(indexName="infodetail_01", fieldNames={"infoid"})
})
public class NewsDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3564650170432551318L;
	
	/**
	 * 主键
	 */
	@Id
	@Column(type = ColumnType.INT)
	private Integer iid;
	
	/**
	 * 消息id
	 */
	@Column(type = ColumnType.INT)
	private Integer infoId;
	
	/**
	 * 用户id
	 */
	@Column(type = ColumnType.INT)
	private Integer usid;
	
	/**
	 * 0：未送达1：已送达2：已阅读
	 */
	@Column(type = ColumnType.INT, length = 1)
	private Integer state;
	
	/**
	 * 回执
	 */
	@Column(type = ColumnType.VARCHAR, length = 1000)
	private String receipt;
	
	/**
	 * 查看时间
	 */
	@Column(type = ColumnType.DATETIME)
	private Date receiptTime;
	
	/**
	 * 发送时间
	 */
	@Column(type = ColumnType.DATETIME)
	private Date sendTime;
	
	/**
	 * 消息类型 1：公有 2：私有
	 */
	@Column(type = ColumnType.INT)
	private Integer infoKind;

	public Integer getIid() {
		return this.iid;
	}

	public void setIid(Integer iid) {
		this.iid = iid;
	}

	public Integer getInfoId() {
		return this.infoId;
	}

	public void setInfoId(Integer infoId) {
		this.infoId = infoId;
	}

	public Integer getUsid() {
		return this.usid;
	}

	public void setUsid(Integer usid) {
		this.usid = usid;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getReceipt() {
		return this.receipt;
	}

	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}

	public Date getReceiptTime() {
		return this.receiptTime;
	}

	public void setReceiptTime(Date receiptTime) {
		this.receiptTime = receiptTime;
	}

	public Date getSendTime() {
		return this.sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Integer getInfoKind() {
		return infoKind;
	}

	public void setInfoKind(Integer infoKind) {
		this.infoKind = infoKind;
	}

}
