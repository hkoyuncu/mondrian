
import java.awt.*;
import javax.swing.JOptionPane;

public class Gamefield {
    
    private Circle blue, red;
    private Rectangle[][] field;
    private boolean first;
    
    public Gamefield() {
        field = new Rectangle[20][20];
        first = false;
    }
    
    public void draw(Graphics g) {
        if (!first) {
            init();
            first = true;
        }
        update(g);
    }
    
    public void addPlayer(Circle c) {
        if (c.getColor() == Color.BLUE || c.getColor() == Color.ORANGE) {
            blue = c;
        }
        if (c.getColor() == Color.RED) {
            red = c;
        }
    }

    public void init() { 
        int mult = 10;
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j <field[i].length; j++) {            
                field[i][j] = new Rectangle(i*mult, j*mult, 10, 10, Color.WHITE);
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
    
    public boolean checkWin() {
        int count = 0;
        final int max = 320;
        for (Rectangle[] ra : field) {
            for (Rectangle r : ra) {
                if (r.getColor() == Color.BLUE) {
                    count++;
                }
            }
        }
        return count >= max;
    }
    
    public boolean checkCollision() {
        return blue.getX() == red.getX() && blue.getY() == red.getY();
    }
}
