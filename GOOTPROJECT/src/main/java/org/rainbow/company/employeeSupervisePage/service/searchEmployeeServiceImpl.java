package org.rainbow.company.employeeSupervisePage.service;

import java.util.HashMap;
import java.util.List;

import org.rainbow.company.employeeSupervisePage.domain.EmployeeExcelDTO;
import org.rainbow.company.employeeSupervisePage.domain.EmployeeSearchDTO;
import org.rainbow.company.employeeSupervisePage.domain.rain_EmpVO;
import org.rainbow.company.employeeSupervisePage.domain.rain_employeeDTO;
import org.rainbow.company.employeeSupervisePage.mapper.searchEmployeeMapper;
import org.rainbow.domain.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j;

@Log4j
@Service
public class searchEmployeeServiceImpl implements searchEmployeeService {
	
	@Autowired
	private searchEmployeeMapper mapper;
	
	// 전체 직원 리스트
	@Override
	public List<rain_employeeDTO> getList() {
		log.info("getList...");
		return mapper.getList();
	}
	
	// 전체 직원 수 카운팅
	@Override 
	public int getTotal() { 
		log.info("getTotal..." ); 
		return mapper.getTotal(); 
	}
	
	// 직원 정보 조회
	@Override
	public rain_EmpVO get(int eno) {
		log.info("get..." + eno);
	    return mapper.get(eno); 
	}
	
	// 직원 정보 등록 
	@Override
	public void insert(rain_EmpVO vo) {
		log.info("insert..." + vo);
		mapper.insert(vo);    
	}
	
	// 직원 정보 편집
	@Override
	public void update(rain_EmpVO vo) {
		log.info("update..." + vo);
		mapper.update(vo);
	}
	
	// 개인 프로필 업데이트
	@Override
	public int profile_update(HashMap<String, Object> result) {
		log.info("profile update..." + result);
		return mapper.profile_update(result);
	}
	// 프사만 저장
	@Override
	public int profilePictureUpdate(HashMap<String, Object> result) {
		log.info("profilePicture update..." + result);
		return mapper.profilePictureUpdate(result);
	}
	// 사원 리스트 엑셀화
	@Override
	public List<EmployeeExcelDTO> excelDown(EmployeeSearchDTO empdto) {
		return mapper.excelDown(empdto);
	}
	
	// 가입된 이메일인지 체크
	@Override
	public boolean checkEmailExists(String email) {
	    return mapper.checkEmailExists(email);
	}
}
