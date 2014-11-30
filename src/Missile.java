
import java.awt.*;
import javax.swing.*;

public class Missile {

    private int x, y;
    private Image image;
    private boolean visible;
    private int width, height;
    private final int BOARD_WIDTH = 800;
    private final int MISSILE_SPEED = 4;
    private int hp;

    public Missile(int x, int y) 
    {
        setImage("Image/missile1.png");

        setVisible(true);

        setX(x);
        setY(y);

        setHp(1);

        width = image.getWidth(null);
        height = image.getHeight(null);
    }

    public void setImage(String imageName) 
    {
        ImageIcon ii = new ImageIcon(this.getClass().getResource(imageName));
        image = ii.getImage();
    }

    public Image getImage() 
    {
        return image;
    }

    public int getX() 
    {
        return x;
    }

    public void setX( int x ) 
    {
        this.x = x;
    }

    public int getY() 
    {
        return y;
    }

    public void setY( int y ) 
    {
        this.y = y;
    }

    public int getHp() 
    {
        return hp;
    }

    public void setHp( int h )
    {
        hp = h;
    }

    public void addHp(int n) 
    {
        hp += n;
    }

    public void subHp(int n) 
    {
        hp -= n;
    }

    public boolean isVisible() 
    {
        return visible;
    }

    public void setVisible(boolean visible) 
    {
        this.visible = visible;
    }

    public Rectangle getBounds() 
    {
        return new Rectangle(x, y, width, height);
    }

    public void move() 
    {
        x += MISSILE_SPEED;
        if (x > BOARD_WIDTH)
            setVisible(false);
    }

    public void draw( Graphics g, Board board )
    {
        Graphics2D g2d = (Graphics2D)g;
        g2d.drawImage(getImage(), getX(), getY(), board);
    }
}