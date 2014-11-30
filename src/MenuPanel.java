
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.* ;
import javax.swing.event.*;

public class MenuPanel extends JPanel implements ActionListener
{
	private JFrame frame;
	private JFrame menuFrame;
	private JButton enter;

	private JButton[] buttons;
	private static final String[] btnNames = {"Stage Mode","PVP Mode" , "Tips", "Rank", "Exit"};
	private JLabel nameLable;

	private static final String[] backgroundPath = {"Image/background1.png", "Image/background2.png", "Image/background3.png", "Image/background4.png"};
	private Image backgroundImage;
	private JButton craftButton[];
	private static final String[] craftPath = {"Image/craft1.png", "Image/craft2.png", "Image/craft3.png"};
	private static final String[] craftTip = {"HP:2 Speed:2", "HP:1 Speed:3", "HP:3 Speed:1"};
	private Audio audio;
	
	public MenuPanel( JFrame mframe )
	{
		setLayout( null );

		menuFrame = mframe;
		audio = new Audio();

		Random randomGenerator = new Random();
		loadBackgroundImage( backgroundPath[ randomGenerator.nextInt( backgroundPath.length ) ] );

		nameLable = new JLabel( "Space Shooter" );
		nameLable.setForeground(Color.white);
		nameLable.setFont( new Font("PLAIN", Font.PLAIN, 80) );
		add(nameLable);

		buttons = new JButton[ btnNames.length ];
		for (int i = 0; i < btnNames.length; i++) 
		{
			buttons[ i ] = new JButton( btnNames[i] );
			buttons[ i ].setFont(new Font("Arial", Font.PLAIN, 28));
			buttons[ i ].addActionListener( this );
			add( buttons[ i ] );
		}

		nameLable.setBounds(130, 20, 730, 150);
		
		buttons[0].setBounds(310, 260, 180, 50);
		buttons[1].setBounds(310, 320, 180, 50);
		buttons[2].setBounds(310, 380, 180, 50);
		buttons[3].setBounds(310, 440, 180, 50);
		buttons[4].setBounds(310, 500, 180, 50);
	}

	public void actionPerformed(ActionEvent event)
	{
		if(event.getSource() == buttons[0]) //Stage Mode
		{	
			menuFrame.remove( this );
			StageChooseCraft stageChooseCraft = new StageChooseCraft( menuFrame );
			menuFrame.add( stageChooseCraft );
			menuFrame.revalidate();
		}
		else if (event.getSource() == buttons[1]) //PVP Mode
		{			
			menuFrame.remove( this );
			ChooseGroup chooseGroup = new ChooseGroup( menuFrame );
			menuFrame.add( chooseGroup );
			menuFrame.revalidate();
		}
		else if (event.getSource() == buttons[2]) //Tips
		{		
			menuFrame.remove( this );
			Tips tips = new Tips( menuFrame );
			menuFrame.add( tips );
			menuFrame.revalidate();
		}
		else if (event.getSource() == buttons[3]) //Rank
		{	
			menuFrame.remove( this );
			Rank rank = new Rank( menuFrame );
			menuFrame.add( rank );
			menuFrame.revalidate();
		}
		else if (event.getSource() == buttons[4]) //exit
		{	
			System.exit(0); 
		}

		//////////////////////////////////////////////////////////////////////////////////

		
		audio.start("Audio/button_click.wav", 1);
	}

	private void loadBackgroundImage(String image) 
	{
        String imagePath = image;
        ImageIcon ii = new ImageIcon(this.getClass().getResource(imagePath));
        backgroundImage = ii.getImage();
    }

    public void paintComponent(Graphics g) 
    {
    	super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;

    	g2d.drawImage(backgroundImage, 0, 0, this);
    }
}

