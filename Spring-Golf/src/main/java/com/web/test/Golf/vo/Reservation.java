package com.web.test.Golf.vo;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "TB_RESERVATION")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RESERVATION_NO")
    private Integer reservation_no;

    @Column(name = "USER_NO")
    private Integer user_no;

    @Column(name = "PLAY_DATE")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate play_date;
    
    @Column(name = "PLAY_TIME")    
    private String play_time;
    
    @Column(name = "LOCATION_NAME")
    private String location_name;
    
    @Column(name = "GOLF_NAME")
    private String golf_name;
    
    @Column(name = "USER_ID")
    private String user_id;
    
    @Column(name = "USER_NAME")
    private String user_name;
    
    @Column(name = "PHONE")
    private String phone;
    
    @Column(name = "ISMODIFIED")
    private String ISMODIFIED;
    
    @Column(name = "ISAVAILABLE", columnDefinition = "BIT", length = 1)
    @Type(type = "org.hibernate.type.BooleanType")
    @org.hibernate.annotations.ColumnDefault("false")
    private boolean isAvailable;

    
    public Reservation() {}


	public Reservation(Integer reservation_no, Integer user_no, LocalDate play_date, String play_time, String location_name, String golf_name,
			String user_id, String user_name, String phone, boolean isAvailable, String ISMODIFIED) {
		super();
		this.reservation_no = reservation_no;
		this.user_no = user_no;
		this.play_date = play_date;
		this.play_time = play_time;
		this.location_name = location_name;
		this.golf_name = golf_name;
		this.user_id = user_id;
		this.user_name = user_name;
		this.phone = phone;
		this.isAvailable = isAvailable;
		this.ISMODIFIED = ISMODIFIED;
	}


	public Integer getReservation_no() {
		return reservation_no;
	}


	public void setReservation_no(Integer reservation_no) {
		this.reservation_no = reservation_no;
	}


	public Integer getUser_no() {
		return user_no;
	}


	public void setUser_no(Integer user_no) {
		this.user_no = user_no;
	}


	public LocalDate getPlay_date() {
		return play_date;
	}


	public void setPlay_date(LocalDate play_date) {
		this.play_date = play_date;
	}


	public String getPlay_time() {
		return play_time;
	}


	public void setPlay_time(String play_time) {
		this.play_time = play_time;
	}


	public String getLocation_name() {
		return location_name;
	}


	public void setLocation_name(String location_name) {
		this.location_name = location_name;
	}

	public String getGolf_name() {
		return golf_name;
	}


	public void setGolf_name(String golf_name) {
		this.golf_name = golf_name;
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

	

	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public boolean isAvailable() {
		return isAvailable;
	}


	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public String getISMODIFIED() {
		return ISMODIFIED;
	}


	public void setISMODIFIED(String iSMODIFIED) {
		ISMODIFIED = iSMODIFIED;
	}


	@Override
	public String toString() {
		return "Reservation [reservation_no=" + reservation_no + ", user_no=" + user_no + ", play_date=" + play_date
				+ ", play_time=" + play_time + ", location_name=" + location_name + ", golf_name=" + golf_name
				+ ", user_id=" + user_id + ", user_name=" + user_name + ", phone=" + phone + ", ISMODIFIED="
				+ ISMODIFIED + ", isAvailable=" + isAvailable + "]";
	}
	
}
