import React, { useState } from "react";
import Header from "../Layout/Header";
import styled from "styled-components";
import { Route, Routes } from "react-router-dom";
import Main from "../Layout/Main";
import data from '../../util/mock/data.js';
import Detail from "../Detail/Detail.jsx";

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

    const [product, setProducts] = useState(data);

    let a;

  return (
    <Container>
      <HeaderArea>
        <Header />
      </HeaderArea>
      <Body>
        <Routes>
          <Route path="/" element={<Main data={product}/>} />
          <Route path="/detail/:pId" element={<Detail data={product}/>} />
        </Routes>
      </Body>
    </Container>
  );
}

export default Display;
