
import java.awt.*;

/**
 * Diese Klasse stellt einen Kreis mit einer bestimmten Farbe dar. 
 * Die Dimension der Koordinaten sind in Pixel festgehalten, da aber
 * das Feld in einer 2-dimensionalen Datenstruktur gespeichert wird,
 * wurde bei den Getter-Methode immer durch 10 dividiert.
 * 
 *  Bsp:    Kreis mit x = 160 und y = 80
 *          |
 *          |
 *          ---->  Zugriff auf das Array mit:   feld[16][8]
 * 
 * @author Koyuncu Harun, Valentin Zambelli
 * @version 2015.06.27
 */
public class Circle {
    
    private int x;
    private int y;
    
    private final int size;
    private final Color color;
    
    /**
     * Default-Konstruktor wird überschrieben.
     * 
     * @param x         x-Koordinate vom Kreis
     * @param y         y-Koordinate vom Kreis
     * @param size      Größe vom Kreis
     * @param c         Farbe vom Kreis
     */
    public Circle(int x, int y, int size, Color c) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.color = c;
    }
    
    /**
     * Die Methode bewegt den Kreis zu einer neuen Position
     * im Frame.
     * 
     * Hier werden die Koordinaten jeweils mit 10 multipliziert, damit wir
     * von der Array-Ebene wieder zurück in die Pixel-Ebene kommen und
     * die Bewegung vom Kreis richtig dargestellt wird.
     * 
     * @param x     wieweit in x-Richtung gegangen werden soll
     * @param y     wieweit in y-Richtung gegangen werden soll
     */
    public void move(int x, int y) {
        this.x += (x*10);
        this.y += (y*10);
    }
    
    /**
     * Diese Methode zeichnet den aktuellen Kreis neu.
     * 
     * @param g     mit diesem Graphics-Object wird der Kreis gezeichnet
     */
    public void drawCircle(Graphics g) {
        // Farbe vom Kreis definieren
        g.setColor(color);
        // Kreis zeichen 
        g.fillOval(x, y, size, size);
    }
    
    public int getX() { return x/10; }
    
    public int getY() { return y/10; }

    public void setX(int x) { this.x = x; }

    public void setY(int y) { this.y = y; }

    public Color getColor() { return color; }
    
    @Override
    public String toString() { return "x: " + x +  "    y: " + y; }
}
