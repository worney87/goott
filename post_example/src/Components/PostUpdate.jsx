import React, { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import timeFormat from "../Utill/timeFormat";
import axios from "axios";

function PostUpdate(props) {
  const location = useLocation();
  const postData = location.state.post;

  const [writer, setWriter] = useState(postData.writer);
  const [title, setTitle] = useState(postData.title);
  const [content, setContent] = useState(postData.content);
  const [idx] = useState(postData.idx);
  const navigate = useNavigate();

  const handleWriteChange = (e) => {
    setWriter(e.target.value);
  };

  const handleTitleChange = (e) => {
    setTitle(e.target.value);
  };

  const handleContentChange = (e) => {
    setContent(e.target.value);
  };

  const submit = async () => {
    if (!writer || !title || !content) {
      alert("모두 입력하세요.");
    } else {
      const postData = {
        title: title,
        content: content,
        writer: writer,
        idx: idx,
      };

      try {
        const response = await axios.put("/api/post", postData);
        const data = response.data;
        if (data === "success") {
          alert("게시글 수정을 성공하였습니다.");
          navigate(`/post/${idx}`);
        } else {
          alert("게시글 수정을 실패하였습니다.");
        }
      } catch (error) {
        console.error("Error submitting post:", error);
        alert("게시글 수정 중 오류가 발생하였습니다.");
      }
    }
  };

  const reset = () => {
    setWriter("");
    setTitle("");
    setContent("");
  };

  const moveList = () => {
    navigate("/post");
  };
  return (
    <>
      <div>
        <h2>
          <label>Title : </label>
          <input
            type="text"
            name="title"
            value={title}
            onChange={handleTitleChange}
          />
        </h2>
        <h4>
          <label>Date : </label> {timeFormat(postData.regdate)}
        </h4>
        <hr />
        <p>
          <label>Content : </label>
          <textarea
            name="content"
            value={content}
            onChange={handleContentChange}
          />
        </p>
        <p>
          <label>Idx : </label> {idx}
        </p>
        <p>
          <label>Writer : </label>
          <input
            type="text"
            name="writer"
            value={writer}
            onChange={handleWriteChange}
          />
        </p>
      </div>
      <div>
        <button onClick={submit}>수정</button>
        <button onClick={reset}>다시 입력</button>
        <button onClick={moveList}>목록으로 이동</button>
      </div>
    </>
  );
}

export default PostUpdate;
