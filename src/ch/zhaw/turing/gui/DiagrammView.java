package ch.zhaw.turing.gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ch.zhaw.turing.logic.FactorialStateControl;
import ch.zhaw.turing.logic.MultiplicationStateControl;
import ch.zhaw.turing.logic.TuringMachine;

public class DiagrammView extends JFrame implements Observer {

    private static final long serialVersionUID = 4980417373952490906L;

    private static final String DIR_SEP = File.separator;

    private static final String MULTIPLICATION_PATTERN = String.format(
            "Multiplikation%sZustaende%s%s hervorgehoben.png", DIR_SEP, DIR_SEP, "%s");

    private static final String FACTORIAL_PATTERN = String.format("Fakultaet%sZustaende%s%s.png", DIR_SEP, DIR_SEP,
            "%s");

    private File dir;

    private BufferedImage bufImage;

    private final JPanel imgPanel = new JPanel();

    public DiagrammView() {
        super("Übergangsdiagramm");
        this.dir = new File(String.format(".%sZeichnungen", DIR_SEP));
        if (!dir.exists()) {
            dir = new File(dir.getParent(), "Zeichnungen");
            if (!dir.exists()) {
                throw new IllegalArgumentException("Wo sind die Zeichnungen? Habe sie in " + dir.getAbsolutePath()
                        + " nicht gefunden..");
            }
        }
        getContentPane().add(imgPanel);
        setSize(500, 500);
        setVisible(true);
    }

    @Override
    public void update(Observable o, Object arg) {
        TuringMachine m = (TuringMachine) o;
        String zustand = m.getCurrentState().toLowerCase();

        String imgName;
        if (m instanceof FactorialStateControl) {
            imgName = String.format(FACTORIAL_PATTERN, zustand);
        } else if (m instanceof MultiplicationStateControl) {
            imgName = String.format(MULTIPLICATION_PATTERN, zustand);
        } else {
            throw new IllegalArgumentException(o.getClass() + " sollte nicht im DiagrammView landen..");
        }

        File imgPath = new File(dir, imgName);
        if (!imgPath.exists()) {
            throw new IllegalArgumentException(imgPath + " existiert nicht..");
        }

        try {
            this.bufImage = ImageIO.read(imgPath);
            
            repaint();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void paint(Graphics g) {
        if (bufImage != null) {
            g.drawImage(bufImage, 0, 0, getWidth(), getHeight(), null);
        }
    }
}
