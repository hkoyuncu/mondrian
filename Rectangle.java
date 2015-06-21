
import java.awt.*;

public class Rectangle {
    
    private int x;
    private int y;
    private int w;
    private int h;
    private Color color;
    
    public Rectangle(int x, int y, int w, int h, Color c) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.color = c;
    }
    
    public void drawRectanbgle(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, w, h);
    }

    public void setColor(Color c) { this.color = c; }
    
    public Color getColor() { return color; }

}