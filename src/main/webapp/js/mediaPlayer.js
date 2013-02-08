jQuery(document).ready(function(){
	jQuery(".videoControl").click(function(event){
		var action = jQuery(this).attr("id");
		event.preventDefault();
		jQuery.ajax({
			url: "player/"+action,
			dataType: "json",
			success : function(response){
				console.log(response.result);
				if(response.location){
					console.log("Loading Video!");
					document.video1.target = "http://" + response.location;	
					vlc.playlist.play();
				}
			}
		})
	});
	
	jQuery(".playbackControl").click(function(event){
		var action = jQuery(this).attr("id");
		event.preventDefault();
		jQuery.ajax({
			url: "playback/"+action,
			dataType: "json",
			success : function(response){
				console.log(response.result);
				if(response.location){
					console.log("Loading Video!");
					document.video1.target = "http://" + response.location;	
					vlc.playlist.play();
				}
			}
		})
	});

	jQuery("#play").click(function(event){
		event.preventDefault();
		jQuery.ajax({
			url: "player/play",
			dataType: "json",
			data: {videoNumber: jQuery("#fileSelect").val()},
			success : function(response){
				console.log(response.result);
				if(response.location){
					document.video1.target = "http://" + response.location;	
					jQuery("#noVideoScreen").slideUp("quick", function(){
						jQuery("#videoWrapper").slideDown("fast", function(){
							vlc.playlist.play();
						});
					});
				}
			}
		})
	});
	
	jQuery("#reload").click(function(event){
		vlc.playlist.play();
	});
	
	jQuery("#fullscreen").click(function(event){
		event.preventDefault();
		vlc.video.toggleFullscreen();
	});
	
	jQuery.ajax({
		url: "player/getFiles",
		dataType: "json",
		success : function(response){
			var options = "";
			
			for(var i=0; i<response.files.length; i++){
				var title = response.files[i];
				title = title.replace(response.basePath,"");
				options += "<option value="+i+">"+title+"</option>";
			}
			
			jQuery("#fileSelect").html(options);
		}
	});

	setInterval(function(){
		if(vlc && vlc.input && (vlc.input.state == 1 || vlc.input.state == 2 || vlc.input.state == 3)){
			jQuery.ajax({url : "player/heart"});
		} 
	},240000);	

});