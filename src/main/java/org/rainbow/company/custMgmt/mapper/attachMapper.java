package org.rainbow.company.custMgmt.mapper;

import java.util.List;

import org.rainbow.company.custMgmt.domain.attachVO;



public interface attachMapper {
	public void insertComBizLicenseFile(attachVO vo);
	public void deleteComBizLicenseFile(String uuid);
	public List<attachVO> findByCompanyNo(int companyNo);
	
}
