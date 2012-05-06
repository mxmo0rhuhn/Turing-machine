package ch.zhaw.turing.logic;

import java.util.LinkedList;

/**
 * Auf eine Turing Maschine optimierte Linked List
 * 
 * @author rethab
 */
public class NeverNeverEnd extends LinkedList<Character> {

    private static final long serialVersionUID = 1819116069884717047L;

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

}
