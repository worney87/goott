package org.rainbow.userAdminPage.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

import org.rainbow.userAdminPage.controller.EmailController;
import org.rainbow.userAdminPage.controller.UserAdminController;
import org.rainbow.userAdminPage.mapper.userAdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;

@Log4j
@Component
public class AutoSendGift {

	@Autowired
	private userAdminMapper userAdminMapper;

	@Autowired
	private EmailController emailController;

	@Autowired
	private UserAdminController UserAdminController;

	// @Scheduled(cron = "0 0 10 * * *") // 매일 오전 10시에 실행
	@Scheduled(fixedRate = 24 * 60 * 60 * 1000) // 24시간마다 실행 (24시간 * 60분 * 60초 * 1000밀리초)
	public void runRainbowBirthDay() {
		
		System.out.println("자동화서비스 실행");
		
		// 현재 날짜 가져오기
        LocalDate today = LocalDate.now();
        
        // 날짜를 원하는 형식으로 포맷팅
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = today.format(formatter);
        
		try {
			// 오늘 날짜에 해당하는 데이터 가져오기
			List<HashMap<String, Object>> orderList = userAdminMapper.getOrderList(formattedDate);

			// 가져온 데이터 중 오늘 날짜에 발송되어야 할 이메일을 처리
			for (HashMap<String, Object> order : orderList) {
				log.info("order:::" + order);
				int ordNo = (int) order.get("ordNo");

				emailController.sendEmail(ordNo);
			}

			List<HashMap<String, Object>> unselected = userAdminMapper.unselected();
			// 가져온 데이터 중 미선택자 기본선물로 지정 및 발송
			for (HashMap<String, Object> defaultGift : unselected) {
				log.info("defaultGift:::" + defaultGift);
				UserAdminController.orderGift(defaultGift);
				int ordNo = (int)defaultGift.get("ordNo");
				emailController.sendGiftEmail(ordNo);
			}

		} catch (Exception e) {
			// 예외 발생 시 적절히 처리
			e.printStackTrace();
		}
	}

}
