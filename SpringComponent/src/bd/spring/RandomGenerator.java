package bd.spring;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

/**
 * Utility class for loading different types of random text.
 * @author desoub3
 *
 */
public class RandomGenerator {

	private static Random generator = new Random();
	
	private static Properties properties;
	static {
		// Java 7 auto-closing try-catch block (InputStream implements AutoClosable interface indirectly)
		try (InputStream is = RandomGenerator.class.getResourceAsStream("RandomGenerator.properties")){
			properties = new Properties();
			properties.load(is);
		} catch(Exception e) {
			throw new ExceptionInInitializerError("Unable to load RandomGenerator.properties: " + e.toString());
		}
	}
	
	/**
	 * Initialize RandomGenerator.
	 */
	public RandomGenerator() {

	}
	
	/**
	 * Count the number of random question propertis defined in the properties file.
	 * @return
	 */
	private static int countRandomQuestionsInProperties() {
		int count = 0;
		Set<Object> keys = properties.keySet();
		Iterator<Object> keyIter = keys.iterator();
		while(keyIter.hasNext()) {
			String key = (String)keyIter.next();
			if(key.startsWith("randomQuestion")) {
				count++;
			}
		}
		return count;
	}
	
	/**
	 * Retrieve a random quesiton string from properties based on a numeric index.
	 * @param index
	 * @throws IllegalArgumentException when a random question string with the given index cannot be found
	 * @return
	 */
	private static String getQuestionFromProperties(int index) throws IllegalArgumentException {
		String randomQuestion = null;

		Set<Object> keys = properties.keySet();
		Iterator<Object> keyIter = keys.iterator();
		while(keyIter.hasNext()) {
			String key = (String)keyIter.next();
			if(key.equals("randomQuestion." + index)) {
				randomQuestion = properties.getProperty(key);
			}
		}
		
		if(randomQuestion == null) {
			throw new IllegalArgumentException("Random question index not found in configuration: " + index);
		}
		
		return randomQuestion;
	}
	
	/**
	 * Retrieve a random question string from the properties file.
	 * @return
	 */
	public String getRandomQuestion() {
		String randomQuestion = null;
		int randomInt = generator.nextInt(countRandomQuestionsInProperties());
		
		try { 
			randomQuestion = getQuestionFromProperties(randomInt);
		} catch(IllegalArgumentException e) {
			randomQuestion = "Exception encountered: " + e.toString();
		}
		
		return randomQuestion;
	}
	
	/**
	 * Retrieve a specific question from the properties file.
	 * @param index
	 * @return
	 */
	public String getSpecificQuestion(int index) {
		String specificQuestion = null;
		
		try { 
			specificQuestion = getQuestionFromProperties(index);
		} catch(IllegalArgumentException e) {
			specificQuestion = "Exception encountered: " + e.toString();
		}
		
		return specificQuestion;
	}
}
