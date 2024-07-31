package org.rainbow.company.custMgmt.service;

import java.util.List;

import org.rainbow.company.custMgmt.domain.cEmpListVO;

import org.rainbow.company.custMgmt.domain.spotAndUserVO;
import org.rainbow.company.custMgmt.domain.spotDownVO;
import org.rainbow.company.custMgmt.domain.spotInputVO;
import org.rainbow.company.custMgmt.domain.spotListVO;
import org.rainbow.company.custMgmt.domain.spotSearchDTO;
import org.rainbow.company.custMgmt.domain.spotVO;
import org.rainbow.company.custMgmt.domain.userVO;

public interface spotService {
	
	/** 'spotList.jsp' 에서 지점 리스트 가져오기 */
	public List<spotVO> spotList();
	
	/** 서치바-키워드 검색*/
	public List<spotListVO> giveKeyword(String keyword);
	
	/** 엑셀 파일 업로드 version 2024-02-21 */
	public int spotExcelInput(spotInputVO vo);
	
	/** 체크박스 벨류를 받아서 필터링된 전체 데이터를 리스트로 가져온다. */
	public List<spotDownVO> downloadSpotExcel(spotSearchDTO filterResult); // 파라미터 이름 수정
	
	/** 'spotList.jsp' 에서 지점 정보 가져오기  */
	public spotVO spotView(int spotNo);
	
	/** 'salesView.jsp' 에서  지점 담당자 정보 가져오기  */
	public userVO getUserVO(int spotNo);
	
	/** 지점 정보 저장*/
	public void spotRegisterInsert(spotAndUserVO vo);
	
	/** 지점 정보 수정*/
	public void spotUpdate(spotAndUserVO vo);
	
	/**담당자 정보 모달창 : 담당자 정보 가져오기 */
	public userVO getManagerInfo(int spotNo);
	
	/**담당자 정보 모달창 : 담당자 정보 수정 */
	public int updateManagerInfo(userVO vo);
	
	/** 지점 임직원 리스트 모달창  : 지점 임직원 리스트 가져오기*/
	public List<cEmpListVO> getEmpList(int spotNo);
	


}
