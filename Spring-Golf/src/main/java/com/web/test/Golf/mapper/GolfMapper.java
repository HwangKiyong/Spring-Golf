package com.web.test.Golf.mapper;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.web.test.Golf.vo.Criteria;
import com.web.test.Golf.vo.GolfVo;
import com.web.test.Golf.vo.Reservation;
import com.web.test.Golf.vo.ReservationEvent;
import com.web.test.User.vo.UserVo;

import net.nurigo.sdk.message.response.SingleMessageSentResponse;

@Mapper
public interface GolfMapper {

//	List<Reservation> getMonthlyReservationTotals = null;

	List<GolfVo> getGolfListByUser(@Param("user_id")String user_id, Criteria cri);	
	
	List<UserVo> userInfo(Integer user_no);
	//페이징 처리
	public List<GolfVo> noticeList(Criteria cri);
	
	public int noticeListCnt() throws Exception;
	
	//검색결과
	int searchListCnt(String user_id, String user_name, String searchCondition, String searchKeyword);

	public List<GolfVo> searchNotices(@Param("user_id")String user_id, @Param("user_name")String user_name, @Param("searchCondition") String searchCondition, @Param("searchKeyword")String searchKeyword, Criteria cri);

	
	
	//카운트 올리기
	void incrementUserCount(Integer user_no);

	void insertReservation(Reservation reservation);	
	
	List<Reservation> getReservationList(@Param("play_date") LocalDate play_date,@Param("play_time") String play_time, @Param("location_name") String location_name);
	

	List<GolfVo> getAvailableTime(@Param("play_date") LocalDate play_date, @Param("location_name") String location_name);

	
    void updateReservationTime(@Param("play_date") LocalDate play_date,@Param("play_time") String play_time, @Param("location_name") String location_name);

	List<UserVo> getUserInfoByUserId(Integer user_no, @Param("start") String start, @Param("end") String end);

	List<UserVo> getUserReservationDates(Integer user_no, String start, String end, Criteria cri);

	List<String> getReservedTime(@Param("location_name") String location_name, @Param("play_date")LocalDate play_date);

	void cancelReservation(String location_name, LocalDate play_date, String play_time);

	List<UserVo> getUserReservationCheck(String user_id, Criteria cri);

	void deleteReservation(String user_id, String location_name, LocalDate play_date, String play_time);

	int getUserReservationDatesCount(Integer user_no, String start, String end);

	int getGolfListByUserCount(String user_id);

	int getUserReservationCheckCount(String user_id);
	
	List<Reservation> getAllReservations();

	void modifyReservation(String user_id, String location_name, LocalDate play_date, String play_time, Integer reservation_no);

	void updateText(Integer user_no, String text);

	UserVo getUserData(Integer user_no, String start, String end);

	

	boolean updateEvent(String user_id, String eventTitle, LocalDate eventStart, LocalDate play_date, Integer reservation_no, String phone, String location_name, String play_time);

	String selectUserById(Integer reservation_no);

	void updateGolfname(Reservation reservation);

	UserVo getUserInfoByUserNo(Integer user_no);	

	void createReservation(Reservation reservation);

	String getUserPhoneByName(String user_name);

	void delete(Integer reservation_no);

	void createNonMemberReservation(Reservation reservation);

	Integer getUserByName(String user_name);

	void insertUser(UserVo userVo);

	Integer getUserNoByUserName(String user_name);

	void multiCreate(Reservation reservation);

	String getUserPhone(String user_name, String phone);

}
