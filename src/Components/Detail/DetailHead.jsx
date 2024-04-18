import React from "react";
import DescriptionList from "./DescriptionList";
import styled from "styled-components";

const DetailHeadWrapper = styled.div`
  max-width: 100%;
  margin-right: auto;
  margin-left: auto;
  display: grid;
  grid-template-rows: 1fr;
  grid-template-columns: 1fr 1fr;
`;

const ProductImg = styled.img`
  margin: auto;
  width: 100%;
`;

function DetailHead({ data }) {
  const product = data[0];

  return (
    <DetailHeadWrapper>
      <ProductImg src={`/images/product/${product.main}`} alt="제품사진" />
      <DescriptionList data={product} />
    </DetailHeadWrapper>
  );
}

export default DetailHead;
