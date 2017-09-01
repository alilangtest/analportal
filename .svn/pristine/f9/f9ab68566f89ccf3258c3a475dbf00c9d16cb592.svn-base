<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../_include/_taglib.jsp"%>
<%@include file="../_include/_s0.jsp"%>
<%
String flgs = request.getParameter("error");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
html,body{
	overflow:hidden;
}
.file_up {
	margin: 0 auto;
	margin-top: 20px;
}

.file_up p {
	width: 100%;
	height: 40px;
	line-height: 20px;
	text-align: left;
	font-size: 14px;
	color : red;
}

.form-control:focus {
	border: 1px solid #78CD51;
}
.form-sty-lab{
	margin-left:20px;
	margin-right:20px;
}
</style>
</head>
<body>
	<!-- ${ctx}/dataIndex/uploaddata.do -->
	<form class="form-sty-lab" action="${ctx}/index_manager/uploaddata.do" id="anoderesistanceform" enctype="multipart/form-data" method="post" target="id_iframe" onsubmit="return openLoad();">
		<div class="file_up">
			<p id="pinfo">
				<%-- <% if(flgs!= null && flgs.equals("true")){
				%>
					※Excel数据是全量更新，上传文件时先下载上一次的Excel上传文件，再对文件进行修改。
				<%
				}else{ %>
					 ※Excel数据是全量更新，上传文件请先下载<a href="${ctx}/dataIndex/dol.do"><span style="color: red">模版</span></a>
				<%} %> --%>
			</p>
			<div class="form-group" style='text-align: center; font-size: 14px;'>
				<label for="exampleInputFile">文件上传</label>：<input type="file" accept=".xls,.xlsx" id="file" name="file" style='display: inline-block;'>
			</div>
			<div class="form-group" style='text-align: center; font-size: 14px;'>
				<input type="submit" class='btn btn-info' id="uploadSubmit" value="提交" >
				<!-- ${ctx}/dataIndex/uploaddata.do<input type="button" id="uploadSubmit" value = "提交"/> -->
			</div>
		</div>
		<iframe id="id_iframe" name="id_iframe" style="display: none;"></iframe>
	</form>
	<script type="text/javascript">
	$.post(_ctx + "/index_manager/queryDataIndexCount", null, function(result) {
		 var p =document.getElementById("pinfo");
		if(result == "true"){
			p.innerHTML = "※Excel数据是全量更新，</br>上传文件时先下载上一次的Excel上传文件，再对文件进行修改。";
		}else{
			p.innerHTML = '※Excel数据是全量更新，上传文件请先下载<a href="${ctx}/index_manager/dol.do"><span style="color: blue">模版</span></a>';
		}
	});
	function openLoad(){
		index = layer.load(0);
		return true;
	}
	var msg;
	function closes(){
		layer.closeAll();
		parent.location.href = _ctx + "/index_manager/dataIndex.html?error=false";
	}
		document.getElementById("id_iframe").onload = function() {
			var body = $(window.frames['id_iframe'].document.body);
			var data = eval('(' + body[0].textContent + ')');
			if (data && data.success) {
				layer.msg(data.msg,{icon:6,time:2000},function(index){
					layer.close(index);
					parent.location.href = _ctx + "/index_manager/dataIndex.html";
	            });
			} else if(data && !data.success) {
				if(data.path != null && data.path != ""){
					msg = layer.msg('<a href="#" onclick="closes()">'+data.msg+'</a>', {
						  time:2000*100,
						  icon: 5, 
						  btn: ['关闭']
						},function(index){
							layer.closeAll();
						});
				}else{
					layer.msg(data.msg,{icon:5,time:2000},function(index){
						layer.closeAll();
		            });
				}
			}
		};
	</script>
</body>
</html>