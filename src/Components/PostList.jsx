import React, { useEffect, useState } from "react";
import axios from "axios";
import { Link, useNavigate } from "react-router-dom";
import timeFormat from "../Utill/timeFormat";
import paging from "../code/paging";

function PostList(props) {
  const navigate = useNavigate();
  const [currentPage, setCurrentPage] = useState(1);
  const [amount] = useState(4);
  const [loading, setLoading] = useState(true);
  const [posts, setPosts] = useState([]);
  const [totalCount, setTotalCount] = useState(0);

  useEffect(() => {
    const getPosts = async () => {
      setLoading(true); // 데이터 로딩 시작
      const [postsResponse, totalCountResponse] = await axios.all([
        axios.get(`/api/postList/${currentPage}/${amount}`),
        axios.get(`/api/totalCount`),
      ]);
      setPosts(postsResponse.data);
      setTotalCount(totalCountResponse.data);
      setLoading(false); // 데이터 로딩 완료
    };
    getPosts();
  }, [currentPage, amount]);

  const obj = paging(totalCount, currentPage, amount);
  const { startPage, endPage, prev, next } = obj;
  const pageList = Array.from(
    { length: endPage - startPage + 1 },
    (_, i) => startPage + i
  );

  const handlePageClick = (page) => {
    setCurrentPage(page);
  };

  const newPost = () => {
    navigate("/write");
  };

  return (
    <div>
      <div>
        <button onClick={newPost}>새 게시글 등록</button>
      </div>
      {loading ? (
        <div>loading...</div>
      ) : (
        <div>
          <table>
            <thead>
              <tr>
                <th>번호</th>
                <th>제목</th>
                <th>작성자</th>
                <th>작성일</th>
              </tr>
            </thead>
            <tbody>
              {posts.map((post, index) => (
                <tr key={post.idx}>
                  <td>{index + 1}</td>
                  <td>
                    <Link to={`/post/${post.idx}`}>{post.title}</Link>
                  </td>
                  <td>{post.writer}</td>
                  <td>{timeFormat(post.regdate)}</td>
                </tr>
              ))}
            </tbody>
          </table>
          <div>
            {prev && (
              <button onClick={() => handlePageClick(currentPage - 1)}>
                이전
              </button>
            )}
            {pageList.map((page) => (
              <button key={page} onClick={() => handlePageClick(page)}>
                {page}
              </button>
            ))}
            {next && (
              <button onClick={() => handlePageClick(currentPage + 1)}>
                다음
              </button>
            )}
          </div>
        </div>
      )}
    </div>
  );
}

export default PostList;
