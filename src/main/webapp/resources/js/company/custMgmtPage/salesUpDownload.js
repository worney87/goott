// 다운로드 버튼 눌러서 함수호출
document.getElementById('downloadButton').addEventListener('click', ()=>{
	download();
});

//다운로드 버튼 눌러서 함수호출 (양식)
document.getElementById('exExcelPrd').addEventListener('click', ()=>{
	exPrdDownload();
});


function download() 
{
	 let filterResult = {
	    		"keyword": document.getElementById('keyword').value,
			    "checkedValues": [] //
	    };
	    
	    document.querySelectorAll('input[type=checkbox][data-filter="salesList-csStatus"]:checked').forEach(function(checkbox) {
	        if(checkbox.value != '전체')
			{
	        	filterResult.checkedValues.push(checkbox.value);
			}
	    });

	    console.log(filterResult);

		
    // 서버로 데이터 전송
    fetch('/downloadSalesExcel', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(filterResult)
    })
    .then(response => response.blob())
    .then(blob =>
    {
        // 엑셀 파일 다운로드	
        const url = window.URL.createObjectURL(new Blob([blob])); 	// Blob 데이터로부터 URL 생성
        const a = document.createElement('a');  					// <a> 요소 생성
        a.href = url;  
        a.download = 'excelTest.xlsx';  							// 다운로드되는 파일의 이름 설정
        document.body.appendChild(a); 								// <a> 요소를 문서에 추가
        a.click();  												// 클릭해서 다운로드 시작
        window.URL.revokeObjectURL(url);  							// URL 객체 해제
        document.body.removeChild(a);  								// <a> 요소를 문서에서 제거
    })
    .catch(error => 
    {
        console.error('Error:', error);
    });
}









	function exPrdDownload() 
	{
		fetch('/exPrdExcel')
	    .then(response => response.blob())
	    .then(blob =>
	    {
	        // 엑셀 파일 다운로드	
	        const url = window.URL.createObjectURL(new Blob([blob])); 	// Blob 데이터로부터 URL 생성
	        const a = document.createElement('a');  					// <a> 요소 생성
	        a.href = url;  
	        a.download = 'exExcel.xlsx';  							// 다운로드되는 파일의 이름 설정
	        document.body.appendChild(a); 								// <a> 요소를 문서에 추가
	        a.click();  												// 클릭해서 다운로드 시작
	        window.URL.revokeObjectURL(url);  							// URL 객체 해제
	        document.body.removeChild(a);  								// <a> 요소를 문서에서 제거
	    })
	    .catch(error => 
	    {
	        console.error('Error:', error);
	    });
	}

