/*
 * This exception is launched when user plays a non word
 */
package com.piksel.ghost.exception;


/**
 * The Class NotWordException.
 */
public class NotWordException extends Exception{

	/**
	 * Instantiates a new not word exception.
	 *
	 * @param message the message
	 */
	public NotWordException(String message) {
		super(message);
	}

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5476836727415480368L;

}
