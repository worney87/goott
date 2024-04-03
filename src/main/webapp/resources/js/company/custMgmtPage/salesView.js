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


const CSS_FILE_PATH = ['/resources/css/company/custMgmtPage/salesMgmt.css','/resources/css/company/custMgmtPage/salesMgmtModal.css' ];
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


/** 공통 부분 끝*/


/**영업 상태 값이 '최초 인입' 에서 다른 값으로 변화된 해당 날짜를 응대일에 자동으로 입력 */
const csResponseDate = f.querySelector('input[name="csResponseDate"]');
const csStatus = f.querySelector('select[name="csStatus"]');

csStatus.addEventListener('change', function() {

	const selectedValue = csStatus.value;
	// 현재 날짜 가져오기
	const currentDate = new Date();
	
	// 날짜를 yyyy-MM-dd 형식으로 변환
	const formattedDate = currentDate.toISOString().split('T')[0];
	
	// 최초 인입이 아닌 경우에만 응대일 값 변경
	if (selectedValue !== '최초 인입') {
		csResponseDate.value = formattedDate;
	}
});

/**영업 상태 값이 '계약 실패' 일 때만 계약 실패 사유, 계약 실패 상세사유란 보이게 하기*/
document.addEventListener("DOMContentLoaded", function () {

    const csStatus = document.getElementById("selectSalesStatus");
    const failReasonDiv = document.getElementById("failReasonDiv");

    // 초기 상태 설정
    if (csStatus.value === "계약 실패") {
        failReasonDiv.style.display = "table"; 
    } else {
        failReasonDiv.style.display = "none"; 
    }

    csStatus.addEventListener("change", function () {
        if (csStatus.value === "계약 실패") {
            failReasonDiv.style.display = "table"; 
        } else {
            failReasonDiv.style.display = "none"; 
        }
    });
});


/** 영업 히스토리에서 + 버튼 누르면 영업 히스토리 날짜 와 영업 히스토리 내용 작성란 새로 추가 (최대 5개 까지) */
let currentRowNumber = 0; // 초기값을 0으로 설정하거나 필요에 따라 다른 값으로 설정합니다.


document.getElementById('imgBtnPlus').addEventListener('click', function() {
    if (currentRowNumber < 4) { // 최대 5개까지 생성 가능
        currentRowNumber++;
        updateSalesHistoryVisibility();
    } else {
        alert('최대 5개의 영업 히스토리 항목까지만 추가할 수 있습니다.');
    }
});

function updateSalesHistoryVisibility() {
    let salesHistoryEntries = document.getElementsByClassName('salesHistoryEntry');
    for (let i = 0; i < salesHistoryEntries.length; i++) {
        if (i < currentRowNumber) {
            salesHistoryEntries[i].style.display = 'block';
        } else {
            salesHistoryEntries[i].style.display = 'none';
        }
    }
}



/** 영업 담당자에 세션에 저장된 이름 할당하기 */
    const sessionId = sessionStorage.getItem('eName');
    
    const csEnameInput = document.querySelector('input[name="csEname"]');
    const selectSalesStatus = document.getElementById('selectSalesStatus');

    // 영업 상태가 '최초 인입'인 경우에만 세션 ID 할당
    if (selectSalesStatus.value === '최초 인입') {
        csEnameInput.value = sessionId;
    } 


 // 저장 버튼 클릭 시
    document.getElementById('saveBtn').addEventListener('click', function(event) {
        event.preventDefault(); // 폼 제출의 기본 동작인 페이지 새로 고침 방지
        
        // 필요한 데이터 수집
        var csStatus = document.getElementById('selectSalesStatus').value;
        var csResponseDate = document.getElementsByName('csResponseDate')[0].value;
        var cshDate1 = document.getElementsByName('cshDate1')[0].value;
        var cshContent1 = document.getElementsByName('cshContent1')[0].value;

        // 새로 추가된 입력란 데이터 수집
        var cshDate2 = document.getElementsByName('cshDate2')[0].value;
        var cshContent2 = document.getElementsByName('cshContent2')[0].value;
        var cshDate3 = document.getElementsByName('cshDate3')[0].value;
        var cshContent3 = document.getElementsByName('cshContent3')[0].value;
        var cshDate4 = document.getElementsByName('cshDate4')[0].value;
        var cshContent4 = document.getElementsByName('cshContent4')[0].value;
        var cshDate5 = document.getElementsByName('cshDate5')[0].value;
        var cshContent5 = document.getElementsByName('cshContent5')[0].value;
        
        var csFailReason = document.getElementById('selectCsFailReason').value;
        var csFailDetailReason = document.getElementById('csFailDetailReason').value;
        var consultHistoryNo = document.getElementsByName('consultHistoryNo')[0].value;
        var consultNo = document.getElementsByName('consultNo')[0].value;
        var csEname = document.getElementsByName('csEname')[0].value;

     // Ajax 요청 전송
        var xhr = new XMLHttpRequest();
        xhr.open('POST', '/saveOrUpdateSales', true); // 하나의 컨트롤러 메서드로 통합
        xhr.setRequestHeader('Content-Type', 'application/json'); // JSON 형식으로 데이터 전송
        xhr.onload = function() {
            if (xhr.status === 200) {
                alert('저장되었습니다.');
                location.reload(); // 저장 후 페이지 새로고침
            } else {
                alert('저장에 실패했습니다.');
            }
        };

        // 데이터를 JSON 형식으로 변환하여 전송
        var data = JSON.stringify({
            consultNo: consultNo,
            consultHistoryNo: consultHistoryNo,
            csStatus: csStatus,
            csEname: csEname,
            csResponseDate: csResponseDate,
            cshDate1: cshDate1,
            cshContent1: cshContent1,
            cshDate2: cshDate2, // 새로 추가된 입력란
            cshContent2: cshContent2, // 새로 추가된 입력란
            cshDate3: cshDate3, // 새로 추가된 입력란
            cshContent3: cshContent3, // 새로 추가된 입력란
            cshDate4: cshDate4, // 새로 추가된 입력란
            cshContent4: cshContent4, // 새로 추가된 입력란
            cshDate5: cshDate5, // 새로 추가된 입력란
            cshContent5: cshContent5, // 새로 추가된 입력란
            csFailReason: csFailReason,
            csFailDetailReason: csFailDetailReason
        });
        xhr.send(data);
    });
    
    
 // 새로 추가된 입력란 데이터 수집
    var cshDate2 = document.getElementsByName('cshDate2')[0].value;
    var cshContent2 = document.getElementsByName('cshContent2')[0].value;
    var cshDate3 = document.getElementsByName('cshDate3')[0].value;
    var cshContent3 = document.getElementsByName('cshContent3')[0].value;
    var cshDate4 = document.getElementsByName('cshDate4')[0].value;
    var cshContent4 = document.getElementsByName('cshContent4')[0].value;
    var cshDate5 = document.getElementsByName('cshDate5')[0].value;
    var cshContent5 = document.getElementsByName('cshContent5')[0].value;

    // 영업 히스토리 입력란들과 각 입력란에 해당하는 테이블 로우를 가져옵니다.
    var salesHistoryEntries = document.querySelectorAll('.salesHistoryEntry');
    var salesHistoryRows = document.querySelectorAll('.salesHistoryEntry > table');

    // 각 입력란의 값이 존재하는지 확인하고, 값이 있으면 해당 로우를 보이도록 합니다.
    if (cshDate2 || cshContent2) {
        salesHistoryEntries[0].style.display = 'block';
    }
    if (cshDate3 || cshContent3) {
        salesHistoryEntries[1].style.display = 'block';
    }
    if (cshDate4 || cshContent4) {
        salesHistoryEntries[2].style.display = 'block';
    }
    if (cshDate5 || cshContent5) {
        salesHistoryEntries[3].style.display = 'block';
    }

    // 새로 추가된 입력란이 모두 보이면 + 버튼을 숨깁니다.
    if (salesHistoryEntries.length === currentRowNumber + 1) {
        document.getElementById('imgBtnPlus').style.display = 'none';
    }



