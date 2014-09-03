
import java.awt.*;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

public abstract class Craft {

    private String craft;

    private int dx;
    private int dy;
    private int x;
    private int y;
    private int hp;
    private int speed;
    private Image image;
    private boolean visible;
    private int score;
    private int missileBoost;

    public Craft() 
    {
        x = 100;
        y = 250;
    }

    public void move() 
    {
        x += dx;
        y += dy;
    }

    public int getX() 
    {
        return x;
    }

    public int getY() 
    {
        return y;
    }

    public void setX( int x ) 
    {
        this.x = x;
    }

    public void setY( int y ) 
    {
        this.y = y;
    }

    public int getDX() 
    {
        return dx;
    }

    public int getDy() 
    {
        return dy;
    }

    public void setDX(int dx) 
    {
        this.dx = dx;
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
        
        if(hp <= 0)
        {
            Audio craftDie = new Audio();
            craftDie.start("Audio/explosion_player.wav", 1);
        }    
    }

    public void setSpeed( int n ) 
    {
        speed = n;
    }

    public int getSpeed() 
    {
        return speed;
    }

    public void addSpeed( int n )
    {
        speed += n;
    }

    public void subSpeed( int n )
    {
        if (speed - n > 0) 
        {
            speed -= n;
        }
    }

    public int getScore() 
    {
        return score;
    }

    public void setScore(int s) 
    {
        score = s;
    }

    public void addScore(int add) 
    {
        score += add;
    }

    public void setDY(int dy) 
    {
        this.dy = dy;
    }

    public void setMissileBoost( int n )
    {
        missileBoost = n;
    }

    public int getMissileBoost()
    {
        return missileBoost;
    }

    public void addMissileBoost( int n )
    {
        missileBoost += n;
    }

    public void subMissileBoost( int n )
    {
        missileBoost -= n;
    }

    public Image getImage() 
    {
        return image;
    }

    public void setImage( String craft ) 
    {
        ImageIcon ii = new ImageIcon(this.getClass().getResource(craft));
        image = ii.getImage();
    }

    public void setVisible(boolean visible) 
    {
        this.visible = visible;
    }

    public boolean isVisible() 
    {
        return visible;
    }

    public abstract void keyPressed(KeyEvent e);

    public void keyReleased(KeyEvent e) 
    {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            setDX(0);
        }

        if (key == KeyEvent.VK_RIGHT) {
            setDX(0);
        }

        if (key == KeyEvent.VK_UP) {
            setDY(0);
        }

        if (key == KeyEvent.VK_DOWN) {
            setDY(0);
        }
    }

    public abstract void draw( Graphics g, Board board );
}
