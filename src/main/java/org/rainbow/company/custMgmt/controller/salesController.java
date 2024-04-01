package org.rainbow.company.custMgmt.controller;


import java.util.List;
import java.util.Map;

import org.rainbow.company.custMgmt.domain.consultAndCshVO;
import org.rainbow.company.custMgmt.domain.consultSearchDTO;
import org.rainbow.company.custMgmt.domain.consultVO;
import org.rainbow.company.custMgmt.domain.cshVO;
import org.rainbow.company.custMgmt.service.salesServiceImpl;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.log4j.Log4j;
@CrossOrigin(origins = "<http://localhost:8080>")
@Log4j
@Controller
public class salesController {

	@Autowired
	salesServiceImpl salesService;

	/** 'salesList.jsp' : 상담 요청 리스트 가져오기 */
	@GetMapping(value = "/salesList")
	public String salesList(Model model) {
		log.info("salesList_success");

		model.addAttribute("consultVO", salesService.salesList());

		return "/company/custMgmtPage/salesMgmt/salesList";
	}
	
	/** 'salesList.jsp' : 서치바 키워드 및 기간 검색 */
    @ResponseBody
	@PostMapping(value = "/searchConsult", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<consultVO>> searchConsult(@RequestBody consultSearchDTO csSearchDto){
    	log.info(csSearchDto);
    	List<consultVO> list = salesService.searchConsult(csSearchDto);
		log.info(list);
		
		return new ResponseEntity<List<consultVO>>(list, HttpStatus.OK);		
	}	
    
    /** 'salesView.jsp' : 상담 및 영업 정보 가져오기 */
    @GetMapping("/salesView")
    public String salesView(int consultNo, Model model) {
        log.info("salesView_success" + consultNo);
        
        // consultVO 가져오기
        consultVO consultVO = salesService.salesView(consultNo);
        model.addAttribute("consultVO", consultVO);
        
        // cshVO 가져오기
        cshVO cshVO = salesService.getCshVO(consultNo); 
        model.addAttribute("cshVO", cshVO);
        
        return "/company/custMgmtPage/salesMgmt/salesView";
    }
    
	/** 'salesView.jsp' : 영업 내용, 영업 히스토리 저장(수정)하기 */
    @PostMapping("/saveSales")
    public String saveSales(consultAndCshVO vo, RedirectAttributes rttr) {
        log.info("saveSales_success" + vo);
        
        log.info(
                "ConsultNo=" + vo.getConsultNo() + ", " + 
                "CsStatus=" + vo.getCsStatus() + ", " + 
                "CsEname=" + vo.getCsEname() + ", " + 
                "CsFailReason=" + vo.getCsFailReason() + ", " +
                "CsResponseDate=" + vo.getCsResponseDate()+ ", " +
                "ConsultHistoryNo=" + vo.getConsultHistoryNo()+ ", " +
                "CshContent1=" + vo.getCshContent1()+ ", " +
                "CshDate1=" + vo.getCshDate1()
        		
        		);
    
            salesService.saveSales(vo);

        
        rttr.addFlashAttribute("result","success");
        
        return "redirect:/salesList";
    }
    
    
    /** 'salesView.jsp' : 영업 내용, 영업 히스토리 저장(수정)하기 */
    @PostMapping("/updateSalesAndHistory")
    public String updateSalesAndHistory(consultAndCshVO vo, RedirectAttributes rttr) {
        log.info("updateSalesAndHistory_success" + vo);
        
     
        salesService.updateSalesAndHistory(vo);
        
        rttr.addFlashAttribute("result","success");
        
        return "redirect:/salesList";
    }
    
    
    /** 'companyViewView.jsp' : 기업명 찾기 - 기업명 리스트로 가져오기 */
    @GetMapping(value = "/searchModal", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<List<consultVO>> searchModal() {
        List<consultVO> list = salesService.searchCompanyListModal();
        
        // consultVO 리스트의 consultNo 값 및 형식 확인
        for (consultVO vo : list) {
            Object consultNo = vo.getConsultNo();
            System.out.println("consultNo의 형식: " + consultNo.getClass().getSimpleName());
            System.out.println("consultNo의 값: " + consultNo);
            System.out.println("CsCompanyName: " + vo.getCsCompanyName());
        }
        
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
	
	/** 'companyView.jsp' : 기업명 찾기 - 기업명 검색 결과 가져오기*/
	@PostMapping(value = "/searchModalComName", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<List<consultVO>> searchModalComName(@RequestBody Map<String, String> jsonData) {
	    log.info("searchModalComName_success");
	    
	    String companyName = jsonData.get("companyName"); 
	    
	    log.info("기업명 검색" + companyName);
	    
	    List<consultVO> list = salesService.searchModalComName(companyName); 
	    
	    log.info("리스트" + list);
	    System.out.println("리스트" + list);
	    
	    return new ResponseEntity<List<consultVO>>(list, HttpStatus.OK);
	}

    
}
