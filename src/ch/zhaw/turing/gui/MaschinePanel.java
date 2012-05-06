package ch.zhaw.turing.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import ch.zhaw.turing.logic.ReadWriteHead;

public class MaschinePanel extends JPanel implements Observer {

    private static final long serialVersionUID = 4732119788313761840L;

    static int topOffset = 40;
    static int leftOffset = 3;

    static int elemWidth = 20;
    static int elemHeight = 30;

    static int schreibkopfHeight = 10;

    private ReadWriteHead curRWH;

    private Character[] curTape;
    
    public void setRWH(ReadWriteHead curRWH) {
        this.curRWH = curRWH;
        curTape = curRWH.getTapeState();
    }

    @Override
    protected void paintComponent(Graphics g) {
        zeichneBandElement(g);
    }

    private void zeichneBandElement(Graphics g) {

        g.clearRect(0, 0, getWidth(), getHeight());
        int topOffset = MaschinePanel.topOffset + elemHeight;

        g.setColor(new Color(0, 0, 0));
        // Schreibkopf
        g.fillRect(16 * leftOffset + 15 * elemWidth, topOffset - schreibkopfHeight - 3, elemWidth, schreibkopfHeight);

        // Band
        for (int j = 0; j < curTape.length; j++) {
            int leftOffset = (j + 1) * MaschinePanel.leftOffset + j * elemWidth;

            // System.out.println(curTape[j]);
            switch (getElemAsChar(curTape, j)) {
            case ReadWriteHead.ONE_CHAR:
                g.setColor(new Color(25, 25, 112));
                break;
            case ReadWriteHead.ZERO_CHAR:
                g.setColor(new Color(255, 0, 0));
                break;
            default:
                g.setColor(new Color(190, 190, 190));
                break;
            }

            g.drawRect(leftOffset, topOffset, elemWidth, elemHeight);
            g.drawString(getElem(curTape, j), leftOffset + elemWidth / 2 - 4, topOffset + (elemHeight / 2) + 4);
        }
    }

    private String getElem(Character[] band, int idx) {
        try {
            return Character.toString(band[idx]);
        } catch (ArrayIndexOutOfBoundsException e) {
            return "B";
        }
    }

    private char getElemAsChar(Character[] band, int idx) {
        try {
            return band[idx];
        } catch (ArrayIndexOutOfBoundsException e) {
            return ReadWriteHead.EMPTY_CHAR;
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        curTape = curRWH.getTapeState();
        repaint();
    }
}
