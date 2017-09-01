//add by lisw

//发送时间校验
function checkDate(type,index){
	var year;
	var month;
	var day;
	var hour;
	var minute;
	if(type == "excel"){
		year = $("#yearExcel").val();
		month = $("#monthExcel").val();
		day = $("#dayExcel").val();
		hour = $("#hourExcel").val();
		minute = $("#minuteExcel").val();
	}else{
		year = $("#year").val();
		month = $("#month").val();
		day = $("#day").val();
		hour = $("#hour").val();
		minute = $("#minute").val();
	}
	//当前系统时间
	var d = new Date();
	//年份四位数字正则
	var yearRegx = /^(\d{4})$/;
	//月份
	var monthRegx = /^(\d{1,2})$/;
	//天
	var dayRegx = /^(\d{1,2})$/;
	//小时
	var hoursRegx = /^(\d{1,2})$/;
	//分钟
	var minutesRegx = /^(\d{1,2})$/;
	var append = "";
	switch(parseInt(index)){
		case 1:
			//仅发送一次
			if(!yearRegx.test(year)){
				append += '请输入四位数字年份！';
			}
			if(parseInt(year) < parseInt(d.getFullYear())){
				append += '输入年份不能小于当前系统年份！';
			}
			if(!monthRegx.test(month) || parseInt(month) > 12){
				append += '请输入1~12正确月份！';
			}
			if(parseInt(month) < (d.getMonth() + 1)){
				append += '输入月份不能小于当前系统月份！';
			}
			if(!dayRegx.test(day)){
				append += '请输入1~31正确天数！';
			}else{
				if(parseInt(day) > parseInt(getYearMonthLastDay(year,month))){
					append += '输入天数已大于指定月份的最大天数请修改！';
				}
			}
			if(!hoursRegx.test(hour) || parseInt(hour) < 0 || parseInt(hour) > 23){
				append += '请输入正确小时数！';
			}
			if(!minutesRegx.test(minute) || parseInt(minute) < 0 || parseInt(minute) > 59){
				append += '请输入正确分钟数！';
			}
			break;
		case 4:
			//每月
			if(!dayRegx.test(day) || parseInt(day) > 31){
				append += '请输入1~31正确天数！';
			}
			if(!hoursRegx.test(hour) || parseInt(hour) < 0 || parseInt(hour) > 23){
				append += '请输入正确小时数！';
			}
			if(!minutesRegx.test(minute) || parseInt(minute) < 0 || parseInt(minute) > 59){
				append += '请输入正确分钟数！';
			}
			break;
		case 5:
			//每年发送
			if(!monthRegx.test(month) || parseInt(month) > 12){
				append += '请输入1~12正确月份！';
			}
			if(!dayRegx.test(day)){
				append += '请输入1~31正确天数！';
			}else{
				if(parseInt(day) > parseInt(getYearMonthLastDay(d.getFullYear(),month))){
					append += '输入天数已大于指定月份的最大天数请修改！';
				}
			}
			if(!hoursRegx.test(hour) || parseInt(hour) < 0 || parseInt(hour) > 23){
				append += '请输入正确小时数！';
			}
			if(!minutesRegx.test(minute) || parseInt(minute) < 0 || parseInt(minute) > 59){
				append += '请输入正确分钟数！';
			}
			break;
		default:
			//每日、每周发送
			if(!hoursRegx.test(hour) || parseInt(hour) < 0 || parseInt(hour) > 23){
				append += '请输入正确小时数！';
			}
			if(!minutesRegx.test(minute) || parseInt(minute) < 0 || parseInt(minute) > 59){
				append += '请输入正确分钟数！';
			}
	};
	return append;
}

//获取元素指定位置
Array.prototype.indexOf = function(val) {
	for (var i = 0; i < this.length; i++) {
		if (this[i] == val)
			return i;
	}
	return -1;
};

//删除数组指定元素
Array.prototype.remove = function(val) {
	var index = this.indexOf(val);
	if (index > -1) {
		this.splice(index, 1);
	}
};