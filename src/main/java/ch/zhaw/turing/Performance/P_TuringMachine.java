package ch.zhaw.turing.Performance;

public abstract class P_TuringMachine {
    
    public abstract void doStep();
    
    public abstract boolean acceptedState();
    
    public abstract int getNumberOfSteps();
    
    public abstract String getCurrentState();
}
