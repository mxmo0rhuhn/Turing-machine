package ch.zhaw.turing.gui;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ch.zhaw.turing.logic.FactorialStateControl;
import ch.zhaw.turing.logic.MultiplicationStateControl;
import ch.zhaw.turing.logic.ReadWriteHead;
import ch.zhaw.turing.logic.TuringMachine;
import ch.zhaw.turing.logic.ZustandsUebergansListener;

public class MaschineView implements ActionListener, ZustandsUebergansListener, ChangeListener {

    static boolean debug = false;

    private static final String MULTIPLIZIEREN_MENU_EINTRAG = "Multiplizieren";

    private static final String FAKULTAET_MENU_EINTRAG = "Fakultät";

    static volatile int timeout = 1000;

    static volatile boolean pausiert = false;

    private static final ExecutorService paintService = Executors.newSingleThreadExecutor();

    private final JFrame frame;

    public MaschineView() {
        this.frame = new JFrame("Turning Maschine");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.frame.setJMenuBar(createMenu());

        this.frame.setLocation(100, 0);
        this.frame.setVisible(true);
        this.frame.setSize(730, 270);
    }

    private JMenuBar createMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu maschine = new JMenu("Maschine");

        JMenuItem multi = new JMenuItem(MULTIPLIZIEREN_MENU_EINTRAG);
        maschine.add(multi);
        multi.addActionListener(this);

        JMenuItem fact = new JMenuItem(FAKULTAET_MENU_EINTRAG);
        maschine.add(fact);
        fact.addActionListener(this);

        maschine.add(erstelleTimeoutSliderMenu());
        maschine.add(erstellePauseKnopf());
        menuBar.add(maschine);
        return menuBar;
    }

    private JMenuItem erstellePauseKnopf() {
        JMenuItem pause = new JMenuItem("Pause");
        pause.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                pausiert = !pausiert;
            }

        });
        return pause;
    }

    private JMenuItem erstelleTimeoutSliderMenu() {
        JMenuItem timeoutSlider = new JMenuItem("Timeout");
        timeoutSlider.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame f = new JFrame("Timeout in Millisekunden");
                JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 5000, 1000);
                slider.addChangeListener(MaschineView.this);
                slider.setMajorTickSpacing(500);
                slider.setMinorTickSpacing(100);
                slider.setPaintLabels(true);
                slider.setPaintTicks(true);
                f.getContentPane().add(slider);
                f.setLocation(100, 300);
                f.setSize(600, 100);
                f.setVisible(true);
            }

        });
        return timeoutSlider;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JMenuItem menuItem = (JMenuItem) e.getSource();
        TuringMachine m = null;
        if (MULTIPLIZIEREN_MENU_EINTRAG.equals(menuItem.getText())) {
            m = multipliziere();
        } else if (FAKULTAET_MENU_EINTRAG.equals(menuItem.getText())) {
            m = fakultaet();
        }

        if (m != null) {
            starteMaschine(m);
        }
    }

    private void starteMaschine(final TuringMachine m) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                m.doAllSteps();
            }
        }).start();
    }

    private TuringMachine fakultaet() {
        String eingabe = JOptionPane.showInputDialog("Geben Sie eine Zahl ein: ");
        try {
            return new FactorialStateControl(Integer.parseInt(eingabe.trim()), this);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Fehler: " + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    private TuringMachine multipliziere() {
        String eingabe = JOptionPane.showInputDialog("Geben Sie zwei Zahlen ein (z.B. '13 10'): ");
        try {
            String[] parts = eingabe.split(" ");
            return new MultiplicationStateControl(Integer.parseInt(parts[0].trim()), Integer.parseInt(parts[1].trim()),
                    this);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Fehler: " + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    @Override
    public void inNeuenZustandGewechselt(String zustand, ReadWriteHead[] tapes) {
        sollenWirMalPauseMachen();
        debug("Neuer Zustand: " + zustand);

        Character[][] bandInhalte = new Character[tapes.length][];
        for (int i = 0; i < tapes.length; i++) {
            bandInhalte[i] = get30Chars(tapes[i].getPrefix(), tapes[i].read(), tapes[i].getSuffix());
        }
        fireUpdate(bandInhalte);
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            Thread.interrupted();
        }
    }

    static Character[] get30Chars(Character[] prefix, Character curChar, Character[] suffix) {
        Character[] resultat = new Character[31];
        
        List<Character> reverse = Arrays.asList(prefix);
        Collections.reverse(reverse);
        prefix = reverse.toArray(new Character[0]);

        if (prefix.length > 15) {
            prefix = Arrays.copyOfRange(prefix, 0, 15); // take at most 15
        }
        if (suffix.length > 15) {
            suffix = Arrays.copyOfRange(suffix, 0, 15); // take at most 15
        }

        System.arraycopy(prefix, 0, resultat, 15 - prefix.length, prefix.length);
        resultat[15] = curChar;
        System.arraycopy(suffix, 0, resultat, 16, suffix.length);
        fillNullValues(resultat);

        return resultat;
    }

    private static void fillNullValues(Character[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == null) {
                arr[i] = new Character('B');
            }
        }
    }

    private void sollenWirMalPauseMachen() {
        while (pausiert) {
            try {
                debug("pause machen..");
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.interrupted();
            }
        }
    }

    private void fireUpdate(final Character[][] bandInhalte) {
        paintService.execute(new Runnable() {
            @Override
            public void run() {
                Container c = frame.getContentPane();
                c.removeAll();
                MaschinePanel mp = new MaschinePanel(bandInhalte);
                c.add(mp);

                c.validate();
                c.repaint();
            }

        });
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        timeout = ((JSlider) e.getSource()).getValue();
        debug("Timeout angepasst nach: " + timeout);
    }

    static void debug(String message) {
        if (debug) {
            System.out.println(message);
        }
    }

}
