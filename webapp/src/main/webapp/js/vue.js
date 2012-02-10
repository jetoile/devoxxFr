$(function() {
	//overlay (jquerytools)
	// positions for each overlay
	var positions = [ 
		[0,530],
		[400,20],
		[400,530],
		[0,20],
			[0,20]	
	];	
		
	// setup triggers
	
	
	
	$("li[rel]").each(function(i) {
			
		$(this).overlay({

			// common configuration for each overlay
			oneInstance: false, 
			closeOnClick: false, 
			 
			// setup custom finish position
			 
			/*top:this.offsetTop + 95,*/
			left: $("#logon").offsetLeft + 105,
			top:$("#logon").offsetTop +105
			// use apple effect
			/*effect: 'apple'*/
			
		});			
	});
	$("li.li_question").each(function(i) {
		 
		
		$(this).overlay({
		target:"#content_answer",
		oneInstance: false, 
			closeOnClick: false, 
		left: $("#logon").width()+$("#logon").offset().left +$("#content_play").width() +55 ,
		top: $("#logon").offset().top  ,
		});
	});
});