package com.web.test.Golf.vo;

import java.time.LocalDate;

public class ReservationEvent{
	private String user_id;
    private String title;
    private LocalDate start;
    private String user_name; 
    private ExtendedProps extendedProps;   
    private Reservation reservation;
    private Integer reservation_no;
    private Integer user_no;
    private String phone;
    private String backgroundColor;
    private String ISMODIFIED;
    
    public ReservationEvent(String user_id, String title, LocalDate start, String user_name, ExtendedProps extendedProps, Reservation reservation, Integer reservation_no, Integer user_no, String phone, String backgroundColor, String ISMODIFIED) {
		super();
		this.user_id = user_id;
		this.title = title;
		this.start = start;
		this.user_name = user_name;
		this.extendedProps = extendedProps;
		this.reservation = reservation;
		this.reservation_no= reservation_no;
		this.user_no = user_no;
		this.phone = phone;
		this.ISMODIFIED = ISMODIFIED;
	}
     
    // 생성자를 통해 예약 정보를 받아서 할당
    public ReservationEvent(Reservation reservation) {
        this.reservation = reservation;
    }
    
	public Reservation getReservation() {
		return this.reservation;
	}



	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	public ReservationEvent() {
		this.extendedProps = new ExtendedProps();
	}


	public String getUser_id() {
		return user_id;
	}


	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDate getStart() {
		return start;
	}

	public void setStart(LocalDate start) {
		this.start = start;
	}
	
	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public ExtendedProps getExtendedProps() {
		return extendedProps;
	}

	public void setExtendedProps(ExtendedProps extendedProps) {
		this.extendedProps = extendedProps;
		this.phone = extendedProps.getPhone();
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	

	public String getISMODIFIED() {
		return ISMODIFIED;
	}

	public void setISMODIFIED(String iSMODIFIED) {
		ISMODIFIED = iSMODIFIED;
	}

	@Override
	public String toString() {
		return "ReservationEvent [user_id=" + user_id + ", title=" + title + ", start=" + start + ", user_name="
				+ user_name + ", extendedProps=" + extendedProps + ", reservation=" + reservation + ", reservation_no="
				+ reservation_no + ", user_no=" + user_no + ", phone=" + phone + ", backgroundColor=" + backgroundColor
				+ ", ISMODIFIED=" + ISMODIFIED + "]";
	}

	public static class ExtendedProps {
		private String user_id;
        private String play_time;
        private String location_name;  
        private LocalDate play_date;
        private Integer reservation_no;
        private Integer user_no;
        private String phone;
        
        public ExtendedProps(String user_id, String play_time, String location_name, LocalDate play_date,Integer reservation_no,Integer user_no, String phone) {
            this.user_id = user_id;
        	this.play_time = play_time;
            this.location_name = location_name;
            this.play_date = play_date;
            this.reservation_no = reservation_no;
            this.user_no = user_no;
            this.phone = phone;
        }

		public ExtendedProps() {}

		public String getUser_id() {
			return user_id;
		}

		public void setUser_id(String user_id) {
			this.user_id = user_id;
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

		public LocalDate getPlay_date() {
			return play_date;
		}

		public void setPlay_date(LocalDate play_date) {
			this.play_date = play_date;
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
		

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		@Override
		public String toString() {
			return "ExtendedProps [user_id=" + user_id + ", play_time=" + play_time + ", location_name=" + location_name
					+ ", play_date=" + play_date + ", reservation_no=" + reservation_no + ", user_no=" + user_no
					+ ", phone=" + phone + "]";
		}				      
      }
}
