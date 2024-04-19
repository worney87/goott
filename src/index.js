import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import App from "./App";
import reportWebVitals from "./reportWebVitals";
import { BrowserRouter } from "react-router-dom";
import { legacy_createStore } from "redux";
import rootReducer from "./Components/Reducer";
import { Provider } from "react-redux";
import { StyleSheetManager } from "styled-components";

const store = legacy_createStore(rootReducer);
const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  //<React.StrictMode>
  <BrowserRouter>
    <Provider store={store}>
      <StyleSheetManager shouldForwardProp={(prop) => prop !== "image"}>
        <App />
      </StyleSheetManager>
    </Provider>
  </BrowserRouter>
  //</React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
