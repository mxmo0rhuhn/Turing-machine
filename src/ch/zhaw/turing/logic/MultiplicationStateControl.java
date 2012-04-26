package ch.zhaw.turing.logic;

public class MultiplicationStateControl {

    private Configuration curConfiguration;
    private ReadWriteHead curRWH;

    /**
     * Erstellt eine neue Zustandssteuerung für die Multiplikation und
     * initialisiert das Band. Die Position des LS-Kopfes ist danach genau vor
     * dem ersten Zeichen der Eingabe
     * 
     * @param multiplikator
     *            linke Zahl der Multiplikation.
     * @param multiplikant
     *            rechte Zahl der Multiplikation.
     */
    public MultiplicationStateControl(int multiplikator, int multiplikant) {
        for (int i = 0; i < multiplikator; i++) {
            curRWH.write('0');
            curRWH.moveRight();
        }

        // Trennzeichen der zwei Zahlen
        curRWH.write('1');

        for (int i = 0; i < multiplikant; i++) {
            curRWH.write('0');
            curRWH.moveRight();
        }

        // wieder an die Ausgangsposition fahren
        do {
            curRWH.moveLeft();
        } while (curRWH.read() != 'B');

        curConfiguration = new Configuration("q0", curRWH.read());
    }

    public void doStep() {
        // hier is der Switch ueber die derzeitige Konfiguration und darauf die
        // Entscheidung fuer die naechste konfiguration.

        //curConfiguration = new Configuration(, curRWH.read());
    }

    public void printCurrentState() {
        System.out.println("Zustand: d{" + curConfiguration.getCurState() + ", " + curConfiguration.getCurCharacter()
                + "}");
    }
}
