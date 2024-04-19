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

const LoadMoreButton = styled.button`
  display: block;
  margin: auto;
  width: 50%;
  padding: 10px 20px;
  text-align: center;
  margin-top: 30px;
  margin-bottom: 60px;
  font-size: 1.1rem;
  font-weight: bold;
  color: white;
  background-color: darkorange;
  border: 0;
  border-radius: 10px;
  box-shadow: 5px 5px 5px 5px #e8e4e4;
  & {
    transition: all 0.1s linear;
  }
  &:hover {
    background-color: orange;
    cursor: pointer;
    transform: scale(1.01);
  }
`;

function Main({ data, handleLoadMore, loadMore }) {
  return (
    <Wrapper>
      <MainBG />
      {/* 상품 목록이 들어갈 예정 */}
      <ProductList data={data} />
      {loadMore && (
        <LoadMoreButton onClick={handleLoadMore}>더보기</LoadMoreButton>
      )}
    </Wrapper>
  );
}

export default Main;
