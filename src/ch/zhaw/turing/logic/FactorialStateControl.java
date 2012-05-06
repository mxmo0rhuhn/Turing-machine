package ch.zhaw.turing.logic;

import static ch.zhaw.turing.logic.ReadWriteHead.EMPTY_CHAR;
import static ch.zhaw.turing.logic.ReadWriteHead.EMPTY_VALUE;
import static ch.zhaw.turing.logic.ReadWriteHead.ONE_CHAR;
import static ch.zhaw.turing.logic.ReadWriteHead.ONE_VALUE;
import static ch.zhaw.turing.logic.ReadWriteHead.ZERO_CHAR;
import static ch.zhaw.turing.logic.ReadWriteHead.ZERO_VALUE;

import java.util.Observer;

public class FactorialStateControl extends TuringMachine {

    public static final String Q0 = "Q0";
    public static final String Q1 = "Q1";
    public static final String Q2 = "Q2";
    public static final String Q3 = "Q3";
    public static final String Q4 = "Q4";
    public static final String Q5 = "Q5";
    public static final String Q6 = "Q6";
    public static final String Q7 = "Q7";
    public static final String Q8 = "Q8";
    
    public static final String MULTIPLICATION = "MULTIPLICATION";

    private int nuberOfSteps;

    private String curState;
    // private String nextState;

    private ReadWriteHead firstRSH;
    private ReadWriteHead secondRSH;
    private ReadWriteHead thirdRSH;
    
    private TuringMachine multiplikation;
    
    private Observer observer;

    /**
     * Erstellt eine neue Zustandssteuerung für die Fakultätsberechnung und
     * initialisiert das Band. Die Position des LS-Kopfes ist danach genau auf
     * dem ersten Zeichen der Eingabe.
     * 
     * @param number
     *            die Zahl deren Fakultät berechnet werden soll.
     */
    public FactorialStateControl(int number) {
        this.firstRSH = new ReadWriteHead();
        this.secondRSH = new ReadWriteHead();
        this.thirdRSH = new ReadWriteHead();

        setUpTape(number);
    }

    public FactorialStateControl(int number, ReadWriteHead firstRSH, ReadWriteHead secondRSH, ReadWriteHead thirdRSH) {
        this.firstRSH = firstRSH;
        this.secondRSH = secondRSH;
        this.thirdRSH = thirdRSH;

        setUpTape(number);
    }
    
    @Override
    public synchronized void addObserver(Observer o) {
        this.observer = o;
        super.addObserver(o);
    }

    /**
     * 
     * @param number
     * @param firstRSH
     */
    private void setUpTape(int number) {
        curState = FactorialStateControl.Q0;
        nuberOfSteps = 0;

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

        while (!acceptedState()) {
            doStep();
        }
    }

    @Override
    public boolean acceptedState() {
        return curState == FactorialStateControl.Q8 || curState == FactorialStateControl.Q7;
    }

    @Override
    public void doStep() {
        
        if (multiplikation != null) {
            multiplikation.doStep();
            nuberOfSteps++;
            if (multiplikation.acceptedState()) {
                multiplikation = null; // das nächste mal wollen wir wieder fakultät rechnen
            }
            return;
        }
        
        String startState = curState;

        char fstTapeChar = firstRSH.read().charValue();
        char sndTapeChar = secondRSH.read().charValue();

        nuberOfSteps++;

        // hier is der Switch ueber die derzeitige Konfiguration und darauf die
        // Entscheidung fuer die naechste konfiguration.

        if (curState == FactorialStateControl.Q0) {
            curState = handleQ0(fstTapeChar, sndTapeChar);
        } else if (curState == FactorialStateControl.Q1) {
            curState = handleQ1(fstTapeChar, sndTapeChar);
        } else if (curState == FactorialStateControl.Q2) {
            curState = handleQ2(fstTapeChar, sndTapeChar);
        } else if (curState == FactorialStateControl.Q3) {
            curState = handleQ3(fstTapeChar, sndTapeChar);
        } else if (curState == FactorialStateControl.Q4) {
            curState = handleQ4(fstTapeChar, sndTapeChar);
        } else if (curState == FactorialStateControl.Q5) {
            curState = handleQ5(fstTapeChar, sndTapeChar);
        } else if (curState == FactorialStateControl.Q6) {
            curState = handleQ6(fstTapeChar, sndTapeChar);
        } else if (curState == FactorialStateControl.Q7) {
            curState = handleQ7(fstTapeChar, sndTapeChar);
        } else if (curState == FactorialStateControl.Q8) {
            curState = handleQ8(fstTapeChar, sndTapeChar);
        } else if (curState == FactorialStateControl.MULTIPLICATION) {
            curState = handleBackFromMultiplication(fstTapeChar, sndTapeChar);
        } else {
            throw new IllegalStateException(curState + " existiert nicht!");
        }

        if (!startState.equals(curState)) {
            setChanged();
            notifyObservers();
        }
    }

    private String handleBackFromMultiplication(char fstTapeChar, char sndTapeChar) {
        if (firstRSH.read().charValue() == ZERO_CHAR) {
            firstRSH.write(EMPTY_VALUE);
            firstRSH.moveRight();

            return FactorialStateControl.Q5;
        } else {
            dumpTape1Exeption(FactorialStateControl.Q4, fstTapeChar);
            return null;
        }
    }

    private String handleQ8(char fstTapeChar, char sndTapeChar) {
        if (!(fstTapeChar == EMPTY_CHAR)) {
            dumpTape1Exeption(FactorialStateControl.Q8, fstTapeChar);
        }

        if (!(sndTapeChar == EMPTY_CHAR)) {
            dumpTape2Exeption(FactorialStateControl.Q8, sndTapeChar);
        }
        return FactorialStateControl.Q8;
    }

    private String handleQ7(char fstTapeChar, char sndTapeChar) {
        if (!(fstTapeChar == EMPTY_CHAR)) {
            dumpTape1Exeption(FactorialStateControl.Q7, fstTapeChar);
        }

        if (!(sndTapeChar == EMPTY_CHAR)) {
            dumpTape2Exeption(FactorialStateControl.Q7, sndTapeChar);
        }
        return FactorialStateControl.Q7;
    }

    private String handleQ6(char fstTapeChar, char sndTapeChar) {
        ReadWriteHead firstRSH = this.firstRSH;
        ReadWriteHead secondRSH = this.secondRSH;

        if (!(fstTapeChar == EMPTY_CHAR)) {
            dumpTape1Exeption(FactorialStateControl.Q6, fstTapeChar);
        }

        if (sndTapeChar == ZERO_CHAR) {
            firstRSH.write(ZERO_VALUE);
            firstRSH.moveRight();

            secondRSH.write(EMPTY_VALUE);
            secondRSH.moveLeft();

            return FactorialStateControl.Q6;
        } else if (sndTapeChar == ONE_CHAR) {

            secondRSH.write(EMPTY_VALUE);
            secondRSH.moveLeft();

            return FactorialStateControl.Q6;
        } else if (sndTapeChar == EMPTY_CHAR) {
            return FactorialStateControl.Q7;
        } else {
            dumpTape2Exeption(FactorialStateControl.Q6, sndTapeChar);
            return null;// FIXME richtige excpetion werfen? (runtime)
        }
    }

    private String handleQ5(char fstTapeChar, char sndTapeChar) {
        if (fstTapeChar == ZERO_CHAR) {
            firstRSH.moveRight();

            return FactorialStateControl.Q5;
        } else if (fstTapeChar == EMPTY_CHAR) {
            firstRSH.moveLeft();

            secondRSH.write(ONE_VALUE);
            secondRSH.moveRight();

            return FactorialStateControl.Q2;
        } else {
            dumpTape1Exeption(FactorialStateControl.Q5, fstTapeChar);
            return null;
        }
    }

    private String handleQ4(char fstTapeChar, char sndTapeChar) {
        ReadWriteHead firstRSH = this.firstRSH;
        ReadWriteHead secondRSH = this.secondRSH;

        if (!(fstTapeChar == EMPTY_CHAR)) {
            dumpTape1Exeption(FactorialStateControl.Q4, fstTapeChar);
        }

        if (sndTapeChar == ZERO_CHAR) {
            secondRSH.moveLeft();

            return FactorialStateControl.Q4;
        } else if (sndTapeChar == ONE_CHAR) {
            secondRSH.moveLeft();

            return FactorialStateControl.Q4;
        } else if (sndTapeChar == EMPTY_CHAR) {
            firstRSH.moveRight();

            // Auf erstes Zeichen stellen
            secondRSH.moveRight();

            multiplikation = new MultiplicationStateControl(secondRSH, thirdRSH, nuberOfSteps);
            multiplikation.addObserver(this.observer);
            this.observer.update(multiplikation, null);
            return MULTIPLICATION;
        } else {
            dumpTape2Exeption(FactorialStateControl.Q4, sndTapeChar);
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

            secondRSH.write(ONE_VALUE);
            secondRSH.moveLeft();

            return FactorialStateControl.Q4;
        } else {
            dumpTape1Exeption(FactorialStateControl.Q3, fstTapeChar);
            return null;
        }
    }

    private String handleQ2(char fstTapeChar, char sndTapeChar) {
        if (fstTapeChar == ZERO_CHAR) {
            firstRSH.moveLeft();

            secondRSH.write(ZERO_VALUE);
            secondRSH.moveRight();

            return FactorialStateControl.Q3;
        } else if (fstTapeChar == EMPTY_CHAR) {
            secondRSH.moveLeft();

            return FactorialStateControl.Q6;
        } else {
            dumpTape1Exeption(FactorialStateControl.Q2, fstTapeChar);
            return null;
        }
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
            dumpTape1Exeption(FactorialStateControl.Q1, fstTapeChar);
            return null;
        }
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

            return FactorialStateControl.Q8;
        } else {
            dumpTape1Exeption(FactorialStateControl.Q0, fstTapeChar);
            return null;
        }
    }

    private void dumpTape2Exeption(String zustand, char sndTapeChar) {
        System.err.println("Fakultät Zustand: " + zustand + " Ungültiger Buchstabe auf Band 2:" + sndTapeChar);
    }

    private void dumpTape1Exeption(String zustand, char fstTapeChar) {
        System.err.println("Fakultät Zustand: " + zustand + " Ungültiger Buchstabe auf Band 1:" + fstTapeChar);
    }

    int getFirstNumberAsInteger() {
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

    @Override
    public int getNumberOfSteps() {
        return nuberOfSteps;
    }

    @Override
    public String getCurrentState() {
        return this.curState;
    }

}
