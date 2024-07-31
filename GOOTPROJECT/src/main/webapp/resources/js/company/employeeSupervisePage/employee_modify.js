/*// ----- css 파일 추가
// 1. 파일 경로 설정
const CSS_FILE_PATH = '/resources/css/employeeSupervisePage/employee_modify.css';
// 2. link 태그 생성
let linkEle = document.createElement('link');
linkEle.rel = 'stylesheet';
linkEle.type = 'text/css';
linkEle.href = CSS_FILE_PATH;
// 3. head 태그에 link 엘리먼트 추가
document.head.appendChild(linkEle);*/

const f = document.forms[0];

//데이트 피커 (달력 기능)
flatpickr.localize(flatpickr.l10ns.ko);

let datePick1 = document.querySelector('.datePick1');
datePick1.flatpickr({
    local: 'ko'

});

document.querySelectorAll('input[type="button"]').forEach( btn => {
	btn.addEventListener( 'click', (event) => {
		event.preventDefault(); 
		
		let type = btn.id;
		
		if( type === 'closeBtn') close();
		else if ( type === 'saveBtn'){
			
			update(f);
		}
	})
})

function close(){
	location.href = '/searchEmployee';
}

function update(f){

	 console.log(f);
	 f.action = '/employee_modify';
	 f.submit();
}
