//var rootPath = getRootPath();
var pageSize =13;
var reportIdValue = new Array();
var tableIdValue = new Array();
var tableIdDelValue = new Array();
var updateReportIds="";
var updateTableIds="";
var tabId="";
var sureTabId="";
//var sureTableIds="";
var sureTableIds= new Array();
var oldColumn = "";
var oldTabId = "";
var delExcelColumn = "";
var oldDelColumn = new Array();
var addDates = new Array();
var tabIdExcelDelColumn = new Array();
var count =0 ;
var tempExcelColIsCkecked= new Array();
/** 获取系统根路径
 */
/**
 * 页面初始化
 * 
 */
$(document).ready(function(){
	$("#searchValue").val('');
	var tanchuceng = $('.tanchuang');
	var tanchuceng1 = $('.tanchuang0');
	var tanchuceng2 = $('.tanchuang2');
	var tanchuceng3 = $('.tanchuang3');
	var tanchuceng4 = $('.tanchuang4');
	var tanchuceng5 = $('.tanchuang5');
	var tanchuceng6 = $('.tanchuang6');//选择发送附件还是正文
	$('.closeBtn').click(function(){
		tanchuceng.stop(true).fadeOut(400);
		allClose();
	});

	$('#button_add').click(function(){
		clearTab();
		tanchuceng.stop(true).fadeIn(400);
		tanchuceng1.stop(true).fadeIn(400);
		innitAddPage();
	});
	
	
	$('#next_1').click(function(){
		allClose();
		tanchuceng2.stop(true).fadeIn(400);
	});
	$('#prev_1').click(function(){
		allClose();
		tanchuceng1.stop(true).fadeIn(400);
	});
	$('#next_2').click(function(){
		var type = $("#sendTimeSelect").val();
		var str = checkDate("", type);		
		if(str != ""){
			$(".tanc_c_bottom").html(str);
			$(".tanc_c_bottom").css("color","red");
		}else{
			allClose();
			tanchuceng3.stop(true).fadeIn(400);
		}
	});
	$('#prev_2').click(function(){
		allClose();
		tanchuceng2.stop(true).fadeIn(400);
	});
	$('#next_6').click(function(){
		allClose();
		//tanchuceng6.stop(true).fadeIn(400);
		changeSendDetails()
		$('.tanchuang6').stop(true).fadeIn(400);
	});
	$('#prev_6').click(function(){
		allClose();
		tanchuceng3.stop(true).fadeIn(400);
	});
	$('#next_3').click(function(){
		allClose();
		tanchuceng4.stop(true).fadeIn(400);
	});
	$('#prev_3').click(function(){
		allClose();
		//tanchuceng6.stop(true).fadeIn(400);
		$('.tanchuang6').stop(true).fadeIn(400);
	});
	initTableData();
	innitAddPage();
	/******excel********/
	var tanchucengExcel = $('.tanchuangExcel');
	var tanchucengExcel1 = $('.tanchuangExcel0');
	var tanchucengExcel2 = $('.tanchuangExcel2');
	var tanchucengExcel3 = $('.tanchuangExcel3');
	var tanchucengExcel4 = $('.tanchuangExcel4');
	var tanchucengExcel5 = $('.tanchuangExcel5');
	$('.closeBtnExcel').click(function(){
		tanchucengExcel.stop(true).fadeOut(400);
		allClose();
	});
	$('#button_add_excel').click(function(){
		clearTab();
		tanchucengExcel.stop(true).fadeIn(400);
		tanchucengExcel1.stop(true).fadeIn(400);
		initMultArr();
	});
	$('#nextExcel_1').click(function(){
		allClose();
		tanchucengExcel2.stop(true).fadeIn(400);
	});
	$('#prevExcel_1').click(function(){
		allClose();
		tanchucengExcel1.stop(true).fadeIn(400);
	});
	$('#nextExcel_2').click(function(){
		var type = $("#sendTimeSelectExcel").val();
		var str = checkDate("excel", type);		
		if(str != ""){
			$(".tanc_c_bottomExcel").html(str);
			$(".tanc_c_bottomExcel").css("color","red");
		}else{
			allClose();
			tanchucengExcel3.stop(true).fadeIn(400);
		}
	});
	$('#prevExcel_2').click(function(){
		allClose();
		tanchucengExcel2.stop(true).fadeIn(400);
	});
	
	$('#nextExcel_3').click(function(){
		allClose();
		tanchucengExcel4.stop(true).fadeIn(400);
	});
	$('#prevExcel_3').click(function(){
		allClose();
		$('.tanchuangExcel3').stop(true).fadeIn(400);
	});
	initTableExcelData();
	
	/******excel end********/
});

function clearTab(){
	//$("#excelTabName").val("");
	$("#updateHide").show();//显示表名输入框
	$("#screeningExcel").val("");//清空筛选条件下拉框
	$("input[name=name_input]").attr("disabled",false);
	$("#dashboard_list").empty();
	$("input[name=condition_name]").val('time'); 
	$("input[name=mailTitle]").val(''); 
	$("#xuanze_list").empty();
	var d = new Date();
	$("#year").val(d.getFullYear());
	$("#month").val(d.getMonth()+1);
	$("#day").val(d.getDate());
	$("#week").val(d.getDay()+1);
	$("#hour").val(d.getHours());
	$("#minute").val(d.getMinutes());
	$("#yearLi").val('');
	$("#monthLi").val('');
	$("#weekLi").val('');
	$("#dayLi").val('');
	$("#hourLi").val('');
	$("#minuteLi").val('');
	$(".tanc_c_bottom").empty();
	$(".mail_ul").empty();
	$("#view_dashboard_list").empty();
	$("input[name=view_condition_name]").val('');
	$("#view_xuanze_list").empty();
	$("#view_mail_sh").empty();
	$("input[name=viewSendTime]").val('');
	$("input[name=viewMailTitle]").val('');
	$("#viewsendTimeSelect").val('');
	$("#sure_close").attr("onclick","saveInfor(1)");
	$("#detailsLi").val('');//内容
	/*********************excel************************/
	$("input[name=mailTitleExcel]").val(''); 
	$("#yearExcel").val(d.getFullYear());
	$("#monthExcel").val(d.getMonth()+1);
	$("#dayExcel").val(d.getDate());
	$("#weekExcel").val(d.getDay()+1);
	$("#hourExcel").val(d.getHours());
	$("#minuteExcel").val(d.getMinutes());
	$("#yearLiExcel").val('');
	$("#monthLiExcel").val('');
	$("#weekLiExcel").val('');
	$("#dayLiExcel").val('');
	$("#hourLiExcel").val('');
	$("#minuteLiExcel").val('');
	$(".tanc_c_bottomExcel").empty();
	$(".excelTabName_ul").empty();
	$(".mailExcel_ul").empty();
	$("#view_mailExcel_sh").empty();
	$("input[name=viewSendTimeExcel]").val('');
	$("input[name=viewMailTitleExcel]").val('');
	$("#viewsendTimeSelectExcel").val('');
	$("#addDate").val("7");
	$("#selCond").val("");
	$("#sure_closeExcel").attr("onclick","saveInforExcel(1)");
	$(".columnAddExcel").empty();
	/*********************excel end************************/
}

function allClose(){
	$('.tanchuang1').stop(true).fadeOut(200);
	$('.tanchuang2').stop(true).fadeOut(200);
	$('.tanchuang3').stop(true).fadeOut(200);
	$('.tanchuang4').stop(true).fadeOut(200);
	$('.tanchuang5').stop(true).fadeOut(200);
	$('.tanchuang6').stop(true).fadeOut(200);
	/*****Excel*******/
	$('.tanchuangExcel1').stop(true).fadeOut(200);
	$('.tanchuangExcel2').stop(true).fadeOut(200);
	$('.tanchuangExcel3').stop(true).fadeOut(200);
	$('.tanchuangExcel4').stop(true).fadeOut(200);
	$('.tanchuangExcel5').stop(true).fadeOut(200);
	/*****Excel end*******/
}
function searchList(){
	$("#searchValue").val($('#searchName').val());
	initTableData();
	initTableExcelData();
}

/**
 * 获取表格后台数据
 */
/**
 * note by lisw
 * 获取首页第一个表格数据
 * 第一个表格是仪表板
 */
function initTableData(){
	/*URLS.uerySubscribeList=_ctx+"/tab/pushReport/querySubscribeList"
	$.post(URLS.uerySubscribeList,function(data) {
		createShowingTable(data);
	});*/
	$.ajax({
		url :_ctx+"/tab/pushReport/querySubscribeList.do?rows="+pageSize,
		type : "POST",
		//dataType : "json",
		data :  "searchValue="+$("#searchValue").val(),
		success : function(data) {
			// 调用创建表和填充动态填充数据的方法.
			//alert("调用创建表和填充动态填充数据的方法");
			createShowingTable(data);
		},
		error : function() {
			//alert("dataList!");
		}
	}); 
}
/**************************excel****************************/
/**
 * 获取表格后台数据
 */
/**
 * notes by lisw。
 * 获取首页第二个列表的数据。
 * 第二个列表是获取excel表格数据
 */
function initTableExcelData(){
	//alert("获取表格后台数据");
	$.ajax({
		url :_ctx+"/tab/pushExcel/querySubscribeListExcel.do?rows="+pageSize,
		type : "POST",
		//dataType : "json",
		data :  "searchValue="+$("#searchValue").val(),
		success : function(data) {
			// 调用创建表和填充动态填充数据的方法.
			//alert("调用创建表和填充动态填充数据的方法excel");
			createShowingExcelTable(data);
		},
		error : function() {
			//alert("dataList!");
		}
	}); 
}

/**************************excel end****************************/
/**
 * 工作簿
 */
/**
 * note by lisw
 * 点击“新增”按钮，查询工作簿
 */
function innitAddPage(){
	$("#name_select").attr("disabled",false);
		$.ajax({
			url :_ctx+"/tab/subscribe/allWorkbooks.do?rows="+pageSize,
			type : "POST",
			//dataType : "json",
			//data :  {},
			success : function(data) {
				if(data.success){
					// 调用创建表和填充动态填充数据的方法.
					var optionHtml = "<option value=\"\">请选择</option>";
					$.each(data.rows,function(i,row){
						//alert(data.rows+","+i+","+row);
						optionHtml+="<option value=\""+row.workbooksid+"\">"+row.workbooksname+"</option>";
					});
					$("#name_select").empty().append(optionHtml);
					$("#view_name_select").empty().append(optionHtml);
				}
			},
			error : function() {
				//alert("dataList!");
			}
		}); 
		$("#name_select").change(function(){
			innitDashboard($(this).val(),true);
			var workbookId = $("#name_select option:checked").val();
			$("#view_name_select").val(workbookId);
			
			$("#xuanze_list").empty();
			$("#view_xuanze_list").empty();
		});
}
//初始化仪表板
function innitDashboard(workbookId,flag){
	if(workbookId==""){
		$("#dashboard_list").empty();
		$("#view_dashboard_list").empty();
	}else{
		$.ajax({
			type:"post",
			//dataType : "json",
			url :  _ctx+"/tab/subscribe/getViewsByWorkBook.do",
			//传入的参数
			data : "workBookId=" + workbookId,
			async : false,
			success : function(data){
				var dashboardStr = "";
				var viewDashboard = "";
				$.each(data.rows,function(i,row){
					if(flag){
						dashboardStr+="<li><input name=\"dashboard_"+row.reportId+"\" type=\"checkbox\" onChange=\"initDataSource(this)\" onclick=\"radioHandle(this.name,this.value)\"  value=\""+row.reportId+"\"><span>"+row.reportName+"</span></li>";
					}else{
						dashboardStr+="<li><input name=\"dashboard_"+row.reportId+"\" type=\"checkbox\" disabled=\"disabled\" onclick=\"radioHandle(this.name,this.value)\" value=\""+row.reportId+"\"><span>"+row.reportName+"</span></li>";
					}
					viewDashboard+="<li><input name=\"dashboardId\" id=\"dashboard_"+row.reportId+"\" disabled=\"disabled\" type=\"checkbox\" value=\""+row.reportId+"\"><span>"+row.reportName+"</span></li>";
				});
				$("#dashboard_list").empty().append(dashboardStr);
				$("#view_dashboard_list").empty().append(viewDashboard);
			}
		});
	}
}
Array.prototype.removeByValue = function(val) {
    for(var i=0; i<this.length; i++) {
      if(this[i] == val) {
        this.splice(i, 1);
        break;
      }
    }
};
//复选框的顺序
function radioHandle(name,value){
    // --- 选中 push--
    if($("input:checkbox[name="+name+"][value="+value+"]").is(':checked')){
        	reportIdValue.push(value);
    }else{
        	reportIdValue.removeByValue(value);
    }
   // alert("****************"+reportIdValue+"****************");
}   


function initDataSource(obj){
	var workbookId = $(obj).val();//仪表板
	var isChecked = $(obj).is(":checked");
	if(isChecked){
		$("#view_dashboard_list input[id=\"dashboard_"+workbookId+"\"]").prop("checked",true);
	}else{
		$("#view_dashboard_list input[id=\"dashboard_"+workbookId+"\"]").prop("checked",false);
	}
		var dashboardLen = $("#view_dashboard_list input[name=dashboardId]:checked").length;
		$("#xuanze_list").empty();
		$("#view_xuanze_list").empty();
		if(dashboardLen>0){
			var reportIds="";
			for(var i =0;i<dashboardLen;i++){
				var val = $("#view_dashboard_list input[name=dashboardId]:checked:eq("+i+")");
				reportIds+=val.val()+",";
			}
			//选中的增加数据源
			$.ajax({
				type:"post",
				//dataType : "json",
				url :  _ctx+"/tab/subscribe/getDataResource.do",
				//传入的参数
				data : "workbookId=" + reportIds,
				async : false,
				success : function(data){//datasourceId
					var dataSourceStr = "";
					var viewDataSource = "";
					$.each(data.rows,function(i,row){
						dataSourceStr+="<li name=\"dataResource_"+workbookId+"\"><span>"+row.datasourceName+"</span><select name=\"flag_"+row.datasourceId+"\" id=\"flag_"+row.datasourceId+"\" onChange=\"changeDataSource(this)\">";
						dataSourceStr+="<option value=\"N\">可选</option><option value=\"Y\">必选</option></select></li>";
						
						viewDataSource+="<li name=\"dataResource_"+workbookId+"\"><span>"+row.datasourceName+"</span><select id=\"flag_"+row.datasourceId+"\" disabled=\"disabled\">";
						viewDataSource+="<option value=\"N\">可选</option><option value=\"Y\">必选</option></select></li>";
					});
					$("#xuanze_list").empty().append(dataSourceStr);
					$("#view_xuanze_list").empty().append(viewDataSource);
				}
			});
		}
/*	}else{
		//取消选中的移除数据源
		$("li[name=dataResource_"+workbookId+"]").remove();
		$("#view_dashboard_list input[name=dashboard_"+workbookId+"]").attr("checked",false);*/
	//}
}

function changeDataSource(obj){
	var type = $(obj).val();
	var name = $(obj).attr("name");
	$("#view_xuanze_list select[id=\""+name+"\"]").val(type);
}
//////////////////////////////////////发送时间管理//////////////////////////////
//发送时间改变
function changeSendTime(){
	$("#yearLi").val('');
	$("#monthLi").val('');
	$("#weekLi").val('');
	$("#dayLi").val('');
	$("#hourLi").val('');
	$("#minuteLi").val('');

	var type = $("#sendTimeSelect").val();
	//alert("这一步走到了");
	switch(parseInt(type)){
	case 2://每日发送
		$("#yearLi").hide();
		$("#monthLi").hide();
		$("#dayLi").hide();
		$("#hourLi").show();
		$("#minuteLi").show();
		$("#weekLi").hide();
		$("#hourSendOper").hide();
		$("#tHourSend").hide();
		break;
	case 3://每周发送
		$("#yearLi").hide();
		$("#monthLi").hide();
		$("#weekLi").show();
		$("#dayLi").hide();
		$("#hourLi").show();
		$("#minuteLi").show();
		$("#hourSendOper").hide();
		$("#tHourSend").hide();
		break;
	case 4://每月发送
		$("#yearLi").hide();
		$("#monthLi").hide();
		$("#dayLi").show();
		$("#hourLi").show();
		$("#minuteLi").show();
		$("#weekLi").hide();
		$("#hourSendOper").hide();
		$("#tHourSend").hide();
		break;
	case 1://仅发一次
		$("#yearLi").show();
		$("#monthLi").show();
		$("#dayLi").show();
		$("#hourLi").show();
		$("#minuteLi").show();
		$("#weekLi").hide();
		$("#hourSendOper").hide();
		$("#tHourSend").hide();
		break;
	case 5://每年发送
		$("#yearLi").hide();
		$("#monthLi").show();
		$("#dayLi").show();
		$("#hourLi").show();
		$("#minuteLi").show();
		$("#weekLi").hide();
		$("#hourSendOper").hide();
		$("#tHourSend").hide();
		break;
	case 6://小时发送
		$("#yearLi").hide();
		$("#monthLi").hide();
		$("#dayLi").hide();
		$("#hourLi").show();
		$("#minuteLi").show();
		$("#weekLi").hide();
		$("#hourSendOper").show();
		$("#tHourSend").show();
		/*var tHtml ="<div class=\"hoursend_b\"><ul class=\"hoursend_ul\"></ul></div>";
		$("#tHourSend").empty().append(tHtml);*/
		break;
	}
}

/***************************Excel****************************************/

/***************************Excel end****************************************/
function changeTimeHtml(){
	var type = $("#sendTimeSelect").val();
	var append = "";
	var errorStr = "";
	//星期x的提示显示（用第x天的样式）
	var appendDayx="";
	var dayx = new Array();
	var dayxlen = $("input[name=dayx]:checked").length;
	for(var i=0;i<dayxlen;i++){
		//var dayx = $("input[name=dayx]:checked:eq("+i+")").val();
		dayx[i] = $("input[name=dayx]:checked:eq("+i+")").val();
		if(dayx[i]!=null && dayx[i]!=""){
			if(dayx[i]=="1"){
				dayx[i]="日";
			}else if(dayx[i]=="2"){
				dayx[i]="一";
			}else if(dayx[i]=="3"){
				dayx[i]="二";
			}else if(dayx[i]=="4"){
				dayx[i]="三";
			}else if(dayx[i]=="5"){
				dayx[i]="四";
			}else if(dayx[i]=="6"){
				dayx[i]="五";
			}else if(dayx[i]=="7"){
				dayx[i]="六";
			}
			appendDayx+= "星期"+dayx[i]+",";
		}	
	}
	switch(parseInt(type)){
	case 2:
		var str = checkDate("", type);		
		if(str != ""){
			errorStr = str;
		}else{
			append="本邮件将在每天"+$("#hour").val()+"时"+$("#minute").val()+"分投递到对方邮箱";
		}
		break;
	/*case 3:
		append="本邮件将在每周"+$("#week").val()+" "+$("#hour").val()+"时"+$("#minute").val()+"分投递到对方邮箱";
		break;*/
	case 3:
		//append="本邮件将在每周"+$("#week").val()+" "+$("#hour").val()+"时"+$("#minute").val()+"分投递到对方邮箱";
		var str = checkDate("", type);		
		if(str != ""){
			errorStr = str;
		}else{
			append="本邮件将在："+appendDayx+""+$("#hour").val()+"时"+$("#minute").val()+"分投递到对方邮箱";
		}
		break;
	case 4:
		var str = checkDate("", type);		
		if(str != ""){
			errorStr = str;
		}else{
			append="本邮件将在每月"+$("#day").val()+"日"+$("#hour").val()+"时"+$("#minute").val()+"分投递到对方邮箱";
		}
		break;
	case 1:	
		var str = checkDate("", type);		
		if(str != ""){
			errorStr = str;
		}else{
			append="本邮件将在"+$("#year").val()+"年"+$("#month").val()+"月"+$("#day").val()+"日"+$("#hour").val()+"时"+$("#minute").val()+"分投递到对方邮箱";
		}
		break;
	case 5:
		var str = checkDate("", type);		
		if(str != ""){
			errorStr = str;
		}else{
			append="本邮件将在每年"+$("#month").val()+"月"+$("#day").val()+"日"+$("#hour").val()+"时"+$("#minute").val()+"分投递到对方邮箱";
		}
		break;
	}
	if(errorStr != ""){
		$(".tanc_c_bottom").html(errorStr);
		$(".tanc_c_bottom").css("color","red");
	}else{
		$(".tanc_c_bottom").html(append);
		$(".tanc_c_bottom").css("color","black");
	}
}
//获取指定年份月份的最后一天天数
function getYearMonthLastDay(year,month){
	var new_year = year;  //取当前的年份
	var new_month = month++;//取下一个月的第一天，方便计算（最后一天不固定）
	var new_date = new Date(new_year,new_month,1);//取当年当月中的第一天
	return (new Date(new_date.getTime()-1000*60*60*24)).getDate();//获取当月最后一天日期
}

//发送类型(附件 or 内容)
	function changeSendDetails(){
		$("#detailsLi").val('');		
		var type = $("#sendDetailsSelect").val();	
		switch(parseInt(type)){
		case 1:
			//附件 内容框显示
			$("#detailsLi").show();
			break;
		case 2:
			//内容 内容框隐藏
			$("#detailsLi").hide();	
			break;		
		}
	}

//////////////////////////////////////发送时间管理//////////////////////////////
//////////////////////////////////////邮箱管理//////////////////////////////
function addMail(){
	var mailName = $("#mailName").val();
	var regx = /^([0-9A-Za-z\-_\.]+)@([0-9a-z]+\.[a-z]{2,3}(\.[a-z]{2})?)$/;
	if(!regx.test(mailName)){
		layer.confirm("邮箱格式错误", function(){
			layer.closeAll('dialog');
		});
	}else if(mailName!=null && mailName!=""){
		
		var appendHtml="";
		appendHtml+="<li><input type=\"checkbox\" onChange=\"changeCheckedMail(this)\" value=\""+mailName+"\" name=\"mail\"><span>"+mailName+"</span></li>";
		$(".mail_ul").append(appendHtml);
		$("#mailName").val('@hfbank.com.cn');
	}
}
function delMail(){
	$("input[name=mail]:checked").parent().remove();
	var length = $("input[name=mail]:checked").length;
	$(".mail_c").html("已选联系人("+length+")");
	
}
function changeCheckedMail(obj){
	var length = $("input[name=mail]:checked").length;
	$(".mail_c").html("已选联系人("+length+")");
}
/*********excel*************/


/*function changeAddDateExcel(){
	tabId = tabId.substring(0, tabId.length-1);
	//先删除
	for(var i=0;i<addDates.length;i++){
		var addDateList = addDates[i].split("-");
		var addDateTabId = addDateList[0];
		if(addDateTabId == tabId){
			addDates.removeByValue(addDates[i]);
		}
	}
	//再更新
	var addDateExcel = $("#addDate").val();
	var addDateId ="";
	addDateId = tabId +"-"+addDateExcel;
	addDates.push(addDateId);
	addDateId ="";
}*/


//单选效果
/*function excelCheck(excelTabName,cb) {
	$("#addDate").val('');
	for ( var j = 0; j < count; j++) {
		if (eval("document.excelClick.excelTabName[" + j + "].checked") == true) {
			document.excelClick.excelTabName[j].checked = false;
		
		}
	}
	
	document.excelClick.excelTabName[cb].checked = true;
	//筛选天数
	var excelColumnList = excelTabName.split("-");
	var tableId = excelColumnList[0];
	
	for(var i=0;i<addDates.length;i++){
		var addDateList = addDates[i].split("-");
		var addDateTabId = addDateList[0];
		var getAddDate = addDateList[1];
		if(addDateTabId == tableId){
			$("#addDate").val(getAddDate);
		}
	}
	queryColumnExcel(excelTabName);
}*/

//字段点击 
function radioColumn(name,value){
    // --- 选中 push--
    if($("input:checkbox[name="+name+"][value="+value+"]").is(':checked')){
    	tableIdValue.push(value);
    }else{
    	tableIdValue.removeByValue(value);
    }
    
}  
function removeTabId(){
	//清空 取消勾选
	tableIdValue=[];
	//alert("清空了"+tableIdValue);
	$("input:checkbox[name=excelColumn]").attr("checked", false);
}


/*********excel end*************/

//////////////////////////////////////邮箱管理//////////////////////////////

//预览信息
function viewInfor(){
	var condition_name = $("input[name=condition_name]").val();
	$("input[name=view_condition_name]").val(condition_name);
	
	//星期x的提示显示（用第x天的样式）
	var appendDayx="";
	var dayx = new Array();
	var dayxlen = $("input[name=dayx]:checked").length;
	for(var i=0;i<dayxlen;i++){
		//var dayx = $("input[name=dayx]:checked:eq("+i+")").val();
		dayx[i] = $("input[name=dayx]:checked:eq("+i+")").val();
		if(dayx[i]!=null && dayx[i]!=""){
			if(dayx[i]=="1"){
				dayx[i]="日";
			}else if(dayx[i]=="2"){
				dayx[i]="一";
			}else if(dayx[i]=="3"){
				dayx[i]="二";
			}else if(dayx[i]=="4"){
				dayx[i]="三";
			}else if(dayx[i]=="5"){
				dayx[i]="四";
			}else if(dayx[i]=="6"){
				dayx[i]="五";
			}else if(dayx[i]=="7"){
				dayx[i]="六";
			}
			appendDayx+= "星期"+dayx[i]+",";
		}	
	}
	
	var len = $(".mail_ul input").length;
	var appendHtml = "";
	for(var i=0;i<len;i++){
		var val = $(".mail_ul input:eq("+i+")").val();
		appendHtml+="<p>"+val+"</p>";
	}
	$("#view_mail_sh").empty().append(appendHtml);
	
	var year = $("#year").val()==""?"":$("#year").val()+"年";
	var month = $("#month").val()==""?"":$("#month").val()+"月";
	//var week = $("#week").val()==""?"":$("#week").find("option:selected").text();
	var day = $("#day").val()==""?"":$("#day").val()+"日 ";
	var hour = $("#hour").val()==""?"":$("#hour").val()+"时";
	var minute = $("#minute").val()==""?"":$("#minute").val()+"分";
	$("input[name=viewSendTime]").val(year+""+month+""+day+""+hour+""+minute);
	
	$("input[name=viewMailTitle]").val($("input[name=mailTitle]").val());
	var type = $("#sendTimeSelect").val();
	switch(parseInt(type)){
	case 2:
		$("#viewsendTimeSelect").val("每日发送");
		break;
	/*case 3:
		$("#viewsendTimeSelect").val("每周发送");
		$("input[name=viewSendTime]").val(week +" "+hour+""+minute);
		break;*/
	case 3:
		$("#viewsendTimeSelect").val("每周："+appendDayx+"发送");
		//$("input[name=viewSendTime]").val(week +" "+hour+""+minute);
		$("input[name=viewSendTime]").val(hour+""+minute);
		break;
	case 4:
		$("#viewsendTimeSelect").val("每月发送");
		break;
	case 1:	
		$("#viewsendTimeSelect").val("仅发一次");
		break;
	case 5:
		$("#viewsendTimeSelect").val("每年发送");
		break;
	}
	//发送类型  正文信息
	var type = $("#sendDetailsSelect").val();
	switch(parseInt(type)){
	case 1:
		$("#viewSendDetailsSelect").val("发送附件");
		$("textarea[name=viewSendInfo]").val($("textarea[name=sendInfo]").val());
		break;
	case 2:
		$("#viewSendDetailsSelect").val("发送内容");
		$("textarea[name=viewSendInfo]").val("");
		break;
	}
	
	$("#view_dashboard_list").hide();
	$("#view_xuanze_list").hide();
}

function saveInfor(saveType){
	var reportId = "";
	var dataSources = [];
	var mailList = "";
	var sendHourList = "";
	//报告名称
	var workbookId = $("#name_select option:checked").val();
	//判定条件
	var condition = $("input[name=view_condition_name]").val();
	var mailTitle = $("input[name=viewMailTitle]").val();
	//仪表板
	var dashboardLen = $("#view_dashboard_list input[name=dashboardId]:checked").length;
	for(var i =0;i<dashboardLen;i++){
		var val = $("#view_dashboard_list input[name=dashboardId]:checked:eq("+i+")");
		reportId+=val.val()+",";
	}
	if(saveType==1){
		//添加
		//拆分reportIdValue组装reportIds
		var reportIds="";
		for(var i=0;i<reportIdValue.length;i++){
			reportIds=reportIds+","+reportIdValue[i];
		}
		reportIds=reportIds.substring(1, reportIds.length);
	}else if(saveType==2){
		//修改					
		reportIds=updateReportIds;		
	}				
	//数据源
	var dataSourceLen = $("#view_xuanze_list select").length;
	for(var i =0;i<dataSourceLen;i++){
		var val = $("#view_xuanze_list select:eq("+i+")").val();
		var dataSourceId = $("#view_xuanze_list select:eq("+i+")").attr("id");
		dataSourceId = dataSourceId.substring(5, dataSourceId.length);
		dataSources.push({dataSourceId:dataSourceId,value:val});
	}
	//日历
	//发送类型
	var sendType = $("#sendTimeSelect").val();
	
	//星期x发送拼接
	var appendDayx="";
	var dayxlen = $("input[name=dayx]:checked").length;
	for(var i=0;i<dayxlen;i++){
		var dayx = $("input[name=dayx]:checked:eq("+i+")").val();
		if(dayx!=null && dayx!=""){
			appendDayx+= ""+dayx+",";
		}	
	}
	
	//发送时间
	var year = $("#year").val()==""?"":$("#year").val()+"-";
	var month = $("#month").val()==""?"":$("#month").val()+"-";
	var day = $("#day").val()==""?"":$("#day").val()+" ";
	//var week = $("#week").val()==""?"":$("#week").val()+"_";
	var hour = $("#hour").val()==""?"":$("#hour").val()+":";
	var minute = $("#minute").val();
	var sendTime = year+""+month+""+day+""+hour+""+minute;
	if(sendType=="2" ){
		sendTime=hour+minute;
	}
	else if(sendType=="3"){
		//每周发送(拼接格式如：1,2,3,_18:20  意味星期天 星期一 星期二 的每天下午六点二十发送)
		sendTime=appendDayx+"_"+hour+minute;
	}else if(sendType=="4"){
		sendTime=day+""+hour+""+minute;
	}else if(sendType=="5"){
		sendTime=month+""+day+""+hour+""+minute;
	}
	if(sendType=="6" ){
		var hourSendLen = $(".hoursend_ul input").length;
		for(var i=0;i<hourSendLen;i++){
			var val = $(".hoursend_ul input:eq("+i+")").val();
			console.log("当前发送时间为："+val);
			sendHourList+=val+",";
			console.log("所有的发送时间为："+sendHourList);
		}
		sendTime=sendHourList.substring(0,sendHourList.length-1);
	}
	//邮件名称
	var mailLen = $(".mail_ul input").length;
	for(var i=0;i<mailLen;i++){
		var val = $(".mail_ul input:eq("+i+")").val();
		mailList+=val+",";
	}
	

	//发送类型  正文信息
	var sendDetails = "";
	var sendInfo = "";
	var type = $("#sendDetailsSelect").val();
	switch(parseInt(type)){
	case 1:
		sendDetails="1";
		sendInfo = $("textarea[name=sendInfo]").val();
		break;
	case 2:
		sendDetails="2";
		$("textarea[name=sendInfo]").val("");
		sendInfo = $("textarea[name=sendInfo]").val();
		break;
	}
	
	var temp="1";
	if(workbookId==""){
		temp="0";
		layer.msg("工作簿不能为空!");
	}
	else if(reportId==""){
		temp="0";
		layer.msg("仪表板不能为空!");
	}
	else if(condition==""){
		temp="0";
		layer.msg("判断条件不能为空!");
	}
	else if(sendTime==""){
		temp="0";
		layer.msg("推送时间不能为空!");
	}
	else if(mailList==""){
		temp="0";
		layer.msg("推送的邮箱不能为空!");
	}
	
	else if(sendDetails==""){
		temp="0";
		layer.msg("发送类型不能为空!");
	}
	if(temp=="1"){
		var url = "";
		if(saveType==1){
			//新增
			url = _ctx+"/tab/pushReport/addSubscribe.do";
		}
		else if(saveType==2){
			//修改
			url = _ctx+"/tab/pushReport/updateSubscribe.do";
		}
		$.ajax({
			type:"post",
			//dataType : "json",
			url :  url,
			data : {
				workbookId:workbookId,
				condition:condition,
				mailTitle:mailTitle,
				/*reportId:reportId,*/
				reportId:reportIds,
				/*reportNameValue:reportNameValue,*/
				jsonDataSources:JSON.stringify(dataSources),
				/*rili:rili,*/
				sendType:sendType,
				sendTime:sendTime,
				mails:mailList,
				sendDetails:sendDetails,
				sendInfo:sendInfo
			},
			success : function(data){
				layer.confirm(data, function(){
					layer.closeAll('dialog');
					window.location.reload();
				});
				
			},error:function(data){
				layer.confirm(data.responseText, function(){
					layer.closeAll('dialog');
					window.location.reload();
				});
				
				//alert("对不起，您的操作失败啦！");
			}
		});
		
		allClose();
		$('.tanchuang').stop(true).fadeOut(400);
	}
}


/************excel***********************************/

/*
 * 填充table
 * 不同table、动态显示列，通用
 * AurhUser 唐华博
 */
function createShowingExcelTable(data){
	
	//以下为分页代码
	var pages = data.length%5 == 0 ? data.length/5 : parseInt(data.length/5+1);
	layui.use(['laypage', 'layer'], function(){
	  var laypage = layui.laypage
	  ,layer = layui.layer;
	  laypage({
	    cont: 'page2' ,
	    pages: pages,
	    skip: true ,
	    jump: function(obj){
	     	indexExcelTable(obj.curr,data);
	    }
	  });
	});
}
/************excel end***********************************/



/*
 * 填充table
 * 不同table、动态显示列，通用
 * AurhUser 唐华博
 */
function createShowingTable(data){
	//以下为分页代码
	var pages = data.length%5 == 0 ? data.length/5 : parseInt(data.length/5+1);
	layui.use(['laypage', 'layer'], function(){
	  var laypage = layui.laypage
	  ,layer = layui.layer;
	  laypage({
	    cont: 'page1' ,
	    pages: pages,
	    skip: true ,
	    jump: function(obj){
	     	indexTable(obj.curr,data);
	    }
	  });
	});
	
	
	//获取后台传过来的jsonData,并进行解析
}
function sendMail(reportId){
	layer.confirm('确定是否发送邮件？', function(){
		layer.closeAll('dialog');
		$.ajax({
			type:"post",
			//dataType : "json",
			url :  _ctx+"/tab/pushReport/sendEmail.do",
			//传入的参数
			data : "reportId=" + reportId,
			success : function(data){
				layer.msg(data);
			}
		});
	});
}

/*******************excel end*******************************/



/**
 * 查看发送状态
 * @param reportId
 */
function viewResult(reportId){
	$('.tanchuang').stop(true).fadeIn(400);
	$('.tanchuang5').stop(true).fadeIn(400);
	$.ajax({
		type:"post",
		//dataType : "json",
		url :  _ctx+"/tab/pushReport/querySendResult.do",
		//传入的参数
		data : "reportId=" + reportId,
		success : function(data){
			var resultStr = "";
			$.each(data,function(i,row){
				resultStr+= "<ul>"+
					"<li>"+row.emailId+"</li>"+
					"<li style='color:#1CA722;'>";
				if("成功"==row.sendState){
					resultStr+="<i style='color:#1CA722;'>&#xe61e;</i>成功</li>";
				}else{
					resultStr+="<i style='color:#C82900;'>&#xe60d;</i>失败</li>";
				}
				resultStr+="<li>"+row.sendTime+"</li>"+
					"</ul>";
			});
			$("#tanchuang5_bottom").empty().append(resultStr);
		}
	});
	
}
/**
 * 删除订阅规则
 * @param reportId
 */
function deleteConfig(reportId){
	layer.confirm('确定删除订阅规则？', function(){
		layer.closeAll('dialog');
		$.ajax({
			type:"post",
			//dataType : "json",
			url :  _ctx+"/tab/pushReport/deleteSubscribe.do",
			//传入的参数
			data : "reportId=" + reportId,
			success : function(data){
				if(data){
					layer.msg("删除成功！");
					window.location.reload();
				}else{
//					alert("删除失败！");
					layer.msg("删除失败！");
				}
			}
		});
	});
	
}
/**
 * 更新订阅规则
 * @param reportId
 */
function updateConfig(reportId){
	clearTab();
	$.ajax({
		type:"post",
		//dataType : "json",
		url :  _ctx+"/tab/pushReport/querySubscribe.do",
		//传入的参数
		data : "reportId=" + reportId,
		success : function(data){
				$("#name_select").val(data.workbookId);//工作簿，只读
				$("#view_name_select").val(data.workbookId);
				$("#name_select").attr("disabled",true);
				//初始化仪表板
				innitDashboard(data.workbookId,false);
				var reportIds = data.reportId;
				if(reportIds!=null){
					var len = reportIds.split(",").length;
					for(var i=0;i<len;i++){
						var reportId = reportIds.split(",")[i];
						$("#dashboard_list input[name=dashboard_"+reportId+"]").attr("checked",true);
						//初始化数据源
						initDataSource($("#dashboard_list input[name=dashboard_"+reportId+"]")[0]);
					}
				}
				
				$("input[name=condition_name]").val(data.condition);
				$("input[name=mailTitle]").val(data.mailTitle);
				var dataSouces = data.dataSources;
				for(var key in dataSouces){
					$("#xuanze_list select[id=\"flag_"+key+"\"]").val(dataSouces[key]);
					$("#view_xuanze_list select[id=\"flag_"+key+"\"]").val(dataSouces[key]);
				}
					
				//发送时间初始化
				$("#sendTimeSelect").val(data.sendType);
				changeSendTime();
				var sendTime = data.sendTime;
				if(parseInt(data.sendType)==2){
					//每日发送
					var times1 = sendTime.split(":");
					$("#hour").val(times1[0]);
					$("#minute").val(times1[1]);
				}else if(parseInt(data.sendType)==6){
					var hourSendList=data.hourSendList;
					$.each(hourSendList,function(i,tHourSend){
						setHourSnedStyle(tHourSend);
					});
				}else{
					var times = sendTime.split(" ");
					var ymd="";
					var hm ="";
					if(times.length>1){
						//年月日
						ymd = times[0].split("-");
						switch(ymd.length){
						case 3:
							$("#year").val(ymd[0]);
							$("#month").val(ymd[1]);
							$("#day").val(ymd[2]);
							break;
						case 2:
							$("#month").val(ymd[0]);
							$("#day").val(ymd[1]);
							break;
						case 1:
							$("#day").val(ymd[0]);
							break;
						}
						//小时分
						hm = times[1].split(":");
					}else{
						//小时分
						hm = times[0].split(":");
					}
					
					switch(hm.length){
					case 2:
						var wk=hm[0].split("_");
						if(wk.length==2){
							//$("#week").val(wk[0]);
							//appendDayx.val(wk[0]);
							var xday = wk[0].split(",");
							//循环
						    for(var i=0;i<xday.length;i++){
						    	if(xday[i]==1){
						    		//$("input[name=name_input]").attr("disabled",false);
						    		//星期日
						    		$("input[value=1]:checkbox").attr("checked",true);
						    	}else if(xday[i]==2){
						    		//星期一
						    		$("input[value=2]:checkbox").attr("checked",true);
						    	}else if(xday[i]==3){
						    		//星期二
						    		$("input[value=3]:checkbox").attr("checked",true);
						    	}else if(xday[i]==4){
						    		//星期三
						    		$("input[value=4]:checkbox").attr("checked",true);
						    	}else if(xday[i]==5){
						    		//星期四
						    		$("input[value=5]:checkbox").attr("checked",true);
						    	}else if(xday[i]==6){
						    		//星期五
						    		$("input[value=6]:checkbox").attr("checked",true);
						    	}else if(xday[i]==7){
						    		//星期六
						    		$("input[value=7]:checkbox").attr("checked",true);
						    	}
						    }
							$("#hour").val(wk[1]);
						}else{
							$("#hour").val(hm[0]);
						}
						$("#minute").val(hm[1]);
						break;
					case 1:
						$("#minute").val(hm[0]);
						break;
					}
				}
				changeTimeHtml();
				//邮件初始化
				var mailList = data.mailList;
				$.each(mailList,function(i,mail){
					var appendHtml="";
					appendHtml+="<li><a title='"+mail+"'><input type=\"checkbox\" onChange=\"changeCheckedMail(this)\" value=\""+mail+"\" name=\"mail\"><span>"+mail+"</span></a></li>";
					$(".mail_ul").append(appendHtml);
				});
				
				//发送类型 正文内容
				$("#sendDetailsSelect").val(data.sendDetails);
				changeSendDetails();
				$("textarea[name=sendInfo]").val(data.sendInfo);
			
				updateReportIds = data.reportId;
				$("#sure_close").attr("onclick","saveInfor(2)");
				
				var tanchuceng = $('.tanchuang');
				var tanchuceng1 = $('.tanchuang0');
				tanchuceng.stop(true).fadeIn(400);
				tanchuceng1.stop(true).fadeIn(400);
		}
	});
}


/************************excel****************************/
/**
 * 查看发送状态
 * @param tableId
 */
function viewExcelResult(id){
	//alert(tableId);
	$('.tanchuangExcel').stop(true).fadeIn(400);
	$('.tanchuangExcel5').stop(true).fadeIn(400);
	$.ajax({
		type:"post",
		//dataType : "json",
		url :  _ctx+"/tab/pushExcel/querySendExcelResult.do",
		//传入的参数
		//data : "tableId=" + tableId,
		data : "id=" + id,
		success : function(data){
			var resultStr = "";
			$.each(data,function(i,row){
				resultStr+= "<ul>"+
					"<li>"+row.emailId+"</li>"+
					"<li style='color:#1CA722;'>";
				if("成功"==row.sendStateExcel){
					resultStr+="<i style='color:#1CA722;'>&#xe61e;</i>成功</li>";
				}else{
					resultStr+="<i style='color:#C82900;'>&#xe60d;</i>失败</li>";
				}
				resultStr+="<li>"+row.sendTimeExcel+"</li>"+
					"</ul>";
			});
			//alert(resultStr);
			$("#tanchuangExcel5_bottom").empty().append(resultStr);
		}
	});
	
}


function addHourSend(){
	//获取并拼接按小时发送的时间点
	var hour = $("#hour").val()==""?"":$("#hour").val()+":";
	var minute = $("#minute").val();
	var hourSendTime = hour+""+minute;
	
	setHourSnedStyle(hourSendTime);
	/*var appendHtml="";
	appendHtml+="<li><input type=\"checkbox\" style=\"width:30px;height:15px;vertical-align:middle\" onChange=\"changeHourSendMail(this)\" value=\""+hourSendTime+"\" name=\"pHoueSend\"><span>"+hourSendTime+"</span></li>";
	$(".hoursend_ul").append(appendHtml);*/
}

//赋值到文本域中显示
function setHourSnedStyle(hourSendTime){
	var appendHtml="";
	appendHtml+="<li><input type=\"checkbox\" style=\"width:30px;height:15px;vertical-align:middle\" " +
			"onChange=\"changeHourSendMail(this)\" value=\""+hourSendTime+"\" name=\"pHoueSend\"><span>"+
			hourSendTime+"</span></li>";
	$(".hoursend_ul").append(appendHtml);
}

function delHourSend(){
	$("input[name=pHoueSend]:checked").parent().remove();
	//var length = $("input[name=pHoueSend]:checked").length;
	//$(".mail_c").html("已选发送时间("+length+")");
	
}
/*******************excel*******************************/

/************************excel end****************************/

function clearInfo(){
	$("#searchName").val("");
}