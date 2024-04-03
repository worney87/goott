package org.rainbow.company.custMgmt.domain;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class consultAndCshVO {
	
	private int consultNo, csBdgt, csEmpNum;
	private String csCompanyName, csArea, csName, csContact, csEmail, csContent, 
	csStatus, csEname, csFailReason, csFailDetailReason;
	private Date  csDate, csResponseDate;
	
	
	private int consultHistoryNo;
	private String cshContent1, cshContent2, cshContent3, cshContent4, cshContent5;
	private Date  cshDate1, cshDate2, cshDate3, cshDate4, cshDate5;
	// consultAndCshVO 객체를 Map<String, Object>으로 변환하는 메서드
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("consultHistoryNo", this.consultHistoryNo);
        map.put("consultNo", this.consultNo);
        map.put("cshDate1", this.cshDate1);
        map.put("cshContent1", this.cshContent1);
        map.put("cshDate2", this.cshDate2);
        map.put("cshContent2", this.cshContent2);
        map.put("cshDate3", this.cshDate3);
        map.put("cshContent3", this.cshContent3);
        map.put("cshDate4", this.cshDate4);
        map.put("cshContent4", this.cshContent4);
        map.put("cshDate5", this.cshDate5);
        map.put("cshContent5", this.cshContent5);
        return map;
    }

}
