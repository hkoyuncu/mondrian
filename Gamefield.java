
import java.awt.*;
import java.util.*;

public class Gamefield {
    
    private Circle blue, red;  
    private Rectangle[][] field;
    private Color[] colors = { Color.RED, Color.YELLOW, Color.BLUE, Color.BLACK, Color.LIGHT_GRAY };
    private boolean first;
  
    
    private ArrayList<Rectangle> area;
    private boolean moving;
    
    // ok
    public Gamefield() {
        field = new Rectangle[20][20];
        first = false;
        area = new ArrayList<Rectangle>();
        moving = false;
    }
    
    // ok
    public void draw(Graphics g) {
        if (!first) {
            init();
            first = true;
        }
        update(g);
    }
    
    // ok
    public void addPlayer(Circle c) {
        if (c.getColor() == Color.BLUE || c.getColor() == Color.ORANGE) {
            blue = c;
        }
        if (c.getColor() == Color.RED) {
            red = c;
        }
    }

    // ok
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
    
    // ok
    public void update(Graphics g) {
        for (Rectangle[] ra : field) {
            for (Rectangle r : ra) {
                r.drawRectanbgle(g);
            }
        }
        blue.drawCircle(g);   
        red.drawCircle(g);
    }
    
    // ok
    private void fillBorder() {
        for (int i = 0; i < field.length; i++) {
            field[0][i].setColor(Color.BLUE);  field[0][i].setPresent(1);
            field[i][0].setColor(Color.BLUE);  field[i][0].setPresent(1);
            field[field.length - 1][i].setColor(Color.BLUE); field[field.length - 1][i].setPresent(1);
            field[i][field.length - 1].setColor(Color.BLUE); field[i][field.length - 1].setPresent(1);
        }
    }

    
    public void move(int x, int y) {
        x /= 10;
        y /= 10;
        if (blue.getX() + x < 0 || blue.getX() + x > 19) {
            return;
        }
        if (blue.getY() + y < 0 || blue.getY() + y > 19) {
            return;
        }
        if (field[blue.getX() + x][blue.getY() + y].getColor() != Color.YELLOW) {
            if (field[blue.getX()][blue.getY()].getPresent() == 0 && field[blue.getX()][blue.getY()].getColor() != Color.BLUE) {
                field[blue.getX()][blue.getY()].setColor(Color.BLUE);
            }

            blue.move(x, y);
            moveControll(blue.getX() - x, blue.getY() - y, blue.getX(), blue.getY());
        }
    }
    
    // nochmals checken
    private void moveControll(int xOld, int yOld, int xNew, int yNew) {

        // blue befindet sich am Rand
        if (field[xOld][yOld].getColor() == Color.BLUE && field[xOld][yOld].getPresent() == 1 && !moving) {
            System.out.println("rand");
        }
        else if (field[xNew][yNew].getColor() == Color.WHITE && field[xNew][yNew].getPresent() == 0 ) {
            moving = true;
            System.out.println("new point");
            area.add(field[xOld][yOld]);
        }
        // blue befinden sich auf weißem Feld
        else if (field[xOld][yOld].getPresent() == 0 && field[xNew][yNew].getPresent() == 1 && moving) {
            System.out.println("new point & end");
            area.add(field[xOld][yOld]);
            area.add(field[xNew][yNew]);
            moving = false;
            
            fill();
            //print(area);
            area.clear();
        }
    }
    
    // ist ok
    private void fill() {
        if (area.isEmpty() || area.size() < 2) {
            return;
        }
        // kleiner fehler
        //area = optimize(area);

        Rectangle s = area.get(0);
        Rectangle e = area.get(area.size() - 1);

        setPresent();

        // linke seite vom roten Punkt
        if (s.getX() < red.getX() && e.getX() < red.getX() || s.getY() < red.getY() && e.getY() > red.getY()) {
            System.err.println("links vom roten punkt");
            for (int i = s.getY(); i < e.getY(); i++) {
                int lineCounter = 1;

                Rectangle r = field[lineCounter][i];
                boolean run = true;

                while (run) {
                    if (r.getColor() == Color.BLUE && r.getPresent() == 1) {
                        run = false;
                    }
                    if (run) {
                        r.setColor(Color.YELLOW);
                        r.setPresent(1);
                        lineCounter++;
                        r = field[lineCounter][i];
                    }
                }
            }
        }
        // rechte seite vom roten punkt
        else if (s.getX() > red.getX() && e.getX() > red.getX() || s.getY() < red.getY() && e.getY() > red.getY()) {
            System.err.println("rechts vom roten punkt");
            for (int i = 19; i > e.getY(); i--) {
                int lineCounter = 1;

                Rectangle r = field[i][lineCounter];
                boolean run = true;

                while (run) {
                    if (r.getColor() == Color.BLUE && r.getPresent() == 1) {
                        run = false;
                    }
                    if (run) {
                        r.setColor(Color.YELLOW);
                        r.setPresent(1);
                        lineCounter++;
                        r = field[i][lineCounter];
                    }
                }
            }
        }
        // untere seite vom roten punkt
        else if (s.getX() < red.getX() && e.getX() > red.getX() && s.getY() > red.getY() && e.getY() > red.getY()) {
            System.err.println("unterhalb vom roten punkt");
            for (int i = s.getY(); i < e.getY(); i++) {
                int lineCounter = 1;

                Rectangle r = field[lineCounter][i];
                boolean run = true;

                while (run) {
                    if (r.getColor() == Color.BLUE && r.getPresent() == 1) {
                        run = false;
                    }
                    if (run) {
                        r.setColor(Color.YELLOW);
                        r.setPresent(1);
                        lineCounter++;
                        r = field[lineCounter][i];
                    }
                }
            }
        }
        else {
            System.err.println("oberhalb vom roten punkt");
            for (int i = s.getY(); i < e.getY(); i++) {
                int lineCounter = 1;

                Rectangle r = field[lineCounter][i];
                boolean run = true;

                while (run) {
                    if (r.getColor() == Color.BLUE && r.getPresent() == 1) {
                        run = false;
                    }
                    if (run) {
                        r.setColor(Color.YELLOW);
                        r.setPresent(1);
                        lineCounter++;
                        r = field[lineCounter][i];
                    }
                }
            }
        }
        area.clear();
    }
    
    private ArrayList<Rectangle> optimize(ArrayList<Rectangle> a) {
        Rectangle start = a.get(0);
        Rectangle end = a.get(a.size() - 1);
        if (start.getY() > end.getY()) {
            ArrayList<Rectangle> opt = new ArrayList<Rectangle>();
            for (int i = a.size() - 1; i >= 0; i--) {
                a.get(i).setY(a.get(i).getY()*10 + 10);
                opt.add(a.get(i));
            }
            return opt;
        }
        if (start.getX() > end.getX()) {
            ArrayList<Rectangle> opt = new ArrayList<Rectangle>();
            for (int i = a.size() - 1; i >= 0; i--) {
                a.get(i).setX(a.get(i).getX()*10 + 10);
                opt.add(a.get(i));
            }
            return opt;
        }
        else {
            return a;
        }
    }
    
    private void print(ArrayList<Rectangle> area) {
        area.stream().forEach((Rectangle x) -> {
            System.out.println(x);
        });
    }
    
    
    // ok
    private void setPresent() {
        area.stream().forEach((Rectangle x) -> {
            x.setPresent(1);
        });
    }

    // ok
    public Circle getBlue() { return blue; }
    
    // ok
    public Circle getRed() {  return red; }
    
    // ok
    public void moveBlueTimer( int blueMoveSpeed) {
        int x = (int) (Math.random() * 3);
        int y = (int) (Math.random() * 3);
        
        blueMoveSpeed /= 10;
        
        if (x == 0 && y == 1) {
            if (blue.getY() + blueMoveSpeed > 19) return;
            Rectangle toPos = field[blue.getX()][blue.getY() + blueMoveSpeed ];
            if (toPos.getColor() != Color.YELLOW) {
                field[blue.getX()][blue.getY()].setColor(Color.BLUE);
                blue.move(0, blueMoveSpeed);
            }
        }
        if (x == 0 && y == 2) {
            if (blue.getY() - blueMoveSpeed  < 0) return;
            Rectangle toPos = field[blue.getX()][blue.getY() - blueMoveSpeed];
            if (toPos.getColor() != Color.YELLOW) {
                field[blue.getX()][blue.getY()].setColor(Color.BLUE);
                blue.move(0, -blueMoveSpeed);
            }
        }
        if (x == 1 && y == 0) {
            if (blue.getX() + blueMoveSpeed > 19) return;
            Rectangle toPos = field[blue.getX() + blueMoveSpeed ][blue.getY()];
            if (toPos.getColor() != Color.YELLOW) {
                field[blue.getX()][blue.getY()].setColor(Color.BLUE);
                blue.move(blueMoveSpeed, 0);
            }
        }
        if (x == 2 && y == 0) {
            if (blue.getX() - blueMoveSpeed < 0)  return;
            Rectangle toPos = field[blue.getX() - blueMoveSpeed ][blue.getY()];
            if (toPos.getColor() != Color.YELLOW) {
                field[blue.getX()][blue.getY()].setColor(Color.BLUE);
                blue.move(-blueMoveSpeed, 0);
            }
        }
    }
    
    // ok
    public void moveRedTimer(int redMoveSpeeed) {
        int x = (int) (Math.random() * 3);
        int y = (int) (Math.random() * 3);
        
        redMoveSpeeed /= 10;
        
        if (x == 0 && y == 1) {                         // nach unten bewegen
            if (red.getY() + redMoveSpeeed > 19 ) return;
            for (int i = 0; i < 2; i ++) {
                Rectangle tmp = field[red.getX()][red.getY() + redMoveSpeeed - i];
                if (tmp.getPresent() == 1) {
                    return;
                }
            }
            Rectangle toPos = field[red.getX()][red.getY() + redMoveSpeeed];
            if (toPos.getColor() == Color.BLUE) {
                red.move(0, redMoveSpeeed/2);  
                return ; 
            }
            red.move(0, redMoveSpeeed);  
        }
        if (x == 0 && y == 2) {                         // nach oben bewegen
            if (red.getY() - redMoveSpeeed < 1 ) return;
            for (int i = 0; i < 2; i ++) {
                Rectangle tmp = field[red.getX()][red.getY() - redMoveSpeeed - i];
                if (tmp.getPresent() == 1) {
                    return;
                }
            }
            Rectangle toPos = field[red.getX()][red.getY() - redMoveSpeeed];
            if (toPos.getColor() == Color.BLUE) {
                red.move(0, -redMoveSpeeed/2);  
                return ; 
            }
            red.move(0, -redMoveSpeeed);
        }
        if (x == 1 && y == 0) {                         // nach rechts bewegen
            if (red.getX()+ redMoveSpeeed > 19 ) return;
            for (int i = 0; i < 2; i ++) {
                Rectangle tmp = field[red.getX() + redMoveSpeeed - i][red.getY()];
                if (tmp.getPresent() == 1) {
                    return;
                }
            }
            Rectangle toPos = field[red.getX() + redMoveSpeeed][red.getY()];
            if (toPos.getColor() == Color.BLUE) {
                red.move(redMoveSpeeed/2, 0);  
                return ; 
            }
            red.move(redMoveSpeeed, 0);
        }
        if (x == 1 && y == 1) {                     // nach rechts unten
            if (red.getX() + redMoveSpeeed > 19 || red.getY() + redMoveSpeeed > 19 ) return;
            for (int i = 0; i < 2; i ++) {
                Rectangle tmp = field[red.getX() + redMoveSpeeed - i][red.getY() + redMoveSpeeed - i];
                if (tmp.getPresent() == 1) {
                    return;
                }
            }
            Rectangle toPos = field[red.getX() + redMoveSpeeed][red.getY() + redMoveSpeeed];
            if (toPos.getColor() == Color.BLUE) {
                red.move(redMoveSpeeed/2, redMoveSpeeed/2);  
                return ; 
            }
            red.move(redMoveSpeeed, redMoveSpeeed); 
        }
        if (x == 1 && y == 2) {                     // nach rechts oben
            if (red.getX() + redMoveSpeeed> 19 || red.getY() - redMoveSpeeed < 1 ) return;
            for (int i = 0; i < 2; i ++) {
                Rectangle tmp = field[red.getX() + redMoveSpeeed - i][red.getY() - redMoveSpeeed - i];
                if (tmp.getPresent() == 1) {
                    return;
                }
            }
            Rectangle toPos = field[red.getX() + redMoveSpeeed][red.getY() - redMoveSpeeed];
            if (toPos.getColor() == Color.BLUE) {
                red.move(redMoveSpeeed/2, -redMoveSpeeed/2);  
                return ; 
            }
            red.move(redMoveSpeeed, -redMoveSpeeed);
        }
        if (x == 2 && y == 0) {                            // nach links bewegen
            if (red.getX() - redMoveSpeeed < 1 ) return;
            for (int i = 0; i < 2; i ++) {
                Rectangle tmp = field[red.getX() - redMoveSpeeed - i][red.getY()];
                if (tmp.getPresent() == 1) {
                    return;
                }
            }
            Rectangle toPos = field[red.getX() - redMoveSpeeed][red.getY()];
            if (toPos.getColor() == Color.BLUE) {
                red.move(-redMoveSpeeed/2, 0);  
                return ; 
            }
            red.move(-redMoveSpeeed, 0);
        }
        if (x == 2 && y == 1) {                                 // nach links unten
            if (red.getX() - redMoveSpeeed < 1  || red.getY() + redMoveSpeeed > 19 ) return;
            for (int i = 0; i < 2; i ++) {
                Rectangle tmp = field[red.getX() - redMoveSpeeed - i][red.getY() + redMoveSpeeed - i];
                if (tmp.getPresent() == 1) {
                    return;
                }
            }
            Rectangle toPos = field[red.getX() - redMoveSpeeed][red.getY() + redMoveSpeeed];
            if (toPos.getColor() == Color.BLUE) {
                red.move(-redMoveSpeeed/2, redMoveSpeeed/2);  
                return ; 
            }
            red.move(-redMoveSpeeed, redMoveSpeeed);
        }
        if (x == 2 && y == 2) {                                 // nach links oben
            if (red.getX() - redMoveSpeeed < 1  || red.getY() - redMoveSpeeed < 1 ) return;
            for (int i = 0; i < 2; i ++) {
                Rectangle tmp = field[red.getX() - redMoveSpeeed - i][red.getY() - redMoveSpeeed - i];
                if (tmp.getPresent() == 1) {
                    return;
                }
            }
            Rectangle toPos = field[red.getX() - redMoveSpeeed][red.getY() - redMoveSpeeed];
            if (toPos.getColor() == Color.BLUE) {
                red.move(-redMoveSpeeed/2, -redMoveSpeeed/2);  
                return ; 
            }
            red.move(-redMoveSpeeed, -redMoveSpeeed);
        }
    }

    // ok; impl fehlt
    public Color getRandomColor() {
        return colors[(int)(Math.random() * 5)];
    }
    
    // ok
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
    
    // ok; wird auf zentrum geprüft
    public boolean checkCollision(final int redMoveSpeed) {
        if (blue.getX() == red.getX() && blue.getY() == red.getY()) {
            return true;
        }
    
        if (field[red.getX()-1][red.getY()].getPresent() == 0 && field[red.getX()-1][red.getY()-1].getColor() == Color.BLUE) {
            return true;
        }
        if (field[red.getX()][red.getY()].getPresent() == 0 && field[red.getX()][red.getY()].getColor() == Color.BLUE) {
            return true;
        }
        return false;
    }
}
