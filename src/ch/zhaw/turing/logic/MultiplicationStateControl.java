package ch.zhaw.turing.logic;

import static ch.zhaw.turing.logic.ReadWriteHead.EMPTY_CHAR;
import static ch.zhaw.turing.logic.ReadWriteHead.EMPTY_VALUE;
import static ch.zhaw.turing.logic.ReadWriteHead.ONE_CHAR;
import static ch.zhaw.turing.logic.ReadWriteHead.ONE_VALUE;
import static ch.zhaw.turing.logic.ReadWriteHead.ZERO_CHAR;
import static ch.zhaw.turing.logic.ReadWriteHead.ZERO_VALUE;

public class MultiplicationStateControl implements TuringMachine {

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

    private final ReadWriteHead firstRSH;
    private final ReadWriteHead secondRSH;
    
    private final ZustandsUebergansListener listener;
    
    /**
     * Erstellt eine neue Zustandssteuerung für die Multiplikation und initialisiert das Band. Die Position des
     * LS-Kopfes ist danach genau auf dem ersten Zeichen der Eingabe. Die Lese-Schreibeköpfe können mitgegeben erstellt.
     * 
     * @param multiplikator
     *            linke Zahl der Multiplikation.
     * @param multiplikant
     *            rechte Zahl der Multiplikation.
     */
    public MultiplicationStateControl(int multiplikator, int multiplikant, ZustandsUebergansListener listener) {
        this.firstRSH = new ReadWriteHead();
        this.secondRSH = new ReadWriteHead();
        
        this.listener = listener;
        
        setUpTape(multiplikator, multiplikant);
    }

    /**
     * Erstellt eine neue Zustandssteuerung für die Multiplikation die genau die mitgegebenen Bänder nutzt.
     * 
     * Die Position des LS-Kopfes des oberen Bandes muss dazu genau auf dem ersten Zeichen der Eingabe sein. Das Zweite
     * Band muss leer sein.
     * 
     * @param firstRSH
     *            der erste Lese-Schreibkopf der Maschine
     * @param secondRSH
     *            der zweite Lese-Schreibkopf der Maschine
     * 
     */
    public MultiplicationStateControl(ReadWriteHead firstRSH, ReadWriteHead secondRSH, ZustandsUebergansListener listener) {
        this.firstRSH = firstRSH;
        this.secondRSH = secondRSH;
        
        this.listener = listener;
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
     * Führt alle Schritte der Multiplikation aus. Am Ende der Berechnung steht der Lese- Schreibkopf des oberen Bandes
     * hinter der letzten Stelle der multiplizierten Zahl.
     */
    public void doAllSteps() {        
        ReadWriteHead[] tapes = new ReadWriteHead[]{this.firstRSH, this.secondRSH};
        
        Character fstTapeChar = tapes[0].read().charValue();
        Character sndTapeChar = tapes[1].read().charValue();

        String curState = MultiplicationStateControl.Q0;

        while (curState != MultiplicationStateControl.Q10) {
            curState = doStep(curState, fstTapeChar, sndTapeChar);
            
            this.listener.inNeuenZustandGewechselt(curState, tapes);
            
            fstTapeChar = tapes[0].read().charValue();
            sndTapeChar = tapes[1].read().charValue();
        }
    }

    public String doStep(String lastState, char fstTapeChar, char sndTapeChar) {
        // printCurrentState();

        // hier is der Switch ueber die derzeitige Konfiguration und darauf die
        // Entscheidung fuer die naechste konfiguration.

        String nextState;

        if (lastState == MultiplicationStateControl.Q0) {
            nextState = handleQ0(fstTapeChar, sndTapeChar);
        } else if (lastState == MultiplicationStateControl.Q1) {
            nextState = handleQ1(fstTapeChar, sndTapeChar);
        } else if (lastState == MultiplicationStateControl.Q2) {
            nextState = handleQ2(fstTapeChar, sndTapeChar);
        } else if (lastState == MultiplicationStateControl.Q3) {
            nextState = handleQ3(fstTapeChar, sndTapeChar);
        } else if (lastState == MultiplicationStateControl.Q4) {
            nextState = handleQ4(fstTapeChar, sndTapeChar);
        } else if (lastState == MultiplicationStateControl.Q5) {
            nextState = handleQ5(fstTapeChar, sndTapeChar);
        } else if (lastState == MultiplicationStateControl.Q6) {
            nextState = handleQ6(fstTapeChar, sndTapeChar);
        } else if (lastState == MultiplicationStateControl.Q7) {
            nextState = handleQ7(fstTapeChar, sndTapeChar);
        } else if (lastState == MultiplicationStateControl.Q8) {
            nextState = handleQ8(fstTapeChar, sndTapeChar);
        } else if (lastState == MultiplicationStateControl.Q9) {
            nextState = handleQ9(fstTapeChar, sndTapeChar);
        } else if (lastState == MultiplicationStateControl.Q10) {
            nextState = handleQ10(fstTapeChar, sndTapeChar);
        } else {
            throw new IllegalStateException(lastState + " existiert nicht");
        }

        // curConfiguration = new TwoTapeConfiguration(nextState, firstRSH.read(), secondRSH.read());
        // printCurrentStateWithDirection();

        return nextState;
    }

    private String handleQ10(char fstTapeChar, char sndTapeChar) {
        if (!(fstTapeChar == EMPTY_CHAR)) {
            dumpTape1Exeption(MultiplicationStateControl.Q10, fstTapeChar);
        }

        if (!(sndTapeChar == EMPTY_CHAR)) {
            dumpTape2Exeption(MultiplicationStateControl.Q10, sndTapeChar);
        }

        return MultiplicationStateControl.Q10;
    }

    private String handleQ9(char fstTapeChar, char sndTapeChar) {
        if (fstTapeChar == ZERO_CHAR) {
            firstRSH.moveLeft();

            secondRSH.stay();

            return MultiplicationStateControl.Q9;
        } else if (fstTapeChar == ONE_CHAR) {
            firstRSH.moveLeft();

            secondRSH.stay();

            return MultiplicationStateControl.Q9;
        } else if (fstTapeChar == EMPTY_CHAR) {
            secondRSH.moveRight();

            return MultiplicationStateControl.Q7;
        } else {
            dumpTape1Exeption(MultiplicationStateControl.Q9, fstTapeChar);
        }

        if (!(sndTapeChar == EMPTY_CHAR)) {
            dumpTape2Exeption(MultiplicationStateControl.Q9, sndTapeChar);
        }

        return null;
    }

    private String handleQ8(char fstTapeChar, char sndTapeChar) {
        if (!(fstTapeChar == EMPTY_CHAR)) {
            dumpTape1Exeption(MultiplicationStateControl.Q8, fstTapeChar);
        }

        if (sndTapeChar == ZERO_CHAR) {
            firstRSH.write(ZERO_VALUE);
            firstRSH.moveRight();

            secondRSH.write(EMPTY_VALUE);
            secondRSH.moveLeft();

            return MultiplicationStateControl.Q8;
        } else if (sndTapeChar == EMPTY_CHAR) {
            firstRSH.stay();

            secondRSH.stay();

            return MultiplicationStateControl.Q10;
        } else {
            dumpTape2Exeption(MultiplicationStateControl.Q8, sndTapeChar);
            return null;
        }
    }

    private String handleQ7(char fstTapeChar, char sndTapeChar) {
        if (fstTapeChar == ZERO_CHAR) {
            firstRSH.write(EMPTY_VALUE);
            firstRSH.moveRight();

            secondRSH.stay();

            return MultiplicationStateControl.Q7;
        } else if (fstTapeChar == ONE_CHAR) {
            firstRSH.write(EMPTY_VALUE);
            firstRSH.moveRight();

            secondRSH.stay();

            return MultiplicationStateControl.Q7;
        } else if (fstTapeChar == EMPTY_CHAR) {
            firstRSH.stay();

            secondRSH.moveLeft();

            return MultiplicationStateControl.Q8;
        } else {
            dumpTape1Exeption(MultiplicationStateControl.Q7, fstTapeChar);
            return null;
        }
    }

    private String handleQ6(char fstTapeChar, char sndTapeChar) {
        ReadWriteHead firstRSH = this.firstRSH;
        ReadWriteHead secondRSH = this.secondRSH;

        if (fstTapeChar == ZERO_CHAR) {
            firstRSH.moveLeft();

            secondRSH.stay();

            return MultiplicationStateControl.Q6;
        } else if (fstTapeChar == ONE_CHAR) {
            firstRSH.moveRight();

            secondRSH.stay();

            return MultiplicationStateControl.Q0;
        } else {
            dumpTape1Exeption(MultiplicationStateControl.Q6, fstTapeChar);
            return null;
        }
    }

    private String handleQ5(char fstTapeChar, char sndTapeChar) {
        if (fstTapeChar == ZERO_CHAR) {
            firstRSH.moveLeft();

            secondRSH.stay();

            return MultiplicationStateControl.Q6;
        } else if (fstTapeChar == ONE_CHAR) {
            firstRSH.moveLeft();

            secondRSH.stay();

            return MultiplicationStateControl.Q5;
        } else if (fstTapeChar == EMPTY_CHAR) {
            firstRSH.moveRight();

            secondRSH.stay();

            return MultiplicationStateControl.Q7;
        } else {
            dumpTape1Exeption(MultiplicationStateControl.Q5, fstTapeChar);
            return null;
        }
    }

    private String handleQ4(char fstTapeChar, char sndTapeChar) {
        if (fstTapeChar == ZERO_CHAR) {
            firstRSH.moveLeft();

            secondRSH.stay();

            return MultiplicationStateControl.Q4;
        } else if (fstTapeChar == ONE_CHAR) {
            firstRSH.moveLeft();

            secondRSH.stay();

            return MultiplicationStateControl.Q5;
        } else {
            dumpTape1Exeption(MultiplicationStateControl.Q4, fstTapeChar);
            return null;
        }
    }

    private String handleQ3(char fstTapeChar, char sndTapeChar) {
        if (fstTapeChar == ZERO_CHAR) {
            firstRSH.moveRight();

            secondRSH.write(ZERO_VALUE);
            secondRSH.moveRight();

            return MultiplicationStateControl.Q3;
        } else if (fstTapeChar == ONE_CHAR) {
            firstRSH.moveLeft();

            secondRSH.stay();

            return MultiplicationStateControl.Q4;
        } else {
            dumpTape1Exeption(MultiplicationStateControl.Q3, fstTapeChar);
            return null;
        }
    }

    private String handleQ2(char fstTapeChar, char sndTapeChar) {
        if (fstTapeChar == ZERO_CHAR) {
            firstRSH.moveRight();

            secondRSH.write(ZERO_VALUE);
            secondRSH.moveRight();

            return MultiplicationStateControl.Q3;
        } else if (fstTapeChar == ONE_CHAR) {
            firstRSH.moveLeft();

            secondRSH.stay();

            return MultiplicationStateControl.Q9;
        } else {
            dumpTape1Exeption(MultiplicationStateControl.Q2, fstTapeChar);
            return null;
        }
    }

    private String handleQ1(char fstTapeChar, char sndTapeChar) {
        ReadWriteHead firstRSH = this.firstRSH;
        ReadWriteHead secondRSH = this.secondRSH;

        if (fstTapeChar == ZERO_CHAR) {
            firstRSH.moveRight();

            secondRSH.stay();

            return MultiplicationStateControl.Q1;
        } else if (fstTapeChar == ONE_CHAR) {
            firstRSH.moveRight();

            secondRSH.stay();

            return MultiplicationStateControl.Q2;
        } else {
            dumpTape1Exeption(MultiplicationStateControl.Q1, fstTapeChar);
            return null;
        }
    }

    private String handleQ0(char fstTapeChar, char sndTapeChar) {
        if (fstTapeChar == ZERO_CHAR) {
            firstRSH.write(ONE_VALUE);
            firstRSH.moveRight();

            secondRSH.stay();

            return MultiplicationStateControl.Q1;
        } else if (fstTapeChar == ONE_CHAR) {
            firstRSH.stay();

            secondRSH.stay();

            return MultiplicationStateControl.Q7;
        } else {
            dumpTape1Exeption(MultiplicationStateControl.Q0, fstTapeChar);
            return null;
        }
    }

    private void dumpTape2Exeption(String zustand, char sndTapeChar) {
        System.err.println("Zustand: " + zustand + " Ungültiger Buchstabe auf Band 2:" + sndTapeChar);
    }

    private void dumpTape1Exeption(String zustand, char fstTapeChar) {
        System.err.println("Zustand: " + zustand + " Ungültiger Buchstabe auf Band 1:" + fstTapeChar);
    }

    /**
     * Kodiert die errechnete Zahl aus der unären Darstellung in die dezimale. Der Lese-Schreibkopf des ersten Bandes
     * steht nach dieser Operation hinter der letzten Ziffer der Zahl.
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
}
