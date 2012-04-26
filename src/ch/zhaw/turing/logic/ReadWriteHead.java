package ch.zhaw.turing.logic;

import java.util.Observable;
import java.util.Stack;

/**
 * Stellt ein einzelnes Band mit einer Spur und einem Lese-Schreib Kopf dar
 * 
 * @author Max Schrimpf
 */
public class ReadWriteHead extends Observable{

    public static int LEFT = 0;
    public static int RIGHT = 1;
    
    private final Stack<Character> prefix = new Stack<Character>();
    private final Stack<Character> suffix = new Stack<Character>();

    private int lastDirection; 
    private Character curChar;

    /**
     * Initialisiert den Lese Schreib Kopf. Der derzeitige Buchstabe ist nun ein
     * Blank. Im Suffix ist nun auch ein Blank.
     */
    public ReadWriteHead() {
        curChar = 'B';
        suffix.push('B');
        prefix.push('B');
    }

    public void moveRight() {
        prefix.push(curChar);
        curChar = suffix.pop();

        // Simulation eines unendlichen Bandes
        // bug: je öfter gegen das Ende gefahren wird, destso mehr nutzlose
        // blanks werden in den Stack gepushed
        if (curChar == 'B') {
            suffix.push('B');
        }
         
        lastDirection = ReadWriteHead.RIGHT;
        sendNotification();
    }

    public void moveLeft() {
        suffix.push(curChar);
        curChar = prefix.pop();

        // Simulation eines unendlichen Bandes
        // bug: je öfter gegen das Ende gefahren wird, destso mehr nutzlose
        // blanks werden in den Stack gepushed
        if (curChar == 'B') {
            prefix.push('B');
        }

        lastDirection = ReadWriteHead.LEFT;
        sendNotification();
    }

    private void sendNotification()
    {
        setChanged();
        notifyObservers();
        
    }
    
    public Character read() {
        return curChar;
    }

    public void write(Character curCharacter) {
        this.curChar = curCharacter;
    }

    /**
     * Gibt den Wert des Feldes prefix zurück
     *
     * @return Der Wert von prefix
     */
    public Stack<Character> getPrefix() {
        return prefix;
    }

    /**
     * Gibt den Wert des Feldes suffix zurück
     *
     * @return Der Wert von suffix
     */
    public Stack<Character> getSuffix() {
        return suffix;
    }

    /**
     * Gibt den Wert des Feldes lastDirection zurück
     *
     * @return Der Wert von lastDirection
     */
    public int getLastDirection() {
        return lastDirection;
    }
}
