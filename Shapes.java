
import java.awt.*;

public interface Shapes {
    
    public void move(int x, int y);
    
    public void draw(Graphics2D g, Point p);
    
    public int getX();
    
    public int getY();
    
    public void setX(int x);
    
    public void setY(int y);
    
    public Color getColor();

}
