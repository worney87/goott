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
		
		
	<script type="text/javascript">
		// 대표 정보가 표시된 요소
		var headerElement = document.getElementById("header");

		// 모달창 요소
		var modal = document.getElementById("modal");

		// 대표 정보를 클릭했을 때
		headerElement.addEventListener("click", function(event) {
		
		// 대표 정보 요소에서 직책과 이름을 가져옴
	    var job = this.querySelector("p").innerText.trim();
	    var name = this.querySelector("strong").innerText.trim();
	    
	 	// 대표 정보 요소에서 이메일과 전화번호를 가져옴
	    var email = this.querySelector("input[type='hidden'][name='email']").value;
	    var phoneNumber = this.querySelector("input[type='hidden'][name='phoneNumber']").value;

	    // 모달창 내용 업데이트
	    document.getElementById("representativeJob").innerText = job;
	    document.getElementById("representativeName").innerText = name;
	    document.getElementById("representativeEmail").innerText = email; // 추가: 이메일 표시
	    document.getElementById("representativePhoneNumber").innerText = phoneNumber; // 추가: 전화번호 표시
	    
	    // 모달창의 너비
	    var modalWidth = modal.offsetWidth;

	    // 클릭한 요소의 위치 정보를 가져옴
	    var rect = event.target.getBoundingClientRect();

	    // 모달창이 오른쪽에 나타나도록 설정
	    var modalLeft = rect.left + window.scrollX + rect.width + 170;
	    var modalTop = rect.top + window.scrollY + 50;

	    // 모달창이 화면을 벗어나는지 확인하고 위치 조정
	    if (modalLeft + modalWidth > window.innerWidth) {
	        modalLeft = window.innerWidth - modalWidth;
	    } 

	    // 모달창 위치 설정
	    modal.style.left = modalLeft + "px";
	    modal.style.top = modalTop + "px";
	    
	    // 모달창을 나타냄
	    modal.style.display = "block";

	    // 이벤트 전파(stopPropagation) 방지
	    event.stopPropagation();
	});
	
		// 리더 정보가 표시된 요소
		var readerElements = document.querySelectorAll("#reader-Name"); 
	
		// 모달창 요소
		var modal = document.getElementById("modal");
 
		// 리더 정보를 클릭했을 때
    	readerElements.forEach(function(readerElement) {
    		readerElement.addEventListener("click", function(event) {
    
		// 리더 정보 요소에서 직책과 이름을 가져옴
	    var job = this.querySelector("p").innerText.trim();
	    var name = this.querySelector("strong").innerText.trim();
	    
	 	// 리더 정보 요소에서 이메일과 전화번호를 가져옴
	    var email = this.querySelector("input[type='hidden'][name='email']").value;
	    var phoneNumber = this.querySelector("input[type='hidden'][name='phoneNumber']").value;

	    // 모달창 내용 업데이트
	    document.getElementById("representativeJob").innerText = job;
	    document.getElementById("representativeName").innerText = name;
	    document.getElementById("representativeEmail").innerText = email; // 추가: 이메일 표시
	    document.getElementById("representativePhoneNumber").innerText = phoneNumber; // 추가: 전화번호 표시
	    
	    // 모달창의 너비
	    var modalWidth = modal.offsetWidth;

	    // 클릭한 요소의 위치 정보를 가져옴
	    var rect = event.target.getBoundingClientRect();

	    // 모달창이 오른쪽에 나타나도록 설정
	    var modalLeft = rect.left + window.scrollX + rect.width + 170;
	    var modalTop = rect.top + window.scrollY + 50;

	    // 모달창이 화면을 벗어나는지 확인하고 위치 조정
	    if (modalLeft + modalWidth > window.innerWidth) {
	        modalLeft = window.innerWidth - modalWidth;
	    } 

	    // 모달창 위치 설정
	    modal.style.left = modalLeft + "px";
	    modal.style.top = modalTop + "px";
	    
	    // 모달창을 나타냄
	    modal.style.display = "block";

	    // 이벤트 전파(stopPropagation) 방지
	    event.stopPropagation();
	});
});
		
		// 다른 곳을 클릭했을 때 모달창이 닫히도록 설정
		document.addEventListener("click", function(event) {
	    // 모달창을 클릭한 경우에는 모달창이 닫히지 않도록 설정
	    if (!modal.contains(event.target)) {
	        modal.style.display = "none";
	    }
	});

	</script>
</body>
</html>