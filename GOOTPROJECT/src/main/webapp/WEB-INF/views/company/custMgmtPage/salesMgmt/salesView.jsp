<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>newSalesViewDetails.jsp</title>
</head>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<body>
	<div class="navBar">
		<jsp:include page="../../../navBar.jsp"/>
	</div>
	
	<div class="container-fluid" align="center" style="padding: 100px;">
	
	<div class="entire">
	<div class="title" align="left">
		<h3>신규 영업</h3>
	</div>
	<div align="left">
	<div class="subject_title_div" style="width:162px;">● 상담 신청 내용
	</div>
	</div>
	<table class="subject_content_tbl" id="consult_tbl">
	<thead>
	<tr>
						<td class="tbl_subtitle">기업명</td>
						<td>
							<input type="text" name="csCompanyName" value="${consultAndCshVO.csCompanyName }" readonly 
							>
						</td>
						<td rowspan="3"></td>
						<td class="tbl_subtitle">상담 신청일</td>
						<td>
							<input type="text" name="csDate" value="${consultAndCshVO.csDate }" readonly>
						</td>
						<td rowspan="3"></td>
						<td class="tbl_subtitle">지역</td>
						<td>
							<input type="text" name="csArea" value="${consultAndCshVO.csArea }" readonly>
						</td>
						<td></td>
					</tr>
	
	<tr>
						<td class="tbl_subtitle">담당자</td>
						<td>
							<input type="text" name="csName" value="${consultAndCshVO.csName }" readonly>
						</td>
						
						<td class="tbl_subtitle">연락처</td>
						<td>
							<input type="text" name="csContact" value="${consultAndCshVO.csContact }" readonly>
						</td>
						
						<td class="tbl_subtitle">이메일</td>
						<td colspan="2" >
							<input type="email" name="csEmail" value="${consultAndCshVO.csEmail }" readonly style="width:250px;">
						</td>
						
						
					</tr>	
					<tr>
						<td class="tbl_subtitle">직원 수</td>
						<td>
							<input type="text" name="csEmpNum" value="${consultAndCshVO.csEmpNum }" readonly>
						</td>
						
						<td class="tbl_subtitle">예상 예산</td>
						<td>
							<input type="text" name="csBdgt" value="${consultAndCshVO.csBdgt }" readonly>
						</td>
						
						<td colspan="3"></td>

					</tr>
					<tr>
						<td class="tbl_subtitle">문의 내용</td>
						<td colspan="8" id="inquiryDetails">
						<textarea rows="10"  style="width: 95%; resize: none" readonly>${consultAndCshVO.csContent}</textarea>
						
						</td>

					</tr>

	</thead>
	</table>

	<div align="left">
	<div class="subject_title_div">● 영업 내용
	</div>
	</div>
	
	<form method="post">
	<table class="subject_content_tbl">
	<thead>
	
						<tr>
						<td class="tbl_subtitle">영업 담당자</td>
						<td><div class="input-with-image">
						<input type="text" name="csEname" value="${consultAndCshVO.csEname }" readonly><a href="#" id="open_searchEname_modal">
						<input type="button" id="imgBtnSearchEname"></a>
						</div></td>
						<td></td>
						<td class="tbl_subtitle">영업 상태</td>
						<td>
							<select name="csStatus" id="selectSalesStatus">
							<option value="최초 인입" ${consultAndCshVO.csStatus eq '최초 인입' ? 'selected' : ''}>최초 인입</option>
							<option value="응대 완료" ${consultAndCshVO.csStatus eq '응대 완료' ? 'selected' : ''}>응대 완료</option>
							<option value="견적 발송 완료" ${consultAndCshVO.csStatus eq '견적 발송 완료' ? 'selected' : ''}>견적 발송 완료</option>
							<option value="미팅 완료" ${consultAndCshVO.csStatus eq '미팅 완료' ? 'selected' : ''}>미팅 완료</option>
							<option value="계약 완료" ${consultAndCshVO.csStatus eq '계약 완료' ? 'selected' : ''}>계약 완료</option>
							<option value="계약 실패" ${consultAndCshVO.csStatus eq '계약 실패' ? 'selected' : ''}>계약 실패</option>
							
		
							</select>
						</td>
						<td></td>
						<td class="tbl_subtitle">응대일</td>
						<td>
							<input type="date" name="csResponseDate" placeholder="날짜 입력" value="${consultAndCshVO.csResponseDate }" >
						</td>
						<td></td>
					</tr>
					 <tr>
		                <td class="tbl_subtitle">영업 히스토리</td>
		                <td colspan="8" style="background-color: #EEEEEE;"></td>
		            </tr>
			</thead>
			</table>		
		    <div class="salesHistory">
		        <table class="subject_content_tbl">
		            <tr>
		                <td class="tbl_subtitle">날짜 </td>
		                <td colspan="8">
		                    <input type="date" name="cshDate1" placeholder="영업 날짜 선택" value="${consultAndCshVO.cshDate1 }" style="width: 150px">
		                </td>
		            </tr>
		            <tr>
		                <td class="tbl_subtitle">내용</td>
		                <td colspan="8">
		                    <textarea rows="10" placeholder="영업 내용 입력" style="width: 90%; resize: none" name="cshContent1">${consultAndCshVO.cshContent1 }</textarea>
		                </td>
		            </tr>
		        </table>
		    </div>
		    
		    
			<div class="salesHistoryEntry" style="display: none;">
		        <table class="subject_content_tbl">
		            <tr>
		                <td class="tbl_subtitle">날짜 </td>
		                <td colspan="8">
		                    <input type="date" name="cshDate2" placeholder="영업 날짜 선택" value="${consultAndCshVO.cshDate2 }" style="width: 150px">
		                </td>
		            </tr>
		            <tr>
		                <td class="tbl_subtitle">내용</td>
		                <td colspan="8">
		                    <textarea rows="10" placeholder="영업 내용 입력" style="width: 90%; resize: none" name="cshContent2">${consultAndCshVO.cshContent2 }</textarea>
		                </td>
		            </tr>
		        </table>
		    </div>
		    	    <div class="salesHistoryEntry" style="display: none;">
		        <table class="subject_content_tbl">
		            <tr>
		                <td class="tbl_subtitle">날짜 </td>
		                <td colspan="8">
		                    <input type="date" name="cshDate3" placeholder="영업 날짜 선택" value="${consultAndCshVO.cshDate3 }" style="width: 150px">
		                </td>
		            </tr>
		            <tr>
		                <td class="tbl_subtitle">내용</td>
		                <td colspan="8">
		                    <textarea rows="10" placeholder="영업 내용 입력" style="width: 90%; resize: none" name="cshContent3">${consultAndCshVO.cshContent3 }</textarea>
		                </td>
		            </tr>
		        </table>
		    </div>
		    	    <div class="salesHistoryEntry" style="display: none;">
		        <table class="subject_content_tbl">
		            <tr>
		                <td class="tbl_subtitle">날짜 </td>
		                <td colspan="8">
		                    <input type="date" name="cshDate4" placeholder="영업 날짜 선택" value="${consultAndCshVO.cshDate4 }" style="width: 150px">
		                </td>
		            </tr>
		            <tr>
		                <td class="tbl_subtitle">내용</td>
		                <td colspan="8">
		                    <textarea rows="10" placeholder="영업 내용 입력" style="width: 90%; resize: none" name="cshContent4">${consultAndCshVO.cshContent4 }</textarea>
		                </td>
		            </tr>
		        </table>
		    </div>
		    	    <div class="salesHistoryEntry" style="display: none;">
		        <table class="subject_content_tbl">
		            <tr>
		                <td class="tbl_subtitle">날짜 </td>
		                <td colspan="8">
		                    <input type="date" name="cshDate5" placeholder="영업 날짜 선택" value="${consultAndCshVO.cshDate5 }" style="width: 150px">
		                </td>
		            </tr>
		            <tr>
		                <td class="tbl_subtitle">내용</td>
		                <td colspan="8">
		                    <textarea rows="10" placeholder="영업 내용 입력" style="width: 90%; resize: none" name="cshContent5">${consultAndCshVO.cshContent5 }</textarea>
		                </td>
		            </tr>
		        </table>
		    </div>

	
	<div class="space_img_btns">
				 <input type="button" class="salesViewBtns" id="imgBtnPlus">
				 <input type="button" class="salesViewBtns" id="imgBtnMinus">
				</div>
			<table class="subject_content_tbl" id="failReasonDiv" style="display:none">
				<thead>
				<tr>
						<td class="tbl_subtitle">계약 실패 사유</td>	
						<td>
							<select name="csFailReason" id="selectCsFailReason">
							<option value="단가 불만족" ${consultAndCshVO.csFailReason eq '단가 불만족' ? 'selected' : ''}>단가 불만족</option>
							<option value="상품 불만족" ${consultAndCshVO.csFailReason eq '상품 불만족' ? 'selected' : ''}>상품 불만족</option>
							<option value="서비스 불만족" ${consultAndCshVO.csFailReason eq '서비스 불만족' ? 'selected' : ''}>서비스 불만족</option>
							<option value="경쟁사 이용" ${consultAndCshVO.csFailReason eq '경쟁사 이용' ? 'selected' : ''}>경쟁사 이용</option>
							<option value="중복인입" ${consultAndCshVO.csFailReason eq '중복인입' ? 'selected' : ''}>중복인입</option>
							<option value="허위인입" ${consultAndCshVO.csFailReason eq '허위인입' ? 'selected' : ''}>허위인입</option>
							<option value="기타" ${consultAndCshVO.csFailReason eq '기타' ? 'selected' : ''}>기타</option>
							</select>
						</td>
						<td colspan="7"></td>
						
					</tr>

				<tr>
						<td class="tbl_subtitle">내용</td>
						<td colspan="8">
						<textarea rows="10" style="width: 90%; resize: none" id="csFailDetailReason" placeholder="계약 실패 사유 상세 기재" name="csFailDetailReason">${consultAndCshVO.csFailDetailReason}</textarea>
						</td>
					
					
					</tr>
				
				
				</thead>
				</table>
	
		<div class="btn_div">
		<input type="button" class="salesViewBtns" id="saveBtn" value="저장">
		<input type="hidden" name="consultNo" value="${consultAndCshVO.consultNo }">
		<input type="hidden" name="consultHistoryNo" value="${consultAndCshVO.consultHistoryNo }">
		</div>
</form>



			<!--------------------------------------- 영업부 사원 검색 모달창-->
			<div class="modal" id="searchCsEname_modal">
    <div class="modal-content">
        <div class="modal-top">
            <table>
                <tr class="bordered-row">
                    <td><input type="text" name="keyword" size="20" placeholder="영업부 사원" style="border: 1px solid black;"></td>
                    <td><input type="button" id="searchBtn" value="검색"></td>
                </tr>
            </table>
        </div>
        <div class="list_div_modal">
            <table class="list_div_tbl_modal" id="searchCsEnameModal_tbl">
                <thead>
                    <tr class="top_bar_of_list_modal">
                    	<td>사원번호</td>
                        <td>성명</td>
                        <td>직급</td>
                        
                    </tr>
                </thead>
                	<tbody>
						
					</tbody>
            </table>
        </div>
    <div class="modal-bottom">
    	<input type="button" id="close_searchEname_modal" value="닫기">

    </div>
    </div>
</div>
	</div>

</div>
<script type="text/javascript" src="/resources/js/company/custMgmtPage/salesView.js"></script>
<script type="text/javascript" src="/resources/js/company/custMgmtPage/salesViewModal.js"></script>

</body>
</html>