package ch.zhaw.turing.gui;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import ch.zhaw.turing.logic.FactorialStateControl;
import ch.zhaw.turing.logic.MultiplicationStateControl;
import ch.zhaw.turing.logic.TuringMachine;

public class DiagrammView extends JFrame implements Observer {

    private static final long serialVersionUID = 4980417373952490906L;

    private static final String DIR_SEP = File.separator;

    private static final String MULTIPLICATION_PATTERN = String.format("Multiplikation%sZustaende%s%s hervorgehoben.png", DIR_SEP, DIR_SEP, "%s");

    private static final String FACTORIAL_PATTERN = String.format("Fakultaet%sZustaende%s%s hervorgehoben.png", DIR_SEP, DIR_SEP, "%s");
    
    private File dir;

    private final JLabel label = new JLabel();

    public DiagrammView() {
        super("Übergangsdiagramm");
        this.dir = new File(String.format(".%sZeichnungen", DIR_SEP));
        if (!dir.exists()) {
            dir = new File(dir.getParent(), "Zeichnungen");
            if (!dir.exists()) {
                throw new IllegalArgumentException("Wo sind die Zeichnungen? Habe sie in " + dir.getAbsolutePath() + " nicht gefunden..");
            }
        }
        pack();
        setVisible(true);
    }

    @Override
    public void update(Observable o, Object arg) {
        TuringMachine m = (TuringMachine) o;
        String zustand = m.getCurrentState().toLowerCase();
        
        String imgPath;
        if (m instanceof FactorialStateControl) {
            imgPath = String.format(FACTORIAL_PATTERN, zustand);
        } else if (m instanceof MultiplicationStateControl) {
            imgPath = String.format(MULTIPLICATION_PATTERN, zustand);
        } else {
            throw new IllegalArgumentException(o.getClass() + " sollte nicht im DiagrammView landen..");
        }
        
        File img = new File(dir, imgPath);
        if (!img.exists()) {
            throw new IllegalArgumentException(imgPath + " existiert nicht..");
        }
        
        label.setIcon(new ImageIcon(img.getAbsolutePath()));
    }
}
