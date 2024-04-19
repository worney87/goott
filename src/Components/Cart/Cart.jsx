import React from "react";
import { useDispatch, useSelector } from "react-redux";
import styled from "styled-components";
import formatKoreanCurrency from "../../util/display/display.js";
import { delete_item, increase, decrease } from "../Action/cartAction.js";
import { FaPlus, FaMinus } from "react-icons/fa";
import { GrClose } from "react-icons/gr";
import { BsFillCartXFill } from "react-icons/bs";

const CartWrapper = styled.div`
  max-width: 70%;
  margin: auto;
  table {
    width: 100%;
    border : 0;
    border-collapse: collapse;
  }
  th {
    font-size: 1.2rem;
    padding: 20px;
    border-bottom: 0.5px solid grey;
  }
  td {
    text-align: center;
    font-size: 1rem;
    padding: 10px;
    border-bottom: 0.5px solid #c9c9c9;
  }
  td button {
    width : 30px;
    height : 30px;
    border: 0;
  }
`;

const ProductImage = styled.img`
  max-width: 70px;
  max-height: 70px;
`;

function Cart(props) {
  const cart = useSelector((state) => state.cart);
  const dispatch = useDispatch();
  const deleteItem = (id) => {
    dispatch(delete_item(id));
  };
  const increaseQuentity = (id) => {
    dispatch(increase(id));
  };
  const decreaseQuentity = (id) => {
    dispatch(decrease(id));
  };

  return (
    <CartWrapper>
      <table>
        <thead>
          <tr>
            <th>
              <input type="checkbox" />
            </th>
            <th colSpan={2}>상품정보</th>
            <th></th>
            <th>수량</th>
            <th></th>
            <th>가격</th>
            <th>삭제</th>
          </tr>
        </thead>
        <tbody>
          {cart.length === 0 ? (
            <tr>
              <td colSpan={8} style={{fontSize:'1.5rem', color:'gray', height : '60vh'}}><BsFillCartXFill/>장바구니가 비었습니다.</td>
            </tr>
          ) : (
            cart.map((item) => (
              <tr key={item.id}>
                <td>
                  <input type="checkbox" />
                </td>
                <td>
                  <ProductImage src={process.env.PUBLIC_URL + `/images/product/${item.productImg}`} alt="product" />
                </td>
                <td>{item.productName}</td>
                <td>
                  <button
                    onClick={() => {
                      decreaseQuentity(item.id);
                    }}
                    disabled={item.count === 1}
                  >
                    <FaMinus />
                  </button>
                </td>
                <td>{item.count}</td>
                <td>
                  <button
                    onClick={() => {
                      increaseQuentity(item.id);
                    }}
                  >
                    <FaPlus />
                  </button>
                </td>
                <td>{formatKoreanCurrency(item.price * item.count)}</td>
                <td>
                  <button
                    onClick={() => {
                      deleteItem(item.id);
                    }}
                  >
                    <GrClose />
                  </button>
                </td>
              </tr>
            ))
          )}
        </tbody>
      </table>
    </CartWrapper>
  );
}

export default Cart;
