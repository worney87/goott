// 각 모달 창과 관련된 요소들을 가져옵니다.
var managerInpoModal = document.getElementById('managerInpo_modal');
var openManagerInpoModalBtns = document.querySelectorAll('#open_managerInpo_modal');
var closeManagerInpoModalBtn = document.getElementById('close_managerInpo_modal');

// 지점 담당자 정보 수정 모달창 열기
openManagerInpoModalBtns.forEach(function(button) {
    button.onclick = function() {
        managerInpoModal.style.display = 'block';
    };
});

// 지점 담당자 정보 수정 모달창 닫기
closeManagerInpoModalBtn.onclick = function() {
    managerInpoModal.style.display = 'none';
};

// 지점 담당자 정보 수정 모달창 닫기 (딴 곳 누를 시)
window.onclick = function(event) {
    if (event.target === managerInpoModal) {
        managerInpoModal.style.display = 'none';
    }
};

//각각의 담당자 정보 수정 버튼에 대한 이벤트 핸들러 설정
document.querySelectorAll("#open_managerInpo_modal").forEach(function(button) {
    button.addEventListener('click', function() {
        let spotNo = this.getAttribute("data-spot-no");
        fetch('/getManagerInfo', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ spotNo: spotNo })
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('서버 응답이 성공적이지 않습니다.');
            }
            return response.json();
        })
        .then(json => {
            console.log(json);
            let msg = '';
            if (json) {
                msg = `
                    <tr>
                        <td style="font-weight:bold;">이름</td>
                        <td><input type="text" name="userName" value="${json.userName}" style="border: 1px solid black;">
                        		<input type="hidden" name="spotNo" value="${json.spotNo}">
                        </td>
                    </tr>
                    <tr>
                        <td style="font-weight:bold;">연락처</td>
                        <td><input type="text" name="userContact" value="${json.userContact}" style="border: 1px solid black;"></td>
                    </tr>
                    <tr>
                        <td style="font-weight:bold;">이메일</td>
                        <td><input type="text" name="userEmail" value="${json.userEmail}" style="border: 1px solid black;"></td>
                    </tr>
                    <tr>
                        <td style="font-weight:bold;">비밀번호</td>
                        <td><input type="text" name="userPw" value="${json.userPw}" style="border: 1px solid black;"></td>
                    </tr>
                `;
            }
            const tableBody = document.querySelector('#managerInpo_tbl tbody');
            tableBody.innerHTML = msg;
        })
        .catch(error => console.error('Error:', error.message));
    });
});

// 담당자 정보 수정
document.getElementById("update_managerInpo_modal").addEventListener('click', function() {
    const vo = {
    	userName: document.querySelector('input[name="userName"]').value,
    	userContact: document.querySelector('input[name="userContact"]').value,
    	userEmail: document.querySelector('input[name="userEmail"]').value,
    	userPw: document.querySelector('input[name="userPw"]').value,
    	spotNo: document.querySelector('input[name="spotNo"]').value
    };
    
    console.log(vo);

    // fetch 요청 보내기
    fetch('/updateManagerInfo', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(vo)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('서버 응답이 성공적이지 않습니다.');
        }
        return response.json();
    })
    .then(responseData => {
        console.log(responseData);
        alert("지점 담당자 정보가 수정되었습니다.");
        managerInpoModal.style.display = 'none';
        window.location.reload(); // 페이지 새로고침
    })
    .catch(error => console.error('Error:', error.message));
});




/** --------------------직원 리스트 모달창 시작------ */

var empListModal = document.getElementById('empList_modal');
var openEmpListModalBtn = document.getElementById('open_empList_modal');
var closeEmpListModalBtn = document.getElementById('close_empList_modal');

/**직원 리스트 모달창 열기 */ 
openEmpListModalBtn.onclick = function() {
	  empListModal.style.display = 'block';
	}

/**직원 리스트 모달창 닫기 */ 
closeEmpListModalBtn.onclick = function() {
	empListModal.style.display = 'none';
}

/**직원 리스트 모달창 닫기 (딴 곳 누를 시)*/ 
window.onclick = function(event) {
  if (event.target === empListModal) {
	  empListModal.style.display = 'none';
  }
}




//각각의 직원 리스트 보기 버튼에 대한 이벤트 핸들러 설정
document.querySelectorAll("#open_empList_modal").forEach(function(button) {
    button.addEventListener('click', function() {
        let spotNo = this.getAttribute("data-spot-no");
        
        fetch('/getEmpList', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ spotNo: spotNo })
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('서버 응답이 성공적이지 않습니다.');
            }
            return response.json();
        })
        .then(empList => {
            console.log(empList);
            
            let msg = '';
            empList.forEach(emp => {
                msg += `
                   <tr class="list">
                        <td><a href="#" id="open_empInfo_modal">${emp.cEmpNo}</a></td>
                        <td>${emp.cEmpName}</td>
                        <td>${emp.cEmpTel}</td>
                        <td>${emp.cEmpEmail}</td>
                        <td>${emp.cEmpPosition}</td>
                        <td>${emp.spBdgt}</td>
                        <td>${myTime(emp.cEmpBirth)}</td>
                        <td>${myTime(emp.orderDate)}</td>
                        <td>${emp.prdName}</td>
                    </tr>
                `;
            });
            const tableBody = document.querySelector('#empList_tbl tbody');
            tableBody.innerHTML = msg;
        })
        .catch(error => console.error('Error:', error.message));
    });
});































/** --------------------직원 리스트 모달창 끝------ */




/** --------------------지점-직원 정보 수정 모달창 시작------ */

var empInpoModal = document.getElementById('empInpo_modal');
var openEmpInpoModalBtn = document.getElementById('open_empInpo_modal');
var closeEmpInpoModalBtn = document.getElementById('close_empInpo_modal');

/**지점-직원 정보 수정 모달창 열기 */ 
openEmpInpoModalBtn.onclick = function() {
	empInpoModal.style.display = 'block';
}

/**지점-직원 정보 수정 모달창 닫기 */ 
closeEmpInpoModalBtn.onclick = function() {
	empInpoModal.style.display = 'none';
}

/**지점-직원 정보 수정 모달창 닫기 (딴 곳 누를 시)*/ 
window.onclick = function(event) {
  if (event.target === empInpoModal) {
	  empInpoModal.style.display = 'none';
  }
}
/** --------------------지점-직원 정보 수정 모달창 끝------ */




function myTime(unixTimeStamp) {
    moment.locale('ko'); // 한국어 설정
	// 오늘 날짜 가져오기
	const mytime = moment(unixTimeStamp).format('YYYY-MM-DD');

    return mytime;
}