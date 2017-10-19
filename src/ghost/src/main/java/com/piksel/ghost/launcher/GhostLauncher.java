/*
 * Test Class for ghost
 */
package com.piksel.ghost.launcher;

import java.util.Scanner;

import org.apache.log4j.Logger;

import com.piksel.ghost.Ghost;
import com.piksel.ghost.exception.NotWordException;
import com.piksel.ghost.exception.WinException;
import com.piksel.ghost.exception.WordException;


/**
 * The Class GhostLauncher.
 */
public class GhostLauncher {

	/** The log. */
	private static Logger LOG = Logger.getLogger(GhostLauncher.class);

	/**
	 * The main method. You can play from command line java -jar ghost-1.0.0.jar
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		LOG.info("**********************************************************************");
		LOG.info("*    WELLCOME TO GHOST GAME                                          *");
		LOG.info("**********************************************************************");
		LOG.info("*    Description: the ghost game                                     *");
		LOG.info("*                                                                    *");
		LOG.info("*    The ghost game it's a game about to forming words, there is     *");
		LOG.info("*    two player, each turn, one player adds one letter to forming    *");
		LOG.info("*    a word, but if the player puts a word that doesn't form a       *");
		LOG.info("*    word or completes a word, the player will loose, if the letter  *");
		LOG.info("*    it's ok then it's the turn of the other player                  *");
		LOG.info("*                                                                    *");
		LOG.info("**********************************************************************");
		LOG.info("*                                                                    *");
		// prompt for the user's name
		LOG.info("*    Enter your play:                                                *");
		LOG.info("*               a b c d e f g h i j k l m n o p q r s t u v w x y    *");
		LOG.info("*                                                                    *");
		LOG.info("**********************************************************************");
		LOG.info("\n ");

		// create a scanner so we can read the command-line input
		Scanner scanner = new Scanner(System.in);
		// get their input as a String
		String wordPlayed = scanner.next();
		Ghost ghost = new Ghost();
		String result = "";
		try {
			while (result != null) {
				result = ghost.play(wordPlayed);
				LOG.info("**********************************************************************");
				LOG.info("*                                                                    *");
				LOG.info("*    GHOST PLAYS: \"" + result + "\"");
				LOG.info("*                                                                    *");
				LOG.info("**********************************************************************");
				wordPlayed = scanner.next();
			}
		} catch (NotWordException e) {
			LOG.error("**********************************************************************");
			LOG.error("*                                                                    *");
			LOG.error("*    YOU LOOSE!! \"" + wordPlayed + "\" is not a word.               *");
			LOG.error("*                                                                    *");
			LOG.error("*   Still playables previous moves                                   *");
			LOG.error("*   " + e.getMessage());
			LOG.error("*                                                                    *");
			LOG.error("**********************************************************************");
		} catch (WordException e) {
			LOG.error("**********************************************************************");
			LOG.error("*                                                                    *");
			LOG.error("*    YOU LOOSE!! \"" + wordPlayed + "\" is a word.                   *");
			LOG.error("*                                                                    *");
			LOG.error("*                                                                    *");
			LOG.error("**********************************************************************");
		} catch (WinException e) {
			LOG.error("**********************************************************************");
			LOG.error("*                                                                    *");
			LOG.error("*  YOU WIN!!                                                         *");
			LOG.error("*                                                                    *");
			LOG.error("*    the computer has no available moves                             *");
			LOG.error("*                                                                    *");
			LOG.error("*    posible moves are words                                         *");
			LOG.error("*                                                                    *");
			LOG.error("*   " + e.getMessage());
			LOG.error("*                                                                    *");
			LOG.error("**********************************************************************");
		}
		scanner.close();
	}
}
