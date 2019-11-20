package com.example.demo.utils.common.entity;

import javax.persistence.Transient;
import java.io.Serializable;

/**
 * 
 * @描述: 基础实体类，包含各实体公用属性 .
 * @作者: WuShuicheng .
 * @创建时间: 2013-7-28,下午8:53:52 .
 * @版本: 1.0 .
 */
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Transient
	private Integer pageIndex;

	@Transient
	private Integer pageSize;

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}
