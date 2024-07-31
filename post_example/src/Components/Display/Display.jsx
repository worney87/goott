import React from "react";
import { Route, Routes } from "react-router-dom";
import PostList from "../PostList";
import Main from "../Layout/Main";
import Header from "../Layout/Header";
import PostDetail from "../PostDetail";
import PostWrite from "../PostWrite";
import PostUpdate from "../PostUpdate";

function Display(props) {
  const MyStyle = {
    margin: "5px",
    padding: "5px",
  };
  return (
    <div style={MyStyle}>
      <Header />
      <Routes>
        <Route path="/" element={<Main />} />
        <Route path="post" element={<PostList/>} />
        <Route path="post/:idx" element={<PostDetail />} />
        <Route path="write" element={<PostWrite />} />
        <Route path="updatePost" element={<PostUpdate />} />
      </Routes>
    </div>
  );
}

export default Display;
