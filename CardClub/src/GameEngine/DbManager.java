package GameEngine;

public class DbManager 
{
	// Use stored procedure on Db, instead of sql call here
	public boolean getUserName(String username)
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
}
