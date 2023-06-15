package com.web.test.Golf.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.test.Golf.mapper.GolfMapper;
import com.web.test.Golf.vo.Criteria;
import com.web.test.Golf.vo.GolfVo;
import com.web.test.Golf.vo.Reservation;
import com.web.test.Golf.vo.ReservationEvent;
import com.web.test.User.mapper.UserMapper;
import com.web.test.User.vo.UserVo;

import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;

@Service
public class GolfService {

	@Autowired
	private GolfMapper golfMapper;
	
	@Autowired
	private UserMapper userMapper;
		
	
	public List<GolfVo> getGolfListByUser(String user_id, Criteria cri) {
	    return golfMapper.getGolfListByUser(user_id, cri);
	}
	
	public List<UserVo> userInfo(Integer user_no) {
		return golfMapper.userInfo(user_no);
	}
	
	
	//페이징처리
	public int noticeListCnt() throws Exception {
		return golfMapper.noticeListCnt();
	}
	
	public List<GolfVo> noticeList(Criteria cri) {
		return golfMapper.noticeList(cri);
	}

	//검색하기
	public int searchListCnt(String user_id, String user_name, String searchCondition, String searchKeyword) {
		return golfMapper.searchListCnt(user_id, user_name, searchCondition, searchKeyword);
	}


	public List<GolfVo> searchNotices(String user_id, String user_name, String searchCondition, String searchKeyword, Criteria cri) {
    	return golfMapper.searchNotices(user_id, user_name, searchCondition, searchKeyword, cri);
    }
    
    public List<GolfVo> searchNoticesByTitle(String user_id,Criteria cri) throws Exception {
    	return golfMapper.searchNotices(user_id, null, null,  null,cri);
    }
	
    public List<GolfVo> searchNoticesByWriter(String user_name, Criteria cri) throws Exception{
    	return golfMapper.searchNotices(null, user_name, null,  null, cri);
    }
    
	//예약하기	
    public void insertReservation(GolfVo golfVo, String phone) {
        Reservation reservation = new Reservation();
        reservation.setUser_no(golfVo.getUser_no());
        reservation.setPlay_date(golfVo.getPlay_date());
        reservation.setLocation_name(golfVo.getLocation_name());
        reservation.setPlay_time(golfVo.getPlay_time());
        reservation.setPhone(phone);
        golfMapper.insertReservation(reservation);
    }
	
	public boolean isAvailable(LocalDate play_date, String play_time, String location_name) {
		GolfVo golfVo = new GolfVo();
		List<Reservation> reservationList = golfMapper.getReservationList(play_date,play_time, location_name);
	    for (Reservation reservation : reservationList) {
	        if (reservation.getPlay_time().equals(play_time)) {
	            return false; // 예약 시간이 중복되면 false 반환
	        }
	    }
	    return true; // 중복되지 않으면 true 반환
	}
	
	public void updateReservationTime(LocalDate play_date, String play_time, String location_name, boolean available) {
	    // 골프장 예약 시간 업데이트
	    Reservation reservation = new Reservation();
	    reservation.setLocation_name(location_name);
	    reservation.setPlay_date(play_date);
	    reservation.setPlay_time(play_time);
	    reservation.setAvailable(available);
	    
	    golfMapper.updateReservationTime(play_date, play_time, location_name);
	}


	public List<GolfVo> getAvailableTime(LocalDate play_date, String location_name) {
        return golfMapper.getAvailableTime(play_date, location_name);
    }

	public List<UserVo> getUserInfoByUserId(Integer user_no, String start, String end) {		
		return golfMapper.getUserInfoByUserId(user_no, start, end);
	}

	public List<UserVo> getUserReservationDates(Integer user_no ,String start, String end, Criteria cri) {
		List<UserVo> dates = golfMapper.getUserReservationDates(user_no ,start, end, cri);
	    return dates;
	}

	public List<String> getReservedTime(String location_name,LocalDate play_date) {
        return golfMapper.getReservedTime(location_name, play_date);
    }
	
	public void cancelReservation(String location_name, LocalDate play_date, String play_time) {
	    golfMapper.cancelReservation(location_name, play_date, play_time);
	}

	public void incrementUserCount(Integer user_no) {
		golfMapper.incrementUserCount(user_no);
	}

	public List<UserVo> getUserReservationCheck(String user_id, Criteria cri) {
		List<UserVo> dates = golfMapper.getUserReservationCheck(user_id, cri);
	    return dates;
	}

	public void deleteReservation(String user_id, String location_name, LocalDate play_date, String play_time) {
		golfMapper.deleteReservation(user_id, location_name, play_date, play_time);
	}

	public int getUserReservationDatesCount(Integer user_no, String start, String end) {
		return golfMapper.getUserReservationDatesCount(user_no, start, end);
	}

	public int getGolfListByUserCount(String user_id) {
		return golfMapper.getGolfListByUserCount(user_id);
	}

	public int getUserReservationCheckCount(String user_id) {
		return golfMapper.getUserReservationCheckCount(user_id);
	}

	public List<Reservation> getAllReservations() {
        return golfMapper.getAllReservations();
    }

	//예약 변경하기
	public void modifyReservation(String user_id, String location_name, LocalDate play_date, String play_time, Integer reservation_no) {
		golfMapper.modifyReservation(user_id, location_name, play_date, play_time, reservation_no);
	}

	public void updateText(Integer user_no, String text) {
		golfMapper.updateText(user_no, text);
	}

	public UserVo getUserData(Integer user_no, String start, String end) {
		return golfMapper.getUserData(user_no, start, end);
	}	

	public boolean updateEvent(String user_id, String eventTitle, LocalDate eventStart, LocalDate play_date, Integer reservation_no, String phone, String location_name, String play_time) {		
		return golfMapper.updateEvent(user_id, eventTitle, eventStart, play_date, reservation_no, phone,location_name, play_time);
	}

	
	
	

	public void updateGolfname(Reservation reservation) {
		golfMapper.updateGolfname(reservation);
	}

	public UserVo getUserInfoByUserNo(Integer user_no) {
		return golfMapper.getUserInfoByUserNo(user_no);
	}

	public String selectUserById(Integer reservation_no) {
		return golfMapper.selectUserById(reservation_no);
	}
	
	public void createReservation(String user_name, String location_name, String play_time, LocalDate play_date) {
		try {
		String phone = golfMapper.getUserPhoneByName(user_name);
		
		System.out.println("phone: " + phone);
		System.out.println("Creating reservation: user_name=" + user_name + ", location_name=" + location_name + ", play_time=" + play_time);
		Reservation reservation = new Reservation();
		reservation.setUser_name(user_name);
		reservation.setPlay_date(play_date);
	    reservation.setLocation_name(location_name);
	    reservation.setPlay_time(play_time);
	    reservation.setPhone(phone);
	    
	    System.out.println("phone: " + phone);
		golfMapper.createReservation(reservation);	
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void delete(Integer reservation_no) {
		golfMapper.delete(reservation_no);
	}

	public void createNonMemberReservation(String user_name,String location_name, String play_time, LocalDate play_date) {
		try {
			
			String phone = golfMapper.getUserPhoneByName(user_name);
			
			System.out.println("phone: " + phone);
			System.out.println("Creating reservation: user_name=" + user_name + ", location_name=" + location_name + ", play_time=" + play_time);
			Reservation reservation = new Reservation();
			reservation.setUser_name(user_name);
			reservation.setPlay_date(play_date);
		    reservation.setLocation_name(location_name);
		    reservation.setPlay_time(play_time);
		    
			golfMapper.createNonMemberReservation(reservation);	
			} catch(Exception e) {
				e.printStackTrace();
			}
	}

	public Integer getUserByName(String user_name) {
		return golfMapper.getUserByName(user_name);
	}

	public void insertUser(UserVo userVo){
		golfMapper.insertUser(userVo);
	}

	public Integer getUserNoByUserName(String user_name) {
		return golfMapper.getUserNoByUserName(user_name);
	}

	public void multiCreate(String user_name, String location_name, String play_time, LocalDate play_date, String phone) {
		try {			
			Reservation reservation = new Reservation();
			reservation.setUser_name(user_name);
			reservation.setPlay_date(play_date);
		    reservation.setLocation_name(location_name);
		    reservation.setPlay_time(play_time);
		    
		    golfMapper.getUserPhone(user_name, phone);
		    
		    reservation.setPhone(phone);
		    System.out.println("phone: " + phone);
			System.out.println("Creating reservation: user_name=" + user_name + ", location_name=" + location_name + ", play_time=" + play_time + ", phone: " + phone);
			
		    
			golfMapper.multiCreate(reservation);	
			} catch(Exception e) {
				e.printStackTrace();
			}
		
	}	
}


