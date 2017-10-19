/*
 * 
 */
package com.piksel.ghost;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.piksel.ghost.exception.NotWordException;
import com.piksel.ghost.exception.WinException;
import com.piksel.ghost.exception.WordException;
import com.piksel.ghost.utils.GhostUtils;

/**
 * Handles requests for the application home page.
 */
@Controller
public class GhostController {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(GhostController.class);
	
	private Ghost ghost = new Ghost();
//	private Ghost ghost = new Ghost("/Users/usuario/workspaces/ultimo/ghost/src/main/resources/com.piksel.ghost.dictionary/word.lst", false);
//	private Ghost ghost = new Ghost(5);

	/**
	 * Simply selects the home view to render by returning its name.
	 *
	 * @param locale
	 *            the locale
	 * @param model
	 *            the model
	 * @return the string
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome to the ghost game...");

		model.addAttribute("minimunWordSize", ghost.getMinimunWordSize());
		
		model.addAttribute("notWordException", NotWordException.class.getName());
		model.addAttribute("wordException", WordException.class.getName());
		model.addAttribute("winException", WinException.class.getName());
		return "home";
	}

	/**
	 * Play.
	 *
	 * @param userPlay
	 *            the userPlay
	 * @param locale
	 *            the locale
	 * @param model
	 *            the model
	 * @return the response entity
	 */
	@RequestMapping(value = "/play", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> play(@RequestParam("userPlay") String userPlay, Locale locale, Model model) {
		logger.info("play...");
		logger.info("userPlay: " + userPlay);
		String json = null;
		Map<String, Object> mapValues = new HashMap<String, Object>();
		try {
			userPlay = ghost.play(userPlay);
			mapValues.put("ghostPlay", userPlay);
		} catch (NotWordException e) {
			mapValues.put("endPlay", e.getClass().getName());
			mapValues.put("endMessage", e.getMessage());
		} catch (WordException e) {
			mapValues.put("endPlay", e.getClass().getName());
			mapValues.put("endMessage", e.getMessage());
		} catch (WinException e) {
			mapValues.put("endPlay", e.getClass().getName());
			mapValues.put("endMessage", e.getMessage());
		}
		json = GhostUtils.buildJSON(mapValues);
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

}
