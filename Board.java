
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class Board extends JPanel implements KeyListener {
    
    private Circle blue, red;

    private final int WIDTH = 200;
    private final int HEIGHT = 200;
    
    private final int MOVESPEED = 10;

    public Board() {
        super();
        
        blue = new Circle(10);
        blue.setC(Color.BLUE);

        addKeyListener(this);
        setFocusable(true);

        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(200, 200));
        
        // Rand ist blau
        Border border = this.getBorder();
        Border margin = new LineBorder(Color.BLUE, 2);
        this.setBorder(new CompoundBorder(border, margin));
        
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawCircle((Graphics2D) g);
    }
    
    private void drawCircle(Graphics2D g2) {
        g2.setColor(blue.getC());
        g2.fillOval(blue.getX(), blue.getY(), blue.getSize(), blue.getSize());
    }
    
    private void move(Circle p, int x, int y) {
        if (p.getX() + x < 0 || p.getX() + x > WIDTH-MOVESPEED) {
            System.out.println("err rand!");
            return;
        }
        if (p.getY() + y < 0 || p.getY() + y > HEIGHT-MOVESPEED) {
            System.out.println("err rand!");
            return;
        }
        p.setX(p.getX() + x);
        p.setY(p.getY() + y);
        repaint();
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                move(blue, -MOVESPEED,0);
                System.out.println("x: " + blue.getX() + "   y: " + blue.getY());
                break;
            case KeyEvent.VK_RIGHT:
                move(blue, MOVESPEED,0);
                System.out.println("x: " + blue.getX() + "   y: " + blue.getY());
                break;
            case KeyEvent.VK_UP:
                move(blue, 0,-MOVESPEED);
                System.out.println("x: " + blue.getX() + "   y: " + blue.getY());
                break;
            case KeyEvent.VK_DOWN:
                move(blue, 0,MOVESPEED);
                System.out.println("x: " + blue.getX() + "   y: " + blue.getY());
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
