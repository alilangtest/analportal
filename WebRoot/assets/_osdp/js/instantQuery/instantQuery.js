/**
 * 数据分析
 */

var  ZI_BIAOQIAN="";
var  REP_ATTRIBUTE_INFO="";
    $(document).ready(function(){
    	var url="instantQuery/queryTableauInstantQuery.do";
    	$.ajax({
  			url:url,
  			type:"POST",
			datatype:"JSON",
  			success:function(data){
  				var tableHTML1="";
  				var labelAnalysis=data.labelAnalysis;
  				for (var i = 0; i < labelAnalysis.length; i++) {
  					tableHTML1+="<li class=\"dianzan_list_li\">";
  					var reportId=labelAnalysis[i].reportId;
  					var publisper=labelAnalysis[i].publisper;
  					
  					tableHTML1+="<ol class=\"dianzan_list_ol\">";
  					tableHTML1+="<li class=\"list_oli list_oli_one\">";
  					tableHTML1+="<a href=\"javascript:void(0)\" onclick=\"edit("+reportId+")\"><i class=\"fa fa-bar-chart-o\"></i>"+labelAnalysis[i].reportName+"</a>";
  					tableHTML1+="</li>";
  					tableHTML1+="</ol>";
  					tableHTML1+="</li>";
				}
  				$("#ULTable").html(tableHTML1);
  			}
		});
   });
    
    /***
     * 根据标签查询数据
     * @param labelId
     */
    function querylabel(menuId){
    
    	//获取菜单下的子菜单
    	var repLab=$("#repLab").val();
    	//获取报表属性
    	var repAtt=$("#repAtt").val();
    	//请求路径
    	var url="instantQuery/querylabelDataAnalysis.do";
    	//参数
    	var param={};
    	param.menuId=menuId;
    	
    	$.ajax({
  			url:url,
  			type:"POST",
  			data :param,
			datatype:"JSON",
  			success:function(data){
  				var tableHTML1="";
  				var labelAnalysis=data.labelAnalysis;
  				for (var i = 0; i < labelAnalysis.length; i++) {
  					tableHTML1+="<li class=\"dianzan_list_li\">";
  					
  					var reportId=labelAnalysis[i].reportId;
  					var publisper=labelAnalysis[i].publisper;
  					tableHTML1+="<ol class=\"dianzan_list_ol\">";
  					tableHTML1+="<li class=\"list_oli list_oli_one\">";
  					tableHTML1+="<a href=\"javascript:void(0)\" onclick=\"edit("+reportId+")\"><i class=\"fa fa-bar-chart-o\"></i>"+labelAnalysis[i].reportName+"</a>";
  					tableHTML1+="</li>";
  					tableHTML1+="</ol>";
  					tableHTML1+="</li>";
				}
  				$("#ULTable").html(tableHTML1);	
  			}
    	});
    }
    
    /**
     * 打开新的页面（报表编辑页面）
     * @param reportId
     */
    function edit(reportId){
    	$.post("instantQuery/queryTableReport.do",{reportid:reportId},function(result){
				if (result.success==1){
					window.open(result.url);  
				}else if (result.error==4){
					layer.alert('提示当前service服务ip配置不正确，请管理员查看tableau权限配置中service服务ip配置是否正确');
				}else if (result.error==3){
					layer.alert('提示当前用户还未分配所属机构，他无权限访问报表！请联系管理员分配对应机构');
				}else if (result.error==2){
					layer.alert('提示获取tableau用户票证失败！请管理员查看tableau权限配置用户和密码是否正确,tableau服务器是否授权！');
				}else if (result.error==1){
					layer.alert('提示查看报表内容出错，tableau服务器未授权！请联系管理员！');
				}else if (result.error==5){
					layer.alert('tableau用户不存在，请联系管理员！');
				}
			},'json');
    }
