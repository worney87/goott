/* eslint-disable jsx-a11y/alt-text */
import React from "react";
import styled from "styled-components";
import Product from "../Product/Product";

const ProductGrid = styled.div`
  max-width: 70%;
  margin-right: auto;
  margin-left: auto;
  display: grid;
  grid-template-rows: 1fr;
  grid-template-columns: 1fr 1fr 1fr;
`;

function ProductList({ data }) {
  return (
    <ProductGrid>
      {
        /* key = id , data = product */
        data.map((product) => {
          return <Product key={product.id} data={product} />;
        })
      }
    </ProductGrid>
  );
}

export default ProductList;
