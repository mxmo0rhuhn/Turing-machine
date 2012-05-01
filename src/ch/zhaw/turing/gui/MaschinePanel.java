package ch.zhaw.turing.gui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

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

            g.fillRect(16 * leftOffset + 15 * elemWidth, topOffset - schreibkopfHeight - 3, elemWidth,
                    schreibkopfHeight);

            Character[] tape = bandInhalte[i];
            for (int j = 0; j < tape.length; j++) {
                int leftOffset = (j + 1) * MaschinePanel.leftOffset + j * elemWidth;

                g.drawRect(leftOffset, topOffset, elemWidth, elemHeight);
                g.drawString(getElem(tape, j), leftOffset + elemWidth / 2 - 4, topOffset + (elemHeight / 2) + 4);
            }
        }
    }

    String getElem(Character[] band, int idx) {
        try {
            return Character.toString(band[idx]);
        } catch (ArrayIndexOutOfBoundsException e) {
            return "B";
        }
    }

}
