<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../_include/_taglib.jsp"%>
<%@include file="../_include/_s0.jsp"%>
<%
String error = request.getParameter("error");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${ctx}/assets/bootstrap/css/dataIndex/bootstrap.min.css" rel="stylesheet">
<link href="${ctx}/assets/bootstrap/css/dataIndex/byit.css" rel="stylesheet">
<script type="text/javascript" src="${ctx}/assets/_osdp/js/dataIndex/queryDataIndex.js"></script>
<title>数据指标</title>
<style>
.file_up {
	margin: 0 auto;
	margin-top: 20px;
}

.file_up p {
	width: 100%;
	height: 40px;
	line-height: 40px;
	text-align: center;
	font-size: 15px;
}

.form-control:focus {
	border: 1px solid #78CD51;
}
</style>
</head>
<body class='table_css table_css_tree'>
	<input type="hidden" id="error" value="<%=error%>">
	<a href="${ctx}/index_manager/downloadErrorFile" id="errorA" style="display: none;">ssss</a>
	<header class="panel-heading"> <span class="label label-primary new_label">数据指标</span> <span class="tools pull-right"> </span> </header>
	<div class="panel-body">
		<div class="adv-table editable-table ">
			<div class="clearfix">
				<div class="btn-group text-right float-right" style=''>
					<div class='float-rightd'>
						<input type="text" id="fileName" class="form-control" placeholder="文件名称">
					</div>
					<div class='float-rightd'>
						<input type="text" id="uploadPeople" class="form-control" placeholder="上传人">
					</div>
					<button id="query" class="btn btn-info green" id="query" onclick="EL.query()">查询</button>
					<input type="button" id="clear" class="btn btn-default green" value="清空" onclick="clear()">
				</div>
				<div class="btn-group">
					<button id="suppBatchUpload" class="btn green btn_gray new_add">
						上传 <i class="fa fa-plus"></i>
					</button>
				</div>
			</div>
		</div>
	</div>
	<div class="space15"></div>
	<div class='table_cell' style="height: 83%">
		<table class="table table-striped table-hover table-bordered table-table1" id="dataIndex"></table>
	</div>

	<script type="text/javascript">
		URLS.upLoadWindow = _ctx + '/index_manager/fileUpload.html';
		URLS.querydata = _ctx + "/index_manager/queryDataIndex.do";
		EL.uploadButton = $("#suppBatchUpload");
		EL.queryButton = $("#query");
		EL.clearButton = $("#clear");
		EL.clearButton.on("click", function() {
			document.getElementById("fileName").value = "";
			document.getElementById("uploadPeople").value = "";
		});
		EL.uploadButton.on("click", function() {
			GV.uploadWindow = layer.open({
				type : 2,
				title : '文件上传',
				shade : 0.3,
				offset : [ '30%' ],
				area : [ '440px', '210px' ],
				content : URLS.upLoadWindow,
			});

		});
	</script>
</body>
</html>