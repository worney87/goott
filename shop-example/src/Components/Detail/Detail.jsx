import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import styled from "styled-components";
import DetailHead from "./DetailHead";
import DetailBody from "./DetailBody";

const DetailWrapper = styled.div`
  margin: 60px auto 0;
  max-width: 1200px;
  width: 80%;
`;

function Detail({ data }) {
  const { pId } = useParams();
  const product = data.find((obj) => obj.id === parseInt(pId));

  const [loading, setLoading] = useState(true);

  useEffect(() => {
    setLoading(false);
  }, [product]);

  useEffect(() => {
    if (product) {
      let storageData = localStorage.getItem("recently");
      if (!storageData) {
        storageData = [];
      } else {
        storageData = JSON.parse(storageData);
      }
      
      // 제품이 최근 본 목록에 이미 있는지 확인
      const index = storageData.findIndex((obj) => obj.id === product.id);
      if (index !== -1) {
        // 기존 항목 제거
        storageData.splice(index, 1);
      }

      // 현재 제품을 목록의 맨 앞에 추가
      storageData.unshift(product);

      // 목록을 3개로 제한
      if (storageData.length > 3) {
        storageData = storageData.slice(0, 3);
      }

      localStorage.setItem("recently", JSON.stringify(storageData));
    }
  }, [product]);

  return (
    <DetailWrapper>
      {loading ? (
        <p>Loading..</p>
      ) : (
        <>
          {product ? (
            <>
              <DetailHead data={product} />
              <DetailBody detail={product.detail} />
            </>
          ) : (
            <p>Product not found.</p>
          )}
        </>
      )}
    </DetailWrapper>
  );
}

export default Detail;
