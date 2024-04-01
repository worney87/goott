package org.rainbow.company.custMgmt.controller;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javax.servlet.http.HttpServletResponse;


import org.rainbow.company.custMgmt.domain.spotAttachFileDTO;
import org.rainbow.company.custMgmt.domain.companyDownVO;
import org.rainbow.company.custMgmt.domain.companyInputVO;

import org.rainbow.company.custMgmt.domain.companyVO;
import org.rainbow.company.custMgmt.domain.spotAndUserVO;
import org.rainbow.company.custMgmt.service.companyServiceImpl;
import org.rainbow.domain.ExcelDownloadUtil;
import org.rainbow.domain.ExcelListener;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.log4j.Log4j;
@CrossOrigin(origins = "<http://localhost:8080>")
@Log4j
@Controller
public class companyController {

	@Autowired
	companyServiceImpl companyService;

	/** 'companyList.jsp' 에서 기업 리스트 가져오기 */
	@GetMapping("/companyList")
	public String companyList(Model model) {

		log.info("companyList_success");

		model.addAttribute("companyVO", companyService.companyList());

		return "/company/custMgmtPage/companyMgmt/companyList";
	}
	
	/** 서치바에서 키워드 검색 */
    @ResponseBody
    @GetMapping(value = "/searchCompany", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<companyVO>> prdSeach(@RequestParam("keyword") String keyword)
    {
        log.info("keyword..."+keyword);
        
        
        List<companyVO> list = companyService.giveKeyword(keyword);
        log.info(list);
        

        // ResponseEntity에 list와 ptdo를 함께 담아 반환

        // 리스트 비동기로 뿌려주기
        return new ResponseEntity<List<companyVO>>(list, HttpStatus.OK);
    }


	/** 'companyList.jsp' 에서 기업 등록 버튼 누르면 'companyRegister.jsp'로 이동 */
	@GetMapping("/moveCompanyRegister")
	public String moveCompanyRegister() {
		log.info("moveCompanyRegister_success");
		return "/company/custMgmtPage/companyMgmt/companyRegister";

	}

	/** 'companyView.jsp' 에서 기업 정보 가져오기 */
	@GetMapping("/companyView")
	public String companyView(int companyNo, Model model) {

		log.info("companyView_success" + companyNo);

		model.addAttribute("companyVO", companyService.companyView(companyNo));

		return "/company/custMgmtPage/companyMgmt/companyView";

	}


	/** 사업자등록증 파일 업로드1 */
	@PostMapping("/uploadFile")
	public ResponseEntity<String> handleFileUpload(@RequestPart("file") MultipartFile file) {
		// 파일 업로드 로직을 수행하고, 파일명을 얻을 수 있습니다.
		String fileName = file.getOriginalFilename();

		// 파일명을 클라이언트로 전송하거나 필요한 로직을 수행합니다.
		return new ResponseEntity<>(fileName, HttpStatus.OK);
	}
	
	


	@ResponseBody
	@PostMapping(value = "/checkBizNum", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<companyVO>> checkBizNum(@RequestBody List<String> bizNumArray) {
		// 여기서 bizNumArray를 이용하여 국세청 API를 호출하고 결과를 리턴하는 코드 작성
		// YourResultType은 API 호출 결과에 따라 적절한 형태로 변경해야 합니다.

		List<companyVO> result = companyService.checkBizNum(bizNumArray);

		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	
	
	 // 엑셀 파일 업로드 처리
    @ResponseBody
    @PostMapping(value = "/companyExcelInput", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> ExcelUpload(@RequestParam("EXCEL") MultipartFile file) 
    {	
    	log.info(file);
    	int result = 0;
        ExcelListener listener = new ExcelListener();
        if (!file.isEmpty()) 
        {
            try 
            {
                // 엑셀 파일 처리를 위한 리스너로 데이터 추출
                List<companyInputVO> dataList = listener.companyExcelListner(file.getInputStream());
                log.info(dataList);
                System.out.println(dataList);
                log.info(dataList.get(0).getCompanyNo()); 
                System.out.println(dataList.get(0).getCompanyNo()); 
                // 데이터베이스에 엑셀 데이터 저장
                for(companyInputVO vo : dataList) 
                {
                	 log.error(vo.getComName());
                	 result = companyService.insertCompanyExcel(vo);
                }
                
                System.out.println("result = " + result);
                return result >= 1 ? new ResponseEntity<String>("success",HttpStatus.OK) :
                new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
                
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
                return  new ResponseEntity<String>("error",HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } 
        else 
        {
        	System.out.println("파일 정보가 안들어옴");
        	return  new ResponseEntity<String>("no file",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /** 엑셀 데이터 다운로드 처리*/
    @ResponseBody
    @PostMapping("/companyListDownloadExcel")
    public void companyListDownloadExcel(HttpServletResponse response, @RequestBody List<String> filteredValues) throws IOException 
    {
    	
    	System.out.println("엑셀로 다운로드 메서드 탐");
    	log.info("엑셀로 다운로드 메서드 탐");
    	//System.out.println("필터"+filteredValues);

    	Map<String, Object> filteredValue = new HashMap<>();
    	
    	filteredValue.put("filteredValues", filteredValues);
    	
    	List<companyDownVO> downlist = companyService.downExcelList(filteredValue);
    	//System.out.println("다운리스트" + downlist);
    	
    	
        // 리스트를 넣으면 엑셀화됨.
        ExcelDownloadUtil.dowonloadUtill(response, downlist);
    }
    
    /** 파일 다운로드*/
    @PostMapping("/getCompanyLicenseFileURL")
    public void getCompanyLicenseFileURL() {
    	
    	
    	
    	
    }
    
 
    
//   /** 기업 등록 하기*/
//	@PostMapping(value = "/companyRegisterInsert")
//	public String companyRegisterInsert(@RequestParam("file") MultipartFile file, companyVO vo, RedirectAttributes rttr) {
//	    log.info("companyRegisterInsert..." + vo);
//
//	    // 파일 업로드 처리
//	    if (!file.isEmpty()) {
//	        try {
//	            // 파일 업로드 경로 설정
//	            String uploadDir = "/uploads/";
//	            Path uploadPath = Paths.get(uploadDir);
//	            
//	            // 업로드 디렉토리가 없으면 생성
//	            if (!Files.exists(uploadPath)) {
//	                Files.createDirectories(uploadPath);
//	            }
//	            
//	            // 파일명 중복 방지를 위한 고유한 파일명 생성
//	            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//	            Path filePath = uploadPath.resolve(fileName);
//	            Files.copy(file.getInputStream(), filePath);
//	            
//	            // 업로드된 파일 경로를 VO에 설정
//	            vo.setFilePath(filePath.toString());
//	        } catch (IOException e) {
//	            e.printStackTrace();
//	        }
//	    }
//
//	    companyService.companyRegisterInsert(vo);
//
//	    rttr.addFlashAttribute("message", "기업 등록이 완료되었습니다.");
//
//	    return "redirect:/companyList";
//	}
    
 // 기업 등록 처리
    @PostMapping("/companyRegisterInsert")
    public String register(companyVO vo, RedirectAttributes rttr) {
        log.info("기업 등록하기 companyRegisterInsert..." + vo);
        
        // 기업 등록 서비스 호출
        companyService.companyRegisterInsert(vo);
        
        // 등록된 파일 리스트 출력
        log.info("파일 리스트 : " + vo.getAttachList());
        
        if(vo.getAttachList() != null) {
            vo.getAttachList().forEach(attach -> log.info("파일 리스트 결과 : " + attach));
        }
        
        // 기업 등록이 완료되었음을 사용자에게 알림
        rttr.addFlashAttribute("message", "기업 등록이 완료되었습니다.");
        
        // 기업 목록 페이지로 Redirect
        return "redirect:/companyList";
    }
    
  //오늘 날짜의 경로를 문자열로 생성
  	private String getFolder() {
  		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
  		Date date = new Date();
  		String str = sdf.format(date);
  		return str.replace("-", File.separator);
  	}

    

}
