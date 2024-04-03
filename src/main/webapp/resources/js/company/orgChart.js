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