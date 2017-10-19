/*
 * 
 */
package com.piksel.ghost.utils;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The Class GhostUtils.
 */
public class GhostUtils {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(GhostUtils.class);
	/**
	 * Builds the json.
	 *
	 * @param mapValues the map values
	 * @return the string
	 */
	public static String buildJSON(Map<String, Object> mapValues) {
		logger.info("buildJSON - start");
		String result = "{";
		for (String key : mapValues.keySet()) {
			String value = (String) mapValues.get(key);
			result += ("\"" + key + "\":\"" + value + "\"") + ",";
		}
		result = result.substring(0, result.length() - 1) + "}";
		logger.info("buildJSON result: " + result);
		return result;
	}
}
