import java.awt.*;
import javax.swing.*;

/**
 * Diese Klasse startet die Applikation (das Spiel)  und kann
 * ueber den Schliess-Button wieder geschlossen werden. Mit Hilfe
 * der Pfeiltasten laesst sich der blaue Kreis steuren.
 * 
 * @author PK-Team, Koyuncu Harun, Valentin Zambelli
 * @version 2015.06.27
 */
public class Mondrian extends JFrame {
    
    /**
     * Default-Konstruktor wird ueberschrieben
     */
    public Mondrian() {
        // das Board im Frame plazieren
        add(new Board());
        // die Fenstergroesse laesst sich nicht mehr aendern
        setResizable(false);
        // Platziereung des Boards im Frame optimieren
        pack();
        // Titel vom Fenster setzen
        setTitle("Mondrian");
        // Aktion festlgene, die beim Druecken des Close-Buttons passieren soll
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
