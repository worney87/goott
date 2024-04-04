fetch("/userAdminPage/getUsageList/" + sessionStorage.getItem("Okja"))
  .then((Response) => {
    if (Response.ok) {
      return Response.json(); // 성공 시 json 응답을 반환합니다.
    }
    throw new Error("Network response was not ok.");
  })
  .then((data) => {
    showUsageList(data);
  })
  .catch((error) => {
    console.error("서버 에러:", error.message);
    swal("서버 에러", "불러오기 실패하였습니다.", "error");
  });

// 버튼 요소를 가져옵니다.
const downUsageListButton = document.getElementById('downUsageList');

// 버튼 클릭 이벤트에 대한 리스너를 추가합니다.
downUsageListButton.addEventListener('click', function() {
    // 서버로 GET 요청을 보냅니다.
    fetch('/userAdminPage/downUsageList/' + sessionStorage.getItem("Okja"))  // spotNo는 여기에 적절한 값으로 대체되어야 합니다.
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            // 서버에서 받은 파일을 다운로드합니다.
            response.blob().then(blob => {
                const url = window.URL.createObjectURL(blob);
                const a = document.createElement('a');
                a.href = url;
                a.download = 'usageList.xlsx';
                document.body.appendChild(a);
                a.click();
                window.URL.revokeObjectURL(url);
            });
            // 성공 메시지를 표시합니다.
            swal("성공", "거래명세서를 다운로드 완료하였습니다.", "success");
        })
        .catch(error => {
            // 요청이 실패했을 때 처리합니다.
            console.error('요청이 실패했습니다.', error);
            // 에러 메시지를 표시합니다.
            swal("서버 에러", "거래명세서를 다운로드 실패하였습니다.", "error");
        });
});  



function showUsageList(data) {
  let msg = "";
  let usageList = document.querySelector("#usageList");

  if (data === "") {
    msg += '<div class="my-2">';
    msg += '<span style="font-size:1rem;">거래내역이 없습니다.</span>';
    msg += "</div>";
  } else {
    data.forEach((list) => {
      msg += '<div class="my-2 ">';
      msg += '<a href="' + list.year + "-" + String(list.month).padStart(2, "0") + '" class="text-decoration-none" onclick="moveDatailUsage(event)" style="color: black;">';
      msg += '<div class="row bg-danger-subtle p-3 rounded-2 shadow-sm p-3 mb-5 bg-body-tertiary rounded">';
      msg += '<div class="col" style="width: 100px;">';
      msg += '<img width="48" height="48" src="https://img.icons8.com/fluency/48/buy-with-card.png" alt="buy-with-card"/>';
      msg += '</div>';
      msg += '<div class="col">';
      msg += '<div>';
      msg += '<span class="py-1 fw-bolder">' + list.year + "년  " + String(list.month).padStart(2, "0") + "월</span>";
      msg += '</div>';
      msg += '<div>';
      msg += '<span class="py-1" style="font-size:0.8rem;">' + list.count + "건의 주문</span>";
      msg += '</div>';
      msg += '</div>';
      msg += '<div class="col">';
      msg += '<span class="py-1 fw-bolder">청구금액 : ' + numberWithCommas(list.totalSum) + "원</span>";
      msg += '</div>';
      msg += '</div>';
      msg += '</a>';
      msg += '</div>';
    });
  }
  usageList.innerHTML = msg;
}

// 세자리마다 쉼표를 찍는 함수
function numberWithCommas(x) {
  return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

function moveDatailUsage(event) {
  event.preventDefault();

// 이벤트가 발생한 요소인 a 태그의 href 속성을 가져옵니다.
let recDate = event.currentTarget.getAttribute("href");

// sessionStorage에서 사용자 식별번호를 가져옵니다.
let no = sessionStorage.getItem("Okja");
  
  const form = document.createElement("form");
  form.setAttribute("method", "POST");
  form.setAttribute("action", "/userAdminPage/usageDetailHistory");
  document.body.appendChild(form);

  const recDateInput = document.createElement("input");
  recDateInput.setAttribute("type", "hidden");
  recDateInput.setAttribute("name", "recDate");
  recDateInput.setAttribute("value", recDate);
  form.appendChild(recDateInput);

  const noInput = document.createElement("input");
  noInput.setAttribute("type", "hidden");
  noInput.setAttribute("name", "no");
  noInput.setAttribute("value", no);
  form.appendChild(noInput);

  form.submit();
}

