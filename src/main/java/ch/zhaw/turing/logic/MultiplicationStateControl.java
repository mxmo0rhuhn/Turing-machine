/*
 * Copyright (c) 2014 - Max Schrimpf
 *
 * Licensed under the EUPL, Version 1.1 or – as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 * https://joinup.ec.europa.eu/software/page/eupl5
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 */

package ch.zhaw.turing.logic;

import static ch.zhaw.turing.logic.ReadWriteHead.*;

public class MultiplicationStateControl extends TuringMachine {

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

    private String curState;

    private int nuberOfSteps;

    private final ReadWriteHead firstRSH;
    private final ReadWriteHead secondRSH;

    /**
     * Erstellt eine neue Zustandssteuerung für die Multiplikation und
     * initialisiert das Band. Die Position des LS-Kopfes ist danach genau auf
     * dem ersten Zeichen der Eingabe. Die Lese-Schreibeköpfe können mitgegeben
     * erstellt.
     *
     * @param multiplikator linke Zahl der Multiplikation.
     * @param multiplikant  rechte Zahl der Multiplikation.
     */
    public MultiplicationStateControl(int multiplikator, int multiplikant) {
        this.firstRSH = new ReadWriteHead();
        this.secondRSH = new ReadWriteHead();

        setUpTape(multiplikator, multiplikant);
    }

    /**
     * Erstellt eine neue Zustandssteuerung für die Multiplikation die genau die
     * mitgegebenen Bänder nutzt.
     * <p/>
     * Die Position des LS-Kopfes des oberen Bandes muss dazu genau auf dem
     * ersten Zeichen der Eingabe sein. Das Zweite Band muss leer sein.
     *
     * @param firstRSH  der erste Lese-Schreibkopf der Maschine
     * @param secondRSH der zweite Lese-Schreibkopf der Maschine
     */
    public MultiplicationStateControl(ReadWriteHead firstRSH, ReadWriteHead secondRSH, int nuberOfSteps) {
        this.firstRSH = firstRSH;
        this.secondRSH = secondRSH;

        this.nuberOfSteps = nuberOfSteps;

        curState = MultiplicationStateControl.Q0;
    }

    public MultiplicationStateControl(int multiplikator, int multiplikant, ReadWriteHead firstRSH,
                                      ReadWriteHead secondRSH) {
        this.firstRSH = firstRSH;
        this.secondRSH = secondRSH;

        setUpTape(multiplikator, multiplikant);
    }

    /**
     * @param multiplikator
     * @param multiplikant
     * @param firstRSH
     */
    private void setUpTape(int multiplikator, int multiplikant) {
        curState = MultiplicationStateControl.Q0;

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
     * Führt alle Schritte der Multiplikation aus. Am Ende der Berechnung steht
     * der Lese- Schreibkopf des oberen Bandes hinter der letzten Stelle der
     * multiplizierten Zahl.
     */
    public void doAllSteps() {

        while (!acceptedState()) {
            doStep();
        }
    }

    @Override
    public boolean acceptedState() {
        return curState == MultiplicationStateControl.Q10;
    }

    @Override
    public void doStep() {
        String startState = curState;

        char fstTapeChar = firstRSH.read().charValue();
        char sndTapeChar = secondRSH.read().charValue();

        nuberOfSteps++;

        if (curState == MultiplicationStateControl.Q0) {
            curState = handleQ0(fstTapeChar, sndTapeChar);
        } else if (curState == MultiplicationStateControl.Q1) {
            curState = handleQ1(fstTapeChar, sndTapeChar);
        } else if (curState == MultiplicationStateControl.Q2) {
            curState = handleQ2(fstTapeChar, sndTapeChar);
        } else if (curState == MultiplicationStateControl.Q3) {
            curState = handleQ3(fstTapeChar, sndTapeChar);
        } else if (curState == MultiplicationStateControl.Q4) {
            curState = handleQ4(fstTapeChar, sndTapeChar);
        } else if (curState == MultiplicationStateControl.Q5) {
            curState = handleQ5(fstTapeChar, sndTapeChar);
        } else if (curState == MultiplicationStateControl.Q6) {
            curState = handleQ6(fstTapeChar, sndTapeChar);
        } else if (curState == MultiplicationStateControl.Q7) {
            curState = handleQ7(fstTapeChar, sndTapeChar);
        } else if (curState == MultiplicationStateControl.Q8) {
            curState = handleQ8(fstTapeChar, sndTapeChar);
        } else if (curState == MultiplicationStateControl.Q9) {
            curState = handleQ9(fstTapeChar, sndTapeChar);
        } else if (curState == MultiplicationStateControl.Q10) {
            curState = handleQ10(fstTapeChar, sndTapeChar);
        } else {
            throw new IllegalStateException(curState + " existiert nicht");
        }

        if (!startState.equals(curState)) {
            setChanged();
            notifyObservers();
        }
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

            return MultiplicationStateControl.Q9;
        } else if (fstTapeChar == ONE_CHAR) {
            firstRSH.moveLeft();

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

            return MultiplicationStateControl.Q7;
        } else if (fstTapeChar == ONE_CHAR) {
            firstRSH.write(EMPTY_VALUE);
            firstRSH.moveRight();

            return MultiplicationStateControl.Q7;
        } else if (fstTapeChar == EMPTY_CHAR) {
            secondRSH.moveLeft();

            return MultiplicationStateControl.Q8;
        } else {
            dumpTape1Exeption(MultiplicationStateControl.Q7, fstTapeChar);
            return null;
        }
    }

    private String handleQ6(char fstTapeChar, char sndTapeChar) {
        ReadWriteHead firstRSH = this.firstRSH;

        if (fstTapeChar == ZERO_CHAR) {
            firstRSH.moveLeft();

            return MultiplicationStateControl.Q6;
        } else if (fstTapeChar == ONE_CHAR) {
            firstRSH.moveRight();

            return MultiplicationStateControl.Q0;
        } else {
            dumpTape1Exeption(MultiplicationStateControl.Q6, fstTapeChar);
            return null;
        }
    }

    private String handleQ5(char fstTapeChar, char sndTapeChar) {
        if (fstTapeChar == ZERO_CHAR) {
            firstRSH.moveLeft();

            return MultiplicationStateControl.Q6;
        } else if (fstTapeChar == ONE_CHAR) {
            firstRSH.moveLeft();

            return MultiplicationStateControl.Q5;
        } else if (fstTapeChar == EMPTY_CHAR) {
            firstRSH.moveRight();

            return MultiplicationStateControl.Q7;
        } else {
            dumpTape1Exeption(MultiplicationStateControl.Q5, fstTapeChar);
            return null;
        }
    }

    private String handleQ4(char fstTapeChar, char sndTapeChar) {
        if (fstTapeChar == ZERO_CHAR) {
            firstRSH.moveLeft();

            return MultiplicationStateControl.Q4;
        } else if (fstTapeChar == ONE_CHAR) {
            firstRSH.moveLeft();

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

            return MultiplicationStateControl.Q9;
        } else {
            dumpTape1Exeption(MultiplicationStateControl.Q2, fstTapeChar);
            return null;
        }
    }

    private String handleQ1(char fstTapeChar, char sndTapeChar) {
        ReadWriteHead firstRSH = this.firstRSH;

        if (fstTapeChar == ZERO_CHAR) {
            firstRSH.moveRight();

            return MultiplicationStateControl.Q1;
        } else if (fstTapeChar == ONE_CHAR) {
            firstRSH.moveRight();

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

            return MultiplicationStateControl.Q1;
        } else if (fstTapeChar == ONE_CHAR) {

            return MultiplicationStateControl.Q7;
        } else {
            dumpTape1Exeption(MultiplicationStateControl.Q0, fstTapeChar);
            return null;
        }
    }

    private void dumpTape2Exeption(String zustand, char sndTapeChar) {
        System.err.println("Multiplikation Zustand: " + zustand + " Ungültiger Buchstabe auf Band 2:" + sndTapeChar);
    }

    private void dumpTape1Exeption(String zustand, char fstTapeChar) {
        System.err.println("Multiplikation Zustand: " + zustand + " Ungültiger Buchstabe auf Band 1:" + fstTapeChar);
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
