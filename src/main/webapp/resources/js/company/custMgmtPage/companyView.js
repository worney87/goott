/** 공통 부분 시작*/

//데이트피커 
let datePickAll = document.querySelectorAll('input[type="date"]');
datePickAll.forEach(function(input) {
	flatpickr(input, {
		locale: 'ko'
	});
});

//css 파일 동적 바인딩
const CSS_FILE_PATH = ['/resources/css/company/custMgmtPage/companyMgmt.css','/resources/css/company/custMgmtPage/companyMgmtModal.css' ];
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

//form 객체 가져오기 
const f = document.forms[0];




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
		    const comAddrInput = document.querySelector('input[name="comAddr"]');
		    const comAreaInput = document.querySelector('input[name="comArea"]');
		    //console.log(comAddrInput);


			if(data.userSelectedType==='R') { //R은 도로명 주소 클릭 시 
				comAddrInput.value = data.roadAddress;
				
			}else { //지번 주소 클릭 시
				comAddrInput.value =data.jibunAddress;
			}
			
			comAreaInput.value = data.sido;
		}
	}).open();
	

}

//기존 파일 목록을 저장하는 배열
let existingFiles = [];

// 기존 파일을 불러오는 함수
function loadExistingFiles() {
    // 서버에서 기존 파일 목록을 가져오는 fetch 또는 Ajax 요청을 수행합니다.
    fetch('/getExistingFiles')
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to fetch existing files');
            }
            return response.json();
        })
        .then(data => {
            // 받아온 기존 파일 목록을 화면에 출력합니다.
            const uploadResult = document.querySelector(".uploadResult ul");
            const existingFilesList = uploadResult.querySelector(".existing-files");

            // 기존 파일 목록이 있는 경우에만 출력합니다.
            if (existingFilesList && data && data.length > 0) {
                data.forEach(fileInfo => {
                    const listItem = document.createElement('li');
                    const downloadLink = document.createElement('a');
                    downloadLink.href = `#`; // 파일 다운로드 링크 설정
                    downloadLink.textContent = fileInfo.fileName; // 파일 이름 설정
                    listItem.appendChild(downloadLink);
                    existingFilesList.appendChild(listItem);
                });
            }
        })
        .catch(error => {
            console.error('Error fetching existing files:', error);
        });
}

//파일 입력 요소의 변경 이벤트 리스너 등록
document.getElementById("fileInput").addEventListener('change', () => {
    const files = document.getElementById('fileInput').files;
    const newFiles = Array.from(files); // 새로운 파일 목록

    // 새로운 파일 목록을 기존 파일 목록 아래에 추가하고 화면에 표시
    showUploadedFiles(newFiles);
});

//파일 삭제 함수
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

//업로드된 파일 목록을 출력하는 함수
function showUploadedFiles(files) {
    console.log(files); // 배열에 들어있는 파일들을 콘솔에 출력
    console.log("Uploaded Files:");
    const uploadResult = document.querySelector(".uploadResult ul");
    
    // 기존 목록 초기화 후 새로운 파일 목록을 출력
    const newList = document.createElement('ul');
    newList.classList.add('new-files');

    if (!files || files.length === 0) {
        return;
    }

    // 새로운 파일 목록 출력
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
        newList.appendChild(listItem);
    }

    // 새로운 파일 목록을 기존 파일 목록 아래에 추가
    const existingList = uploadResult.querySelector(".existing-files");
    if (existingList) {
        uploadResult.removeChild(existingList);
    }
    uploadResult.appendChild(newList);
}




/** 사업자등록번호 유효 API */
document.getElementById("imgBtnSearchBizNum").addEventListener('click', () => {

	    	 $("#comBizNum").val($("#comBizNum").val().replace(/[^0-9]/g, ""));
	    	 comBizNum = $("#comBizNum").val();

	    	   
	    	    var data = {
	    	        "b_no": [comBizNum]
	    	    };
	    	    
	    	    $.ajax({
	    	        url: "https://api.odcloud.kr/api/nts-businessman/v1/status?serviceKey=RcQTidl2gdgS%2FjYwj9Nwfy1D7bKtcwekloVeGJ%2BU4NxpCVbVTrc%2FdueJNvAJoSgWSe9fvIGz%2FJzX4Y%2FaOFkkHA%3D%3D",  // serviceKey 값을 xxxxxx에 입력
	    	        type: "POST",
	    	        data: JSON.stringify(data), // json 을 string으로 변환하여 전송
	    	        dataType: "JSON",
	    	        traditional: true,
	    	        contentType: "application/json; charset:UTF-8",
	    	        accept: "application/json",
	    	        success: function(result) {
	    	            console.log(result);
	    	            if(result.match_cnt == "1") {
	    	            	// 성공
	    	            	// 성공
	    	                $("#bizNumValidationResult").text("국세청에 등록된 사업자등록번호입니다.");
	    	                $("#bizNumValidationResult").removeClass("error");
	    	            } else {
	    	            	  $("#bizNumValidationResult").text("국세청에 등록되지 않은 사업자등록번호입니다. 다시 확인바랍니다.");
	    	                  $("#bizNumValidationResult").addClass("error");
	    	                //alert(result.data[0]["tax_type"]);
	    	            }
	    	        },
	    	        error: function(result) {
	    	            console.log("error");
	    	            console.log(result.responseText); //responseText의 에러메세지 확인
	    	        }
	    	    });

	});





//기업 정보 update
function updateCompanyView(){
	let result = confirm("기업 정보를 변경하시겠습니까?");
	
	const a = f.comOpenningDate.value;
	//console.log(a);
	
	if(result){
		
		
		f.action='/updateCompany';
		f.submit();
		
		
	}
	
	
	
	
}
