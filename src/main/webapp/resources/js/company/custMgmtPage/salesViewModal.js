/** --------------------영업부 사원 검색 모달창 시작------ */

var searchCsEnameModal = document.getElementById('searchCsEname_modal');
var openSearchCsEnameModalBtn = document.getElementById('open_searchEname_modal');
var closeSearchCsEnameModalBtn = document.getElementById('close_searchEname_modal');

//영업부 사원 검색 모달창 열기 
openSearchCsEnameModalBtn.onclick = function() {
	searchCsEnameModal.style.display = 'block';
	}

//영업부 사원 검색 모달창 닫기 
closeSearchCsEnameModalBtn.onclick = function() {
	searchCsEnameModal.style.display = 'none';
}

//영업부 사원 검색 모달창 닫기 (딴 곳 누를 시)
window.onclick = function(event) {
  if (event.target === searchCsEnameModal) {
	  searchCsEnameModal.style.display = 'none';
  }
}

//영업부 사원 검색 및 영업부 사원  변경(모달창)
document.getElementById("imgBtnSearchEname").addEventListener('click', function() {
	//영업부 사원 검색 찾기 검색란 값 비우기
	document.querySelector('input[name="keyword"]').value = ''
    // 모달 창 열기
    let modal = document.getElementById('open_searchEname_modal');
    modal.style.display = "block";
    
    // 영업부 사원 리스트 가져오기
    fetch('/getCsEnameListModal')
    .then(response => response.json())
    .then(json => {
      //  console.log("계약 완료된 기업 리스트 불러오기: ", json); 
        
        let msg = '';
        if (json.length > 0) {
            json.forEach(item => {
       
                msg += `
                    <tr class="list">
                    	 <td>${item.eno}</a></td>
                        <td><a href="#" class="salesDepartment" data-name="${item.eName}">${item.eName}</a></td>
                         <td>${item.job}</a></td>
                    </tr>
                `;
            });
        } else {
            msg = `
                <tr>
                    <td colspan="3">영업부 사원이 없습니다.</td>
                </tr>
            `;
        }
        
        const tableBody = document.querySelector('#searchCsEnameModal_tbl tbody');
        tableBody.innerHTML = msg;

        changeComName();
    })
    .catch(error => console.error('Error:', error));
});


//영업부 사원 검색(모달창)에서 영업부 사원 검색 기능 

document.getElementById("searchBtn").addEventListener('click', function() {
    let keyword = document.querySelector('input[name="keyword"]').value;
    let jsonData;
    
    if (!isNaN(keyword)) { //숫자인 경우
        jsonData = JSON.stringify({ "keyword": parseInt(keyword) }); // 숫자로 변환하여 전송
    } else {
        jsonData = JSON.stringify({ "keyword": keyword }); // 문자열 그대로 전송
    }
    
    fetch('/searchModalCsEname', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: jsonData
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Failed to search csEname');
        }
        return response.json();
    })
    .then(json => {
    	
    	console.log(json);
    	
 
    	let msg = '';
        if (json.length > 0) {
            json.forEach(item => {
       
                msg += `
                    <tr class="list">
                    	 <td>${item.eno}</a></td>
                        <td><a href="#" class="salesDepartment" data-name="${item.eName}">${item.eName}</a></td>
                         <td>${item.job}</a></td>
                    </tr>
                `;
            });
        } else {
            msg = `
                <tr>
                    <td colspan="3">검색 결과가 없습니다.</td>
                </tr>
            `;
        }
        
        // HTML을 원하는 위치에 추가 또는 변경
        const tableBody = document.querySelector('#searchCsEnameModal_tbl tbody');
        tableBody.innerHTML = msg;

        changeComName();
    })
    .catch(error => console.error('Error:', error));
});




//영업부 사원 변경하기
function changeComName(){

    document.querySelectorAll('.salesDepartment').forEach(link => {
        link.addEventListener('click', function(event) {
            event.preventDefault(); // 기본 링크 동작 방지
            let result = confirm("영업 담당자로 선택하시겠습니까?");
            if(result){
               
                let selectedCsEname = this.dataset.name;
               
                document.querySelector('input[name="csEname"]').value = selectedCsEname;
                
                console.log(selectedCsEname);
            }
            document.querySelector('input[name="keyword"]').value = ''; 
            searchCsEnameModal.style.display = 'none'; 
            
        });
    });
	
	
};
/** --------------------기업명 찾기 모달창 끝------ */