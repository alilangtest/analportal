<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../_include/_taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<%@include file="../_include/_meta.jsp"%>
<%@include file="../_include/_s0.jsp"%>
<style type="text/css">
.txtareas{
    width: 542px;
    height: 80px;
    border: 1px solid #e2e2e4;
    box-shadow: none;
    color: #c2c2c2;
    padding: 5px 5px;
    margin-right: 30px;
    font-size: 12px;
    border-radius: 3px;
    float: left;
    color: #333;
}
</style>
</head>
<body>
		<div class='add_box' style='margin-top:30px;'>
			<div class="add_box_in pt-page-scaleUpCenter">
				<ul class='add_box_in_ul'>
					<li class="add_box_in_uli">
						<span>报表分类: </span>
						<input name="name" id="reportClass" readonly="readonly" type="text">
					</li>
					<li class="add_box_in_uli">
						<span>报表名称: </span>
						<input name="name" id="reportName" readonly="readonly" type="text">
					</li>
					<li class="add_box_in_uli">
						<span>维度名称: </span>
						<input id="indexName" name="parentName" readonly="readonly" type="text" class="x-icon-menu-" readonly="readonly">
					</li>
					<li class="add_box_in_uli">
						<span>数据元名称: </span>
						<input id="dataSource" name="ordinal" readonly="readonly" type="text">
					</li>
					<li class="add_box_in_uli add_box_in_textarea">
						<span>含义描述: </span>
						<!-- <input class="txtareas" name="code" type="text" id="tableRemarks" readonly="readonly" style='width:465px;height:80px;'> -->
						<textarea class="txtareas" rows="3" id="tableRemarks" readonly="readonly">
							
						</textarea>
					</li>
					
					<li class="add_box_in_uli add_box_in_textarea">
						<span>字段值: </span>
						<!-- <input name="path" id="fieldsValue" type="text" style='width:465px;height:80px;'> -->
						<textarea class="txtareas" rows="3" id="fieldsValue" readonly="readonly">
							
						</textarea>
					</li>
				</ul>
				<div class="button_succ">
					<!-- <button id="cancel" type="button" class="btn btn-shadow btn-default">关闭</button> -->
				</div>
			</div>
		</div>
</body>
</html>