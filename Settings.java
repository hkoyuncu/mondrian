
/**
 * Diese Klasse beinhaltet satische Konfiguratonswerte,
 * die in mehereren Klassen verwendet weden.
 * 
 * @author Koyuncu Harun, Valentin Zambelli
 * @version 2015.06.28
 */
public final class Settings {
    
    // Bewegungsgeschwindigkeit vom blauen Kreis festlegen
    final static int BLUE_MOVESPEED = 10;
    
    // Bewegungsgeschwindigkeit vom roten Kreis festlegen
    final static int RED_MOVESPEED = 20;
    
    // Bewegungsgeschwindigkeit vom roten Kreis in
    // Millisekunden festlegen, dies Zeit wird dann dem
    // swing-Timer uebergeben
    final static int RED_MOVESPEED_TIMER = 200;
    
    // Anzahl der Felder, die der Spieler erreichen muss,
    // um das zu gewinnen
    final static int WIN_SCORE = 320;
    
    // Breite und Hoehe vom Fenster festlegen
    final static int GAME_WIDTH = 400;
    final static int GAME_HEIGHT = 400;
    
    // Breite und Hoehe von einem Feld auf der
    // Spielerflaeche festlegen
    final static int ONE_FRAME = 10;
    
    // Anzahl der Spalten und Zeilen, in die das gesamte
    // Spielfeld unterteilt wird, hier also i ein 20x20 Feld
    final static int GAME_SIZE = 20;
    
    // Hier wird die Groesse vom blauen Kreis festgelegt
    final static int BLUE_SIZE = 10;
    
    // Hier wird die Groesse vom roten Kreis(Feind) festgelegt
    final static int RED_SIZE = 10;
    
}
