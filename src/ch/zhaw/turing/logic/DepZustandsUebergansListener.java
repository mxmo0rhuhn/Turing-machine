package ch.zhaw.turing.logic;

/**
 * 
 * @author rethab
 * 
 */
public interface DepZustandsUebergansListener {

    /**
     * 
     * @param tapes alles, was darstellt werden soll
     */
    public void inNeuenZustandGewechselt(String zustand, boolean akzeptiert);

}