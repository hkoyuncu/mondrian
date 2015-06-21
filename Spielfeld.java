
import java.awt.Color;
import java.awt.Graphics;

public class Spielfeld {
    
    private Rectangle ras[][];
    
    public Spielfeld() {
        ras = new Rectangle[20][20];
    }
    
    public void init(Graphics g) { 

        int mult = 10;
        
        for (int i = 0; i < ras.length; i++) {
            for (int j = 0; j <ras[i].length; j++) {            
                ras[i][j] = new Rectangle(i*mult, j*mult, 10, 10, Color.WHITE);
                
                Rectangle t = ras[i][j];
                g.setColor(t.getColor());
                g.fillRect(t.getX(), t.getY(), t.getW(), t.getH());
            }
        }
        fillBorder(g);
    }
    
    public void update(Graphics g) {
        for (Rectangle[] ra : ras) {
            for (Rectangle r : ra) {
                g.setColor(r.getColor());
                g.fillRect(r.getX(), r.getY(), r.getW(), r.getH());
            }
        }
    }
    
    
    private void fillBorder(Graphics g) {
        for (int i = 0; i < ras.length; i++) {
            for (int j = 0; j < ras[i].length; j++) {
                if (i == 0) {
                    Rectangle t = ras[i][j];
                    t.setColor(Color.BLUE);
                    g.setColor(t.getColor());
                    g.fillRect(t.getX(), t.getY(), t.getW(), t.getH());
                }
                else if (i == ras[i].length-1) {
                    Rectangle t = ras[i][j];
                    t.setColor(Color.BLUE);
                    g.setColor(t.getColor());
                    g.fillRect(t.getX(), t.getY(), t.getW(), t.getH());
                }
                else if (j == 0) {
                    Rectangle t = ras[i][j];
                    t.setColor(Color.BLUE);
                    g.setColor(t.getColor());
                    g.fillRect(t.getX(), t.getY(), t.getW(), t.getH());
                }
                else if (j == ras.length-1) {
                    Rectangle t = ras[i][j];
                    t.setColor(Color.BLUE);
                    g.setColor(t.getColor());
                    g.fillRect(t.getX(), t.getY(), t.getW(), t.getH());
                }
            }
        }
    }
    
    public void move(Circle p, int x, int y) {
        if (p.getX() + x < 0 || p.getX() + x > 190) {
            System.out.println("err rand x!");
            return;
        }
        if (p.getY() + y < 0 || p.getY() + y > 190) {
            System.out.println("err rand y!");
            return;
        }
        System.out.println("Feld vor fill: " + ras[(p.getX() + x) / 10][(p.getY() + y) / 10].getColor().toString());
        fillPosition(p.getX() + x, p.getY() + y);
        System.out.println("Feld nach fill: " + ras[(p.getX() + x) / 10][(p.getY() + y) / 10].getColor().toString());
        
        p.setX(p.getX() + x);
        p.setY(p.getY() + y);
    }
    
    private void fillPosition(int x, int y) {
        Rectangle t = ras[x/10][y/10];
        if (t.getColor() == Color.BLUE) {
            return;
        }
        else {
            t.setColor(Color.BLUE);
        }
    }
    
    public void printFeld() {
        System.out.println("--------------------------------------");
        for (int i = 0; i < ras.length; i++) {
            for (int j = 0; j <ras[i].length; j++) {
                if (ras[i][j].getColor() == Color.BLUE) 
                    System.out.print("B  ");
                System.out.print("W  ");
            }
            System.out.println();
        }
        System.out.println("--------------------------------------");
    }
    
    
    // Methoden um auf das Feld zuzugreifen
    public void test() {
        
    }

    public Rectangle[][] getField() {
        return ras;
    }
}
