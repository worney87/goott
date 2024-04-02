/*// ----- css 파일 추가
// 1. 파일 경로 설정
const CSS_FILE_PATH = '/resources/css/employeeSupervisePage/employee_insert.css';
// 2. link 태그 생성
let linkEle = document.createElement('link');
linkEle.rel = 'stylesheet';
linkEle.type = 'text/css';
linkEle.href = CSS_FILE_PATH;
// 3. head 태그에 link 엘리먼트 추가
document.head.appendChild(linkEle);*/

const f = document.forms[0];

document.querySelectorAll('input[type="button"]').forEach( btn => {
	btn.addEventListener( 'click', (event) => {
		event.preventDefault(); 
		
		let type = btn.id;
		
		if( type === 'closeBtn') close();
		else if ( type === 'saveBtn'){
			insert(f);
		}
	})
})

function close(){
	location.href = '/searchEmployee';
}

function insert(f) {
	
	if (!/^[a-zA-Z가-힣]{2,10}$/.test(f.eName.value)) {
	    alert("이름은 한글이나 영어로 최소 2글자에서 최대 10글자까지 입력해야 합니다.");
	    return false;
    } else if (f.dName.value != '인사' && f.dName.value != '재무' && f.dName.value != '영업' && f.dName.value != '상품') {
        alert("부서명이 올바르지 않습니다. 인사, 재무, 영업, 상품 중 하나를 입력하세요.");
        return false;
    } else if (!/^\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\d|3[01])-(?:[1234]\d{6})$/.test(f.identyNum.value)) {
        alert("올바른 주민등록번호 형식이 아닙니다.");
        return false;
    } else if (f.job.value == '') {
        alert("직급을 입력하세요");
        return false;
    } else if (!/^\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\d|3[01])-(?:[1234]\d{6})$/.test(f.identyNum.value)) {
        alert("올바른 주민등록번호 형식이 아닙니다.");
        return false;
    } else if (!/^[\w\.-]+@[a-zA-Z\d\.-]+\.[a-zA-Z]{2,}$/.test(f.email.value)) {
        alert("이메일 양식이 올바르지 않습니다");
        return false;
    } else if (!/^\d{3}-\d{4}-\d{4}$/.test(f.ePhone.value)){
    	alert("전화번호 양식에 맞지 않습니다");
    	return false;
    }
	fetch('/checkEmailExists?email=' + encodeURIComponent(f.email.value))
    .then(response => response.json())
    .then(data => {
        if (data.exists) {
            alert("이미 등록된 이메일입니다.");
        } else {
            // 서버에 이메일이 존재하지 않는 경우 폼을 서버에 제출합니다.
        	
    console.log(f);
	f.action = '/employee_insert';
	f.submit();

	return true;
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert("서버에서 에러가 발생했습니다. 다시 시도해주세요.");
    });
}
document.getElementById('searchIcon').addEventListener('click', function() {
    // sample6_execDaumPostcode() 함수 실행
    sample6_execDaumPostcode();
	
});


