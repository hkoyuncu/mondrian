
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Board extends JPanel {
    
    private Gamefield feld;
    
    private final int WIDTH = 200;
    private final int HEIGHT = 200;
     
    private final int BLUEMOVESPEED = 10;
    private final int REDMOVESPEED = 2 * BLUEMOVESPEED;
    
    private boolean control;
    private Timer timer;

    public Board() {
        super();
        
        control = true;
        
        feld = new Gamefield();
        feld.addPlayer(new Circle(0, 0, 10, Color.ORANGE));
        feld.addPlayer(new Circle(90, 90, 10, Color.red));
        
        addKeyListener(new KeyHandler());
        timer = new Timer(1000, new ActionHandler());
        timer.start();

        setFocusable(true);
        setPreferredSize(new Dimension(200, 200));
        
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        feld.draw(g);
        repaint();
    }

    private class KeyHandler implements KeyListener {
        
        @Override
        public void keyPressed(KeyEvent e) {
            if (control) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        feld.move(-BLUEMOVESPEED, 0);
                        break;
                    case KeyEvent.VK_RIGHT:
                        feld.move(BLUEMOVESPEED, 0);
                        break;
                    case KeyEvent.VK_UP:
                        feld.move(0, -BLUEMOVESPEED);
                        break;
                    case KeyEvent.VK_DOWN:
                        feld.move(0, BLUEMOVESPEED);
                        break;
                }
                //feld.moveRedTimer(REDMOVESPEED);
            }
            
            if (feld.checkWin()) {
                timer.stop();
                JOptionPane.showMessageDialog(null, "Sie haben das Spiel gewonnen!", "Gratulation", JOptionPane.INFORMATION_MESSAGE);
                control = false;
            }

            if (feld.checkCollision(REDMOVESPEED)) {
                timer.stop();
                JOptionPane.showMessageDialog(null, "Sie haben das Spiel verloren!", "Verloren", JOptionPane.INFORMATION_MESSAGE);
                control = false;
            }

        }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyReleased(KeyEvent e) { }

    }
    
    private class ActionHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //feld.moveBlueTimer(BLUEMOVESPEED);
            feld.moveRedTimer(REDMOVESPEED);
            repaint();
        }
    }
}
