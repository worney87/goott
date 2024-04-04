package org.rainbow.userAdminPage.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.rainbow.domain.ExcelListener;
import org.rainbow.userAdminPage.service.userAdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j;

@Controller
@SessionAttributes
@RequestMapping("/userAdminPage/*")
@Log4j
public class UserAdminController {

	@Autowired
	private userAdminServiceImpl userService;

	// 최초 접근 시
	@GetMapping("/userLogin")
	public String userLogin() {
		return "userAdminPage/userLogin";
	}

	// 사용자 로그인
	@ResponseBody
	@PostMapping(value = "/adminLogin", consumes = "application/json", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<HashMap<String, Object>> customerLogin(@RequestBody HashMap<String, String> userMap,
			HttpServletRequest req) {

		// 요청 정보 담을 맵 생성
		HashMap<String, String> inputMap = new HashMap<String, String>();
		// 맵에 정보 담기
		inputMap.put("userEmail", (String) userMap.get("userEmail"));
		inputMap.put("userPw", (String) userMap.get("userPw"));
		log.info("input info..." + inputMap); // 담긴 정보 확인

		// 결과 정보 담을 맵 생성
		HashMap<String, Object> resultMap = userService.customerLogin(inputMap);
		log.info("result info..." + resultMap); // 결과 정보 확인

		// 결과 정보 판단 후 status 및 추가작업
		if (resultMap == null) {
			return ResponseEntity.badRequest().body(null);
		} else {
			// 세션 생성 및 필요 정보 세션 저장
			HttpSession session = req.getSession();
			session.setAttribute("userName", (String) resultMap.get("userName"));
			session.setAttribute("spotNo", (Integer) resultMap.get("spotNo"));
			session.setMaxInactiveInterval(60 * 15);

			return ResponseEntity.ok().body(resultMap);
		}
	}

	// 사용자 로그아웃
	@GetMapping("/goLogout")
	public String goLogout(HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		session.invalidate();
		return "/userAdminPage/userLogin";
	}

	// 사용자 개인정보 수정페이지 이동
	@GetMapping("/userEdit/{no}")
	public String userEdit(@PathVariable String no, Model model) {
		int spotNo = Integer.parseInt(no);
		HashMap<String, Object> userMap = userService.getUserInfo(spotNo);
		model.addAttribute("result", userMap);
		return "/userAdminPage/userEdit";
	}

	// 사용자 개인정보 업데이트
	@ResponseBody
	@PostMapping("/updateUserInfo/{no}")
	public ResponseEntity<String> updateUserInfo(@RequestBody HashMap<String, Object> updateInfo,
			@PathVariable String no) {
		int spotNo = Integer.parseInt(no);
		updateInfo.put("spotNo", spotNo);
		log.info("updateInfo..." + updateInfo);
		int result = userService.updateUserInfo(updateInfo);
		if (result > 0) {
			return new ResponseEntity<String>(HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}

	// dashboard 정보 가져오기
	@GetMapping("/goDashboard/{no}")
	public String getDashboard(@PathVariable String no, Model model) {
		int spotNo = Integer.parseInt(no);
		HashMap<String, Object> dashboardMap = userService.getDashboard(spotNo);
		log.info("infoMap..." + dashboardMap);
		model.addAttribute("dashboard", dashboardMap);
		List<HashMap<String, Object>> bestTop5 = userService.getBestTop5(spotNo);
		log.info("bestTop5..." + bestTop5);
		model.addAttribute("bestTop5", bestTop5);
		System.out.println(model);
		return "/userAdminPage/dashboard";
	}

	@ResponseBody
	@GetMapping(value = "/getMonthlyData/{no}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<HashMap<String, Object>>> getMonthlyData(@PathVariable String no) {
		int spotNo = Integer.parseInt(no);
		List<HashMap<String, Object>> MonthlyData = userService.getMonthlyData(spotNo);
		log.info("MonthlyData.." + MonthlyData);
		return new ResponseEntity<List<HashMap<String, Object>>>(MonthlyData, HttpStatus.OK);
	}

	// 직원관리 페이지 이동
	@GetMapping("/goManagemember")
	public String goManagemember() {
		return "/userAdminPage/manage_member";
	}

	// 직원 리스트 가져오기
	@GetMapping(value = "/manage_member/{no}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<HashMap<String, Object>>> getEmpList(@PathVariable String no) {
		int spotNo = Integer.parseInt(no);
		// 컨트롤러에서 서비스를 호출하여 직원 리스트를 가져옵니다.
		List<HashMap<String, Object>> empList = userService.getEmpList(spotNo);
		log.info("empList..." + empList);
		// ResponseEntity에 직원 리스트와 HttpStatus를 담아 반환합니다.
		return new ResponseEntity<>(empList, HttpStatus.OK);
	}

	// 직원 추가하기
	@ResponseBody
	@PostMapping(value = "/addEmp", consumes = "application/json", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> addUserEmp(@RequestBody HashMap<String, Object> addForm) {
		log.info("InquiryList..." + addForm);
		boolean result = userService.addUserEmp(addForm);
		System.out.println("추가여부 : " + result);
		return result == true ? new ResponseEntity<String>("success", HttpStatus.OK)
				: new ResponseEntity<String>("fail", HttpStatus.BAD_REQUEST);
	}

	// 직원 수정하기
	@ResponseBody
	@PostMapping("/updateEmp")
	public ResponseEntity<String> updateEmp(@RequestBody List<HashMap<String, Object>> updateEmp) {
		try {
			for (HashMap<String, Object> emp : updateEmp) {
				log.info("수정정보.." + emp);
				boolean result = userService.updateEmp(emp);
				System.out.println("수정여부 : " + result);
			}
			// 업데이트 성공 시 success 반환
			return new ResponseEntity<String>("success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			// 업데이트 실패 시 fail 반환
			return new ResponseEntity<String>("fail", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// 직원 삭제하기
	@ResponseBody
	@PostMapping("/deleteEmp/{no}")
	public String deleteEmployees(@PathVariable String no, @RequestBody List<String> empNumbers) {
		try {
			int spotNo = Integer.parseInt(no);
			System.out.println(empNumbers);
			List<HashMap<String, Integer>> deleteParams = new ArrayList<>();

			// empNumbers 리스트의 각 요소를 deleteParams에 추가합니다.
			for (String empNumber : empNumbers) {
				HashMap<String, Integer> deleteParam = new HashMap<>();
				deleteParam.put("spotNo", spotNo);
				deleteParam.put("cEmpNo", Integer.parseInt(empNumber));
				deleteParams.add(deleteParam);
			}
			System.out.println(deleteParams);
			// 서비스 계층을 통해 직원 삭제를 처리
			boolean result = userService.deleteEmployees(deleteParams);

			if (result) {
				return "success"; // 삭제 성공시 success 반환
			} else {
				return "error"; // 삭제 실패시 error 반환
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error"; // 삭제 실패시 error 반환
		}
	}

	@ResponseBody
	@PostMapping(value = "/allMemberInsert/{no}", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> ExcelUpload(@RequestParam("EXCEL") MultipartFile file, @PathVariable String no) {
		// 업로드된 파일 확인
		log.info("Excel data received: " + file.getOriginalFilename());

		// spotNo를 정수형으로 변환
		int spotNo = Integer.parseInt(no);

		// 엑셀 파일을 처리하기 위한 리스너 생성
		ExcelListener listener = new ExcelListener();

		if (!file.isEmpty()) {
			try {
				// 엑셀 파일 처리를 위한 리스너로 데이터 추출
				List<HashMap<String, Object>> dataList = listener.cMemberExcelListner(file.getInputStream());

				// 추출된 데이터 확인
				log.info("Data extracted from Excel: " + dataList);

				// 데이터베이스에 엑셀 데이터 저장
				boolean allDataProcessed = true; // 모든 데이터가 처리되었는지 여부를 나타내는 변수
				for (HashMap<String, Object> addForm : dataList) {
					// 데이터에 null 값이 있는지 확인
					if (addForm.containsValue(null)) {
						log.warn("Skipping data with null values: " + addForm);
						allDataProcessed = false; // 데이터에 null 값이 있음을 표시
						break; // for 루프를 중단하고 빠져나옴
					}

					// 데이터에 spotNo 추가
					addForm.put("spotNo", spotNo);

					// UserService를 통해 데이터베이스에 사용자 추가
					boolean result = userService.addUserEmp(addForm);

					// 저장 결과 확인 후 적절한 응답 반환
					if (!result) {
						log.error("Failed to add user: " + addForm);
						return new ResponseEntity<>("failure", HttpStatus.INTERNAL_SERVER_ERROR);
					}
				}

				// 데이터가 성공적으로 저장된 경우 성공 응답 반환
				return new ResponseEntity<>("success", HttpStatus.OK);

			} catch (IOException e) {
				// 파일 읽기 실패 시 에러 응답 반환
				log.error("Failed to read Excel file", e);
				return new ResponseEntity<>("error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			}

		} else {
			// 파일이 비어있을 경우 에러 응답 반환
			log.error("No file uploaded");
			return new ResponseEntity<>("no file", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// 이용현황 페이지 이동
	@GetMapping("/goUsagehistorylist/{no}")
	public String goUsagehistorylist(@PathVariable String no, Model model) {
		return "/userAdminPage/usageHistory_list";
	}

	// 이용현황 페이지 리스트 가져오기
	@ResponseBody
	@GetMapping(value = "/getUsageList/{no}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<HashMap<String, Object>>> getUsageList(@PathVariable String no) {
		int spotNo = Integer.parseInt(no);
		List<HashMap<String, Object>> usageList = userService.getUsageList(spotNo);
		log.info("usageList..." + usageList);
		return new ResponseEntity<List<HashMap<String, Object>>>(usageList, HttpStatus.OK);
	}

	@PostMapping("/usageDetailHistory")
	public String getDetailUsage(@RequestParam String recDate, @RequestParam String no, Model model) {
		HashMap<String, Object> inputMap = new HashMap<String, Object>();
		inputMap.put("recDate", recDate);
		int spotNo = Integer.parseInt(no);
		inputMap.put("spotNo", spotNo);
		log.info(inputMap);
		// 서비스로부터 반환된 데이터를 모델에 추가
		List<HashMap<String, Object>> detailUsageData = userService.getDetailUsage(inputMap);
		log.info("detailUsageData.." + detailUsageData);
		model.addAttribute("detailUsageData", detailUsageData);

		return "/userAdminPage/usageHistory_details";
	}

	// 고객지원 페이지 이동
	@GetMapping("/goInquiryboard/{no}")
	public String goInquiryboard(@PathVariable String no, Model model) {
		return "/userAdminPage/inquiryBoard";
	}

	// 고객지원 문의 리스트 가져오기
	@ResponseBody
	@GetMapping(value = "/getInquiryboard/{no}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<HashMap<String, Object>>> getInquiryList(@PathVariable String no) {
		int spotNo = Integer.parseInt(no);
		// 컨트롤러에서 서비스를 호출하여 문의 리스트를 가져옵니다.
		List<HashMap<String, Object>> InquiryList = userService.getInquiryList(spotNo);
		log.info("InquiryList..." + InquiryList);
		return new ResponseEntity<>(InquiryList, HttpStatus.OK);
	}

	// 고객지원 문의글 등록하기
	@PostMapping("/addQnA/{no}")
	public String addQnA(@RequestParam("inquiryTitle") String inquiryTitle,
			@RequestParam("customMessege") String customMessege, @PathVariable("no") String spotNo) {
		HashMap<String, Object> addQnAMap = new HashMap<String, Object>();
		addQnAMap.put("questionTitle", inquiryTitle);
		addQnAMap.put("questionContent", customMessege);
		addQnAMap.put("spotNo", Integer.parseInt(spotNo));
		boolean result = userService.addQnA(addQnAMap);
		log.info("addQnA..." + result);
		return "/userAdminPage/inquiryBoard";
	}

	// 선물관리 페이지 이동
	@GetMapping("/goManagegift")
	public String goManagegift() {
		return "/userAdminPage/manage_gift";
	}

	// 지점 커스텀 선물 가져오기
	@ResponseBody
	@GetMapping(value = "/getCustomGift/{no}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<HashMap<String, Object>>> getCustomGift(@PathVariable String no) {
		int spotNo = Integer.parseInt(no);
		List<HashMap<String, Object>> result = userService.getCustomGift(spotNo);
		log.info("지점 커스텀 선물 리스트.." + result);
		return new ResponseEntity<List<HashMap<String, Object>>>(result, HttpStatus.OK);
	}

	// 지점 기본 선물 가져오기
	@ResponseBody
	@GetMapping(value = "/getDefaultGift/{no}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<HashMap<String, Object>> getDefailtInfo(@PathVariable String no) {
		int spotNo = Integer.parseInt(no);
		HashMap<String, Object> result = userService.getDefaultGift(spotNo);
		log.info("지점 기본 선물 정보.." + result);
		return new ResponseEntity<HashMap<String, Object>>(result, HttpStatus.OK);
	}

	// 지점 선물 편집 페이지 이동
	@GetMapping("/giftCustom")
	public String goCustom() {
		return "/userAdminPage/giftCustom";
	}

	// 지점 커스텀 선물 저장
	@ResponseBody
	@PostMapping("/customGift/{no}")
	public String saveCustom(@PathVariable String no, @RequestBody String giftList) {
		try {
			int spotNo = Integer.parseInt(no);
			HashMap<String, Object> customGift = new HashMap<String, Object>();
			customGift.put("spotNo", spotNo);
			customGift.put("spEditGift", giftList);
			log.info(customGift);

			boolean result = userService.updateCustomGift(customGift);
			log.info(result);

			if (result) {
				return "success";
			} else {
				return "error";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	// 지점 기본 선물 저장
	@ResponseBody
	@PostMapping("/defaultGift")
	public String saveDefault(@RequestBody HashMap<String, Object> defaultGift) {
		try {
			boolean result = userService.updateDefaultGift(defaultGift);
			if (result) {
				return "success";
			} else {
				return "error";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	// 기본 선물 편집 페이지 이동
	@GetMapping("/giftDefault")
	public String goDefault() {
		return "/userAdminPage/giftDefault";
	}

	// 지점 선물 전체리스트 가져오기
	@GetMapping(value = "/getGiftList", produces = MediaType.APPLICATION_PROBLEM_JSON_UTF8_VALUE)
	public ResponseEntity<List<HashMap<String, Object>>> getGiftList() {
		List<HashMap<String, Object>> result = userService.getGiftList();
		log.info("선물리스트..." + result);
		return new ResponseEntity<List<HashMap<String, Object>>>(result, HttpStatus.OK);
	}

	// 생일카드관리 페이지 이동
	@GetMapping("/goManagecard/{no}")
	public String goManagecard(@PathVariable String no, Model model) {
		return "/userAdminPage/manage_card";
	}

	// 생일카드정보 가져오기
	@ResponseBody
	@GetMapping(value = "/getCardInfo/{no}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<HashMap<String, Object>> getCardInfo(@PathVariable String no) {
		int spotNo = Integer.parseInt(no);
		HashMap<String, Object> result = userService.getCardInfo(spotNo);
		log.info("생일카드정보.." + result);
		return new ResponseEntity<HashMap<String, Object>>(result, HttpStatus.OK);
	}

	// 생일카드 커스텀 내용 저장
	@ResponseBody
	@PostMapping("/saveCard/{no}")
	public String saveCard(@RequestBody HashMap<String, Object> cardForm, @PathVariable String no) {
		int spotNo = Integer.parseInt(no);
		cardForm.put("spotNo", spotNo);
		log.info("카드커스텀.." + cardForm);
		boolean result = userService.saveCard(cardForm);
		if (result) {
			return "success";
		} else {
			return "error";
		}
	}

	// 이번달 대상자 현황 페이지 이동
	@GetMapping("/goManagerecipients/{no}")
	public String goManagerecipients(@PathVariable String no, Model model) {
		return "/userAdminPage/manage_recipients";
	}

	// 월별 대상자 리스트 가져오기
	@ResponseBody
	@GetMapping(value = "/recipients/{no}/{month}/{year}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<HashMap<String, Object>>> getRecipients(@PathVariable String no,
			@PathVariable String month, @PathVariable String year) {
		int spotNo = Integer.parseInt(no);
		HashMap<String, Object> inputValue = new HashMap<String, Object>();
		inputValue.put("spotNo", spotNo);
		inputValue.put("month", month);
		inputValue.put("year", year);
		log.info(inputValue);
		List<HashMap<String, Object>> result = userService.getRecipients(inputValue);
		return new ResponseEntity<List<HashMap<String, Object>>>(result, HttpStatus.OK);
	}

	private static final String FILE_NAME = "임직원일괄업로드양식.xlsx";
	// 파일이 위치한 상대 경로
	private static final String FILE_PATH = "/resources/images/";

	@Autowired
	private ServletContext servletContext;

	@ResponseBody
	@GetMapping("/download/excel")
	public ResponseEntity<Resource> downloadExcelTemplate() {
		// 상대 경로를 절대 경로로 변환
		String absoluteFilePath = servletContext.getRealPath(FILE_PATH + FILE_NAME);

		try {
			File file = new File(absoluteFilePath);
			if (!file.exists()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}

			// 파일을 리소스로 읽기
			Resource resource = new FileSystemResource(file);

			// 응답 헤더 설정
			HttpHeaders headers = new HttpHeaders();
			headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + FILE_NAME);

			// 파일의 Content-Type 지정
			headers.setContentType(
					MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));

			// 응답 생성
			return ResponseEntity.ok().headers(headers).body(resource);
		} catch (Exception e) {
			// 파일이 없는 경우
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@GetMapping("/userEmpGiftList/{ordNo}/{spotNo}")
	public String moveuserEmpGiftList(@PathVariable int ordNo, @PathVariable int spotNo, Model model) {
		List<HashMap<String, Object>> result = userService.getCustomGift(spotNo);
		model.addAttribute("giftList", result);
		model.addAttribute("ordNo", ordNo);
		return "/userAdminPage/userEmpGiftList";
	}

	@ResponseBody
	@PostMapping("/orderGift")
	public String orderGift(@RequestBody HashMap<String, Object> orderInfo) {
		try {
			UUID uuid = UUID.randomUUID();
			String serialNo = (orderInfo.get("prdNo") + uuid.toString()).replace("-", "");
			orderInfo.put("serialNo", serialNo);
			boolean result = userService.orderGift(orderInfo);
			log.info(result);

			if (result) {
				return "success";
			} else {
				return "error";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@ResponseBody
	@GetMapping("/downUsageList/{spotNo}")
	public ResponseEntity<String> downUsageList(@PathVariable int spotNo, HttpServletResponse response) {
		List<HashMap<String, Object>> usageList = userService.getDownUsageList(spotNo);
		System.out.println(usageList);

		try (Workbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet("Usage List");
			Row headerRow = sheet.createRow(0);

			// 컬럼 순서를 직접 지정합니다.
			String[] columns = { "기업명", "지점명", "주문일자", "주문자", "상품분류", "상품명", "총액", "공급액", "세액", "금액차감", "차감금액", "금액추가",
					"추가금액" };

			// 컬럼명을 엑셀에 기재합니다.
			for (int i = 0; i < columns.length; i++) {
				Cell cell = headerRow.createCell(i);
				cell.setCellValue(columns[i]);
			}

			// Date 포맷을 지정할 패턴 설정
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

			// 데이터를 엑셀에 기재합니다.
			int rowCount = 1;
			for (HashMap<String, Object> row : usageList) {
			    Row dataRow = sheet.createRow(rowCount++);
			    int cellCount = 0;
			    for (String column : columns) {
			        Object value = row.get(column);
			        Cell cell = dataRow.createCell(cellCount++);
			        if (value != null) {
			            if (value instanceof String) {
			                cell.setCellValue((String) value);
			            } else if (value instanceof Integer) {
			                // 숫자 형식으로 변환하여 셀에 기재합니다.
			                cell.setCellValue((Integer) value);
			            } else if (value instanceof Double) {
			                // 숫자 형식으로 변환하여 셀에 기재합니다.
			                cell.setCellValue((Double) value);
			            } else if (value instanceof Date) {
			                // 날짜 형식으로 변환하여 셀에 기재합니다.
			                cell.setCellValue(dateFormat.format((Date) value));
			            } else {
			                // 기타 형식일 경우 문자열로 처리합니다.
			                cell.setCellValue(value.toString());
			            }
			        } else {
			            // null인 경우 0으로 셀에 기재합니다.
			            cell.setCellValue(0);
			        }
			    }
			}

			// Excel 파일을 생성하고 응답으로 전송합니다.
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment; filename=usageList.xlsx");
			workbook.write(response.getOutputStream());

			return new ResponseEntity<>("success", HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>("false", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
