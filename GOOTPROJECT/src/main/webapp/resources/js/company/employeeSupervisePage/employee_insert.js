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

//은행 선택 시 계좌번호 형식 자동 추가
document.getElementById("bank-select").addEventListener("change", function() {
    var bankSelect = document.getElementById("bank-select");
    var accountInput = document.getElementsByName("salAccount")[0];
    var bankName = bankSelect.options[bankSelect.selectedIndex].value;

    switch (bankName) {
        case "하나은행":
            accountInput.placeholder = "XXX-XXXXXX-XXXXX";
            accountInput.maxLength = 16; // 하이픈 포함하여 최대 16글자
            break;
        case "우리은행":
            accountInput.placeholder = "XXXX-XXX-XXXXXX";
            accountInput.maxLength = 15; // 하이픈 포함하여 최대 15글자
            break;
        case "국민은행":
            accountInput.placeholder = "XXXXXX-XX-XXXXXX";
            accountInput.maxLength = 16; // 하이픈 포함하여 최대 16글자
            break;
        case "기업은행":
            accountInput.placeholder = "XXX-XXXXXX-XX-XXX";
            accountInput.maxLength = 17; // 하이픈 포함하여 최대 17글자
            break;
        case "농협은행":
            accountInput.placeholder = "XXX-XXXX-XXXX-XX";
            accountInput.maxLength = 16; // 하이픈 포함하여 최대 16글자
            break;
        case "카카오 뱅크":
            accountInput.placeholder = "XXXX-XX-XXXXXXX";
            accountInput.maxLength = 15; // 하이픈 포함하여 최대 15글자
            break;
        default:
            accountInput.placeholder = "";
        	accountInput.maxLength = 16; // 하이픈 포함하여 최대 16글자
            break;
    }
});

//계좌번호 입력 필드
var accountInput = document.getElementsByName("salAccount")[0];

// 계좌번호 입력 시 자동으로 하이픈 추가
accountInput.addEventListener("input", function() {
    var trimmedValue = this.value.replace(/-/g, ''); // 하이픈 제거
    var formattedValue = ''; // 포맷팅된 결과를 저장할 변수

    // 선택된 은행에 따라 계좌번호 양식 결정
    var selectedBank = document.getElementById("bank-select").value;

    switch(selectedBank) {
        case "하나은행":
            // 하나은행 계좌번호 양식에 맞게 하이픈 추가
            formattedValue = trimmedValue.replace(/(\d{3})(\d{7})(\d{5})/, '$1-$2-$3');
            break;
        case "우리은행":
            // 우리은행 계좌번호 양식에 맞게 하이픈 추가
            formattedValue = trimmedValue.replace(/(\d{4})(\d{3})(\d{6})/, '$1-$2-$3');
            break;
        case "국민은행":
            // 국민은행 계좌번호 양식에 맞게 하이픈 추가
            formattedValue = trimmedValue.replace(/(\d{6})(\d{2})(\d{6})/, '$1-$2-$3');
            break;
        case "기업은행":
            // 기업은행 계좌번호 양식에 맞게 하이픈 추가
            formattedValue = trimmedValue.replace(/(\d{3})(\d{6})(\d{2})(\d{3})/, '$1-$2-$3-$4');
            break;
        case "농협은행":
            // 농협은행 계좌번호 양식에 맞게 하이픈 추가
            formattedValue = trimmedValue.replace(/(\d{3})(\d{4})(\d{4})(\d{2})/, '$1-$2-$3-$4');
            break;
        case "카카오 뱅크":
            // 카카오 뱅크 계좌번호 양식에 맞게 하이픈 추가
            formattedValue = trimmedValue.replace(/(\d{4})(\d{2})(\d{7})/, '$1-$2-$3');
            break;
        default:
            // 기본적으로는 하이픈을 추가하지 않음
            formattedValue = trimmedValue;
    }

    // 최종적으로 입력 필드에 반영
    this.value = formattedValue;
});

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
   	
    console.log(f);
	f.action = '/employee_insert';
	f.submit();
}

document.getElementById('searchIcon').addEventListener('click', function() {
    // sample6_execDaumPostcode() 함수 실행
    sample6_execDaumPostcode();
	
});


