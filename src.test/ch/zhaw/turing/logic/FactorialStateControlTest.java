package ch.zhaw.turing.logic;

import org.junit.Assert;
import org.junit.Test;

/**
 * Testcases fuer die Multiplikation
 * 
 * @author Max Schrimpf
 */
public class FactorialStateControlTest {

    // < 1 Sekunde
    @Test
    public void afactorialOfZero() {
        FactorialStateControl curFactorialStateControl = new FactorialStateControl(0);

        curFactorialStateControl.doAllSteps();

        Assert.assertEquals(1, curFactorialStateControl.getFirstNumberAsInteger());
    }

    // < 1 Sekunde
    @Test
    public void bfactorialOfOne() {
        FactorialStateControl curFactorialStateControl = new FactorialStateControl(1);

        curFactorialStateControl.doAllSteps();

        Assert.assertEquals(1, curFactorialStateControl.getFirstNumberAsInteger());
    }

    // < 1 Sekunde
    @Test
    public void cfactorialOfTwo() {
        FactorialStateControl curFactorialStateControl = new FactorialStateControl(2);

        curFactorialStateControl.doAllSteps();

        Assert.assertEquals(2, curFactorialStateControl.getFirstNumberAsInteger());
    }

    // < 1 Sekunde
    @Test
    public void dfactorialOfThree() {
        FactorialStateControl curFactorialStateControl = new FactorialStateControl(3);

        curFactorialStateControl.doAllSteps();

        Assert.assertEquals(6, curFactorialStateControl.getFirstNumberAsInteger());
    }

    // < 1 Sekunde
    @Test
    public void efactorialOfFour() {
        FactorialStateControl curFactorialStateControl = new FactorialStateControl(4);

        curFactorialStateControl.doAllSteps();

        Assert.assertEquals(24, curFactorialStateControl.getFirstNumberAsInteger());
    }

    // ~1 Sekunde
    @Test
    public void ffactorialOfFive() {
        FactorialStateControl curFactorialStateControl = new FactorialStateControl(5);

        curFactorialStateControl.doAllSteps();

        Assert.assertEquals(120, curFactorialStateControl.getFirstNumberAsInteger());
    }

    // 17 Sekunden
    @Test
    public void gfactorialOfSix() {
        FactorialStateControl curFactorialStateControl = new FactorialStateControl(6);

        curFactorialStateControl.doAllSteps();

        Assert.assertEquals(720, curFactorialStateControl.getFirstNumberAsInteger());
    }

    // 713.137 Sekunden ( ~11.883 Minuten)
    // Reto: 8 Sekunden
    // Max... wieder auf 24sek hoch...
    @Test
    public void hfactorialOfSeven() {
        FactorialStateControl curFactorialStateControl = new FactorialStateControl(7);

        curFactorialStateControl.doAllSteps();

        Assert.assertEquals(5040, curFactorialStateControl.getFirstNumberAsInteger());

    }
//
    @Test
    public void ifactorialOfEight() {
        FactorialStateControl curFactorialStateControl = new FactorialStateControl(8);

        curFactorialStateControl.doAllSteps();

        Assert.assertEquals(40320, curFactorialStateControl.getFirstNumberAsInteger());
    }
//
//    @Test
//    public void ifactorialOfNine() {
//        FactorialStateControl curFactorialStateControl = new FactorialStateControl(9, this);
//
//        curFactorialStateControl.doAllSteps();
//
//        Assert.assertEquals(40320, curFactorialStateControl.getFirstNumberAsInteger());
//    }

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
