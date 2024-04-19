// 상수 값은 ADD_ITEM, DELETE_ITEM

import {
  ADD_ITEM,
  DECREASE,
  DELETE_ITEM,
  INCREASE,
  UPDATE_ITEM,
} from "../Action/type.js";

const initialState = [];
/*
  {id : 1, productName : '', price : 0, count : 0}
*/
const cart = (state = initialState, action) => {
  switch (action.type) {
    case ADD_ITEM:
      return [...state, action.payload];

    case UPDATE_ITEM:
      return state.map((item) => {
        if (item.id === parseInt(action.payload)) {
          return {
            ...item,
            count: item.count + 1,
          };
        } else {
          return item;
        }
      });

    case DELETE_ITEM:
      return state.filter((item) => item.id !== action.payload);

    case INCREASE:
      return state.map((item) => {
        if (item.id === parseInt(action.payload)) {
          return {
            ...item,
            count: item.count + 1,
          };
        } else {
          return item;
        }
      });

    case DECREASE:
      return state.map((item) => {
        if (item.id === parseInt(action.payload)) {
          return {
            ...item,
            count: item.count - 1,
          };
        } else {
          return item;
        }
      });

    default:
      return state;
  }
};

export default cart;
