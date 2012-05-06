package ch.zhaw.turing.logic;

import java.util.Observer;


public interface TuringMachine {
    
    public void doStep();
    
    public boolean acceptedState();
    
    public int getNumberOfSteps();
    
    public void addObserver(Observer o);
}
