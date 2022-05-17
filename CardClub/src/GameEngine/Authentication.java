package GameEngine;

/**
 * Class to validate information
 * @author Martin
 *
 */
public class Authentication 
{
	/**
	 * Takes input from other class's methods for validation
	 * This method "depends" on DbManagers output from getUserName and getPassword
	 * @param boolean username
	 * @param boolean password
	 * @return true/false
	 */
	public boolean validateUserInfo(boolean username, boolean password)
	{
		if( username && password)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
