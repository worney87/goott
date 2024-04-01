package org.rainbow.company.custMgmt.service;

import org.rainbow.company.custMgmt.mapper.attachMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j;

@Log4j
@Service
public class attachServiceImpl implements attachService{
	
	
	@Autowired
	 private attachMapper attachMapper;
		
	
	
	
	
	@Override
	public void delete(String uuid) {
		log.info("delete...." + uuid);
		return;
		
	}
}