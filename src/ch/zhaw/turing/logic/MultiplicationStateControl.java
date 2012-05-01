package ch.zhaw.turing.logic;

import java.util.Observable;
import static ch.zhaw.turing.logic.ReadWriteHead.EMPTY_CHAR;
import static ch.zhaw.turing.logic.ReadWriteHead.ZERO_CHAR;
import static ch.zhaw.turing.logic.ReadWriteHead.ONE_CHAR;
import static ch.zhaw.turing.logic.ReadWriteHead.EMPTY_VALUE;
import static ch.zhaw.turing.logic.ReadWriteHead.ZERO_VALUE;
import static ch.zhaw.turing.logic.ReadWriteHead.ONE_VALUE;

public class MultiplicationStateControl extends Observable {

    public static final String Q0 = "Q0";
    public static final String Q1 = "Q1";
    public static final String Q2 = "Q2";
    public static final String Q3 = "Q3";
    public static final String Q4 = "Q4";
    public static final String Q5 = "Q5";
    public static final String Q6 = "Q6";
    public static final String Q7 = "Q7";
    public static final String Q8 = "Q8";
    public static final String Q9 = "Q9";
    public static final String Q10 = "Q10";

    private TwoTapeConfiguration curConfiguration;
    
    private String curState;
    
    private ReadWriteHead firstRSH;
    private ReadWriteHead secondRSH;

    /**
     * Erstellt eine neue Zustandssteuerung für die Multiplikation die genau die
     * mitgegebenen Bänder nutzt.
     * 
     * Die Position des LS-Kopfes des oberen Bandes muss dazu genau auf dem
     * ersten Zeichen der Eingabe sein. Das Zweite Band muss leer sein.
     * 
     * @param firstRSH
     *            der erste Lese-Schreibkopf der Maschine
     * @param secondRSH
     *            der zweite Lese-Schreibkopf der Maschine
     * 
     */
    public MultiplicationStateControl(ReadWriteHead firstRSH, ReadWriteHead secondRSH) {
        this.firstRSH = firstRSH;
        this.secondRSH = secondRSH;

        curState = MultiplicationStateControl.Q0;
        curConfiguration = new TwoTapeConfiguration(curState, firstRSH.read(), secondRSH.read());
    }

    /**
     * 
     * @param multiplikator
     * @param multiplikant
     * @param firstRSH
     */
    private void setUpTape(int multiplikator, int multiplikant) {
        for (int i = 0; i < multiplikator; i++) {
            firstRSH.write(ZERO_VALUE);
            firstRSH.moveRight();
        }

        // Trennzeichen der zwei Zahlen
        firstRSH.write(ONE_CHAR);
        firstRSH.moveRight();

        for (int i = 0; i < multiplikant; i++) {
            firstRSH.write(ZERO_VALUE);
            firstRSH.moveRight();
        }

        // Endzeichen
        firstRSH.write(ONE_CHAR);

        // wieder an die Ausgangsposition fahren
        do {
            firstRSH.moveLeft();
        } while (firstRSH.read() != 'B');

        firstRSH.moveRight();
    }

    /**
     * Erstellt eine neue Zustandssteuerung für die Multiplikation und
     * initialisiert das Band. Die Position des LS-Kopfes ist danach genau auf
     * dem ersten Zeichen der Eingabe. Die Lese-Schreibeköpfe können mitgegeben
     * erstellt.
     * 
     * @param multiplikator
     *            linke Zahl der Multiplikation.
     * @param multiplikant
     *            rechte Zahl der Multiplikation.
     */
    public MultiplicationStateControl(int multiplikator, int multiplikant) {
        this.firstRSH = new ReadWriteHead();
        this.secondRSH = new ReadWriteHead();

        setUpTape(multiplikator, multiplikant);
        curState = MultiplicationStateControl.Q0;
        curConfiguration = new TwoTapeConfiguration(curState, firstRSH.read(), secondRSH.read());
    }

    /**
     * Führt alle Schritte der Multiplikation aus. Am Ende der Berechnung steht
     * der Lese- Schreibkopf des oberen Bandes hinter der letzten Stelle der
     * multiplizierten Zahl.
     */
    public void doAllSteps() {
        String curState = MultiplicationStateControl.Q0;
        
        while (curState != MultiplicationStateControl.Q10) {
            curState = doStep();
        }
    }

    public String doStep() {
        //printCurrentState();

        // hier is der Switch ueber die derzeitige Konfiguration und darauf die
        // Entscheidung fuer die naechste konfiguration.
        
        String nextState;

        if (curConfiguration.getCurState().equals(MultiplicationStateControl.Q0)) {
            nextState = handleQ0();
        } else if (curConfiguration.getCurState().equals(MultiplicationStateControl.Q1)) {
            nextState = handleQ1();
        } else if (curConfiguration.getCurState().equals(MultiplicationStateControl.Q2)) {
            nextState = handleQ2();
        } else if (curConfiguration.getCurState().equals(MultiplicationStateControl.Q3)) {
            nextState = handleQ3();
        } else if (curConfiguration.getCurState().equals(MultiplicationStateControl.Q4)) {
            nextState = handleQ4();
        } else if (curConfiguration.getCurState().equals(MultiplicationStateControl.Q5)) {
            nextState = handleQ5();
        } else if (curConfiguration.getCurState().equals(MultiplicationStateControl.Q6)) {
            nextState = handleQ6();
        } else if (curConfiguration.getCurState().equals(MultiplicationStateControl.Q7)) {
            nextState = handleQ7();
        } else if (curConfiguration.getCurState().equals(MultiplicationStateControl.Q8)) {
            nextState = handleQ8();
        } else if (curConfiguration.getCurState().equals(MultiplicationStateControl.Q9)) {
            nextState = handleQ9();
        } else if (curConfiguration.getCurState().equals(MultiplicationStateControl.Q10)) {
            nextState = handleQ10();
        } else {
            throw new IllegalStateException(curConfiguration.getCurState() + " existiert nicht");
        }
        
        curConfiguration = new TwoTapeConfiguration(nextState, firstRSH.read(), secondRSH.read());
        //printCurrentStateWithDirection();
        
        return nextState;
    }

    private String handleQ10() {
        if (!(curConfiguration.getFirstTapeCharacter() == EMPTY_CHAR)) {
            dumpTape1Exeption();
        }

        if (!(curConfiguration.getSecondTapeCharacter() == EMPTY_CHAR)) {
            dumpTape2Exeption();
        }
        
        return MultiplicationStateControl.Q10;
    }

    private String handleQ9() {
        if (curConfiguration.getFirstTapeCharacter() == ZERO_CHAR) {
            firstRSH.moveLeft();

            secondRSH.stay();

            return MultiplicationStateControl.Q9;
        } else if (curConfiguration.getFirstTapeCharacter() == ONE_CHAR) {
            firstRSH.moveLeft();

            secondRSH.stay();

            return MultiplicationStateControl.Q9;
        } else if (curConfiguration.getFirstTapeCharacter() == EMPTY_CHAR) {
            secondRSH.moveRight();

            return MultiplicationStateControl.Q7;
        } else {
            dumpTape1Exeption();
        }

        if (!(curConfiguration.getSecondTapeCharacter() == EMPTY_CHAR)) {
            dumpTape2Exeption();
        }
        
        return null;
    }

    private String handleQ8() {
        if (!(curConfiguration.getFirstTapeCharacter() == EMPTY_CHAR)) {
            dumpTape1Exeption();
        }

        if (curConfiguration.getSecondTapeCharacter() == ZERO_CHAR) {
            firstRSH.write(ZERO_VALUE);
            firstRSH.moveRight();

            secondRSH.write(EMPTY_VALUE);
            secondRSH.moveLeft();

            return MultiplicationStateControl.Q8;
        } else if (curConfiguration.getSecondTapeCharacter() == EMPTY_CHAR) {
            firstRSH.stay();

            secondRSH.stay();

            return MultiplicationStateControl.Q10;
        } else {
            dumpTape2Exeption();
            return null;
        }
    }

    private String handleQ7() {
        if (curConfiguration.getFirstTapeCharacter() == ZERO_CHAR) {
            firstRSH.write(EMPTY_VALUE);
            firstRSH.moveRight();

            secondRSH.stay();

            return MultiplicationStateControl.Q7;
        } else if (curConfiguration.getFirstTapeCharacter() == ONE_CHAR) {
            firstRSH.write(EMPTY_VALUE);
            firstRSH.moveRight();

            secondRSH.stay();

            return MultiplicationStateControl.Q7;
        } else if (curConfiguration.getFirstTapeCharacter() == EMPTY_CHAR) {
            firstRSH.stay();

            secondRSH.moveLeft();

            return MultiplicationStateControl.Q8;
        } else {
            dumpTape1Exeption();
            return null;
        }

        //if (!(curConfiguration.getSecondTapeCharacter() == EMPTY_CHAR)) { dumpTape2Exeption(); }
    }

    private String handleQ6() {
        if (curConfiguration.getFirstTapeCharacter() == ZERO_CHAR) {
            firstRSH.moveLeft();

            secondRSH.stay();

            return MultiplicationStateControl.Q6;
        } else if (curConfiguration.getFirstTapeCharacter() == ONE_CHAR) {
            firstRSH.moveRight();

            secondRSH.stay();

            return MultiplicationStateControl.Q0;
        } else {
            dumpTape1Exeption();
            return null;
        }

        //if (!(curConfiguration.getSecondTapeCharacter() == EMPTY_CHAR)) { dumpTape2Exeption(); }
    }

    private String handleQ5() {
        if (curConfiguration.getFirstTapeCharacter() == ZERO_CHAR) {
            firstRSH.moveLeft();

            secondRSH.stay();

            return MultiplicationStateControl.Q6;
        } else if (curConfiguration.getFirstTapeCharacter() == ONE_CHAR) {
            firstRSH.moveLeft();

            secondRSH.stay();

            return MultiplicationStateControl.Q5;
        } else if (curConfiguration.getFirstTapeCharacter() == EMPTY_CHAR) {
            firstRSH.moveRight();

            secondRSH.stay();

            return MultiplicationStateControl.Q7;
        } else {
            dumpTape1Exeption();
            return null;
        }

        //if (!(curConfiguration.getSecondTapeCharacter() == EMPTY_CHAR)) { dumpTape2Exeption(); }
    }

    private String handleQ4() {
        if (curConfiguration.getFirstTapeCharacter() == ZERO_CHAR) {
            firstRSH.moveLeft();

            secondRSH.stay();

            return MultiplicationStateControl.Q4;
        } else if (curConfiguration.getFirstTapeCharacter() == ONE_CHAR) {
            firstRSH.moveLeft();

            secondRSH.stay();

            return MultiplicationStateControl.Q5;
        } else {
            dumpTape1Exeption();
            return null;
        }

        //if (!(curConfiguration.getSecondTapeCharacter() == EMPTY_CHAR)) { dumpTape2Exeption(); }
    }

    private String handleQ3() {
        if (curConfiguration.getFirstTapeCharacter() == ZERO_CHAR) {
            firstRSH.moveRight();

            secondRSH.write(ZERO_VALUE);
            secondRSH.moveRight();

            return MultiplicationStateControl.Q3;
        } else if (curConfiguration.getFirstTapeCharacter() == ONE_CHAR) {
            firstRSH.moveLeft();

            secondRSH.stay();

            return MultiplicationStateControl.Q4;
        } else {
            dumpTape1Exeption();
            return null;
        }

        //if (!(curConfiguration.getSecondTapeCharacter() == EMPTY_CHAR)) { dumpTape2Exeption(); }
    }

    private String handleQ2() {
        if (curConfiguration.getFirstTapeCharacter() == ZERO_CHAR) {
            firstRSH.moveRight();

            secondRSH.write(ZERO_VALUE);
            secondRSH.moveRight();

            return MultiplicationStateControl.Q3;
        } else if (curConfiguration.getFirstTapeCharacter() == ONE_CHAR) {
            firstRSH.moveLeft();

            secondRSH.stay();

            return MultiplicationStateControl.Q9;
        } else {
            dumpTape1Exeption();
            return null;
        }

        //if (!(curConfiguration.getSecondTapeCharacter() == EMPTY_CHAR)) { dumpTape2Exeption(); }
    }

    private String handleQ1() {
        if (curConfiguration.getFirstTapeCharacter() == ZERO_CHAR) {
            firstRSH.moveRight();

            secondRSH.stay();

            return MultiplicationStateControl.Q1;
        } else if (curConfiguration.getFirstTapeCharacter() == ONE_CHAR) {
            firstRSH.moveRight();

            secondRSH.stay();

            return MultiplicationStateControl.Q2;
        } else {
            dumpTape1Exeption();
            return null;
        }

        //if (!(curConfiguration.getSecondTapeCharacter() == EMPTY_CHAR)) { dumpTape2Exeption(); }
    }

    private String handleQ0() {
        if (curConfiguration.getFirstTapeCharacter() == ZERO_CHAR) {
            firstRSH.write(ONE_VALUE);
            firstRSH.moveRight();

            secondRSH.stay();

            return MultiplicationStateControl.Q1;
        } else if (curConfiguration.getFirstTapeCharacter() == ONE_CHAR) {
            firstRSH.stay();

            secondRSH.stay();

            return MultiplicationStateControl.Q7;
        } else {
            dumpTape1Exeption();
            return null;
        }

        //if (!(curConfiguration.getSecondTapeCharacter() == EMPTY_CHAR)) { dumpTape2Exeption(); }
    }

    private void dumpTape2Exeption() {
        System.err.println("Zustand: " + curConfiguration.getCurState() + " Ungültiger Buchstabe auf Band 2:"
                + curConfiguration.getFirstTapeCharacter());
    }

    private void dumpTape1Exeption() {
        System.err.println("Zustand: " + curConfiguration.getCurState() + " Ungültiger Buchstabe auf Band 1:"
                + curConfiguration.getFirstTapeCharacter());
    }

    /**
     * Kodiert die errechnete Zahl aus der unären Darstellung in die dezimale.
     * Der Lese-Schreibkopf des ersten Bandes steht nach dieser Operation hinter
     * der letzten Ziffer der Zahl.
     * 
     * @return das Ergebnis der Rechnung als Decimalzahl.
     */
    public int getFirstNumberAsInteger() {
        int i = 0;
    
        firstRSH.moveLeft();
    
        while (firstRSH.read() != 'B') {
            firstRSH.moveLeft();
        }
    
        firstRSH.moveRight();
    
        while (firstRSH.read() == '0') {
            firstRSH.moveRight();
            i++;
        }
    
        return i;
    }

    public void printCurrentState() {
        System.out.print("d{" + curConfiguration.getCurState() + ", " + curConfiguration.getFirstTapeCharacter() + ", "
                + curConfiguration.getSecondTapeCharacter() + "}");
    }

    public void printCurrentStateWithDirection() {
        System.out.println(" |- d{" + curConfiguration.getCurState() + ", " + curConfiguration.getFirstTapeCharacter()
                + ", " + curConfiguration.getSecondTapeCharacter() + ", " + firstRSH.getLastDirection() + ", "
                + secondRSH.getLastDirection() + "}");
    }
}
