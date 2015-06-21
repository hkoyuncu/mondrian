
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Board extends JPanel {
    
    private Gamefield feld;
    
    private final int WIDTH = 200;
    private final int HEIGHT = 200;
     
    private final int MOVESPEED = 10;
    
    private boolean fist;

    public Board() {
        super();
        
        fist = false;
        
        feld = new Gamefield();
        feld.addPlayer(new Circle(20, 20, 10, Color.ORANGE));
        feld.addPlayer(new Circle(100, 100, 10, Color.red));
        
        addKeyListener(new KeyHandler());

        setFocusable(true);
        setPreferredSize(new Dimension(200, 200));
        
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawCircle(g);
        repaint();
    }
    
    private void drawCircle(Graphics g) {
        if (!fist) {
            feld.init(g);
            fist = true;
        }
        feld.update(g);
    }
    
    private class KeyHandler implements KeyListener {
        
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
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyReleased(KeyEvent e) { }
    
    }
}
