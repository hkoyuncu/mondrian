
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Board extends JPanel implements KeyListener {
    
    private Gamefield feld;
    
    private final int WIDTH = 200;
    private final int HEIGHT = 200;
     
    private final int MOVESPEED = 10;

    public Board() {
        super();
        
        feld = new Gamefield();
        feld.addPlayer(new Circle(50, 50, 10, Color.BLUE));
        feld.addPlayer(new Circle(100, 100, 40, Color.red));
        

        addKeyListener(this);
        
        
        setFocusable(true);
        setPreferredSize(new Dimension(200, 200));
        
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawCircle(g);
    }
    
    private void drawCircle(Graphics g) {
        feld.init(g);
        feld.update(g);
        repaint();
    }
  
    
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                feld.move(-MOVESPEED, 0);
                break;
            case KeyEvent.VK_RIGHT:
                feld.move(MOVESPEED, 0);
                break;
            case KeyEvent.VK_UP:
                feld.move(0, -MOVESPEED);
                break;
            case KeyEvent.VK_DOWN:
                feld.move(0, MOVESPEED);
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
