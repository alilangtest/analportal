var URLS = URLS || {};
URLS.saveReport=_ctx + '/report/saveReport.do';
URLS.saveAttribute=_ctx + '/report/saveAttribute.do';
URLS.queryAttribute=_ctx + '/report/queryAttribute.do';
URLS.reportById=_ctx + '/report/reportById.do';
//-----------------------------------------------------------

var portals='';//报表属性

$(function() {
	var reportid = $("#reportid").val();
	//查询报表
	$.ajax({
		url: URLS.reportById,
		type:'POST',
		async:false,
		data:{
			"reportid":reportid
		},
		success: function(result){
			$("#publisper").val(result.publisper); 
		    $("#datescreen").val(result.datescreen);
		    $("#reportdescription").text(result.reportdescription);
		}
	});
	
	laydate({
		elem : '#datescreen',
		format : 'YYYY-MM-DD',
		min: laydate.now()
	});
	
	
	//查询属性
/*	$.ajax({
		url: URLS.queryAttribute,
		type:'POST',
		async:false,
		data:{
			"reportid":reportid
		},
		success: function(result){
			var list=result.list;
			var checkedlist=result.checkedlist;
			var checkedArray = new Array ();
			if(null!=checkedlist){
				for (var j=0; j<checkedlist.length; j++){
					checkedArray.push(checkedlist[j].name);
				}
			}
			//alert(checkedlist.length+"checkedlist.length");
			for (var i=0; i<list.length; i++){
				if(null!=checkedlist){
					if(checkedArray.indexOf(list[i].name) != -1 ){
						$("#clearfix").append("<a  title="+list[i].name+" href='javascript:void(0);' onclick='aclick(this)' style='color: #f00 '><span>"+list[i].name+"</span></a>");
						 portals=portals+list[i].name+",";
					}else{
						$("#clearfix").append("<a  title="+list[i].name+" href='javascript:void(0);' onclick='aclick(this)'><span>"+list[i].name+"</span></a>");
					}
				}else{
					$("#clearfix").append("<a  title="+list[i].name+" href='javascript:void(0);' onclick='aclick(this)'><span>"+list[i].name+"</span></a>");
				}
			}
		}
	});*/

})


/*修改与新增*/
function updateReport() {
	var err=true;
	var reportid = $("#reportid").val();
	var publisper =$.trim($("#publisper").val());
	var datescreen = $("#datescreen").val();
	var reportdescription = $("#reportdescription").val();

	
	if(publisper=='' || publisper==null){
		layer.msg("请输入制作人！", {
			msg : [ '#BB0000' ],
			time : 2000
		});
		err=false;
	}
	if(publisper.length>20){
		layer.msg("制作人长度不超过20个字！", {
			msg : [ '#BB0000' ],
			time : 2000
		});
		err=false;
	}

	if(err){
		$.ajax({
			url: URLS.saveReport,
			type:'POST',
			data:{
				'reportId':reportid,
				'publisper':publisper,
				'datescreen':datescreen,
				'reportdescription':reportdescription,
				'names':portals
			},
			success: function(result){
				if (result.success){
					layer.msg("修改报表信息保存成功！",{icon:1},function(){
		                parent.location.reload(); // 父页面刷新
		                var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		                parent.layer.close(index);
		            });
				} else {
				   layer.msg("修改报表信息保存出错！", {
						msg : [ '#BB0000' ],
						time : 2000
					});
				}
			}
		});
	}

}

//添加新标签
function onadd(){
	var name=$("#name").val().trim();
	if(name.length>5){
		layer.msg("标签长度不能超过5个字！", {
			msg : [ '#BB0000' ],
			time : 2000
		});
	}else if(name.length<1){
		layer.msg("请填写标签名！", {
			msg : [ '#BB0000' ],
			time : 2000
		});
	}else{
		$.ajax({
			url:URLS.saveAttribute,
			data:{
				"name":name
			},
			dataType:"text",
			type:"post",
			success:function(result){
				var result = eval('('+result+')');
				if(result.success){
					$("#clearfix").append("<a  title="+name+" href='javascript:void(0);' onclick='aclick(this)'><span>"+name+"</span></a>");
				}else{
					layer.msg("添加标签失败！", {
						msg : [ '#BB0000' ],
						time : 2000
					});
				}
			}
		})
	}
}


//添加属性
function aclick(self){
   var title=self.title+",";
   
   if( portals.indexOf(title) >= 0){
	   portals = portals.replace(title,'');
	   
	   self.style.color = "#000"
   }else{
	   portals=portals+title;
	   self.style.color = "#f00"
   }
  
}

//关闭
function closeReport(){
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引  
    parent.layer.close(index);
}

//-------------------------------------------------------------------
