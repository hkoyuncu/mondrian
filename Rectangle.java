
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

    public int getX() { return x; }

    public int getY() { return y; }
    
    public int getW() { return w; }

    public int getH() { return h; }

    public Color getColor() { return color; }
    
    public void setColor(Color c) { this.color = c; }


   
}