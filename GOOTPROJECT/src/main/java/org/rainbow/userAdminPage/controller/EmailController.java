package org.rainbow.userAdminPage.controller;

import java.util.HashMap;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.rainbow.userAdminPage.service.userAdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

@RestController
public class EmailController {

	@Autowired
	private userAdminServiceImpl userService;
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private SpringTemplateEngine templateEngine;

	@GetMapping("/send-email/{ordNo}")
	public String sendEmail(@PathVariable int ordNo) {

		HashMap<String, String> sendInfo = userService.sendMail(ordNo);

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper messageHelper;

		try {
			messageHelper = new MimeMessageHelper(message, true, "utf-8");

			// 받는 사람 메일주소 설정
			String fromEmail = (String) sendInfo.get("cEmpEmail");
			messageHelper.setFrom(fromEmail);
			// 보내는 사람 메일주소 설정
			messageHelper.setTo("worney87@gmail.com");

			// 메일 제목 설정
			String sendName = "";
			if (sendInfo.get("sendName").equals("")) {
				sendName = sendInfo.get("comName");
			} else {
				sendName = sendInfo.get("sendName");
			}
			String subject = "[Rainbow BirthDay] '" + sendName + "'에서 보낸 '" + sendInfo.get("cEmpName")
					+ "' 님의 생일선물이 도착하였습니다.";
			messageHelper.setSubject(subject);

			// 템플릿에 전달할 데이터 설정
			Context context = new Context();

			context.setVariable("ordNo", ordNo);

			context.setVariable("spotNo", sendInfo.get("spotNo"));

			// 카드 배경 가져오기
			String theme = null;
			switch (sendInfo.get("selectedTheme")) {
			case "theme_basic":
				theme = "https://i.ibb.co/wQCCCmP/theme-basic.png";
				break;
			case "theme_blue":
				theme = "https://i.ibb.co/RyVDNZZ/theme-blue.jpg";
				break;
			case "theme_pink":
				theme = "https://i.ibb.co/vz2DDSQ/theme-pink.jpg";
				break;
			case "theme_yellow":
				theme = "https://i.ibb.co/x5GwsZN/theme-yellow.jpg";
				break;
			}
			context.setVariable("theme", theme);
			System.out.println("theme.." + theme);

			String messegeContent = sendInfo.get("messegeContent");
			String cEmpName = sendInfo.get("cEmpName");
			String cEmpPosition = sendInfo.get("cEmpPosition");

			// 메세지 내용에서 각 변수를 매칭하여 문자열 치환
			messegeContent = messegeContent.replace("{name}", cEmpName).replace("{발신자명}", sendName).replace("{grade}",
					cEmpPosition);
			System.out.println("messegeContent.." + messegeContent);

			// 메일 본문내용으로 설정
			context.setVariable("content", messegeContent);
			// 이메일 내용 전달 : 템플릿 프로세스
			String html = templateEngine.process("mailTemplate", context);

			messageHelper.setText(html, true);

			mailSender.send(message);

			// 메일 발송 완료 후 order 상태값 변경
			userService.updateStep(ordNo);

			return "Email sent successfully";
		} catch (MessagingException e) {
			e.printStackTrace();

			return "Failed to send email: " + e.getMessage();
		}
	}

	@GetMapping("/validateGiftSelection/{ordNo}")
	public String validateGiftSelection(@PathVariable int ordNo) {
		String result = userService.validateGiftSelection(ordNo);
		if (result.equals("선택중")) {
			return "success";
		} else {
			return "false";
		}
	}

	@GetMapping("/send-gift/{ordNo}")
	public String sendGiftEmail(@PathVariable int ordNo) {
	    // 선물 발송 메서드 시작
	    System.out.println("선물발송 실행됨");
	    try {
	        // 주문 번호를 사용하여 선물 정보를 가져옴
	        HashMap<String, Object> sendInfo = userService.sendGift(ordNo);

	        // MimeMessage 생성 및 MimeMessageHelper 초기화
	        MimeMessage message = mailSender.createMimeMessage();
	        MimeMessageHelper messageHelper = new MimeMessageHelper(message, false, "utf-8");

	        // 발신자 이메일 주소 설정
	        String fromEmail = (String) sendInfo.get("cEmpEmail");
	        System.out.println("fromEmail::" + fromEmail);
	        messageHelper.setFrom(fromEmail);

	        // 수신자 이메일 주소 설정
	        messageHelper.setTo("worney87@gmail.com");

	        // 이메일 제목 설정
	        messageHelper.setSubject("[Rainbow BirthDay] 선물이 도착하였습니다.");

	        // 선물 정보 및 내용 설정
	        String gift = (String)sendInfo.get("prdName");
	        String serialNumber = (String)sendInfo.get("serialNo");
	        System.out.println("gift::"+gift);
	        System.out.println("serialNumber::"+serialNumber);
	        String content = "<h1>선택하신 선물이 도착했습니다.</h1><br><p> 선택선물 : " + gift + "</p><p>선물번호 : " + serialNumber + 
	                            "</p><br><span>해당 선물번호로 교환처 및 가맹처에서 사용가능합니다.</span><br><span>사용문의 : Tel)1588-3082";
	        // HTML 형식의 내용 설정
	        messageHelper.setText(content, true);

	        // 이메일 전송
	        mailSender.send(message);
	        
	        int retult = userService.updateOrderStep(ordNo);
	        System.out.println("retult::"+retult);
	        // 선물 발송 성공을 나타내는 문자열 반환
	        return "success"; 
	    } catch (Exception e) {
	        // 예외 발생 시 에러 로그 출력
	        e.printStackTrace();
	        // 선물 발송 실패를 나타내는 문자열 반환
	        return "false"; 
	    }
	}

}
