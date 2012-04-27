package ch.zhaw.turing.logic;


import org.junit.Assert;
import org.junit.Test;

/**
 * Testcases fuer die Multiplikation
 * 
 * @author Max Schrimpf
 */ 
public class MultiplicationStateControlTest {

    @Test
    public void zeroMultipliedByZero() {
        MultiplicationStateControl curMultiplicationStateControl = new MultiplicationStateControl(0, 0);
        
        curMultiplicationStateControl.doAllSteps();
        
        Assert.assertEquals(0, curMultiplicationStateControl.getFirstNumberAsInteger());
    }
    
    @Test
    public void zeroMultipliedByFive() {
        MultiplicationStateControl curMultiplicationStateControl = new MultiplicationStateControl(0, 5);
        
        curMultiplicationStateControl.doAllSteps();
        
        Assert.assertEquals(0, curMultiplicationStateControl.getFirstNumberAsInteger());
    }
    
    @Test
    public void fiveMultipliedByZero() {
        MultiplicationStateControl curMultiplicationStateControl = new MultiplicationStateControl(5, 0);
        
        curMultiplicationStateControl.doAllSteps();
        
        Assert.assertEquals(0, curMultiplicationStateControl.getFirstNumberAsInteger());
    }
    
    @Test
    public void fiveMultipliedByFive() {
        MultiplicationStateControl curMultiplicationStateControl = new MultiplicationStateControl(5, 5);
        
        curMultiplicationStateControl.doAllSteps();
        
        Assert.assertEquals(25, curMultiplicationStateControl.getFirstNumberAsInteger());
    }
}
