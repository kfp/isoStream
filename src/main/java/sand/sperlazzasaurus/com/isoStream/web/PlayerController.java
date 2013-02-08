package sand.sperlazzasaurus.com.isoStream.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sand.sperlazzasaurus.com.isoStream.config.IsoStreamConstants;
import sand.sperlazzasaurus.com.isoStream.model.StreamInfo;
import sand.sperlazzasaurus.com.isoStream.model.StreamingVideoPlayer;

import com.google.gson.Gson;

/**
 * Controller for items related to the player itself (outside of playback)
 * @author elliott
 *
 */
@Controller
@RequestMapping("/player")
public class PlayerController {

	private List<String> availableIsos;

	
	Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private Properties appProps;

	/**
	 * ensures a player exists on the session, reutrns the player page
	 * @param session
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String getPlayerPage(HttpSession session) {
		try {
			getOrCreatePlayer(session);
		} catch (IOException e) {
			return "error?player";
		}
				
		
		return "player";
	}

	/**
	 * Starts streaming a dvd 
	 * @param videoNumber
	 * @param session
	 * @return JSON with the result of starting the stream and the stream location
	 */
	@RequestMapping(value = "play", method = RequestMethod.GET)
	@ResponseBody
	public String playVideo(@RequestParam int videoNumber, HttpSession session) {
		log.debug("Attempting to play movie!");
		
		StreamingVideoPlayer player = 
				(StreamingVideoPlayer) session.getAttribute(IsoStreamConstants.PLAYER_SESSION_KEY);

		Map<String, Object> jsonMap = new HashMap<String, Object>();
		Gson jsonConverter = new Gson();

		try {
			String streamLoc = player.streamDVD(availableIsos.get(videoNumber));
			jsonMap.put("result", "success");
			jsonMap.put("location", streamLoc);
		} catch (InterruptedException e) {
			e.printStackTrace();
			jsonMap.put("result", "failure");
			jsonMap.put("reason", e.getMessage());
		}

		return jsonConverter.toJson(jsonMap);
	}

	/**
	 * moves the dvd menu down
	 * @param session
	 * @return JSON success message
	 */
	@RequestMapping("menuDown")
	@ResponseBody
	public String moveMenuDown(HttpSession session) {
		log.debug("Attempting to menu down!");
		
		StreamingVideoPlayer player = 
				(StreamingVideoPlayer) session.getAttribute(IsoStreamConstants.PLAYER_SESSION_KEY);

		Map<String, Object> jsonMap = new HashMap<String, Object>();
		Gson jsonConverter = new Gson();

		player.menuDown();

		jsonMap.put("result", "success");

		return jsonConverter.toJson(jsonMap);
	}

	/**
	 * moves the dvd menu up
	 * @param session
	 * @return JSON success message
	 */
	@RequestMapping("menuUp")
	@ResponseBody
	public String moveMenuUp(HttpSession session) {
		StreamingVideoPlayer player = 
				(StreamingVideoPlayer) session.getAttribute(IsoStreamConstants.PLAYER_SESSION_KEY);
		
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		Gson jsonConverter = new Gson();

		player.menuUp();

		jsonMap.put("result", "success");

		return jsonConverter.toJson(jsonMap);
	}

	/**
	 * moves the dvd menu left
	 * @param session
	 * @return JSON success message
	 */
	@RequestMapping("menuLeft")
	@ResponseBody
	public String moveMenuLeft(HttpSession session) {
		StreamingVideoPlayer player = 
				(StreamingVideoPlayer) session.getAttribute(IsoStreamConstants.PLAYER_SESSION_KEY);
		
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		Gson jsonConverter = new Gson();

		player.menuLeft();

		jsonMap.put("result", "success");

		return jsonConverter.toJson(jsonMap);
	}

	/**
	 * moves dvd menu right
	 * @param session
	 * @return JSON success message
	 */
	@RequestMapping("menuRight")
	@ResponseBody
	public String moveMenuRight(HttpSession session) {
		StreamingVideoPlayer player = 
				(StreamingVideoPlayer) session.getAttribute(IsoStreamConstants.PLAYER_SESSION_KEY);
		
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		Gson jsonConverter = new Gson();

		player.menuRight();

		jsonMap.put("result", "success");

		return jsonConverter.toJson(jsonMap);
	}

	/**
	 * selects the currently activated item on a dvd menu
	 * @param session
	 * @return JSON success message
	 */
	@RequestMapping("menuActivate")
	@ResponseBody
	public String activateMenu(HttpSession session) {
		StreamingVideoPlayer player = 
				(StreamingVideoPlayer) session.getAttribute(IsoStreamConstants.PLAYER_SESSION_KEY);
		
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		Gson jsonConverter = new Gson();

		player.menuActivate();

		jsonMap.put("result", "success");

		return jsonConverter.toJson(jsonMap);
	}

	/**
	 * Stops the current stream
	 * @param session
	 * @return JSON success message
	 */
	@RequestMapping("unload")
	@ResponseBody
	public String unload(HttpSession session) {
		StreamingVideoPlayer player = 
				(StreamingVideoPlayer) session.getAttribute(IsoStreamConstants.PLAYER_SESSION_KEY);
		
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		Gson jsonConverter = new Gson();

		player.stopVideo();

		// player.unloadStream();

		jsonMap.put("result", "success");

		return jsonConverter.toJson(jsonMap);
	}

	/**
	 * Gets the current stream info and saves it as a cookie
	 * @param session
	 * @param response
	 * @return JSON success message with StreamInfo
	 */
	@RequestMapping("getStreamInfo")
	@ResponseBody
	public String getStreamInfo(HttpSession session, HttpServletResponse response) {
		StreamingVideoPlayer player = 
				(StreamingVideoPlayer) session.getAttribute(IsoStreamConstants.PLAYER_SESSION_KEY);
		
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		Gson jsonConverter = new Gson();

		StreamInfo info = player.getCurrentStreamInfo();

		jsonMap.put("result", "success");
		jsonMap.put("StreamInfo", info);
		
		Cookie cookie = new Cookie(IsoStreamConstants.LAST_PLAYED_COOKIE, jsonConverter.toJson(info));
		cookie.setMaxAge(31536000);
		response.addCookie(cookie);
		
		return jsonConverter.toJson(jsonMap);
	}

	/**
	 * gets all ISO files and maps them to an order so that real paths are not used (and not abused)
	 * @param session
	 * @return json success and the paths to the ISO files (with the base path hidden)
	 * @throws IOException
	 */
	@RequestMapping("getFiles")
	@ResponseBody
	public String getFiles(HttpSession session) throws IOException {
		StreamingVideoPlayer player = 
				(StreamingVideoPlayer) session.getAttribute(IsoStreamConstants.PLAYER_SESSION_KEY);
		
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		Gson jsonConverter = new Gson();

		availableIsos = new ArrayList<String>();
		
		String[] command = new String[]{"find", 
				appProps.getProperty("base.location"), "-name", "*.iso"};
		Process pr =  Runtime.getRuntime().exec(command);

		BufferedReader br = new BufferedReader(new InputStreamReader(pr.getInputStream()));
		String line;
		while ((line = br.readLine()) != null) {
			availableIsos.add(line);
		}
		
		Collections.sort(availableIsos);
		
		jsonMap.put("result", "success");
		
		jsonMap.put("files", availableIsos);
		
		jsonMap.put("basePath", appProps.getProperty("base.location"));

		return jsonConverter.toJson(jsonMap);
	}

	/**
	 * keeps the session alive while playing the dvd
	 * @param session
	 */
	@RequestMapping("heartbeat")
	public void getHeartbeat(HttpSession session) {
		return;
	}
	
	/**
	 * Loads a saved stream from the steam cookie
	 * @param session
	 * @param lastPlayedCookie
	 * @return JSON success with stream location or failure with reason
	 */
	@RequestMapping("loadSavedStream")
	@ResponseBody
	public String loadSavedStream(HttpSession session, @CookieValue(IsoStreamConstants.LAST_PLAYED_COOKIE) Cookie lastPlayedCookie){
		StreamingVideoPlayer player = 
			(StreamingVideoPlayer) session.getAttribute(IsoStreamConstants.PLAYER_SESSION_KEY);
	
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		Gson jsonConverter = new Gson();
	
		StreamInfo info = jsonConverter.fromJson(lastPlayedCookie.getValue(), StreamInfo.class);

		try {
			String streamLoc = player.loadFromStreamInfo(info);
			jsonMap.put("result", "success");
			jsonMap.put("location", streamLoc);
		} catch (InterruptedException e) {
			e.printStackTrace();
			jsonMap.put("result", "failure");
			jsonMap.put("reason", e.getMessage());
		}	
	
		return jsonConverter.toJson(jsonMap);
	}

	/**
	 * Adds the current stream location to the model for the player page
	 * @param session
	 * @return
	 */
	@ModelAttribute("streamLocation")
	public String getStreamLocation(HttpSession session) {
		StreamingVideoPlayer player;
		try {
			player = getOrCreatePlayer(session);
		} catch (IOException e) {
			return null;
		}
		
		return player.getStreamLocation();
	}
	
	/**
	 * Gets StreamingVideoPlayer from the session or creates a new one if not found.  
	 * @param session
	 * @return a valid StreamingVideoPlayer suitable for streaming ISOs
	 * @throws IOException
	 */
	private StreamingVideoPlayer getOrCreatePlayer(HttpSession session) throws IOException{
		
		StreamingVideoPlayer player = 
			(StreamingVideoPlayer) session.getAttribute(IsoStreamConstants.PLAYER_SESSION_KEY);
		
		if(player == null){
			log.info("Creating player!");
			try {
				player = new StreamingVideoPlayer(appProps.getProperty("stream.ip"), 
						appProps.getProperty("stream.host"));
			} catch (IOException e) {
				log.error("Could not create new StreamingVideoPlayer()");
				throw e;
			}
			
			session.setAttribute(IsoStreamConstants.PLAYER_SESSION_KEY, player);
		}
		
		return player;
	}

}
