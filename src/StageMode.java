
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class StageMode extends Board
{
    private static final int[] stagePoint = { 250, 800, 1600, 3200, 5000, 8000, 12000 };
    private int stagePointIndex; 

    public StageMode( String name, int craftIndex, JFrame f ) 
    {	
        super( name, craftIndex, f );
        addKeyListener(new TAdapter());
        
        setIngame( true );    
        reset();
        loadBackgroundImage(backgroundImage[ getStage() - 1 ]);
    }

    @Override
    public void initStones( int s ) 
    {
        super.initStones( s + 20 );
    }

    @Override
    public void initEnemyCraft( int s ) 
    {
        super.initEnemyCraft( s + 1 );
    }

    @Override
    public void initItems( int n )
    {
        Random randomGenerator = new Random();
        super.initItems( randomGenerator.nextInt( n ) );
    }

    public void setGameStory( int s )
    {
        initStones( s );
        initEnemyCraft( s );
        initItems( s );
    }

    public void updateRank()
    {
        Records records = new Records( ( Board )this );

        records.setOutput();
        records.writeFile();
    }

    public void reset()
    {
        super.reset();
        setStage( 1 );
        setGameStory( getStage() );
        stagePointIndex = 0;
    }

    public void restart()
    {
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
            // level up
            if( getScore() > stagePoint[stagePointIndex] ) 
            {
                if( stagePointIndex < stagePoint.length )
                {
                    stagePointIndex += 1;
                }
                
                addStage( 1 );
                setGameStory( getStage() );
                if( getStage() < backgroundImage.length )
                {
                    loadBackgroundImage(backgroundImage[ getStage() - 1 ]);
                }
                else
                {
                    loadBackgroundImage(backgroundImage[ backgroundImage.length - 1 ]);
                }
            }

            craft.draw(g2d, this);

            // draw Stage
            g2d.setColor( Color.WHITE );
            g2d.setFont( new Font("Helvetica", Font.PLAIN, 28) );
            g2d.drawString("Stage " + getStage(), 340, 60);
        }
        // Game Over!
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
                updateRank();
                restart();    
            }
            else if (key == KeyEvent.VK_Q) 
            {
                if( getScore() != 0 )
                    updateRank();

                back2Menu();
            }
        }
    }
}