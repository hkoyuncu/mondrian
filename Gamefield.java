
import java.awt.*;

public class Gamefield {
    
    private Circle blue,red;
    private Rectangle[][] field;
    
    public Gamefield() {
        field = new Rectangle[20][20];
    }
    
    public void addPlayer(Circle c) {
        if (c.getColor() == Color.BLUE || c.getColor() == Color.ORANGE) {
            blue = c;
        }
        if (c.getColor() == Color.RED) {
            red = c;
        }
    }

    public void init(Graphics g) { 
        
        int mult = 10;
        
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j <field[i].length; j++) {            
                field[i][j] = new Rectangle(i*mult, j*mult, 10, 10, Color.WHITE);
                field[i][j].drawRectanbgle(g);
            }
        }
        fillBorder();
    }
    
    public void update(Graphics g) {
        for (Rectangle[] ra : field) {
            for (Rectangle r : ra) {
                r.drawRectanbgle(g);
            }
        }
        blue.drawCircle(g);   
        red.drawCircle(g);
    }
    
    private void fillBorder() {
        for (int i = 0; i < field.length; i++) {
            field[0][i].setColor(Color.BLUE);
            field[i][0].setColor(Color.BLUE);
            field[field.length - 1][i].setColor(Color.BLUE);
            field[i][field.length - 1].setColor(Color.BLUE);
        }
    }

    public void move(int x, int y) {
        if (blue.getX() + x < 0 || blue.getX() + x > 190) {
            return;
        }
        if (blue.getY() + y < 0 || blue.getY() + y > 190) {
            return;
        }
        field[blue.getX()/10][blue.getY()/10].setColor(Color.BLUE);
        blue.setX(blue.getX() + x);
        blue.setY(blue.getY() + y);
    }
}
