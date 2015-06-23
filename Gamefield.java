
import java.awt.*;
import java.util.ArrayList;

public class Gamefield {
    
    private Circle blue, red;
    private Rectangle[][] field;
    private boolean first;
    
    private ArrayList<Rectangle> area;
    private boolean moving;
    
    public Gamefield() {
        field = new Rectangle[20][20];
        area = new ArrayList<Rectangle>();
        first = false;
        moving = false;
    }
    
    public void draw(Graphics g) {
        if (!first) {
            init();
            first = true;
            moving = true;
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
            field[0][i].setColor(Color.BLUE);  field[0][i].setPresent(1);
            field[i][0].setColor(Color.BLUE);  field[i][0].setPresent(1);
            field[field.length - 1][i].setColor(Color.BLUE); field[field.length - 1][i].setPresent(1);
            field[i][field.length - 1].setColor(Color.BLUE); field[i][field.length - 1].setPresent(1);
        }
    }

    public void move(int x, int y) {
        if (blue.getX() + x < 0 || blue.getX() + x > 190) {
            return;
        }
        if (blue.getY() + y < 0 || blue.getY() + y > 190) {
            return;
        }
        
        lel(x,y);
        
        if (field[blue.getX()/10][blue.getY()/10].getColor() != Color.YELLOW) 
            field[blue.getX()/10][blue.getY()/10].setColor(Color.BLUE);
        
        blue.setX(blue.getX() + x);
        blue.setY(blue.getY() + y);
 
    }
    
    
    private void lel(int x, int y) {
        if (field[(blue.getX() / 10) + x / 10][(blue.getY() / 10) + y / 10].getPresent() == 1) {
            moving = false;
            area.add(field[blue.getX() / 10 + x / 10][blue.getY() / 10 + y / 10]);
            fillArea();
            area.clear();
        }
        if (field[blue.getX()/10 + x/10][blue.getY()/10 + y/10].getPresent() == 0) {
            moving = true;
        }
        if (field[blue.getX() / 10][blue.getY() / 10].getPresent() == 0 && moving) {
            area.add(field[blue.getX() / 10][blue.getY() / 10]);
        }

        System.out.println("P: " + field[blue.getX() / 10][blue.getY() / 10].getPresent()  + "   moving: " + moving);
    }
    
    private void fillArea() {
        if (area.isEmpty()) return;
        
        Rectangle start = area.get(0);
        Rectangle end = area.get(area.size()-1);
        
        // links von roten punkt
        if (start.getX() < red.getX()) {
            // links oberhablb von red
            if (start.getY() < red.getY()) {
                
                if (end.getX() < red.getX()) {
                    for (int i = 1; i < start.getX()/10; i++) {
                        for (int j = 1; j < end.getY()/10; j++) {
                            field[i][j].setColor(Color.YELLOW);
                            field[i][j].setPresent(1);
                        }
                    }
                }
                if (end.getX() > red.getX()) {
                    for (int i = start.getX()/10; i < end.getX()/10; i++) {
                        for (int j = 1; j < end.getY()/10; j++) {
                            field[i][j].setColor(Color.YELLOW);
                            field[i][j].setPresent(1);
                        }
                    }
                } 
            }
            
            // ab hier weiter machen .................
            
            // linkls unterhalb von red
            if (start.getY() > red.getY()) {
                if (end.getX() < red.getX()) {
                    
                }
                
                if (end.getX() > red.getX()) {
                    
                }
            }
        }
        // rechts vom roten punkt
        if (start.getX() > red.getY()) {
            if (start.getY() < red.getY()) {
                
            }
            if (start.getY() > red.getY()) {
                
            }
        }
        setPresent();
    }
    
    private void setPresent() {
        for (int i = 1 ;i < field.length; i++) {
            for (int j = 1; j < field[i].length; j++) {
                if (field[i][j].getColor() == Color.BLUE) {
                    field[i][j].setPresent(1);
                }
            }
        }
    }
    
    public boolean checkWin() {
        int count = 0;
        final int max = 320;
        for (Rectangle[] ra : field) {
            for (Rectangle r : ra) {
                if (r.getColor() == Color.BLUE || r.getColor() == Color.YELLOW) {
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
