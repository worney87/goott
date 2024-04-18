function formatCurrency(amount) {
    // 숫자를 원화표시로 변환하는 함수
    const currencySymbol = '₩'; // 원화 기호
    const formattedAmount = new Intl.NumberFormat('ko-KR', {
        style: 'currency',
        currency: 'KRW'
    }).format(amount);

    return formattedAmount;
}
function numberWithCommas(x) {
    // 천단위 구분 기호를 추가하는 함수
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}
function formatKoreanCurrency(amount, type){

    let returnVal;

    if(type==='curr'){
        returnVal = numberWithCommas(formatCurrency(amount));
    }else{
        returnVal = numberWithCommas(amount) + '원';
    }

    return returnVal;
} 

export default formatKoreanCurrency;