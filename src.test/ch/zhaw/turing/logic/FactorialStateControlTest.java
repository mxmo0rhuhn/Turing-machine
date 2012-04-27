package ch.zhaw.turing.logic;


import org.junit.Assert;
import org.junit.Test;

/**
 * Testcases fuer die Multiplikation
 * 
 * @author Max Schrimpf
 */ 
public class FactorialStateControlTest {

    @Test
    public void factorialOfZero() {
        FactorialStateControl curFactorialStateControl = new FactorialStateControl(0);
        
        curFactorialStateControl.doAllSteps();
        
        Assert.assertEquals(1, curFactorialStateControl.getFirstNumberAsInteger());
    }

    @Test
    public void factorialOfOne() {
        FactorialStateControl curFactorialStateControl = new FactorialStateControl(1);
        
        curFactorialStateControl.doAllSteps();
        
        Assert.assertEquals(1, curFactorialStateControl.getFirstNumberAsInteger());
    }
    @Test
    public void factorialOfTwo() {
        FactorialStateControl curFactorialStateControl = new FactorialStateControl(2);
        
        curFactorialStateControl.doAllSteps();
        
        Assert.assertEquals(2, curFactorialStateControl.getFirstNumberAsInteger());
    }
    
    @Test
    public void factorialOfThree() {
        FactorialStateControl curFactorialStateControl = new FactorialStateControl(3);
        
        curFactorialStateControl.doAllSteps();
        
        Assert.assertEquals(6, curFactorialStateControl.getFirstNumberAsInteger());
    }
    
    @Test
    public void factorialOfFour() {
        FactorialStateControl curFactorialStateControl = new FactorialStateControl(4);
        
        curFactorialStateControl.doAllSteps();
        
        Assert.assertEquals(24, curFactorialStateControl.getFirstNumberAsInteger());
    }
}
