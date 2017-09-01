var path=$("#path").val();
var loginer = $('#loginer');
var nav=$("nav"); 
var sc=$(document);
//var collections = $('#collections');
loginer.click(function(){
	$('#login_erd').stop(true).show(true);
});

$('#collections').click(function(){
	layer.open({
	      type: 2,
	      title: '收藏',
	      shadeClose: true,
	      shade: 0.3,
	      maxmin: true, //开启最大化最小化按钮
	      area: ['1000px', '600px'],
	      content: _ctx+'/tableauserver/collections.html'
	 });
});

$('#colose').click(function(){
	$('#login_erd').stop(true).hide(true);
});
scroll_tp();
$(window).scroll(function(){
	scroll_tp();
});
function scroll_tp(){
  if(sc.scrollTop()>=60&&sc.scrollTop()<500){
    	// nav.addClass("fixednav");
    	$('.logo_left img').attr('src',path+'/assets/bootstrap/images/logo@3x.png');
    	$('nav').addClass('fixednav');
    	$('nav').removeClass('fixednav2');
    	$('.nav_list .nav_list_li span a').css('color','#333');
    	$('.nav_right a').addClass('hover2');
    	//$('.dengluSucess').css('color','#333');
  }else if(sc.scrollTop()>=500){
  		/*$('nav').css({
  			'border-bottom':'1px solid #f0f0f0'
  		});
  		$('nav').addClass('fixednav2');*/
	  $('nav').css({
	   		'border-bottom':'1px solid rgba(0,0,0,0)'
	   	});
	   	$('nav').removeClass('fixednav');
	   	$('nav').removeClass('fixednav2');
	   $('.nav_list .nav_list_li span a').css('color','#fff');
	   $('.logo_left img').attr('src',path + '/assets/bootstrap/images/logo_whi.png');
	   $('.nav_right a').removeClass('hover2');
	   //$('.dengluSucess').css('color','#fff');
	   $('nav').addClass('fixednav2');
  }else if(sc.scrollTop()<=0){
   // nav.removeClass("fixednav");
   	$('nav').css({
   		'border-bottom':'1px solid rgba(0,0,0,0)'
   	});
   	$('nav').removeClass('fixednav');
   	$('nav').removeClass('fixednav2');
   $('.nav_list .nav_list_li span a').css('color','#fff');
   $('.logo_left img').attr('src',path + '/assets/bootstrap/images/logo_whi.png');
   $('.nav_right a').removeClass('hover2');
  // $('.dengluSucess').css('color','#fff');
  }
}
$('.new_add').click(function(){
   $('#add_box').stop(true).show(); 
});
$('#close_btn1').click(function(){
   $('#add_box').stop(true).hide();  
});
$('.new_del').click(function(){
  $('#add_box2').stop(true).show();
});
 $('#close_btn2').click(function(){
   $('#add_box2').stop(true).hide();  
});
 $('.new_view').click(function(){
    $('#add_box3').stop(true).show();
 });
 $('#close_btn3').click(function(){
    $('#add_box3').stop(true).hide();
 });