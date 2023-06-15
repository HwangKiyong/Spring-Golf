package com.web.test.Golf.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.web.test.Golf.service.GolfService;
import com.web.test.Golf.vo.Criteria;
import com.web.test.Golf.vo.GolfVo;
import com.web.test.Golf.vo.Paging;
import com.web.test.Golf.vo.Reservation;
import com.web.test.Golf.vo.ReservationEvent;
import com.web.test.Golf.vo.ReservationEvent.ExtendedProps;
import com.web.test.User.vo.UserVo;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;

@Controller
public class GolfController {

	DefaultMessageService messageService;

	@Autowired
	private GolfService golfService;

	// 사용자 정보보기
	@GetMapping("/list")
	public String getGolfListByDate(Model model, Criteria cri, HttpSession session) throws Exception {

		String user_id = (String) session.getAttribute("user_id");
		String start = (String) session.getAttribute("start");
		String end = (String) session.getAttribute("end");

		Paging paging = new Paging(cri, golfService.getGolfListByUserCount(user_id));

		cri.setPerPageNum(5); // 페이지당 보여줄 개수 설정
		cri.setTotalCount(golfService.getGolfListByUserCount(user_id));

		List<GolfVo> golf = golfService.noticeList(cri);
		List<GolfVo> golfList = golfService.getGolfListByUser(user_id, cri);

		System.out.println(cri.getPageStart());
		System.out.println(cri.getPageEnd());

		model.addAttribute("list", golf);
		model.addAttribute("paging", paging);
		model.addAttribute("start", start);
		model.addAttribute("end", end);
		model.addAttribute("golfList", golfList);

		System.out.println(golfList);

		return "/notice/golfList";
	}

	// 유저 정보보기
	@RequestMapping("/logInfo")
	public String logInfo(Model model, @RequestParam(value = "text", required = false) String text,
			@RequestParam(value = "user_no", required = false)Integer user_no,
			@RequestParam(value = "user_id", required = false) String user_id, @ModelAttribute("userVo") UserVo userVo,
			@RequestParam(value = "start", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") String start,
			@RequestParam(value = "end", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") String end,
			HttpSession session) throws Exception {

		userVo.setUser_id(user_id); // UserVo 객체에 user_id 설정
		
		System.out.println(user_id);
		System.out.println(user_no);
		System.out.println(userVo);

		List<UserVo> user = golfService.getUserInfoByUserId(user_no, start, end);
		model.addAttribute("user", user);
		System.out.println(user);

		session.setAttribute("user_no", user_no);
		session.setAttribute("user_id", user_id);

		session.setAttribute("start", start);
		session.setAttribute("end", end);

		model.addAttribute("user", user);
		model.addAttribute("start", start);
		model.addAttribute("end", end);
		return "/user/logInfo";
	}

	// 비고란 작성
	@RequestMapping(value = "/saveRemarks", method = RequestMethod.POST)
	@ResponseBody
	public String saveRemarks(@RequestParam("text") String text,
			@RequestParam(value = "start", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") String start,
			@RequestParam(value = "end", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") String end,
			Model model, @ModelAttribute("userVo") UserVo userVo, HttpSession session) throws Exception {

		Integer user_no = (Integer) session.getAttribute("user_no");

		try {
			System.out.println("USER_NO" + user_no);
			System.out.println("text" + text);
			golfService.updateText(user_no, text);
			// user_id에 빈값으로 나옴
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	// 유저데이터 가져오기
	@RequestMapping(value = "/getUserData", method = RequestMethod.GET)
	@ResponseBody
	public UserVo getUserData(
			@RequestParam(value = "start", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") String start,
			@RequestParam(value = "end", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") String end,
			Model model, @ModelAttribute("userVo") UserVo userVo, HttpSession session) {

		Integer user_no = (Integer) session.getAttribute("user_no");

		try {
			UserVo userData = golfService.getUserData(user_no, start, end);
			System.out.println("user_no" + user_no);
			return golfService.getUserData(user_no, start, end);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// 유저 이용 날짜 이용골프장 보여주기
	@GetMapping("/reservationDates")
	public String userReservationDates(Model model, HttpSession session, Criteria cri) throws Exception {
		
		Integer user_no = (Integer) session.getAttribute("user_no");
		String start = (String) session.getAttribute("start");
		String end = (String) session.getAttribute("end");

		cri.setPerPageNum(5); // 페이지당 보여줄 개수 설정
		cri.setTotalCount(golfService.getUserReservationDatesCount(user_no, start, end));

		List<UserVo> dates = golfService.getUserReservationDates(user_no, start, end, cri);
		List<GolfVo> list = golfService.noticeList(cri);

		if (dates == null) {
			dates = new ArrayList<>();
		}

		Paging paging = new Paging(cri, golfService.getUserReservationDatesCount(user_no, start, end));

		System.out.println(cri.getPageStart());
		System.out.println(cri.getPageEnd());

		model.addAttribute("dates", dates);
		model.addAttribute("list", list);
		model.addAttribute("paging", paging);

		return "notice/reservationDates";
	}

	// 예약하기
	@RequestMapping(value = "/reserve", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String reserve(@ModelAttribute GolfVo golfVo, HttpSession session, Model model) {
		UserVo user = (UserVo) session.getAttribute("user");
		LocalDate play_date = golfVo.getPlay_date();
		String play_time = golfVo.getPlay_time();
		String location_name = golfVo.getLocation_name();

		System.out.println(user);

		if (user == null) {
			return "redirect:/login";
		} else {
			Integer user_no = user.getUser_no();
			String phone = user.getPhone();
			System.out.println(user.getUser_no());

			System.out.println(user.getUser_no());
			golfVo.setAvailable(true); // 예약 가능 여부 설정
			golfVo.setUser_no(user_no); // golfVo에 user_no 설정
			golfVo.setPhone(phone);
			golfService.insertReservation(golfVo, phone);
			System.out.println(user.getPhone());
			System.out.println("insert 부분!");
			// 예약 시간 삭제
			golfService.updateReservationTime(play_date, play_time, location_name, false);

			golfService.incrementUserCount(user_no);		
			
			System.out.println(play_time);

			if (play_time.equals("07:00")) {
				play_time = "1부(07:00~12:00)";
			} else {
				play_time = "2부(13:00~18:00)";
			}
			
			// CoolSMS 초기화
			this.messageService = NurigoApp.INSTANCE.initialize("NCSJN08KXCIXWEQT", "JUJJ0ABGZAPKS0ZLFBR2SBWBZGBTWQMG",
					"https://api.coolsms.co.kr");

			try {						
				
				// 문자 내용 구성 // 예약자의 휴대폰 번호
				String messageContent = "예약이 신청이왔습니다." + "\n" + "예약 정보: " + "\n" + "유저이름:" + user.user_name + "\n"
						+ "예약날짜: " + golfVo.getPlay_date() + "\n" + "예약시간: " + play_time + "\n" + "지역: "
						+ golfVo.getLocation_name() + "\n" + "전화번호:" + user.getPhone();

				// 문자 발송
				Message message = new Message();
				message.setFrom("01084602389"); // 발신 번호
				message.setTo("01084602389"); // 수신 번호
				message.setText(messageContent);

				SingleMessageSentResponse response = this.messageService
						.sendOne(new SingleMessageSendingRequest(message));

				// 발송 결과 확인
				System.out.println("SMS 발송 결과: " + response.toString());
			} catch (Exception e) {
				// 예외 처리
				e.printStackTrace();
				return "error";
			}

			return "success";
		}
	}

	@RequestMapping("/get_available_time")
	public List<GolfVo> getAvailableTime(
			@RequestParam("play_date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate play_date,
			@RequestParam("location_name") String location_name) {
		return golfService.getAvailableTime(play_date, location_name);
	}

	// 예약 결과 페이지
	@RequestMapping("/reservation_result")
	public String reservationResult() {
		return "user/reservation_result";
	}

	// 예약 페이지 보여주기
	@RequestMapping(value = "/reservePage", method = { RequestMethod.GET, RequestMethod.POST })
	public String reservePage(Model model) {
		LocalDate today = LocalDate.now(); // 현재 날짜 가져오기
		String location_name = "골프장 이름"; // 골프장 이름 설정
		List<GolfVo> availableTimes = golfService.getAvailableTime(today, location_name); // 오늘 날짜와 골프장 이름을 기준으로 가능한 시간대
																							// 목록 가져오기
		List<String> reservedTimeList = golfService.getReservedTime(location_name, today); // 골프장 이름과 오늘 날짜를 기준으로 예약된 시간
																							// 목록 가져오기
		Set<String> reservedTimeSet = new HashSet<>(reservedTimeList); // 예약된 시간을 중복 제거하기 위해 Set으로 변환

		// 중복 예약된 시간 비활성화 처리
		for (GolfVo availableTime : availableTimes) {
			if (reservedTimeSet.contains(availableTime.getPlay_time())) {
				availableTime.setAvailable(false);
			}
		}

		model.addAttribute("availableTimes", availableTimes); // model에 담아 view로 전달
		return "user/reservePage";
	}

	// 예약 비활성화
	@PostMapping("/get_reserved_time")
	@ResponseBody
	public Map<String, Object> getReservedTime(@RequestParam("location_name") String location_name,
			@RequestParam("play_date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate play_date) {
		Map<String, Object> resultMap = new HashMap<>();

		try {
			List<String> reservedTimeList = golfService.getReservedTime(location_name, play_date); // 예약된 시간 가져오기

			resultMap.put("result", "success");
			resultMap.put("reserved_time", reservedTimeList); // 예약된 시간 목록 전달
		} catch (Exception e) {
			resultMap.put("result", "failure");
			resultMap.put("message", "예약된 시간을 가져오는 도중 오류가 발생하였습니다.");
			e.printStackTrace();
		}

		return resultMap;
	}

	// 예약 조회하기, 페이징처리
	@GetMapping("/reservation_check")
	public String check(Model model, HttpSession session, Criteria cri) throws Exception {

		UserVo user = (UserVo) session.getAttribute("user");
		String user_id = user.getUser_id();

		System.out.println(user);
		Paging paging = new Paging(cri, golfService.getUserReservationCheckCount(user_id));

		cri.setPerPageNum(5); // 페이지당 보여줄 개수 설정
		cri.setTotalCount(golfService.getUserReservationCheckCount(user_id));

		List<GolfVo> list = golfService.noticeList(cri);
		List<UserVo> dates = golfService.getUserReservationCheck(user_id, cri);

		model.addAttribute("list", list);
		model.addAttribute("dates", dates);
		model.addAttribute("paging", paging);

		return "user/reservation_check";
	}

	// 예약 삭제하기
	@GetMapping("/deleteReservation")
	public String deleteReservation(@RequestParam String location_name,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate play_date, @RequestParam String play_time,
			@ModelAttribute GolfVo golfVo, HttpSession session, Model model, Criteria cri) {
		Object userObj = session.getAttribute("user");

		UserVo user = (UserVo) session.getAttribute("user");
		if (userObj instanceof UserVo) {
			String user_id = ((UserVo) userObj).getUser_id();
			golfService.deleteReservation(user_id, location_name, play_date, play_time);
			List<UserVo> dates = golfService.getUserReservationCheck(user_id, cri);
			model.addAttribute("dates", dates);

			this.messageService = NurigoApp.INSTANCE.initialize("NCSJN08KXCIXWEQT", "JUJJ0ABGZAPKS0ZLFBR2SBWBZGBTWQMG",
					"https://api.coolsms.co.kr");

			try {
				play_time = golfVo.getPlay_time();

				if (play_time.equals("07:00")) {
					play_time = "1부(07:00~12:00)";
				} else {
					play_time = "2부(13:00~18:00)";
				}
				// 문자 내용 구성 // 예약자의 휴대폰 번호
				String messageContent = "예약이 취소되었습니다." + "\n" + "예약 정보: " + "\n" + "유저이름:" + user.user_name + "\n"
						+ "예약날짜: " + golfVo.getPlay_date() + "\n" + "예약시간: " + play_time + "\n" + "지역: "
						+ golfVo.getLocation_name() + "\n" + "전화번호:" + user.getPhone();

				// 문자 발송
				Message message = new Message();
				message.setFrom("01084602389"); // 발신 번호
				message.setTo("01084602389"); // 수신 번호
				message.setText(messageContent);

				SingleMessageSentResponse response = this.messageService
						.sendOne(new SingleMessageSendingRequest(message));

				// 발송 결과 확인
				System.out.println("SMS 발송 결과: " + response.toString());
			} catch (Exception e) {
				// 예외 처리
				e.printStackTrace();
				return "error";
			}
			System.out.println(location_name);
			System.out.println("여기가 실행됫다");
			return "redirect:/reservation_check";
		} else {
			// 예외 처리 또는 로그 출력 등의 작업을 수행할 수 있습니다.
			return "redirect:/error";
		}
	}

	// 예약 변경하기
	@GetMapping("/modifyReservation")
	public String showModifyReservationPage(HttpSession session, Model model, Criteria cri,
			@ModelAttribute GolfVo golfVo) {
		Object userObj = session.getAttribute("user");
		if (userObj instanceof UserVo) {
			String user_id = ((UserVo) userObj).getUser_id();

			List<UserVo> dates = golfService.getUserReservationCheck(user_id, cri);
			model.addAttribute("dates", dates);

			return "/user/updatePage";
		} else {
			return "redirect:/error";
		}
	}

	// 예약변경후 문자보내기
	@GetMapping("/modifyReservation/update")
	public String modifyReservation(@RequestParam String location_name,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate play_date, @RequestParam String play_time,
			@RequestParam(required = false) Integer reservation_no, HttpSession session, Model model, Criteria cri,
			@ModelAttribute GolfVo golfVo) {
		Object userObj = session.getAttribute("user");
		UserVo user = (UserVo) session.getAttribute("user");
		if (userObj instanceof UserVo) {
			String user_id = ((UserVo) userObj).getUser_id();

			// 예약 변경 로직을 구현
			golfService.modifyReservation(user_id, location_name, play_date, play_time, reservation_no);

			List<UserVo> dates = golfService.getUserReservationCheck(user_id, cri);
			model.addAttribute("dates", dates);

			this.messageService = NurigoApp.INSTANCE.initialize("NCSJN08KXCIXWEQT", "JUJJ0ABGZAPKS0ZLFBR2SBWBZGBTWQMG",
					"https://api.coolsms.co.kr");

			try {
				// 문자 내용 구성 // 예약자의 휴대폰 번호
				String messageContent = "예약이 변경되었습니다." + "\n" + "예약 정보: " + "\n" + "유저이름:" + user.user_name + "\n"
						+ "예약날짜: " + golfVo.getPlay_date() + "\n" + "예약시간: " + golfVo.getPlay_time() + "\n" + "지역: "
						+ golfVo.getLocation_name() + "\n" + "전화번호:" + user.getPhone();

				// 문자 발송
				Message message = new Message();
				message.setFrom("01084602389"); // 발신 번호
				message.setTo("01084602389"); // 수신 번호
				message.setText(messageContent);

				SingleMessageSentResponse response = this.messageService
						.sendOne(new SingleMessageSendingRequest(message));

				// 발송 결과 확인
				System.out.println("SMS 발송 결과: " + response.toString());
			} catch (Exception e) {
				// 예외 처리
				e.printStackTrace();
				return "error";
			}
			return "redirect:/modifyReservation"; // 변경 후 다시 예약 변경 페이지로 이동
		} else {
			return "redirect:/error";
		}
	}

	// 검색하기
	@GetMapping("/search")
	public String searchNotices(@RequestParam(required = false) String user_id,
			@RequestParam(required = false) String user_name,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false) String searchCondition,
			@RequestParam(required = false) String searchKeyword, Criteria cri, Model model, HttpSession session)
			throws Exception {

		String start = (String) session.getAttribute("start");
		String end = (String) session.getAttribute("end");

		int totalCount = golfService.searchListCnt(user_id, user_name, searchCondition, searchKeyword);
		System.out.println(totalCount);
		cri.setTotalCount(totalCount);
		System.out.println(totalCount);
		cri.setPerPageNum(5);

		Paging paging = new Paging(cri, totalCount);

		List<GolfVo> searchResults = golfService.searchNotices(user_id, user_name, searchCondition, searchKeyword, cri);

		// 검색결과에 따른것만 조회하기
		if (user_id != null && !user_id.isEmpty()) {
			searchResults = searchResults.stream().filter(golf -> user_id.equals(golf.getUser_id()))
					.collect(Collectors.toList());
		}

		model.addAttribute("paging", paging);
		model.addAttribute("start", start);
		model.addAttribute("end", end);
		model.addAttribute("searchResults", searchResults);

		System.out.println(cri.getPageStart());
		System.out.println(cri.getPageEnd());
		System.out.println(totalCount);

		return "/notice/golfList";
	}

	// 달력별 예약일정보기
	@RequestMapping("calendar")
	public String calendar() {
		return "/notice/calendar";
	}

	// FullCalendar 이벤트 데이터를 반환하는 API 엔드포인트
	@RequestMapping("/calendar/events")
	@ResponseBody
	public List<ReservationEvent> getReservationEvents() {
		List<ReservationEvent> events = new ArrayList<>();

		// 예약 정보 조회 로직
		List<Reservation> reservations = golfService.getAllReservations();

		// 예약 정보를 ReservationEvent로 변환하여 events에 추가
		for (Reservation reservation : reservations) {
			ReservationEvent event = new ReservationEvent();
			event.setTitle(reservation.getUser_name());
			event.setStart(reservation.getPlay_date());
			event.setReservation_no(reservation.getReservation_no());
//	        System.out.println("Reservation" + reservation);
			event.setExtendedProps(new ExtendedProps(reservation.getUser_id(), reservation.getPlay_time(),
					reservation.getLocation_name(), reservation.getPlay_date(), reservation.getReservation_no(),
					reservation.getUser_no(), reservation.getPhone()));

			if (reservation.getGolf_name() != null) {
				event.setBackgroundColor("#87CEFA"); // golf_name이 null이 아닌 경우 연한 파란색으로 지정
			} else if (reservation.getISMODIFIED() != null) {
				event.setBackgroundColor("#FFD700"); // 변경된 예약인 경우 노란색으로 지정
			} else if(reservation.getUser_id() == null) {
				event.setBackgroundColor("#FFC0CB");
			} else {
				event.setBackgroundColor("#98FB98"); // 그 외의 경우 밝은 초록색으로 지정
			}

//	        System.out.println("reservation.getISMODIFIED()" + reservation.getISMODIFIED());
//	        System.out.println("reservation.getUser_id()" + reservation.getUser_id());
//	        System.out.println("reservation.getUser_no()" + reservation.getUser_no());
//	        System.out.println("reservation.getPhone()" + reservation.getPhone());
			events.add(event);

		}
		return events;
	}

	// 문자 발송 보내기
	@RequestMapping("/calendar/confirm")
	@ResponseBody
	public SingleMessageSentResponse sendOne(@RequestBody Reservation reservation) throws Exception {
		this.messageService = NurigoApp.INSTANCE.initialize("NCSJN08KXCIXWEQT", "JUJJ0ABGZAPKS0ZLFBR2SBWBZGBTWQMG",
				"https://api.coolsms.co.kr");

		LocalDate play_date = reservation.getPlay_date();
		String user_id = reservation.getUser_id();
		String play_time = reservation.getPlay_time();
		String location_name = reservation.getLocation_name();
		Integer user_no = reservation.getUser_no();
		String golf_name = reservation.getGolf_name();
		String phone = reservation.getPhone();
		Integer reservation_no = reservation.getReservation_no();

		System.out.println(reservation_no);

		golfService.updateGolfname(reservation);
		if (phone == null) {
			phone = golfService.selectUserById(reservation_no);
		}

		System.out.println(user_no);

		Message message = new Message();
		// 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.
		message.setFrom("01084602389"); // 발신 번호
		message.setTo(phone); // 수신 번호
		message.setText("예약날짜: " + play_date + "\n" + "예약시간: " + play_time + "\n" + "지역: " + location_name + "\n"
				+ "골프장 이름: " + golf_name);

		System.out.println("여기구나");
		SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
		System.out.println("reservation_no: " + reservation_no);
		System.out.println("user_no: " + user_no);
		System.out.println("phone: " + phone);
		System.out.println("예약날짜: " + play_date + "\n" + "예약시간: " + play_time + "\n" + "지역: " + location_name + "\n"
				+ "골프장 이름: " + golf_name + "\n" + "user_no: " + user_no);
		System.out.println(response);

		return response;
	}

	// 관리자 예약변경하기
	@PostMapping("/calendar/update")
	@ResponseBody
	public String updateEvent(@RequestBody Reservation reservation) throws Exception {
		// request에서 이벤트 정보를 추출하여 데이터베이스 업데이트 작업 수행
		String user_id = reservation.getUser_id();
		String eventTitle = reservation.getUser_name();
		LocalDate eventStart = reservation.getPlay_date();
		LocalDate play_date = reservation.getPlay_date();
		String phone = reservation.getPhone();
		Integer reservation_no = reservation.getReservation_no();
		Integer user_no = reservation.getUser_no();
		String location_name = reservation.getLocation_name();
		String play_time = reservation.getPlay_time();

		golfService.updateGolfname(reservation);
		if (phone == null) {
			phone = golfService.selectUserById(reservation_no);
		}

		System.out.println(reservation.getLocation_name());
		System.out.println(reservation.getPlay_time());
		System.out.println(reservation.getPhone());
		System.out.println(reservation.getUser_id());
		System.out.println(reservation.getReservation_no());
		System.out.println(reservation.getUser_no());
		System.out.println(reservation.getPlay_date());

		// 업데이트 작업 수행
		boolean isSuccessful = golfService.updateEvent(user_id, eventTitle, eventStart, play_date, reservation_no,
				phone, location_name, play_time);

		if (isSuccessful) {
			System.out.println(isSuccessful);
			return "success";
		} else {
			System.out.println(isSuccessful);
			return "failure";
		}

	}

	// 관리자가 달력에서 예약하기
	@PostMapping("/calendar/reservation")
	@ResponseBody
	public ResponseEntity<?> createReservation(@RequestBody Reservation reservation) {
		try {
			// 요청으로부터 필요한 정보 추출
			String user_name = reservation.getUser_name();
			String location_name = reservation.getLocation_name();
			LocalDate play_date = reservation.getPlay_date();
			String play_time = reservation.getPlay_time();
			String phone = reservation.getPhone();
			
			Integer member = golfService.getUserByName(user_name);
			
			System.out.println(member);
			
			if (member != null && member == 0) {
				//비회원 tb_user에 생성하기
				UserVo userVo = new UserVo();
				userVo.setUser_name(user_name);
				userVo.setPhone(phone);				
				
				System.out.println(user_name);
				System.out.println(phone);
				golfService.insertUser(userVo);
				// 비회원 예약 생성 로직 수행
				golfService.createNonMemberReservation(user_name,location_name, play_time, play_date);
			} else {
				// 회원 예약 생성 로직 수행
				if(member == 1) {
				golfService.createReservation(user_name, location_name, play_time, play_date);
				} else {
					golfService.multiCreate(user_name, location_name, play_time, play_date, phone);
				}				
			}

			return ResponseEntity.ok(reservation);
		} catch (Exception e) {
			// 예약 생성 실패 시 예외 처리
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예약 생성에 실패했습니다.");
		}
	}

	@PostMapping("/calendar/delete")
	public ResponseEntity<String> deleteEvent(@RequestBody Reservation reservation) {
		Integer reservation_no = reservation.getReservation_no();
		System.out.println(reservation_no);

		try {
			// 이벤트 삭제 로직을 구현하세요.
			golfService.delete(reservation_no); // 예약 번호를 이용하여 이벤트를 삭제하는 메소드를 호출합니다.

			// 이벤트 삭제 성공 시 HttpStatus.OK를 반환합니다.
			return new ResponseEntity<>("이벤트가 삭제되었습니다.", HttpStatus.OK);
		} catch (Exception e) {
			// 이벤트 삭제 실패 시 HttpStatus.INTERNAL_SERVER_ERROR를 반환합니다.
			return new ResponseEntity<>("이벤트 삭제에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	//예약시 유저정보 가져오기
	@PostMapping("/calendar/check")
	public ResponseEntity<Integer> checkUser(@RequestParam("user_name") String user_name) {
	    
		Integer reservation = golfService.getUserNoByUserName(user_name);
	    
	    if (reservation > 0) {
	        // DB에 정보가 있는 경우	        	        
	        return ResponseEntity.ok(reservation);
	    } else {
	        // DB에 정보가 없는 경우	        
	        return ResponseEntity.ok(0);
	    }
	}

}