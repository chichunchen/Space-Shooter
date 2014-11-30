
import java.awt.*;
import java.awt.event.*;
import javax.swing.* ;
import javax.swing.event.*;

public class Tips extends JPanel implements ActionListener
{
	private JFrame menuFrame;
	private JButton back;
	private Audio audio;
	private static final String[] tips = { "Enter name and click the craft button to choose craft."
											, "Click up, down, right, left button to control your craft."
											, "Click P to pause and R to resume the game."
											, "Let's go for your own space travel!!!" };

	public Tips( JFrame mframe )
	{
		menuFrame = mframe;
		setLayout( null );

		back = new JButton("BACK");
		back.setFont(new Font("Arial", Font.PLAIN, 28));
		back.addActionListener( this );
		add(back);
		back.setBounds(310, 450, 180, 50);
	}

	public void paintComponent( Graphics g )
	{
		super.paintComponent( g );
		setBackground(Color.black);

		audio = new Audio();

		g.setColor(Color.white);
		g.setFont( new Font("Helvetica", Font.PLAIN, 60) );
		g.drawString( "Tips", 340, 100 );

		g.setFont( new Font("Helvetica", Font.PLAIN, 28) );
		for ( int i = 0; i < tips.length; ++i) 
		{
			g.drawString( tips[ i ], 40, 150 + (50 * i) );
		}
	}

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