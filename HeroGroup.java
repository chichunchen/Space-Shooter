
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import java.io.*;
import java.net.*;

public class HeroGroup extends Board
{
    private ServerSocket server;
    private Socket connection;
    private ObjectInputStream input;
    private ObjectOutputStream output;

    private JLabel waitForClient;
    private SendingObject sendingObject;

    public HeroGroup( String name, int craftIndex, JFrame f ) 
    {   
        super( name, craftIndex, f );
        addKeyListener(new TAdapter());

        setIngame( true );
        reset();
        loadBackgroundImage( "Image/space6.png" );
    }

    public void runServer()
    {
        try
        {
            server = new ServerSocket( 8000 );

            try
            {
                waitForConnection();
                getStreams();
                processConnection();
            }
            catch( EOFException eofException )
            {
                System.out.println( "Server terminated connection" );
            }
        }
        catch( IOException ioException )
        {
            ioException.printStackTrace();
        }
    }

    public void waitForConnection() throws IOException
    {
        System.out.println("Wait for connection");
        connection = server.accept();
    }

    public void getStreams() throws IOException
    {
        output = new ObjectOutputStream( connection.getOutputStream() );
        output.flush();

        input = new ObjectInputStream( connection.getInputStream() );
        System.out.println( "Got IO streams" );
    }

    public void processConnection() throws IOException
    {
        String message = "Connection successful";
        sendData( message );

        try
        {
            sendingObject = (SendingObject)input.readObject();
            System.out.println( "Server got it. Stone number is : " + sendingObject.getStoneNum() );
            initStones( sendingObject.getStoneNum() );
            System.out.println( "Server got it. eCraft number is : " + sendingObject.getECraftNum() );
            initEnemyCraft( sendingObject.getECraftNum() );
            System.out.println( "Server got it. Item number is : " + sendingObject.getItemNum() );
            initItems( sendingObject.getItemNum() );
        }
        catch( ClassNotFoundException classNotFoundException )
        {
            System.out.println( "Unknown object type received" );
        }
    }

    public void closeConnection()
    {
        System.out.println( "Terminating connection" );
        try
        {
            output.close();
            input.close();
            connection.close();
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
            output.writeObject( "Server>>" + message + "\n" );
            output.flush();
            System.out.println( "Server>>" + message + "\n" );
        }
        catch( IOException ioException )
        {
            System.out.println( "error writing object" );
        }
    }

    public void setGameStory( int s )
    {
        initStones( s );
        initEnemyCraft( s );
        initItems( s );
    }

    public void reset()
    {
        super.reset();
        setStage( 1 );
        setGameStory( 1 );

        runServer(); 
    }

    public void restart()
    {
        closeConnection();
        
        if ( getIngame() == false ) 
        {
            reset();
        }
    }

    public void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;

        // Draw space background image
        g2d.drawImage( getBackgroundImage(), getX1(), 0, this );
        g2d.drawImage( getBackgroundImage(), getX2(), 0, this );

        // playing the game
        if( getIngame() ) 
        {
            craft.draw(g2d, this);

            // draw PVP Mode
            g2d.setColor( Color.WHITE );
            g2d.setFont( new Font("Helvetica", Font.PLAIN, 28) );
            g2d.drawString("PVP Mode", 320, 60);
        }
        // Game Over! (dead)
        else 
        {
            drawGameOver( g2d, this );
        }

        drawAllMissile( g2d, this );

        drawAllStones( g2d, this );

        drawAllEnemyCraft( g2d, this );

        drawAllItems( g2d, this );

        drawGameAttribute( g2d, this );

        // 
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    @Override
    public void actionPerformed( ActionEvent event )
    {
        super.actionPerformed( event );
    }

    private class TAdapter extends KeyAdapter 
    {
        public void keyReleased(KeyEvent e) 
        {
            craft.keyReleased(e);
        }

        public void keyPressed(KeyEvent e) 
        {
            craft.keyPressed(e);

            int key = e.getKeyCode();
            if (key == KeyEvent.VK_P) 
            {
                pause();
            }
            else if (key == KeyEvent.VK_R) 
            {
                resume();
            }
            else if (key == KeyEvent.VK_ENTER) 
            {
                stopBackgroundMusic();
                restart();    
            }
            else if (key == KeyEvent.VK_Q) 
            {
                //closeConnection();
                back2Menu();
            }
            /*
            else if (key == KeyEvent.VK_S) 
            {
                initStones( 10 );
            }
            */
        }
    }
}