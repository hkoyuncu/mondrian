
import java.awt.*;

public class Circle {
    
    private int x;
    private int y;
    private final int size;
    private Color color;
    
    public Circle(int x, int y, int size, Color c) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.color = c;
    }
    
    public void move(int x, int y) {
        this.x += x;
        this.y += y;
    }
    
    public void drawCircle(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, size, size);
    }

    public int getX() { return x; }
    
    public int getY() { return y; }

    public void setX(int x) { this.x = x; }

    public void setY(int y) { this.y = y; }

    public Color getColor() { return color; }
    
    public String toString() {
        return "x: " + x +  "    y: " + y;
    }
}
