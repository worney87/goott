package org.rainbow.company.custMgmt.domain;



import java.sql.Date;
import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class companyVO {
	

	private int companyNo;
    private int consultNo;
    private String comName;
    private String comBizLicenseFile; // 파일 객체로 변경
    private String comBizNum;
    private String comBizType;
    private String comArea;
    private String comAddr;
    private String comDetailAddr;
    private String comBizStatus;
    private String comBizCategory;
    private String comCEO;
    private String comContact;
    private String comEmail;
    private Date comOpenningDate; // 날짜 타입으로 변경
	    private List<attachVO> attachList; // 첨부 파일 리스트

	    private String file; // 업로드된 파일 객체
	  

}
