package ch.zhaw.turing.logic;

import java.util.Observable;


public abstract class TuringMachine extends Observable {

    public abstract void doStep();

    public abstract boolean acceptedState();

    public abstract int getNumberOfSteps();

    public abstract String getCurrentState();
}
