
import java.awt.Color;

public class Circle {
    
    private int x;
    private int y;
    private int size;
    private Color c;
    
    public Circle(int size) {
        this.x = 0;
        this.y = 0;
        this.size = size;
    }
    
    public void bewege(int x, int y) {
        this.x += x;
        this.y += y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Color getC() {
        return c;
    }

    public void setC(Color c) {
        this.c = c;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
