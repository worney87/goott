package org.rainbow.company.custMgmt.domain;



import com.alibaba.excel.annotation.ExcelProperty;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class companyDownVO {
	
	@ExcelProperty("기업 관리 번호")
	private int companyNo;
	
	@ExcelProperty("상담 신청 관리 번호")
	private int consultNo; 
	
	@ExcelProperty("기업명")
	private String comName;
	
	@ExcelProperty("사업자등록증")
	private String comBizLicenseFile;
	
	@ExcelProperty("사업자 등록 번호")
	private String comBizNum;
	
	@ExcelProperty("사업자 구분")
	private String comBizType;
	
	@ExcelProperty("기업 소재 지역")
	private String comArea; 
	
	@ExcelProperty("기업 주소")
	private String comAddr; 
	
	@ExcelProperty("상세 주소")
	private String comDetailAddr;	
	
	@ExcelProperty("업태")
	private String	comBizStatus; 
	
	@ExcelProperty("업종")
	private String	comBizCategory; 
	
	@ExcelProperty("대표자")
	private String	comCEO; 
	
	@ExcelProperty("회사 연락처")
	private String	comContact; 
	
	@ExcelProperty("이메일")
	private String	comEmail; 
	
	//@DateTimeFormat("yyyy-MM-dd")
	//@ExcelProperty("창립 기념일")
	//private Date comOpenningDate; 
	
	

}
