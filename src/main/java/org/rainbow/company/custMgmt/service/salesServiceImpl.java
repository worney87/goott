package org.rainbow.company.custMgmt.service;


import java.util.List;


import org.rainbow.company.custMgmt.domain.consultAndCshVO;
import org.rainbow.company.custMgmt.domain.consultSearchDTO;
import org.rainbow.company.custMgmt.domain.consultVO;
import org.rainbow.company.custMgmt.domain.cshVO;
import org.rainbow.company.custMgmt.domain.salesDownVO;
import org.rainbow.company.custMgmt.mapper.salesMapper;
import org.rainbow.company.employeeSupervisePage.domain.rain_EmpVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.log4j.Log4j;

@Log4j
@Service
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class salesServiceImpl implements salesService {

	@Autowired
	private salesMapper salesMapper;
	
	@Override
	public List<consultVO> searchConsult(consultSearchDTO csSearchDto) {
		
		return salesMapper.searchConsult(csSearchDto);
	}

	@Override
	public List<consultVO> salesList() {
		
		return salesMapper.salesList();
	}
	
	@Override
	public List<salesDownVO> downloadSalesExcel(consultSearchDTO filterResult) {
		
		return salesMapper.downloadSalesExcel(filterResult);
	}
	@Override
	public consultAndCshVO salesView(int consultNo) {
		
		return salesMapper.salesView(consultNo);
	}
	
	@Override
	public cshVO getCshVO(int consultNo) {
		
		return salesMapper.getCshVO(consultNo);
	}
	


	 	@Override
	    @Transactional
	    public void saveOrUpdateSales(consultAndCshVO vo) {
	        if (vo.getConsultHistoryNo() == 0) {
	            // 새로운 상담 히스토리인 경우

	        	// 영업 내용 저장(수정)
	        	salesMapper.saveSales(vo);
	            // 상담 히스토리 저장
	            salesMapper.insertCsh(vo.toMap());

	        } else {
	            // 기존 상담 히스토리의 경우 업데이트 로직 구현
	            // 상담 히스토리 수정
	        	// 영업 내용 저장(수정)
	        	salesMapper.saveSales(vo);
	            salesMapper.updateCsh(vo.toMap());
	        }
	    }




	
	
	
	
	@Override
	public List<consultVO> searchCompanyListModal() {
		log.info("searchCompanyListModal...." );
		
		List<consultVO> list = salesMapper.searchCompanyListModal();
		
		log.info(list);
		
		for (consultVO vo : list) {
		    System.out.println("consultNo 서비스 확인: " + vo.getConsultNo());
		}
		
		return list;
	}
	
		@Override
		public List<consultVO> searchModalComName(String companyName) {
			log.info("searchModalComName...." );
			return salesMapper.searchModalComName(companyName);
		}
		
		@Override
		public List<consultVO> takeCsNameList() {
			
			return salesMapper.takeCsNameList();
		}

		@Override
		public List<consultVO> searchTakeCsName(String csName) {
			
			return salesMapper.searchTakeCsName(csName);
		}
		
		@Override
		public List<rain_EmpVO> getCsEnameListModal() {
			
			return salesMapper.getCsEnameListModal();
		}
		
		@Override
		public List<rain_EmpVO> searchModalCsEname(Object keyword) {
			
			return salesMapper.searchModalCsEname(keyword);
		}


}