
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;
import java.text.*;

public abstract class Board extends JPanel implements ActionListener
{
	private Image spaceImage;
	private int X1;
	private int X2;
	
	private long StartTime;
    private long ProcessTime;
    private String currentTime;
    public static final String[] backgroundImage = {"Image/space1.png", "Image/space2.png", 
                                                    "Image/space3.png", "Image/space4.png", 
                                                    "Image/space5.png", "Image/space6.png"};
    
    private boolean ingame;
    private int stage;
    private int score;
    private Timer timer;
    private Audio backgroundMusic;

    // game object
    public NormalCraft craft;
    private ArrayList<Stone> stones;
    private ArrayList<EnemyCraft> enemyCraft;
    private ArrayList<Item> items;
    private int[][] stonePos;
    private int[][] eCraftPos;
    private int[][] itemPos;

    // constructor argument
    private String playerName;
    private int craftModelIndex;
    private JFrame frame;

	public Board( String name, int craftIndex, JFrame f )
	{
		setX1(0);
		setX2(794);

        setFocusable(true);
        setDoubleBuffered(true);

		setPlayerName( name );
		setCraftModelIndex( craftIndex );
		frame = f;

		backgroundMusic = new Audio();

		timer = new Timer(5, this);
        timer.start();
	}

	public void setX1( int n )
	{
		X1 = n;
	}

	public void setX2( int n )
	{
		X2 = n;
	}

	public int getX1()
	{
		return X1;
	}

	public int getX2()
	{
		return X2;
	}

	public void addX1( int n )
	{
		X1 += n;
	}

	public void subX1( int n )
	{
		X1 -= n;
	}

	public void addX2( int n )
	{
		X2 += n;
	}

	public void subX2( int n )
	{
		X2 -= n;
	}

	public void loadBackgroundImage( String imageName ) 
    {
        String path = imageName;
        ImageIcon ii = new ImageIcon(this.getClass().getResource(path));
        spaceImage = ii.getImage();
    }

    public Image getBackgroundImage()
    {
    	return spaceImage;
    }

    public void setStartTime()
    {
    	StartTime = System.currentTimeMillis();
    }

    public long getStartTime()
    {
    	return StartTime;
    }

    public void setProcessTime()
    {
    	ProcessTime = System.currentTimeMillis() - StartTime; 
    }

    public long getProcessTime()
    {
    	return ProcessTime;
    }

    public void setCurrentTime( String s )
    {
    	currentTime = s;
    }

    public String getCurrentTime()
    {
    	return currentTime;
    }
    
    public void setCurrentTime2Digit(long recordTime) 
    {
        float a = (float) recordTime / 1000;
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits( 2 ); 

        setCurrentTime( String.valueOf(nf.format(a)) );   
    }

    public void setPlayerName( String pName )
    {
        playerName = pName;
    }

    public String getPlayerName()
    {
        return playerName;
    }

    public void setIngame( boolean b )
    {
    	ingame = b;
    }

    public boolean getIngame()
    {
    	return ingame;
    }

    public void setCraftModelIndex( int n )
    {
        craftModelIndex = n;
    }

    public int getCraftModelIndex()
    {
        return craftModelIndex;
    }

    public int getStage() 
    {
        return stage;
    }

    public void setStage( int s ) 
    {
        stage = s;
    }

    public void addStage( int s )
    {
    	stage += s;
    }

    public int getScore() 
    {
        return score;
    }

    public void setScore( int x ) 
    {
        score = x;
    }

    public void addScore( int s )
    {
        score += s;
    }

    public void gameTimerStop()
    {
    	timer.stop();
    }

    public void gameTimerRestart()
    {
    	timer.restart();
    }

    public void pause()
    {
        if ( getIngame() ) 
        {
            gameTimerStop();
            backgroundMusic.stop();
            backgroundMusic.start("Audio/pause.wav", 1);
        }
    }

    public void resume()
    {
        gameTimerRestart();
        backgroundMusic.gameMusic();
    }

    public void reset()
    {
        backgroundMusic.gameMusic();

        craft = new NormalCraft( getCraftModelIndex() );
        craft.setVisible(true);

        setIngame( true );
        setScore( 0 );
        setStartTime();
    }

    public void back2Menu() 
    {
        backgroundMusic.stop();
        frame.dispose();
    }

    public void stopBackgroundMusic()
    {
    	backgroundMusic.stop();
    }

    public void initStones( int n ) 
    {
        stones = new ArrayList<Stone>();

        int stoneNum = n;
        stonePos = new int [stoneNum][2]; 
        Random randomGenerator = new Random();

        for (int i = 0; i < stoneNum; ++i) 
        {
            for (int j = 0; j < 2; ++j) 
            {
                if (j == 0) {
                    stonePos[i][j] = randomGenerator.nextInt(2500) + 1000;
                }
                else {
                    stonePos[i][j] = randomGenerator.nextInt(570);
                }
            }
        }

        for (int i = 0; i < stonePos.length; ++i) 
        {
            stones.add( new Stone(stonePos[i][0], stonePos[i][1]) );
        }
    }

    public void drawAllStones( Graphics g2d, Board board )
    {
    	// Draw stone 
        for (int i = 0; i < stones.size(); ++i) 
        {
            Stone s = (Stone)stones.get(i);
            s.draw( g2d, this );
        }
    }

    public void initEnemyCraft( int n ) 
    {
        enemyCraft = new ArrayList<EnemyCraft>();

        int eCraftNum = n;
        eCraftPos = new int [eCraftNum][2];
        Random randomGenerator = new Random();

        for (int i = 0; i < eCraftNum; ++i) 
        {
            for (int j = 0; j < 2; ++j) 
            {
                if (j == 0) 
                {
                    eCraftPos[i][j] = randomGenerator.nextInt(2500) + 1000;
                }
                else {
                    eCraftPos[i][j] = randomGenerator.nextInt(500) + 40;
                }
            }
        }

        for (int i = 0; i < eCraftPos.length; ++i) 
        {
            enemyCraft.add( new EnemyCraft( eCraftPos[i][0], eCraftPos[i][1], randomGenerator.nextInt( getStage() ) ) );
        }
    }

    public void drawAllEnemyCraft( Graphics g2d, Board board )
    {
        for (int i = 0; i < enemyCraft.size(); ++i) 
        {
            EnemyCraft ec = (EnemyCraft)enemyCraft.get(i);
            ec.draw( g2d, this );
        }
    }

    public void initItems( int n )
    {
        items = new ArrayList<Item>();
        Random randomGenerator = new Random();

        int itemNum = n;
        itemPos = new int [itemNum][2];

        for (int i = 0; i < itemNum; ++i) 
        {
            for (int j = 0; j < 2; ++j) 
            {
                if (j == 0) 
                {
                    itemPos[i][j] = randomGenerator.nextInt(2500) + 1000;
                }
                else {
                    itemPos[i][j] = randomGenerator.nextInt(500) + 40;
                }

            }
        }

        for (int i = 0; i < itemPos.length; ++i) 
        {
            items.add( new Item( itemPos[i][0], itemPos[i][1], randomGenerator.nextInt( 3 ) ) );
        }
    }

    public void drawAllItems( Graphics g2d, Board board )
    {
    	for (int i = 0; i < items.size(); ++i) 
        {
            Item it = (Item)items.get(i);
            it.draw( g2d, this );
        }
    }

    public void drawAllMissile( Graphics g2d, Board board )
    {
    	ArrayList ms = craft.getMissiles();
        for (int i = 0; i < ms.size(); i++ ) 
        {
            Missile m = (Missile) ms.get(i);
            m.draw( g2d, this );
        }
    }

    public void drawGameAttribute( Graphics g2d, Board board )
    {
    	// Print processing time
        g2d.setColor( Color.WHITE );
        g2d.setFont( new Font("Helvetica", Font.PLAIN, 28) );
        setCurrentTime2Digit( getProcessTime() );
        g2d.drawString("Time: " + getCurrentTime(), 620, 30);

        // Print score
        g2d.setColor( Color.WHITE );
        g2d.setFont( new Font("Helvetica", Font.PLAIN, 28) );
        g2d.drawString("Score: " + getScore(), 620, 60);

        // Print craft health
        g2d.setColor( Color.WHITE );
        g2d.setFont( new Font("Helvetica", Font.PLAIN, 28) );
        g2d.drawString("Life", 20, 30);
        for (int i = 0; i<craft.getHp(); ++i) 
        {
            g2d.setColor( Color.WHITE );
            g2d.fillRect(120+(i*20), 10, 20, 20);
        }

        // Print craft Speed
        g2d.setColor( Color.WHITE );
        g2d.setFont( new Font("Helvetica", Font.PLAIN, 28) );
        g2d.drawString("Speed", 20, 60);
        g2d.setColor( Color.WHITE );
        g2d.fillRect(120, 40, 20 * craft.getSpeed(), 20);
    }

    public void drawGameOver( Graphics g2d, Board board )
    {
    	String msg = "Game Over";
        Font gameOverFont = new Font("Helvetica", Font.BOLD, 36);
        FontMetrics metr = this.getFontMetrics(gameOverFont);

        g2d.setColor(Color.white);
        g2d.setFont(gameOverFont);
        g2d.drawString(msg, (800 - metr.stringWidth(msg)) / 2, 600 / 2);

        g2d.setFont( new Font( "Helvetica", Font.PLAIN, 28 ) );
        g2d.drawString("Click Enter to Restart / Q to Exit", 190, 100);
    }

    // physics logics in game
    public void actionPerformed( ActionEvent event )
    {
    	if( getIngame() )
            setProcessTime();
		

        // set the location of space background
		subX1(1);
		subX2(1);
		if(getX1() < -794) {
			setX1(794);
		}
		if(getX2() < -794) {
			setX2(794);
		}
        ////////////////////////////////////////////////////////////

		// move the missiles
        ArrayList ms = craft.getMissiles();

        for (int i = 0; i < ms.size(); i++) 
        {
            Missile m = (Missile) ms.get(i);
            if (m.getHp() > 0) 
                m.move();
            else ms.remove(i);
        }

    	// move stones
        for (int i = 0; i < stones.size(); ++i) 
        {
            Stone s = (Stone)stones.get(i);
            if(s.getHp() > 0)
                s.move();
            else 
            {
                stones.remove(i);
            }
        }

        // move enemy craft
        for (int i = 0; i < enemyCraft.size(); ++i) 
        {
            EnemyCraft ec = (EnemyCraft)enemyCraft.get(i);
            if(ec.getHp() > 0)
                ec.move();
            else 
            {
                enemyCraft.remove(i);
            }
        }

        // move items
        for (int i = 0; i < items.size(); ++i) 
        {
            Item it = (Item)items.get(i);
            if(it.getHp() > 0)
                it.move();
            else 
            {
                items.remove(i);
            }    
        }
        craft.move();
        checkCollisions();
        repaint();  
    }

    public void checkCollisions() 
    {
        Rectangle rCraft = craft.getBounds();

        // collision between craft and stone
        if(craft.getHp() > 0) 
        {
            for (int j = 0; j < stones.size(); ++j) 
            {
                Stone s = (Stone)stones.get(j);
                Rectangle rStone = s.getBounds();

                if (rCraft.intersects(rStone)) 
                {
                    s.subHp(1);
                    craft.subHp(1);

                    if (s.getHp() < 1) 
                    {
                        stones.remove(s);
                    }

                    if (craft.getHp() < 1) 
                    {
                        craft.setVisible(false);
                        setIngame( false );
                        stopBackgroundMusic();
                        //backgroundMusic.gameOverMusic();
                        // game over
                    }
                }

                if (j < enemyCraft.size()) 
                {
                    EnemyCraft ec = (EnemyCraft)enemyCraft.get(j);
                    Rectangle rECraft = ec.getBounds();   

                    if (rCraft.intersects(rECraft)) 
                    {
                        ec.subHp(1);
                        craft.subHp(1);

                        if (ec.getHp() < 1) 
                        {
                            enemyCraft.remove(ec);
                        }

                        if (craft.getHp() < 1) 
                        {
                            craft.setVisible(false);
                            setIngame( false );
                            stopBackgroundMusic();
                            //backgroundMusic.gameOverMusic();
                            // game over
                        }
                    }
                }

                if (j < items.size()) 
                {
                    Item it = (Item)items.get(j);
                    Rectangle rItem = it.getBounds();

                    if(rCraft.intersects(rItem))
                    {
                        it.subHp(1);

                        if (it.getHp() < 1) 
                        {
                            items.remove(it);   
                            if (it.getType() == 0) 
                            {
                                craft.addHp(1);
                            }
                            else if (it.getType() == 1) 
                            {
                                craft.addSpeed(1);    
                            }
                            else if (it.getType() == 2) 
                            {
                                craft.addMissileBoost(1);
                            }
                        }
                    }    
                }
            }
        }

        ArrayList ms = craft.getMissiles();

        // collision between missile and stone
        for (int i = 0; i < ms.size(); ++i) 
        {
            Missile m = (Missile)ms.get(i);
            Rectangle rMissile = m.getBounds();

            for (int j = 0; j < stones.size(); ++j) 
            {
                Stone s = (Stone)stones.get(j);
                Rectangle rStone = s.getBounds();

                if(rMissile.intersects(rStone) && m.getX() < 820) 
                {
                    s.subHp(1);
                    m.subHp(1);
                    addScore( s.getPoint() + getStage() * 5 );

                    if (s.getHp() < 1) 
                    {
                        stones.remove(s);
                    }

                    if (m.getHp() < 1) 
                    {
                        ms.remove(m);
                    }
                }

                if (j < enemyCraft.size()) 
                {
                    EnemyCraft ec = (EnemyCraft)enemyCraft.get(j);
                    Rectangle rECraft = ec.getBounds();   

                    if (rMissile.intersects(rECraft) && m.getX() < 820) 
                    {
                        ec.subHp(1);
                        m.subHp(1);
                        addScore( ec.getPoint() + getStage() * 10 );

                        if (ec.getHp() < 1) 
                        {
                            enemyCraft.remove(ec);
                        }

                        if (m.getHp() < 1) 
                        {
                            ms.remove(m);
                        }
                    }
                }
            }
        }
    }
}