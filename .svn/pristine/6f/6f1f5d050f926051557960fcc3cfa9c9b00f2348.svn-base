var EL = EL || {}, FN = FN || {}, GV = GV || {}, URLS = URLS || {}, TS = TS || {};
URLS.queryIndrelaData=_ctx + '/index_manager/queryIndrelaData';
URLS.queryIndrelaPageData=_ctx + '/index_manager/queryIndrelaPageData';
$(function() {

var this_height = $('body,html').height();
$('.div').css("height", this_height - 30);
//初始加载左侧菜单数据
//报表查询参数信息
queryindrelaParam.functionClass = document.getElementById("functionClass").value;
queryindrelaParam.reportClass = document.getElementById("reoprtClass").value;
queryindrelaParam.reportSubclass = document.getElementById("reportSubclass").value;
queryindrelaParam.reportName = document.getElementById("reportName").value;
queryindrelaParam.currentPageNumber = 1;
queryindrelaParam.indexName = null;
queryindrelaParam.vagueReportName = null;
//queryindrelaUrl = URLS.queryIndrelaData;
queryindrelas();
	
});

var queryindrelaParam = new Object();
queryindrelaParam.functionClass = null;
queryindrelaParam.reportClass = null;
queryindrelaParam.reportSubclass = null;
queryindrelaParam.reportName = null;

//分页工具类
var pageUtil = new Object();
pageUtil.pageSize = 10;
pageUtil.totlePage = 1;
pageUtil.totleNumber = null;
pageUtil.currentPageNumber = 1;
pageUtil.startNumber = null;
pageUtil.endNumber = null;
//分页工具类
var weiduPageUtil = new Object();
weiduPageUtil.pageSize = 10;
weiduPageUtil.totlePage = 1;
weiduPageUtil.totleNumber = null;
weiduPageUtil.currentPageNumber = 1;
weiduPageUtil.startNumber = null;
weiduPageUtil.endNumber = null;
function queryindrelas(){
	//点击菜单加载报表信息
	$.post(URLS.queryIndrelaData, queryindrelaParam, function(result) {
		var indexTbody = document.getElementById("indexTbody");
		indexTbody.innerHTML = "";
		var weiduTbody = document.getElementById("weiduTbody");
		weiduTbody.innerHTML = "";
		for(var key in result){
			if(key == "indexPage"){
				pageUtil.totlePage = result[key].totlePage;
				pageUtil.totleNumber = result[key].totleNumber;
				initIndexPage();
			}else if(key == "weiduPage"){
				weiduPageUtil.totlePage = result[key].totlePage;
				weiduPageUtil.totleNumber = result[key].totleNumber;
				initWeiDuPage();
			}else{
				for(var k in result[key]){
					var tr = document.createElement("tr");
					var td0 = document.createElement("td");
					td0.innerText = "";
					tr.appendChild(td0);
					var td1 = document.createElement("td");
					td1.innerText = result[key][k].reportClass;
					tr.appendChild(td1);
					var td2 = document.createElement("td");
					td2.innerText = result[key][k].reportName;
					tr.appendChild(td2);
					var td3 = document.createElement("td");
					td3.innerText = result[key][k].indexName;
					tr.appendChild(td3);
					var td4 = document.createElement("td");
					td4.innerText = result[key][k].dataSource;
					tr.appendChild(td4);
					var td5 = document.createElement("td");
					td5.innerText = result[key][k].tableRemarks;
					tr.appendChild(td5);
					if(key == "index"){
						var td6 = document.createElement("td");
						td6.innerText = result[key][k].calculate;
						tr.appendChild(td6);
						/*var td7 = document.createElement("td");
						var a = document.createElement("a");
						a.innerText = "报表明细";
						a.href = "#";
						a.onclick = function () {
							reportInfo();
						};
						td7.appendChild(a);
						tr.appendChild(td7);*/
						indexTbody.appendChild(tr);
					}else{
						weiduTbody.appendChild(tr);
					}
				}
			}
		}
	});
}
//初始化指标分页信息
function initIndexPage(){
	layui.use(['laypage', 'layer'], function(){
	  var laypage = layui.laypage
	  ,layer = layui.layer;
	  laypage({
	    cont: 'demo7'
	    ,pages: pageUtil.totlePage
	    ,skip: true
	    ,jump: function(obj){
	     	indexPage(obj.curr);
	    }
	  });
	});
}
//初始化维度分页信息
function initWeiDuPage(){
	layui.use(['laypage', 'layer'], function(){
	  var laypage = layui.laypage
	  ,layer = layui.layer;
	  //调用分页
	  laypage({
	    cont: 'demo8'
	    ,pages: weiduPageUtil.totlePage //得到总页数
	    ,skip: true
	    ,jump: function(obj){
	     	weiduPage(obj.curr);
	    }
	  });
	});
}
//指标翻页操作
function indexPage(curr){
	pageUtil.currentPageNumber = curr;
	queryindrelaParam.type = "index";
	queryindrelaParam.currentPageNumber = curr;
	//分页加载指标信息
	$.post(URLS.queryIndrelaPageData, queryindrelaParam, function(result) {
		bindData(result);
	});
}
//维度翻页操作
function weiduPage(curr){
	weiduPageUtil.currentPageNumber = curr;
	queryindrelaParam.type = "weidu";
	queryindrelaParam.currentPageNumber = curr;
	//分页加载维度信息
	$.post(URLS.queryIndrelaPageData, queryindrelaParam, function(result) {
		bindData(result);
	});
}
//指标/维度分页查询时绑定数据方法
function bindData(result){
	var index = 1;
	var indexTbody = document.getElementById("indexTbody");
	var weiduTbody = document.getElementById("weiduTbody");
	for(var key in result){
		if(key == "index"){
			indexTbody.innerHTML = "";
		}else if(key == "weidu"){
			weiduTbody.innerHTML = "";
		}
		for(var k in result[key]){
			var tr = document.createElement("tr");
			var td0 = document.createElement("td");
			td0.innerText = index;
			index++;
			tr.appendChild(td0);
			var td1 = document.createElement("td");
			td1.innerText = result[key][k].reportClass;
			tr.appendChild(td1);
			var td2 = document.createElement("td");
			td2.innerText = result[key][k].reportName;
			tr.appendChild(td2);
			var td3 = document.createElement("td");
			td3.innerText = result[key][k].indexName;
			tr.appendChild(td3);
			var td4 = document.createElement("td");
			td4.innerText = result[key][k].dataSource;
			tr.appendChild(td4);
			var td5 = document.createElement("td");
			td5.innerText = result[key][k].tableRemarks;
			tr.appendChild(td5);
			if(key == "index"){
				var td6 = document.createElement("td");
				td6.innerText = result[key][k].calculate;
				tr.appendChild(td6);
				/*var td7 = document.createElement("td");
				var a = document.createElement("a");
				a.innerText = "报表明细";
				a.href = "#";
				a.onclick = function () {
					reportInfo();
				};
				td7.appendChild(a);
				tr.appendChild(td7);*/
				indexTbody.appendChild(tr);
			}else if(key == "weidu"){
				weiduTbody.appendChild(tr);
			}
		}
	}
}

