package ch.zhaw.turing.logic;

/**
 * Ist das Überhaupt notwendig?
 *
 * @author Max Schrimpf
 */
public class TwoTapeConfiguration {

    private final String curState;
    private final Character firstTapeCharacter;
    private final Character secondTapeCharacter;

    public TwoTapeConfiguration(String curState, Character firstTapeCharacter, Character secondTapeCharacter){
        this.curState = curState;
        this.firstTapeCharacter = firstTapeCharacter;
        this.secondTapeCharacter = secondTapeCharacter;
    }

    /**
     * Gibt den Wert des Feldes curState zurück
     *
     * @return Der Wert von curState
     */
    public String getCurState() {
        return curState;
    }

    /**
     * Gibt den Wert des Feldes firstTapeCharacter zurück
     *
     * @return Der Wert von firstTapeCharacter
     */
    public Character getFirstTapeCharacter() {
        return firstTapeCharacter;
    }

    /**
     * Gibt den Wert des Feldes secondTapeCharacter zurück
     *
     * @return Der Wert von secondTapeCharacter
     */
    public Character getSecondTapeCharacter() {
        return secondTapeCharacter;
    }
}
