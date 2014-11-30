
import java.awt.*;
import javax.swing.*;

public abstract class SpaceObject {

    private int x;
    private int y;
    private boolean visible;
    private Image image;
    private int hp;
    private int speed;
    private int point;

    public SpaceObject(int x, int y) 
    {
        visible = true;
        this.x = x;
        this.y = y;
    }

    public abstract void move();

    public int getX() 
    {
        return x;
    }

    public void setX(int x) 
    {
    	this.x = x;
    }

    public int getY() 
    {
        return y;
    }

    public void setY(int y) 
    {
    	this.y = y;
    }

    public boolean isVisible() 
    {
        return visible;
    }

    public void setVisible(Boolean visible) 
    {
        this.visible = visible;
    }

    public void setImage(String path) 
    {
    	ImageIcon ii = new ImageIcon(this.getClass().getResource(path));
        image = ii.getImage();
    }

    public Image getImage() 
    {
        return image;
    }

    public int getWidth() 
    {
        return getImage().getWidth(null);
    }

    public int getHeight() 
    {
        return getImage().getHeight(null);
    }

    public Rectangle getBounds() 
    {
        return new Rectangle( getX(), getY(), getWidth(), getHeight() );
    }

    public int getHp() 
    {
    	return hp;
    }

    public void setHp(int hp) 
    {
    	this.hp = hp;
    }

    public void addHp(int n) 
    {
        hp += n;
    }

    public void subHp(int n) 
    {
        hp -= n;
    }

    public int getSpeed() 
    {
        return speed;
    }

    public void setSpeed(int n) 
    {
        speed = n;
    }

    public void addSpeed(int s)
    {
        speed += s;
    }

    public void subSpeed(int s) 
    {
        //if (speed - s > 0) {
            speed -= s;
        //}
    }

    public int getPoint()
    {
        return point;
    }

    public void setPoint(int p)
    {
        point = p;
    }

    public abstract void draw( Graphics g, Board board );
}