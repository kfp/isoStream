<html!>
<head>
	<title>Demo of VLC mozilla plugin</title>
	<script type="text/javascript" src="../js/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="../js/mediaPlayer.js"></script>
	<link rel="stylesheet" type="text/css" href="../css/mediaPlayer.css">
</head>
<body style="text-align:center">

	<div id="screen">
		<div id="videoWrapper">
			<embed type="application/x-vlc-plugin"
		      name="video1" toolbar="false"
		      autoplay="no" loop="yes" width="600" height="400"
		      target="http://${streamLocation}" 
		      id="vlc"/>
	    </div>
	    <div id="noVideoScreen">
	    	<img src="../images/noVideo.png"><br/>
	    	<div id="welcomeMessage">
	    	<table>
				<tr>
				<td>Select a DVD and click </td>
				<td><span class="button demo"><img src="../images/top.png" alt="Start" /></span></td>
				</tr>
			</table>
			</div>
	    </div>
    </div>
      
    <div class="dvdControls">
    	<a id="resumeVideo" class="button playbackControl" href="#" title="Resume DVD"><img src="../images/player_play.png" alt="Resume"/></a>
    	<a id="pauseVideo" class="button playbackControl" href="#" title="Pause DVD"><img src="../images/player_pause.png" alt="Pause"/></a>  
    	<a id="stopVideo" class="button playbackControl" href="#" title="Stop DVD"><img src="../images/player_stop.png" alt="Stop"/></a>
    	<a id="fullscreen" class="button" href="#" title="Toggle Fullscreen"><img src="../images/window_fullscreen.png" alt="Fullscreen"/></a>
    	<a id="skipThirtySeconds" class="button playbackControl" href="#" title="Skip 30 Seconds"><img src="../images/player_fwd.png" alt="Skip 30"/></a> 
    	<a id="getStreamInfo" class="button videoControl" href="#" title="Save Stream Info"><img src="../images/tab_new.png" alt="Save Stream Info"/></a>
    	<a id="loadSavedStream" class="button videoControl" href="#" title="Load Saved Stream"><img src="../images/tab_duplicate.png" alt="Load Saved Stream"/></a>
	</div>
	<div class="dvdControls">
    	<a id="decrementTitle" class="button playbackControl" href="#" title="Prev Title"><img src="../images/player_start.png" alt="&lt;&lt;"/></a> 
    	<a id="decrementChapter" class="button playbackControl" href="#" title="Prev Chapter"><img src="../images/player_rev.png" alt="&lt;"/></a> 
		<a id="menuLeft" class="button videoControl" href="#" title="Menu Left"><img src="../images/1leftarrow.png" alt="Left"/></a> 
		<a id="menuUp" class="button videoControl" href="#" title="Menu Up"><img src="../images/1uparrow.png" alt="Up"/></a> 
		<a id="menuActivate" class="button videoControl" href="#" title="Menu Select"><img src="../images/add.png" alt="Select"/></a> 
		<a id="menuDown" class="button videoControl" href="#" title="Menu Down"><img src="../images/1downarrow.png" alt="Down"/></a>  
		<a id="menuRight" class="button videoControl" href="#" title="Menu Right"><img src="../images/1rightarrow.png" alt="Right"/></a>  
		<a id="incrementChapter" class="button playbackControl" href="#" title="Next Chapter"><img src="../images/player_fwd.png" alt="&gt;"/></a> 
		<a id="incrementTitle" class="button playbackControl" href="#" title="Next Title"><img src="../images/player_end.png" alt="&gt;&gt;"/></a> 
	</div>   

	<div id="fileBrowser">
		<table>
		<tr>
		<td>DVDs: <select id="fileSelect"></select></td>
    	<td><a id="play" class="button" href="#" title="Start DVD"><img src="../images/top.png" alt="Start"/></a>
    		<a id="reload" class="button" href="#" title="Reload Current Stream"><img src="../images/redo.png" alt="Reload Stream"/></a></td>
    	</tr>
    	</table>
	</div>


</body>
</html>