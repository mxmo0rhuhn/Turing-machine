package ch.zhaw.turing.gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
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

    private static final String DIR_SEP = "/"; // windows jar-separator ist auch forward slash. yay

    private static final String FULL_JAR_FILENAME_MULIPLICATION = String.format(
    //        "%ssrc%smain%sressources%sMultiplikation%sZustaende%s%s hervorgehoben.png", DIR_SEP, DIR_SEP, DIR_SEP, DIR_SEP, DIR_SEP, DIR_SEP, "%s");
    "Multiplikation%sZustaende%s%s hervorgehoben.png", DIR_SEP, DIR_SEP, "%s");
    //Multiplikation/Zustaende/q0 hervorgehoben.png

    private static final String FULL_JAR_FILENAME_FACTORIAL = String.format(
    //        "%ssrc%smain%skressourcesultaet%sZustaende%s%s.png", DIR_SEP, DIR_SEP, DIR_SEP, DIR_SEP, DIR_SEP, DIR_SEP, "%s");
    "Fakultaet%sZustaende%s%s.png", DIR_SEP, DIR_SEP, "%s");
    //Fakultaet/Zustaende/q0.png

    private BufferedImage bufImage;

    private final JPanel imgPanel = new JPanel();

    public DiagrammView() {
        super("Übergangsdiagramm");
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
            imgName = String.format(FULL_JAR_FILENAME_FACTORIAL, zustand);
        } else if (m instanceof MultiplicationStateControl) {
            imgName = String.format(FULL_JAR_FILENAME_MULIPLICATION, zustand);
        } else {
            throw new IllegalArgumentException(o.getClass() + " sollte nicht im DiagrammView landen..");
        }

//        imgName = zustand + ".png";

        URL fileUrl = this.getClass().getClassLoader().getResource(imgName);
        if (fileUrl == null) {
            throw new RuntimeException(imgName + " does not exist");
        }
        try {
            this.bufImage = ImageIO.read(fileUrl);
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
