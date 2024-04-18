import React from "react";
import formatKoreanCurrency from "../../util/display/display";
import styled from "styled-components";

const StyledTable = styled.table`
  height: 100%;
`;

const StyledHeadRow = styled.tr`
  font-size: 1.5em; /* h1 수준의 크기 */
`;

function DescriptionList({ data }) {
  return (
    <div>
      <StyledTable>
        <thead>
          <StyledHeadRow>
            <td colSpan="2">{data.content}</td>
          </StyledHeadRow>
          <StyledHeadRow>
            <td colSpan="2">{formatKoreanCurrency(data.price)}</td>
          </StyledHeadRow>
        </thead>
        <tbody>
          <tr>
            <th>원산지</th>
            <td>상품 정보 참고</td>
          </tr>
          <tr>
            <th>배송비</th>
            <td>3,000원</td>
          </tr>
        </tbody>
        <tfoot>
          <tr>
            <td>
              <button>장바구니</button>
            </td>
            <td>
              <button>바로구매</button>
            </td>
          </tr>
        </tfoot>
      </StyledTable>
    </div>
  );
}

export default DescriptionList;
