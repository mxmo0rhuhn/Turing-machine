/*
 * Copyright (c) 2014 - Max Schrimpf
 *
 * Licensed under the EUPL, Version 1.1 or – as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 * https://joinup.ec.europa.eu/software/page/eupl5
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 */

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
