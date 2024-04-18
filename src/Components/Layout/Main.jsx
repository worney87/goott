import React from "react";
import styled from "styled-components";
import ProductList from "./ProductList";

const Wrapper = styled.div``;
const MainBG = styled.div`
  max-width: 70%;
  min-height: 120px;
  margin: 0 auto;
  min-height: 500px;
  background-image: url(/images/main/main1.jpg);
  background-size: contain;
  background-position: center;
  background-repeat: no-repeat;
`;

function Main({ data }) {
  return (
    <Wrapper>
      <MainBG />
      {/* 상품 목록이 들어갈 예정 */}
      <ProductList data={data} />
    </Wrapper>
  );
}

export default Main;
