/** 공통 부분 시작*/

//데이트피커 
let datePickAll = document.querySelectorAll('input[type="date"]');
datePickAll.forEach(function(input) {
	flatpickr(input, {
		locale: 'ko'
	});
});


//form 객체 가져오기
const f = document.forms[0];

//css 파일 동적으로 바인딩
const CSS_FILE_PATH = ['/resources/css/company/custMgmtPage/spotMgmt.css','/resources/css/company/custMgmtPage/spotMgmtModal.css' ];
cssBinding(CSS_FILE_PATH);
function cssBinding(cssFiles) {
	cssFiles.forEach(css => {
		
		//2. link 태그 생성
		let linkEle = document.createElement('link');
		linkEle.rel = 'stylesheet';
		linkEle.type = 'text/css';
		linkEle.href = css;
		
		
		//3.  head 태그에 link 엘리먼트 추가 (여기서 head 태그에 동적으로 바인딩 된다)
		document.head.appendChild(linkEle);
	})
}


//이전페이지 가기 다른페이지에 물려있어서 페이지 이동보단 뒤로가기가 나은듯합니다.
function backPage() 
{
	window.location = document.referrer;
} 
/** 공통 부분 끝*/




/** 도로명 주소 팝업창 (API)   */
function openAddressPopup() {
	
	new daum.Postcode({
		oncomplete: function(data) {
			console.log(data);
		    const spAddrInput = document.querySelector('input[name="spAddr"]');


			if(data.userSelectedType==='R') { //R은 도로명 주소 클릭 시 
				spAddrInput.value = data.roadAddress;
				
			}else { //지번 주소 클릭 시
				spAddrInput.value =data.jibunAddress;
			}
			
		}
	}).open();
	

}



//// 정규 표현식과 최대 파일 크기 설정
//const regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
//const MAX_SIZE = 5242880; // 5MB
//
//// 파일 확장자 및 크기를 체크하는 함수
//function checkExtension(fileName, fileSize) {
//  if (fileSize >= MAX_SIZE) {
//      alert("파일 사이즈 초과");
//      return false;
//  }
//
//  if (regex.test(fileName)) {
//      alert("해당 종류의 파일은 업로드 할 수 없습니다.");
//      return false;
//  }
//  return true;
//}



// 기존 파일 목록을 저장하는 배열
let existingFiles = [];

// 파일을 업로드할 때 사용할 함수
function uploadFile(file) {
const formData = new FormData();
formData.append("file", file);

fetch('/uploadAsyncAction', {
  method: 'POST',
  body: formData
})
.then(response => {
  if (!response.ok) {
      throw new Error('Network response was not ok');
  }
  return response.json(); // 서버로부터 JSON 데이터를 받아옵니다.
})
.then(data => {
console.log('Received data:', data); // 받은 데이터를 콘솔에 출력합니다.

// 받은 데이터를 화면에 출력하거나 다른 작업을 수행합니다.
if (Array.isArray(data)) {
data.forEach(fileInfo => {
  console.log('UUID:', fileInfo.uuid); // 파일의 UUID를 출력합니다.
  console.log('Upload Path:', fileInfo.uploadPath); // 파일의 업로드 경로를 출력합니다.
});
} else {
console.log('UUID:', data.uuid); // 파일의 UUID를 출력합니다.
console.log('Upload Path:', data.uploadPath); // 파일의 업로드 경로를 출력합니다.
}
})
}

// 파일 입력 요소의 변경 이벤트 리스너 등록
document.getElementById('fileInput').addEventListener('change', event => {
const files = event.target.files;
if (files.length > 0) {
  const file = files[0]; // 여기서는 하나의 파일만 업로드하는 것으로 가정합니다.
  uploadFile(file);
}
});


// 파일 입력 요소의 변경 이벤트 리스너 등록
document.getElementById("fileInput").addEventListener('change', () => {
const files = document.getElementById('fileInput').files;
const newFiles = Array.from(files); // 새로운 파일 목록
console.log("New Files:", newFiles); // 새로운 파일 목록 콘솔 출력

// 새로운 파일 목록을 기존 파일 목록 아래에 추가하고 화면에 표시
existingFiles = existingFiles.concat(newFiles);
showUploadedFiles(existingFiles);
});

// 파일 삭제 함수
function deleteFile(event) {
const fileName = event.target.getAttribute('data-file-name');
console.log("파일 삭제:", fileName);

// 삭제할 파일을 화면에서 제거
const listItem = event.target.closest('li');
listItem.remove();

// 배열에서도 해당 파일 제거
existingFiles = existingFiles.filter(file => file.name !== fileName);
console.log("existingFiles:", existingFiles); // existingFiles 배열을 콘솔에 출력
}

// 업로드된 파일 목록을 출력하는 함수
function showUploadedFiles(files) {
console.log(files); // 배열에 들어있는 파일들을 콘솔에 출력
console.log("Uploaded Files:");
for (let i = 0; i < files.length; i++) {
  const file = files[i];
  
  }

const uploadResult = document.querySelector(".uploadResult ul");

// 기존 목록 초기화 후 새로운 파일 목록을 출력
uploadResult.innerHTML = '';

if (!files || files.length === 0) {
  return;
}

for (let i = 0; i < files.length; i++) {
  const file = files[i];
  const listItem = document.createElement('li');

  // 파일 객체에 다운로드 링크 추가
  const downloadLink = document.createElement('a');
  downloadLink.href = URL.createObjectURL(file);
  downloadLink.download = file.name;
  downloadLink.textContent = file.name;
  listItem.appendChild(downloadLink);

  // 파일 삭제 이미지 추가 및 이벤트 리스너 등록
  const deleteImage = document.createElement('img');
  deleteImage.src = '/resources/images/mt-cancel.svg'; // 삭제 이미지 URL 설정
  deleteImage.style.width = '15px'; // 삭제 이미지 크기 조정
  deleteImage.style.height = '15px'; // 추가: 이미지 높이도 조정
  deleteImage.style.cursor = 'pointer'; // 마우스 커서를 포인터로 변경
  deleteImage.setAttribute('data-file-name', file.name); // 삭제할 파일 이름을 데이터 속성으로 추가
  deleteImage.addEventListener('click', deleteFile); // 삭제 버튼 클릭 이벤트 리스너 등록

  listItem.appendChild(deleteImage);
  uploadResult.appendChild(listItem);
}
}


// 파일 삭제 함수
function deleteFile(event) {
const fileName = event.target.getAttribute('data-file-name');
console.log("파일 삭제:", fileName);

fetch('/deleteFile', {
  method: 'post',
  body: fileName,
  headers: {'Content-type': 'text/plain'}
})
.then(response => response.text())
.then(text => {
  console.log(text);
  // 파일 삭제에 성공하면 해당 파일을 existingFiles 배열에서도 제거
  existingFiles = existingFiles.filter(file => file.name !== fileName);
  console.log("existingFiles:", existingFiles); // existingFiles 배열을 콘솔에 출력
  showUploadedFiles(existingFiles); // 변경된 파일 목록을 다시 출력
})
.catch(err => console.log(err));
}


/** 지점 등록 */
document.getElementById("spotRegisterInsertBtn").addEventListener('click', () => {
    
    // JavaScript를 사용하여 폼 요소에 접근
    const companyNoInput = document.querySelector('input[name="companyNo"]');

    // 히든 입력란의 값을 가져와서 콘솔에 출력
    console.log('Company Number:', companyNoInput.value);
    console.log(f.comName.value);
    console.log(f.companyNo.value );
    console.log(f.spAddr.value );
    console.log(f.spDetailAddr.value );
    console.log(f.spContact.value );
    console.log(f.spAgreementFile.value ); // 파일명 출력
    console.log(f.file.value );
    console.log(f.spAgreementDate.value );
    console.log(f.spAgreementTerm.value);
    console.log(f.spAutoExtension.value );
    console.log(f.spBdgt.value );
    console.log(f.spEmpNum.value);
    console.log(f.spPayMethod.value );
    console.log(f.userName.value );
    console.log(f.userContact.value );
    console.log(f.userEmail.value);
    
    f.action='/spotRegisterInsert';
    f.submit();
});
