/*
 * 
 */
package com.piksel.ghost.test;

import org.junit.Test;

import com.piksel.ghost.Ghost;
import com.piksel.ghost.exception.NotWordException;
import com.piksel.ghost.exception.WinException;
import com.piksel.ghost.exception.WordException;


/**
 * The Class JUnitTester.
 */
public class JUnitTester {

	/**
	 * Test ghost.
	 */
	@Test
	public void testGhost() {
		Ghost ghost = new Ghost();
		assert(ghost != null);
	}

	/**
	 * Test win.
	 */
	@Test
	public void testWin(){
		Ghost ghost = new Ghost();
		boolean result = false;
		try {
			ghost.play("deede");
		} catch (NotWordException e) {
		} catch (WordException e) {
		} catch (WinException e) {
			result = true;
		}
		assert(result == true);
	}
	
	/**
	 * Test not word.
	 */
	@Test
	public void testNotWord() {
		Ghost ghost = new Ghost();
		boolean result = false;
		try {
			ghost.play("andd");
		} catch (NotWordException e) {
			result = true;
		} catch (WordException e) {
		} catch (WinException e) {
		}
		assert(result == true);
	}

}
