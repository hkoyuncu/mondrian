
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * In dieser Klasse wird die GUI aufbereitet und alle Initialisierungen
 * werden durchgeführt. Sobald der Spieler das Spiel gewonnen hat, wird
 * eine Siegermeldung ausgegeben bzw. wenn er verloren hat, eine entsprechende
 * Meldung.
 * 
 * @author Koyuncu Harun, Valentin Zambelli
 * @version 2015.06.27
 */
public class Board extends JPanel {
    
    // Objekt der Klasse Gamefield definieren
    // diese Klasse verwaltet die gesamte Logik zum Spiel
    private final Gamefield feld;
    
    // Breite und Höhe vom 
    private final int GAME_WIDTH = 200;
    private final int GAME_HEIGHT = 200;
    
    // Bewegungsgeschwindigkeit vom blauen Punkt festlegen
    private final int BLUEMOVESPEED = 10;
    // die Bewegungsgeschwindigkeit vom roten Punkt ist das Doppelte vom Blauen
    private final int REDMOVESPEED = 2 * BLUEMOVESPEED;
   
    // Timer Objekt definieren, um den roten und blauen Punkt 
    // in bestimmten Zeitintervallen zu bewegen
    private final Timer timer;
    
    // diese Kontrollvariable dient dazu, um festzustellen, ob der Spieler
    // das Spiel gewonnen bzw. verloren hat
    private boolean inGame;

    /**
     * Default-Konstruktor wird überschrieben
     */
    public Board() {
        // Konstruktor der super-Klasse aufrufen
        super();
        // zu Begin ist er im Spiel und macht seine Züge
        inGame = true;
        // Objekt der Klasse Gamefield für die Logik des Spieles erzeugen 
        feld = new Gamefield();
        // blauen Punkt erzeugen und links oben ins Spielfeld setzen
        feld.addPlayer(new Circle(0, 0, 10, Color.ORANGE));
        // roten (orangenen) Punkt erzeugen, der den Feind darstellt und mittig
        // im Spielfeld setzen
        feld.addPlayer(new Circle(GAME_WIDTH/2 - 10, GAME_HEIGHT/2 - 10 , 10, Color.RED));
        // damit sich der blaue Ball auf Tastendruck bewegen kann, wird 
        // ein KeyListener auf das Spiel gesetzt
        addKeyListener(new KeyHandler());
        // neues Timer-Objekt erzeugen, der alle 500ms den roten und blauen 
        // Ball im Spielfeld bewegt.
        timer = new Timer(200, new ActionHandler());
        timer.start();
        // Fokus wird auf das Fenster gelegt, damit die Steuerung möglich ist
        setFocusable(true);
        // Größe vom Fenster festlegen
        setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        
    }
    
    /**
     * Diese Methode zeichnet alle Objekte des Spielfeldes neu bzw.
     * aktualisiert die bestehenden Objkete im Spielfeld.
     * 
     * @param g         Graphics-Objekt, mit dem die Objekte gezeichnet werden
     */
    @Override
    public void paintComponent(Graphics g) {
        // Methode der super-Klasse aufrufen
        super.paintComponent(g);
        // alle Rechteck-Objekte und Kreise werden gezeichnet
        feld.draw(g);
        // durch den Aufruf der Methode repaint, wird das gesamte
        // Feld neu gezeichnet
        repaint();
    }
    
    /**
     * In dieser inneren Klasse wird ein KeyListener implementiert.
     * Mit ihm wird das Bewegen des blaunen Punktes auf Tastendruck
     * realisiert.
     */
    private class KeyHandler implements KeyListener {
        
        /**
         * In dieser Methode wird je nach Tastendruck der blaue Ball
         * bewegt. Außerdem wird nach jedem Tasendruck überprüft,
         * ob der Spieler gewonnen bzw. verloren hat.
         * 
         * @param e         KeyEvent, der die gedrückte Taste beinhaltet
         */
        @Override
        public void keyPressed(KeyEvent e) {
            // solange das Game noch läuft...
            if (inGame) {
                // es wird nach den Pfeiltsten geswitcht
                switch (e.getKeyCode()) {
                    // wenn die linke-Pfeiltaste gedrückt wurde...
                    case KeyEvent.VK_LEFT:
                        // blauer Punkt fährt im Spiel nach links 
                        feld.move(-BLUEMOVESPEED, 0);
                        break;
                    // wenn die rechte-Pfeiltaste gedrückt wurde...
                    case KeyEvent.VK_RIGHT:
                        // blauer Punkt fährt im Spiel nach rechts 
                        feld.move(BLUEMOVESPEED, 0);
                        break;
                    // wenn die obere-Pfeiltaste gedrückt wurde...
                    case KeyEvent.VK_UP:
                        // blauer Punkt fährt im Spiel nach oben 
                        feld.move(0, -BLUEMOVESPEED);
                        break;
                    // wenn die untere-Pfeiltaste gedrückt wurde...
                    case KeyEvent.VK_DOWN:
                        // blauer Punkt fährt im Spiel nach unten 
                        feld.move(0, BLUEMOVESPEED);
                        break;
                }
                // nun wird überprüft, ob der Spieler mehr als 80% des Spielfeldes
                // erobert hat...
                if (feld.checkWin()) {
                    // wenn ja, dann wird der Timer gestoppt und eine Siegermeldung
                    // wird ausgegeben
                    timer.stop();
                    JOptionPane.showMessageDialog(null, "Sie haben das Spiel gewonnen!", "Gratulation", JOptionPane.INFORMATION_MESSAGE);
                    // die Kontrollvariable inGame wird auf false gesetzt, damit keine weiteren 
                    // Aktionen mehr ausgeführt werden
                    inGame = false;
                }
                // wenn der rote Ball den blauen Ball bzw. seine unvollständige Linie berührt hat...
                if (feld.checkCollision(REDMOVESPEED)) {
                    // dann hat der Spieler verloren und eine entsprechende Meldung
                    // wird ausgegeben; Timer wird gestopt
                    timer.stop();
                    JOptionPane.showMessageDialog(null, "Sie haben das Spiel verloren!", "Verloren", JOptionPane.INFORMATION_MESSAGE);
                    // die Kontrollvariable inGame wird auf false gesetzt, damit keine weiteren 
                    // Aktionen mehr ausgeführt werden
                    inGame = false;
                }
            }
        }
    
        // unbenutzt
        @Override
        public void keyTyped(KeyEvent e) { }

        // unbenutzt
        @Override
        public void keyReleased(KeyEvent e) { }

    }
    
    /**
     * In dieser inneren Klasse wird ein ActionListener implementiert, damit
     * auch eine konkrete Aktion ausgeführt wird, wenn der Timer gestartet wurde
     * und die Hit-Time erreicht ist. 
     */
    private class ActionHandler implements ActionListener {

        /**
         * In dieser Methode wird der rote und blaue Kreis in eine zufällige
         * Richtung bewegt
         * 
         * @param e     ActionEvent, der vom Timer ausgelöst wird
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            // blauen Punkt bewegen
            //feld.moveBlueTimer(BLUEMOVESPEED);
            // roten Punkt bewegen
            feld.moveRedTimer(REDMOVESPEED);
        }
    }
}
