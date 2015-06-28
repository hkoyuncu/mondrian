# mondrian

Das Programm Mondrian ist ein Swingbasiertes Spiel für eine Person. Man spielt
einen kleinen blauen Ball, der sich waagrecht und senkrecht auf einem 200x200
großen Spielfeld bewegen kann.

Auf ausgefüllten Flächen kann sich der blaue Ball gefahrlos bewegen. Anfangs ist
nur der Rand des Spielfelds ausgefüllt. Um neue Flächen auszufüllen, muss der
blaue Ball eine ausgefüllte Fläche verlassen und zieht dann einen Strich hinter
sich her, bis wieder eine ausgefüllte Fläche erreicht wird. Während dieser
Bewegung ist der blaue Ball verwundbar: der Computergegner, ein roter Ball,
versucht den blauen Ball oder die von ihm gezogene Linie zu berühren. Gelingt die
Berührung, ist das Spiel verloren.

Erreicht der blaue Ball eine bereits ausgefüllte Fläche, ohne dass er oder seine
Linie berührt wurde, hat der nachgezogene Strich den noch unausgefüllten Bereich
in zwei Teile geteilt. Eine der beiden Flächen wird dann ausgefüllt.

Ziel des Spiels ist es, mehr als 80% der Spielfeldfläche auszufüllen.

Das Spiel wird aufgeteilt in eine (einfache) Grundaufgabe und mehrere
Bonusaufgaben. Insgesamt sind 100 Punkte erreichbar.



Grundaufgabe: (51 Punkte)  ======>  zum Teil erledigt (Flächen füllen fehlt)
--------------------------
Erweitern Sie JPanel um eine eigene paintComponent(Graphics g) Methode, mit der
Sie das Spiel zeichnen und zeigen Sie das Spielfeld in einem JFrame an.

Das Spielfeld hat eine fixe Größe von 200x200 Pixeln, das Fenster ist
entsprechend größer und zeigt den Text "Mondrian" im Titel. Wenn der Knopf zum
Schließen des Fensters gedrückt wird, beendet sich das Programm (bereits im
Codegerüst erfüllt).

Fügen Sie ihrem Spielfeld mit addKeyListener() einen KeyListener hinzu, der auf
keyPressed Events reagiert: mit den Pfeiltasten kann der blaue Ball waagrecht
und senkrecht bewegt werden. Weder die Begrenzung des Spielfelds noch
ausgefüllte bzw nicht ausgefüllte Flächen wirken sich auf die Bewegungsfreiheit
des blauen Balles aus. Der Ball hat einen Durchmesser von 3 Pixeln.

Bewegt sich der blaue Ball, zieht er einen Strich hinter sich her. Erreicht der
Strich eine bereits ausgefüllte Fläche, wird eine (beliebige) der beiden
Teilflächen gelb ausgefüllt.

Tipp: verwenden Sie für die logische Speicherung des Spielfelds eine eigene
Datenstruktur auf Basis eines Rasters und nicht dieselbe Datenstruktur wie für
die Darstellung des Spielfelds.

Bonusaufgabe: statischer Computergegner (5 Punkte)      ===> erledigt
---------------------------------------------------
In die Mitte des Spielfelds wird ein roter Ball gesetzt, der den Computergegner
darstellt (Durchmesser 3 Pixel). Liegt das Zentrum des roten Balls auf der
gleichen Stelle wie das Zentrum des blauen Balls oder auf einer Linie, die
gerade gezogen wird, ist das Spiel verloren. So lange sich der Computergegner
nicht bewegt, reicht es aus, auf Kollisionen zwischen blauem und rotem Ball zu
testen, da die gezeichnete Linie sicher erst nach dem blauen Ball berührt wird.

Bonusaufgabe: richtige Fläche ausfüllen (5 Punkte)      ====>  fehlt
---------------------------------------------------
Beim Ausfüllen einer neuen Fläche wird jene Teilfläche gewählt, in der sich der
Computergegner aktuell nicht befindet.
Sind mehr als 80% der Gesamtfläche ausgefüllt, ist das Spiel gewonnen und eine
entsprechende Nachricht wird angezeigt.

Bonusaufgabe: korrekte Bewegung (5 Punkte)              ===> Code vorhanden und funktionstüchtig: erledigt
-------------------------------------------
Nur auf nicht ausgefüllten Flächen zieht der blaue Ball eine Linie hinter sich
her. Der blaue Ball kann das Spielfeld nicht verlassen.

Bonusaufgabe: Schleifen in Linien eliminieren (15 Punkte)    ====>   fehlt
------------------------------------------------------------
Kreuzt der blaue Ball eine gerade gezeichnete Linie, so gibt es einen
Kreuzungspunkt von vier Linienteilen, die wir Einfahrt alt, Ausfahrt alt,
Einfahrt neu und Ausfahrt neu (in der Reihenfolge ihrer Entstehung) nennen.
Zeichnet der blaue Ball eine derartige Linie, muss die Schleife bestehend aus
Ausfahrt alt und Einfahrt neu entfernt werden, da sich der blaue Ball sonst
jederzeit auf eine sichere Insel retten könnte, womit das Spiel an Reiz
verlieren würde.

Bonusaufgabe: SpielerIn und Computergegner bewegen (10 Punkte)     =====> erledigt
---------------------------------------------------------------
Mittels Timer wird in regelmäßigen Abständen ein ActionListener aufgerufen, der
sowohl den blauen Ball (falls gerade eine Pfeiltaste gedrückt wird) als auch den
roten Ball (jedenfalls) zu bewegen versucht. Beide Bälle müssen bei der Bewegung
den Regeln des Spiels folgen: kein Ball darf das Spielfeld verlassen; der rote
Ball kann sich waagrecht, senkrecht oder diagonal aber nur in freien Flächen
bewegen; der rote Ball bewegt sich schneller als der blaue Ball; der blaue Ball
kann sich nur waagrecht oder senkrecht bewegen; bei Berührung des roten Balls
mit dem blauen Ball bzw einer Linie, die gerade gezogen wird, ist das Spiel
verloren. Erreicht der rote Ball eine Grenze, prallt er von ihr wieder ab.

Bonusaufgabe: zufälliger Computergegner (5 Punkte)          ===> zum Teil erledigt
----------------------------------------------------
Der rote Ball ändert mit Hilfe einer zufällig generierten Zahl manchmal seine
Richtung und steuert auf den blauen Ball zu.
Bonusaufgabe: optischer Aufputz und Kollisionen (4 Punkte)
Die Flächen werden zufällig in den Farben rot, gelb, blau, schwarz und hellgrau
gefüllt.
Der kleine blaue Ball bekommt einen Durchmesser von 8 Pixeln. Der rote Ball
einen Durchmesser von 12 Pixeln. Kollisionen werden nicht mehr nur in Bezug auf
die Zentren, sondern auf die gesamte Fläche der Bälle bezogen.

Bereitgestellte Dateien
Mondrian.java: ausführbare Klasse und Startgerüst für das Programm
Board.java: von jawax.swing.JPanel abgeleitete Klasse für das Spielfeld
Beide Klassen können Sie verändern und gegebenenfalls weitere Klassen erstellen.

Testen der Implementierung
Testen Sie ihr Programm regelmäßig interaktiv. Wenn Fragen zum gewünschten
Verhalten auftreten, wenden Sie sich bitte an das TUWELForum.
