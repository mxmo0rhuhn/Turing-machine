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
    public void factorialOfZero() {
        FactorialStateControl curFactorialStateControl = new FactorialStateControl(0);

        curFactorialStateControl.doAllSteps();

        Assert.assertEquals(1, curFactorialStateControl.getFirstNumberAsInteger());
    }

    // < 1 Sekunde
    @Test
    public void factorialOfOne() {
        FactorialStateControl curFactorialStateControl = new FactorialStateControl(1);

        curFactorialStateControl.doAllSteps();

        Assert.assertEquals(1, curFactorialStateControl.getFirstNumberAsInteger());
    }

    // < 1 Sekunde
    @Test
    public void factorialOfTwo() {
        FactorialStateControl curFactorialStateControl = new FactorialStateControl(2);

        curFactorialStateControl.doAllSteps();

        Assert.assertEquals(2, curFactorialStateControl.getFirstNumberAsInteger());
    }

    // < 1 Sekunde
    @Test
    public void factorialOfThree() {
        FactorialStateControl curFactorialStateControl = new FactorialStateControl(3);

        curFactorialStateControl.doAllSteps();

        Assert.assertEquals(6, curFactorialStateControl.getFirstNumberAsInteger());
    }

    // < 1 Sekunde
    @Test
    public void factorialOfFour() {
        FactorialStateControl curFactorialStateControl = new FactorialStateControl(4);

        curFactorialStateControl.doAllSteps();

        Assert.assertEquals(24, curFactorialStateControl.getFirstNumberAsInteger());
    }

    // ~1 Sekunde
    @Test
    public void factorialOfFive() {
        FactorialStateControl curFactorialStateControl = new FactorialStateControl(5);

        curFactorialStateControl.doAllSteps();

        Assert.assertEquals(120, curFactorialStateControl.getFirstNumberAsInteger());
    }

    // 17 Sekunden
    @Test
    public void factorialOfSix() {
        FactorialStateControl curFactorialStateControl = new FactorialStateControl(6);

        curFactorialStateControl.doAllSteps();

        Assert.assertEquals(720, curFactorialStateControl.getFirstNumberAsInteger());
    }

    // 713.137 Sekunden ( ~11.883 Minuten)
    // @Test
    // public void factorialOfSeven() {
    // FactorialStateControl curFactorialStateControl = new
    // FactorialStateControl(7);
    //
    // curFactorialStateControl.doAllSteps();
    //
    // Assert.assertEquals(5040,
    // curFactorialStateControl.getFirstNumberAsInteger());
    // }
    //
    // > 8h
//     @Test
//     public void factorialOfEight() {
//     FactorialStateControl curFactorialStateControl = new
//     FactorialStateControl(8);
//    
//     curFactorialStateControl.doAllSteps();
//    
//     Assert.assertEquals(40320,
//     curFactorialStateControl.getFirstNumberAsInteger());
//     }
    //
    // oo +1
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
