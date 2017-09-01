var EL = EL || {}, FN = FN || {}, GV = GV || {}, URLS = URLS || {}, TS = TS || {};
URLS.loadQuestionGrid=_ctx + '/index_manager/querydata.do';
$(function() {
EL.DataGrid = $('#dataIndex');
EL.fileName=$('#fileName');
EL.uploadPeople=$('#uploadPeople');
EL.DataGrid.datagrid({
	url: URLS.loadQuestionGrid,
	pageSize:15,
	queryParams : {
		filters : __.encode({
			'file_name':'',
			'operation_name':''				
		})
	},
	pageList:[15,30,50],
	striped: true,
	collapsible:true,
	autoRowHeight: false,
	singleSelect:true,
	remoteSort:true,
	pagination:true,
	rownumbers:true,
	columns: 
	[[
      {field:'OPERATION_NAME',title:'上传人',width:270},			  
	  {field:'FILE_NAME',title:'上传文件',width:420},
	  {field:'OPERATION_DATE',title:'上传时间',width:180},
	  {field:'下载',title:'文件下载',width:180, formatter:function (value, row, index){
		  return '<a href='+_ctx+'/index_manager/download?id='+row.ID+'>下载</a>';
	  }}
]],

});

	EL.query=function() {
		var fileName=EL.fileName.val();
		var people= EL.uploadPeople.val();
		EL.DataGrid.datagrid('reload', {
			filters : __.encode({
				'file_name':fileName,
				'operation_name': people
			})
		});
	};
	
	var error = document.getElementById("error").value;
	//alert(error);
	if(error != null && error != "" && error != "null"){
		document.getElementById("errorA").click();
	}
	$(".datagrid-header-rownumber").html("序号");
});
