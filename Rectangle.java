
import java.awt.*;

public class Rectangle implements Shapes {
    
    private int x;
    private int y;
    private int w;
    private int h;
    private Color c;
    
    public Rectangle(int x, int y, int w, int h, Color c) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.c = c;
    }

    @Override
    public void move(int x, int y) {
        
    }

    @Override
    public void draw(Graphics2D g, Point p) {
       
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
    
    public void setColor(Color c) {
        this.c = c;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }
}