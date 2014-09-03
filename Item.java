
import java.awt.*;
import javax.swing.*;

public class Item extends SpaceObject 
{
    private int width;
    private int height;
    private int type;
    private static final String[] itmeImagePath = { "Image/health.png", "Image/star.png", "Image/Missile_strengthen.png" };

    public Item(int x, int y, int n) 
    {
        super(x, y);
        type = n;
        setType();
    }

    public void setType()
    {
    	if ( type == 0 )		// add hp 
    	{
    		setImage( itmeImagePath[ type ] );
	        setHp(2);
	        setSpeed(1);
	        setPoint(50);
    	}
    	else if ( type == 1 )	// add speed 
    	{
    		setImage( itmeImagePath[ type ] );
	        setHp(1);
	        setSpeed(2);
	        setPoint(50);
    	}
    	else if ( type == 2 ) // change the missile mode
    	{
    		setImage( itmeImagePath[ type ] );
	        setHp(1);
	        setSpeed(3);
	        setPoint(50);
    	}
    }

    public int getType()
    {
        return type;
    }

    public void move() 
    {
        setX( getX() - getSpeed() );
    }

    public void subHp(int n) 
    {
        super.subHp( n );
        
        if(getHp() <= 0)
        {
            Audio craftDie = new Audio();
            
            if(getType() == 0)
            {
                craftDie.start("Audio/Item.wav", 1);
            }
            else if (getType() == 1) 
            {
                craftDie.start("Audio/Item.wav", 1);
            }
            else if (getType() == 2)
            {
                craftDie.start("Audio/missile_level_up.wav", 1);
            }
        }    
    }

    @Override
    public void draw( Graphics g, Board board )
    {
        Graphics2D g2d = (Graphics2D)g;
        g2d.drawImage(getImage(), getX(), getY(), board);
    }
}