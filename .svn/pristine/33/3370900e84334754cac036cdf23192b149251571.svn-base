<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../_include/_taglib.jsp"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%@include file="../_include/_meta.jsp"%>
<style type="text/css">
.x-from .divInput{
    border:1px solid #d2d2d2;
    width:415px;
    height:109px;
    margin-left:5px;
    overflow:auto;
    
}
.x-from td button{
   float:left;
   margin-left: 7px;
}
.x-from .divInput input {
    height: 14px;
    width: 20px;
}
.model_div table tr td input[type='number'] {
    width: 55px;
    height: 28px;
    text-indent: 5px;
    font-size: 14px;
    color: #666;
    border: 1px solid #d2d2d2;
    margin: 5px 5px 5px 0px;
}
.model_div table tr td p {
    font-size: 14px;
    color: #666;
}
.model_div table tr td input[type='radio'] {
    margin-right: 5px;
    margin-left: 5px;
    width:13px;
    height:13px;
}
.model_div table tr td select {
 	margin: 5px 5px 5px 0px;
    width:80px;
}
.model_div .tanc_c_center ul li{
	display:inline-block;
}
.iptname {
	width: 247px;
    height: 32px;
    line-height: 32px;
    border: 1px solid #ccc;
    border-radius: 3px;
    text-indent: 4px;
    margin-left: 6px;
}
#lables img{
	heigth:16px;
}
#redSpan{
	color:red;
	width: 0px;
	min-width: 0px;
}
</style>
<link rel="stylesheet" href="${ctx}/assets/bootstrap/css/byit.css">
<link rel="stylesheet" href="${ctx}/assets/bootstrap/css/layui.css">
<link rel="stylesheet" href="${ctx}/assets/workBook/jquery-ui-1.11.4.custom/jquery-ui.css" />
<script type="text/javascript">;var _ctx = '${ctx}',EL=EL||{},FN=FN||{},GV=GV||{},URLS=URLS||{};</script>
<script src="${ctx}/assets/json2/json2.js"></script>
<script src="${ctx}/assets/jquery/1.11.1/jquery.min.js"></script>
<script src="${ctx}/assets/jquery/patch/jquery-patch-1244.js"></script>
<script src="${ctx}/assets/easyui/1.4.5/jquery.easyui.min.js"></script>
<script src="${ctx}/assets/easyui/1.4.5/locale/easyui-lang-zh_CN.js"></script>
<script src="${ctx}/assets/easyui/patch/easyui-patch.js"></script>

<script src="${ctx}/assets/bootstrap/js/bootstrap.min.js"></script>
<script src="${ctx}/assets/bootstrap/plugin/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
<script src="${ctx}/assets/bootstrap/plugin/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"></script>

<script src="${ctx}/assets/layer/1.9.3/layer.js"></script>
<script src="${ctx}/assets/laydate/1.1/laydate.js"></script>

<link rel="stylesheet" type="text/css" href="${ctx}/assets/easyui/1.4.5/themes/bootstrap/easyui.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/assets/easyui/1.4.5/themes/icon.css" />

<link rel="stylesheet" type="text/css" href="${ctx}/assets/bootstrap/plugin/data-tables/DT_bootstrap.css">
<link rel="stylesheet" type="text/css" href="${ctx}/assets/bootstrap/css/animations.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/assets/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/assets/bootstrap/css/bootstrap-reset.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/assets/bootstrap/css/loading.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/assets/bootstrap/css/byit.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/assets/layer/1.9.3/skin/layer.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/assets/jquery/patch/jquery-patch.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/assets/bootstrap/plugin/font-awesome/css/font-awesome.css" />
<script src="${ctx}/assets/workBook/select2/4.0.0/js/select2.min.js"></script>
<script src='${ctx}/assets/bootstrap/js/jquery-1.11.1.min.js'></script>
<script src="${ctx}/assets/workBook/jquery-ui-1.11.4.custom/jquery-ui.js"></script>
<script src="${ctx}/assets/layer/layer.js"></script>
<script src="${ctx}/assets/bootstrap/js/layui.all.js"></script>
</head>
<body class='model_input2'>

	<div style="margin-top: 10px;padding:15px;" class='model_div'>

		<form id="oform" class="x-from">
			<input type = "hidden" id = "id" name="id"/>
			<input type = "hidden" id = "siteId" name="siteId"/>
			
			<input type = "hidden" id = "refreshFreq" />
			<input type = "hidden" id = "refreshTime" />
			<input type = "hidden" id = "dispatchState" />
			<table>
				<tr>
					<td class='hop'><lable>工作簿名称</lable></td>
					<td ><input id="workbookName" class="iptname" type="text" name="name" value="" readonly="readonly" style='margin-left:0;width:255px;'></td>
				</tr>
				<tr>
					<td class='hop'><lable>刷新时间<span id="redSpan">*</span></lable></td>
					<td>
					 <div class="tanc_c_center">
						<ul >
							<li id="yearLi">
								<input type="number" value='' id="year" class="a" >
								<span>年</span>
							</li>
							<li id="monthLi">
								<input type="number" max="12" min="1" value='' id="month" >
								<span>月</span>
							</li>
							<li id="weekLi" style="display: none;">
								<select name="week" id="week">
									<option value="1">星期日</option>
									<option value="2">星期一</option>
									<option value="3">星期二</option>
									<option value="4">星期三</option>
									<option value="5">星期四</option>
									<option value="6">星期五</option>
									<option value="7">星期六</option>
								</select>
							</li>
							<li id="dayLi">
								<input type="number" max="31" main="0" value='' id="day" >
								<span>日</span>
							</li>
							<li id="hourLi">
								<input type="number" max="23" main="0" value='' id="hour" >
								<span>时</span>
							</li>
							<li id="minuteLi">
								<input type="number" max="59" min="0" value='' id="minute" >
								<span>分</span>
							</li>
						</ul>
					</div> 
					
					</td>
				</tr>
				<tr>
					<td class='hop'><lable>刷新频率</lable></td>
					<td>
					<p><input type="radio" checked="checked"  name="refreshFreq" value="2"  >每天</p>
					<p><input type="radio"  name="refreshFreq" value="3"  >每周</p>
					<p><input type="radio"  name="refreshFreq" value="4"   >每月</p>
					<p><input type="radio"  name="refreshFreq" value="1"  >仅一次</td>
				</tr>
				<tr>
					<td class='hop'><lable>调度状态</lable></td>
					<td>
					<p><input type="radio"  name="dispatchState" value="1"   >启动<input type="radio"  name="dispatchState" value="0" checked="checked" >停止</p>
				</tr>				
			</table>
	
			<div class="x-form-buttons" style="text-align: center;">
				<button type="button" id="apply" class='btn btn-info green'>确定</button>
				<button type="button" id="cancel" class="btn btn-default green">取消</button>
			</div>
	
		</form>
	</div>

	<script type="text/javascript">
		var scopeUser = new Array();
	 	var tablesData = new Array();
		function init(){
			URLS.confirm = _ctx + "/work-book/confirm.do";
			EL.refreshFreq=$("input[name='refreshFreq']");
			
			$("input[name='dispatchState'][value='"+$("#dispatchState").val()+"']").attr("checked","checked"); 
			$("input[name='refreshFreq'][value='"+$("#refreshFreq").val()+"']").attr("checked","checked"); 
			//初始化日期赋值
			var d = new Date();
			$("#year").val(d.getFullYear());
			$("#month").val(d.getMonth() + 1);
			$("#day").val(d.getDate());
			$("#week").val(d.getDay() + 1);
			$("#hour").val(d.getHours());
			$("#minute").val(d.getMinutes());
			$("#yearLi").val('');
			$("#monthLi").val('');
			$("#weekLi").val('');
			$("#dayLi").val('');
			$("#hourLi").val('');
			$("#minuteLi").val('');
			
			changeSendTime();			
			if($("#refreshFreq").val() != null && $("#refreshFreq").val() !=0 && $("#refreshFreq").val() != "0"){
				setSendTime($("#refreshFreq").val(), $("#refreshTime").val());
			}

			$("#apply").click(function(){
				//获取选中刷新频率
				var sendType = $('input:radio[name="refreshFreq"]:checked').val();
				//获取选中调度状态
				var dispatchState = $('input:radio[name="dispatchState"]:checked').val();	
					var year = $("#year").val();
					//发送时间
					if (year != "") {
						if (year<=0 || year>9999) {
							layer.alert("请输入有效的年份！");
							return;
						}
					}
					var month = $("#month").val();
					if (month != "") {
						if (month<=0 || month>12) {
							layer.alert("请输入有效的月份!");
							return;
						}
					}
					var day = $("#day").val();
					if (day != "") {
						//根据当前日期获取月份的天数
						var date = new Date(year, month, 0);
						var dayMonth = date.getMonth();
						var daycount = date.getDate();
						//alert(month+"月有"+daycount+"天");
						if (day<=0 || day>daycount) {
							layer.alert(month + "月共有" + daycount + "天，请输入有效的天!");
							return;
						}
					}
					var hour = $("#hour").val();
					if (hour != "") {
						if (hour<0 || hour>23) {
							layer.alert("请输入有效的小时!");
							return;
						}
					}
					var minute = $("#minute").val();
					if (minute != "") {
						if (minute<0 || minute>60) {
							layer.alert("请输入有效的分钟!");
							return;
						}
					}

					var year = $("#year").val() == "" ? "" : $("#year").val() + "-";
					var month = $("#month").val() == "" ? "" : $("#month").val() + "-";
					var day = $("#day").val() == "" ? "" : $("#day").val() + " ";
					var week = $("#week").val() == "" ? "" : $("#week").val() + "_";
					var hour = $("#hour").val() == "" ? "" : $("#hour").val() + ":";
					var minute = $("#minute").val();
					var sendTime = year + "" + month + "" + day + "" + hour + ""
							+ minute;
					//判断刷新频率
					if (sendType == "2") {
						if (hour == "" || minute == "") {
							layer.alert("请输入有效的时间!");
							return;
						}
						sendTime = hour + minute;
					} else if (sendType == "3") {
						if (week == "" || hour == "" || minute == "") {
							layer.alert("请输入有效的时间!");
							return;
						}
						sendTime = week + hour + minute;
					} else if (sendType == "4") {
						if (day == "" || hour == "" || minute == "") {
							layer.alert("请输入有效的时间!");
							return;
						}
						sendTime = day + "" + hour + "" + minute;
					} else if (sendType == "5") {
						if (month == "" || day == "" || hour == "" || minute == "") {
							layer.alert("请输入有效的时间!");
							return;
						}
						sendTime = month + "" + day + "" + hour + "" + minute;
					}
				$.ajax({
					type : "post",
					dataType : "json",
					url : URLS.confirm,
					data : {
						id:$("#id").val(),
						refreshTime : sendTime,
						refreshFreq : sendType,
						dispatchState : dispatchState
					},
					success : function(data) {
						if(data == true){
							layer.alert("操作成功！",
							function() {
								var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
								parent.layer.close(index);
								parent.pagedQuery(parent.pageParam.currPage);
							});
						}else{
							layer.alert("操作失败！");
						}
					},
					error : function(data) {
						layer.alert("操作失败！");
					}
				});	
			});
			

			$("#cancel").click(function(){
				
				var index = parent.layer.getFrameIndex(window.name);
				parent.layer.close(index);
			});
			EL.refreshFreq.on("change", function(){
				changeSendTime();
			});
		
		}
		$(function() {});
		
		/* 规则不同显示的时间格式不同
		 * 发送时间改变
		 */
		function changeSendTime() {
			$("#yearLi").val('');
			$("#monthLi").val('');
			$("#weekLi").val('');
			$("#dayLi").val('');
			$("#hourLi").val('');
			$("#minuteLi").val('');
			var type = $('input:radio[name="refreshFreq"]:checked').val();
			switch (parseInt(type)) {
			case 2:
				$("#yearLi").hide();
				$("#monthLi").hide();
				$("#dayLi").hide();
				$("#hourLi").show();
				$("#minuteLi").show();
				$("#weekLi").hide();
				break;
			case 3:
				$("#yearLi").hide();
				$("#monthLi").hide();
				$("#weekLi").show();
				$("#dayLi").hide();
				$("#hourLi").show();
				$("#minuteLi").show();
				break;
			case 4:
				$("#yearLi").hide();
				$("#monthLi").hide();
				$("#dayLi").show();
				$("#hourLi").show();
				$("#minuteLi").show();
				$("#weekLi").hide();
				break;
			case 1:
				$("#yearLi").show();
				$("#monthLi").show();
				$("#dayLi").show();
				$("#hourLi").show();
				$("#minuteLi").show();
				$("#weekLi").hide();
				break;
			case 5:
				$("#yearLi").hide();
				$("#monthLi").show();
				$("#dayLi").show();
				$("#hourLi").show();
				$("#minuteLi").show();
				$("#weekLi").hide();
				break;
			}
		}
	function setSendTime(sendType,sendTime){
		//发送时间初始化
		changeSendTime();
		if(parseInt(sendType)==2){
			//每日发送
			var times1 = sendTime.split(":");
			$("#hour").val(times1[0]);
			$("#minute").val(times1[1]);
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
					$("#week").val(wk[0]);
					$("#hour").val(wk[1]);
				}else{
					$("#hour").val(hm[0]);
				}
				$("#minute").val(hm[1]);
				break;
			case 1:
				$("#hour").val(hm[0]);
				$("#minute").val(hm[1]);
				break;
			}
		}
	}	
	</script>
</body>
</html>
