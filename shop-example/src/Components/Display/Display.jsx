import React, { useEffect, useState } from "react";
import Header from "../Layout/Header";
import styled from "styled-components";
import { Route, Routes } from "react-router-dom";
import Main from "../Layout/Main";
import data from "../../util/mock/data.js";
import Detail from "../Detail/Detail.jsx";
import axios from "axios";
import Cart from "../Cart/Cart.jsx";
import Recently from "../../Recently/Recently.jsx";

const Container = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-direction: column;
`;
const HeaderArea = styled.div`
  width: 100%;
  background-color: white;
  position: sticky;
  top: 0;
  z-index: 9999;
`;
const Body = styled.div`
  width: 100%;
  min-height: 50vh;
`;

function Display(props) {
  // 일반 변수로 사용할건지, 상태 값으로 사용할건지..

  // const [product, setProducts] = useState(data);

  // <- proxy 사용해서 렌더링
  const [products, setProducts] = useState([]); // 전체 데이터
  const [displayData, setDisplayData] = useState([]); // 화면에 보여줄 데이터
  const [cnt, setCnt] = useState(1); // 더보기 클릭 횟수
  const [loadMore, setLoadMore] = useState(true); // 더보기 버튼 활성화 상태

  // async() 활용
  const getProductList = async () => {
    // 1. fetch
    // const response = await fetch('api/products');
    // const json = await response.json();

    // 2. axios
    const json = await (await axios.get("/api/products")).data;
    setProducts(json);

    // axios('api/products')
    // .then(json => setProducts(json.data));
  };

  // useEffect() 활용
  useEffect(() => {
    // axios('api/products')
    // .then(json => setProducts(json.data));
    getProductList();
  }, []);

  /* proxy 예제

  fetch("/test/getText/")
    .then((Response) => Response.text())
    .then((text) => console.log(text));

  axios("/test/getText/").then((Response) =>
    console.log(Response.data)
  );

  fetch("/test/getList/")
    .then((Response) => Response.text())
    .then((text) => console.log(text));

  axios("/test/getList/").then((Response) =>
    console.log(Response.data)
  );
  axios("/api/products/").then((Response) =>
    console.log(Response.data)
  );
  */

  // ------------------------------------------------------------------------------------------------
  useEffect(() => {
    setDisplayData(products.slice(0, cnt * 3));
    let totalCnt;
    if (products.length % 3 !== 0) {
      totalCnt = products.length / 3 + 1;
    } else {
      totalCnt = products.length / 3;
    }

    totalCnt === cnt && setLoadMore(false);
  }, [cnt, products]);

  const handleLoadMore = () => {
    setCnt((prev) => prev + 1);
  };

  return (
    <Container>
      <HeaderArea>
        <Header />
      </HeaderArea>
      <Body>
        <Routes>
          <Route
            path="/"
            element={
              <>
                <Recently />
                <Main
                  data={displayData}
                  handleLoadMore={handleLoadMore}
                  loadMore={loadMore}
                />
              </>
            }
          />
          <Route path="/detail/:pId" element={<Detail data={products} />} />
          <Route path="/cart" element={<Cart />} />
        </Routes>
      </Body>
    </Container>
  );
}

export default Display;
