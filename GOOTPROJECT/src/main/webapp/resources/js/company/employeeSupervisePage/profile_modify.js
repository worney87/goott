const f = document.forms[0];
let selectedFile; 

// 주소 API 실행 
document.getElementById('searchIcon').addEventListener('click', function() {

    // sample6_execDaumPostcode() 함수 실행
    sample6_execDaumPostcode();
	
});


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


// 프로필 사진 편집
document.getElementById('img-change').addEventListener('click', function() {
	  const imgUpload = document.getElementById('img-upload');
	  
	    if (imgUpload) {
	        // 파일 탐색기 열기
	        imgUpload.click();
	    } else {
	        console.error("img-upload element not found.");
	    }
	});

document.getElementById('img-upload').addEventListener('change', function() {
	selectedFile = this.files[0]; // 선택한 파일 객체 가져오기
    const reader = new FileReader(); // 파일을 읽기 위한 객체 생성
   
    reader.onload = function(event) {
    const imageURL = event.target.result; // 이미지 데이터 URL 가져오기
    	
        // 이미지 요소 생성
        const img = document.createElement('img');
        img.src = imageURL;
        img.width = 302;
        img.height = 152;
        
        // 이미지 표시 영역에 새로운 이미지 추가
        const profileImgDiv = document.getElementById('profile-img');
        profileImgDiv.innerHTML = ''; // 기존 내용 지우기
        profileImgDiv.appendChild(img);
    };
    // 파일을 읽어 이미지 URL로 변환
    reader.readAsDataURL(selectedFile);
    
    this.value = '';
});

// 프로필 사진만 저장
document.getElementById('img-save').addEventListener('click', function() {
	
	const formData = new FormData();
	 formData.append('eno', f.eno.value);
	 if (profilePictureSrc) {
	        formData.append('profilePicturePath', profilePictureSrc);
	    } else {
	        formData.append('profilePicturePath', ''); 
	    }
	    // 파일 탐색기로 선택한 이미지 저장할 때
	    if (selectedFile) {
	        formData.append('profilePicture', selectedFile);
	    }else {
	    	formData.append('profilePicture', '');
		}
	    
	    
	    fetch("/profilePictureUpdate", {
	        method: 'POST',
	        body: formData,
	    })
		.then((response) => {
			
			if(response.ok){ 
				// 요청이 성공하면 처리
				alert('프로필 사진이 수정이 완료되었습니다.'); 
				location.reload();
			}else {
	        	// 요청이 실패하면 에러 처리
	        	console.error('프로필 사진 수정에 실패했습니다.');
	            // 에러 메시지 출력
	          response.json().then((error) => {
	          console.error('에러 메시지:', error.message);
	          })
			}
		})	
		.then((data) => {
			
		})	
		.catch( err => {
			// 에러 처리
			console.log('Fetch error:', err);
			// 에러 메시지 출력
			console.log('Error message:', err.message);
		});
	  });	

document.querySelectorAll('input[type="button"]').forEach( btn => {
	btn.addEventListener( 'click', (event) => {
		event.preventDefault(); 
		
		let type = btn.id;
		
		if( type === 'closeBtn')close();
		else if( type === 'saveBtn'){
			save();
		}
	})
})

// 뒤로 가기 버튼
function close(){
	 window.history.back();
}

// 프로필 수정한 거 저장
function save(){
		
	if( f.ePw.value !== f.checkEPw.value ){
		alert("비밀번호가 일치 하지 않습니다");
		f.checkEPw.focus();
		return; 
	}

	console.log(profilePictureSrc);
	const formData = new FormData();
    formData.append('eno', f.eno.value);
    formData.append('deptNo', f.deptNo.value);
    formData.append('eName', f.eName.value);
    formData.append('ePhone', f.ePhone.value);
    formData.append('ePw', f.ePw.value);
    formData.append('eAddr', f.eAddr.value);
    formData.append('eAddr2', f.eAddr2.value);
    formData.append('salAccount', f.salAccount.value);
    formData.append('eBank', f.eBank.value);
    // vo에 저장된 이미지URL로 저장할 때
    if (profilePictureSrc) {
        formData.append('profilePicturePath', profilePictureSrc);
    } else {
        formData.append('profilePicturePath', ''); 
    }
    // 파일 탐색기로 선택한 이미지 저장할 때
    if (selectedFile) {
        formData.append('profilePicture', selectedFile);
    }else {
    	formData.append('profilePicture', '');
	}
    console.log(formData.get("profilePicturePath"));
    console.log(formData.get("profilePicture"));

    fetch("/profile_modify", {
        method: 'POST',
        body: formData,
    })
	.then((response) => {
		
		if(response.ok){ 
			// 요청이 성공하면 처리
			alert('프로필 수정이 완료되었습니다.');
			f.checkEPw.value = '';  
			location.reload();
		}else {
        	// 요청이 실패하면 에러 처리
        	console.error('프로필 수정에 실패했습니다.');
            // 에러 메시지 출력
          response.json().then((error) => {
          console.error('에러 메시지:', error.message);
          })
		}
	})	
	.then((data) => {
		
	})	
	.catch( err => {
		// 에러 처리
		console.log('Fetch error:', err);
		// 에러 메시지 출력
		console.log('Error message:', err.message);
	});
  };	