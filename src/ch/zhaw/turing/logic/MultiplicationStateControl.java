package ch.zhaw.turing.logic;


public class MultiplicationStateControl {

    // Hier werden alle Zustände, die es gibt erfasst.
    public static final String q0 = "q0";
    public static final String q1 = "q1";
    public static final String q2 = "q2";
    public static final String q3 = "q3";
    public static final String q4 = "q4";
    public static final String q5 = "q5";
    public static final String q6 = "q6";
    public static final String q7 = "q7";
    public static final String q8 = "q8";
    public static final String q9 = "q9";
    public static final String q10 = "q10";

    private TwoTapeConfiguration curConfiguration;
    private String curState;
    private ReadWriteHead firstRSH;
    private ReadWriteHead secondRSH;

    /**
     * Erstellt eine neue Zustandssteuerung für die Multiplikation und
     * initialisiert das Band. Die Position des LS-Kopfes ist danach genau auf
     * dem ersten Zeichen der Eingabe. Die Lese-Schreibeköpfe können mitgegeben
     * werden.
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
    public MultiplicationStateControl(int multiplikator, int multiplikant, ReadWriteHead firstRSH,
            ReadWriteHead secondRSH) {
        this.firstRSH = firstRSH;
        this.secondRSH = secondRSH;

        for (int i = 0; i < multiplikator; i++) {
            firstRSH.write('0');
            firstRSH.moveRight();
        }

        // Trennzeichen der zwei Zahlen
        firstRSH.write('1');
        firstRSH.moveRight();

        for (int i = 0; i < multiplikant; i++) {
            firstRSH.write('0');
            firstRSH.moveRight();
        }

        // Endzeichen
        firstRSH.write('1');

        // wieder an die Ausgangsposition fahren
        do {
            firstRSH.moveLeft();
        } while (firstRSH.read() != 'B');

        firstRSH.moveRight();
        curState = MultiplicationStateControl.q0;
        curConfiguration = new TwoTapeConfiguration(curState, firstRSH.read(), secondRSH.read());
    }

    /**
     * Erstellt eine neue Zustandssteuerung für die Multiplikation und
     * initialisiert das Band. Die Position des LS-Kopfes ist danach genau vor
     * dem ersten Zeichen der Eingabe. Die Lese-Schreibeköpfe werden automatisch
     * erstellt.
     * 
     * @param multiplikator
     *            linke Zahl der Multiplikation.
     * @param multiplikant
     *            rechte Zahl der Multiplikation.
     */
    public MultiplicationStateControl(int multiplikator, int multiplikant) {
        this(multiplikator, multiplikant, new ReadWriteHead(), new ReadWriteHead());
    }

    public void doAllSteps()
    {
        while(curConfiguration.getCurState() != MultiplicationStateControl.q10)
        {
            doStep();
        }
        
        System.out.println("Ergebnis: " + getFirstNumberAsInteger());
    }
    
    public int getFirstNumberAsInteger() {
        int i = 0;
        
        firstRSH.moveLeft();
        
        while (firstRSH.read() != 'B'){
            firstRSH.moveLeft();
        }
        
        firstRSH.moveRight();
        
        while (firstRSH.read() == '0'){
            firstRSH.moveRight();
            i++;
        }
        
        return i;
    }

    public void doStep() {
        printCurrentState();

        // hier is der Switch ueber die derzeitige Konfiguration und darauf die
        // Entscheidung fuer die naechste konfiguration.

        if (curConfiguration.getCurState().equals(MultiplicationStateControl.q0)) {
            handleQ0();
        } else if (curConfiguration.getCurState().equals(MultiplicationStateControl.q1)) {
            handleQ1();
        } else if (curConfiguration.getCurState().equals(MultiplicationStateControl.q2)) {
            handleQ2();
        } else if (curConfiguration.getCurState().equals(MultiplicationStateControl.q3)) {
            handleQ3();
        } else if (curConfiguration.getCurState().equals(MultiplicationStateControl.q4)) {
            handleQ4();
        } else if (curConfiguration.getCurState().equals(MultiplicationStateControl.q5)) {
            handleQ5();
        } else if (curConfiguration.getCurState().equals(MultiplicationStateControl.q6)) {
            handleQ6();
        } else if (curConfiguration.getCurState().equals(MultiplicationStateControl.q7)) {
            handleQ7();
        } else if (curConfiguration.getCurState().equals(MultiplicationStateControl.q8)) {
            handleQ8();
        } else if (curConfiguration.getCurState().equals(MultiplicationStateControl.q9)) {
            handleQ9();
        } else if (curConfiguration.getCurState().equals(MultiplicationStateControl.q10)) {
            handleQ10();
        }

        curConfiguration = new TwoTapeConfiguration(curState, firstRSH.read(), secondRSH.read());
        printCurrentStateWithDirection();
    }

    private void handleQ10() {
        if (!(curConfiguration.getFirstTapeCharacter() == 'B')) {
            dumpTape1Exeption();
        }

        if (!(curConfiguration.getSecondTapeCharacter() == 'B')) {
            dumpTape2Exeption();
        }
    }

    private void handleQ9() {
        if (curConfiguration.getFirstTapeCharacter() == '0') {
            firstRSH.moveLeft();
         
            secondRSH.stay();
           
            curState = MultiplicationStateControl.q9;
        } else if (curConfiguration.getFirstTapeCharacter() == '1') {
            firstRSH.moveLeft();
    
            secondRSH.stay();
      
            curState = MultiplicationStateControl.q9;
        } else if (curConfiguration.getFirstTapeCharacter() == 'B') {
            secondRSH.moveRight();
      
            curState = MultiplicationStateControl.q7;
        } else {
            dumpTape1Exeption();
        }
        
        if (!(curConfiguration.getSecondTapeCharacter() == 'B')) {
            dumpTape2Exeption();
        }
    }

    private void handleQ8() {
        if (!(curConfiguration.getFirstTapeCharacter() == 'B')) {
            dumpTape1Exeption();
        }

        if (curConfiguration.getSecondTapeCharacter() == '0') {
            firstRSH.write('0');
            firstRSH.moveRight();

            secondRSH.write('B');
            secondRSH.moveLeft();
         
            curState = MultiplicationStateControl.q8;
        } else if (curConfiguration.getSecondTapeCharacter() == 'B') {
            firstRSH.stay();
            
            secondRSH.stay();
            
            curState = MultiplicationStateControl.q10;
        } else {
            dumpTape2Exeption();
        }

    }

    private void handleQ7() {
        if (curConfiguration.getFirstTapeCharacter() == '0') {
            firstRSH.write('B');
            firstRSH.moveRight();

            secondRSH.stay();
            
            curState = MultiplicationStateControl.q7;
        } else if (curConfiguration.getFirstTapeCharacter() == '1') {
            firstRSH.write('B');
            firstRSH.moveRight();
        
            secondRSH.stay();
            
            curState = MultiplicationStateControl.q7;
        } else if (curConfiguration.getFirstTapeCharacter() == 'B') {
            firstRSH.stay();
            
            secondRSH.moveLeft();
          
            curState = MultiplicationStateControl.q8;
        } else {
            dumpTape1Exeption();
        }

        if (!(curConfiguration.getSecondTapeCharacter() == 'B')) {
            dumpTape2Exeption();
        }
    }

    private void handleQ6() {
        if (curConfiguration.getFirstTapeCharacter() == '0') {
            firstRSH.moveLeft();
            
            secondRSH.stay();
            
            curState = MultiplicationStateControl.q6;
        } else if (curConfiguration.getFirstTapeCharacter() == '1') {
            firstRSH.moveRight();
       
            secondRSH.stay();
            
            curState = MultiplicationStateControl.q0;
        } else {
            dumpTape1Exeption();
        }

        if (!(curConfiguration.getSecondTapeCharacter() == 'B')) {
            dumpTape2Exeption();
        }
    }

    private void handleQ5() {
        if (curConfiguration.getFirstTapeCharacter() == '0') {
            firstRSH.moveLeft();
          
            secondRSH.stay();
            
            curState = MultiplicationStateControl.q6;
        } else if (curConfiguration.getFirstTapeCharacter() == '1') {
            firstRSH.moveLeft();
          
            secondRSH.stay();
            
            curState = MultiplicationStateControl.q5;
        } else if (curConfiguration.getFirstTapeCharacter() == 'B') {
            firstRSH.moveRight();
           
            secondRSH.stay();
            
            curState = MultiplicationStateControl.q7;
        } else {
            dumpTape1Exeption();
        }

        if (!(curConfiguration.getSecondTapeCharacter() == 'B')) {
            dumpTape2Exeption();
        }
    }

    private void handleQ4() {
        if (curConfiguration.getFirstTapeCharacter() == '0') {
            firstRSH.moveLeft();
          
            secondRSH.stay();
            
            curState = MultiplicationStateControl.q4;
        } else if (curConfiguration.getFirstTapeCharacter() == '1') {
            firstRSH.moveLeft();
          
            secondRSH.stay();
            
            curState = MultiplicationStateControl.q5;
        } else {
            dumpTape1Exeption();
        }

        if (!(curConfiguration.getSecondTapeCharacter() == 'B')) {
            dumpTape2Exeption();
        }
    }

    private void handleQ3() {
        if (curConfiguration.getFirstTapeCharacter() == '0') {
            firstRSH.moveRight();

            secondRSH.write('0');
            secondRSH.moveRight();

            curState = MultiplicationStateControl.q3;
        } else if (curConfiguration.getFirstTapeCharacter() == '1') {
            firstRSH.moveLeft();
         
            secondRSH.stay();
            
            curState = MultiplicationStateControl.q4;
        } else {
            dumpTape1Exeption();
        }

        if (!(curConfiguration.getSecondTapeCharacter() == 'B')) {
            dumpTape2Exeption();
        }
    }

    private void handleQ2() {
        if (curConfiguration.getFirstTapeCharacter() == '0') {
            firstRSH.moveRight();

            secondRSH.write('0');
            secondRSH.moveRight();

            curState = MultiplicationStateControl.q3;
        } else if (curConfiguration.getFirstTapeCharacter() == '1') {
            firstRSH.moveLeft();
            
            secondRSH.stay();
            
            curState = MultiplicationStateControl.q9;
        } else {
            dumpTape1Exeption();
        }

        if (!(curConfiguration.getSecondTapeCharacter() == 'B')) {
            dumpTape2Exeption();
        }
    }

    private void handleQ1() {
        if (curConfiguration.getFirstTapeCharacter() == '0') {
            firstRSH.moveRight();
          
            secondRSH.stay();
            
            curState = MultiplicationStateControl.q1;
        } else if (curConfiguration.getFirstTapeCharacter() == '1') {
            firstRSH.moveRight();
            
            secondRSH.stay();
            
            curState = MultiplicationStateControl.q2;
        } else {
            dumpTape1Exeption();
        }

        if (!(curConfiguration.getSecondTapeCharacter() == 'B')) {
            dumpTape2Exeption();
        }
    }

    private void handleQ0() {
        if (curConfiguration.getFirstTapeCharacter() == '0') {
            firstRSH.write('1');
            firstRSH.moveRight();
        
            secondRSH.stay();
            
            curState = MultiplicationStateControl.q1;
        } else if (curConfiguration.getFirstTapeCharacter() == '1') {
            firstRSH.stay();
            
            secondRSH.stay();
            
            curState = MultiplicationStateControl.q7;
        } else {
            dumpTape1Exeption();
        }

        if (!(curConfiguration.getSecondTapeCharacter() == 'B')) {
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

    public void printCurrentState() {
        System.out.print("d{" + curConfiguration.getCurState() + ", "
                + curConfiguration.getFirstTapeCharacter() + ", " + curConfiguration.getSecondTapeCharacter() + "}");
    }
    
    public void printCurrentStateWithDirection() {
        System.out.println(" |- d{" + curConfiguration.getCurState() + ", "
                + curConfiguration.getFirstTapeCharacter() + ", " + curConfiguration.getSecondTapeCharacter() + ", " + firstRSH.getLastDirection() + ", " + secondRSH.getLastDirection() + "}");
    }
}
