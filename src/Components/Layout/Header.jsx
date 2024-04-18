import React from "react";
import { Link } from "react-router-dom";
import styled from "styled-components";

const Wrapper = styled.div`
  position: relative;
  max-width: 70%;
  min-height: 120px;
  margin: 0 auto;
  margin-bottom: 10px;
  border-bottom: 3px solid gray;
`;
const Logo = styled.div`
  position: relative;
  top: 50px;
  left: 0;
  width: 180px;
  height: 40px;
  background-image: url(/images/headerLogo.png);
  background-repeat: no-repeat;
  background-size: contain;
`;



function Header(props) {
  return (
    <div>
      <Wrapper>
        <Link to={"/"}>
          <Logo />
        </Link>
      </Wrapper>
    </div>
  );
}

export default Header;
