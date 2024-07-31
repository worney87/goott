<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../../navBar.jsp" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>

<link rel="stylesheet" href="/resources/css/company/employeeSupervisePage/searchEmployeePage.css">

<body>

   <div id="pull-wrap">
   <h3> 직원조회 </h3>
      <div id="searchBar-select">
         <div id="searchBar">
            <span>키워드</span>
         </div>
         <div id="searchbar">
            <input class="searchBar" type="text" name="search" placeholder="이름/팀명/이메일">
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-search" viewBox="0 0 16 16" style="position: absolute; top: 50%; transform: translateY(-50%); right: 20px;">
                  <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001q.044.06.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1 1 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0"/>
              </svg>
         </div>
         <div id="selectDept">
            <span>조직별 구분</span>
            <select class="dept-sb">
               <option value="전체"> 전체 </option>
               <option value="인사"> 인사 </option>
               <option value="재무"> 재무 </option>
               <option value="영업"> 영업 </option>
               <option value="상품"> 상품 </option>
            </select>
         </div>
     	 <div class="excelBtn">
         	<button id="excelBtn"> 엑셀로 내려받기 </button>
         </div>
      </div>
      <hr style="border : 0.5px solid gray; width: 98%; margin: 0 auto; margin-top: 10px; margin-bottom: 10px;">
      <div id="searchResult_table">
         <table id="searchResult" class="table">
            <thead>
               <tr id="table_header">
                  <th>사번<button type="button" class="sort-btn" data-column="eno">🔽</button></th>
                  <th>이름</th>
                  <th>이메일</th>
                  <th>연락처</th>
                  <th>부서<button type="button" class="sort-btn" data-column="dName">🔽</button></th>
                  <th>직급<button type="button" class="sort-btn" data-column="job">🔽</button></th>
                  <th>재직상태</th>
                  <th>입사일<button type="button" class="sort-btn" data-column="hireDt">🔽</button></th>
                  <th>퇴사일<button type="button" class="sort-btn" data-column="endDt">🔽</button></th>
                  <th>계정상태<button type="button" class="sort-btn" data-column="idStatus">🔽</button></th>
               </tr>
            </thead>
            <tbody>
               <c:forEach var="vo" items="${list }">
                  <tr class="employee">
                     <td><a href="${vo.eno }">${vo.eno }</a></td>
                     <td>${vo.EName }</td>
                     <td>${vo.email }</td>
                     <td>${vo.EPhone }</td>
                     <td>${vo.DName }</td>
                     <td>${vo.job }</td>
                     <%-- <td>${empty vo.endDt ? '재직 중' : '퇴사'}</td> --%>
                     <td id="status_${vo.eno}">${empty vo.endDt ? '재직 중' : vo.endDt }</td> 
                     <td>${vo.hireDt }</td>
                     <td>${empty vo.endDt ? '-' : vo.endDt }</td>
                     <td>${vo.idStatus }</td>   
                  </tr>
               </c:forEach>
            </tbody>
         </table>
          <%-- deptNo가 1인 인사팀 경우에만 버튼을 보이도록 함 --%>
            <c:choose>
                <c:when test="${deptNo == 1}">
                    <div class="insertEmployeeBtn">
                        <button id="insertBtn" name="insertEmployee">신규등록</button>
                    </div>
                </c:when>
                 <c:otherwise>
           <%-- 인사팀 아닌 경우에는 아무것도 출력하지 않음 --%>
                   </c:otherwise>
            </c:choose>
            
             <!-- page -->
    		 <div id="pagination" class="page-wrap" align="center" style="width: 100%;">	
    		 	<ul class="page-nation">
        	 <!-- 페이지네이션은 이곳에 동적으로 생성 -->
        
    			</ul>
			 </div>
          </div>
       </div>

   <script type="text/javascript" src="/resources/js/company/employeeSupervisePage/searchEmployeePage1.js"></script>
   <script type="text/javascript" src="/resources/js/company/employeeSupervisePage/searchEmployeePage.js"></script>
</body>
</html>