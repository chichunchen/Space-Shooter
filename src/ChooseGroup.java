
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.* ;
import javax.swing.event.*;

public class ChooseGroup extends JPanel implements ActionListener
{
	private static final String[] backgroundPath = {"Image/background1.png", "Image/background2.png", "Image/background3.png", "Image/background4.png"};
	private Image backgroundImage;
	private JFrame menuFrame;
	private JLabel nameLable;
	private JButton heroBtn;
	private JButton enemyBtn;
	private JButton back;
	private Audio audio;

	public ChooseGroup( JFrame mframe )
	{
		menuFrame = mframe;
		setLayout( null );

		audio = new Audio();
		Random randomGenerator = new Random();
		loadBackgroundImage( backgroundPath[ randomGenerator.nextInt( backgroundPath.length ) ] );

		nameLable = new JLabel( "Choose the Group" );
		nameLable.setForeground(Color.white);
		nameLable.setFont( new Font("PLAIN", Font.PLAIN, 66) );
		add(nameLable);

		heroBtn = new JButton("Hero");
		heroBtn.setFont(new Font("Arial", Font.PLAIN, 28));
		heroBtn.addActionListener( this );
		add(heroBtn);

		enemyBtn = new JButton("Enemy");
		enemyBtn.setFont(new Font("Arial", Font.PLAIN, 28));
		enemyBtn.addActionListener( this );
		add(enemyBtn);

		back = new JButton("BACK");
		back.setFont(new Font("Arial", Font.PLAIN, 28));
		back.addActionListener( this );
		add(back);

		nameLable.setBounds(100, 20, 650, 150);
		heroBtn.setBounds(310, 200, 180, 50);
		enemyBtn.setBounds(310, 280, 180, 50);
		back.setBounds(310, 360, 180, 50);
	}

	public void paintComponent( Graphics g )
	{
		super.paintComponent( g );
		Graphics2D g2d = (Graphics2D)g;
		
		g2d.drawImage(backgroundImage, 0, 0, this);
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
		if ( event.getSource() == heroBtn ) 
		{
			audio.start("Audio/button_click.wav", 1);
			
			menuFrame.remove( this );
			menuFrame.add( new PVPChooseCraft( menuFrame ) );
			menuFrame.revalidate();
		}
		if ( event.getSource() == enemyBtn ) 
		{
			audio.start("Audio/button_click.wav", 1);
			
			menuFrame.remove( this );
			menuFrame.add( new EnemyGroup() );
			menuFrame.revalidate();
		}
	}

	private void loadBackgroundImage(String image) 
	{
        String imagePath = image;
        ImageIcon ii = new ImageIcon(this.getClass().getResource(imagePath));
        backgroundImage = ii.getImage();
    }
}