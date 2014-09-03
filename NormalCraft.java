
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class NormalCraft extends Craft {

    private ArrayList<Missile> missiles;
    private final int CRAFT_SIZE = 50;
    private int width;
    private int height;
    private static final String[] model = {"Image/craft1.png", "Image/craft2.png", "Image/craft3.png"};
    private int type;

    public NormalCraft(int n) 
    {
        setImage( model[n-1] );
        setX( 100 );
        setY( 250 );
        type = n;
        setModel();
        setMissileBoost(1);
        missiles = new ArrayList<Missile>();
        width = getImage().getWidth(null);
        height = getImage().getHeight(null);
        setVisible(true);
        setScore(0);
    }

    public void setModel() 
    {
        if( type == 1 ) {
            setHp(2);
            setSpeed(2);
        }
        else if ( type == 2 ) 
        {
            setHp(1);
            setSpeed(3);
        }
        else if ( type == 3 ) 
        {
            setHp(3);
            setSpeed(1);
        }
    }

    public ArrayList getMissiles() 
    {
        return missiles;
    }

    public Rectangle getBounds() 
    {
        return new Rectangle( getX(), getY(), width, height );
    }

    public void fire() 
    {
        if (getMissileBoost() == 1) 
        {
            missiles.add( new Missile( getX() + CRAFT_SIZE, getY() + CRAFT_SIZE / 2 ) );
        }
        else if (getMissileBoost() == 2) 
        {
            missiles.add( new Missile( getX() + CRAFT_SIZE, getY() + CRAFT_SIZE * 2 / 5 ) );
            missiles.add( new Missile( getX() + CRAFT_SIZE, getY() + CRAFT_SIZE * 3 / 5 ) );
        }
        else if (getMissileBoost() == 3) 
        {
            missiles.add( new Missile( getX() + CRAFT_SIZE, getY() + CRAFT_SIZE * 2 / 7 ) );
            missiles.add( new Missile( getX() + CRAFT_SIZE, getY() + CRAFT_SIZE / 2 ) );
            missiles.add( new Missile( getX() + CRAFT_SIZE, getY() + CRAFT_SIZE * 5 / 7 ) );
        }
        else if (getMissileBoost() >= 4) 
        {
            missiles.add( new Missile( getX() + CRAFT_SIZE, getY() + CRAFT_SIZE * 1 / 4 ) );
            missiles.add( new Missile( getX() + CRAFT_SIZE, getY() + CRAFT_SIZE * 2 / 4 ) );
            missiles.add( new Missile( getX() + CRAFT_SIZE, getY() + CRAFT_SIZE * 3 / 4 ) );
            missiles.add( new Missile( getX() + CRAFT_SIZE, getY() + CRAFT_SIZE * 4 / 4 ) );
        }

        Audio audio = new Audio();
        audio.start( "Audio/weapon_player.wav", 1 );
    }

    public void keyPressed(KeyEvent e)
    {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_SPACE && getHp() > 0) 
        {
            fire();
        }

        if (key == KeyEvent.VK_LEFT) 
        {
            if(getX() > 0)
                setDX(-getSpeed());
            else
                setX(0);
        }

        if (key == KeyEvent.VK_RIGHT) 
        {
            setDX(getSpeed());
        }

        if (key == KeyEvent.VK_UP) 
        {
            if(getY() > 0)
                setDY(-getSpeed());
            else
                setY(0);
        }

        if (key == KeyEvent.VK_DOWN) 
        {
            if(getY() < 540)
                setDY(getSpeed());
            else 
                setY(540);
        }
    }

    @Override
    public void draw( Graphics g, Board board )
    {
        Graphics2D g2d = (Graphics2D)g;
        g2d.drawImage(getImage(), getX(), getY(), board);
    }
}