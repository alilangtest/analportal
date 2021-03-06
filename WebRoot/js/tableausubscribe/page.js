/**
 * 报表订阅分页 
 * Mr.tang
 */
/**
 * Table分页
 */
function indexTable(pageNum, data){
	var divOL = "<table border='0' cellspacing='0' cellpadding='0' class='layui-table' lay-even='' lay-skin='row'>" +
		"<tr>" +
		"<th>序号</th>" +
		"<th>主题名称</th>" +
		"<th>订阅人</th>" +
		"<th>订阅规则</th>" +
		"<th>发送时间</th>" +
	//	"<th>发送状态</th>" +
		"<th>操作修改</th>" +
		"</tr>";
	//总条数
	var len = data.length;
	//开始条数
	var startNumber = (pageNum == 1 ? 0 : (pageNum-1) * 5 );
	//结束条数
	var endNumber = pageNum * 5;
	var index = 1;
	
	for(var i=startNumber ;i<len ; i++){
		if(i>endNumber-1){
			break;
		}
		divOL = divOL +
		"<tr>"+
		"<td><a title='"+ data[i].seqNum +"'>"+ data[i].seqNum +"</a> </td>"+
		"<td><a title='"+ data[i].mailTitle +"'>"+ data[i].mailTitle +"</a> </td>"+
		"<td class='a-width'><a title='"+data[i].mails+"'>"+data[i].mails+"</a> </td>"+
		//邮箱太长------
		"<td>"+ data[i].sendTypeName +"</td>"+
		"<td>"+ data[i].sendTimeName +"</td>"+
			//"<td>"+ data[i].sendState +"</td>"+
			"<td>    <strong title='修改' onclick='updateConfig(\""+data[i].reportId+"\");'>&#xe892;</strong>" +
					"<strong title='删除' onclick='deleteConfig(\""+data[i].reportId+"\");'>&#xe61c;</strong>" +
					"<strong title='查看' style='font-size:14px;' class='eye_cha' onclick='viewResult(\""+data[i].reportId+"\");'>&#xe60f;</strong>" +
					"<strong title='手动发送' style='font-size:14px;' onclick='sendMail(\""+data[i].reportId+"\");'>&#xe616;</strong></td>" +
			"</tr>"
		"</table>";	
	}
	
		//将动态生成的table添加的事先隐藏的div中.
		$("#h5_section_top").html(divOL);
}

/**
 * EXCELTABLE分页
 */
function indexExcelTable(pageNum, data){
	var divOLExcel = "<table border='0' cellspacing='0' cellpadding='0' class='layui-table' lay-even='' lay-skin='row'>" +
	"<tr>" +
	"<th>序号</th>" +
	//"<th style=\"display:none;\">id</th>" +
	"<th >id</th>" +
	"<th>主题名称</th>" +
	"<th>表名称</th>" +
	"<th>订阅人</th>" +
	"<th>订阅规则</th>" +
	"<th>发送时间</th>" +
	"<th>操作修改</th>" +
	"</tr>"
	var len = data.length;
	//开始条数
	var startNumber = (pageNum == 1 ? 0 : (pageNum-1) * 5 );
	//结束条数
	var endNumber = pageNum * 5;
	var index = 1;
	for(var i=startNumber ;i<len ; i++){
		if(i>endNumber-1){
			break;
		}
		divOLExcel = divOLExcel +
		"<tr>"+
		"<td><a title='"+ data[i].seqNum +"'>"+ data[i].seqNum +"</a> </td>"+
		//"<td style=\"display:none;\"><a title='"+ data[i].id +"'>"+ data[i].id +"</a> </td>"+
		"<td ><a title='"+ data[i].id +"'>"+ data[i].id +"</a> </td>"+
		"<td ><a title='"+ data[i].mailTitleExcel +"'>"+ data[i].mailTitleExcel +"</a> </td>"+
		"<td ><a title='"+ data[i].tableId +"'>"+ data[i].tableId +"</a> </td>"+
		"<td ><a title='"+data[i].excelMails+"'>"+data[i].excelMails+"</a> </td>"+
		//邮箱太长------
		"<td >"+ data[i].sendTypeNameExcel +"</td>"+
		"<td >"+ data[i].sendTimeNameExcel +"</td>"+
			//"<td >"+ data[i].sendStateExcel +"</td>"+
			/*"<li><strong onclick='updateExcelConfig(\""+data[i].tableId+"\");'>&#xe892;</strong>" +*/
			"<td>" +
					"<strong title='修改'  onclick='updateExcelConfig(\""+data[i].id+"\");'>&#xe892;</strong>"+
					"<strong title='删除'  onclick='deleteExcelConfig(\""+data[i].id+"\");'>&#xe61c;</strong>" +
					"<strong title='查看'  style='font-size:14px;' class='eye_cha' onclick='viewExcelResult(\""+data[i].id+"\");'>&#xe60f;</strong>" +
					"<strong title='手动发送' style='font-size:14px;' onclick='sendExcelMail(\""+data[i].id+"\");'>&#xe616;</strong></td>" +
					/*"<strong title='修改'  onclick='updateExcelConfig(\""+data[i].tableId+"\");'>&#xe892;</strong>"+
					"<strong title='删除'  onclick='deleteExcelConfig(\""+data[i].tableId+"\");'>&#xe61c;</strong>" +
					"<strong title='查看'  style='font-size:14px;' class='eye_cha' onclick='viewExcelResult(\""+data[i].tableId+"\");'>&#xe60f;</strong>" +
					"<strong title='手动发送' style='font-size:14px;' onclick='sendExcelMail(\""+data[i].tableId+"\");'>&#xe616;</strong></td>" +*/
			"</tr>"
			"</table>";		
	}
	//将动态生成的table添加的事先隐藏的div中.
	//alert("将动态生成的table添加的事先隐藏的div中"+divOLExcel);
	$("#h5_section_excel_bottom").html(divOLExcel);
}