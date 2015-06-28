
import java.awt.*;

/**
 * Diese Klasse repräsentiert eine Rechteck-Figur. Nachdem das gesamte Spielfeld
 * nach einem Raster(2-dimensionales Array) aufgebaut ist, wird zu jedem Punkt
 * eine Rechteck-Figur gespeichert.
 * 
 * Die Variable "present" speichert, ob ein Rechteck vollkommen ist. Ein Rechteck
 * ist vollkommen, wenn der blaue Ball eine Linie von einem Rand zu einem anderen
 * Rand zieht. Dann ist es vollkommen (present = 1) und der rote Ball prallt nun
 * von dieser Linie ab, falls es die Linie berührt. 
 * 
 * Während der blaue Ball die Linie zieht, ist das Rechteck nicht vollkommen
 * (present = 0) und diese Eigenschaft dient zur Kollissionsüberprüfung, weil das
 * Spiel verloren ist, wenn der rote Ball die Linie berührt bevor der blaue Ball
 * ein sicheres Ende (blaues Rand) erreicht hat.
 * 
 * 
 * @author Koyuncu Harun, Valentin Zambelli
 * @version 2015.06.27
 */
public class Rectangle {
    
    private int x;
    private int y;
    
    private final int w;
    private final int h;
    
    private Color color;
    
    private int present;

    /**
     * Default Konstruktor wird überschrieben.
     * 
     * @param x         x-Koordinare vom Rechteck
     * @param y         y-Koordinare vom Rechteck
     * @param w         Länge vom Rechteck
     * @param h         Breite vom Rechteck
     * @param c         Farbe vom Rechteck
     */
    public Rectangle(int x, int y, int w, int h, Color c) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.present = 0;
        this.color = c;
    }
    
    /**
     * Diese Methode zeichnet das aktuelle Rechteck neu.
     * 
     * @param g     mit diesem Graphics-Object wird das Rechteck gezeichnet
     */
    public void drawRectanbgle(Graphics g) {
        // Farbe vom Rechteck definieren
        g.setColor(color);
        // Rechteck im Farme zeichnen
        g.fillRect(x, y, w, h);
    }

    public void setColor(Color c) { this.color = c; }
    
    public Color getColor() { return color; }

    public int getPresent() { return present; }

    public void setPresent(int present) { this.present = present; }
    
    public int getX() { return x/10; }

    public int getY() { return y/10; }
    
    public void setX(int x) { this.x = x; } 
    
    public void setY(int y) { this.y = y; } 
    
    @Override
    public String toString() { return "X: " + x + "  Y: " + y  + "   p: " + present; }
 
}