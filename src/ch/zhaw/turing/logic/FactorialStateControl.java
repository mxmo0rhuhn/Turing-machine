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

    private TwoTapeConfiguration curConfiguration; // wird immer null sein

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
    }

    public void doAllSteps() {
        ReadWriteHead firstRSH = this.firstRSH;
        ReadWriteHead secondRSH = this.secondRSH;
        
        // Startzustand
        String curState = FactorialStateControl.Q0;
        Character fstTapeChar = firstRSH.read().charValue();
        Character sndTapeChar = secondRSH.read().charValue();

        while ((curState != FactorialStateControl.Q8) && (curState != FactorialStateControl.Q7)) {
            curState = doStep(curState, fstTapeChar, sndTapeChar);
            fstTapeChar = firstRSH.read().charValue();
            sndTapeChar = secondRSH.read().charValue();
        }

        // System.out.println("Ergebnis: " + getFirstNumberAsInteger());
    }

    public String doStep(String lastState, char fstTapeChar, char sndTapeChar) {
        // printCurrentState();

        // hier is der Switch ueber die derzeitige Konfiguration und darauf die
        // Entscheidung fuer die naechste konfiguration.
        String nextState;

        if (lastState == FactorialStateControl.Q0) {
            nextState = handleQ0(fstTapeChar, sndTapeChar);
        } else if (lastState == FactorialStateControl.Q1) {
            nextState = handleQ1(fstTapeChar, sndTapeChar);
        } else if (lastState == FactorialStateControl.Q2) {
            nextState = handleQ2(fstTapeChar, sndTapeChar);
        } else if (lastState == FactorialStateControl.Q3) {
            nextState = handleQ3(fstTapeChar, sndTapeChar);
        } else if (lastState == FactorialStateControl.Q4) {
            nextState = handleQ4(fstTapeChar, sndTapeChar);
        } else if (lastState == FactorialStateControl.Q5) {
            nextState = handleQ5(fstTapeChar, sndTapeChar);
        } else if (lastState == FactorialStateControl.Q6) {
            nextState = handleQ6(fstTapeChar, sndTapeChar);
        } else if (lastState == FactorialStateControl.Q7) {
            nextState = handleQ7(fstTapeChar, sndTapeChar);
        } else if (lastState == FactorialStateControl.Q8) {
            nextState = handleQ8(fstTapeChar, sndTapeChar);
        } else {
            throw new IllegalStateException(lastState + " existiert nicht!");
        }

        // man kann doch auch etwas machen und im gleichen zustand bleiben? das wollen die leute da draussen auch wissen
        // ;)
        // if (!curState.equals(curConfiguration.getCurState())) { setChanged(); notifyObservers(); }
        
        // FIXME ein observer sollte allenfalls printen, aber nicht das program selbst
        // printCurrentStateWithDirection();
        return nextState;
    }

    private String handleQ8(char fstTapeChar, char sndTapeChar) {
        if (!(fstTapeChar == EMPTY_CHAR)) {
            dumpTape1Exeption();
        }

        if (!(sndTapeChar == EMPTY_CHAR)) {
            dumpTape2Exeption();
        }
        return FactorialStateControl.Q8;
    }

    private String handleQ7(char fstTapeChar, char sndTapeChar) {
        if (!(fstTapeChar == EMPTY_CHAR)) {
            dumpTape1Exeption();
        }

        if (!(sndTapeChar == EMPTY_CHAR)) {
            dumpTape2Exeption();
        }
        return FactorialStateControl.Q7;
    }

    private String handleQ6(char fstTapeChar, char sndTapeChar) {
        ReadWriteHead firstRSH = this.firstRSH;
        ReadWriteHead secondRSH = this.secondRSH;

        if (!(fstTapeChar == EMPTY_CHAR)) {
            dumpTape1Exeption();
        }

        if (sndTapeChar == ZERO_CHAR) {
            firstRSH.write(ZERO_VALUE);
            firstRSH.moveRight();

            secondRSH.write(EMPTY_VALUE);
            secondRSH.moveLeft();

            return FactorialStateControl.Q6;
        } else if (sndTapeChar == ONE_CHAR) {
            firstRSH.stay();

            secondRSH.write(EMPTY_VALUE);
            secondRSH.moveLeft();

            return FactorialStateControl.Q6;
        } else if (sndTapeChar == EMPTY_CHAR) {
            firstRSH.stay();

            secondRSH.stay();

            return FactorialStateControl.Q7;
        } else {
            dumpTape2Exeption();
            return null;// FIXME richtige excpetion werfen? (runtime)
        }
    }

    private String handleQ5(char fstTapeChar, char sndTapeChar) {
        if (fstTapeChar == ZERO_CHAR) {
            firstRSH.moveRight();

            secondRSH.stay();

            return FactorialStateControl.Q5;
        } else if (fstTapeChar == EMPTY_CHAR) {
            firstRSH.moveLeft();

            secondRSH.write(ONE_VALUE);
            secondRSH.moveRight();

            return FactorialStateControl.Q2;
        } else {
            dumpTape1Exeption();
            return null;
        }

        // if (!(curConfiguration.getSecondTapeCharacter().charValue() == EMPTY_CHAR)) { dumpTape2Exeption(); }
    }

    private String handleQ4(char fstTapeChar, char sndTapeChar) {
        ReadWriteHead firstRSH = this.firstRSH;
        ReadWriteHead secondRSH = this.secondRSH;

        if (!(fstTapeChar == EMPTY_CHAR)) {
            dumpTape1Exeption();
        }

        if (sndTapeChar == ZERO_CHAR) {
            firstRSH.stay();

            secondRSH.moveLeft();

            return FactorialStateControl.Q4;
        } else if (sndTapeChar == ONE_CHAR) {
            firstRSH.stay();

            secondRSH.moveLeft();

            return FactorialStateControl.Q4;
        } else if (sndTapeChar == EMPTY_CHAR) {
            firstRSH.moveRight();

            // Auf erstes Zeichen stellen
            secondRSH.moveRight();

            MultiplicationStateControl myMultiplicationStateControl = new MultiplicationStateControl(secondRSH,
                    thirdRSH);
            myMultiplicationStateControl.doAllSteps();

            if (firstRSH.read().charValue() == ZERO_CHAR) {
                firstRSH.write(EMPTY_VALUE);
                firstRSH.moveRight();

                secondRSH.stay();

                return FactorialStateControl.Q5;
            } else {
                dumpTape1Exeption();
                return null;
            }

            // if (!(curConfiguration.getSecondTapeCharacter().charValue() == EMPTY_CHAR)) { dumpTape2Exeption(); }

        } else {
            dumpTape2Exeption();
            return null;
        }
    }

    private String handleQ3(char fstTapeChar, char sndTapeChar) {
        if (fstTapeChar == ZERO_CHAR) {
            firstRSH.moveLeft();

            secondRSH.write(ZERO_VALUE);
            secondRSH.moveRight();

            return FactorialStateControl.Q3;
        } else if (fstTapeChar == EMPTY_CHAR) {
            firstRSH.stay();

            secondRSH.write(ONE_VALUE);
            secondRSH.moveLeft();

            return FactorialStateControl.Q4;
        } else {
            dumpTape1Exeption();
            return null;
        }

        // if (!(curConfiguration.getSecondTapeCharacter().charValue() == EMPTY_CHAR)) { dumpTape2Exeption(); }
    }

    private String handleQ2(char fstTapeChar, char sndTapeChar) {
        if (fstTapeChar == ZERO_CHAR) {
            firstRSH.moveLeft();

            secondRSH.write(ZERO_VALUE);
            secondRSH.moveRight();

            return FactorialStateControl.Q3;
        } else if (fstTapeChar == EMPTY_CHAR) {
            firstRSH.stay();

            secondRSH.moveLeft();

            return FactorialStateControl.Q6;
        } else {
            dumpTape1Exeption();
            return null;
        }

        // if (!(curConfiguration.getSecondTapeCharacter().charValue() == EMPTY_CHAR)) { dumpTape2Exeption(); }
    }

    private String handleQ1(char fstTapeChar, char sndTapeChar) {
        ReadWriteHead firstRSH = this.firstRSH;
        ReadWriteHead secondRSH = this.secondRSH;

        if (fstTapeChar == ZERO_VALUE.charValue()) {
            firstRSH.moveRight();

            secondRSH.write(ZERO_VALUE);
            secondRSH.moveRight();

            return FactorialStateControl.Q1;
        } else if (fstTapeChar == ONE_VALUE.charValue()) {
            firstRSH.write(EMPTY_VALUE);
            firstRSH.moveLeft();

            secondRSH.write(ONE_VALUE);
            secondRSH.moveRight();

            return FactorialStateControl.Q2;
        } else {
            dumpTape1Exeption();
            return null;
        }

        // if (!(curConfiguration.getSecondTapeCharacter().charValue() == EMPTY_CHAR)) {
        // dumpTape2Exeption();
        // }
        // Reto optimiert: Wir haben doch genügend Unit Tests um sicherzustellen, dass da nicht schief geht?
    }

    private String handleQ0(char fstTapeChar, char sndTapeChar) {
        if (fstTapeChar == ZERO_CHAR) {
            firstRSH.write(EMPTY_VALUE);
            firstRSH.moveRight();

            secondRSH.write(ZERO_VALUE);
            secondRSH.moveRight();

            return FactorialStateControl.Q1;
        } else if (fstTapeChar == ONE_CHAR) {
            firstRSH.write(ZERO_VALUE);
            firstRSH.stay();

            secondRSH.stay();

            return FactorialStateControl.Q8;
        } else {
            dumpTape1Exeption();
            return null;
        }

        // if (!(curConfiguration.getSecondTapeCharacter().charValue() == EMPTY_CHAR)) { dumpTape2Exeption(); }
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
