package org.rainbow.company.custMgmt.service;


import java.util.List;
import java.util.Map;

import org.rainbow.company.custMgmt.domain.attachVO;
import org.rainbow.company.custMgmt.domain.companyDownVO;
import org.rainbow.company.custMgmt.domain.companyInputVO;

import org.rainbow.company.custMgmt.domain.companyVO;
import org.rainbow.company.custMgmt.mapper.attachMapper;
import org.rainbow.company.custMgmt.mapper.companyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.log4j.Log4j;

@Log4j
@Service
public class companyServiceImpl implements companyService{
	
	@Autowired
	private companyMapper companyMapper;
	
	
	@Autowired
	private attachMapper attachMapper;
	
	@Override
	public List<companyVO> giveKeyword(String keyword) {
		
		return companyMapper.giveKeyword(keyword);
	}
	
	
	
	@Override
	public List<companyVO> companyList() {
		
		return companyMapper.companyList();
	}
	
	@Transactional
	@Override
	public void companyRegisterInsert(companyVO vo) {
		
		log.info("companyRegister...  vo: " + vo);
		
		
		  //1. tbl_board 테이블에 게시글 등록
		companyMapper.companyRegisterInsert(vo);
	      
	      //2. 1번에서 등록된 게시글의 번호 가져오기
	      int companyNo = companyMapper.getCompanyNo();
	      log.info("companyNo " + companyNo);
	      //3. 첨부 파일 등록
	      //3-1. 첨부파일이 있는지 확인한다
	      //3-2. 첨부 파일 테이블에 insert 되어야 하는 데이터를 확인한다
	      //3-3. .insert 한다
	      //uuid, fileName, uploadPath, bno
	      if(vo.getAttachList()!=null && vo.getAttachList().size() > 0) {
	    	  for(attachVO attachVO : vo.getAttachList()) {
	    		  attachVO.setCompanyNo(companyNo);
	    		  attachMapper.insertComBizLicenseFile(attachVO);
	    	  }
	    	
	      }
		
		
	}
	
	@Override
	public companyVO companyView(int companyNo) {
		
		return companyMapper.companyView(companyNo);
	}
	
	
	@Override
	public List<companyVO> checkBizNum(List<String> bizNumArray) {
		
		return companyMapper.checkBizNum(bizNumArray);
	}
	
	@Override
	public int insertCompanyExcel(companyInputVO vo) {
		
		return companyMapper.insertCompanyExcel(vo);
	}
	@Override
	public List<companyDownVO> downExcelList(Map<String, Object> filteredValue) {
		
		return companyMapper.downExcelList(filteredValue);
	}
	@Override
	public List<companyVO> getCompanyLicenseFileURL(String jsonData) {
	
		log.info("getCompanyLicenseFileURL...." );
		return companyMapper.getCompanyLicenseFileURL(jsonData);
	}
	@Override
	public int updateCompany(companyVO vo) {
		log.info("updateCompany...." + vo);
		
		int list = companyMapper.updateCompany(vo);
		log.info(list);
		return list;
	}
	
	@Override
	public List<companyVO> takeComNameList() {
		
		return companyMapper.takeComNameList();
	}
	
	@Override
	public List<companyVO> searchTakeComName(String comName) {
		
		return companyMapper.searchTakeComName(comName);
	}
	

}
