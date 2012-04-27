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
    public void oneMultipliedByOne() {
        MultiplicationStateControl curMultiplicationStateControl = new MultiplicationStateControl(1, 1);
        
        curMultiplicationStateControl.doAllSteps();
        
        Assert.assertEquals(1, curMultiplicationStateControl.getFirstNumberAsInteger());
    }
    
    @Test
    public void twoMultipliedByTwo() {
        MultiplicationStateControl curMultiplicationStateControl = new MultiplicationStateControl(2, 2);
        
        curMultiplicationStateControl.doAllSteps();
        
        Assert.assertEquals(4, curMultiplicationStateControl.getFirstNumberAsInteger());
    }    
    
    @Test
    public void fiveMultipliedByTwo() {
        MultiplicationStateControl curMultiplicationStateControl = new MultiplicationStateControl(5, 2);
        
        curMultiplicationStateControl.doAllSteps();
        
        Assert.assertEquals(10, curMultiplicationStateControl.getFirstNumberAsInteger());
    }    

    @Test
    public void twoMultipliedByFive() {
        MultiplicationStateControl curMultiplicationStateControl = new MultiplicationStateControl(2, 5);
        
        curMultiplicationStateControl.doAllSteps();
        
        Assert.assertEquals(10, curMultiplicationStateControl.getFirstNumberAsInteger());
    }    

    @Test
    public void fiveMultipliedByFive() {
        MultiplicationStateControl curMultiplicationStateControl = new MultiplicationStateControl(5, 5);
        
        curMultiplicationStateControl.doAllSteps();
        
        Assert.assertEquals(25, curMultiplicationStateControl.getFirstNumberAsInteger());
    }
}
