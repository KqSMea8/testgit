package com.hanweb.jmp.global.entity;

import java.io.Serializable;
import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;
import com.hanweb.jmp.constant.Tables;

/**
 * 缓存数据表
 * @author hq
 */
@Table(name = Tables.CACHEDATA)
public class CaheData implements Serializable {

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = -8177266631659256821L;

	/**
	 * 主键
	 */
	@Id
	@Column(type = ColumnType.INT, length = 11)
	private Integer iid;

	/**
	 * 用户id
	 */
	@Column(type = ColumnType.VARCHAR, length = 1000)
	private String  name;

	/**
	 *用户名字
	 */
	@Column(type = ColumnType.VARCHAR, length = 1000)
	private String spec;

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

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}
	
}