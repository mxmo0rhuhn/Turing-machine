package ch.zhaw.turing.gui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

import ch.zhaw.turing.logic.ReadWriteHead;

public class MaschinePanel extends JComponent {

    private static final long serialVersionUID = 4732119788313761840L;

    static int topOffset = 40;
    static int leftOffset = 3;

    static int elemWidth = 20;
    static int elemHeight = 30;

    static int schreibkopfHeight = 10;

    private final Character[][] bandInhalte;

    public MaschinePanel(Character[][] bandInhalte) {
        this.bandInhalte = bandInhalte;
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        zeichneBandElement(g);
    }

    private void zeichneBandElement(Graphics g) {

        for (int i = 0; i < bandInhalte.length; i++) {
            int topOffset = (i + 1) * MaschinePanel.topOffset + i * elemHeight;

            g.setColor(new Color(0, 0, 0));
            // Schreibkopf
            g.fillRect(16 * leftOffset + 15 * elemWidth, topOffset - schreibkopfHeight - 3, elemWidth,
                    schreibkopfHeight);

            // Band
            Character[] curTape = bandInhalte[i];
            for (int j = 0; j < curTape.length; j++) {
                int leftOffset = (j + 1) * MaschinePanel.leftOffset + j * elemWidth;

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
}
