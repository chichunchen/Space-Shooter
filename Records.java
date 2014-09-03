
import java.io.*;

public class Records 
{
	public String output;
	public Board gameBoard;

	public Records( Board board )
	{
		gameBoard = board;
	}

	public void setOutput()
	{
		output = gameBoard.getPlayerName() + " " 
		+ gameBoard.getCraftModelIndex() + " " 
		+ gameBoard.getStage() + " " 
		+ gameBoard.getScore() + " " 
		+ gameBoard.getCurrentTime();
	}

	public void writeFile()
	{
		try
		{
			File file = new File("Records.txt");

			if (!file.exists()) 
			{
				file.createNewFile();	
			}

			FileWriter fileWritter = new FileWriter( file.getName(), true );
			BufferedWriter bufferWritter = new BufferedWriter( fileWritter );
			bufferWritter.newLine();
    	    bufferWritter.write( output );
    	    bufferWritter.close();

    	    //System.out.println("Done");
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}