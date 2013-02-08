package sand.sperlazzasaurus.com.isoStream.model;

import uk.co.caprica.vlcj.player.MediaPlayer;

/**
 * Information about a snapshot of an ISO stream, the chapter, title, 
 * position, path, and associated DvdInfo 
 * @author elliott
 *
 */
public class StreamInfo {

	private int chapter;
	private int title;
	private float position;
	private String mediaPath;

	private DvdInfo dvdInfo;
	
	public StreamInfo(MediaPlayer mediaPlayer, String path){
		dvdInfo = new DvdInfo(mediaPlayer);
		
		chapter = mediaPlayer.getChapter();
		title = mediaPlayer.getTitle();
		position = mediaPlayer.getPosition();
		
		mediaPath = path;
	}

	public int getChapter() {
		return chapter;
	}

	public int getTitle() {
		return title;
	}

	public float getPosition() {
		return position;
	}

	public DvdInfo getDvdInfo() {
		return dvdInfo;
	}
	
	public String getMediaPath(){
		return mediaPath;
	}
}
