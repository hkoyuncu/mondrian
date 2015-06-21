
import java.awt.*;

public class Raster implements Shapes {
    
    private final int width;
    private final int height;
    private int x;
    private int y;
    
    public Raster(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
    }

    @Override
    public void move(int x, int y) { }

    @Override
    public void draw(Graphics2D g, Point p) {
        g.drawRect(x, y, width, height);
    }

    @Override
    public int getX() {
        return 0;
    }

    @Override
    public int getY() {
        return 0;
    }

    @Override
    public void setX(int x) { }

    @Override
    public void setY(int y) { }

    @Override
    public Color getColor() {
        return null;
    }  
}
