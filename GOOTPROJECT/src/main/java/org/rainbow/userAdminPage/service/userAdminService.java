package org.rainbow.userAdminPage.service;

import java.util.HashMap;
import java.util.List;


public interface userAdminService {
	
	public HashMap<String, Object> customerLogin(HashMap<String, String> inputMap);
	
	public HashMap<String, Object> getDashboard(int spotNo);
	
	public List<HashMap<String, Object>> getBestTop5(int spotNo);
	
	public List<HashMap<String, Object>> getMonthlyData(int spotNo);
	
	public HashMap<String, Object> getUserInfo(int spotNo);
	
	public int updateUserInfo(HashMap<String, Object> updateInfo);
	
	public List<HashMap<String, Object>> getEmpList(int spotNo);
	
	public List<HashMap<String, Object>> getInquiryList(int spotNo);
	
	public boolean addQnA(HashMap<String, Object> addQnAMap);
	
	public boolean addUserEmp(HashMap<String, Object> addForm);
	
	public List<HashMap<String, Object>> getGiftList();
	
	public boolean updateEmp(HashMap<String, Object> emp);
	
	public boolean deleteEmployees(List<HashMap<String, Integer>> deleteParams);
	
	public boolean updateCustomGift(HashMap<String, Object> customGift);
	
	public boolean updateDefaultGift(HashMap<String, Object> defaultGift);
	
	public List<HashMap<String, Object>> getCustomGift(int spotNo);
	
	public HashMap<String, Object> getDefaultGift(int spotNo);
	
	public boolean saveCard(HashMap<String, Object> cardForm);
	
	public HashMap<String, Object> getCardInfo(int spotNo);
	
	public List<HashMap<String, Object>> getRecipients (HashMap<String, Object> inputValue);
	
	public List<HashMap<String, Object>> getUsageList(int spotNo);
	
	public List<HashMap<String, Object>> getDetailUsage(HashMap<String, Object> inputMap);
	
	public HashMap<String, String> sendMail(int ordNo);
	
	public String validateGiftSelection(int ordNo);
	
	public int updateStep(int ordNo);
	
	public boolean orderGift(HashMap<String, Object> orderInfo);
	
	public HashMap<String, Object> sendGift(int ordNo);
	
	public int updateOrderStep(int ordNo);
	
	public List<HashMap<String, Object>> getDownUsageList(int spotNo);
}
