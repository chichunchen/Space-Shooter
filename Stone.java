
import java.awt.*;
import javax.swing.*;

public class Stone extends SpaceObject 
{
    private String stone = "Image/stone.png";
    private int width;
    private int height;

    public Stone(int x, int y) 
    {
        super(x, y);
        setImage( stone );
        setHp(1);
        setSpeed(1);
        setPoint(10);
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
            craftDie.start("Audio/explosion_asteroid.wav", 1);
        }    
    }

    @Override
    public void draw( Graphics g, Board board )
    {
        Graphics2D g2d = (Graphics2D)g;
        g2d.drawImage(getImage(), getX(), getY(), board);
    }
}