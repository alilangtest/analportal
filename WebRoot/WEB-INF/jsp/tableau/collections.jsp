<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="byit.osdp.base.security.UserHolder"%>
<%@include file="../_include/_taglib.jsp"%>
<%
	String from = request.getParameter("from");
	String useid=UserHolder.getId();
%>
<!DOCTYPE html>
<html lang="zh">
<head>
<%@include file="../_include/_meta.jsp"%>
<%@include file="../_include/_s0.jsp"%>
<script type="text/javascript" src="${ctx}/assets/_osdp/js/tableau/tableauServer.js"></script>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css"><!-- SMOOTHNESS CSS-->
<link href="${ctx}/assets/bootstrap/css/tasks.css" rel="stylesheet"><!-- TASK CSS-->
<link href="${ctx}/assets/bootstrap/css/style.css" rel="stylesheet"><!-- THEME BASIC CSS -->
<link href="${ctx}/assets/bootstrap/css/style-responsive.css" rel="stylesheet"><!-- THEME BASIC RESPONSIVE  CSS -->
	<style>
		body{
			background-color: #fff;
		}
		.panel-heading{
			border-top:none;
		}
		.section_box{
			padding:5px 15px;
		}
		.hidden-phone button.btn{margin-right:5px;}
	</style>
<style type="text/css">
</style>
<script type="text/javascript">
//通知信息列表
$(function() {
	$('#ttt').empty();
	URLS = _ctx + '/tableauserver/countCollections.do';
	$.getJSON(URLS,
		function(result) {
			var listCollects = result.rows;
			if(result.changdu>0){
				var j = result.changdu;
				for(var i = 0;i < j;i++){
					if(i==0){
						$('#ttt').append('<li><div class="task-checkbox"><i>'+(i+1)+'</i></div><div class="task-title"><span class="task-title-sp">'+listCollects[i].REPORTNAME+'</span><div class="pull-right hidden-phone"><button class="btn btn-danger btn-xs" title="删除" onclick="collectDelete(\''+ listCollects[i].REPORTID +'\',\''+listCollects[i].EMPLOYEEID+'\')"><i class="fa fa-trash-o"></i></button></div></div></li>');
					}else{
						$('#ttt').append('<li><div class="task-checkbox"><i>'+(i+1)+'</i></div><div class="task-title"><span class="task-title-sp">'+listCollects[i].REPORTNAME+'</span><div class="pull-right hidden-phone"><button class="btn btn-success btn-xs"  onclick="collectToTop(\''+ listCollects[i].REPORTID +'\',\''+listCollects[i].EMPLOYEEID+'\')"><i class="fa fa-upload"></i></button><button class="btn btn-danger btn-xs"  onclick="collectDelete(\''+ listCollects[i].REPORTID +'\',\''+listCollects[i].EMPLOYEEID+'\')"><i class="fa fa-trash-o"></i></button></div></div></li>');
					}
				}
			}
		}
	)
});
//取消收藏
function collectDelete(reportid,employeeid){
	var postUrl = _ctx + '/tableauserver/collectDelete.do';			
	$.post(postUrl,{"reportid":reportid,"employeeid":employeeid},function(result){
		if (result.success){
			$('#ttt').empty();
			URLS = _ctx + '/tableauserver/countCollections.do';
			$.getJSON(URLS,
				function(result) {
					var listCollects = result.rows;
					if(result.changdu>0){
						var j = result.changdu;
						for(var i = 0;i < j;i++){
							if(i==0){
								$('#ttt').append('<li><div class="task-checkbox"><i>'+(i+1)+'</i></div><div class="task-title"><span class="task-title-sp">'+listCollects[i].REPORTNAME+'</span><div class="pull-right hidden-phone"><button class="btn btn-danger btn-xs" title="删除" onclick="collectDelete(\''+ listCollects[i].REPORTID +'\',\''+listCollects[i].EMPLOYEEID+'\')"><i class="fa fa-trash-o"></i></button></div></div></li>');
							}else{
								$('#ttt').append('<li><div class="task-checkbox"><i>'+(i+1)+'</i></div><div class="task-title"><span class="task-title-sp">'+listCollects[i].REPORTNAME+'</span><div class="pull-right hidden-phone"><button class="btn btn-success btn-xs"  onclick="collectToTop(\''+ listCollects[i].REPORTID +'\',\''+listCollects[i].EMPLOYEEID+'\')"><i class="fa fa-upload"></i></button><button class="btn btn-danger btn-xs"  onclick="collectDelete(\''+ listCollects[i].REPORTID +'\',\''+listCollects[i].EMPLOYEEID+'\')"><i class="fa fa-trash-o"></i></button></div></div></li>');
							}
						}
					}
				}
			)
		} else {
			layer.msg('取消该报表收藏失败', {
				msg : [ '#BB0000' ],
				shift : 5,
				time : 1500
			});
			return;
		}
	},'json');	
}
//置顶操作
function collectToTop(reportid,employeeid){
	var postUrl = _ctx + '/tableauserver/collectToTop.do';
	$.post(postUrl,{"reportid" : reportid,"employeeid" :employeeid},function(result){
		if (result.success){
			$('#ttt').empty();
			URLS = _ctx + '/tableauserver/countCollections.do';
			$.getJSON(URLS,
				function(result) {
					var listCollects = result.rows;
					if(result.changdu>0){
						var j = result.changdu;
						for(var i = 0;i < j;i++){
							if(i==0){
								$('#ttt').append('<li><div class="task-checkbox"><i>'+(i+1)+'</i></div><div class="task-title"><span class="task-title-sp">'+listCollects[i].REPORTNAME+'</span><div class="pull-right hidden-phone"><button class="btn btn-danger btn-xs" title="删除" onclick="collectDelete(\''+ listCollects[i].REPORTID +'\',\''+listCollects[i].EMPLOYEEID+'\')"><i class="fa fa-trash-o"></i></button></div></div></li>');
							}else{
								$('#ttt').append('<li><div class="task-checkbox"><i>'+(i+1)+'</i></div><div class="task-title"><span class="task-title-sp">'+listCollects[i].REPORTNAME+'</span><div class="pull-right hidden-phone"><button class="btn btn-success btn-xs"  onclick="collectToTop(\''+ listCollects[i].REPORTID +'\',\''+listCollects[i].EMPLOYEEID+'\')"><i class="fa fa-upload"></i></button><button class="btn btn-danger btn-xs"  onclick="collectDelete(\''+ listCollects[i].REPORTID +'\',\''+listCollects[i].EMPLOYEEID+'\')"><i class="fa fa-trash-o"></i></button></div></div></li>');
							}
						}
					}
				}
			)
		} else {
			layer.msg('置顶该报表收藏失败', {
				msg : [ '#BB0000' ],
				shift : 5,
				time : 1500
			});
			return;
		}
	},'json');
}
</script>
</head>
<body>
	<section class="panel tasks-widget section_box">
		<header class="panel-heading"> 收藏列表 </header>
		<div class="panel-body">
			<div class="task-content">
				<ul id = "ttt" class="task-list">
				<!-- 
					<li>
						<div class="task-checkbox">
							<i>1、</i>
						</div>
						<div class="task-title">
							<span class="task-title-sp"> 不论是高校还是进入职场,都免不了要经历自我介绍的环节。 </span>
							<span class="badge badge-sm label-success"> New </span>
							<div class="pull-right hidden-phone">
								<button class="btn btn-success btn-xs" title='置顶'>
									<i class=" fa fa-upload"> </i>
								</button>
								<button class="btn btn-danger btn-xs" title='删除'>
									<i class="fa fa-trash-o "> </i>
								</button>
							</div>
						</div>
					</li>
					 -->
					
					
				</ul>
			</div>
		</div>
	</section>
</body>
</html>