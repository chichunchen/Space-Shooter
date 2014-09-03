
import java.awt.*;
import javax.swing.*;

public class EnemyCraft extends SpaceObject 
{
    //private String stone = "Image/Ecraft1.png";
    private int width;
    private int height;
    private static final String[] model = {"Image/Ecraft1.png", "Image/Ecraft2.png", "Image/Ecraft3.png", "Image/Ecraft4.png"};

    public EnemyCraft(int x, int y, int n) 
    {
        super(x, y);
        setModel(n);
    }

    public void setModel(int n)
    {
        if( n < model.length )
            setImage( model[ n ] );
        else
            setImage( model[ model.length - 1 ]);

        if ( n == 0 ) 
        {
            setHp(1);
            setSpeed(2);
            setPoint(40);
        }
        else if ( n == 1 ) 
        {
            setHp(2);
            setSpeed(2);
            setPoint(60);
        }
        else if ( n == 2 ) 
        {
            setHp(3);
            setSpeed(2);
            setPoint(80);
        }
        else if ( n == 3 ) 
        {
            setHp(3);
            setSpeed(3);
            setPoint(120);
        }
    }

    public static int getModelNum()
    {
        return model.length;
    }

    public void move() 
    {
        if (getX() < 0) 
            setX(800);
        setX( getX() - getSpeed() );
    }

    public void subHp(int n) 
    {
        super.subHp( n );
        
        if(getHp() <= 0)
        {
            Audio craftDie = new Audio();
            craftDie.start("Audio/explosion_enemy.wav", 1);
        }    
    }

    @Override
    public void draw( Graphics g, Board board )
    {
        Graphics2D g2d = (Graphics2D)g;
        g2d.drawImage(getImage(), getX(), getY(), board);
    }
}