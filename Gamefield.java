
import java.awt.*;

public class Gamefield {
    
    private Circle blue,red;
    private Rectangle[][] field;
    
    public Gamefield() {
        field = new Rectangle[20][20];
    }
    
    public void addPlayer(Circle c) {
        if (c.getColor() == Color.BLUE) {
            blue = c;
        }
        if (c.getColor() == Color.RED) {
            red = c;
        }
    }

    public Circle getBlue() {
        return blue;
    }

    public Circle getRed() {
        return red;
    }

    public void init(Graphics g) { 
        
        int mult = 10;
        
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j <field[i].length; j++) {            
                field[i][j] = new Rectangle(i*mult, j*mult, 10, 10, Color.WHITE);
                
                Rectangle t = field[i][j];
                g.setColor(t.getColor());
                g.fillRect(t.getX(), t.getY(), t.getW(), t.getH());
            }
        }
        // Rand faetben
        fillBorder(g);
    }
    
    public void update(Graphics g) {
        for (Rectangle[] ra : field) {
            for (Rectangle r : ra) {
                g.setColor(r.getColor());
                g.fillRect(r.getX(), r.getY(), r.getW(), r.getH());
            }
        }
        
        g.setColor(blue.getColor());
        g.fillOval(blue.getX(), blue.getY(), blue.getSize(), blue.getSize());
        
        g.setColor(red.getColor());
        g.fillOval(red.getX(), red.getY(), red.getSize(), red.getSize());
    }
    
    private void fillBorder(Graphics g) {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                Rectangle t = field[i][j];
                t.setColor(Color.BLUE);
                if (i == 0) {
                    g.setColor(t.getColor());
                    g.fillRect(t.getX(), t.getY(), t.getW(), t.getH());
                }
                else if (i == field[i].length-1) {
                    g.setColor(t.getColor());
                    g.fillRect(t.getX(), t.getY(), t.getW(), t.getH());
                }
                else if (j == 0) {
                    g.setColor(t.getColor());
                    g.fillRect(t.getX(), t.getY(), t.getW(), t.getH());
                }
                else if (j == field.length-1) {
                    g.setColor(t.getColor());
                    g.fillRect(t.getX(), t.getY(), t.getW(), t.getH());
                }
            }
        }
    }
    
    // ab hier weitermachen
    public void move(int x, int y) {
        if (blue.getX() + x < 0 || blue.getX() + x > 190) {
            return;
        }
        if (blue.getY() + y < 0 || blue.getY() + y > 190) {
            return;
        }
        blue.setX(blue.getX() + x);
        blue.setY(blue.getY() + y);
    }

    public Rectangle[][] getField() {
        return field;
    }
}
