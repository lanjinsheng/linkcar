var isShow;
function openDialogResize(URL,width,height)
{
  window.open(URL,"","height="+height+",width="+width+",status=0,toolbar=no,menubar=no,location=no,scrollbars=yes,top=200,left=200,resizable=yes,modal=yes,dependent=yes,dialog=yes,minimizable=no");
}

//弹出警告框信息
function warningInfo(msg){
	jQuery.messager.alert('提示：',msg);
}

function table_reload(length){
	var options=jQuery('#table').datagrid('getPager').data("pagination").options;
	var currentPageNumber=options.pageNumber;
	var rowSize=jQuery('#table').datagrid('getRows').length;
	if(currentPageNumber!=1 && rowSize==length) //你自己所选择的行数与当前页面的行数一样
	{
		var queryParams = jQuery('#table').datagrid('options').queryParams;  
		queryParams.page = queryParams.page-1;
		jQuery('#table').datagrid('reload');
	}else
	{
		jQuery('#table').datagrid('reload');
	}
}
function checkNullOREmpty(val,msg){
	if(val == '' || val == null){
		jQuery.messager.alert("提示",msg+'不能为空');
		return false;
	}
	return true;
}

//添加对numberbox点击时清空的操作
jQuery(function(){
	jQuery('.easyui-numberbox').click(function(){
		var tempValue = jQuery(this).numberbox('getValue');
		jQuery(this).select();
		jQuery(this).blur(function(){
			if(jQuery(this).numberbox('getValue') == ''){
				jQuery(this).numberbox('setValue',tempValue);
			}
		});
	});
	
	jQuery("input[class='textbox-text validatebox-text']").click(function(){
		var tempValue = jQuery(this).val();
		jQuery(this).select();
		jQuery(this).blur(function(){
			if(jQuery(this).val() == ''){
				jQuery(this).val(tempValue);
		}});
	});
	
});

function selectText(obj){
		jQuery(obj).select();
	}

function myFunction(obj,backFunction){
		jQuery(obj).keypress(function(e){
			var currKey = 0;
	 		var e = e || event;
	  		currKey = e.keyCode || e.which || e.charCode;
	  		if (currKey == 0X000D){
	  			eval(backFunction+'();');
	  		}
		});
	}
function checkSameProduct(rowData,rows){
	if (rows.length > 0){
		for(var i=0;i<rows.length;i++){
			if(rows[i].id==rowData.id){
				return false;
			}
		}
	}
	return true;
}
function checkSameProduct1(rowData,rows){
	if (rows.length > 0){
		for(var i=0;i<rows.length;i++){
			if(rows[i].material_id==rowData.id){
				return false;
			}
		}
	}
	return true;
}
function checkSameProduct2(rowData,rows){
	if (rows.length > 0){
		for(var i=0;i<rows.length;i++){
			if(rows[i].material_id==rowData.product_id && rows[i].supplier_id==rowData.supplier_id){
				return false;
			}
		}
	}
	return true;
}
function checkSameProduct3(rowData,rows){
	if (rows.length > 0){
		for(var i=0;i<rows.length;i++){
			if(rows[i].material_id==rowData.material_id){
				return false;
			}
		}
	}
	return true;
}

function jsonToStr(o){
	return JSON.stringify(o);
//	var arr=[];
//	var fmt=function(s){
//		if(typeof s=='object'&&s!=null)
//			{
//			return JsonToStr(s);
//			}
//		return /^(string|number)$/.test(typeof s)?"'"+s+"'":s;
//	}
//	for(var i in o){
//		arr.push("'"+i+"':"+fmt(o[i]));
//	}
//	return '{'+arr.join(',')+'}';
}

function getToday(){
	var date = new Date();
	var y = date.getFullYear();
	var m = date.getMonth()+1;
	var d = date.getDate();
	return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
}
function getTodaySecond(){
	var date = new Date();
	var y = date.getFullYear();
	var m = date.getMonth()+1;
	var d = date.getDate();
	var sDate= y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
	 sDate = sDate + " " + date.getHours() + ":" + date.getMinutes() + ":" +  date.getSeconds();
	 return sDate;
}
function strTime(){
	var date = new Date();
	var y = date.getFullYear();
	var m = date.getMonth()+1;
	var d = date.getDate();
	var h = date.getHours();
	var mi = date.getMinutes();
	var s = date.getSeconds();
	var strDate=y+""+(m<10?('0'+m):m)+""+(d<10?('0'+d):d)+""+(h<10?('0'+h):h)+""+(mi<10?('0'+mi):mi)+""+(s<10?('0'+s):s);
	return  strDate;
}
//获取当天日期
function getDays(strDateStart,strDateEnd){
	   var strSeparator = "-"; //日期分隔符
	   var oDate1;
	   var oDate2;
	   var iDays;
	   oDate1= strDateStart.split(strSeparator);
	   oDate2= strDateEnd.split(strSeparator);
	   var strDateS = new Date(oDate1[0], oDate1[1]-1, oDate1[2]);
	   var strDateE = new Date(oDate2[0], oDate2[1]-1, oDate2[2]);
	   iDays = parseInt(Math.abs(strDateS - strDateE ) / 1000 / 60 / 60 /24)//把相差的毫秒数转换为天数 
	   return iDays ;
	}
