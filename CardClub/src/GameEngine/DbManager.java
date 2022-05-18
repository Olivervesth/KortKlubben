package GameEngine;

public class DbManager 
{
	/**
	 * Fields for DbManager
	 */
	private String connectionString;
	
	/**
	 * Constructor for DbManager
	 * @param String connectionString
	 */
	public DbManager(String connectionString)
	{
		this.connectionString = connectionString;
	}
	
	// Use stored procedure on Db, instead of sql call here
	public boolean getUserName(String userName)
	{
		int count = 0;
		// count -> SELECT count(*) FROM Users WHERE Name = username
		if(count > 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	// Use stored procedure on Db, instead of sql call here
	public boolean getPassword(String password)
	{
		int count = 0;
		// count -> SELECT count(*) FROM Users WHERE Password = password
		if(count > 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public String getStats(String userName)
	{
		// TODO implement get player stats
		return "";
	}
}
