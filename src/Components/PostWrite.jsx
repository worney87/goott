import axios from "axios";
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

function PostWrite(props) {
  const [writer, setWriter] = useState("");
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
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
        };

        try {
            const response = await axios.post("/api/post", postData);
            const data = response.data;
            if (data === 'success') {
                alert('게시글 등록을 성공하였습니다.');
                navigate('/post');
            } else {
                alert('게시글 등록을 실패하였습니다.');
            }
        } catch (error) {
            console.error('Error submitting post:', error);
            alert('게시글 등록 중 오류가 발생하였습니다.');
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
    <div>
      <h1>게시글 작성</h1>
      <div>
        <label>작성자 : </label>
        <input
          type="text"
          name="writer"
          value={writer}
          onChange={handleWriteChange}
        />
      </div>
      <div>
        <label>제목 : </label>
        <input
          type="text"
          name="title"
          value={title}
          onChange={handleTitleChange}
        />
      </div>
      <div>
        <label>내용 : </label>
        <textarea
          placeholder="내용을 입력하세요"
          name="content"
          value={content}
          onChange={handleContentChange}
        />
      </div>
      <div>
        <button onClick={submit}>등록</button>
        <button onClick={reset}>다시 입력</button>
        <button onClick={moveList}>목록으로 이동</button>
      </div>
    </div>
  );
}

export default PostWrite;
