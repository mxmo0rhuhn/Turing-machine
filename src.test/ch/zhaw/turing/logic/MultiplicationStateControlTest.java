package ch.zhaw.turing.logic;


import org.junit.Assert;
import org.junit.Test;

/**
 * Testcases fuer die Multiplikation
 * 
 * @author Max Schrimpf
 */ 
public class MultiplicationStateControlTest {

    private static final int ITERATIONEN = 20;    
    
    @Test
    public void sollRichtigMultiplizieren() {
        for (int i = 0; i < ITERATIONEN; i++) {
            Assert.assertTrue(multiplikationKorrekt(i, i + 1));
            Assert.assertTrue(multiplikationKorrekt(i + 1, i));
            Assert.assertTrue(multiplikationKorrekt(i, i));
        }
    }
    
    private boolean multiplikationKorrekt(int a, int b) {
        MultiplicationStateControl curMultiplicationStateControl = new MultiplicationStateControl(a, b);
        curMultiplicationStateControl.doAllSteps();
        int result = curMultiplicationStateControl.getFirstNumberAsInteger();
        return result == a * b;
    }

    
}
