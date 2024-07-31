package org.rainbow.company.custMgmt.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.rainbow.company.custMgmt.domain.cEmpListVO;
import org.rainbow.company.custMgmt.domain.companyInputVO;
import org.rainbow.company.custMgmt.domain.spotAndUserVO;
import org.rainbow.company.custMgmt.domain.spotDownVO;
import org.rainbow.company.custMgmt.domain.spotInputVO;
import org.rainbow.company.custMgmt.domain.spotListVO;
import org.rainbow.company.custMgmt.domain.spotSearchDTO;
import org.rainbow.company.custMgmt.domain.spotVO;
import org.rainbow.company.custMgmt.domain.userVO;
import org.rainbow.company.custMgmt.mapper.spotMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.log4j.Log4j;

@Log4j
@Service
public class spotServiceImpl implements spotService{
	
	@Autowired
	private spotMapper spotMapper;
	
	@Override
	public List<spotVO> spotList() {
		
		return spotMapper.spotList();
	}
	@Override
	public List<spotListVO> giveKeyword(String keyword) {
		
		return spotMapper.giveKeyword(keyword);
	}
	@Override
	public int spotExcelInput(spotInputVO vo) {
		
		return spotMapper.spotExcelInput(vo);
	}
	@Override
	public List<spotDownVO> downloadSpotExcel(spotSearchDTO filterResult) {
		
		return spotMapper.downloadSpotExcel(filterResult);
	}
	@Override
	public spotVO spotView(int spotNo) {
		
		return spotMapper.spotView(spotNo);
	}
	
	@Override
	public userVO getUserVO(int spotNo) {
		
		return spotMapper.getUserVO(spotNo);
	}
	
	@Transactional
	@Override
	public void spotRegisterInsert(spotAndUserVO vo) {
		
		int companyNo = vo.getCompanyNo();
		String spAddr = vo.getSpAddr();
		String spDetailAddr = vo.getSpDetailAddr();
		String spContact = vo.getSpContact();
		String filePa = vo.getFilePath();


		// 가져온 값들을 출력
		System.out.println("Company No: " + companyNo);
		System.out.println("Address: " + spAddr);
		System.out.println("Detail Address: " + spDetailAddr);
		System.out.println("Contact: " + spContact);
		System.out.println("filePa: " + filePa);
		
		// 1. 새로운 지점 등록
		spotMapper.spotRegisterInsert(vo);
		
		
		//2. 1번에서 등록된 지점 관리 번호 가져오기
	      int spotNo = spotMapper.getSpotNo();
	      log.info("spotNo " + spotNo);
		
	   //여기서 문제
		    // 3. 새로운 지점 담당자 등록
	      //spotMapper.userRegisterInsert(vo, spotNo);
	      Map<String, Object> params = new HashMap<>();
	      params.put("vo", vo);
	      params.put("spotNo", spotNo);
	      
	      // params 맵에서 vo 객체 가져오기
	      spotAndUserVO uvo = (spotAndUserVO) params.get("vo");

	      // vo 객체에서 companyNo 값 가져와서 로그로 출력
	      log.info("지점 등록  companyNo: " + uvo.getCompanyNo());
	      log.info("지점 등록  userName: " + uvo.getUserName());
	      log.info("지점 등록  userEmail: " + uvo.getUserEmail());
	      log.info("지점 등록  userContact: " + uvo.getUserContact());
	      log.info("지점 등록  userPw: " + uvo.getUserPw());
	      
	      
	      
	      log.info("지점 등록  vo: " + params.get("vo"));
	      log.info("지점 등록  spotNo: " + params.get("spotNo"));
	      
	      spotMapper.userRegisterInsert(params);
		
	}
	
	
	@Transactional
	@Override
	public void spotUpdate(spotAndUserVO vo) {
		
		int companyNo = vo.getCompanyNo();
		String spAddr = vo.getSpAddr();
		String spDetailAddr = vo.getSpDetailAddr();
		String spContact = vo.getSpContact();
		String filePa = vo.getFilePath();


		// 가져온 값들을 출력
		System.out.println("Company No: " + companyNo);
		System.out.println("Address: " + spAddr);
		System.out.println("Detail Address: " + spDetailAddr);
		System.out.println("Contact: " + spContact);
		System.out.println("filePa: " + filePa);
		
		// 1. 지점 정보 업데이트 
		spotMapper.spotUpdate(vo);
		
		
		//2. 1번에서 수정한 지점 관리 번호 가져오기
	      
		    int spotNo = vo.getSpotNo();
		    log.info("spotNo " + spotNo);

		// 3. 지점 담당자 정보 수정

	      Map<String, Object> params = new HashMap<>();
	      params.put("vo", vo);
	      params.put("spotNo", spotNo);
	      
	      // params 맵에서 vo 객체 가져오기
	      spotAndUserVO uvo = (spotAndUserVO) params.get("vo");

	      // vo 객체에서 companyNo 값 가져와서 로그로 출력
	      log.info("지점 등록  companyNo: " + uvo.getCompanyNo());
	      log.info("지점 등록  userName: " + uvo.getUserName());
	      log.info("지점 등록  userEmail: " + uvo.getUserEmail());
	      log.info("지점 등록  userContact: " + uvo.getUserContact());
	      log.info("지점 등록  userPw: " + uvo.getUserPw());
	      
	      
	      
	      log.info("지점 등록  vo: " + params.get("vo"));
	      log.info("지점 등록  spotNo: " + params.get("spotNo"));
	      
	      spotMapper.userUpdate(params);
		
		
	}
	
	@Override
	public userVO getManagerInfo(int spotNo) {
		
		return spotMapper.getManagerInfo(spotNo);
	}
	
	@Override
	public int updateManagerInfo(userVO vo) {
		log.info("updateManagerInfo...." + vo);
		
		int SpotNo = vo.getSpotNo();
		String UserName = vo.getUserName();
		String UserEmail = vo.getUserEmail();
		String UserContact = vo.getUserContact();
		String UserPw = vo.getUserPw();


		// 가져온 값들을 출력
		System.out.println("SpotNo: " + SpotNo);
		System.out.println("UserName: " + UserName);
		System.out.println("UserEmail: " + UserEmail);
		System.out.println("UserContact: " + UserContact);
		System.out.println("UserPw: " + UserPw);
		
		return spotMapper.updateManagerInfo(vo);
		
	}
	
		@Override
		public List<cEmpListVO> getEmpList(int spotNo) {
			
			return spotMapper.getEmpList(spotNo);
		}

}
