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

	/** 'salesList.jsp' 에서 상담 요청 리스트 가져오기 */
	@GetMapping(value = "/salesList")
	public String salesList(Model model) {
		log.info("salesList_success");

		model.addAttribute("consultVO", salesService.salesList());

		return "/company/custMgmtPage/salesMgmt/salesList";

	}
	/** 서치바 키워드 및 기간 검색 */
    @ResponseBody
	@PostMapping(value = "/searchConsult", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<consultVO>> searchConsult(@RequestBody consultSearchDTO csSearchDto)
	{
    	log.info(csSearchDto);
    	List<consultVO> list = salesService.searchConsult(csSearchDto);
		log.info(list);
		
		return new ResponseEntity<List<consultVO>>(list, HttpStatus.OK);
				
	}	

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


	/** 'salesView.jsp' 에서 영업 내용, 영업 히스토리 저장(수정)하기 */
    @PostMapping("/saveSales")
    public String saveSales(consultAndCshVO vo, RedirectAttributes rttr) {
        log.info("saveSales_success" + vo);
        
        // 클라이언트에서 전달한 consultHistoryNo를 사용하여 저장 또는 수정을 결정
        if (vo.getConsultHistoryNo() != 0) {
            // consultHistoryNo가 0이 아니면 수정을 수행
            salesService.updateSalesAndHistory(vo);
        } else {
            // consultHistoryNo가 0이면 저장을 수행
            salesService.saveSales(vo);
        }
        
        rttr.addFlashAttribute("result","success");
        
        return "redirect:/salesList";
    }



	
	

	@GetMapping(value = "/searchModal", produces = {
			MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<List<consultVO>> searchModal() {
	    log.info("안녕");
	    log.info("searchCompanyListModal_success");
	    List<consultVO> list = salesService.searchCompanyListModal();    
	    log.info("리스트" + list);
	    System.out.println("리스트" + list);
	    return new ResponseEntity<List<consultVO>>(list, HttpStatus.OK);
	}
	
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
