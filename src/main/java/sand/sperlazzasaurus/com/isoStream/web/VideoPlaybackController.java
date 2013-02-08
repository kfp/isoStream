package sand.sperlazzasaurus.com.isoStream.web;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sand.sperlazzasaurus.com.isoStream.config.IsoStreamConstants;
import sand.sperlazzasaurus.com.isoStream.model.StreamingVideoPlayer;

import com.google.gson.Gson;

/**
 * Controller for video playback methods
 * @author elliott
 *
 */
@Controller
@RequestMapping("/playback")
public class VideoPlaybackController {

	Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private Properties appProps;
	
	/**
	 * Stops the current video stream
	 * @param session
	 * @return JSON success message
	 */
	@RequestMapping("stopVideo")
	@ResponseBody
	public String stopVideo(HttpSession session) {
		StreamingVideoPlayer player = 
				(StreamingVideoPlayer) session.getAttribute(IsoStreamConstants.PLAYER_SESSION_KEY);
		
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		Gson jsonConverter = new Gson();

		player.stopVideo();
		player.unloadStream();

		jsonMap.put("result", "success");

		return jsonConverter.toJson(jsonMap);
	}

	/**
	 * pauses the current video stream
	 * @param session
	 * @return JSON success message
	 */
	@RequestMapping("pauseVideo")
	@ResponseBody
	public String pauseVideo(HttpSession session) {
		StreamingVideoPlayer player = 
				(StreamingVideoPlayer) session.getAttribute(IsoStreamConstants.PLAYER_SESSION_KEY);
		
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		Gson jsonConverter = new Gson();

		player.pauseVideo();

		jsonMap.put("result", "success");

		return jsonConverter.toJson(jsonMap);
	}

	/**
	 * Resumes a paused video stream
	 * @param session
	 * @return JSON success message
	 */
	@RequestMapping("resumeVideo")
	@ResponseBody
	public String resumeVideo(HttpSession session) {
		StreamingVideoPlayer player = 
				(StreamingVideoPlayer) session.getAttribute(IsoStreamConstants.PLAYER_SESSION_KEY);
		
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		Gson jsonConverter = new Gson();

		player.resumeVideo();

		jsonMap.put("result", "success");

		return jsonConverter.toJson(jsonMap);
	}
	
	/**
	 * Skips thirty seconds into the current video stream
	 * @param session
	 * @return JSON success message
	 */
	@RequestMapping("skipThirtySeconds")
	@ResponseBody
	public String skipThirtySeconds(HttpSession session) {
		StreamingVideoPlayer player = 
				(StreamingVideoPlayer) session.getAttribute(IsoStreamConstants.PLAYER_SESSION_KEY);
		
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		Gson jsonConverter = new Gson();

		player.skip(30000);

		jsonMap.put("result", "success");

		return jsonConverter.toJson(jsonMap);
	}
	
	/**
	 * Increments stream chapter by one
	 * @param session
	 * @return JSON success message
	 */
	@RequestMapping("incrementChapter")
	@ResponseBody
	public String incrementChapter(HttpSession session) {
		StreamingVideoPlayer player = 
				(StreamingVideoPlayer) session.getAttribute(IsoStreamConstants.PLAYER_SESSION_KEY);
		
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		Gson jsonConverter = new Gson();

		player.incrementChapter();

		jsonMap.put("result", "success");

		return jsonConverter.toJson(jsonMap);
	}

	/**
	 * Increments the current stream title by one 
	 * @param session
	 * @return JSON success message
	 */
	@RequestMapping("incrementTitle")
	@ResponseBody
	public String incrementTitle(HttpSession session) {
		StreamingVideoPlayer player = 
				(StreamingVideoPlayer) session.getAttribute(IsoStreamConstants.PLAYER_SESSION_KEY);
		
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		Gson jsonConverter = new Gson();

		player.incrementTitle();

		jsonMap.put("result", "success");

		return jsonConverter.toJson(jsonMap);
	}

	/**
	 * Decrements the current stream chapter by one
	 * @param session
	 * @return JSON success message
	 */
	@RequestMapping("decrementChapter")
	@ResponseBody
	public String decrementChapter(HttpSession session) {
		StreamingVideoPlayer player = 
				(StreamingVideoPlayer) session.getAttribute(IsoStreamConstants.PLAYER_SESSION_KEY);
		
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		Gson jsonConverter = new Gson();

		player.decrementChapter();

		jsonMap.put("result", "success");

		return jsonConverter.toJson(jsonMap);
	}

	/**
	 * decrements the current stream title by one
	 * @param session
	 * @return JSON success message
	 */
	@RequestMapping("decrementTitle")
	@ResponseBody
	public String decrementTitle(HttpSession session) {
		StreamingVideoPlayer player = 
				(StreamingVideoPlayer) session.getAttribute(IsoStreamConstants.PLAYER_SESSION_KEY);
		
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		Gson jsonConverter = new Gson();

		player.decrementTitle();

		jsonMap.put("result", "success");

		return jsonConverter.toJson(jsonMap);
	}
	
}
