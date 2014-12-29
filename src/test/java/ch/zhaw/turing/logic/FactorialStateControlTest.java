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

import java.util.Observable;
import java.util.Observer;

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

        long zstVorher;
        long zstNachher;

        zstVorher = System.currentTimeMillis();

        while (!curFactorialStateControl.acceptedState()) {
            curFactorialStateControl.doStep();
        }

        zstNachher = System.currentTimeMillis();

        Assert.assertEquals(1, curFactorialStateControl.getFirstNumberAsInteger());
        display(0, curFactorialStateControl.getFirstNumberAsInteger(), curFactorialStateControl.getNumberOfSteps(), ((zstNachher - zstVorher) / 1000));
    }

    // < 1 Sekunde
    @Test
    public void bfactorialOfOne() {
        FactorialStateControl curFactorialStateControl = new FactorialStateControl(1);
        curFactorialStateControl.addObserver(DUMMY_OBSERVER);

        long zstVorher;
        long zstNachher;

        zstVorher = System.currentTimeMillis();

        while (!curFactorialStateControl.acceptedState()) {
            curFactorialStateControl.doStep();
        }

        zstNachher = System.currentTimeMillis();

        Assert.assertEquals(1, curFactorialStateControl.getFirstNumberAsInteger());
        display(1, curFactorialStateControl.getFirstNumberAsInteger(), curFactorialStateControl.getNumberOfSteps(), ((zstNachher - zstVorher) / 1000));
    }

    // < 1 Sekunde
    @Test
    public void cfactorialOfTwo() {
        FactorialStateControl curFactorialStateControl = new FactorialStateControl(2);
        curFactorialStateControl.addObserver(DUMMY_OBSERVER);

        long zstVorher;
        long zstNachher;

        zstVorher = System.currentTimeMillis();

        while (!curFactorialStateControl.acceptedState()) {
            curFactorialStateControl.doStep();
        }

        zstNachher = System.currentTimeMillis();

        Assert.assertEquals(2, curFactorialStateControl.getFirstNumberAsInteger());
        display(2, curFactorialStateControl.getFirstNumberAsInteger(), curFactorialStateControl.getNumberOfSteps(), ((zstNachher - zstVorher) / 1000));
    }

    // < 1 Sekunde
    @Test
    public void dfactorialOfThree() {
        FactorialStateControl curFactorialStateControl = new FactorialStateControl(3);
        curFactorialStateControl.addObserver(DUMMY_OBSERVER);

        long zstVorher;
        long zstNachher;

        zstVorher = System.currentTimeMillis();

        while (!curFactorialStateControl.acceptedState()) {
            curFactorialStateControl.doStep();
        }

        zstNachher = System.currentTimeMillis();

        Assert.assertEquals(6, curFactorialStateControl.getFirstNumberAsInteger());
        display(3, curFactorialStateControl.getFirstNumberAsInteger(), curFactorialStateControl.getNumberOfSteps(), ((zstNachher - zstVorher) / 1000));
    }

    // < 1 Sekunde
    @Test
    public void efactorialOfFour() {
        FactorialStateControl curFactorialStateControl = new FactorialStateControl(4);
        curFactorialStateControl.addObserver(DUMMY_OBSERVER);

        long zstVorher;
        long zstNachher;

        zstVorher = System.currentTimeMillis();

        while (!curFactorialStateControl.acceptedState()) {
            curFactorialStateControl.doStep();
        }

        zstNachher = System.currentTimeMillis();

        Assert.assertEquals(24, curFactorialStateControl.getFirstNumberAsInteger());
        display(4, curFactorialStateControl.getFirstNumberAsInteger(), curFactorialStateControl.getNumberOfSteps(), ((zstNachher - zstVorher) / 1000));
    }

    // ~1 Sekunde
    @Test
    public void ffactorialOfFive() {
        FactorialStateControl curFactorialStateControl = new FactorialStateControl(5);
        curFactorialStateControl.addObserver(DUMMY_OBSERVER);

        long zstVorher;
        long zstNachher;

        zstVorher = System.currentTimeMillis();

        while (!curFactorialStateControl.acceptedState()) {
            curFactorialStateControl.doStep();
        }

        zstNachher = System.currentTimeMillis();

        Assert.assertEquals(120, curFactorialStateControl.getFirstNumberAsInteger());
        display(5, curFactorialStateControl.getFirstNumberAsInteger(), curFactorialStateControl.getNumberOfSteps(), ((zstNachher - zstVorher) / 1000));
    }

    // 17 Sekunden
    @Test
    public void gfactorialOfSix() {
        FactorialStateControl curFactorialStateControl = new FactorialStateControl(6);
        curFactorialStateControl.addObserver(DUMMY_OBSERVER);

        long zstVorher;
        long zstNachher;

        zstVorher = System.currentTimeMillis();

        while (!curFactorialStateControl.acceptedState()) {
            curFactorialStateControl.doStep();
        }

        zstNachher = System.currentTimeMillis();

        Assert.assertEquals(720, curFactorialStateControl.getFirstNumberAsInteger());
        display(6, curFactorialStateControl.getFirstNumberAsInteger(), curFactorialStateControl.getNumberOfSteps(), ((zstNachher - zstVorher) / 1000));
    }


    // 713.137 Sekunden ( ~11.883 Minuten)
    // Reto: 8 Sekunden
    // Max... wieder auf 24sek hoch...
    @Test
    public void hfactorialOfSeven() {
        FactorialStateControl curFactorialStateControl = new FactorialStateControl(7);
        curFactorialStateControl.addObserver(DUMMY_OBSERVER);

        long zstVorher;
        long zstNachher;

        zstVorher = System.currentTimeMillis();

        while (!curFactorialStateControl.acceptedState()) {
            curFactorialStateControl.doStep();
        }

        zstNachher = System.currentTimeMillis();

        Assert.assertEquals(5040, curFactorialStateControl.getFirstNumberAsInteger());
        display(7, curFactorialStateControl.getFirstNumberAsInteger(), curFactorialStateControl.getNumberOfSteps(), ((zstNachher - zstVorher) / 1000));
    }

    //
    @Test
    public void ifactorialOfEight() {
        FactorialStateControl curFactorialStateControl = new FactorialStateControl(8);
        curFactorialStateControl.addObserver(DUMMY_OBSERVER);

        long zstVorher;
        long zstNachher;

        zstVorher = System.currentTimeMillis();

        while (!curFactorialStateControl.acceptedState()) {
            curFactorialStateControl.doStep();
        }

        zstNachher = System.currentTimeMillis();

        Assert.assertEquals(40320, curFactorialStateControl.getFirstNumberAsInteger());
        display(8, curFactorialStateControl.getFirstNumberAsInteger(), curFactorialStateControl.getNumberOfSteps(), ((zstNachher - zstVorher) / 1000));
    }

    @Test
    public void jfactorialOfNine() {
        FactorialStateControl curFactorialStateControl = new FactorialStateControl(9);
        curFactorialStateControl.addObserver(DUMMY_OBSERVER);

        long zstVorher;
        long zstNachher;

        zstVorher = System.currentTimeMillis();

        while (!curFactorialStateControl.acceptedState()) {
            curFactorialStateControl.doStep();
        }

        zstNachher = System.currentTimeMillis();

        Assert.assertEquals(362880, curFactorialStateControl.getFirstNumberAsInteger());
        display(9, curFactorialStateControl.getFirstNumberAsInteger(), curFactorialStateControl.getNumberOfSteps(), ((zstNachher - zstVorher) / 1000));
    }

    @Test
    public void theDeath() {
        FactorialStateControl curFactorialStateControl = new FactorialStateControl(10);
        curFactorialStateControl.addObserver(DUMMY_OBSERVER);

        long zstVorher;
        long zstNachher;

        zstVorher = System.currentTimeMillis();

        while (!curFactorialStateControl.acceptedState()) {
            curFactorialStateControl.doStep();
        }

        zstNachher = System.currentTimeMillis();

        Assert.assertEquals(3628800, curFactorialStateControl.getFirstNumberAsInteger());
        display(10, curFactorialStateControl.getFirstNumberAsInteger(), curFactorialStateControl.getNumberOfSteps(), ((zstNachher - zstVorher) / 1000));
    }

    private void display(int number, int result, int steps, long timeInSec) {
        System.out.println(number + "!: Ergebnis: " + result + " Schritte: " + steps + " Benötigte Zeit: " + timeInSec + " Sekunden");
    }
}
