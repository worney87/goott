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
public class cEmpListVO {
	
	private int spotNo , cEmpNo, spBdgt;
	private String cEmpName, cEmpTel, cEmpEmail, cEmpPosition, prdName;
	private Date  cEmpBirth, orderDate;
	


}
