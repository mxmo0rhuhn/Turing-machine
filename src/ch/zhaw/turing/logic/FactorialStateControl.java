package ch.zhaw.turing.logic;


public class FactorialStateControl {

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

    private TwoTapeConfiguration curConfiguration;
    private String curState;
    private ReadWriteHead firstRSH;
    private ReadWriteHead secondRSH;
    private ReadWriteHead thirdRSH;

    /**
     * Erstellt eine neue Zustandssteuerung für die Fakultätsberechnung und
     * initialisiert das Band. Die Position des LS-Kopfes ist danach genau auf
     * dem ersten Zeichen der Eingabe. 
     *
     * @param number
     *            die Zahl deren Fakultät berechnet werden soll.
     * 
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

        // wieder an die Ausgangsposition fahren
        do {
            firstRSH.moveLeft();
        } while (firstRSH.read() != 'B');

        firstRSH.moveRight();
        curState = FactorialStateControl.q0;
        curConfiguration = new TwoTapeConfiguration(curState, firstRSH.read(), secondRSH.read());
    }

     public void doAllSteps()
    {
         
        while((curConfiguration.getCurState() != FactorialStateControl.q8) && (curConfiguration.getCurState() != FactorialStateControl.q7) )
        {
            doStep();
        }
        
        System.out.println("Ergebnis: " + getFirstNumberAsInteger());
    }
    
     /**
      * Kodiert die errechnete Zahl aus der unären Darstellung in die dezimale.
      * Der Lese-Schreibkopf des ersten Bandes steht nach dieser Operation hinter der letzten Ziffer der Zahl.
      * Diese Position wird auch als Ausgangspunkt für die Umwandelung angesehen.
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

     public void doStep() {
         printCurrentState();

         // hier is der Switch ueber die derzeitige Konfiguration und darauf die
         // Entscheidung fuer die naechste konfiguration.

         if (curConfiguration.getCurState().equals(FactorialStateControl.q0)) {
             handleQ0();
         } else if (curConfiguration.getCurState().equals(FactorialStateControl.q1)) {
             handleQ1();
         } else if (curConfiguration.getCurState().equals(FactorialStateControl.q2)) {
             handleQ2();
         } else if (curConfiguration.getCurState().equals(FactorialStateControl.q3)) {
             handleQ3();
         } else if (curConfiguration.getCurState().equals(FactorialStateControl.q4)) {
             handleQ4();
         } else if (curConfiguration.getCurState().equals(FactorialStateControl.q5)) {
             handleQ5();
         } else if (curConfiguration.getCurState().equals(FactorialStateControl.q6)) {
             handleQ6();
         } else if (curConfiguration.getCurState().equals(FactorialStateControl.q7)) {
             handleQ7();
         } else if (curConfiguration.getCurState().equals(FactorialStateControl.q8)) {
             handleQ8();
         }
         
         curConfiguration = new TwoTapeConfiguration(curState, firstRSH.read(), secondRSH.read());
         printCurrentStateWithDirection();
     }

     private void handleQ8() {
         if (!(curConfiguration.getFirstTapeCharacter() == 'B')) {
             dumpTape1Exeption();
         }

         if (!(curConfiguration.getSecondTapeCharacter() == 'B')) {
             dumpTape2Exeption();
         }
     }

     private void handleQ7() {
         if (!(curConfiguration.getFirstTapeCharacter() == 'B')) {
             dumpTape1Exeption();
         }

         if (!(curConfiguration.getSecondTapeCharacter() == 'B')) {
             dumpTape2Exeption();
         }
     }

     private void handleQ6() {
         if (!(curConfiguration.getFirstTapeCharacter() == 'B')) {
             dumpTape1Exeption();
         }

         if (curConfiguration.getSecondTapeCharacter() == '0') {
             firstRSH.write('0');
             firstRSH.moveRight();

             secondRSH.write('B');
             secondRSH.moveLeft();

             curState = FactorialStateControl.q6;
         } else if (curConfiguration.getSecondTapeCharacter() == '1') {
             firstRSH.stay();

             secondRSH.write('B');
             secondRSH.moveLeft();

             curState = FactorialStateControl.q6;
         } else if (curConfiguration.getSecondTapeCharacter() == 'B') {
             firstRSH.stay();

             secondRSH.stay();

             curState = FactorialStateControl.q7;
         } else {
             dumpTape2Exeption();
         }
     }

     private void handleQ5() {
         if (curConfiguration.getFirstTapeCharacter() == '0') {
             firstRSH.moveRight();

             secondRSH.stay();

             curState = FactorialStateControl.q5;
         } else if (curConfiguration.getFirstTapeCharacter() == 'B') {
             firstRSH.moveLeft();

             secondRSH.write('1');
             secondRSH.moveRight();

             curState = FactorialStateControl.q2;
         } else {
             dumpTape1Exeption();
         }

         if (!(curConfiguration.getSecondTapeCharacter() == 'B')) {
             dumpTape2Exeption();
         }
     }

     private void handleQ4() {

         if (!(curConfiguration.getFirstTapeCharacter() == 'B')) {
             dumpTape1Exeption();
         }
         
         if (curConfiguration.getSecondTapeCharacter() == '0') {
             firstRSH.stay();

             secondRSH.moveLeft();

             curState = FactorialStateControl.q4;
         } else if (curConfiguration.getSecondTapeCharacter() == '1') {
             firstRSH.stay();

             secondRSH.moveLeft();

             curState = FactorialStateControl.q4;
         } else if (curConfiguration.getSecondTapeCharacter() == 'B') {
             firstRSH.moveRight();

             // Auf erstes Zeichen stellen
             secondRSH.moveRight();

             MultiplicationStateControl myMultiplicationStateControl = new MultiplicationStateControl(secondRSH, thirdRSH);
             myMultiplicationStateControl.doAllSteps();

             curConfiguration = new TwoTapeConfiguration(curState, firstRSH.read(), secondRSH.read());
             
             if (curConfiguration.getFirstTapeCharacter() == '0') {
                 firstRSH.write('B');
                 firstRSH.moveRight();

                 secondRSH.stay();

                 curState = FactorialStateControl.q5;
             } else {
                 dumpTape1Exeption();
             }
             
             if (!(curConfiguration.getSecondTapeCharacter() == 'B')) {
                 dumpTape2Exeption();
             }
             
         } else {
             dumpTape2Exeption();
         }
     }

    private void handleQ3() {
        if (curConfiguration.getFirstTapeCharacter() == '0') {
            firstRSH.moveLeft();
            
            secondRSH.write('0');
            secondRSH.moveRight();

            curState = FactorialStateControl.q3;
        } else if (curConfiguration.getFirstTapeCharacter() == 'B') {
            firstRSH.stay();
         
            secondRSH.write('1');
            secondRSH.moveLeft();
            
            curState = FactorialStateControl.q4;
        } else {
            dumpTape1Exeption();
        }

        if (!(curConfiguration.getSecondTapeCharacter() == 'B')) {
            dumpTape2Exeption();
        }
    }

    private void handleQ2() {
        if (curConfiguration.getFirstTapeCharacter() == '0') {
            firstRSH.moveLeft();

            secondRSH.write('0');
            secondRSH.moveRight();

            curState = FactorialStateControl.q3;
        } else if (curConfiguration.getFirstTapeCharacter() == 'B') {
            firstRSH.stay();
            
            secondRSH.moveLeft();
            
            curState = FactorialStateControl.q6;
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
          
            secondRSH.write('0');
            secondRSH.moveRight();
            
            curState = FactorialStateControl.q1;
        } else if (curConfiguration.getFirstTapeCharacter() == '1') {
            firstRSH.write('B');
            firstRSH.moveLeft();
            
            secondRSH.write('1');
            secondRSH.moveRight();
            
            curState = FactorialStateControl.q2;
        } else {
            dumpTape1Exeption();
        }

        if (!(curConfiguration.getSecondTapeCharacter() == 'B')) {
            dumpTape2Exeption();
        }
    }

    private void handleQ0() {
        if (curConfiguration.getFirstTapeCharacter() == '0') {
            firstRSH.write('B');
            firstRSH.moveRight();
        
            secondRSH.write('0');
            secondRSH.moveRight();
            
            curState = FactorialStateControl.q1;
        } else if (curConfiguration.getFirstTapeCharacter() == '1') {
            firstRSH.write('0');
            firstRSH.stay();
            
            secondRSH.stay();
            
            curState = FactorialStateControl.q8;
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
