
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

/**
 * Diese Klasse übernimmt die gesamte Spiellogik. Sie stellt Methoden
 * dar wie zum Beispiel die Kollissionserkennung oder das Füllen der
 * entsprechenden Fläche, die vom blauen Punkt gezogen wurde usw.
 * 
 * @author Koyuncu Harun, Valentin Zambelli
 * @version 2015.06.28
 */
public class Gamefield {
    
    private int scoreCounter;
    
    // blauer Kreis-Objekt und roter Kreis-Objekt
    private Circle blue, red;
    // in diesem 2-dimensionalen Array aus Rectangles ist das Spielfeld aufgebaut
    private final Rectangle[][] field;
    // in diesem Array sind alle zulässigen Farben für das Füllen der Flächen,
    // die vom bleuen Kreis gezogen wurde, enthalten
    private final Color[] colors = { Color.RED, Color.YELLOW, Color.BLUE, Color.BLACK, Color.LIGHT_GRAY };
    // die Kontrollvariable dient beim starten des Spieles für die einmalige
    // Färbung des Spielrandes
    private boolean first;
    // In dieser Kontrollvariable wird gespeichert, wenn sich der blaue Kreis
    // in einem offfenen Feld(Farbe = weiß) befindet und sich zum nächsten Rand
    // bewegt
    private boolean moving;
    // in dieser Liste werden alle Punkte gespeichert, die der blaue Kreis
    // hintersich her zieht, bis es wieder am Rand ankommt 
    private ArrayList<Rectangle> area;
    
    /**
     * Default-Konstruktor wird überschrieben
     */
    public Gamefield() {
        // das Spielfeld wird in ein 20*20 Array aufgeteilt
        field = new Rectangle[Settings.GAME_SIZE][Settings.GAME_SIZE];
        // nachdem der Rand blau gefärbt wurde, erst dann wird die 
        // Kontrollvariable auf true gesetzt; zu Beginn ist sie auf false
        first = false;
        // ArrayList erzeugen
        area = new ArrayList<>();
        // der blaue Punkt bewegt sich zu Beginn nicht auf weißem Feld,
        // daher wird die Variable auf false gesetzt
        moving = false;
        // diese Variable speicher den aktuellen Scoretable vom Spieler
        scoreCounter = 0;
    }
    
    /**
     *        !!    OK   !!
     * 
     * Diese Methode iterriert duch das gesamte Array und füllt die Objekte
     * je nach ihrem Zustand in der entsorechenden Farbe
     * 
     * @param g     mit diesm Graphics-Objekt wird das Objekt grfärbt
     */
    public void draw(Graphics g) {
        // wenn der Rand noch nicht gefärbt wurde, dann wird dieser
        // blau gefärbt, ansonsten wird nur noch das innere des
        // Spielfeldes neugezeichnet/neugefärbt
        if (!first) {
            // Speilfeld erzeugen und Rand blau färben
            init();
            fillBorder();
            // da der Rand nun blau ist, wird die Kontrollvariable auf
            // true gesetzt
            first = true;
        }
        // Speilfeld wird je nach Zustand des Objektes neu gezeichnet
        update(g);
    }

    /**
     *          !!    OK   !!
     * 
     * In dieser Methode werden die Spieler im Spiel erzeugt
     * 
     * @param c         Farbe des Spielers
     */
    public void addPlayer(Circle c) {
        // wenn die Farbe blau bzw. orange ist, dann wird ein neuer
        // blauer (oranger) Spieler erzeugt
        if (c.getColor() == Color.BLUE || c.getColor() == Color.ORANGE) {
            blue = c;
        }
        // wenn die Farbe rot ist, dann wird der feindlicher Spieler 
        // erzeugt
        if (c.getColor() == Color.RED) {
            red = c;
        }
    }

    /**
     *            !!    OK   !!
     * 
     * In dieser Methode wird das Spieldelf erzeugt und das Array
     * mit Rectangle-Objekten gefüllt. Die Farbe wird auf weiß gesetzt und
     * das present Bit auf 0
     */
    public void init() { 
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j <field[i].length; j++) {            
                field[i][j] = new Rectangle(i*Settings.ONE_FRAME, j*Settings.ONE_FRAME, Settings.ONE_FRAME, Settings.ONE_FRAME, Color.WHITE);
                field[i][j].setPresent(0);
            }
        }
    }
    
    /**
     *             !!    OK   !!
     * 
     * Diese Methode zeichnet alle Objekte des Spielfeldes neu bzw.
     * aktualisiet sie mit Hilfe des Graphics-Objektes.
     * 
     * @param g         Objekte des Spielfeldes werden gezeichnet
     */
    public void update(Graphics g) {
        // alle Array-Objekte(Spielfeld) zeichnen
        for (Rectangle[] ra : field) {
            for (Rectangle r : ra) {
                r.drawRectanbgle(g);
            }
        }
        // blauen und roten Punkt ebenfalls zeichnen
        blue.drawCircle(g);   
        red.drawCircle(g);
    }
    
    /**
     *             !!    OK   !!
     * 
     * In dieser Methode wird der Rand des Spielfeldes blau gefärbt
     * und das present Bit auf 1 gesetzt. Dadurch kann sich der blaue
     * Punkt ohne der Gefahr vom roten Punkt in diesem Bereich bewegen
     */
    private void fillBorder() {
        for (int i = 0; i < field.length; i++) {
            field[0][i].setColor(Color.BLUE);  field[0][i].setPresent(1); scoreCounter++;
            field[i][0].setColor(Color.BLUE);  field[i][0].setPresent(1); scoreCounter++;
            field[field.length - 1][i].setColor(Color.BLUE); field[field.length - 1][i].setPresent(1); scoreCounter++;
            field[i][field.length - 1].setColor(Color.BLUE); field[i][field.length - 1].setPresent(1); scoreCounter++;
        }
    }

    /**
     *           !!    OK   !!
     * 
     * 
     * In dieser Methode wird der blaue Punkt aufgrund einer Pfeiltaste
     * entsprechend bewegt. Das die übergeben x und y Koordinaten in
     * Pixel-Eben sind, werden diese jeweils durch 10 dividiert, damit
     * der Zugriff auf das Spielfeld-Array gegeben ist.
     *  
     * @param x     Bewegung in x-Richtung in Pixel-Ebene
     * @param y     Bewegung in y-Richtung in Pixel-Ebene
     */
    public void move(int x, int y) {
        // x und y durch 10 dividieren
        x /= Settings.ONE_FRAME;
        y /= Settings.ONE_FRAME;
        
        // nun wird überprüft, ob sich der blaue Punkt im
        // gültigen Breich des Spielfeldes befindet   -> für x-Koordinaten
        // wenn nein, dann wird er nicht bewegt
        if (blue.getX() + x < 0 || blue.getX() + x > 19) {
            return;
        }
        // nun wird überprüft, ob sich der blaue Punkt im
        // gültigen Breich des Spielfeldes befindet   -> für y-Koordinaten
        // wenn nein, dann wird er nicht bewegt
        if (blue.getY() + y < 0 || blue.getY() + y > 19) {
            return;
        }
        // wenn der blaue Kreis sich in ein bereits markiertes Feld bewegt, dann wird dies
        // verhindert;
        if (field[blue.getX() + x][blue.getY() + y].getPresent() <= 1) {
            // wenn das aktelle Feld, wo sich der blaue Kreis befindent gleich weiß ist, dann wird dieses
            // Feld blau gefärbt, damit hinterlässt der blaue Kreis eine blaue Linie
            if (field[blue.getX()][blue.getY()].getPresent() == 0 && field[blue.getX()][blue.getY()].getColor() != Color.BLUE) {
                // hier wird das Feld vom blauen Punkt blau gefärbt; dies ist dann die blaue Linie,
                // die der blaue Punkt hinter sich her zieht
                field[blue.getX()][blue.getY()].setColor(Color.BLUE);
            }
            // blauer Punkt wird bewegt
            blue.move(x, y);
            // hier wird überprüft, ob der blaue Punkt eine Fläche eingeschlossen hat
            // wenn ja dann wird diese entsprechend eingefärbt
            moveControll(blue.getX() - x, blue.getY() - y, blue.getX(), blue.getY());
        }
    }
    
    /**
     * 
     * 
     * @param x
     * @param y
     * @param in
     * @param p
     * @param get
     * @param fill 
     */
    private void fillAreaRecusivly(int x, int y, int in, int p, Color get, Color fill) {
        if (field[x][y].getPresent() == in && field[x][y].getColor() == get) {
            field[x][y].setColor(fill);
            field[x][y].setPresent(p);
            scoreCounter++;
            fillAreaRecusivly(x + 1, y, in, p, get, fill);
            fillAreaRecusivly(x - 1, y, in, p, get, fill);
            fillAreaRecusivly(x, y + 1, in, p, get, fill);
            fillAreaRecusivly(x, y - 1, in, p, get, fill);
        }
    }
    
    /**
     * 
     * 
     * @param x
     * @param y
     * @param in
     * @param p
     * @param fill 
     */
    private void fillAreaRecusivly(int x, int y, int in, int p, Color fill) {
        if (field[x][y].getPresent() == in) {
            field[x][y].setColor(fill);
            field[x][y].setPresent(p);
            scoreCounter--;
            fillAreaRecusivly(x + 1, y, in, p, fill);
            fillAreaRecusivly(x - 1, y, in, p, fill);
            fillAreaRecusivly(x, y + 1, in, p, fill);
            fillAreaRecusivly(x, y - 1, in, p, fill);
        }
    }

    /**
     *          !!    OK   !!
     * 
     * 
     * Diese Methode überprüft, ob der blaue Kreis von einem Rand zum anderen Rand
     * gefahren ist und dabei nicht vom roten Punkt erwischt wurde. Während der
     * Bewegung wird in einer Liste die gezogene blaue Linie gespeichert.
     * 
     * @param xOld      x-Koordinate bervor sich der blaue Kreis bewegt hat
     * @param yOld      y-Koordinate bervor sich der blaue Kreis bewegt hat
     * @param xNew      x-Koordinate nachdem sich der blaue Kreis bewegt hat
     * @param yNew      y-Koordinate nachdem sich der blaue Kreis bewegt hat
     */
    private void moveControll(int xOld, int yOld, int xNew, int yNew) {

        // blauer Kreis befindet sich am Rand, in diesem Fall nichts unternehmen
        if (field[xOld][yOld].getColor() == Color.BLUE && field[xOld][yOld].getPresent() == 1 && !moving) {
        
        }
        // blauer Kreis fahrt auf weißem Feld, all diese Felder in die Liste hinzufügen
        else if (field[xNew][yNew].getColor() == Color.WHITE && field[xNew][yNew].getPresent() == 0 ) {
            moving = true;
            area.add(field[xOld][yOld]);
        }
        // blauer Kreis befinden sich auf aktuell auf weißem Feld und geht in ein Randfeld über
        else if (field[xOld][yOld].getPresent() == 0 && field[xNew][yNew].getPresent() == 1 && moving) {
            // beide Punkte in die Liste aufnehmen
            area.add(field[xOld][yOld]);
            area.add(field[xNew][yNew]);
            // Kontrollvariable wieder auf false setzen, da der blaue Kreis sich in einem
            // sicheren Bereich bewegt
            moving = false;
            // vom blauen kreis umschlossene Fläche füllen
            fill();
            // Liste mit der gezogenen blauen Line vom blauen Kreis wieder leeren
            area.clear();
        }
    }
    
    /**
     * 
     * 
     * @param s
     * @param member 
     */
    private void fillTopLeft(Rectangle s, int member) {
        if (s.getX() - 1 >= 1  && field[s.getX() - 1][s.getY()].getPresent() == 0) {  
            fillAreaRecusivly(s.getX() - 1, s.getY(), 0, member, Color.WHITE, getRandomColor());
            if (field[red.getX()][red.getY()].getPresent() == member) {
                fillAreaRecusivly(s.getX() - 1, s.getY(), member, 0, Color.WHITE);
                
                fillBottomLeft(s, member);
            }
        }
        else {
            fillBottomLeft(s, member);
        }
    }
    
    /**
     * 
     * 
     * @param s
     * @param member 
     */
    private void fillBottomLeft(Rectangle s, int member) {
        if (s.getX() + 1 <= 18 && field[s.getX() + 1][s.getY()].getPresent() == 0) {
            fillAreaRecusivly(s.getX() + 1, s.getY(), 0, member, Color.WHITE, getRandomColor());
            if (field[red.getX()][red.getY()].getPresent() == member) {
                fillAreaRecusivly(s.getX() + 1, s.getY(), member, 0, Color.WHITE);
                
                fillTopRight(s, member);
            }
        }
        else {
            fillTopRight(s, member);
        }
    }
    
    /**
     * 
     * 
     * @param s
     * @param member 
     */
    private void fillTopRight(Rectangle s, int member) {
        if (s.getY() - 1 >= 1 && field[s.getX()][s.getY() - 1].getPresent() == 0) {
            fillAreaRecusivly(s.getX(), s.getY() - 1, 0, member, Color.WHITE, getRandomColor());
            if (field[red.getX()][red.getY()].getPresent() == member) {
                fillAreaRecusivly(s.getX(), s.getY() - 1, member, 0, Color.WHITE);
                
                fillBottomRight(s, member);
            }
        }
        else {
            fillBottomRight(s, member);
        }
    }
    
    /**
     * 
     * 
     * @param s
     * @param member 
     */
    private void fillBottomRight(Rectangle s, int member) {
        if (s.getY() + 1 <= 18 && field[s.getX()][s.getY() + 1].getPresent() == 0) {
            fillAreaRecusivly(s.getX(), s.getY() + 1, 0, member, Color.WHITE, getRandomColor());
            if (field[red.getX()][red.getY()].getPresent() == member) {
                fillAreaRecusivly(s.getX(), s.getY() + 1, member, 0, Color.WHITE);
            }
        }
    }
    
    /**
     * 
     */
    private void fill() {
        if (area.isEmpty() || area.size() < 2) return;
        setPresent(area);
        int memberInt = new Random().nextInt(20 - 10 + 1) + 10;
        fillTopLeft(area.get(0), memberInt);
    }
 
    /**
     * Diese Methode setzt die gezogene Linie vom blauen Kreis
     * auf present = 1. Damit ist dieses Feld sicher und geschlossen
     * und für den Feind unzugänglich.
     * 
     * @param line      Liste mit Rechtecken, die den Pfad darstellen
     */
    private void setPresent(ArrayList<Rectangle> line) {
        line.stream().forEach((Rectangle r) -> {
            r.setPresent(1);
            scoreCounter++;
        });
    }
    
    /**
     *             !!    OK   !!
     * 
     * 
     * Diese Methode bewegt den blauen Kreis in eine zufällige Richtung. Die
     * Anzahl an Bewegungen wird duch den Timer festgelegt, weil der Timer
     * genau diese Methode aufruft.
     * 
     * @param blueMoveSpeed             Geschwindigkeit vom blauen Kreis
     */
    public void moveBlueTimer() {
        // hier werden Zufallszahlen für die x und y Koordinaten erzeugt
        // un je nach Kombination fährt der blaue Kreis in eine 
        // bestimmte Richtung
        int x = (int) (Math.random() * 3);
        int y = (int) (Math.random() * 3);
        
        // nach unten bewegen
        if (x == 0 && y == 1) {    
            move(0, Settings.BLUE_MOVESPEED);
        }
        // nach oben bewegen
        if (x == 0 && y == 2) {
            move(0, -Settings.BLUE_MOVESPEED);
        }
        // nach links bewegen
        if (x == 1 && y == 0) {
            move(Settings.BLUE_MOVESPEED, 0);
        }
        // nach rechts bewegen
        if (x == 2 && y == 0) {
            move(-Settings.BLUE_MOVESPEED, 0);
        }
    }
    
    /**
     *      !!!    FAST  OK    !!!   hat noch kleine Bugs
     * 
     * 
     * Diese Methode bewegt den roten Kreis (Gegner) in eine zufällige Richtung.
     * Die Anzahl an Bewegungen wird duch den Timer festgelegt, weil der Timer
     * genau diese Methode aufruft. Unterschiedlich zum blauen Punkt. kann sich
     * der rote Punkt auch diagonal bewegen.
     * 
     * @param redMoveSpeeed         Geschwindigkeit vom roten Kreis
     */
    public void moveRedTimer() {
        // hier werden Zufallszahlen für die x und y Koordinaten erzeugt
        // un je nach Kombination fährt der rote Kreis in eine 
        // bestimmte Richtung
        int x = (int) (Math.random() * 3);
        int y = (int) (Math.random() * 3);

        // nach rechts bewegen
        if (x == 0 && y == 1) {
            
        }
        // nach links bewegen
        else if (x == 0 && y == 2) {
            
        }
        // nach unten bewegen
        else if (x == 1 && y == 1) {
            
        }
        // nach oben bewegen
        else if (x == 1 && y == 2) {
            
        }
        // nach links oben bewegen
        else if (x == 2 && y == 0) {
            
        }
        // nach rechts oben bewegen 
        else if (x == 2 && y == 1) {
            
        }
        // nach rechts unten bewegen
        else if (x == 2 && y == 2) {
            
        }
        // nach links unten bewegen
        else if (x == 1 && y == 0) {
            
        }
    }

    /**
     *          !!!   OK    !!!
     * 
     * 
     * Diese Methode liefert eine Zufallsfarbe aus den auswählbaren Farben
     * für die Füllung der markierten Felder.
     * 
     * @return          eine Zufallsfarbe wird zurückgegeben
     */
    public Color getRandomColor() {
        // Zufallsfarbe auswählen und zurückgeben
        return colors[(int)(Math.random() * 5)];
    }
    
    /**
     *          !!    OK   !!
     * 
     * Diese Methode errechnet sich, ob der Spieler bereits 80% oder mehr des
     * Spielfeldes erobert hat. Wenn ja, dann hat er gewonnen.
     * 
     * @return      true wenn der Spieler gewonnen hat; ansonsten false
     */
    public boolean checkWin() {

        return scoreCounter >= 320;
    }
    
    /**
     *                !!    OK   !!
     * 
     * Diese Methode überprüft auf Kollissionen zwischen dem roten und den blauen
     * Kreis bzw. dessen unvollkommene Linie. Wird ein Kontakt ermittelt, dann
     * liefert die Methode true zurück und der Spieler hat verloren, ansonsten
     * false und der Spieler hat ein weiteres Feld erfolgreich eingenommen
     * 
     * @param redMoveSpeed      Geschwindigkeit vom roten Kreis
     * @return                  true, wenn Kontakt ermittelt, ansonsten false
     */
    public boolean checkCollision() {
        // Zentren der beiden Kreise auf Gleichheit prüfen
        // wenn sie gleich sind, dann besteht ein Kontakt und der Spieler hat verloren
        if (blue.getX() == red.getX() && blue.getY() == red.getY()) {
            return true;
        }
        // roten Punkt auf Kontakt mit der gezogenen blauen Linie überprüfen
        if (field[red.getX()][red.getY()].getPresent() == 0 && field[red.getX()][red.getY()].getColor() == Color.BLUE) {
            return true;
        }
        // kein Kontakt der beiden Kreise bzw. mit der blauen Linie vom blauen Kreis
        // daher wird false zurückgegeben
        return false;
    }
    
    public Circle getBlue() { return blue; }
    
    public Circle getRed() {  return red; }
}
