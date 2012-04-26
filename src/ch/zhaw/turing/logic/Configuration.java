package ch.zhaw.turing.logic;

/**
 * Ist das Überhaupt notwendig?
 *
 * @author Max Schrimpf
 */
public class Configuration {

    private final String curState ;
    private final Character curCharacter;

    public Configuration(String curState, Character curCharacter){
        this.curState = curState;
        this.curCharacter = curCharacter;
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
     * Gibt den Wert des Feldes curCharacter zurück
     *
     * @return Der Wert von curCharacter
     */
    public Character getCurCharacter() {
        return curCharacter;
    }
}
