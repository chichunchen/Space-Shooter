
import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;

public class Audio 
{
	private Clip clip;

	public Audio()
	{

	}

	public void menuMusic()
	{
		//start( "" );
	}

	public void gameMusic()
	{
		start( "Audio/music_background.wav", 2 );
	}

	public void gameOverMusic()
	{
		start( "Audio/End_of_Game.wav", 2 );
	}

	public void start( String path, int n )
	{
		try 
		{
            // Open an audio input stream.
         	URL url = this.getClass().getClassLoader().getResource( path );
         	AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
         	// Get a sound clip resource.
         	clip = AudioSystem.getClip();
         	// Open audio clip and load samples from the audio input stream.
	        clip.open(audioIn);

	        if( n == 1 )
	        {
	        	clip.start();
	        }
	        else if( n == 2 )
	        {
	        	clip.loop(Clip.LOOP_CONTINUOUSLY);
	        }
      	} 
      	catch (UnsupportedAudioFileException e) 
      	{
         	e.printStackTrace();
      	} 
      	catch (IOException e) 
      	{
         	e.printStackTrace();
      	} 
      	catch (LineUnavailableException e) 
      	{
            e.printStackTrace();
      	}
	}

	public void stop()
	{
		if (clip.isRunning()) 
			clip.stop();
	}
}