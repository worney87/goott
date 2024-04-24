import axios from "axios";
import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import timeFormat from "../Utill/timeFormat";

function PostDetail(props) {
  const [post, setPost] = useState({});
  const [loading, setLoading] = useState(true);
  const { idx } = useParams();
  const navigate = useNavigate();

  const getPosts = async () => {
    try {
      const response = await axios.get(`/api/post/${idx}`);
      setPost(response.data);
      setLoading(false);
    } catch (error) {
      console.error("Error fetching posts:", error);
    }
  };

  useEffect(() => {
    getPosts();
  }, []);

  const updatePost = () => {
    navigate('/updatePost', { state: { post } })
  }

  const deletePost = async () => {
    try {
      const response = await axios.delete(`/api/post/${post.idx}`);
      const data = response.data;
      if (data === 'success') {
          alert('게시글 삭제를 성공하였습니다.');
          navigate('/post');
      } else {
          alert('게시글 삭제를 실패하였습니다.');
      }
  } catch (error) {
      console.error('Error submitting post:', error);
      alert('게시글 삭제 중 오류가 발생하였습니다.');
  }

  }

  return (
    <>
      {loading ? (
        <div>로딩중</div>
      ) : (
        <div>
          <h2>Title: {post.title}</h2>
          <h4>date: {timeFormat(post.regdate)}</h4>
          <hr />
          <p>Content: {post.content}</p>
          <p>idx: {post.idx}</p>
          <p>writer: {post.writer}</p>
        </div>
      )}
      <button onClick={updatePost}>수정</button>
      <button onClick={deletePost}>삭제</button>
    </>
  );
}

export default PostDetail;
