import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;

public class Board extends JPanel implements KeyListener {

    private final int WIDTH = 200;
    private final int HEIGHT = 200;

    public Board() {
        super();

        setBackground(Color.WHITE);
        
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setDoubleBuffered(true);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawCircle((Graphics2D) g);
    }
    
    private void drawCircle(Graphics2D g2) {
        drawCircle(g2, 0, 0, 2, 2, Color.BLUE);
        drawCircle(g2, (200/2)-1, (200/2)-1, 2, 2, Color.RED);
    }
    
    private void drawCircle(Graphics2D g2, int x, int y, int w, int h, Color c) {
        
    }
    
    private void fillRectangle(Graphics g) {
        
    }

    
    /**
     * 
     * 
     * @param e 
     */
    @Override
    public void keyPressed(KeyEvent e) {
       
    }
    
    @Override
    public void keyTyped(KeyEvent e) { }
    
    @Override
    public void keyReleased(KeyEvent e) { }

}
