package com.hanweb.jmp.cms.entity.infos;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;
import com.hanweb.common.annotation.Index;
import com.hanweb.common.annotation.Indexes;

import com.hanweb.jmp.constant.Tables;

@Table(name = Tables.INFO_COUNT) 
@Indexes({
	@Index(fieldNames={"titleid"}, indexName="infocount_idx_001")
})
public class InfoCount {

	/**
	 * 主键id
	 */
	@Id
	@Column(type = ColumnType.INT)
	private Integer iid;
	
	/**
	 * 信息id
	 */
	@Column(type = ColumnType.INT)
	private Integer titleId;
	
	/**
	 * 访问次数
	 */
	@Column(type = ColumnType.INT)
	private Integer visitCount = 0;
	
	/**
	 * 评论数
	 */
	@Column(type = ColumnType.INT)
	private Integer commentCount = 0;
	
	/**
	 * 点赞数
	 */
	@Column(type = ColumnType.INT)
	private Integer goodCount = 0;
	
	/**
	 * 类型 1是信息 2是报料
	 */
	@Column(type = ColumnType.INT)
	private Integer type = 1;
	
	/**
	 * 是否被点赞
	 */
	private Integer isGood = 0;

	public Integer getIid() {
		return iid;
	}

	public void setIid(Integer iid) {
		this.iid = iid;
	}

	public Integer getTitleId() {
		return titleId;
	}

	public void setTitleId(Integer titleId) {
		this.titleId = titleId;
	}

	public Integer getVisitCount() {
		return visitCount;
	}

	public void setVisitCount(Integer visitCount) {
		this.visitCount = visitCount;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public Integer getGoodCount() {
		return goodCount;
	}

	public void setGoodCount(Integer goodCount) {
		this.goodCount = goodCount;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getIsGood() {
		return isGood;
	}

	public void setIsGood(Integer isGood) {
		this.isGood = isGood;
	}
	
}