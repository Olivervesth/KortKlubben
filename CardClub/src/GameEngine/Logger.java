package GameEngine;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class to log errors
 * @author Martin
 *
 */
public class Logger 
{
	public void saveMessage(String errormessage)
	{
		 try {
			 // filepath and name
		      File nFile = new File("filename.txt");
		      if (nFile.createNewFile()) {
		        System.out.println("File created: " + nFile.getName());
		      } else {
		    	  try {
		    		  // filepath and name
		    	      FileWriter fWriter = new FileWriter("filename.txt");
		    	      fWriter.write("Files in Java might be tricky, but it is fun enough!");
		    	      fWriter.close();
		    	      System.out.println("Successfully wrote to the file.");
		    	    } catch (IOException e) {
		    	      System.out.println("An error occurred.");
		    	      e.printStackTrace();
		    	    }
		      }
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	}
}
