package org.rainbow.company.custMgmt.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.rainbow.company.custMgmt.domain.cEmpListVO;
import org.rainbow.company.custMgmt.domain.companyDownVO;
import org.rainbow.company.custMgmt.domain.companyInputVO;
import org.rainbow.company.custMgmt.domain.companySearchDTO;
import org.rainbow.company.custMgmt.domain.companyVO;
import org.rainbow.company.custMgmt.domain.consultVO;
import org.rainbow.company.custMgmt.domain.spotAndUserVO;
import org.rainbow.company.custMgmt.domain.spotDownVO;
import org.rainbow.company.custMgmt.domain.spotInputVO;
import org.rainbow.company.custMgmt.domain.spotListVO;
import org.rainbow.company.custMgmt.domain.spotSearchDTO;
import org.rainbow.company.custMgmt.domain.spotVO;
import org.rainbow.company.custMgmt.domain.userVO;
import org.rainbow.company.custMgmt.service.companyServiceImpl;
import org.rainbow.company.custMgmt.service.salesServiceImpl;
import org.rainbow.company.custMgmt.service.spotServiceImpl;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



import lombok.extern.log4j.Log4j;
@CrossOrigin(origins = "<http://localhost:8080>")
@Log4j
@Controller
public class spotController {
	
	@Autowired
	private spotServiceImpl spotService;
	
	@Autowired
	private companyServiceImpl companyService;
	
	@Autowired
	private salesServiceImpl salesService;
	
	
	/** 'spotList.jsp' 에서 지점 리스트 가져오기 */
	@GetMapping("/spotList")
	public String spotList(Model model ) {
		log.info("spotList_success");
		
		model.addAttribute("spotListVO",spotService.spotList());

		return "/company/custMgmtPage/spotMgmt/spotList";	
		 
	}
	
	/** 서치바에서 키워드 검색 */
    @ResponseBody
    @GetMapping(value = "/searchSpot", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<spotListVO>> prdSeach(@RequestParam("keyword") String keyword)
    {
        log.info("keyword..."+keyword);
        
        
        List<spotListVO> list = spotService.giveKeyword(keyword);
        log.info(list);
        

        // ResponseEntity에 list와 ptdo를 함께 담아 반환

        // 리스트 비동기로 뿌려주기
        return new ResponseEntity<List<spotListVO>>(list, HttpStatus.OK);
    }
	
    
    /** 'spotView.jsp' 에서 지점 정보 가져오기 */
	@GetMapping("/spotView")
	public String spotView(int spotNo, Model model ) {
		
		log.info("spotView_success" + spotNo);
		
		//spotVO 가져오기
		spotVO spotVO = spotService.spotView(spotNo);
		model.addAttribute("spotVO", spotVO);
		
		
		//userVO 가져오기
		userVO userVO = spotService.getUserVO(spotNo);
		model.addAttribute("userVO",userVO);

		return "/company/custMgmtPage/spotMgmt/spotView";	
		 
	}
	
	// 엑셀 파일 업로드 처리
    @ResponseBody
    @PostMapping(value = "/spotExcelInput", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> spotExcelInput(@RequestParam("EXCEL") MultipartFile file) 
    {	
    	log.info(file);
    	int result = 0;
        ExcelListener listener = new ExcelListener();
        if (!file.isEmpty()) 
        {
            try 
            {
                // 엑셀 파일 처리를 위한 리스너로 데이터 추출
                List<spotInputVO> spotDataList = listener.spotExcelListner(file.getInputStream());
                log.info(spotDataList);
                System.out.println(spotDataList);

                // 데이터베이스에 엑셀 데이터 저장
                for(spotInputVO vo : spotDataList) 
                {
                	 log.error(vo.getComName());
                	 result = spotService.spotExcelInput(vo);
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
    @PostMapping("/downloadSpotExcel")
    public void downloadSpotExcel(HttpServletResponse response, @RequestBody spotSearchDTO filterResult) throws IOException 
    {
    	log.info(filterResult);

    	
    	List<spotDownVO> downlist = spotService.downloadSpotExcel(filterResult);
    	System.out.println("다운 리스트" + downlist);
    	log.info("다운 리스트" + downlist);
    	
    
      //리스트를 넣어서 엑셀화
      ExcelDownloadUtil.dowonloadUtill(response, downlist);
    }
	
	@GetMapping("/spotRegister")
	public String spotRegister( ) {
		
		return "/company/custMgmtPage/spotMgmt/spotRegister";	
		 
	}
	
	/** 담당자 정보 모달창 : 담당자 정보 가져오기*/
		@PostMapping(value = "/getManagerInfo", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE})
	    public ResponseEntity<userVO> getManagerInfo(@RequestBody Map<String, String> requestBody) {
	    	 String spotNoStr = requestBody.get("spotNo");
	    	 int spotNo = Integer.parseInt(spotNoStr);
	    	    
	    	    userVO userVO = spotService.getManagerInfo(spotNo);
	        
	        return new ResponseEntity<userVO>(userVO, HttpStatus.OK);
	    }
	    
	    
	    /** 담당자 정보 모달창 : 담당자 정보 수정하기 */
	    @PostMapping(value = "/updateManagerInfo", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	    public ResponseEntity<String> updateManagerInfo(@RequestBody userVO vo, RedirectAttributes rttr) {
	        log.info("updateManagerInfo_success: " + 
	                 "userName=" + vo.getUserName() + ", " + 
	                 "userContact=" + vo.getUserContact() + ", " + 
	                 "userEmail=" + vo.getUserEmail() + ", " + 
	                 "userPw=" + vo.getUserPw() + ", " +
	                 "spotNo=" + vo.getSpotNo());
	        
	        int result = spotService.updateManagerInfo(vo);
	        // 클라이언트가 JSON 형식의 응답을 기대하므로 JSON 형식으로 반환합니다.
	        return new ResponseEntity<>("{\"message\": \"User information updated successfully\"}", HttpStatus.OK);
	    }

		/** 지점 임직원 리스트 모달창  : 지점 임직원 리스트 가져오기*/
		@PostMapping(value = "/getEmpList", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE})
	    public ResponseEntity<List<cEmpListVO>> getEmpList(@RequestBody Map<String, String> requestBody) {
	    	 String spotNoStr = requestBody.get("spotNo");
	    	 int spotNo = Integer.parseInt(spotNoStr);
	    	 
	    	 log.info("getEmpList_success");
	    	 log.info(spotNo);
	    	    
	    	 List<cEmpListVO> cEmpListVO = spotService.getEmpList(spotNo);
	        
	        return new ResponseEntity<List<cEmpListVO>>(cEmpListVO, HttpStatus.OK);
	    }

	    
	
	
	@GetMapping(value = "/takeComNameList", produces = {
			MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<List<companyVO>> searchModal() {

	    log.info("searchCompanyListModal_success");
	    List<companyVO> list = companyService.takeComNameList();    
	    log.info("리스트" + list);
	    System.out.println("리스트" + list);
	    return new ResponseEntity<List<companyVO>>(list, HttpStatus.OK);
	}
	
	@PostMapping(value = "/searchTakeComName", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<List<companyVO>> searchTakeComName(@RequestBody Map<String, String> jsonData) {
	    log.info("searchTakeComName_success");
	    
	    String comName = jsonData.get("comName"); 
	    
	    log.info("기업명 검색" + comName);
	    
	    List<companyVO> list = companyService.searchTakeComName(comName); 
	    
	    log.info("리스트" + list);
	    System.out.println("리스트" + list);
	    
	    return new ResponseEntity<List<companyVO>>(list, HttpStatus.OK);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	@GetMapping(value = "/takeCsNameList", produces = {
			MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<List<consultVO>> takeCsNameList() {

	    log.info("takeCsNameList_success");
	    List<consultVO> list = salesService.takeCsNameList();    
	    log.info("리스트" + list);
	    System.out.println("리스트" + list);
	    return new ResponseEntity<List<consultVO>>(list, HttpStatus.OK);
	}
	
	@PostMapping(value = "/searchTakeCsName", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<List<consultVO>> searchTakeCsName(@RequestBody Map<String, String> jsonData) {
	    log.info("searchTakeCsName_success");
	    
	    String csName = jsonData.get("csName"); 
	    
	    log.info("담당자명 검색" + csName);
	    
	    List<consultVO> list = salesService.searchTakeCsName(csName); 
	    
	    log.info("리스트" + list);
	    System.out.println("리스트" + list);
	    
	    return new ResponseEntity<List<consultVO>>(list, HttpStatus.OK);
	}
	

	/** 지점 및 지점 담당자 등록 하기*/
	@PostMapping(value = "/spotRegisterInsert")
	public String spotRegisterInsert(@RequestParam("file") MultipartFile file, spotAndUserVO vo, @RequestParam("companyNo") int companyNo, RedirectAttributes rttr) {
	    log.info("spotRegisterInsert..." + vo);

	    // 파일 업로드 처리
	    if (!file.isEmpty()) {
	        try {
	            // 파일 업로드 경로 설정
	            String uploadDir = "/uploads/";
	            Path uploadPath = Paths.get(uploadDir);
	            
	            // 업로드 디렉토리가 없으면 생성
	            if (!Files.exists(uploadPath)) {
	                Files.createDirectories(uploadPath);
	            }
	            
	            // 파일명 중복 방지를 위한 고유한 파일명 생성
	            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
	            Path filePath = uploadPath.resolve(fileName);
	            Files.copy(file.getInputStream(), filePath);
	            
	            // 업로드된 파일 경로를 VO에 설정
	            vo.setFilePath(filePath.toString());
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    // 비밀번호를 랜덤하게 생성하여 VO 객체에 설정
	    vo.setUserPwRandomly();

	    // 지점 정보, 지점 담당자 정보 저장
	    spotService.spotRegisterInsert(vo);

	    // 저장 성공 메시지 설정
	    rttr.addFlashAttribute("message", "지점 등록이 완료되었습니다.");

	    // 등록 후 리다이렉트할 경로 설정 (예시: 지점 목록 페이지)
	    return "redirect:/spotList";
	}
	
	/** 지점 정보 , 지점 담당자 정보 수정 하기*/
	@PostMapping(value = "/spotUpdate")
    public String spotUpdate(@RequestParam("newFile") MultipartFile file, spotAndUserVO vo, RedirectAttributes rttr) {
		log.info("spotUpdate..." + vo);
		
		// 파일 업로드 처리
        if (!file.isEmpty()) {
            try {
                // 파일 업로드 경로 설정
                String uploadDir = "/uploads/";
                Path uploadPath = Paths.get(uploadDir);

                // 업로드 디렉토리가 없으면 생성
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                // 파일명 중복 방지를 위한 고유한 파일명 생성
                String fileName = StringUtils.cleanPath(file.getOriginalFilename());
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(file.getInputStream(), filePath);

                // 업로드된 파일 경로를 VO에 설정
                vo.setFilePath(filePath.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 지점 정보, 지점 담당자 정보 수정 (업데이트)
        spotService.spotUpdate(vo);

        // 저장 성공 메시지 설정
        rttr.addFlashAttribute("message", "지점 정보가 변경되었습니다.");

        // 등록 후 리다이렉트할 경로 설정 (예시: 지점 목록 페이지)
        return "redirect:/spotList";
    }
}
