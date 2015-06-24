
import java.awt.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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
                field[i][j].setPresent(0);
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
        
        
        if (field[blue.getX()/10 + x/10][blue.getY()/10 + y/10].getColor() != Color.YELLOW) {
            field[blue.getX()/10 ][blue.getY()/10].setColor(Color.BLUE);
            blue.setX(blue.getX() + x);
            blue.setY(blue.getY() + y);
            moveControll(x,y);
        }
        
    }
    
    // nochmals checken
    private void moveControll(int x, int y) {
        if (field[blue.getX() / 10][blue.getY() / 10].getPresent() == 1) {
            moving = false;
            area.add(field[ (blue.getX()/10) ][(blue.getY()/10)  ]);
            fillArea();
            area.clear();
        }
        if (field[blue.getX() / 10][blue.getY() / 10].getPresent() == 0) {
            moving = true;
        }
        if (field[(blue.getX()/10) - (x/10)][(blue.getY()/10) - (y/10)].getPresent() == 0 && moving) {
            area.add(field[(blue.getX()/10) - (x/10)][(blue.getY()/10) - (y/10)]);
        }
        System.out.println("P: " + field[blue.getX() / 10][blue.getY() / 10].getPresent()  + "   moving: " + moving);
    }
    
    // ist ok
    private void fillArea() {
        if (area.isEmpty() || area.size() < 2) return;
        //print(area);
        area = optimize(area);
        //System.out.println("-----------------");
        //print(area);
        Rectangle start = area.get(0);
        Rectangle end = area.get(area.size() - 1);

        // nach rechts gegangen
        if (start.getY() == end.getY()) {
            //System.out.println("1");
            // check unter bzw ober halb des roten punktes
            if (start.getY() < red.getY()) {
                //System.out.println("2");
                for (int x = start.getX()/10; x < end.getX()/10; x++) {
                    for (int y = 1; y < end.getY()/10; y++) {
                        field[x][y].setColor(Color.YELLOW);
                        field[x][y].setPresent(1);
                    }
                }
                setPresent(area);
                area.clear();
                return;
            }
            // unterhalb vom roten punkt
            if (start.getY() > red.getY()) {
                //System.out.println("3");
                for (int x = start.getX()/10; x < end.getX()/10; x++) {
                    for (int y = start.getY()/10 + 1; y < 19; y++) {
                        field[x][y].setColor(Color.YELLOW);
                        field[x][y].setPresent(1);
                    }
                }
                setPresent(area);
                area.clear();
                return;
            }
        }
        // nach unten gegangen
        if (start.getX() == end.getX()) {
            //System.out.println("4");
            // check links bzw rechts vom roten punkt
            if (start.getX() < red.getX()) {
                //System.out.println("5");
                for (int x = 1; x < end.getX()/10; x++) {
                    for (int y = start.getY()/10; y < end.getY()/10; y++) {
                        field[x][y].setColor(Color.YELLOW);
                        field[x][y].setPresent(1);
                    }
                }
                setPresent(area);
                area.clear();
                return;
            }
            // rechts vom roten punkt
            if (start.getX() > red.getX()) {
                //System.out.println("6");
                for (int x = start.getX()/10 + 1; x < 19; x++) {
                    for (int y = start.getY()/10; y < end.getY()/10; y++) {
                        field[x][y].setColor(Color.YELLOW);
                        field[x][y].setPresent(1);
                    }
                }
                setPresent(area);
                area.clear();
            }
        }
    }
    
    private void print(ArrayList<Rectangle> a) {
        for (Rectangle x : a) {
            System.out.println(x);
        }
    }
    
    private void setPresent(ArrayList<Rectangle> rec) {
        for (Rectangle x : rec) {
            x.setPresent(1);
        }
    }

    // ist ok
    private ArrayList<Rectangle> optimize(ArrayList<Rectangle> a) {
        Rectangle start = a.get(0);
        Rectangle end = a.get(a.size() - 1);
        if (start.getY() > end.getY()) {
            ArrayList<Rectangle> opt = new ArrayList<Rectangle>();
            for (int i = a.size() - 1; i >= 0; i--) {
                a.get(i).setY(a.get(i).getY() + 10);
                opt.add(a.get(i));
            }
            return opt;
        }
        if (start.getX() > end.getX()) {
            ArrayList<Rectangle> opt = new ArrayList<Rectangle>();
            for (int i = a.size() - 1; i >= 0; i--) {
                a.get(i).setX(a.get(i).getX() + 10);
                opt.add(a.get(i));
            }
            return opt;
        }
        else {
            return a;
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
