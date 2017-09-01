<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="byit.osdp.base.security.UserHolder" %>
<%@include file="../_include/_taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<%@include file="../_include/_meta.jsp"%>
<%@include file="../_include/_s0.jsp"%>
<script type="text/javascript" src="${ctx}/assets/_osdp/js/log/queryAdminLog.js"></script>
<%-- 
<script type="text/javascript" src="${ctx}/js/date/My97DatePicker/WdatePicker.js"></script> --%>
<link rel="stylesheet" href="${ctx}/assets/bootstrap/css/easyui_change2.css">
<link rel="stylesheet" href="${ctx}/assets/bootstrap/plugin/bootstrap-select/bootstrap-select.min.css">
<script src="${ctx}/assets/bootstrap/js/advanced-form-components.js" ></script>
<script src="${ctx}/assets/_osdp/js/logAnalysis/echarts.min.js"></script>
<script src="${ctx}/assets/bootstrap/plugin/bootstrap-select/bootstrap-select.js"></script>

	<link rel="stylesheet" href="${ctx}/assets/workBook/select2/4.0.0/css/select2.min.css" />
	<script src="${ctx}/assets/workBook/select2/4.0.0/js/select2.min.js"></script>
<style type="text/css">
	.panel-body{border:none;}
	body,html{width:100%;height:100%;}
	.btn-info {
	    background-color: #58c9f3;
	    border-color: #58c9f3;
	    color: #FFFFFF;
	}
	.btn-group>.btn:last-child:not(:first-child), .btn-group>.dropdown-toggle:not(:first-child) {
	    border-radius: 4px;
	    margin-right: 10px;
	}
	.btn-info:hover {
	    background-color: #53bee6;
	    border: 1px solid #53bee6;
	}
</style>
<script type="text/javascript">
$(function(){
	URLS.queryAuthOrg = _ctx + '/logss/queryAuthOrg.do';
	URLS.queryAuthUser = _ctx + '/logss/queryAuthUser.do';
	URLS.queryOpercontent = _ctx + '/logss/queryOpercontent.do';
	//查询所有机构信息并绑定到站点下拉框
	$.post(URLS.queryAuthOrg,null,function(records){
		var html = '<option></option>';
		$.each(records, function(i, record) {
			html += '<option value="' + record + '" >' + record + '</option>';
		});
		$("#authOrg").html(html);
	});
	//设置机构下拉框改变事件
	$("#authOrg").select2({
		placeholder : '机构名称',
		minimumResultsForSearch : 0,
		allowClear : true
	}).change(function(e){
		//获取用户信息
		queryAuthUser($('#authOrg').val());
	});
	//获取用户信息
	queryAuthUser(null);
	//设置用户下拉框改变事件
	$("#userName").select2({
		placeholder : '用户名称',
		minimumResultsForSearch : 0,
		allowClear : true
	}).change(function(e){
		
	});
	//查询所有菜单信息并绑定到站点下拉框
	$.post(URLS.queryOpercontent,null,function(records){
		var html = '<option></option>';
		$.each(records, function(i, record) {
			html += '<option value="' + record + '" >' + record + '</option>';
		});
		$("#menu").html(html);
	});
	//设置菜单下拉框改变事件
	$("#menu").select2({
		placeholder : '报表名称',
		minimumResultsForSearch : 0,
		allowClear : true
	}).change(function(e){
		
	});
	
});
function queryAuthUser(authOrg){
	//查询所有用户信息并绑定到站点下拉框
	$.post(URLS.queryAuthUser,{authOrg:authOrg},function(records){
		var html = '<option></option>';
		$.each(records, function(i, record) {
			html += '<option value="' + record + '" >' + record + '</option>';
		});
		$("#userName").html(html);
	});
}
/*
 * 查询
 */
function searchs(){
	//用户id
	var username = $('#userName').val();
	//机构
	var authOrg = $('#authOrg').val();
	//菜单名称
	var menu =  $('#menu').val();
	//开始时间
	var beginlogdate =  $('#beginlogdate').val();
	//结束时间
	var endlogdate =  $('#endlogdate').val();
	EL.questionGrid.datagrid('reload',{
		filters : __.encode({
			'username' : username,
			'menu' : menu,
			'beginlogdate' : beginlogdate,
			'endlogdate' : endlogdate,
			'authOrg' : authOrg
		})
	});
}
</script>
</head>
<body class='table_css'>
  <section class="panel">
    <div class="panel-body">
       <div class="adv-table editable-table ">
          <div class="clearfix" id="questionPanel">
            <div class="btn-group float-right" style=''>
                <div class='float-rightd'>
                	<select id="authOrg" style="width: 179px; height: 34px;" class="iptname"></select>
                  </div>
                  <div class='float-rightd'>
                  	  <select id="userName" style="width: 179px; height: 34px;" class="iptname"></select>
                      <!-- <input onchange="searchs()" type="text" class="form-control form-control_new" id="username" name="username" placeholder="用户"  onkeypress="queryScheduleForEnter(event)"> --> 
                  </div>
                  <div class='float-rightd'>
                  	  <select id="menu" style="width: 179px; height: 34px;" class="iptname"></select>
                      <!-- <input onchange="searchs()" type="text" class="form-control form-control_new" id="menu" name="menu" placeholder="菜单名称" onkeypress="queryScheduleForEnter(event)"> --> 
                  </div>
                  <!-- 
                  <span>日期 : </span>
								<div data-date-viewmode="date" data-date-format="yyyy-mm-dd" data-date="2016-12-29" class="input-append date dpYears" style="display:inline-block;width:160px;">
		                          <input id="date1" class="form-control form-control-inline input-medium default-date-picker" size="16" type="text" value="2016-12-29">
		                        </div>
								<div data-date-viewmode="date" data-date-format="yyyy-mm-dd" data-date="2016-12-29" class="input-append date dpYears" style="display:inline-block;width:160px;">
		                          <input id="date2" class="form-control form-control-inline input-medium default-date-picker" size="16" type="text" value="2016-12-29">
		                        </div>
                   -->
                  
                  <div class='float-rightd' data-date-viewmode="date" data-date-format="yyyy-mm-dd" data-date="2016-12-29" class="input-append date dpYears">
                  
                    <input onchange="searchs()" type="text" placeholder="开始时间" id="beginlogdate" name="beginlogdate"  class="form-control form-control_new default-date-picker" />
                  </div>
                  <div class='float-rightd' >
                  -
                  </div>
                  <div class='float-rightd' data-date-viewmode="date" data-date-format="yyyy-mm-dd" data-date="2016-12-29" class="input-append date dpYears">
                  <input onchange="searchs()" type="text" placeholder="结束时间"  id="endlogdate" name="endlogdate" class="form-control form-control_new default-date-picker"/>
                  </div>
                  <button class="btn btn-info green" id="searchBtn" onclick="searchs()" style="background-color: #58c9f3; border-color: #58c9f3; color: #FFFFFF;">
                  		  搜索
                  </button>
                   <button class="btn btn-default green" id="clearBtn" onclick="empty()">
                   		清空
                  </button>
             </div>
          </div>
          
          <div class="space15" style="padding-top: 20px;"></div>
          <div class='table_cell'>
	          <table class="table table-striped table-hover table-bordered table-table1" id="table_log">
	          </table>
	      </div>
       </div>
    </div>
 </section>
 <script>
 $('.table_cell').height($('body').height()-130);
 </script>
</body>
</html>