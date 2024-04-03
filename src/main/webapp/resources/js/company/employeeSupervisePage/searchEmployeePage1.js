document.querySelectorAll('button').forEach( btn => {
	btn.addEventListener( 'click', (event) => {
		event.preventDefault(); 
		
		let type = btn.id;
		
		if( type === 'insertBtn') insert();
		else if ( type === 'excelBtn'){
			download();
		}
	})
})

 // id가 'status_'로 시작하는 모든 요소를 선택
 var statusElements = document.querySelectorAll("[id^=status_]");
   //각각의 status 요소에 대해 반복
   statusElements.forEach(statusElement => {
      var endDt = statusElement.textContent.trim();
      var status = "";
      
      if (!endDt || endDt === '재직 중') {
         status = "재직 중";
      } else {
    	 // 현재 날짜를 가져온다	
         var currentDate = new Date();
         // 퇴사일을 Date 객체로 변환
         var endDtDate = new Date(endDt);
         
         // 퇴사일이 현재 날짜보다 이전인 경우, '퇴사' 상태로 설정
         if (endDtDate < currentDate) {
            status = "퇴사";
         } else {
        	// 그렇지 않으면 '퇴사 예정' 상태로 설정
            status = "퇴사 예정";
         }
      }
      // 해당 status 요소의 텍스트 콘텐츠를 새로운 상태로 설정합니다.
      statusElement.textContent = status;
   });

function insert(){
	
	location.href = '/employee_insert';
}

// 사번으로 직원 정보 조회  클릭 이벤트
document.querySelectorAll('tbody a').forEach(a => {
	a.addEventListener('click', function(e){
		e.preventDefault();
		
		let eno = a.getAttribute('href');
		// 부서번호로 인사팀 아닐 시 경고창
		let deptNo = sessionStorage.getItem("deptNo");
		
		if (deptNo !== "1") { 
            alert("권한이 없습니다."); 
            return;
        }else{
        	location.href = '/employee_modify?eno=' + eno;
        }
	});
});

// 인사팀 아닐 시 버튼 숨기기
const insertBtn = document.getElementById("insertBtn");
let deptNo = sessionStorage.getItem("deptNo");
if (deptNo !== "1") {
 insertBtn.style.display = "none"; // 버튼 숨기기
}

function download(){

	let empdto = {
			"keyword" : document.querySelector('.searchBar').value,
			"dName" : document.querySelector('.dept-sb').value
	};
	console.log(typeof document.querySelector('.dept-sb').value);

	console.log(empdto);
	
	// 서버로 데이터 전송하기
	fetch('/excelDown', {
		method : 'POST',
		headers : { 'Content-Type' : 'application/json' },
		body : JSON.stringify(empdto)
	})
	.then(response => response.blob())
	.then(blob => 
	{
		console.log(blob);
		
		// 엑셀로 다운로드
		const url = window.URL.createObjectURL(new Blob([blob]));   // Blob 데이터로부터 URL 생성
		const a = document.createElement('a');						// <a> 요소 생성
		a.href = url;
		a.download = 'excelTest.xlsx';								// 다운로드되는 파일의 이름 설정
		document.body.appendChild(a);								// <a> 요소를 문서에 추가
		a.click();													// 클릭해서 다운로드 시작
		window.URL.revokeObjectURL(url);							// URL 객체 해제
		document.body.removeChild(a);								// <a> 요소를 문서에서 제거
	})
		.catch(error => 
	{
		console.error('Error : ', error);
	});
}