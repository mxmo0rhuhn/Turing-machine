package ch.zhaw.turing.logic;

/**
 * 
 * @author rethab
 * 
 */
public interface ZustandsUebergansListener {

    /**
     * 
     * @param tapes alles, was darstellt werden soll
     */
    public void inNeuenZustandGewechselt(String zustand, ReadWriteHead[] tapes);

}