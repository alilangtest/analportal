/**
 * 
 */
//String.prototype.startWith=function(str){
//	debugger;
//    var reg=new RegExp("^"+str);    
//    return reg.test(this);       
//}; 
//
//String.prototype.endWith=function(str){    
//    var reg=new RegExp(str+"$");    
//    return reg.test(this);       
//};
function formatDate(value, rowData, rowIndex){
	var _date = new Date(value);
	var _year = _date.getFullYear();
	var _month = _date.getMonth() * 1 + 1;
	var _day = _date.getDate();
	return _year + "-" + _month + "-" + _day;
}
//获取项目的跟目录
function getRootPath(){
	var _href = window.location.href;
	var _pathName = window.location.pathname;
	var _pos = _href.indexOf(_pathName);
	var _basePath = _href.substring(0, _pos);
	//获取项目名称
	var _projectName = _pathName.substring(0, _pathName.substring(1).indexOf("/") + 1);
	return _basePath + _projectName;
}
//生成URL
function getURL(url, param){
	if(url == null || url == "") throw new Error("url不能为空");
	if(url.indexOf("/") != 0) throw new Error("url必须以/开头");
	if(param == null){
		return getRootPath() + url + "?_currentTime=" + new Date().getTime();
	}else{
		var _url = getRootPath() + url + "?_currentTime=" + new Date().getTime();
		for(var prop in param){
			_url += "&" + prop + "=" + param[prop];
		}
		return _url;
	}
}
//向数组中插入追加不存在的对象
function appendDiff(obj, target){
	if(obj == null || target == null) throw new Error("Argument must not be null!");
	if(!target instanceof Array) throw new Error("target must is a instance of Array");
	var _isContained = false;
	for(var i = 0; i < target.length; i++){
		if(target[i] == obj){
			_isContained = true;
			break;
		}else{
			continue;
		}
	}
	if(!false){
		target.push(obj);
	}
	return target;
}
//获取浏览器版本信息
function getBrowserInfo(){
	var _userAgent = navigator.userAgent;//取得浏览器的userAgent字符串
	var _isOpera = _userAgent.indexOf("Opera") > -1; //判断是否Opera浏览器
    var _isIE = _userAgent.indexOf("compatible") > -1 && _userAgent.indexOf("MSIE") > -1 && !isOpera; //判断是否IE浏览器
    var _isFF = _userAgent.indexOf("Firefox") > -1; //判断是否Firefox浏览器
    var _isSafari = userAgent.indexOf("Safari") > -1; //判断是否Safari浏览器
    if (isIE) {
        var _IE5 = _IE55 = _IE6 = _IE7 = _IE8 = false;
        var _reIE = new RegExp("MSIE (\\d+\\.\\d+);");
        _reIE.test(_userAgent);
        var _fIEVersion = parseFloat(RegExp["$1"]);
        _IE55 = _fIEVersion == 5.5;
        _IE6 = _fIEVersion == 6.0;
        _IE7 = _fIEVersion == 7.0;
        _IE8 = _fIEVersion == 8.0;
        if (_IE55) {
            return "IE55";
        }else if(_IE6) {
            return "IE6";
        }else if(_IE7) {
            return "IE7";
        }else if(_IE8) {
            return "IE8";
        }
    }else if(_isFF) {
        return "FF";
    }else if(_isOpera) {
        return "Opera";
    }
}
//向数组指定的位置插入内容
Array.prototype.insertAt = function (obj, index){
	if(this instanceof Array){
		if(index == this.length){
			this.push(obj);
			return this;
		}else if(index == 0){
			this.unshift(obj);
			return this;
		}else{
			var _result = new Array(this.length + 1);
			var _length = _result.length;
			for(var i = 0; i < _length; i++){
				if(i < index){
					_result[i] = this[i];
				}else if(i == index){
					_result[i] = obj;
				}else if(i > index){
					_result[i] = this[i - 1];
				}
			}
			return _result;
			
		}
	}else{
		throw new Error("操作对象只能是数组");
	}
};
//比较两个数组内容是否弱相等
Array.prototype.equals = function(array){
	if(!array instanceof Array) throw new Error("Argument must not be an instance of Array");
	var _result = true;
	if(this.length != array.length) return false;
	for(var i = 0; i < this.length; i++){
		if(this[i] != array[i]){
			_result = false;
			break;
		}else{
			continue;
		}
	}
	return _result;
};
//比较两个数组是否强相等
Array.prototype.equalsAbsolutely = function(array){
	if(!array instanceof Array) throw new Error("Argument must not be an instance of Array");
	var _result = true;
	if(this.length != array.length) return false;
	for(var i = 0; i < this.length; i++){
		if(this[i] !== array[i]){
			_result = false;
			break;
		}else{
			continue;
		}
	}
	return _result;
};
//判断数组中是否包含指定的元素
Array.prototype.contains = function(obj){
	var _isContains = false;
	for(var i = 0; i < this.length; i++){
		if(this[i] == obj) {
			_isContains = true;
			break;
		}else{
			continue;
		}
	}
	return _isContains;
};
//获取元素在数组中第一次出现的索引，没有则返回-1
Array.prototype.indexOf = function(obj){
	var _index = -1;
	for(var i = 0; i < this.length; i++){
		if(this[i] == obj){
			_index = i;
			break;
		}else{
			continue;
		}
	}
	return _index;
};
/*
 * 将 newChild 插入到 refChild 的后边
 * @param {Object} newChild
 * @param {Object} refChild
 */
function insertAfter(newChild, refChild){
	var _refParentNode = refChild.parentNode;
	//判断 refChild 是否存在父节点
	if(_refParentNode){
		//判断 refChild 节点是否为其父节点的最后一个子节点
			if(refChild == _refParentNode.lastChild){
				_refParentNode.appendChild(newChild);
			}else{
				_refParentNode.insertBefore(newChild, refChild.nextSibling);
			}	
		}
}
//让easyui-layout自适应浏览器高度
function initLayoutHeight(){
	var _height = $(window).height() - 30;
	$(".easyui-layout:first").css("height", _height);
	$(".easyui-layout:first").layout("panel", "west").panel("resize",{
		height : _height
	});
}
//重写easy ui 的datebox格式
//$.fn.datebox.defaults.formatter = function(date){
//	var y = date.getFullYear();
//	var m = date.getMonth()+1;
//	var d = date.getDate();
//	return y+'-'+m+'-'+d;
//};
//function easyShow(msg){
//	$.messager.show({
//		title : "提示",
//		msg : msg,
//		timeout : 1500,
//		showType:'slide'
//
//	});
//}
//$.fn.pagination.defaults = {
//		total : 1,
//		pageSize : 10,
//		pageNumber : 1,
//		pageList : [10,20,30,50],
//		loading : false,
//		buttons : null,
//		showPageList : true,
//		showRefresh : true,
//		beforePageText : "页",
//		afterPageText : "of {pages}",
//		displayMsg : "当前显示 {from} - {to} 条记录   共 {total} 条记录22"
//};
//扩展日期对象的功能
Date.prototype.format = function (format) {
    var o = {
        "M+": this.getMonth() + 1, // month
        "d+": this.getDate(), // day
        "h+": this.getHours(), // hour
        "m+": this.getMinutes(), // minute
        "s+": this.getSeconds(), // second
        "q+": Math.floor((this.getMonth() + 3) / 3), // quarter
        "S": this.getMilliseconds() // millisecond
    };
    if (/(y+)/.test(format))
        format = format.replace(RegExp.$1, (this.getFullYear() + "")
            .substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(format))
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
    return format;
};

function formatDatebox(value) {
    if (value == null || value == '') {
        return '';
    }
    var dt;
    if (value instanceof Date) {
        dt = value;
    } else {
        dt = new Date(value);
    }

    return dt.format("yyyy-MM-dd"); //扩展的Date的format方法(上述插件实现)
}
//让easyui-layout自适应浏览器高度
function autoLayoutHeight(){
	var _height = $(window).height()-30;
	$(".easyui-layout").layout("resize",{
		height : _height
	});
}

//重写jquery的ajax方法,使之处理session超时跳转到登录页面
(function($){
	//备份jquery的ajax方法
	var _ajax=$.ajax;
	
	//备份opt中error和success方法  
    /*var fn = {  
        error:function(XMLHttpRequest, textStatus, errorThrown){},  
        success:function(data, textStatus){}  
    };
    if(opt.error){  
        fn.error=opt.error;  
    }  
    if(opt.success){  
        fn.success=opt.success;  
    }  */

	
	$.ajax=function(opt){
		//扩展增强处理
		var _opt = $.extend(opt,{
			complete : function(XMLHttpRequest, textStatus){
				var sessionstatus=XMLHttpRequest.getResponseHeader("sessionstatus"); //通过XMLHttpRequest取得响应头，sessionstatus， 
				if(sessionstatus=="timeout"){ 
		           easyShow("登录超时,请重新登录！");
		           //如果超时就处理 ，指定要跳转的页面  
		           window.location.replace(getRootPath() + "/loginController/login");   
		         }  
			},
		});
		_ajax(_opt);
	};
})(jQuery);

//展示树形结构
function tree(id, url){
	$("#" + id).tree({
		url: url,
		loadFilter: function(rows){
			return convert(rows);
		}
	});
}
function convert(rows){
	function exists(rows, parentId){
		for(var i=0; i<rows.length; i++){
			if (rows[i].id == parentId) return true;
		}
		return false;
	}
	
	var nodes = [];
	// get the top level nodes
	for(var i=0; i<rows.length; i++){
		var row = rows[i];
		if (!exists(rows, row.parentId)){
			nodes.push({
				id:row.id,
				text:row.name
			});
		}
	}
	
	var toDo = [];
	for(var i=0; i<nodes.length; i++){
		toDo.push(nodes[i]);
	}
	while(toDo.length){
		var node = toDo.shift();	// the parent node
		// get the children nodes
		for(var i=0; i<rows.length; i++){
			var row = rows[i];
			if (row.parentId == node.id){
				var child = {id:row.id,text:row.name};
				if (node.children){
					node.children.push(child);
				} else {
					node.children = [child];
				}
				toDo.push(child);
			}
		}
	}
	return nodes;
}

