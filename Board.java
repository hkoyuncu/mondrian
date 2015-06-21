
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Board extends JPanel implements KeyListener {
    
    private Circle blue, red;
    private Spielfeld feld;
    private boolean firstinit;
    
    private final int WIDTH = 200;
    private final int HEIGHT = 200;
     
    private final int MOVESPEED = 10;

    public Board() {
        super();
        
        feld = new Spielfeld();
        firstinit = false;
        blue = new Circle(10, Color.BLUE);
        blue.setX(50); blue.setY(50);

        addKeyListener(this);
        setFocusable(true);

        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(200, 200));

    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawCircle((Graphics2D) g);
        //feld.printFeld();
    }
    
    private void drawCircle(Graphics2D g2) {
        feld.init(g2);
        feld.update(g2);
        g2.setColor(blue.getColor());
        g2.fillOval(blue.getX(), blue.getY(), blue.getSize(), blue.getSize());
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                feld.move(blue, -MOVESPEED, 0);
                System.out.println("x: " + blue.getX() + "   y: " + blue.getY());
                break;
            case KeyEvent.VK_RIGHT:
                feld.move(blue, MOVESPEED, 0);
                System.out.println("x: " + blue.getX() + "   y: " + blue.getY());
                break;
            case KeyEvent.VK_UP:
                feld.move(blue, 0, -MOVESPEED);
                System.out.println("x: " + blue.getX() + "   y: " + blue.getY());
                break;
            case KeyEvent.VK_DOWN:
                feld.move(blue, 0, MOVESPEED);
                System.out.println("x: " + blue.getX() + "   y: " + blue.getY());
                break;
        }
        firstinit = true;
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
