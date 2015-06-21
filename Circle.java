
import java.awt.Color;

public class Circle {
    
    private int x;
    private int y;
    private final int size;
    private Color c;
    
    public Circle(int x, int y, int size, Color c) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.c = c;
    }

    public int getX() { return x; }
    
    public int getY() { return y; }

    public void setX(int x) { this.x = x; }

    public void setY(int y) { this.y = y; }

    public Color getColor() { return c; }

    public int getSize() { return size; }
}
