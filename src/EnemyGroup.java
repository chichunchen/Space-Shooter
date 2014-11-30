
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.Random;
import java.io.*;
import java.net.*;

public class EnemyGroup extends JPanel implements ActionListener
{
    private JLabel nameLabel = new JLabel( "Enemy Group" );
	private JTextField stoneNumField = new JTextField();
    private JTextField enemyNumField = new JTextField();
    private JTextField itemNumField = new JTextField();
    private JTextField hostField = new JTextField();
	private JTextArea jta = new JTextArea();
    private JButton connectBtn;

    private Socket client;
	private ObjectInputStream input;
    private ObjectOutputStream output;
    private String hostAddress;
    private String message = "";

    // send these three data
    private int stoneNum;
    private int enemyCraftNum;
    private int itemNum;
    private SendingObject sendingObject;

    private int enemyQutoa;
    private JLabel quotaLabel;
    private static final int maxQutoa = 200;

    public EnemyGroup()
    {
    	setLayout( null );
        setBackground( Color.BLACK );

        enemyQutoa = 0;
    	
        nameLabel.setForeground(Color.white);
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 32));
        add( nameLabel );

        add( hostField );
        hostField.setFont(new Font("Arial", Font.PLAIN, 28));
        hostField.setText("Enter server address");
        hostField.addActionListener( new ButtonListener() );

    	add( stoneNumField );
        stoneNumField.setFont(new Font("Arial", Font.PLAIN, 28));
        stoneNumField.setText("Enter stone number");
    	stoneNumField.addActionListener( new ButtonListener() );

        add( enemyNumField );
        enemyNumField.setFont(new Font("Arial", Font.PLAIN, 28));
        enemyNumField.setText("Enter enemy craft number");
        enemyNumField.addActionListener( new ButtonListener() );

        add( itemNumField );
        itemNumField.setFont(new Font("Arial", Font.PLAIN, 28));
        itemNumField.setText("Enter item number");
        itemNumField.addActionListener( new ButtonListener() );

        add( jta );

        connectBtn = new JButton("Connect");
        connectBtn.setFont(new Font("Arial", Font.PLAIN, 28));
        connectBtn.addActionListener( this );
        connectBtn.setEnabled( false );
        add(connectBtn);

        quotaLabel = new JLabel( String.valueOf(enemyQutoa) );
        quotaLabel.setForeground(Color.white);
        quotaLabel.setFont(new Font("Arial", Font.PLAIN, 28));
        add( quotaLabel );

        nameLabel.setBounds(320, 20, 300, 100);
        hostField.setBounds(220, 100, 400, 50);
        stoneNumField.setBounds(220, 150, 400, 50);
        enemyNumField.setBounds(220, 200, 400, 50);
        itemNumField.setBounds(220, 250, 400, 50);
        connectBtn.setBounds(440, 300, 180, 50);
        quotaLabel.setBounds(240, 300, 180, 50);
        jta.setBounds(220, 400, 400, 150);
    	
    }

    public void runClient()
    {
        try
        {
            connectToServer();
            getStreams();
            processConnection();
        }
        catch( EOFException eofException )
        {
            jta.append( "Client terminated connection\n" );
        }
        catch( IOException ioException )
        {
            ioException.printStackTrace();
        }
        finally
        {
            closeConnection();
        }
    }

    private void connectToServer() throws IOException
    {
        jta.append( "Attempting connection\n" );
        client = new Socket( hostAddress, 8000 );
        jta.append( "Connected to " + hostAddress + " \n" );
    }

    public void getStreams() throws IOException
    {
        output = new ObjectOutputStream( client.getOutputStream() );
        output.flush();

        input = new ObjectInputStream( client.getInputStream() );
        jta.append( "Got IO streams\n" );
    }

    public void processConnection() throws IOException
    {
        try
        {
            message = (String)input.readObject();
            jta.append( message );
            sendObject();
        }
        catch( ClassNotFoundException classNotFoundException )
        {
            jta.append( "Unknown object type received\n" );
        }
    }

    public void closeConnection()
    {
        jta.append( "Terminating connection\n" );
        try
        {
            output.close();
            input.close();
            client.close();
        }
        catch( IOException ioException )
        {
            ioException.printStackTrace();
        }
    }

    public void sendData( String message )
    {
        try
        {
            output.writeObject( "Client>>" + message );
            output.flush();
            jta.append( "Client>>" + message + "\n" );
        }
        catch( IOException ioException )
        {
            jta.append( "error writing object\n" );
        }
    }

    public void sendObject()
    {
        try
        {
            output.writeObject( sendingObject );
            output.flush();
            jta.append( "Client>>send object\n" );
        }
        catch( IOException ioException )
        {
            jta.append( "error writing object\n" );
        }
    }

    class ButtonListener implements ActionListener
    {
    	public void actionPerformed( ActionEvent event )
    	{
            connectBtn.setEnabled( false );

            if (event.getSource() != hostField ) 
            {
                enemyQutoa = 0;
            }

            if ( event.getSource() == stoneNumField ) 
            {
                stoneNum =  Integer.valueOf( stoneNumField.getText() );
            }

            if ( event.getSource() == enemyNumField ) 
            {
                enemyCraftNum = Integer.valueOf( enemyNumField.getText() );
            }

            if ( event.getSource() == itemNumField ) 
            {
                itemNum = Integer.valueOf( itemNumField.getText() );    
            }      

            enemyQutoa += (stoneNum * 2);
            enemyQutoa += (enemyCraftNum * 5);
            enemyQutoa -= (itemNum * 10);

            if (enemyQutoa < maxQutoa) 
            {
                quotaLabel.setText( String.valueOf( enemyQutoa ) + " < " + String.valueOf( maxQutoa ) );      
                connectBtn.setEnabled( true );   
            }
            else 
            {
                enemyQutoa = 0;
                quotaLabel.setText( "+ item / - army" );      
            }                
    	}
    }

    public void actionPerformed( ActionEvent event )
    {
        stoneNum = Integer.valueOf( stoneNumField.getText() );
        enemyCraftNum = Integer.valueOf( enemyNumField.getText() );
        itemNum = Integer.valueOf( itemNumField.getText() );

        if ( event.getSource() == connectBtn ) 
        {
            //audio.start("Audio/button_click.wav", 1);
            sendingObject = new SendingObject( stoneNum, enemyCraftNum, itemNum );
            runClient();
        }
    }

}