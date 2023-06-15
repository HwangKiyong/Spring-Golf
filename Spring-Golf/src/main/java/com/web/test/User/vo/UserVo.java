package com.web.test.User.vo;

import com.web.test.Golf.vo.Reservation;

public class UserVo extends Reservation{
	private Integer user_no;
	private String user_id;	
	public String user_name;
	private String user_pwd;
	private String user_nickname;
	private String manager_yn;
	private Integer user_count;
	private int total_count;
    private int period_count;
    private String text;
    private String phone;
    
	
	public UserVo() {}

	public UserVo(Integer user_no, String user_id, String user_name, String user_pwd, String user_nickname,
			String manager_yn, Integer user_count, int total_count, int period_count, String text, String phone) {
		super();
		this.user_no = user_no;
		this.user_id = user_id;
		this.user_name = user_name;
		this.user_pwd = user_pwd;
		this.user_nickname = user_nickname;
		this.manager_yn = manager_yn;
		this.user_count = user_count;
		this.total_count = total_count;
		this.period_count = period_count;
		this.text = text;
		this.phone = phone;
	}

	public Integer getUser_no() {
		return user_no;
	}

	public void setUser_no(Integer user_no) {
		this.user_no = user_no;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_pwd() {
		return user_pwd;
	}

	public void setUser_pwd(String user_pwd) {
		this.user_pwd = user_pwd;
	}

	public String getUser_nickname() {
		return user_nickname;
	}

	public void setUser_nickname(String user_nickname) {
		this.user_nickname = user_nickname;
	}

	public String getManager_yn() {
		return manager_yn;
	}

	public void setManager_yn(String manager_yn) {
		this.manager_yn = manager_yn;
	}

	public Integer getUser_count() {
		return user_count;
	}

	public void setUser_count(Integer user_count) {
		this.user_count = user_count;
	}

	public int getTotal_count() {
		return total_count;
	}

	public void setTotal_count(int total_count) {
		this.total_count = total_count;
	}

	public int getPeriod_count() {
		return period_count;
	}

	public void setPeriod_count(int period_count) {
		this.period_count = period_count;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "UserVo [user_no=" + user_no + ", user_id=" + user_id + ", user_name=" + user_name + ", user_pwd="
				+ user_pwd + ", user_nickname=" + user_nickname + ", manager_yn=" + manager_yn + ", user_count="
				+ user_count + ", total_count=" + total_count + ", period_count=" + period_count + ", text=" + text
				+ ", phone=" + phone + "]";
	}		
}
