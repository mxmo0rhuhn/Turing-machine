package ch.zhaw.turing.logic;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Observable;

/**
 * Stellt ein einzelnes Band mit einer Spur und einem Lese-Schreib Kopf dar
 * 
 * @author Max Schrimpf
 */
public class ReadWriteHead extends Observable {

    // Richtig wären Grossbuchstaben L,R,S ... der Übersicht zuliebe anders.
    public static String LEFT = "left";
    public static String RIGHT = "right";
    public static String STAY = "stay";

//    private final Stack<Character> prefix = new Stack<Character>();
//    private final Stack<Character> suffix = new Stack<Character>();
    
    private final Deque<Character> prefix = new LinkedList<Character>();
    private final Deque<Character> suffix = new LinkedList<Character>();
    
    public static final char EMPTY_CHAR = 'B';
    public static final char ZERO_CHAR = '0';
    public static final char ONE_CHAR = '1';
    public static final Character EMPTY_VALUE = new Character('B');
    public static final Character ZERO_VALUE = new Character('0');
    public static final Character ONE_VALUE = new Character('1');


    private String lastDirection;
    private Character curChar;

    /**
     * Initialisiert den Lese Schreib Kopf. Der derzeitige Buchstabe ist nun ein
     * Blank. Im Suffix ist nun auch ein Blank.
     */
    public ReadWriteHead() {
        curChar = EMPTY_VALUE;
        suffix.push(EMPTY_VALUE);
        prefix.push(EMPTY_VALUE);
    }

    public void stay() {
        lastDirection = ReadWriteHead.STAY;
    }

    public void moveRight() {
        Deque<Character> prefix = this.prefix;
        Deque<Character> suffix = this.suffix;
        
        prefix.push(curChar);
        Character curChar = suffix.pop();

        // Simulation eines unendlichen Bandes
        // bug: je öfter gegen das Ende gefahren wird, destso mehr nutzlose
        // blanks werden in den Stack gepushed
        if (curChar.charValue() == EMPTY_CHAR && suffix.size() == 0) {
            suffix.push(EMPTY_VALUE);
        }
        
        this.curChar = curChar;

        lastDirection = ReadWriteHead.RIGHT;
        //sendNotification();
    }

    public void moveLeft() {
        Deque<Character> prefix = this.prefix;
        Deque<Character> suffix = this.suffix;
        suffix.push(curChar);
        
        Character curChar = prefix.pop();

        // Simulation eines unendlichen Bandes
        // bug: je öfter gegen das Ende gefahren wird, destso mehr nutzlose
        // blanks werden in den Stack gepushed
        if (curChar.charValue() == EMPTY_CHAR && prefix.size() == 0) {
            prefix.push(EMPTY_VALUE);
        }
        
        this.curChar = curChar;

        lastDirection = ReadWriteHead.LEFT;
        //sendNotification();
    }

    public Character read() {
        return curChar;
    }

    public void write(Character newCharacter) {
        Deque<Character> prefix = this.prefix;
        Deque<Character> suffix = this.suffix;
        
        // Damit das Band nicht unendlich wird, werden blanks nur geschrieben
        // wenn wirklich nötig...
        if (newCharacter.charValue() == EMPTY_VALUE) {
            if (prefix.size() > 1 && prefix.peek().charValue() == EMPTY_CHAR) {
                prefix.pop();
            }
            if (suffix.size() > 1 && suffix.peek().charValue() == EMPTY_CHAR) {
                suffix.pop();
            }
        }
        this.curChar = newCharacter;
    }

    /**
     * Gibt den Wert des Feldes prefix zurück
     * 
     * @return Der Wert von prefix
     */
    public Deque<Character> getPrefix() {
        return prefix;
    }

    /**
     * Gibt den Wert des Feldes suffix zurück
     * 
     * @return Der Wert von suffix
     */
    public Deque<Character> getSuffix() {
        return suffix;
    }

    /**
     * Gibt den Wert des Feldes lastDirection zurück
     * 
     * @return Der Wert von lastDirection
     */
    public String getLastDirection() {
        return lastDirection;
    }

    private void sendNotification() {
        setChanged();
        notifyObservers();
    }
}
