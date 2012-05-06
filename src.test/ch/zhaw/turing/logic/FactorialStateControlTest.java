package ch.zhaw.turing.logic;

import java.util.Observable;
import java.util.Observer;

import org.junit.Assert;
import org.junit.Test;

/**
 * Testcases fuer die Multiplikation
 * 
 * @author Max Schrimpf
 */
public class FactorialStateControlTest {

    private Observer DUMMY_OBSERVER = new Observer() {

        @Override
        public void update(Observable o, Object arg) {

        }

    };

    // < 1 Sekunde
    @Test
    public void afactorialOfZero() {
        FactorialStateControl curFactorialStateControl = new FactorialStateControl(0);
        curFactorialStateControl.addObserver(DUMMY_OBSERVER);

        while (!curFactorialStateControl.acceptedState()) {
            curFactorialStateControl.doStep();
        }

        Assert.assertEquals(1, curFactorialStateControl.getFirstNumberAsInteger());

        System.out.println("0!: " + curFactorialStateControl.getNumberOfSteps() + " Schritte");
    }

    // < 1 Sekunde
    @Test
    public void bfactorialOfOne() {
        FactorialStateControl curFactorialStateControl = new FactorialStateControl(1);
        curFactorialStateControl.addObserver(DUMMY_OBSERVER);
        while (!curFactorialStateControl.acceptedState()) {
            curFactorialStateControl.doStep();
        }
        Assert.assertEquals(1, curFactorialStateControl.getFirstNumberAsInteger());
        System.out.println("1!: " + curFactorialStateControl.getNumberOfSteps() + " Schritte");
    }

    // < 1 Sekunde
    @Test
    public void cfactorialOfTwo() {
        FactorialStateControl curFactorialStateControl = new FactorialStateControl(2);
        curFactorialStateControl.addObserver(DUMMY_OBSERVER);
        while (!curFactorialStateControl.acceptedState()) {
            curFactorialStateControl.doStep();
        }

        Assert.assertEquals(2, curFactorialStateControl.getFirstNumberAsInteger());

        System.out.println("2!: " + curFactorialStateControl.getNumberOfSteps() + " Schritte");}

    // < 1 Sekunde
    @Test
    public void dfactorialOfThree() {
        FactorialStateControl curFactorialStateControl = new FactorialStateControl(3);
        curFactorialStateControl.addObserver(DUMMY_OBSERVER);
        while (!curFactorialStateControl.acceptedState()) {
            curFactorialStateControl.doStep();
        }

        Assert.assertEquals(6, curFactorialStateControl.getFirstNumberAsInteger());

        System.out.println("3!: " + curFactorialStateControl.getNumberOfSteps() + " Schritte");
    }

    // < 1 Sekunde
    @Test
    public void efactorialOfFour() {
        FactorialStateControl curFactorialStateControl = new FactorialStateControl(4);
        curFactorialStateControl.addObserver(DUMMY_OBSERVER);
        while (!curFactorialStateControl.acceptedState()) {
            curFactorialStateControl.doStep();
        }

        Assert.assertEquals(24, curFactorialStateControl.getFirstNumberAsInteger());

        System.out.println("4!: " + curFactorialStateControl.getNumberOfSteps() + " Schritte");
    }

    // ~1 Sekunde
    @Test
    public void ffactorialOfFive() {
        FactorialStateControl curFactorialStateControl = new FactorialStateControl(5);
        curFactorialStateControl.addObserver(DUMMY_OBSERVER);
        while (!curFactorialStateControl.acceptedState()) {
            curFactorialStateControl.doStep();
        }

        Assert.assertEquals(120, curFactorialStateControl.getFirstNumberAsInteger());

        System.out.println("5!: " + curFactorialStateControl.getNumberOfSteps() + " Schritte");
    }

    // 17 Sekunden
    @Test
    public void gfactorialOfSix() {
        FactorialStateControl curFactorialStateControl = new FactorialStateControl(6);
        curFactorialStateControl.addObserver(DUMMY_OBSERVER);
        while (!curFactorialStateControl.acceptedState()) {
            curFactorialStateControl.doStep();
        }

        Assert.assertEquals(720, curFactorialStateControl.getFirstNumberAsInteger());

        System.out.println("6!: " + curFactorialStateControl.getNumberOfSteps() + " Schritte");
    }
    

    // 713.137 Sekunden ( ~11.883 Minuten)
    // Reto: 8 Sekunden
    // Max... wieder auf 24sek hoch...
    @Test
    public void hfactorialOfSeven() {
        FactorialStateControl curFactorialStateControl = new FactorialStateControl(7);
        curFactorialStateControl.addObserver(DUMMY_OBSERVER);
        while (!curFactorialStateControl.acceptedState()) {
            curFactorialStateControl.doStep();
        }
        Assert.assertEquals(5040, curFactorialStateControl.getFirstNumberAsInteger());

        System.out.println("7!: " + curFactorialStateControl.getNumberOfSteps() + " Schritte");
    }

    //
    @Test
    public void ifactorialOfEight() {
        FactorialStateControl curFactorialStateControl = new FactorialStateControl(8);
        curFactorialStateControl.addObserver(DUMMY_OBSERVER);
        while (!curFactorialStateControl.acceptedState()) {
            curFactorialStateControl.doStep();
        }

        Assert.assertEquals(40320, curFactorialStateControl.getFirstNumberAsInteger());

        System.out.println("8!: " + curFactorialStateControl.getNumberOfSteps() + " Schritte");
    }

    //
    // @Test
    // public void factorialOfTen() {
    // FactorialStateControl curFactorialStateControl = new
    // FactorialStateControl(10);
    //
    // curFactorialStateControl.doAllSteps();
    //
    // Assert.assertEquals(3628800,
    // curFactorialStateControl.getFirstNumberAsInteger());
    // }
}
