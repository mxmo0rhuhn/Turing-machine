package ch.zhaw.turing.logic;

public class MultiplicationStateControl {

    // Hier werden alle Zustände, die es gibt erfasst.
    public static final String q0 = "q0";
    
    private Configuration curConfiguration;
    private ReadWriteHead firstRSH;
    private ReadWriteHead secondRSH;

    /**
     * Erstellt eine neue Zustandssteuerung für die Multiplikation und
     * initialisiert das Band. Die Position des LS-Kopfes ist danach genau vor
     * dem ersten Zeichen der Eingabe.
     * Die Lese-Schreibeköpfe können mitgegeben werden.
     * 
     * @param multiplikator
     *            linke Zahl der Multiplikation.
     * @param multiplikant
     *            rechte Zahl der Multiplikation.
     * @param firstRSH
     *            der erste Lese-Schreibkopf der Maschine
     * @param secondRSH
     *            der zweite Lese-Schreibkopf der Maschine          
     *            
     */
    public MultiplicationStateControl(int multiplikator, int multiplikant, ReadWriteHead firstRSH, ReadWriteHead secondRSH) {
        this.firstRSH = firstRSH;
        this.secondRSH = secondRSH;
        
        for (int i = 0; i < multiplikator; i++) {
            firstRSH.write('0');
            firstRSH.moveRight();
        }

        // Trennzeichen der zwei Zahlen
        firstRSH.write('1');

        for (int i = 0; i < multiplikant; i++) {
            firstRSH.write('0');
            firstRSH.moveRight();
        }

        // wieder an die Ausgangsposition fahren
        do {
            firstRSH.moveLeft();
        } while (firstRSH.read() != 'B');

        curConfiguration = new Configuration(MultiplicationStateControl.q0, firstRSH.read());
    }
    
    /**
     * Erstellt eine neue Zustandssteuerung für die Multiplikation und
     * initialisiert das Band. Die Position des LS-Kopfes ist danach genau vor
     * dem ersten Zeichen der Eingabe.
     * Die Lese-Schreibeköpfe werden automatisch erstellt.
     * 
     * @param multiplikator
     *            linke Zahl der Multiplikation.
     * @param multiplikant
     *            rechte Zahl der Multiplikation.
     */
    public MultiplicationStateControl(int multiplikator, int multiplikant) {
        this(multiplikator, multiplikant, new ReadWriteHead(), new ReadWriteHead());
    }
    


    public void doStep() {
        // hier is der Switch ueber die derzeitige Konfiguration und darauf die
        // Entscheidung fuer die naechste konfiguration.

        //curConfiguration = new Configuration(, firstRSH.read());
    }

    public void printCurrentState() {
        System.out.println("Zustand: d{" + curConfiguration.getCurState() + ", " + curConfiguration.getCurCharacter()
                + "}");
    }
}
