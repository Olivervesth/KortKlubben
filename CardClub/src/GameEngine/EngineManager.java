package GameEngine;

public class EngineManager 
{
	/**
	 * Used classes in enginemanager
	 */
	private Hashing hashing = new Hashing();
	private DbManager db = new DbManager();
	private Authentication auth = new Authentication();
	
	/**
	 * Attempts to hash, ask database and validate the response
	 * @param String username
	 * @param String password
	 * @return true/false
	 */
	public boolean login(String username, String password)
	{
		// if login is success
		if(auth.validateUserInfo(db.getUserName(hashing.hash(username)), db.getPassword(hashing.hash(password))))
		{
			// create user?
			return true;
		}
		// if login fails
		else
		{
			// log failed attempt?
			return false;
		}
	}
}
