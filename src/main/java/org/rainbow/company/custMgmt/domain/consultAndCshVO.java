package org.rainbow.company.custMgmt.domain;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class consultAndCshVO {
	
	private int consultNo;
	private String  
	csStatus, csEname, csFailReason, csFailDetailReason;
	private Date  csResponseDate;
	
	
	private int consultHistoryNo;
	private String cshContent1, cshContent2, cshContent3, cshContent4, cshContent5;
	private Date  cshDate1, cshDate2, cshDate3, cshDate4, cshDate5;

}
