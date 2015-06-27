
import java.awt.*;

public class Rectangle {
    
    private int x;
    private int y;
    private int w;
    private int h;
    private int present;
    private Color color;
    
    public Rectangle(int x, int y, int w, int h, Color c) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.present = 0;
        this.color = c;
    }
    
    public void drawRectanbgle(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, w, h);
    }

    public void setColor(Color c) { this.color = c; }
    
    public Color getColor() { return color; }

    public int getPresent() { return present; }

    public void setPresent(int present) { this.present = present; }
    
    public int getX() { return x/10; }

    public int getY() { return y/10; }
    
    public void setX(int x) { this.x = x; } 
    
    public void setY(int y) { this.y = y; } 
    
    @Override
    public String toString() { return "X: " + x + "  Y: " + y  + "   p: " + present; }
 
}