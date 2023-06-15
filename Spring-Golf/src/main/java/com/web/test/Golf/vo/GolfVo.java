package com.web.test.Golf.vo;

import java.sql.Date;

import com.web.test.User.vo.UserVo;


public class GolfVo extends Reservation{
    private Integer golf_no;
    private String location_name;
    private String golf_nickname;
    private UserVo userVo;
    
    public GolfVo() {}
    
    public GolfVo(Date play_date, String play_time, String location_name, UserVo userVo) {
    	this.setPlay_date(getPlay_date());
    	this.setPlay_time(play_time);
    	this.location_name = location_name;
    	this.userVo = userVo;
    }
    
    public UserVo getUserVo() {
		return userVo;
	}

	public void setUserVo(UserVo userVo) {
		this.userVo = userVo;
	}

	public GolfVo(Integer user_no, String user_id, String user_name, String user_pwd, String user_nickname,
                  String manager_yn, Integer user_count, Date playdate, Integer golf_no, String location_name,
                  String golf_nickname) {
        super();
        this.golf_no = golf_no;
        this.location_name = location_name;
        this.golf_nickname = golf_nickname;
               
    }

    public Integer getGolf_no() {
        return golf_no;
    }

    public void setGolf_no(Integer golf_no) {
        this.golf_no = golf_no;
    }

    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }

    public String getGolf_nickname() {
        return golf_nickname;
    }

    public void setGolf_nickname(String golf_nickname) {
        this.golf_nickname = golf_nickname;
    }

   

    public String getUser_name() {
        return super.getUser_name();
    }

    public void setUser_name(String user_name) {
        super.setUser_name(user_name);
    }

	
    
    
}