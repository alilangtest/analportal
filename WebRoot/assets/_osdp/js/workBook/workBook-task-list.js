var loginer = $('#loginer');
var nav=$("nav"); 
var sc=$(document);
loginer.click(function(){
	$('#login_erd').stop(true).show(true);
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
    	$('.logo_left img').attr('src','images/logo@3x.png');
    	$('nav').addClass('fixednav');
    	$('.nav_list .nav_list_li span a').css('color','#333');
    	$('.nav_right a').addClass('hover2');
    	$('.dengluSucess').css('color','#333');
  }else if(sc.scrollTop()>=500){
  		$('nav').css({
  			'border-bottom':'1px solid #f0f0f0'
  		});
  		$('nav').addClass('fixednav2');
  }else if(sc.scrollTop()<=0){
   // nav.removeClass("fixednav");
   	$('nav').css({
   		'border-bottom':'1px solid rgba(0,0,0,0)'
   	});
   	$('nav').removeClass('fixednav');
   	$('nav').removeClass('fixednav2');
   $('.nav_list .nav_list_li span a').css('color','#fff');
   $('.logo_left img').attr('src','images/logo_whi.png');
   $('.nav_right a').removeClass('hover2');
   $('.dengluSucess').css('color','#fff');
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