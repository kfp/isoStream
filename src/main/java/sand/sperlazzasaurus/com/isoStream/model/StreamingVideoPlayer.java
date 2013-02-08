package sand.sperlazzasaurus.com.isoStream.model;

import java.io.IOException;
import java.net.ServerSocket;
import org.apache.log4j.Logger;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;

/**
 * A streaming video player. Wrapper for a HeadlessMediaPlayer
 * @author elliott
 *
 */
public class StreamingVideoPlayer {
	
	public final String STREAM_IP;
	public final String STREAM_HOST;
	public final int STREAM_PORT;
	
	private final MediaPlayer mediaPlayer;
	private String vlcOptions;
	private String mediaPath;
	
	private static final int PORT_RANGE_BEGIN = 55500;
	private static final int PORT_RANGE_END = 55600;
	
	Logger log = Logger.getLogger(this.getClass());
		
	/**
	 * Gets a random open port, creates a new HeadlessMediaPlayer
	 * @param ip
	 * @param host
	 * @throws IOException
	 */
	public StreamingVideoPlayer(String ip, String host) throws IOException{
		new NativeDiscovery().discover();
		
		MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory();
		mediaPlayer = mediaPlayerFactory.newHeadlessMediaPlayer();
		
//		int port = getPortInRange();
		int port = getRandomOpenPort();

		STREAM_IP = ip;
		STREAM_PORT = port;
		STREAM_HOST = host;
		
		vlcOptions = formatHttpStream(STREAM_IP, STREAM_PORT);
		
		log.debug("vlcOptions: " + vlcOptions);
		
	}

	/**
	 * Gets a random open port
	 * @return the port, open as of being returned
	 * @throws IOException
	 */
	private int getRandomOpenPort() throws IOException {
		ServerSocket s = new ServerSocket(0);
		int port = s.getLocalPort();
		s.close();
		return port;
	}

	/**
	 * Gets an open port in the specified PORT_RANGE_BEGIN and PORT_RANGE_END
	 * @return the port, open as of being returned
	 * @throws IOException
	 */
	private int getPortInRange() throws IOException {
		int port = -1;
		for(port = PORT_RANGE_BEGIN; port <= PORT_RANGE_END; port++){
			try{
				ServerSocket s = new ServerSocket(port);
				break;
			} catch(IOException e){
				log.debug("Port in use, cannot bind: " + port);
			}
			
		}
		
		if(port == -1 && port > PORT_RANGE_END){
			throw new IOException("Could not find a port in the range: " + PORT_RANGE_BEGIN 
					+ " - " + PORT_RANGE_END);
		}
		return port;
	}

	/**
	 * Streams an ISO via the MediaPlayer
	 * @param mediaPath path of the media to stream 
	 * @return the location of the stream
	 * @throws InterruptedException
	 */
	public String streamDVD(String mediaPath) throws InterruptedException {
		
		mediaPlayer.stop();

		System.out.println("Streaming '" + mediaPath + "' to '" + vlcOptions + "'");

		mediaPlayer.prepareMedia("dvd://"+mediaPath, vlcOptions);
		mediaPlayer.parseMedia();
		
		mediaPlayer.play();
		
		System.out.println("Video Track: " + mediaPlayer.getTitle());
		setMediaPath(mediaPath);
		
		return getStreamLocation();
	}
	
	/**
	 * Loads a stream from a given StreamInfo and plays it
	 * @param info
	 * @return the location of the stream
	 * @throws InterruptedException
	 */
	public String loadFromStreamInfo(StreamInfo info) throws InterruptedException {
		System.out.println("Load from stream!");
		mediaPlayer.stop();
		
		mediaPlayer.prepareMedia("dvd://"+info.getMediaPath(), vlcOptions);
		System.out.println("Streaming Loaded '" + info.getMediaPath() + "' to '" + vlcOptions + "'");
		
		mediaPlayer.parseMedia();

		mediaPlayer.play();

		mediaPlayer.setTitle(info.getTitle());
		mediaPlayer.setChapter(info.getChapter());
		mediaPlayer.setPosition(info.getPosition());
		
		setMediaPath(info.getMediaPath());
		
		System.out.println("All done!");
		
		return getStreamLocation();
	}
	
	/**
	 * wrapper functions for the MediaPlayer
	 */
	
	public void stopVideo(){
		mediaPlayer.stop();
	}
	
	public void pauseVideo(){
		mediaPlayer.pause();
	}
	
	public void resumeVideo(){
		mediaPlayer.play();
	}
	
	public void unloadStream(){
		mediaPlayer.release();
	}

	public void menuDown(){
		mediaPlayer.menuDown();
	}
	
	public void menuUp(){
		mediaPlayer.menuUp();
	}
	
	public void menuLeft(){
		mediaPlayer.menuLeft();
	}
	
	public void menuRight(){
		mediaPlayer.menuRight();
	}
	
	public void menuActivate(){
		mediaPlayer.menuActivate();
	}
	
	public void incrementChapter(){
		mediaPlayer.nextChapter();
	}
	
	public void incrementTitle(){
		int nextTitle = mediaPlayer.getTitle() + 1;
		mediaPlayer.setTitle(nextTitle);
	}

	public void decrementChapter(){
		int prevChapter = mediaPlayer.getChapter() - 1;
		if(prevChapter > 0){
			mediaPlayer.setChapter(prevChapter);
		}
	}
	
	public void decrementTitle(){
		int prevTitle = mediaPlayer.getTitle() - 1;
		if(prevTitle > 0){
			mediaPlayer.setTitle(prevTitle);
		}
	}
	
	public void skip(long skipTime){
		mediaPlayer.skip(skipTime);
	}
	
	/**
	 * gets the current stream info 
	 * @return
	 */
	public StreamInfo getCurrentStreamInfo(){
		return new StreamInfo(mediaPlayer, mediaPath);
	}
		
	public String getStreamLocation(){
		return STREAM_HOST + ":" + STREAM_PORT;
	}
	
	/**
	 * formats the https stream to pass to libVLC
	 * @param serverAddress
	 * @param serverPort
	 * @return a String formatted to pass to libVLC for streaming
	 */
	private String formatHttpStream(String serverAddress, int serverPort) {
		StringBuilder sb = new StringBuilder(60);
		sb.append(":sout=#duplicate{dst=std{access=http,mux=ts,");
		sb.append("dst=");
		sb.append(serverAddress);
		sb.append(':');
		sb.append(serverPort);
		sb.append("}}");
		return sb.toString();
	}
	
	private void setMediaPath(String path){
		mediaPath = path;
	}
	
}
