package ch.zhaw.turing.logic;

import java.util.Stack;

/**
 * 
 * @author Max Schrimpf
 */
public class ReadWriteHead {

    private final Stack<Character> prefix = new Stack<Character>();
    private final Stack<Character> suffix = new Stack<Character>();

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
    }

    public Character read() {
        return curChar;
    }

    public void write(Character curCharacter) {
        this.curChar = curCharacter;
    }
}
