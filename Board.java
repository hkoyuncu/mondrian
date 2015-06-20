import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

public class Board extends JPanel {
    
    private Circle blue, red;

    private final int WIDTH = 200;
    private final int HEIGHT = 200;

    public Board() {
        super();
        
        blue = new Circle(10);
        blue.setC(Color.BLUE);
        

        setBackground(Color.WHITE);
        
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setDoubleBuffered(true);
        addKeyListener(new KeyHandler());
        
        // Rand ist blau
        Border border = this.getBorder();
        Border margin = new LineBorder(Color.BLUE, 4);
        this.setBorder(new CompoundBorder(border, margin));
        
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawCircle((Graphics2D) g);
    }
    
    private void drawCircle(Graphics2D g2) {
        g2.setColor(blue.getC());
        g2.fillOval(blue.getX()+50, blue.getY()+50, blue.getSize(), blue.getSize());
        
    }
    

    private class KeyHandler implements KeyListener {
        
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    JOptionPane.showMessageDialog(null, "left!");
                    break;
                case KeyEvent.VK_RIGHT:
                    JOptionPane.showMessageDialog(null, "right!");
                    break;
                case KeyEvent.VK_UP:
                    JOptionPane.showMessageDialog(null, "up!");
                    break;
                case KeyEvent.VK_DOWN:
                    JOptionPane.showMessageDialog(null, "down!");
                    break;
            }
        }
        
        @Override
        public void keyTyped(KeyEvent e) { }
        
        @Override
        public void keyReleased(KeyEvent e) { } 
    }
}
