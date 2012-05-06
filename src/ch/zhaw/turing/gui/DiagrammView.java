package ch.zhaw.turing.gui;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import ch.zhaw.turing.logic.FactorialStateControl;
import ch.zhaw.turing.logic.MultiplicationStateControl;

public class DiagrammView extends JFrame implements Observer {

    private static final long serialVersionUID = 4980417373952490906L;

    private static final String DIR_SEP = File.pathSeparator;

    private static final String MULTIPLICATION_PATTERN = String.format("Multiplikation%sZustaende%s%s hervorgehoben", DIR_SEP, DIR_SEP);

    private static final String FACTORIAL_PATTERN = String.format("Fakultaet%sZustaende%s%s hervorgehoben", DIR_SEP, DIR_SEP);

    private final JLabel label = new JLabel();

    public DiagrammView() {
        super("Übergangsdiagramm");
        pack();
        setVisible(true);
    }

    @Override
    public void update(Observable o, Object arg) {
        String zustand = null; // FIXME
        
        String imgPath;
        if (o instanceof FactorialStateControl) {
            imgPath = String.format(FACTORIAL_PATTERN, zustand);
        } else if (o instanceof MultiplicationStateControl) {
            imgPath = String.format(MULTIPLICATION_PATTERN, zustand);
        } else {
            throw new IllegalArgumentException(o.getClass() + " sollte nicht im DiagrammView landen..");
        }
        
        File img = new File(imgPath);
        if (!img.exists()) {
            throw new IllegalArgumentException(imgPath + " existiert nicht..");
        }
        
        label.setIcon(new ImageIcon(img.getAbsolutePath()));
    }
}
