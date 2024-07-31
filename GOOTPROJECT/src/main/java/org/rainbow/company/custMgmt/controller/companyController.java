package org.rainbow.company.custMgmt.controller;



import java.io.IOException;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import org.rainbow.company.custMgmt.domain.companyDownVO;
import org.rainbow.company.custMgmt.domain.companyInputVO;
import org.rainbow.company.custMgmt.domain.companySearchDTO;
import org.rainbow.company.custMgmt.domain.companyVO;
import org.rainbow.company.custMgmt.service.companyServiceImpl;
import org.rainbow.domain.ExcelDownloadUtil;
import org.rainbow.domain.ExcelListener;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

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
    public ResponseEntity<String> companyExcelInput(@RequestParam("EXCEL") MultipartFile file) 
    {	
    	log.info(file);
    	int result = 0;
        ExcelListener listener = new ExcelListener();
        if (!file.isEmpty()) 
        {
            try 
            {
                // 엑셀 파일 처리를 위한 리스너로 데이터 추출
                List<companyInputVO> companyDataList = listener.companyExcelListner(file.getInputStream());
                log.info(companyDataList);
                System.out.println(companyDataList);

                // 데이터베이스에 엑셀 데이터 저장
                for(companyInputVO vo : companyDataList) 
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
    @PostMapping("/downloadCompanyExcel")
    public void downloadCompanyExcel(HttpServletResponse response, @RequestBody companySearchDTO filterResult) throws IOException 
    {
    	log.info(filterResult);

    	
    	List<companyDownVO> downlist = companyService.downloadCompanyExcel(filterResult);
    	System.out.println("다운 리스트" + downlist);
    	log.info("다운 리스트" + downlist);
    	
    
      //리스트를 넣어서 엑셀화
      ExcelDownloadUtil.dowonloadUtill(response, downlist);
    }
    
    
    
// // 기업 등록 처리
//    @PostMapping("/companyRegisterInsert")
//    public String registerCompany(@RequestBody companyVO vo, RedirectAttributes rttr) {
//        // 기업 등록 서비스 호출
//        companyService.companyRegisterInsert(vo);
//
//        // 기업 등록이 완료되었음을 사용자에게 알림
//        rttr.addFlashAttribute("message", "기업 등록이 완료되었습니다.");
//
//        // 기업 목록 페이지로 Redirect
//        return "redirect:/companyList";
//    }
    
    


//    @ResponseBody
//    @PostMapping(value = "/companyRegisterInsert", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE,
//			MediaType.APPLICATION_XML_VALUE })
//    public String companyRegisterInsert(@RequestParam("files") MultipartFile[] files, companyVO vo, @RequestParam("companyNo") int companyNo, @RequestParam("consultNo") int consultNo, RedirectAttributes rttr) {
//        
//        log.info("companyRegisterInsert" + vo);
//        // 기업 정보 및 업로드된 파일 처리
//        // companyVO 객체와 files 배열에 데이터가 전달됨
//        // 여기서 데이터를 처리하여 데이터베이스에 저장하거나 추가적인 작업을 수행할 수 있음
//        companyService.companyRegisterInsert(vo);
//        // 예시로 파일의 메타데이터 출력
//        for (MultipartFile file : files) {
//            System.out.println("Uploaded file name: " + file.getOriginalFilename());
//            System.out.println("Uploaded file size: " + file.getSize());
//        }
//
//        // 성공 메시지 반환 (추가적인 작업이 필요한 경우에는 해당 결과를 반환)
//        return "Company registration successful!";
//    }

    
    @PostMapping("/companyRegisterInsert")
    public String handleCompanyRegisterInsert(MultipartFile[] files, companyVO vo) {
        // 클라이언트로부터 받은 데이터를 이용하여 로직 수행
        // 파일 업로드 처리, 데이터베이스에 저장 등의 작업 수행
    	log.info("companyRegisterInsert" + vo);
    	companyService.companyRegisterInsert(vo);
        // 성공적인 응답 반환
        return "Company registration successful!";
    }
    

}
