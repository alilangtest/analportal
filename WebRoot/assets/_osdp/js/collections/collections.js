var EL = EL || {}, FN = FN || {}, GV = GV || {}, URLS = URLS || {}, TS = TS|| {};
URLS.shouList = _ctx + '/tableauserver/tableauServerAdd.html';

function shouList(){
	layer.open({
	      type: 2,
	      title: '新增',
	      shadeClose: true,
	      shade: false,
	      maxmin: true, //开启最大化最小化按钮
	      area: ['600px', '480px'],
	      content: URLS.shouList
	 });
}