import { ADD_ITEM, DELETE_ITEM, UPDATE_ITEM, INCREASE, DECREASE } from "./type";

function add_item(item) {
  return {
    type: ADD_ITEM,
    payload: item,
  };
}
function update_item(id) {
  return {
    type: UPDATE_ITEM,
    payload: id,
  };
}
function delete_item(id) {
  return {
    type: DELETE_ITEM,
    payload: id,
  };
}
function increase(id) {
  return {
    type: INCREASE,
    payload: id,
  };
}
function decrease(id) {
  return {
    type: DECREASE,
    payload: id,
  };
}

export { add_item, delete_item, update_item, increase, decrease };
