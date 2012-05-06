package ch.zhaw.turing.logic;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

/**
 * Stellt ein einzelnes Band mit einer Spur und einem Lese-Schreib Kopf dar
 * 
 * @author Max Schrimpf
 */
public final class ReadWriteHead extends Observable{
    
    public enum Direction {
        LEFT, RIGHT, STAY;
    }

    private NeverNeverEnd prefix;
    private NeverNeverEnd suffix;

    // primitve typen um auto-boxing wenn immer möglich zu vermeiden
    public static final char EMPTY_CHAR = 'B';
    public static final char ZERO_CHAR = '0';
    public static final char ONE_CHAR = '1';

    // wrapper type um auto-boxing wenn immer möglich zu vermeiden
    public static final Character EMPTY_VALUE = new Character('B');
    public static final Character ZERO_VALUE = new Character('0');
    public static final Character ONE_VALUE = new Character('1');

    private Character curChar;
    
    private Direction lastMove = Direction.STAY;

    /**
     * Initialisiert den Lese Schreib Kopf. Der derzeitige Buchstabe ist nun ein Blank. Im Suffix ist nun auch ein
     * Blank.
     */
    public ReadWriteHead() {
        clear();
    }

    void moveRight() {
       Character curChar = this.curChar;
        
        if (curChar.charValue() != EMPTY_CHAR) {
            prefix.push(curChar);
        }
        this.curChar = suffix.pop();
        this.lastMove = Direction.RIGHT;
        
        setChanged();
        notifyObservers();
    }

    void moveLeft() {
        Character curChar = this.curChar;
        if (curChar.charValue() != EMPTY_CHAR) {
            suffix.push(curChar);
        }
        this.curChar = prefix.pop();
        this.lastMove = Direction.LEFT;

        setChanged();
        notifyObservers();
    }

    void write(Character newCharacter) {
        this.curChar = newCharacter;
        
        setChanged();
        notifyObservers();
    }

    public Character read() {
//        setChanged();
//        notifyObservers();
        
        return this.curChar;
    }
    
    /**
     * Die letzte Bewegung von diesem Lese-Schreib-Kopf
     * @return
     */
    public Direction getLastMove() {
        return this.lastMove;
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
    
    /**
     * Erstellt ein Array mit dem derzeitigen Bandinhalt der Maschine
     *
     * @return der Bandinhalt der Maschine.
     */
    public Character[] getTapeState(){
    
        Character[] prefix;
        Character[] suffix;
        Character[] resultat = new Character[31];
    
        prefix = Arrays.copyOfRange(this.prefix.toArray(new Character[0]), 0, 15); // take at most 15
        
        List<Character> reverse = Arrays.asList(prefix);
        Collections.reverse(reverse);
        prefix = reverse.toArray(new Character[0]);

        suffix = Arrays.copyOfRange(this.suffix.toArray(new Character[0]), 0, 15); // take at most 15
    
        System.arraycopy(prefix, 0, resultat, 15 - prefix.length, prefix.length);
        resultat[15] = curChar;
        System.arraycopy(suffix, 0, resultat, 16, suffix.length);
       
        for (int i = 0; i < resultat.length; i++) {
            if (resultat[i] == null) {
                resultat[i] = new Character('B');
            }
        }
        return resultat;
    }

    /**
     * Gibt die Erste Zahl auf dem Band aus. 
     *
     * @return die Erste Zahl auf dem Band.
     */
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
    
    public void clear() {
        curChar = EMPTY_VALUE;
        prefix = new NeverNeverEnd();
        suffix = new NeverNeverEnd();
    }
}