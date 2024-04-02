package org.rainbow.company.custMgmt.domain;


import com.alibaba.excel.annotation.ExcelProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class spotDownVO {
	
	@ExcelProperty("기업 관리 번호")
	private int companyNo;
	@ExcelProperty("지점 관리 번호")
	private int spotNo;
	@ExcelProperty("기업명")
	private String comName;
	@ExcelProperty("지점명")
	private String spName;
	@ExcelProperty("지점 주소")
	private String spAddr; 
	@ExcelProperty("지점 상세 주소")
	private String spDetailAddr; 
	@ExcelProperty("지점 회사 연락처")
	private String spContact; 
	@ExcelProperty("계약서")
	private String spAgreementFile;
	@ExcelProperty("약정 기간")
	private String spAgreementTerm;
	@ExcelProperty("자동 연장")
	private String spAutoExtension;
	@ExcelProperty("결제 방식")
	private String spPayMethod;
	@ExcelProperty("계약 인원")
	private String spEmpNum;
	@ExcelProperty("예산")
	private int spBdgt;
	@ExcelProperty("기본 지정 선물")
	private String spDefaultGift;
	@ExcelProperty("선물 커스텀 목록")
	private String spEditGift;
	@ExcelProperty("해지 처리자")
	private String spCancelEname;
	@ExcelProperty("해지 사유")
	private String spCancelReason;
	@ExcelProperty("상세 사유")
	private String spCancelDetailReason;
	@ExcelProperty("이용 상태")
	private String spStatus;
	@ExcelProperty("지점 담당자명")
	private String userName;
	//@ExcelProperty("계약 일자")
	//private Date spAgreementDate;
	//@ExcelProperty("상태 변경 일자")
	//private Date spChangeDate;
	//@ExcelProperty("지점 임직원 업데이트 날짜")
	//private Date spEmpUpdate;
	

}
