<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../navBar.jsp" %>

<link rel="stylesheet" href="/resources/css/company/orgChart.css">
	
<body>

		<div id="title">
			<h1> 조직도 </h1>
		</div>
		<div id="orgchart">
			<div id="orgchart-form">
				<div id="orgHeader">
					<ul class="orgHeader">
						<li id="header" data-toggle="modal">
							<c:forEach items="${orgchart}" var="vo">
        						<c:if test="${vo.job == '대표'}">	
									<p>${vo.job } </p>
									<Strong>${vo.EName } </Strong>
									<input type="hidden" name="email" value="${vo.email}">
									<input type="hidden" name="phoneNumber" value="${vo.EPhone}">
								</c:if>
							</c:forEach>
						</li>
					</ul>
				</div>
					<div class="lines">
          				<table class="line-table">
             				<tr>
								<td style="width: 50%;"> </td>
              					<td></td>
             				</tr>
          				</table>
        			</div>
				<div id="orgTeam">
					<ul class="team">
						<li id="reader-Name">
							<c:forEach items="${orgchart}" var="vo">
								<c:if test="${vo.job == '팀장' && vo.DName == '재무'}">
									<p> 경영 지원본분 </p>
									<strong>${vo.EName }</strong>
									<input type="hidden" name="email" value="${vo.email}">
									<input type="hidden" name="phoneNumber" value="${vo.EPhone}">
									<div class="connector"></div>
								</c:if>
							</c:forEach>
						</li>
						<li id="team-member">
							<c:forEach items="${orgchart}" var="vo">
								<c:if test="${vo.job == '대리' && vo.DName == '재무'}">
									<p> ${vo.DName }팀 </p>
									<strong> ${vo.EName } </strong>
								</c:if>
							</c:forEach>
						</li>
						<li id="team-member1">
							<c:forEach items="${orgchart}" var="vo">
								<c:if test="${vo.job == '대리' && vo.DName == '인사'}">
									<p> ${vo.DName }팀 </p>
									<strong> ${vo.EName } </strong>
								</c:if>
							</c:forEach>
						</li>
					</ul>
					<ul class="team">
						<li id="reader-Name">
							<c:forEach items="${orgchart}" var="vo">
								<c:if test="${vo.job == '팀장' && vo.DName == '인사'}">
									<p> 운영본분 </p>
									<strong> ${vo.EName } </strong>
									<input type="hidden" name="email" value="${vo.email}">
									<input type="hidden" name="phoneNumber" value="${vo.EPhone}">
									<!-- <div class="connector1"></div> -->
								</c:if>
							</c:forEach>
						</li>
						<li id="team-member">
							<c:forEach items="${orgchart}" var="vo">
								<c:if test="${vo.job == '과장' && vo.DName == '영업'}">
									<p> ${vo.DName }팀 </p>
									<strong> ${vo.EName } </strong>
								</c:if>
							</c:forEach>
						</li>
						<li id="team-member1">
							<c:forEach items="${orgchart}" var="vo">
								<c:if test="${vo.job == '대리' && vo.DName == '상품'}">
									<p> ${vo.DName }팀 </p>
									<strong> ${vo.EName } </strong>
								</c:if>
							</c:forEach>
						</li>
						<li id="team-member1">
							<c:forEach items="${orgchart}" var="vo">
								<c:if test="${vo.job == '대리' && vo.DName == '영업'}">
									<p> 마케팅팀 </p>
									<strong> ${vo.EName } </strong>
								</c:if>
							</c:forEach>
						</li>
					</ul>
					<ul class="team" style="">
						<li id="reader-Name">
							<p> 개발본분 </p>
							<strong> 리자몽 </strong>
							<input type="hidden" name="email" value="${vo.email}">
							<input type="hidden" name="phoneNumber" value="${vo.EPhone}">
							<!-- <div class="connector2"></div> -->
						</li>
						<li id="team-member">
							<p>개발팀</p>
							<strong>리자드</strong>
						</li>
						<li id="team-member1">
							<p>디자인팀</p>
							<strong>파이리</strong>
						</li>
					</ul>
				</div>
			</div>
		</div>
		
		
		<div id="modal">
    <div class="modal-content">
        <div class="modal-body">
            <div class="representative-info">
                <div>
                    <span class="modal-font">직책 :</span>
                </div>
                <p id="representativeJob"></p>
            </div>
            <div class="representative-info">
                <div>
                    <span class="modal-font">이름 :</span>
                </div>
                <p id="representativeName"></p>
            </div>
            <div class="representative-info">
                <div>
                    <span class="modal-font">전화번호 :</span>
                </div>
                <p id="representativePhoneNumber"></p>
            </div>
            <div class="representative-info">
                <div>
                    <span class="modal-font">이메일 :</span>
                </div>
                <p id="representativeEmail"></p>
            </div>
        </div>
    </div>
</div>
		
		
<script type="text/javascript" src="/resources/js/company/orgChart.js"></script>
</body>
</html>