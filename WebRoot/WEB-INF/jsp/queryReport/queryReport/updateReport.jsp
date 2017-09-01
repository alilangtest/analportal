<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="byit.osdp.base.security.UserHolder" %>
<%@include file="../../_include/_taglib.jsp"%>

<!DOCTYPE html>
<html lang="zh">
<head>
<%@include file="../../_include/_meta.jsp"%>
<%@include file="../../_include/_s0.jsp"%>
 <script src="${ctx}/assets/bootstrap/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${ctx}/assets/_osdp/js/queryReport/queryReport/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/assets/_osdp/js/queryReport/queryReport/updateReport.js"></script>
<style type="text/css">
#redSpan{
	color:red;
	width: 0px;
	min-width: 0px;
}
</style>
</head>
<body>
	<div class="po">
		<form id="updateReportForm" enctype="multipart/form-data" method="post">
			<input type="hidden"   id="reportid" name='reportid' value="${reportid}"  style="width: 155px;" class="easyui-validatebox input_bg" maxlength="25">
			<div class="add_box" style='' id='add_box'>
				<div class="add_box_in pt-page-scaleUpCenter">
					<!-- <h4>新增</h4> -->
					<ul class="add_box_in_ul">
						<li class="add_box_in_uli"><span>制作人：<span id="redSpan">*</span></span> 
							<input id="publisper" name="publisper" value="${useid}" >
						</li>
						<li class="add_box_in_uli"><span>有效期：</span> 
							<input id="datescreen" name="datescreen" type="text" value=""  readonly="readonly">
						</li>
						<li class="add_box_in_uli"><span>责任者：</span> 
							<input id="" name="" value="" >
						</li>
						<li class="add_box_in_uli"><span>开发者：</span> 
							<input id="" name="" value="" >
						</li>
						<li class="add_box_in_uli add_box_in_textarea"><span>报表描述：</span> 
							<textarea id="reportdescription" name="reportdescription" class='form-control' style="height:60px;width:72%;"  ></textarea>
						</li>
				<!-- 		<li class="add_box_in_uli add_box_in_textarea">
							<span>报表属性 ：</span> 
							<div class="demo">
								<div class="plus-tag tagbtn clearfix" id="myTags"></div>
								<div class="plus-tag-add">
									<form id="" action="" class="login">
										<ul class="Form FancyForm">
											<li>
												<span class="label">我的标签：</span>
												<input id="name" name="name" type="text" class="stext" maxlength="20" placeholder="新增输入标签"/>
												<label>输入标签</label>
												<span class="fff"></span>
												<button type="button" onclick="onadd()" class="Button RedButton Button18" style="font-size:14px;padding:6px;margin-top: 5px">添加标签</button>
											</li>
										</ul>
									</form>
								</div>plus-tag-add end
								
								<div id="mycard-plus" style="">
									<h4>标签 : </h4>
									<div class="default-tag tagbtn">
										<div id="clearfix" class="clearfix">
        
										</div>
									</div>
								</div>
							</div>
						</li> -->
					</ul>
					<div class="button_succ">
						<button  id="savebutton" type="button" onclick="updateReport()" class="btn btn-shadow btn-info">保存</button>
						<button  type="button" onclick="closeReport()" class="btn btn-shadow btn-default">取消</button>
					</div>
				</div>
			</div>
		</form>
	</div>
	
</body>
</html>