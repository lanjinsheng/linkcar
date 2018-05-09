/**
 * 获取当前月的第一天
 */
function getCurrentMonthFirst(){
	var myDate = new Date();
    var year = myDate.getFullYear();
    var month = myDate.getMonth()+1;
    if (month<10){
        month = "0"+month;
    }
    var firstDay =year+"-"+month+"-"+"01";
 return firstDay;
}
/**
 * 获取当前月的最后一天
 */
function getCurrentMonthLast(){
	var myDate = new Date();
    var year = myDate.getFullYear();
    var month = myDate.getMonth()+1;
    if (month<10){
        month = "0"+month;
    }
    myDate = new Date(year,month,0);
    var lastDay =year+"-"+ month+"-"+myDate.getDate();
    return lastDay;
} 