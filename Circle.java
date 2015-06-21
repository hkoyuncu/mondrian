
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class Circle implements Shapes {
    
    private int x;
    private int y;
    private final int size;
    private final Color c;
    
    public Circle(int size, Color c) {
        this.x = 0;
        this.y = 0;
        this.size = size;
        this.c = c;
    }
    
    @Override
    public void move(int x, int y) {
        this.x += x;
        this.y += y;
    }
    
    @Override
    public void draw(Graphics2D g, Point p) {
        g.drawRect((int)p.getX(), (int)p.getY(), 2, 2);
    }

    @Override
    public int getX() {
        return x;
    }
    
    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public Color getColor() {
        return c;
    }

    public int getSize() {
        return size;
    }
}
