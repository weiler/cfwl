package com.cfwl.androidapp.model;
import java.io.Serializable;
public class TopModel implements Serializable {
	/** serialVersionUID*/
	private static final long serialVersionUID = 1L;
	public Long id;

	public Long del;//删除状态
	public String and_or;//查询方式
	public String last_edit_person;//	最后修改人
	public String last_edit_time;//	最后修改时间
	public String getLast_edit_person() {
		return last_edit_person;
	}
	public void setLast_edit_person(String last_edit_person) {
		this.last_edit_person = last_edit_person;
	}
	public String getLast_edit_time() {
		return last_edit_time;
	}
	public void setLast_edit_time(String last_edit_time) {
		this.last_edit_time = last_edit_time;
	}
	public Long getDel() {
		return del;
	}
	public void setDel(Long del) {
		this.del = del;
	}
	public String getAnd_or() {
		return and_or;
	}
	public void setAnd_or(String and_or) {
		this.and_or = and_or;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
}