/*
 * This exception is launched when user plays doesn't let the computer to play
 */
package com.piksel.ghost.exception;


/**
 * The Class WinException.
 */
public class WinException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4100306319211991219L;

	/**
	 * Instantiates a new win exception.
	 *
	 * @param message the message
	 */
	public WinException(String message) {
		super(message);
	}

}
