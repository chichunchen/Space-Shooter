
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.* ;
import javax.swing.event.*;
import java.io.*;

public class Rank extends JPanel implements ActionListener
{
	private JFrame menuFrame;

	public String fromRecordFile;

	public ArrayList<String> playerName;
    public ArrayList<String> craftModelIndex;
    public ArrayList<Integer> stage;
    public ArrayList<Integer> score;
    public ArrayList<Float> time; 

    private JButton back;
    private Audio audio;

	public Rank( JFrame mframe )
	{
		menuFrame = mframe;
		setLayout( null );
		audio = new Audio();

		playerName = new ArrayList<String>();
		craftModelIndex = new ArrayList<String>();
		stage = new ArrayList<Integer>();
		score = new ArrayList<Integer>();
		time = new ArrayList<Float>();

		back = new JButton("BACK");
		back.addActionListener( this );
		back.setFont(new Font("Arial", Font.PLAIN, 28));
		add(back);
		back.setBounds(50, 50, 160, 60);

		readRecord();
	}

	public void readRecord()
	{
		try
		{
			FileInputStream fstream  = new FileInputStream( "Records.txt" );
			DataInputStream in = new DataInputStream( fstream );
          	BufferedReader br = new BufferedReader( new InputStreamReader(in) );
          	String strLine;          	

          	while( (strLine = br.readLine()) != null )
          	{
          		String[] tokens = strLine.split(" ");
          		
          		playerName.add( tokens[ 0 ] );
          		craftModelIndex.add( tokens[ 1 ] );
          		stage.add( Integer.valueOf( tokens[ 2 ] ) );
          		score.add( Integer.valueOf( tokens[ 3 ] ) );
          		time.add( Float.valueOf( tokens[ 4 ] ) );
          	}
          
          	in.close();
		}
		catch( Exception e )
		{
			System.err.println("Error: " + e.getMessage());
		}
	}

	public void printRecord()
	{
		System.out.println(time.get(3));
	}

	public ArrayList<String> scoreSort()
	{
		ArrayList<String> result = new ArrayList<String>();

		// store the initialize score sequence
		ArrayList<Integer> nstore = new ArrayList<Integer>( score );
		Collections.sort(score);
		Collections.reverse(score);
		int[] indexes = new int [ score.size() ];

		for ( int n = 0; n < score.size(); ++n) 
		{
			indexes[n] = nstore.indexOf( score.get(n) );	
		}

		for ( int i = 0; i < score.size() && i < 10; ++i) 
		{
			result.add( ( String.format( "%-5s%-15s%-10s%-10s%-10s%-10s\n"
				, i + 1
				, playerName.get( indexes[ i ] )
				, craftModelIndex.get( indexes[ i ] )
				, stage.get( indexes[ i ] )
				, score.get( i )
				, time.get( indexes[ i ] ) ) ) );
		}

		System.out.print( result );

		return result;
	}

	public void paintComponent( Graphics g )
	{
		super.paintComponent( g );
		setBackground(Color.black);

		g.setColor(Color.white);
		g.setFont( new Font("Helvetica", Font.PLAIN, 60) );
		g.drawString( "Rank #10", 280, 100 );
		
		g.setFont( new Font("Helvetica", Font.PLAIN, 32) );
		g.drawString( String.format( "%-5s%-15s%-10s%-10s%-10s%-10s\n", "NO", "Name", "CraftID", "Stage", "Score", "Time" ), 50, 175);

		g.setFont( new Font("Helvetica", Font.PLAIN, 32) );
		ArrayList<String> result = scoreSort();
		for( int i = 0; i < result.size() && i < 10; ++i )
		{
			g.drawString( result.get( i ), 50, 200 + (i*35) );
		}
		//	drawString( g, scoreSort(), 50, 200 );
	}

/*	private void drawString(Graphics g, String text, int x, int y) 
	{ 
        for ( String line : text.split("\n") )
        	g.drawString(line, x, y += g.getFontMetrics().getHeight());
    }  */

	public void actionPerformed( ActionEvent event )
	{
		if ( event.getSource() == back ) 
		{
			audio.start("Audio/button_click.wav", 1);

			menuFrame.remove( this );
			menuFrame.add( new MenuPanel( menuFrame ) );
			menuFrame.revalidate();
		}
	}
}