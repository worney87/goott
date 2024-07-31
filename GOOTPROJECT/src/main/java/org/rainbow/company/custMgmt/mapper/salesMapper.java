package org.rainbow.company.custMgmt.mapper;

import java.util.List;
import java.util.Map;

import org.rainbow.company.custMgmt.domain.consultAndCshVO;
import org.rainbow.company.custMgmt.domain.consultSearchDTO;
import org.rainbow.company.custMgmt.domain.consultVO;
import org.rainbow.company.custMgmt.domain.cshVO;
import org.rainbow.company.custMgmt.domain.salesDownVO;
import org.rainbow.company.employeeSupervisePage.domain.rain_EmpVO;



public interface salesMapper {
	

	/** 서치바-키워드 검색*/
	public List<consultVO> searchConsult(consultSearchDTO csSearchDto);
	
	/** 'salesList.jsp' 에서 상담 요청 리스트 가져오기  */
	public List<consultVO> salesList();
	
	/** 체크박스 벨류를 받아서 필터링된 전체 데이터를 리스트로 가져온다. */
	public List<salesDownVO> downloadSalesExcel(consultSearchDTO filterResult); // 파라미터 이름 수정

	
	/** 'salesView.jsp' 에서 상담 신청 내용 가져오기  */
	public consultAndCshVO salesView(int consultNo);
	
	/** 'salesView.jsp' 에서 영업 히스토리 내용 가져오기  */
	public cshVO getCshVO(int consultNo);
	
	/** 영업부 사원 검색(모달창) : 영업부 사원 리스트 가져오기  */
	public List<rain_EmpVO> getCsEnameListModal();
	
	/** 영업부 사원 검색(모달창) : 영업부 사원  검색 결과 가져오기 */
	public List<rain_EmpVO> searchModalCsEname(Object keyword);
	
	
	
	/** 'salesView.jsp' 에서 영업 내용 저장(수정)하기 */
	public int saveSales(consultAndCshVO vo);
	
	/** 'salesView.jsp' 에서 영업 히스토리 저장하기 */
	public int insertCsh(Map<String, Object> params);

	/** 'salesView.jsp' 에서 영업 히스토리 수정하기 */
	public int updateCsh(Map<String, Object> params);
	

	
	
	/** 기업명 찾기 모달창 : 기업 리스트 가져오기*/
	public List<consultVO> searchCompanyListModal();
	
	/** 기업명 찾기(모달창)에서 기업명 검색 기능 */
	public List<consultVO> searchModalComName(String companyName);
	
	/** 담당자명  모달창 에서 담당자 리스트 가져오기 */
	public List<consultVO> takeCsNameList();
	
	/** 담당자명  모달창 에서 검색한 담당자명 가져오기 */
	public List<consultVO> searchTakeCsName(String csName);

}
