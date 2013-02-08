package sand.sperlazzasaurus.com.isoStream.model;

import uk.co.caprica.vlcj.player.MediaPlayer;

/**
 * information about a dvd, the number of titles and chapters
 * @author elliott
 *
 */
public class DvdInfo {

	private int chapters;
	private int titles;
		
	public DvdInfo(MediaPlayer mediaPlayer) {
		chapters = mediaPlayer.getChapterCount();
		titles = mediaPlayer.getTitleCount();
	}

	public int getChapters() {
		return chapters;
	}

	public int getTitles() {
		return titles;
	}

}
