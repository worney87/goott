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
						<li id="header">
							<c:forEach items="${orgchart}" var="vo">
        						<c:if test="${vo.job == '대표'}">	
									<p>${vo.job } </p>
									<Strong>${vo.EName } </Strong>
								</c:if>
							</c:forEach>
						</li>
					</ul>
				</div>
				<div id="orgTeam">
					<ul class="team">
						<li id="reader-Name">
							<c:forEach items="${orgchart}" var="vo">
								<c:if test="${vo.job == '팀장' && vo.DName == '재무'}">
									<p> 경영 지원본분 </p>
									<strong>${vo.EName }</strong>
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
									<div class="connector1"></div>
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
							<div class="connector2"></div>
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
</body>
</html>