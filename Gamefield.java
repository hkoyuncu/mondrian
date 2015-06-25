
import java.awt.*;
import java.util.ArrayList;

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
            field[blue.getX()/10][blue.getY()/10].setColor(Color.BLUE);
            
            blue.move(x, y);
            
            moveControll(blue.getX() - x, blue.getY() - y, blue.getX(), blue.getY());
        }
    }
    
    // nochmals checken
    private void moveControll(int xOld, int yOld, int xNew, int yNew) {
        // Koordinaten in Indizes umwandeln
        xOld /= 10; yOld /= 10; xNew /= 10; yNew /= 10;
    }
    
 
    public Circle getBlue() {
        return blue;
    }
    public Circle getRed() {
        return red;
    }
    
    public void moveBlueTimer(final int blueMoveSpeed) {
        int x = (int) (Math.random() * 3);
        int y = (int) (Math.random() * 3);
        
        if (x == 0 && y == 0) {
            blue.move(0, 0);
        }
        if (x == 0 && y == 1) {
            if (((blue.getY() / 10) + (blueMoveSpeed / 10)) > 19) return;
            Rectangle toPos = field[blue.getX() / 10][blue.getY() / 10 + blueMoveSpeed / 10];
            if (toPos.getColor() != Color.YELLOW) {
                field[blue.getX()/10][blue.getY()/10].setColor(Color.BLUE);
                blue.move(0, blueMoveSpeed);
                
            }
        }
        if (x == 0 && y == 2) {
            if (((blue.getY() / 10) - (blueMoveSpeed / 10)) < 0) return;
            Rectangle toPos = field[blue.getX() / 10][blue.getY() / 10 - blueMoveSpeed / 10];
            if (toPos.getColor() != Color.YELLOW) {
                field[blue.getX()/10][blue.getY()/10].setColor(Color.BLUE);
                blue.move(0, -blueMoveSpeed);
            }
        }
        if (x == 1 && y == 0) {
            if (((blue.getX() / 10) + (blueMoveSpeed / 10)) > 19) return;
            Rectangle toPos = field[blue.getX() / 10 + blueMoveSpeed / 10][blue.getY() / 10];
            if (toPos.getColor() != Color.YELLOW) {
                field[blue.getX()/10][blue.getY()/10].setColor(Color.BLUE);
                blue.move(blueMoveSpeed, 0);
            }
        }
        if (x == 2 && y == 0) {
            if (((blue.getX() / 10) - (blueMoveSpeed / 10)) < 0)  return;
            Rectangle toPos = field[blue.getX() / 10 - blueMoveSpeed / 10][blue.getY() / 10];
            if (toPos.getColor() != Color.YELLOW) {
                field[blue.getX()/10][blue.getY()/10].setColor(Color.BLUE);
                blue.move(-blueMoveSpeed, 0);
            }
        }
    }
    
    public void moveRedTimer(final int redMoveSpeeed) {
        int x = (int) (Math.random() * 3);
        int y = (int) (Math.random() * 3);
        
        if (x == 0 && y == 0) {      
            red.move(0, 0);
        }
        if (x == 0 && y == 1) {  // nach unten bewegen
            if (((red.getY()/10) + (redMoveSpeeed/10)) > 19 ) return;
            Rectangle toPos = field[red.getX()/10][red.getY()/10 + redMoveSpeeed/10];
            if (toPos.getColor() == Color.BLUE) {
                red.move(0, redMoveSpeeed/2);  
                return ; 
            }
            red.move(0, redMoveSpeeed);  
        }
        if (x == 0 && y == 2) { // nach oben bewegen
            if (((red.getY()/10) - (redMoveSpeeed/10)) < 1 ) return;
            Rectangle toPos = field[red.getX()/10][red.getY()/10 - redMoveSpeeed/10];
            if (toPos.getColor() == Color.BLUE) {
                red.move(0, -redMoveSpeeed/2);  
                return ; 
            }
            red.move(0, -redMoveSpeeed);
        }
        if (x == 1 && y == 0) {
            if (red.getX()/10 + redMoveSpeeed/10 > 19 ) return;
            Rectangle toPos = field[red.getX()/10 + redMoveSpeeed/10][red.getY()/10];
            if (toPos.getColor() == Color.BLUE) {
                red.move(redMoveSpeeed/2, 0);  
                return ; 
            }
            red.move(redMoveSpeeed, 0);
        }
        if (x == 1 && y == 1) {
            if (red.getX()/10 + redMoveSpeeed/10 > 19 || red.getY()/10 + redMoveSpeeed/10 > 19 ) return;
            Rectangle toPos = field[red.getX()/10 + redMoveSpeeed/10][red.getY()/10 + redMoveSpeeed/10];
            if (toPos.getColor() == Color.BLUE) {
                red.move(redMoveSpeeed/2, redMoveSpeeed/2);  
                return ; 
            }
            red.move(redMoveSpeeed, redMoveSpeeed); 
        }
        if (x == 1 && y == 2) {
            if (red.getX()/10 + redMoveSpeeed/10 > 19 || red.getY()/10 - redMoveSpeeed/10 < 1 ) return;
            Rectangle toPos = field[red.getX()/10 + redMoveSpeeed/10][red.getY()/10 - redMoveSpeeed/10];
            if (toPos.getColor() == Color.BLUE) {
                red.move(redMoveSpeeed/2, -redMoveSpeeed/2);  
                return ; 
            }
            red.move(redMoveSpeeed, -redMoveSpeeed);
        }
        if (x == 2 && y == 0) {
            if (red.getX()/10 - redMoveSpeeed/10 < 1 ) return;
            Rectangle toPos = field[red.getX()/10 - redMoveSpeeed/10][red.getY()/10];
            if (toPos.getColor() == Color.BLUE) {
                red.move(-redMoveSpeeed/2, 0);  
                return ; 
            }
            red.move(-redMoveSpeeed, 0);
        }
        if (x == 2 && y == 1) {
            if (red.getX()/10 - redMoveSpeeed/10 < 1  || red.getY()/10 + redMoveSpeeed/10 > 19 ) return;
            Rectangle toPos = field[red.getX()/10 - redMoveSpeeed/10][red.getY()/10 + redMoveSpeeed/10];
            if (toPos.getColor() == Color.BLUE) {
                red.move(-redMoveSpeeed/2, redMoveSpeeed/2);  
                return ; 
            }
            red.move(-redMoveSpeeed, redMoveSpeeed);
        }
        if (x == 2 && y == 2) {
            if (red.getX()/10 - redMoveSpeeed/10 < 1  || red.getY()/10 - redMoveSpeeed/10 < 1 ) return;
            Rectangle toPos = field[red.getX()/10 - redMoveSpeeed/10][red.getY()/10 - redMoveSpeeed/10];
            if (toPos.getColor() == Color.BLUE) {
                red.move(-redMoveSpeeed/2, -redMoveSpeeed/2);  
                return ; 
            }
            red.move(-redMoveSpeeed, -redMoveSpeeed);
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
    
    public boolean checkCollision(final int redMoveSpeed) {
        if (blue.getX() == red.getX() && blue.getY() == red.getY()) {
            return true;
        }

        int x = red.getX()/10;
        int y = red.getY()/10;
        
        if (field[x-1][y-1].getPresent() == 0 && field[x-1][y-1].getColor() == Color.BLUE) {
            return true;
        }
        if (field[x][y].getPresent() == 0 && field[x][y].getColor() == Color.BLUE) {
            return true;
        }
        return false;
    }
}
