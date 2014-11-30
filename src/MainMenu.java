  
import javax.swing.*;
import java.awt.*;

public class MainMenu {

	public JFrame frame;

	public MainMenu()
	{
		frame = new JFrame("Space Shooter");
		frame.add( new MenuPanel( frame ) );
		frame.setSize(800,600);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) 
	{	
		new MainMenu();
	}
}
