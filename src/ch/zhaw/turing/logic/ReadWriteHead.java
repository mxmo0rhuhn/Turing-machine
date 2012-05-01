package ch.zhaw.turing.logic;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Stellt ein einzelnes Band mit einer Spur und einem Lese-Schreib Kopf dar
 * 
 * @author Max Schrimpf
 */
public final class ReadWriteHead {

    private final LinkedList<Character> prefix = new NeverNeverEnd();
    private final LinkedList<Character> suffix = new NeverNeverEnd();

    // primitve typen um auto-boxing wenn immer möglich zu vermeiden
    static final char EMPTY_CHAR = 'B';
    static final char ZERO_CHAR = '0';
    static final char ONE_CHAR = '1';

    // wrapper type um auto-boxing wenn immer möglich zu vermeiden
    static final Character EMPTY_VALUE = new Character('B');
    static final Character ZERO_VALUE = new Character('0');
    static final Character ONE_VALUE = new Character('1');

    private Character curChar;

    /**
     * Initialisiert den Lese Schreib Kopf. Der derzeitige Buchstabe ist nun ein Blank. Im Suffix ist nun auch ein
     * Blank.
     */
    ReadWriteHead() {
        curChar = EMPTY_VALUE;
    }

    void moveRight() {
        Deque<Character> prefix = this.prefix;
        Deque<Character> suffix = this.suffix;

        Character curChar = this.curChar;
        // RETOP
        if (curChar.charValue() != EMPTY_CHAR) {
            prefix.push(curChar);
        }
        this.curChar = suffix.pop();
    }

    void moveLeft() {
        Deque<Character> prefix = this.prefix;
        Deque<Character> suffix = this.suffix;

        Character curChar = this.curChar;
        if (curChar.charValue() != EMPTY_CHAR) {
            suffix.push(curChar);
        }

        this.curChar = prefix.pop();
    }

    public Character read() {
        return curChar;
    }

    void write(Character newCharacter) {
        this.curChar = newCharacter;
    }

    /**
     * Gibt den Wert des Feldes prefix zurück
     * 
     * @return Der Wert von prefix
     */
    public Character[] getPrefix() {
        return this.prefix.toArray(new Character[0]);
    }

    /**
     * Gibt den Wert des Feldes suffix zurück
     * 
     * @return Der Wert von suffix
     */
    public Character[] getSuffix() {
        return this.suffix.toArray(new Character[0]);
    }

    public int getResultat() {
        int i = 0;

        moveLeft();

        while (read() != 'B') {
            moveLeft();
        }

        moveRight();
        while (read() == '0') {
            moveRight();
            i++;
        }

        return i;
    }
}

class NeverNeverEnd extends LinkedList<Character> {

    @Override
    public Character pop() {
        if (isEmpty()) {
            return ReadWriteHead.EMPTY_VALUE;
        } else {
            return super.pop();
        }
    }
    
    @Override
    public Character peek() {
        throw new UnsupportedOperationException("implement me");
    }

    private static final long serialVersionUID = 1819116069884717047L;

}
