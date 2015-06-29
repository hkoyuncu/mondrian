
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Diese Klasse uebernimmt die gesamte Spiellogik. Sie stellt Methoden
 * dar wie zum Beispiel die Kollissionserkennung oder das Fuellen der
 * entsprechenden Flaeche, die vom blauen Punkt gezogen wurde usw.
 * 
 * @author Koyuncu Harun, Valentin Zambelli
 * @version 2015.06.28
 */
public class Gamefield {
    
    // in dieser Variable wird der aktuelle Punktestand vom
    // Spieler gespeichert, sobald dieser Wert 80 % (320) oder mehr
    // wird, hat der Spieler das Spiel gewonnen
    private int scoreCounter;
    // blauer Kreis-Objekt und roter Kreis-Objekt
    private Circle blue, red;
    // in diesem 2-dimensionalen Array aus Rectangles ist das Spielfeld aufgebaut
    private final Rectangle[][] field;
    // in diesem Array sind alle zulaessigen Farben fuer das Fuellen der Flaechen,
    // die vom bleuen Kreis gezogen wurde, enthalten
    private final Color[] colors = { Color.RED, Color.YELLOW, Color.GREEN, Color.BLACK, Color.LIGHT_GRAY };
    // die Kontrollvariable dient beim starten des Spieles fuer die einmalige
    // Faerbung des Spielrandes
    private boolean first;
    // In dieser Kontrollvariable wird gespeichert, wenn sich der blaue Kreis
    // in einem offfenen Feld(Farbe = weiss) befindet und sich zum naechsten Rand bewegt
    private boolean moving;
    // in dieser Liste werden alle Punkte gespeichert, die der blaue Kreis
    // hintersich her zieht, bis es wieder am Rand ankommt 
    private final List<Rectangle> area; 
    // diese Variable speichert den Spielstand, wenn
    // der Spieler gewonnen hat oder verloren hat
    private boolean status;
    
    /**
     * Default-Konstruktor wird ueberschrieben
     */
    public Gamefield() {
        // das Spielfeld wird in ein 20*20 Array aufgeteilt
        field = new Rectangle[Settings.GAME_SIZE][Settings.GAME_SIZE];
        // nachdem der Rand blau gefaerbt wurde, erst dann wird die 
        // Kontrollvariable auf true gesetzt; zu Beginn ist sie auf false
        first = false;
        // ArrayList erzeugen
        area = new CopyOnWriteArrayList<Rectangle>();
        // der blaue Punkt bewegt sich zu Beginn nicht auf weissem Feld,
        // daher wird die Variable auf false gesetzt
        moving = false;
        // diese Variable speicher den aktuellen Punktestand vom Spieler,
        // der zu Beginn gleich 0 ist
        scoreCounter = 0;
    }
    
    /**
     * OK
     * 
     * Diese Methode iterriert duch das gesamte Array und fuellt die Objekte
     * je nach ihrem Zustand in der entsorechenden Farbe
     * 
     * @param g     mit diesm Graphics-Objekt wird das Objekt grfaerbt
     */
    public void draw(Graphics g) {
        // wenn der Rand noch nicht gefaerbt wurde, dann wird dieser
        // blau gefaerbt, ansonsten wird nur noch das innere des
        // Spielfeldes neugezeichnet/neugefaerbt
        if (!first) {
            // Speilfeld erzeugen und Rand blau faerben
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
     * OK
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
     * OK
     * 
     * In dieser Methode wird das Spieldelf erzeugt und das Array
     * mit Rectangle-Objekten gefuellt. Die Farbe wird auf weiss gesetzt und
     * das present Bit auf 0
     */
    private void init() { 
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j <field[i].length; j++) {            
                field[i][j] = new Rectangle(i*Settings.ONE_FRAME, j*Settings.ONE_FRAME, Settings.ONE_FRAME, Settings.ONE_FRAME, Color.WHITE);
                field[i][j].setPresent(0);
            }
        }
    }
    
    /**
     * OK
     * 
     * Diese Methode zeichnet alle Objekte des Spielfeldes neu bzw.
     * aktualisiet sie mit Hilfe des Graphics-Objektes.
     * 
     * @param g         Objekte des Spielfeldes werden gezeichnet
     */
    private void update(Graphics g) {
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
     * OK
     * 
     * In dieser Methode wird der Rand des Spielfeldes blau gefaerbt
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
     * OK
     * 
     * In dieser Methode wird der blaue Punkt aufgrund einer Pfeiltaste
     * entsprechend bewegt. Das die uebergeben x und y Koordinaten in
     * Pixel-Eben sind, werden diese jeweils durch 10 dividiert, damit
     * der Zugriff auf das Spielfeld-Array gegeben ist.
     *  
     * @param x     Bewegung in x-Richtung in Pixel-Ebene
     * @param y     Bewegung in y-Richtung in Pixel-Ebene
     */
    public void moveBlue(int x, int y) {
        // x und y durch 10 dividieren
        x /= Settings.ONE_FRAME;
        y /= Settings.ONE_FRAME;
        
        // nun wird ueberprueft, ob sich der blaue Punkt im
        // gueltigen Breich des Spielfeldes befindet   -> fuer x-Koordinaten
        // wenn nein, dann wird er nicht bewegt
        if (blue.getX() + x < 0 || blue.getX() + x > 19) {
            return;
        }
        // nun wird ueberprueft, ob sich der blaue Punkt im
        // gueltigen Breich des Spielfeldes befindet   -> fuer y-Koordinaten
        // wenn nein, dann wird er nicht bewegt
        if (blue.getY() + y < 0 || blue.getY() + y > 19) {
            return;
        }
        // wenn der blaue Kreis sich in ein bereits markiertes Feld bewegt, dann wird dies
        // verhindert;
        if (field[blue.getX() + x][blue.getY() + y].getPresent() <= 1) {
            // wenn das aktelle Feld, wo sich der blaue Kreis befindent gleich weiss ist, dann wird dieses
            // Feld blau gefaerbt, damit hinterlaesst der blaue Kreis eine blaue Linie
            if (field[blue.getX()][blue.getY()].getPresent() == 0 && field[blue.getX()][blue.getY()].getColor() != Color.BLUE) {
                // in diesem kleinen Abschnitt wird festgestellt, dass der blauer Kreis
                // nicht entlang der gezogenen Linie wieder zurueck faehrt
                if (!area.isEmpty()) {
                    // naechste Posotion vom blauen Kreis ermitteln
                    Rectangle t = field[blue.getX() + x][blue.getY() + y];
                    // wenn die naechste Posiition des blauen Kreises die letzte Position in der
                    // Pfad-Liste war, dann versucht derblaue Kreis wieder den Pfad zurueckzufahren
                    // was in diesem Spiel verbietet wird
                    Rectangle l = area.get(area.size() - 1);
                    // wenn er zurueckfaehrt, dann wird returnt und die Bewegung wird nicht ausgefuehrt
                    if (t.getX() == l.getX() && t.getY() == l.getY() && t.getPresent() == l.getPresent() && t.getColor() == l.getColor()) {
                        return;
                    }
                }
                // hier wird das Feld vom blauen Punkt blau gefaerbt; dies ist dann die blaue Linie,
                // die der blaue Punkt hinter sich her zieht
                field[blue.getX()][blue.getY()].setColor(Color.BLUE);
            }
            // blauer Punkt wird bewegt
            blue.move(x, y);
            // hier wird ueberprueft, ob der blaue Punkt eine Flaeche eingeschlossen hat
            // wenn ja dann wird diese entsprechend eingefaerbt
            moveControll(blue.getX() - x, blue.getY() - y, blue.getX(), blue.getY());
        }
    }
    
    /**
     * OK
     * 
     * Diese Methode bewegt den blauen Kreis in eine zufaellige Richtung. Die
     * Anzahl an Bewegungen wird duch den Timer festgelegt, weil der Timer
     * genau diese Methode aufruft.
     * 
     */
    public void moveBlueTimer() {
        // hier werden Zufallszahlen fuer die x und y Koordinaten erzeugt
        // un je nach Kombination faehrt der blaue Kreis in eine 
        // bestimmte Richtung
        int x = (int) (Math.random() * 3);
        int y = (int) (Math.random() * 3);
        
        // nach unten bewegen
        if (x == 0 && y == 1) {
            moveBlue(0, Settings.BLUE_MOVESPEED);
        }
        // nach oben bewegen
        if (x == 0 && y == 2) {
            moveBlue(0, -Settings.BLUE_MOVESPEED);
        }
        // nach links bewegen
        if (x == 1 && y == 0) {
            moveBlue(Settings.BLUE_MOVESPEED, 0);
        }
        // nach rechts bewegen
        if (x == 2 && y == 0) {
            moveBlue(-Settings.BLUE_MOVESPEED, 0);
        }
    }
    
    /**
     * OK
     * 
     * Diese Methode bewegt den roten Kreis (Gegner) in eine zufaellige Richtung.
     * Die Geschwindigkeit der Bewegungen wird duch den Timer festgelegt, weil der
     * Timer genau diese Methode aufruft. Unterschiedlich zum blauen Punkt, kann
     * sich der rote Punkt auch in allen diagonalen Richtungen bewegen. Der rote
     * Kreis bewegt sich doppelt so schnell wie der blaue Kreis.
     * 
     */
    public void moveRedTimer() {
        // hier werden Zufallszahlen fuer die x und y Koordinaten erzeugt
        // un je nach Kombination faehrt der rote Kreis in eine 
        // bestimmte Richtung
        int x = (int) (Math.random() * 3);
        int y = (int) (Math.random() * 3);
        
        // nachdem der Vorgang bei jeder Verzweigung in dieser Methode gleich
        // aufgebaut ist, werde ich nur einen Teil kommentieren
        
        // nach rechts bewegen
        if (x == 0 && y == 1 && !status) {
            // wenn scih der zu bewegende Bereich noch innerhalb des Spielfeldes befindet..
            if (red.getX() + 1 < 19 ) {
                // Rectangle-Objekt von der naechsten theoretischen
                // Position ermitteln
                Rectangle r1 = field[red.getX() + 1][red.getY()];
                // wenn dieses Rectangle-Object blau ist und den present bit 0
                // besitzt, dann wurde eine mauelle Kollision mit der
                // blauen Linie vom blauen Punkt ermittelt und die 
                // Kontrollvariable status wird auf true gesetzt. Somit
                // hat der Spieler verloren und es werden keine Bewegungen
                // mehr fuer den roten Kreis erlaubt
                if (r1.getColor() == Color.BLUE && r1.getPresent() == 0) {
                    status = true;
                }
                // wenn das naechste theoretische Feld weiss ist, dann wird versucht
                // sich auf dieses Feld zu bewegen
                else if (r1.getColor() != Color.BLUE && r1.getPresent() != 1) {
                    // roten Kreis in die entsprechende Richtung um 1 Feld bewegen
                    red.move(1, 0);
                    // exteren Methode zur Kollisionsueberpruefung
                    checkCollision();
                    // wieder werden die Raender des Spielfeldes geprueft,
                    // damit ist gewaehrleistet, dass sich der rote Punkt
                    // innerhaln des Spielfeldes bewegt
                    if (red.getX() + 1 > 0) {
                        // Rectangle-Objekt von der naechsten theoretischen
                        // Position ermitteln
                        Rectangle r2 = field[red.getX() + 1][red.getY()];
                        // wenn die Farbe blau ist und der Present-Bit nicht 
                        // auf 1 gesetzt wurde, dann kreuzt man aktuell den
                        // blauen Pfad vom blauen Kreis
                        if (r2.getColor() == Color.BLUE && r2.getPresent() != 1) {
                            // den roten Kreis auf die naechst ermittelte
                            // Position bewegen und danach auf Kollisionen
                            // pruefen
                            red.move(1, 0);
                            checkCollision();
                        }
                    }
                }
            }
        }
        // nach links bewegen
        if (x == 0 && y == 2 && !status) {
            if (red.getX() - 1 > 0 ) {
                Rectangle r1 = field[red.getX() - 1][red.getY()];
                if (r1.getColor() == Color.BLUE && r1.getPresent() == 0) {
                    status = true;
                }
                else if (r1.getColor() != Color.BLUE && r1.getPresent() != 1) {
                    red.move(-1, 0);
                    checkCollision();
                    if (red.getX() - 1 > 0) {
                        Rectangle r2 = field[red.getX() - 1][red.getY()];
                        if (r2.getColor() == Color.BLUE && r2.getPresent() != 1) {
                            red.move(-1, 0);
                            checkCollision();
                        }
                    }
                }
            }
        }
        // nach unten bewegen
        if (x == 1 && y == 1 && !status) {
           if (red.getY() + 1 < 19 ) {
                Rectangle r1 = field[red.getX()][red.getY()+1];
                if (r1.getColor() == Color.BLUE && r1.getPresent() == 0) {
                    status = true;
                }
                else if (r1.getColor() != Color.BLUE && r1.getPresent() != 1) {
                    red.move(0, 1);
                    checkCollision();
                    if (red.getY() + 1 < 19) {
                        Rectangle r2 = field[red.getX()][red.getY()+1];
                        if (r2.getColor() == Color.BLUE && r2.getPresent() != 1) {
                            red.move(0, 1);
                        }
                    }
                }
            }
        }
        // nach oben bewegen
        if (x == 1 && y == 2 && !status) {
            if (red.getY() - 1 > 0 ) {
                Rectangle r1 = field[red.getX()][red.getY()-1];
                if (r1.getColor() == Color.BLUE && r1.getPresent() == 0) {
                    status = true;
                }
                else if (r1.getColor() != Color.BLUE && r1.getPresent() != 1) {
                    red.move(0, -1);
                    checkCollision();
                    if (red.getY() - 1 > 0) {
                        Rectangle r2 = field[red.getX()][red.getY()-1];
                        if (r2.getColor() == Color.BLUE && r2.getPresent() != 1) {
                            red.move(0, -1);
                        }
                    }
                }
            }
        }
        // nach links oben bewegen
        if (x == 2 && y == 0 && !status) {
            if (red.getY() - 1 > 0 && red.getX() - 1 > 0) {
                Rectangle r1 = field[red.getX()-1][red.getY()-1];
                if (r1.getColor() == Color.BLUE && r1.getPresent() == 0) {
                    status = true;
                }
                else if (r1.getColor() != Color.BLUE && r1.getPresent() != 1) {
                    red.move(-1, -1);
                    checkCollision();
                    if (red.getY() - 1 > 0 && red.getX() - 1 > 0) {
                        Rectangle r2 = field[red.getX()-1][red.getY()-1];
                        if (r2.getColor() == Color.BLUE && r2.getPresent() != 1) {
                            red.move(-1, -1);
                        }
                    }
                }
            }
        }
        // nach rechts oben bewegen 
        if (x == 2 && y == 1 && !status) {
            if (red.getY() - 1 > 0 && red.getX() + 1 < 19) {
                Rectangle r1 = field[red.getX()+1][red.getY()-1];
                if (r1.getColor() == Color.BLUE && r1.getPresent() == 0) {
                    status = true;
                }
                else if (r1.getColor() != Color.BLUE && r1.getPresent() != 1) {
                    red.move(1, -1);
                    checkCollision();
                    if (red.getY() - 1 > 0 && red.getX() + 1 < 19) {
                        Rectangle r2 = field[red.getX()+1][red.getY()-1];
                        if (r2.getColor() == Color.BLUE && r2.getPresent() != 1) {
                            red.move(1, -1);
                        }
                    }
                }
            }
        }
        // nach rechts unten bewegen
        if (x == 2 && y == 2 && !status) {
            if (red.getY() + 1 < 19 && red.getX() + 1 < 19) {
                Rectangle r1 = field[red.getX()+1][red.getY()+1];
                if (r1.getColor() == Color.BLUE && r1.getPresent() == 0) {
                    status = true;
                }
                else if (r1.getColor() != Color.BLUE && r1.getPresent() != 1) {
                    red.move(1, 1);
                    checkCollision();
                    if (red.getY() + 1 < 19 && red.getX() + 1 < 19) {
                        Rectangle r2 = field[red.getX()+1][red.getY()+1];
                        if (r2.getColor() == Color.BLUE && r2.getPresent() != 1) {
                            red.move(1, 1);
                        }
                    }
                }
            }
        }
        // nach links unten bewegen
        if (x == 1 && y == 0 && !status) {
            if (red.getY() + 1 < 19 && red.getX() - 1 > 0) {
                Rectangle r1 = field[red.getX()-1][red.getY()+1];
                if (r1.getColor() == Color.BLUE && r1.getPresent() == 0) {
                    status = true;
                }
                else if (r1.getColor() != Color.BLUE && r1.getPresent() != 1) {
                    red.move(-1, 1);
                    checkCollision();
                    if (red.getY() + 1 < 19 && red.getX() - 1 > 0) {
                        Rectangle r2 = field[red.getX()-1][red.getY()+1];
                        if (r2.getColor() == Color.BLUE && r2.getPresent() != 1) {
                            red.move(-1, 1);
                        }
                    }
                }
            }
        }
    }

    /**
     * OK
     * 
     * Diese Methode ueberprueft, ob der blaue Kreis von einem Rand zum anderen Rand
     * gefahren ist und dabei nicht vom roten Punkt erwischt wurde. Waehrend der
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
        // blauer Kreis fahrt auf weissem Feld, all diese Felder in die Liste hinzufuegen
        else if (field[xNew][yNew].getColor() == Color.WHITE && field[xNew][yNew].getPresent() == 0 ) {
            moving = true;
            area.add(field[xOld][yOld]);
        }
        // blauer Kreis befinden sich auf aktuell auf weissem Feld und geht in ein Randfeld ueber
        else if (field[xOld][yOld].getPresent() == 0 && field[xNew][yNew].getPresent() == 1 && moving) {
            // beide Punkte in die Liste aufnehmen
            area.add(field[xOld][yOld]);
            area.add(field[xNew][yNew]);
            // Kontrollvariable wieder auf false setzen, da der blaue Kreis sich in einem
            // sicheren Bereich bewegt
            moving = false;
            // vom blauen kreis umschlossene Flaeche fuellen
            fill();
            // Liste mit der gezogenen blauen Line vom blauen Kreis wieder leeren
            area.clear();
        }
    }
    
    /**
     * OK
     * 
     * Diese rekursive Methode sezt alle Felder das vom blauen Kreis geschlossene
     * wurde auf ein entsprechendes present-Bit-Wert, sodass diese Felder ganz
     * leicht wieder identifiziert werden koennen und nach gegebener Farbe
     * ausgefuellt werden.
     * 
     * @param x         x-Koordiante von da aus die Methode rekursiv setzen soll
     * @param y         y-Koordiante von da aus die Methode rekursiv setzen soll
     * @param in        present-Bit der Felder, die gesetzt werden soll (ist immer 0)
     * @param p         present-Bit des aktuellen Feldes einmalig fuer die Faerbung
     *                  markieren und setzen
     * @param get       Farbe der Felder, die ersetzt werden sollen
     * @param fill      neue Fuellfarbe der gesetzten Felder
     */
    private void fillAreaRecusivly(int x, int y, int in, int p, Color get, Color fill) {
        // wenn das aktuelle Feld den present-Bit "in" hat und die Farbe des Feldes
        // gleich die Farbe "get" ist, dann wird dieses Feld fuer die Umfaerbung
        // markiert und es werden rekursiv alle Nachbarn des aktuellen Punktes 
        // betrachtet, bis alle Felder, die im geschlossen Bereich des blauen
        // Kreises, gesetzte sind.
        if (field[x][y].getPresent() == in && field[x][y].getColor() == get) {
            field[x][y].setColor(fill);
            field[x][y].setPresent(p);
            scoreCounter++;
            // rekursiv alle Nachbarn betrachten
            fillAreaRecusivly(x + 1, y, in, p, get, fill);
            fillAreaRecusivly(x - 1, y, in, p, get, fill);
            fillAreaRecusivly(x, y + 1, in, p, get, fill);
            fillAreaRecusivly(x, y - 1, in, p, get, fill);
        }
    }
    
    /**
     * OK
     * 
     * Diese Methode setzt alle Aenderungen, die von der obigen Methode durchgefuehrt
     * wurden wieder auf den Ursprung. Auch hier erfolgt das Resetten rekursiv, indem
     * alle Nachbarn des aktuellen Punktes betrachtet und entsprechend gesetzt
     * werden, bis alles so wie am Anfang war.
     * 
     * @param x         x-Koordiante von da aus die Methode die Felder resetten soll
     * @param y         y-Koordiante von da aus die Methode die Felder resetten soll
     * @param in        present-Bit der Felder, die bereits gesetzt worden sind und wieder
     *                  auf den Unrsprung p gesetzt werden soll
     * @param p         present Bit vom Ursprung
     * @param fill      Farbe der urspruenglichen Felder
     */
    private void resetFillAreaRecusivly(int x, int y, int in, int p, Color fill) {
        // wenn das aktuelle Feld den present Bit "in" hat und diese Methode
        // aufgeruifen wird, dann wird er wieder auf den Ursprung gesetzt, indem das
        // present Bit wieder auf "p" und die Farbe auf "fill" gesetzt werden
        if (field[x][y].getPresent() == in) {
            field[x][y].setColor(fill);
            field[x][y].setPresent(p);
            scoreCounter--;
            // rekursiv alle Nachbarn betrachten
            resetFillAreaRecusivly(x + 1, y, in, p, fill);
            resetFillAreaRecusivly(x - 1, y, in, p, fill);
            resetFillAreaRecusivly(x, y + 1, in, p, fill);
            resetFillAreaRecusivly(x, y - 1, in, p, fill);
        }
    }
    
    /**
     * OK
     * 
     * Diese Methode sucht links oben von der gezogenen blauen Linie
     * ein weisses Feld und sobald dieser gefunden wurde, wird dieses
     * geschlossene Feld rekursiv gefuellt. Wenn links oben kein weisses
     * Feld gefunden wurde, dann wird die Methode "fillBottomLeft" aufgerufen
     * und es wird versucht im unteren Bereich des Feldes ein Start zu finden.
     * 
     * @param s             Rectangle-Objekt vom Startpunkt der blauen Linie 
     * @param member        present-Bit Wert, welches auf die Felder gesetzt wird
     */
    private void fillTopLeft(Rectangle s, int member) {
        // wenn links oben ein weisses Feld gefunden wurde...
        if (s.getX() - 1 >= 1  && field[s.getX() - 1][s.getY()].getPresent() == 0) {
            // dann wird das geschlossene Feld gefaerbt...
            fillAreaRecusivly(s.getX() - 1, s.getY(), 0, member, Color.WHITE, getRandomColor());
            // wenn sich nun der rote Ball in diesem gefaerbten Bereich befindet, dann
            // haben wird die flasche Seite erwischt und muessen die Aenderung Rueckgaengig machen
            if (field[red.getX()][red.getY()].getPresent() == member) {
                resetFillAreaRecusivly(s.getX() - 1, s.getY(), member, 0, Color.WHITE);
                
                fillBottomLeft(s, member);
            }
        }
        else {
            fillBottomLeft(s, member);
        }
    }
    
    /**
     * OK
     * 
     * Diese Methode sucht links unterhalb im Spielfeld nach ein weisses Feld
     * und sobald dieser gefunden wurde, wird dieses geschlossene Feld rekursiv
     * gefuellt. Wenn links unten kein weisses Feld gefunden wurde, dann wird die
     * Methode "fillTopRight" aufgerufen und es wird versucht im oberen rechten
     * Bereich des Feldes ein Start zu finden.
     * 
     * @param s             Rectangle-Objekt vom Startpunkt der blauen Linie 
     * @param member        present-Bit Wert, welches auf die Felder gesetzt wird
     */
    private void fillBottomLeft(Rectangle s, int member) {
        // wenn links unten ein weisses Feld gefunden wurde...
        if (s.getX() + 1 <= 18 && field[s.getX() + 1][s.getY()].getPresent() == 0) {
            // dann wird das geschlossene Feld gefaerbt...
            fillAreaRecusivly(s.getX() + 1, s.getY(), 0, member, Color.WHITE, getRandomColor());
            // wenn sich nun der rote Ball in diesem gefaerbten Bereich befindet, dann
            // haben wird die flasche Seite erwischt und muessen die Aenderung Rueckgaengig machen
            if (field[red.getX()][red.getY()].getPresent() == member) {
                resetFillAreaRecusivly(s.getX() + 1, s.getY(), member, 0, Color.WHITE);
                
                fillTopRight(s, member);
            }
        }
        else {
            fillTopRight(s, member);
        }
    }
    
    /**
     * OK
     * 
     * Diese Methode sucht rechts oben im Spielfeld nach ein weisses Feld
     * und sobald dieser gefunden wurde, wird dieses geschlossene Feld rekursiv
     * gefuellt. Wenn rechts oben kein weisses Feld gefunden wurde, dann wird die
     * Methode "fillBottomRight" aufgerufen und es wird versucht im unteren
     * rechten Bereich des Feldes ein Start zu finden.
     * 
     * @param s             Rectangle-Objekt vom Startpunkt der blauen Linie 
     * @param member        present-Bit Wert, welches auf die Felder gesetzt wird
     */
    private void fillTopRight(Rectangle s, int member) {
        // wenn rechts oben ein weisses Feld gefunden wurde...
        if (s.getY() - 1 >= 1 && field[s.getX()][s.getY() - 1].getPresent() == 0) {
            // dann wird das geschlossene Feld gefaerbt...
            fillAreaRecusivly(s.getX(), s.getY() - 1, 0, member, Color.WHITE, getRandomColor());
            // wenn sich nun der rote Ball in diesem gefaerbten Bereich befindet, dann
            // haben wird die flasche Seite erwischt und muessen die Aenderung Rueckgaengig machen
            if (field[red.getX()][red.getY()].getPresent() == member) {
                resetFillAreaRecusivly(s.getX(), s.getY() - 1, member, 0, Color.WHITE);
                
                fillBottomRight(s, member);
            }
        }
        else {
            fillBottomRight(s, member);
        }
    }
    
    /**
     * OK
     * 
     * Diese Methode sucht rechts unterhalb im Spielfeld nach ein weisses Feld
     * und sobald dieser gefunden wurde, wird dieses geschlossene Feld rekursiv
     * gefuellt.
     * 
     * @param s             Rectangle-Objekt vom Startpunkt der blauen Linie 
     * @param member        present-Bit Wert, welches auf die Felder gesetzt wird
     */
    private void fillBottomRight(Rectangle s, int member) {
        // wenn rechts unten ein weisses Feld gefunden wurde...
        if (s.getY() + 1 <= 18 && field[s.getX()][s.getY() + 1].getPresent() == 0) {
            // dann wird das geschlossene Feld gefaerbt...
            fillAreaRecusivly(s.getX(), s.getY() + 1, 0, member, Color.WHITE, getRandomColor());
            // wenn sich nun der rote Ball in diesem gefaerbten Bereich befindet, dann
            // haben wird die flasche Seite erwischt und muessen die Aenderung Rueckgaengig machen
            if (field[red.getX()][red.getY()].getPresent() == member) {
                resetFillAreaRecusivly(s.getX(), s.getY() + 1, member, 0, Color.WHITE);
            }
        }
    }
    
    /**
     * OK
     * 
     * Diese Methode setzt die gezogene Linie vom blauen Kreis
     * auf present = 1. Damit ist dieses Feld sicher und geschlossen
     * und fuer den Feind unzugaenglich.
     * 
     * @param line      Liste mit Rechtecken, die den Pfad darstellen
     */
    private void setPresent(List<Rectangle> line) {
        line.stream().forEach((Rectangle r) -> {
            r.setPresent(1);
            scoreCounter++;
        });
    }
    
    
    /**
     * OK
     * 
     * In dieser Methode werden alle noetigen Methode zum tatsaechlichen umfaerben des
     * Spielfeldes aufgerufen. Deweiteren werden Kreuzungen der blauen Line
     * eliminiert, sodass wieder nur ein Feld zum umfaerben existiert.
     */
    private void fill() {
        if (area.isEmpty()) return;
        // diese Variable speichert die Abbruchbedingung fuer das Eliminieren
        // der ueberfluessigen Pfade
        boolean delete_path = true;
        // solange es ueberfluessige Pfade gibt, werden diese entfernt
        while(delete_path) {
            // hier wird die Liste mit den ueberfluessigen Punkten ermittelt
            List<Rectangle> tmp = eliminateLine();
            // solange diese Liste nicht leer ist(d.h. es gibt ueberfluessige Pfade)
            // weden diese Pfade entfernt
            if (tmp.isEmpty()) delete_path = false;
            // beim Entfernen dieser Pfade wird die EIgenschaft des Feldes
            // auf den Ursprungszustand zurueckgesetzt
            for (Rectangle r : tmp) {
                r.setColor(Color.WHITE);
                r.setPresent(0);
            }
            // nun werden die Pfade aus der Pfad-Liste vom blauen Punkt entfernt
            // damit die Flaeche als ein geschlossenes Feld gefuellt werden kann
            area.removeAll(tmp);
        }
        // blaue Linie vom blauen Kreis auf vollkommen setzten (present Bit = 1)
        setPresent(area);
        // Zufallszahl zwischen 2 und 9 fuer das present-Bit erzeugen
        int memberInt = new Random().nextInt(9 - 2 + 1) + 2;
        // hier wird das eingeschlossene Feld gefaerbt
        fillTopLeft(area.get(0), memberInt);
    }
    
    /**
     * OK
     * 
     * Diese Methode findet und eliminiert Kreuzugen vom blauen Kreis.
     * Eine Kreuzung entsteht dann, wenn der blaue Punkt eine bereits
     * gezogene blaue Linie nochmals ueberquert.
     * 
     * @return  eine Liste mit Rectngle-Objekten, die sozusagen die Koordinaten
     *          der ueberfluessingen Linie beinhaltet
     */
    private List<Rectangle> eliminateLine() {
        // in dieser Liste wird der ueberfluessige Pfad gespeichert
        List<Rectangle> diff = new ArrayList<Rectangle>();
        // in dieser Kontrollvariable wird gespeichert, ob ein
        // ueberfluessiger Pfad gefunden wurde
        boolean found = false;
        // nun wird duch den Pfad durch iterriert 
        for (int i = 0; i < area.size(); i++) {
            Rectangle x = area.get(i);
            // solange kein Pfad gefunden wurde, wird weiter gesucht
            if (!found) {
                // die Pfadliste wird von hinten durchiterriert
                for (int j = area.size()-1; j >= 0; j--) {
                    Rectangle y = area.get(j);
                    // wenn in der Pfad-Liste zwei unterschiedliche Rectangle-Objekte
                    // gefunden wurde, aber die Koordinaten gleich sind, dann besteht
                    // hier eine Kreuzung
                    if (x.getX() == y.getX() && x.getY() == y.getY() && i != j && !found) {
                        // in diese Liste wird nun der ueberfluessige Pfad gespeichert
                        diff = area.subList(i, j);
                        // hier wird nochmals der letzte Punkt im Pfad errechnet
                        // un in die Liste aufgenommen
                        Rectangle f = diff.get(0);
                        Rectangle l = diff.get(diff.size()-1);
                        Rectangle in = field[(f.getX()+l.getX()) /2][(f.getY()+l.getY())/2];
                        diff.add(in);
                        // das erste Element wieder aus der Liste entfernen, da es
                        // die Eckpunkt darstellt und dieser soll nicht geloescht werden
                        diff.remove(0);
                        // Kontrollvariable auf true setzten, da ein ueberfluessiger Pfad
                        // gefunden wurde
                        found = true;
                    }
                }
            }
        }
        // Liste mit dem ueberfluessigen Pfad zurueckgeben
        return diff;
    }

    /**
     * OK
     * 
     * Diese Methode liefert eine Zufallsfarbe aus den auswaehlbaren Farben
     * fuer die Fuellung der markierten Felder.
     * 
     * @return          eine Zufallsfarbe wird zurueckgegeben
     */
    private Color getRandomColor() {
        // Zufallsfarbe auswaehlen und zurueckgeben
        return colors[(int)(Math.random() * 5)];
    }
    
    /**
     * OK
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
     * OK
     * 
     * Diese Methode ueberprueft auf Kollissionen zwischen dem roten und den blauen
     * Kreis bzw. dessen unvollkommene Linie. Wird ein Kontakt ermittelt, dann
     * liefert die Methode true zurueck und der Spieler hat verloren, ansonsten
     * false und der Spieler hat ein weiteres Feld erfolgreich eingenommen
     * 
     * @return                  true, wenn Kontakt ermittelt, ansonsten false
     */
    public boolean checkCollision() {
        // Zentren der beiden Kreise auf Gleichheit pruefen
        // wenn sie gleich sind, dann besteht ein Kontakt und der Spieler hat verloren
        if (blue.getX() == red.getX() && blue.getY() == red.getY()) {
            status = true;
            return true;
        }
        // roten Punkt auf Kontakt mit der gezogenen blauen Linie ueberpruefen
        if (field[red.getX()][red.getY()].getPresent() == 0 && field[red.getX()][red.getY()].getColor() == Color.BLUE) {
            status = true;
            return true;
        }
        // kein Kontakt der beiden Kreise bzw. mit der blauen Linie vom blauen Kreis
        // daher wird false zurueckgegeben
        status = false;
        return false;
    }
    
    public Circle getBlue() { return blue; }
    
    public Circle getRed() {  return red; }
    
    public boolean getStatus() { return  status; }
    
}