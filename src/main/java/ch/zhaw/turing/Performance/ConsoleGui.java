package ch.zhaw.turing.Performance;

/**
 *
 * @author Max Schrimpf
 */
public class ConsoleGui {

    public ConsoleGui()
    {
        new ConsoleFrame();
        afactorialOfZero();
        bfactorialOfOne();
        cfactorialOfTwo();
        dfactorialOfThree();
        efactorialOfFour();
        ffactorialOfFive();
        gfactorialOfSix();
        hfactorialOfSeven();
        ifactorialOfEight();
        jfactorialOfNine();
        theDeath();
    }

    public void afactorialOfZero() {
        P_FactorialStateControl curFactorialStateControl = new P_FactorialStateControl(0);
        
        long zstVorher;
        long zstNachher;

        zstVorher = System.currentTimeMillis();
        
        while (!curFactorialStateControl.acceptedState()) {
            curFactorialStateControl.doStep();
        }
        
        zstNachher = System.currentTimeMillis();

        display(0, curFactorialStateControl.getFirstNumberAsInteger(), curFactorialStateControl.getNumberOfSteps(), ((zstNachher- zstVorher)  /1000));
    }

    public void bfactorialOfOne() {
        P_FactorialStateControl curFactorialStateControl = new P_FactorialStateControl(1);
        
        long zstVorher;
        long zstNachher;

        zstVorher = System.currentTimeMillis();
        
        while (!curFactorialStateControl.acceptedState()) {
            curFactorialStateControl.doStep();
        }
        
        zstNachher = System.currentTimeMillis();
        
        display(1, curFactorialStateControl.getFirstNumberAsInteger(), curFactorialStateControl.getNumberOfSteps(), ((zstNachher- zstVorher)  /1000));
    }

    public void cfactorialOfTwo() {
        P_FactorialStateControl curFactorialStateControl = new P_FactorialStateControl(2);
        
        long zstVorher;
        long zstNachher;

        zstVorher = System.currentTimeMillis();
        
        while (!curFactorialStateControl.acceptedState()) {
            curFactorialStateControl.doStep();
        }
        
        zstNachher = System.currentTimeMillis();

        display(2, curFactorialStateControl.getFirstNumberAsInteger(), curFactorialStateControl.getNumberOfSteps(), ((zstNachher- zstVorher)  /1000));
    }
    
    public void dfactorialOfThree() {
        P_FactorialStateControl curFactorialStateControl = new P_FactorialStateControl(3);
        
        long zstVorher;
        long zstNachher;

        zstVorher = System.currentTimeMillis();
        
        while (!curFactorialStateControl.acceptedState()) {
            curFactorialStateControl.doStep();
        }
        
        zstNachher = System.currentTimeMillis();

        display(3, curFactorialStateControl.getFirstNumberAsInteger(), curFactorialStateControl.getNumberOfSteps(), ((zstNachher- zstVorher)  /1000));        
    }

    public void efactorialOfFour() {
        P_FactorialStateControl curFactorialStateControl = new P_FactorialStateControl(4);
        
        long zstVorher;
        long zstNachher;

        zstVorher = System.currentTimeMillis();
        
        while (!curFactorialStateControl.acceptedState()) {
            curFactorialStateControl.doStep();
        }
        
        zstNachher = System.currentTimeMillis();

        display(4, curFactorialStateControl.getFirstNumberAsInteger(), curFactorialStateControl.getNumberOfSteps(), ((zstNachher- zstVorher)  /1000));
    }

    public void ffactorialOfFive() {
        P_FactorialStateControl curFactorialStateControl = new P_FactorialStateControl(5);
        
        long zstVorher;
        long zstNachher;

        zstVorher = System.currentTimeMillis();
        
        while (!curFactorialStateControl.acceptedState()) {
            curFactorialStateControl.doStep();
        }

        zstNachher = System.currentTimeMillis();
        
        display(5, curFactorialStateControl.getFirstNumberAsInteger(), curFactorialStateControl.getNumberOfSteps(), ((zstNachher- zstVorher)  /1000));
    }

    public void gfactorialOfSix() {
        P_FactorialStateControl curFactorialStateControl = new P_FactorialStateControl(6);
        
        long zstVorher;
        long zstNachher;

        zstVorher = System.currentTimeMillis();
        
        while (!curFactorialStateControl.acceptedState()) {
            curFactorialStateControl.doStep();
        }

        zstNachher = System.currentTimeMillis();
        
        display(6, curFactorialStateControl.getFirstNumberAsInteger(), curFactorialStateControl.getNumberOfSteps(), ((zstNachher- zstVorher)  /1000));
    }
    
    public void hfactorialOfSeven() {
        P_FactorialStateControl curFactorialStateControl = new P_FactorialStateControl(7);
        
        long zstVorher;
        long zstNachher;

        zstVorher = System.currentTimeMillis();
        
        while (!curFactorialStateControl.acceptedState()) {
            curFactorialStateControl.doStep();
        }

        zstNachher = System.currentTimeMillis();
        
        display(7, curFactorialStateControl.getFirstNumberAsInteger(), curFactorialStateControl.getNumberOfSteps(), ((zstNachher- zstVorher)  /1000));
    }

    public void ifactorialOfEight() {
        P_FactorialStateControl curFactorialStateControl = new P_FactorialStateControl(8);
        
        long zstVorher;
        long zstNachher;

        zstVorher = System.currentTimeMillis();
        
        while (!curFactorialStateControl.acceptedState()) {
            curFactorialStateControl.doStep();
        }

        zstNachher = System.currentTimeMillis();
        
        display(8, curFactorialStateControl.getFirstNumberAsInteger(), curFactorialStateControl.getNumberOfSteps(), ((zstNachher- zstVorher)  /1000));
    }
    
    public void jfactorialOfNine() {
        P_FactorialStateControl curFactorialStateControl = new P_FactorialStateControl(9);
        
        long zstVorher;
        long zstNachher;

        zstVorher = System.currentTimeMillis();
        
        while (!curFactorialStateControl.acceptedState()) {
            curFactorialStateControl.doStep();
        }
        
        zstNachher = System.currentTimeMillis();
        
        display(9, curFactorialStateControl.getFirstNumberAsInteger(), curFactorialStateControl.getNumberOfSteps(), ((zstNachher- zstVorher)  /1000));
    }

    public void theDeath() {
        P_FactorialStateControl curFactorialStateControl = new P_FactorialStateControl(10);
        
        long zstVorher;
        long zstNachher;

        zstVorher = System.currentTimeMillis();
        
        while (!curFactorialStateControl.acceptedState()) {
            curFactorialStateControl.doStep();
        }
        
        zstNachher = System.currentTimeMillis();
        
        display(10, curFactorialStateControl.getFirstNumberAsInteger(), curFactorialStateControl.getNumberOfSteps(), ((zstNachher- zstVorher) /1000));
    }
    
     private void display(int number, int result, int steps, long timeInSec){
        System.out.println(number + "!: Ergebnis: " + result + " Schritte: " + steps + " Benötigte Zeit: " + timeInSec + " Sekunden");
    }
    
    public static void main(String[] args) {
        new ConsoleGui();
    }

}
