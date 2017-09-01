var EL = EL || {}, FN = FN || {}, GV = GV || {}, URLS = URLS || {}, TS = TS || {};
URLS.loadQuestionGrid=_ctx + '/logss/logPageList.do';

$(function() {
	// 页面控件
	EL.questionGrid = $('#table_log');
	
	// 初始加载 无查询条件
	EL.questionGrid.datagrid({
		url : URLS.loadQuestionGrid,
		pagination : true,
		rownumbers:true,
		queryParams : {
			filters : __.encode({
				'username' : '',
				'menu' : '',
				'beginlogdate' : '',
				'endlogdate' : ''
			})
		},
		columns : [ [  {
			field : 'USERNAME',
			title : '用户',
			width : 200,
			align : 'center'
		},{
			field : 'USERID',
			title : '用户id',
			align : 'center',
			hidden:true
		}, {
			field : 'LOGDATE',
			title : '操作时间',
			width : 200,
			align : 'center'
		}, {
			field : 'MENU',
			title : '菜单名称',
			width : 200,
			align : 'center'
		}, {
			field : 'OPERTYPE',
			title : '操作类型',
			width : 200,
			align : 'center'
		} , {
			field : 'URL',
			title : '链接',
			width : 200,
			align : 'center'
		}, {
			field : 'OPERCONTENT',
			title : '操作内容',
			width : 200,
			align : 'center'
		}, {
			field : 'IPADDRESS',
			title : '用户访问ip地址',
			width : 200,
			align : 'center'
		} ] ],
	});
	$('.datagrid-header-rownumber').html('序号');
})

/*
 * 清空查询框
 */
function empty(){
	//机构
	$('#authOrg').select2("val", "");
	//用户id
	$('#userName').select2("val", "");
	//菜单名称
	$('#menu').select2("val", "");
	//开始时间
	$('#beginlogdate').val('');
	//结束时间
	$('#endlogdate').val('');
	searchs();
}


/*var columns_v = [[
				  {field:'USERNAME',title:'用户姓名',width:75},
				  {field:'LOGDATE',title:'操作日期',width:150,formatter:function(value,row,index){
					 // alert(value);
					  var  i = parseInt(value);
					//  alert(i);
					  
					   var date = new Date(i);
					   var year  = date.getFullYear();
					  // alert(year);
					  var month = date.getMonth() + 1 < 10 ? '0' + (date.getMonth()+1) : date.getMonth()+1;
					  var day = date.getDate()  < 10 ? '0' + date.getDate() : date.getDate();
					  var returnvalue = year + '-' + month  + '-' + day; 
					 // alert(returnvalue);
					  return value;
				  }},
				  {field:'MENU',title:'功能名称',width:200},					  
				  {field:'SYSTYPE',title:'操作类型',width:160},		  
				  {field:'URL',title:'链接',width:260},
				  {field:'bunissdecription',title:'操作内容',width:260},
				  {field:'reportname',title:'报表名称',width:120}
			]];
$(function() {
	
	EL.body = $(document.body);
	EL.questionGrid = $('#table_log');
	EL.btn2 = $('#searchBtn');
	EL.btn3 = $('#clearBtn');
	__.mask();
	EL.questionGrid.datagrid({
		url: URLS.loadQuestionGrid,
		pageSize:10,
		queryParams : {
			filters : __.encode({
				'username' : '',
				'menu' : '',
				'beginlogdate' : '',
					'endlogdate' : ''
			})
		},
		pageList:[10,15,30,50],
		striped: true,
		collapsible:true,
		autoRowHeight: false,
		singleSelect:true,
		remoteSort:true,
		pagination:true,
		rownumbers:true,	
		columns: columns_v,
	onLoadSuccess:function(data){
			__.unmask();
			EL.questionGrid.datagrid('clearSelections'); //clear selected options
	    }
	});
	EL.questionGrid.datagrid('resize', {
		width : document.documentElement.clientWidth-50,
		height : document.documentElement.clientHeight-$('#questionPanel').height()-70
	});
	EL.btn2=function() {
		var username = $("#username").val();
		var menu = $("#menu").val();
		var beginlogdate = $("#beginlogdate").val();
		var endlogdate = $("#endlogdate").val();
		//var questate = EL.questate.val();
        console.log(username);
        console.log(menu);
        
		EL.questionGrid.datagrid('reload', {
			filters : __.encode({
				'username' : username,
				'menu' : menu,
				'endlogdate':endlogdate,
				'beginlogdate':beginlogdate
				//'questate' : questate
			})
		});
	}
	
	EL.btn3=function() {
		$("#username").val('');
		$("#menu").val('');
		 $("#beginlogdate").val('');
		$("#endlogdate").val('');
	}
	
	
	
	
});
*/