/*
    reducer가 많아지고, 각각의 reducer에서 사용하는 기능들이 같을 수 있으므르,
    상수 앞에 리듀서 명을 붙이기도 한다.
    ex) const INCREAS = 'counter / INCERSE';
*/

export const ADD_ITEM = 'ADD_ITEM'
export const UPDATE_ITEM = 'UPDATE_ITEM'
export const DELETE_ITEM = 'DELETE_ITEM'
export const INCREASE = 'INCREASE'
export const DECREASE = 'DECREASE'