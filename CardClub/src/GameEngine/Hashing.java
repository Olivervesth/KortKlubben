package GameEngine;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Hashing class
 * @author Martin
 *
 */
public class Hashing 
{
	/**
	 * Method to hash a string input
	 * @param String input
	 * @return String hashed input
	 * @return null if hashing failed
	 */
	public String hash(String input)
	{
		try 
		{
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hashedInput = digest.digest(input.getBytes(StandardCharsets.UTF_8));
			return new String(hashedInput);
		} 
		catch (NoSuchAlgorithmException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
}
