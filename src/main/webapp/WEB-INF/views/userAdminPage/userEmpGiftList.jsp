<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width">
    <title>선물고르기</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
          crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
            crossorigin="anonymous"></script>
    <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
    <style>
        /* GOOGLE FONTS */
        @font-face {
          font-family: "S-CoreDream-3Light";
          src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_six@1.2/S-CoreDream-3Light.woff")
            format("woff");
        }

        /* VARIABLES CSS */
        :root {
          --nav--width: 60px;

          /* Colores */
          --bg-color: #fa0050;
          --first-color: #ff5798;
          --sub-color: #ff90da;
          --white-color: #fff;

          /* Fuente y tipografia */
          --body-font: "S-CoreDream-3Light";
          --normal-font-size: 1rem;
          --small-font-size: 0.875rem;
          --font-regular: 400;
          --font-medium: 500;
          --font-bold: 700;
          --font-black: 900;

          /* z index */
          --z-fixed: 100;
        }

        /* BASE */
        *,
        ::before,
        ::after {
          box-sizing: border-box;
          font-family: var(--body-font);
        }

        body {
            margin: 0;
            padding: 0;
            font-family: '';
        }

        .container {
            width: 400px;
            background-color: #FFF15F;
        }

        header {
            padding-top: 30px;
        }

        footer {
            padding: 30px;
        }
        .card{
            background-color: #F2FEDC;
        }

    </style>
</head>
<body>
<div class="container">
    <header data-code="${ordNo}">
        <div class="d-flex text-center">
            <div class="p-2">
                <img width="94" height="94" src="https://img.icons8.com/3d-fluency/94/gift.png" alt="gift" />
            </div>
            <div class="p-2 flex-grow-1 align-self-center">
                <div>
                    <!-- 희망하시는 선물을 선택하고 -->
                    <span class="fw-bold">희망하시는 선물을 선택하고 </span><br>
                    <!-- '선택하기' 버튼을 눌러주세요. -->
                    <span class="fw-bold">'선택하기' 버튼을 눌러주세요.</span>
                </div>
            </div>
        </div>
    </header>
    <hr>
    <div class="main">
        <div class="row row-cols-1 row-cols-md-2 g-4 p-2" id="productList">
            <!-- JSTL을 이용하여 선물 목록 표시 -->
            <c:forEach var="gift" items="${giftList}">
                <div class="col-1" style="width: 180px;">
                    <div class="form-check" style="width: 150px;">
                        <input class="form-check-input" type="radio" name="gift" data-code="${gift.prdSal}" value="${gift.prdNo}" id="${gift.prdNo}" onchange="changeBackground(this)"/>
                        <label class="form-check-label" for="${gift.prdNo}">
                            <div class="card h-100" style="width: 150px;">
                                <img src="${gift.prdImg}" class="card-img-top object-fit-fill border rounded"
                                     alt="product" width="150px" height="150px"/>
                                <div class="card-body">
                                    <div style="height: 85px;">
                                        <!-- 선물 이름 표시 -->
                                        <span class="card-text text-break fw-bold" style="font-size: 0.7rem;">
                                            <script>
                                                var productName = '${gift.prdName}';
                                                var cleanProductName = productName.replace(/\[.*?\]|\(.*?\)/g, '').trim();
                                                document.write(cleanProductName);
                                            </script>
                                        </span>
                                    </div>
                                </div>
                            </div>
                        </label>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
    <hr>
    <footer>
        <div class="d-grid gap-2 col-6 mx-auto">
            <button class="btn btn-primary" type="button" id="selectGift">선택하기</button>
        </div>
    </footer>
</div>
</body>
<script src="/resources/js/userAdminPage/userEmpGiftList.js"></script>
</html>
