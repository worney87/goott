function paging(totalCount, pageNum, amount){
    // 끝 페이지 번호 ( 한 화면에 10 페이지씩 보기 위함 )
  let endPage = Math.ceil( (pageNum / 10) ) * 10;  
  const startPage = endPage - 9;  // 시작 페이지 번호
  const realEnd = Math.ceil(totalCount / amount);
  if(realEnd < endPage){
      endPage = realEnd;
  }
  const prev = startPage > 1;  // 이전 버튼 활성화 유무
  const next = endPage < realEnd;  // 다음 버튼 활성화 유무

  return {
      startPage : startPage, 
      endPage : endPage, 
      prev : prev,
      next : next 
  }
}

export default paging;