/*
 * 
 */
package com.piksel.ghost;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.log4j.Logger;

import com.piksel.ghost.exception.NotWordException;
import com.piksel.ghost.exception.WinException;
import com.piksel.ghost.exception.WordException;


// TODO: Auto-generated Javadoc
/**
 * The Class Ghost.
 */
public class Ghost {

	/** The log. */
	private static Logger LOG = Logger.getLogger(Ghost.class);

	/** The Constant MINIMUN_WORD_SIZE. */
	private static int MINIMUN_WORD_SIZE = 4;

	/** The matrix. */
	private Hashtable<String, List<String>> matrix = new Hashtable<String, List<String>>();

	private static boolean readDictionaryFromClassPath = true;

	/** The dictionary. */
	private static String internalDictionary = "com.piksel.ghost.dictionary/word.lst";
	
	private static String dictionary;
	
	/**
	 * Instantiates a new ghost. Initializate word list with internal dictionary and default minimun word
	 */
	public Ghost() {
		super();
		readDictionaryFromClassPath = true;
		dictionary = internalDictionary;
		this.readWordList();
	}
	
	/**
	 * Instantiates a new ghost. Initializate word list with internal dictionary and user minimun word
	 *
	 * @param minimunWordSize the minimun word size
	 */
	public Ghost(int minimunWordSize) {
		super();
		// it is so easy to make it work for all desired words
		MINIMUN_WORD_SIZE = minimunWordSize;
		dictionary = internalDictionary;
		readDictionaryFromClassPath = true;
		this.readWordList();
	}
	
	/**
	 * Instantiates a new ghost. Initializate word list with dictionary external or internal and user minimun word
	 *
	 * @param minimunWordSize the minimun word size
	 * @param dictionaryFile the dictionary file
	 */
	public Ghost(int minimunWordSize, String dictionaryFile, boolean readFromClassPath) {
		super();
		MINIMUN_WORD_SIZE = minimunWordSize;
		dictionary = dictionaryFile;
		readDictionaryFromClassPath = readFromClassPath;
		this.readWordList();
	}
	
	/**
	 * Instantiates a new ghost. Initializate word list with dictionary external or internal and user minimun word
	 *
	 * @param minimunWordSize the minimun word size
	 * @param dictionaryFile the dictionary file
	 */
	public Ghost(String dictionaryFile, boolean readFromClassPath) {
		super();
		dictionary = dictionaryFile;
		readDictionaryFromClassPath = readFromClassPath;
		this.readWordList();
	}

	/**
	 * Play.
	 *
	 * @param wordPlayed the word played by user
	 * @return the play of computer
	 * @throws NotWordException the not word exception, user play is not a word
	 * @throws WinException the win exception, player has won
	 * @throws WordException the word exception, user play is a word
	 */
	public String play(String wordPlayed) throws NotWordException, WinException, WordException {
		LOG.info("starting play: " + wordPlayed);
		String previousWordPlayed = wordPlayed.substring(0, wordPlayed.length() - 1);
		LOG.debug("previousWordPlayed: " + previousWordPlayed);

		if (wordPlayed.length() >= MINIMUN_WORD_SIZE && this.matrix.get(previousWordPlayed).contains(wordPlayed)) {
			LOG.info("YOU LOOSE: "+WordException.class.getName());
			throw new WordException();
		}

		List<String> listForWordPlayed = this.matrix.get(wordPlayed);
		if (listForWordPlayed == null || listForWordPlayed.size() == 0) {
			String posiblePlays = getPossiblePlaysMessage(previousWordPlayed);
			LOG.info("YOU LOOSE: "+NotWordException.class.getName());
			throw new NotWordException(posiblePlays);
		}

		String result = null;
		List<String> availableWords = getAvailableWords(wordPlayed);
		String winnerPlay = winnerPlay(availableWords, wordPlayed);
		if (winnerPlay != null) {
			result = winnerPlay;
		} else {
			LOG.debug("NO WINNING MOVES...");
			result = randomPlay(availableWords, wordPlayed);
			if (result.length() >= MINIMUN_WORD_SIZE && listForWordPlayed.contains(result)) {
				String posiblePlays = getPossiblePlaysMessage(wordPlayed);
				throw new WinException(posiblePlays);
			}
		}
		return result;
	}

	/**
	 * Random play.
	 *
	 * @param availableWords the available words
	 * @param wordPlayed the word played
	 * @return the random play of computer
	 */
	private String randomPlay(List<String> availableWords, String wordPlayed) {
		LOG.debug("random play");
		LOG.debug("availableWords");
		LOG.debug(availableWords);
		int maxLength = 0;
		List<String> maxLengthWords = new ArrayList<String>();
		for (String word : availableWords) {
			if (word.indexOf(wordPlayed) == 0 && word.length() > maxLength) {
				maxLength = word.length();
				maxLengthWords = new ArrayList<String>();
			}
			if (word.indexOf(wordPlayed) == 0 && word.length() == maxLength) {
				maxLengthWords.add(word);
			}
		}
		LOG.debug("maxLengthWords: " + maxLengthWords);
		int random = getRandomNumber(maxLengthWords.size());
		String result = maxLengthWords.get(random);
		LOG.debug("random word: " + result);
		result = result.substring(0, wordPlayed.length() + 1);
		return result;
	}

	/**
	 * Winner play. return a considered as winner play move for computer
	 *
	 * @param availableWords the available words
	 * @param wordPlayed the word played
	 * @return the string
	 */
	private String winnerPlay(List<String> availableWords, String wordPlayed) {
		LOG.debug("winnerPlay - init");
		int wordPlayedSize = wordPlayed.length();
		LOG.debug("wordPlayedSize: " + wordPlayedSize);
		List<Integer> desiredWordSizes = new ArrayList<Integer>();

		// first we calculate the maximun word available in the list
		int maximunWordAvailable = 0;
		for (String availableWord : availableWords) {
			if (availableWord.length() > maximunWordAvailable) {
				maximunWordAvailable = availableWord.length();
			}
		}
		LOG.debug("maximunWordAvailable: " + maximunWordAvailable);

		// we want words that completes on opposite player turn
		for (int i = wordPlayedSize; i < maximunWordAvailable; i++) {
			if (i >= MINIMUN_WORD_SIZE && hasDesiredSize(i, wordPlayed)) {
				desiredWordSizes.add(i);
			}
		}
		LOG.debug("desiredWordSizes: " + desiredWordSizes);

		List<String> desiredWords = new ArrayList<String>();
		for (String word : availableWords) {
			if (desiredWordSizes.contains(word.length())) {
				desiredWords.add(word);
				LOG.debug("desiredWord: " + word);
			}
		}

		String result = null;
		if (desiredWords.size() > 0) {
			// ghost game description says that the program should play random with the 
			// possible winning moves, to improve dificulty it I would force to the to play
			// random, but in the sublist of desired words with less length
			int random = getRandomNumber(desiredWords.size());
			result = desiredWords.get(random).substring(0, wordPlayedSize + 1);
			LOG.info("YOU PLAYED: \"" + wordPlayed);
			LOG.info("COMPUTER PLAYS: " + result);
		}
		LOG.debug("winnerPlay - end");
		return result;
	}

	/**
	 * Has desired size.
	 *
	 * @param i the i
	 * @param wordPlayed the word played
	 * @return true or false
	 */
	private boolean hasDesiredSize(int i, String wordPlayed) {
		// if word played length is even it is means that player has started the game
		if (wordPlayed.length() % 2 != 0) {
			return i % 2 != 0;
		} else {
			return i % 2 == 0;
		}
	}

	/**
	 * Gets the random number.
	 *
	 * @param size the size
	 * @return the random number
	 */
	private int getRandomNumber(int size) {
		LOG.debug("size: " + size);
		int random = (int) ((Math.random() * 1000) % size);
		LOG.debug("random index: " + random);
		return random;
	}

	/**
	 * Gets the available words.
	 *
	 * @param wordPlayed the word played
	 * @return the available words
	 * @throws NotWordException the not word exception
	 */
	private List<String> getAvailableWords(String wordPlayed) {
		LOG.debug("getAvailableWords - init");
		wordPlayed = wordPlayed.toLowerCase();
		LOG.debug("wordPlayed: " + wordPlayed);
		List<String> result = new ArrayList<String>();
		List<String> listForWordPlayed = this.matrix.get(wordPlayed);
		if (listForWordPlayed != null) {
			for (String word : listForWordPlayed) {
				//we should add a word that its size is lower than minimun if that word lets to form other words
				if (word.length() < MINIMUN_WORD_SIZE && this.matrix.get(word) != null
						&& this.matrix.get(word).size() > 0) {
					result.add(word);
					LOG.debug("word available: " + word);
				}else if (!isWordPrefixInList(result, word)) {
					//we add a word only if there is no other that already contains that word in the result list
					result.add(word);
					LOG.debug("word available: " + word);
				}else{
					LOG.debug("ignoring: "+word);
				}
			}
		}
		return result;
	}

	/**
	 * Gets the possible plays message for word played.
	 *
	 * @param wordPlayed the word played
	 * @return the possible plays
	 */
	private String getPossiblePlaysMessage(String wordPlayed) {
		List<String> listOfWordPlayed = matrix.get(wordPlayed);
		List<String> listOfWordAdded = new ArrayList<String>();
		String result = "";
		if (listOfWordPlayed != null && listOfWordPlayed.size() > 0) {
			for (int i = 0; i < listOfWordPlayed.size(); i++) {
				String word = listOfWordPlayed.get(i);
				if(!isWordPrefixInList(listOfWordAdded, word)){
					result  += word + ", ";
					listOfWordAdded.add(word);
				}
			}
			result = result.substring(0, result.length()-2);
		}
		return result;
	}

	/**
	 * Is word in list.
	 *
	 * @param list the list of words
	 * @param word the word played
	 * @return true, if successful
	 */
	private boolean isWordPrefixInList(List<String> list, String word) {
		LOG.debug("isWordPrefixInList");
		// if the last word added length it's lower than MINIMUN_WORD_SIZE we
		// should add the next word
		boolean result = false;
		if(list.size() > 0){
			// we check with the last member of available word because we
			// suppouse that the list of words is ordered
			String lastWordOfList = list.get(list.size() - 1);
			result = lastWordOfList.length() >= MINIMUN_WORD_SIZE && word.indexOf(lastWordOfList) == 0;
			LOG.debug("checking word: " + word + " against " + list.get(list.size() - 1) + " result=" + result);
		}
		return result;
	}

	/**
	 * Read word list from file.
	 */
	private void readWordList() {
		LOG.debug("readWordList - init");
		InputStream inputStream = null;
		try {
			if (readDictionaryFromClassPath) {
				inputStream = this.getClass().getResourceAsStream("/" + dictionary);
			} else {
				inputStream = new FileInputStream(dictionary);
			}
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			String word;
			while ((word = bufferedReader.readLine()) != null) {
				int wordLength = word.length();
				for (int i = 1; i < wordLength; i++) {
					String key = word.substring(0, i);
					if (!matrix.containsKey(key)) {
						matrix.put(key, new ArrayList<String>());
					}
					List<String> keyList = matrix.get(key);
					keyList.add(word);
					matrix.put(key, keyList);
				}
			}
			LOG.debug("matrix size: " + matrix.size());
			// we should order the list if we dont think that the dictionary is sorter
			inputStream.close();
		} catch (FileNotFoundException e) {
			LOG.error(e);
		} catch (IOException e) {
			LOG.error(e);
		}
		LOG.debug("readWordList - end");
	}

	public int getMinimunWordSize() {
		return MINIMUN_WORD_SIZE;
	}


}
