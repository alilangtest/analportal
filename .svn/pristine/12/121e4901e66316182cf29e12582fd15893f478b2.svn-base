/**
 * 数据指标
 */
function queryDataIndex(type,name){
	var Rpath=$("#Rpath").val();
	
	var param={};
	param.type=type;
	param.name=name;
	var url2=Rpath+"dataQuota/todatazhibiaoinPage.do";
	$.ajax({
			url:url2,
			type:"POST",
			data :param,
			datatype:"JSON",
			success:function(data){
				var url=data.url;
				if (data.result==1) {
					window.location.href=Rpath+"dataQuota/toDataPage.html?type="+type+"&name="+name;
				} else {
					layer.msg("指标没有数据");
				}
			}
		});
}
