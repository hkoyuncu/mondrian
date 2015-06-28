import java.awt.*;
import javax.swing.*;

/**
 * Diese Klasse startet die Applikation (das Spiel)  und kann
 * über den Schließ-Button wieder geschlossen werden. Mit Hilfe
 * der Pfeiltasten lässt sich der blaue Kreis steuren.
 * 
 * @author PK-Team, Koyuncu Harun, Valentin Zambelli
 * @version 2015.06.27
 */
public class Mondrian extends JFrame {
    
    /**
     * Default-Konstruktor wird überschrieben
     */
    public Mondrian() {
        // das Board im Frame plazieren
        add(new Board());
        // die Fenstergröße lässt sich nicht mehr ändern
        setResizable(false);
        // Platziereung des Boards im Frame optimieren
        pack();
        // Titel vom Fenster setzen
        setTitle("Mondrian");
        // Aktion festlgene, die beim Drücken des Close-Buttons passieren soll
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     * Main-Methode zum starten der Applikation
     * 
     * @param args 
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            // neue Klasse erstellen
            Mondrian window = new Mondrian();
            // Fenster mittig vom Bildschirm plazieren
            window.setLocationRelativeTo(null);
            // Fenster sichtbar machen
            window.setVisible(true);
        });
    }
}
