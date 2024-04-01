document.addEventListener("DOMContentLoaded", function() {
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

document.querySelector("#selectGift").addEventListener("click", () => {
  let checkbox = document.querySelector('input[type="radio"]:checked');

  if (!checkbox) {
    swal("선택된 상품이 없습니다.");
    return;
  }

  // 선택된 상품의 상품코드 가져오기
  let prdNo = checkbox.value;

  // ordNo 가져오기
  let ordNo = document.querySelector("header").dataset.code;

  // prdSal 가져오기
  let prdSal = checkbox.dataset.code;

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
    .then((Response) => Response.text())
    .then((text) => {
      if (text === "success") {
        swal("선택된 선물이 주문되었습니다.", {
          icon: "success",
        }).then(() => {
          window.close();
        });
      } else {
        swal("선물 주문이 실패했습니다. 다시 시도해주세요.", {
          icon: "error",
        });
      }
    })
    .catch((error) => {
      console.error("서버에 요청을 보내는 중 오류가 발생했습니다:", error);
      swal("오류가 발생하여 선물 주문에 실패했습니다.", { icon: "error" });
    });
});
