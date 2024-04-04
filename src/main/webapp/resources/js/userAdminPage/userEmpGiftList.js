document.addEventListener("DOMContentLoaded", function () {
  let ordNo = document.querySelector("header").dataset.code;
  fetch("/validateGiftSelection/" + ordNo)
    .then((response) => response.text())
    .then((text) => {
      if (text === "success") {
        // 선물 선택이 유효한 경우
        // 여기에 선물 선택이 가능한 로직을 작성합니다.
      } else {
        swal("선물선택이 유효하지 않습니다.", {
          icon: "error",
        }).then(() => {
          window.close();
        });
      }
    })
    .catch((error) => {
      swal("선물선택이 유효하지 않습니다.", {
        icon: "error",
      }).then(() => {
        window.close();
      });
    });
});

// 선물 선택 버튼 클릭 이벤트 핸들러
document.querySelector("#selectGift").addEventListener("click", () => {
  // 선택된 라디오 버튼 확인
  let checkbox = document.querySelector('input[type="radio"]:checked');

  // 선택된 상품이 없으면 알림 표시 후 종료
  if (!checkbox) {
    swal("선택된 상품이 없습니다.");
    return;
  }

  // 선택된 상품의 상품코드 가져오기
  let prdNo = checkbox.value;

  // 주문번호 가져오기
  let ordNo = document.querySelector("header").dataset.code;

  // 상품가 가져오기
  let prdSal = checkbox.dataset.code;

  // 선물 주문 요청 보내기
  fetch("/userAdminPage/orderGift", {
    method: "post",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      prdNo: prdNo,
      ordNo: ordNo,
      prdSal: prdSal,
    }),
  })
    .then((response) => response.text())
    .then((text) => {
      if (text === "success") {
        // 선물 주문 성공 시 선물 발송 요청
        sendGift(ordNo);
      } else {
        // 선물 주문 실패 시 알림 표시
        swal("선물 주문이 실패했습니다. 다시 시도해주세요.", { icon: "error" });
      }
    })
    .catch((error) => {
      // 오류 발생 시 에러 로그 출력 및 알림 표시
      console.error("서버에 요청을 보내는 중 오류가 발생했습니다:", error);
      swal("오류가 발생하여 선물 주문에 실패했습니다.", { icon: "error" });
    });
});

// 선물 발송 요청 함수
function sendGift(ordNo) {
  // 선물 발송 요청 보내기
  fetch("/send-gift/" + ordNo)
    .then((response) => response.text())
    .then((text) => {
      if (text === "success") {
        // 선물 발송 성공 시 알림 표시
        swal("선물이 발송되었습니다.", { icon: "success" }).then(() =>
          window.close()
        );
      }
    })
    .catch((error) => {
      // 오류 발생 시 에러 로그 출력 및 알림 표시
      console.error("서버에 요청을 보내는 중 오류가 발생했습니다:", error);
      swal("오류가 발생하여 선물 발송에 실패했습니다.", { icon: "error" })
    });
}
