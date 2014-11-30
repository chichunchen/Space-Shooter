
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.* ;
import javax.swing.event.*;

public class PVPChooseCraft extends JPanel implements ActionListener
{
	private JFrame frame;
	private JFrame menuFrame;
	private Audio audio;
	private static final String[] backgroundPath = {"Image/background1.png", "Image/background2.png", "Image/background3.png", "Image/background4.png"};
	private Image backgroundImage;

	private JLabel nameLable;
	private JTextField inputBox;
	private JButton back;
	private JButton craftButton[];
	private static final String[] craftPath = {"Image/craft1.png", "Image/craft2.png", "Image/craft3.png"};
	private static final String[] craftTip = {"HP:2 Speed:2", "HP:1 Speed:3", "HP:3 Speed:1"};
	private String playerName;

	public PVPChooseCraft( JFrame mframe )
	{
		setLayout( null );

		menuFrame = mframe;
		audio = new Audio();

		Random randomGenerator = new Random();
		loadBackgroundImage( backgroundPath[ randomGenerator.nextInt( backgroundPath.length ) ] );

		nameLable = new JLabel( "PVP Mode" );
		nameLable.setForeground(Color.white);
		nameLable.setFont( new Font("PLAIN", Font.PLAIN, 66) );
		add(nameLable);

		back = new JButton("BACK");
		back.setFont(new Font("Arial", Font.PLAIN, 28));
		back.addActionListener( this );
		add(back);

		inputBox = new JTextField("Your name");
		inputBox.setFont( new Font("PLAIN", Font.PLAIN, 20) );
		add(inputBox);

		loadCraftImage();
		
		nameLable.setBounds(130, 20, 730, 150);
		inputBox.setBounds(150, 200, 220, 50);
		back.setBounds(170, 260, 180, 50);
		craftButton[0].setBounds(450, 230, 100, 100);
		craftButton[1].setBounds(450, 340, 100, 100);
		craftButton[2].setBounds(450, 450, 100, 100);
	}

	private void loadCraftImage() 
    {
    	craftButton = new JButton[ craftPath.length ];
		Icon craftIcon;

		for (int i = 0; i < craftPath.length; i++) 
		{
			craftIcon = new ImageIcon( this.getClass().getResource( craftPath[ i ] ) );

			craftButton[ i ] = new JButton( craftIcon );
			craftButton[ i ].setBackground(Color.BLACK);
			craftButton[ i ].setOpaque(true);
			craftButton[ i ].setBorderPainted(false);
			craftButton[ i ].setForeground(Color.WHITE);
			craftButton[ i ].setToolTipText(craftTip[ i ]);
			craftButton[ i ].addActionListener( this );
			add( craftButton[ i ] );
		}
    }

	public void paintComponent( Graphics g )
	{
		super.paintComponent( g );
		Graphics2D g2d = (Graphics2D)g;
		
		g2d.drawImage(backgroundImage, 0, 0, this);
	}

	public void actionPerformed( ActionEvent event )
	{
		playerName = inputBox.getText();

		if ( event.getSource() == back ) 
		{
			audio.start("Audio/button_click.wav", 1);
			
			menuFrame.remove( this );
			menuFrame.add( new MenuPanel( menuFrame ) );
			menuFrame.revalidate();
		}

		for (int i = 0; i < craftButton.length; i++) 
		{
			if(event.getSource() == craftButton[i]) 
			{
				if(playerName.length() > 0 && playerName.equals("Your name") == false) 
				{
					frame = new JFrame( "Space Shooter" );
					
					frame.add( new HeroGroup(playerName, i+1, frame) );
										
					frame.setSize(800, 600);
					frame.setResizable(false);
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
					frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				}
				else 
				{
					JOptionPane.showMessageDialog(frame, "Input your name!");
				}
			}
		}
	}

	private void loadBackgroundImage(String image) 
	{
        String imagePath = image;
        ImageIcon ii = new ImageIcon(this.getClass().getResource(imagePath));
        backgroundImage = ii.getImage();
    }
}