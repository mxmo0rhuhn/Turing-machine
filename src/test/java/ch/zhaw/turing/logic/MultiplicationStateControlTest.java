package ch.zhaw.turing.logic;

import org.junit.Assert;
import org.junit.Test;

/**
 * Testcases fuer die Multiplikation
 *
 * @author Max Schrimpf
 */
public class MultiplicationStateControlTest {

    private static final boolean debug = false;

    private static final int ITERATIONEN = 100;

    @Test
    public void sollRichtigMultiplizieren() throws InterruptedException {
        for (int i = 0; i < ITERATIONEN; i++) {
            Assert.assertTrue(multiplikationKorrekt(i, i + 1));
            if (debug) {
                System.out.printf("TEST: %d * %d korrekt\n", i, i + 1);
            }
            Assert.assertTrue(multiplikationKorrekt(i + 1, i));
            if (debug) {
                System.out.printf("TEST: %d * %d korrekt\n", i + 1, i);
            }
            Assert.assertTrue(multiplikationKorrekt(i, i));
            if (debug) {
                System.out.printf("TEST: %d * %d korrekt\n", i, i);
            }
        }
    }

    private boolean multiplikationKorrekt(int a, int b) {
        MultiplicationStateControl curMultiplicationStateControl = new MultiplicationStateControl(a, b);
        curMultiplicationStateControl.doAllSteps();
        int result = curMultiplicationStateControl.getFirstNumberAsInteger();
        return result == a * b;
    }
}
