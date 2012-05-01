package ch.zhaw.turing.logic;

import java.util.Observable;
import static ch.zhaw.turing.logic.ReadWriteHead.EMPTY_VALUE;
import static ch.zhaw.turing.logic.ReadWriteHead.ZERO_VALUE;
import static ch.zhaw.turing.logic.ReadWriteHead.ONE_VALUE;
import static ch.zhaw.turing.logic.ReadWriteHead.EMPTY_CHAR;
import static ch.zhaw.turing.logic.ReadWriteHead.ZERO_CHAR;
import static ch.zhaw.turing.logic.ReadWriteHead.ONE_CHAR;

public class FactorialStateControl extends Observable {

    public static final String Q0 = "Q0";
    public static final String Q1 = "Q1";
    public static final String Q2 = "Q2";
    public static final String Q3 = "Q3";
    public static final String Q4 = "Q4";
    public static final String Q5 = "Q5";
    public static final String Q6 = "Q6";
    public static final String Q7 = "Q7";
    public static final String Q8 = "Q8";

    private TwoTapeConfiguration curConfiguration;

    private String curState;

    private ReadWriteHead firstRSH;
    private ReadWriteHead secondRSH;
    private ReadWriteHead thirdRSH;

    /**
     * Erstellt eine neue Zustandssteuerung für die Fakultätsberechnung und initialisiert das Band. Die Position des
     * LS-Kopfes ist danach genau auf dem ersten Zeichen der Eingabe.
     * 
     * @param number
     *            die Zahl deren Fakultät berechnet werden soll.
     */
    public FactorialStateControl(int number) {
        this.firstRSH = new ReadWriteHead();
        this.secondRSH = new ReadWriteHead();
        this.thirdRSH = new ReadWriteHead();

        for (int i = 0; i < number; i++) {
            firstRSH.write('0');
            firstRSH.moveRight();
        }

        // Endzeichen
        firstRSH.write('1');

        // wieder nach links fahren
        do {
            firstRSH.moveLeft();
        } while (firstRSH.read() != 'B');

        firstRSH.moveRight();
        curState = FactorialStateControl.Q0;
        curConfiguration = new TwoTapeConfiguration(curState, firstRSH.read(), secondRSH.read());
    }

    public void doAllSteps() {
        while ((curState != FactorialStateControl.Q8)
                && (curState != FactorialStateControl.Q7)) {
            doStep();
        }

        // System.out.println("Ergebnis: " + getFirstNumberAsInteger());
    }

    public void doStep() {
        // printCurrentState();

        // hier is der Switch ueber die derzeitige Konfiguration und darauf die
        // Entscheidung fuer die naechste konfiguration.

        if (curConfiguration.getCurState().equals(FactorialStateControl.Q0)) {
            handleQ0();
        } else if (curConfiguration.getCurState().equals(FactorialStateControl.Q1)) {
            handleQ1();
        } else if (curConfiguration.getCurState().equals(FactorialStateControl.Q2)) {
            handleQ2();
        } else if (curConfiguration.getCurState().equals(FactorialStateControl.Q3)) {
            handleQ3();
        } else if (curConfiguration.getCurState().equals(FactorialStateControl.Q4)) {
            handleQ4();
        } else if (curConfiguration.getCurState().equals(FactorialStateControl.Q5)) {
            handleQ5();
        } else if (curConfiguration.getCurState().equals(FactorialStateControl.Q6)) {
            handleQ6();
        } else if (curConfiguration.getCurState().equals(FactorialStateControl.Q7)) {
            handleQ7();
        } else if (curConfiguration.getCurState().equals(FactorialStateControl.Q8)) {
            handleQ8();
        }

        if (!curState.equals(curConfiguration.getCurState())) {
            setChanged();
            notifyObservers();
        }

        curConfiguration = new TwoTapeConfiguration(curState, firstRSH.read(), secondRSH.read());
        // printCurrentStateWithDirection();
    }

    private void handleQ8() {
        if (!(curConfiguration.getFirstTapeCharacter().charValue() == EMPTY_CHAR)) {
            dumpTape1Exeption();
        }

        if (!(curConfiguration.getSecondTapeCharacter().charValue() == EMPTY_CHAR)) {
            dumpTape2Exeption();
        }
    }

    private void handleQ7() {
        if (!(curConfiguration.getFirstTapeCharacter().charValue() == EMPTY_CHAR)) {
            dumpTape1Exeption();
        }

        if (!(curConfiguration.getSecondTapeCharacter().charValue() == EMPTY_CHAR)) {
            dumpTape2Exeption();
        }
    }

    private void handleQ6() {
        ReadWriteHead firstRSH = this.firstRSH;
        ReadWriteHead secondRSH = this.secondRSH;
        TwoTapeConfiguration curConfiguration = this.curConfiguration;

        if (!(curConfiguration.getFirstTapeCharacter().charValue() == EMPTY_CHAR)) {
            dumpTape1Exeption();
        }

        if (curConfiguration.getSecondTapeCharacter().charValue() == ZERO_CHAR) {
            firstRSH.write(ZERO_VALUE);
            firstRSH.moveRight();

            secondRSH.write(EMPTY_VALUE);
            secondRSH.moveLeft();

            curState = FactorialStateControl.Q6;
        } else if (curConfiguration.getSecondTapeCharacter().charValue() == ONE_CHAR) {
            firstRSH.stay();

            secondRSH.write(EMPTY_VALUE);
            secondRSH.moveLeft();

            curState = FactorialStateControl.Q6;
        } else if (curConfiguration.getSecondTapeCharacter().charValue() == EMPTY_CHAR) {
            firstRSH.stay();

            secondRSH.stay();

            curState = FactorialStateControl.Q7;
        } else {
            dumpTape2Exeption();
        }
    }

    private void handleQ5() {
        if (curConfiguration.getFirstTapeCharacter().charValue() == ZERO_CHAR) {
            firstRSH.moveRight();

            secondRSH.stay();

            curState = FactorialStateControl.Q5;
        } else if (curConfiguration.getFirstTapeCharacter().charValue() == EMPTY_CHAR) {
            firstRSH.moveLeft();

            secondRSH.write(ONE_VALUE);
            secondRSH.moveRight();

            curState = FactorialStateControl.Q2;
        } else {
            dumpTape1Exeption();
        }

        if (!(curConfiguration.getSecondTapeCharacter().charValue() == EMPTY_CHAR)) {
            dumpTape2Exeption();
        }
    }

    private void handleQ4() {

        if (!(curConfiguration.getFirstTapeCharacter().charValue() == EMPTY_CHAR)) {
            dumpTape1Exeption();
        }

        if (curConfiguration.getSecondTapeCharacter().charValue() == ZERO_CHAR) {
            firstRSH.stay();

            secondRSH.moveLeft();

            curState = FactorialStateControl.Q4;
        } else if (curConfiguration.getSecondTapeCharacter().charValue() == ONE_CHAR) {
            firstRSH.stay();

            secondRSH.moveLeft();

            curState = FactorialStateControl.Q4;
        } else if (curConfiguration.getSecondTapeCharacter().charValue() == EMPTY_CHAR) {
            firstRSH.moveRight();

            // Auf erstes Zeichen stellen
            secondRSH.moveRight();

            MultiplicationStateControl myMultiplicationStateControl = new MultiplicationStateControl(secondRSH,
                    thirdRSH);
            myMultiplicationStateControl.doAllSteps();

            curConfiguration = new TwoTapeConfiguration(curState, firstRSH.read(), secondRSH.read());

            if (curConfiguration.getFirstTapeCharacter().charValue() == ZERO_CHAR) {
                firstRSH.write(EMPTY_VALUE);
                firstRSH.moveRight();

                secondRSH.stay();

                curState = FactorialStateControl.Q5;
            } else {
                dumpTape1Exeption();
            }

            if (!(curConfiguration.getSecondTapeCharacter().charValue() == EMPTY_CHAR)) {
                dumpTape2Exeption();
            }

        } else {
            dumpTape2Exeption();
        }
    }

    private void handleQ3() {
        if (curConfiguration.getFirstTapeCharacter().charValue() == ZERO_CHAR) {
            firstRSH.moveLeft();

            secondRSH.write(ZERO_VALUE);
            secondRSH.moveRight();

            curState = FactorialStateControl.Q3;
        } else if (curConfiguration.getFirstTapeCharacter().charValue() == EMPTY_CHAR) {
            firstRSH.stay();

            secondRSH.write(ONE_VALUE);
            secondRSH.moveLeft();

            curState = FactorialStateControl.Q4;
        } else {
            dumpTape1Exeption();
        }

        if (!(curConfiguration.getSecondTapeCharacter().charValue() == EMPTY_CHAR)) {
            dumpTape2Exeption();
        }
    }

    private void handleQ2() {
        if (curConfiguration.getFirstTapeCharacter().charValue() == ZERO_CHAR) {
            firstRSH.moveLeft();

            secondRSH.write(ZERO_VALUE);
            secondRSH.moveRight();

            curState = FactorialStateControl.Q3;
        } else if (curConfiguration.getFirstTapeCharacter().charValue() == EMPTY_CHAR) {
            firstRSH.stay();

            secondRSH.moveLeft();

            curState = FactorialStateControl.Q6;
        } else {
            dumpTape1Exeption();
        }

        if (!(curConfiguration.getSecondTapeCharacter().charValue() == EMPTY_CHAR)) {
            dumpTape2Exeption();
        }
    }

    private void handleQ1() {
        ReadWriteHead firstRSH = this.firstRSH;
        ReadWriteHead secondRSH = this.secondRSH;
        char firstTapeCharacter = this.curConfiguration.getFirstTapeCharacter().charValue();

        if (firstTapeCharacter == ZERO_VALUE.charValue()) {
            firstRSH.moveRight();

            secondRSH.write(ZERO_VALUE);
            secondRSH.moveRight();

            curState = FactorialStateControl.Q1;
        } else if (firstTapeCharacter == ONE_VALUE.charValue()) {
            firstRSH.write(EMPTY_VALUE);
            firstRSH.moveLeft();
            //firstRSH.moveLeftAndWrite(EMPTY_VALUE);

            secondRSH.write(ONE_VALUE);
            secondRSH.moveRight();

            curState = FactorialStateControl.Q2;
        } else {
            dumpTape1Exeption();
        }

        // if (!(curConfiguration.getSecondTapeCharacter().charValue() == EMPTY_CHAR)) {
        // dumpTape2Exeption();
        // }
        // Reto optimiert: Wir haben doch genügend Unit Tests um sicherzustellen, dass da nicht schief geht?
    }

    private void handleQ0() {
        if (curConfiguration.getFirstTapeCharacter().charValue() == ZERO_CHAR) {
            firstRSH.write(EMPTY_VALUE);
            firstRSH.moveRight();

            secondRSH.write(ZERO_VALUE);
            secondRSH.moveRight();

            curState = FactorialStateControl.Q1;
        } else if (curConfiguration.getFirstTapeCharacter().charValue() == ONE_CHAR) {
            firstRSH.write(ZERO_VALUE);
            firstRSH.stay();

            secondRSH.stay();

            curState = FactorialStateControl.Q8;
        } else {
            dumpTape1Exeption();
        }

        if (!(curConfiguration.getSecondTapeCharacter().charValue() == EMPTY_CHAR)) {
            dumpTape2Exeption();
        }
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
     * Kodiert die errechnete Zahl aus der unären Darstellung in die dezimale. Der Lese-Schreibkopf des ersten Bandes
     * steht nach dieser Operation hinter der letzten Ziffer der Zahl. Diese Position wird auch als Ausgangspunkt für
     * die Umwandelung angesehen.
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
