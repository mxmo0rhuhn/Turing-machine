package ch.zhaw.turing.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.sun.org.apache.xerces.internal.impl.RevalidationHandler;

import ch.zhaw.turing.logic.FactorialStateControl;
import ch.zhaw.turing.logic.MultiplicationStateControl;
import ch.zhaw.turing.logic.ReadWriteHead;
import ch.zhaw.turing.logic.TuringMachine;
import ch.zhaw.turing.logic.ZustandsUebergansListener;

public class MaxMaschineView extends JFrame implements ActionListener, ZustandsUebergansListener, ChangeListener {

    private static final int minDelay = System.getProperty("minDelay") != null ? Integer.parseInt(System
            .getProperty("minDelay")) : 200;
    static volatile int timeout = 1;

    static boolean debug = true;
    static volatile boolean pausiert = false;

    private static final String MULTIPLIZIEREN_MENU_EINTRAG = "Multiplizieren";
    private static final String FAKULTAET_MENU_EINTRAG = "Fakultät";

    private static final ExecutorService paintService = Executors.newSingleThreadExecutor();
    private Thread turingThread;
    private static volatile AtomicInteger steps = new AtomicInteger();

    private final JLabel infoLabel = new JLabel("Der (Turing) Maschine");
    private final JLabel stepsLabel = new JLabel("");

    // 3 Panel die die einzelnen Lese-Schreib Koepfe ueberwachen
    private MaxMaschinePanel firstRWHPanel;
    private MaxMaschinePanel secondRWHPanel;
    private MaxMaschinePanel thirdRWHPanel;
    
    private ReadWriteHead firstRWH; 
    private ReadWriteHead secondRWH;
    private ReadWriteHead thirdRWH; 

    public MaxMaschineView() {
        this.setTitle("Turing Maschine");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setJMenuBar(createMenu());

        buildFrame();

        this.setLocation(100, 0);
        this.setVisible(true);
        this.setSize(730, 500);
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {

            @Override
            public void run() {
                if (paintService != null && !paintService.isShutdown()) {
                    paintService.shutdownNow();
                }
            }
        }) {
        });
    }

    private void buildFrame() {
        setLayout(new BorderLayout());
        add(createNorthPanel(), BorderLayout.NORTH);
        add(createCenterPanel(), BorderLayout.CENTER);
        add(createSouthPanel(), BorderLayout.SOUTH);
    }

    private JPanel createSouthPanel() {
        // next Step Pause und run Knoepfe hier
        return new JPanel();
    }

    private JPanel createCenterPanel() {
        JPanel centerJPanel = new JPanel();
        centerJPanel.setLayout(new GridLayout(3, 0));
        
        firstRWHPanel = new MaxMaschinePanel();
        secondRWHPanel = new MaxMaschinePanel();
        thirdRWHPanel = new MaxMaschinePanel();
        
        firstRWH = new ReadWriteHead();
        secondRWH = new ReadWriteHead();
        thirdRWH = new ReadWriteHead();

        firstRWHPanel.setRWH(firstRWH);
        secondRWHPanel.setRWH(secondRWH);
        thirdRWHPanel.setRWH(thirdRWH);

        firstRWH.addObserver((Observer) firstRWHPanel);
        secondRWH.addObserver((Observer) secondRWHPanel);
        thirdRWH.addObserver((Observer) thirdRWHPanel);
        
        centerJPanel.add(firstRWHPanel);
        centerJPanel.add(secondRWHPanel);
        centerJPanel.add(thirdRWHPanel);

        return centerJPanel;
    }

    private JPanel createNorthPanel() {
        JPanel panel = new JPanel();
        panel.add(infoLabel);
        panel.add(stepsLabel);

        return panel;
    }

    private JMenuBar createMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu maschine = new JMenu("Maschine in Action");

        JMenuItem multi = new JMenuItem(MULTIPLIZIEREN_MENU_EINTRAG);
        maschine.add(multi);
        multi.addActionListener(this);

        JMenuItem fact = new JMenuItem(FAKULTAET_MENU_EINTRAG);
        maschine.add(fact);
        fact.addActionListener(this);

        maschine.add(erstelleTimeoutSliderMenu());
        maschine.add(erstellePauseKnopf());
        maschine.add(erstelleStopKnopf());
        menuBar.add(maschine);
        return menuBar;
    }

    private JMenuItem erstelleStopKnopf() {
        JMenuItem stop = new JMenuItem("Stop");
        stop.addActionListener(new ActionListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void actionPerformed(ActionEvent e) {
                if (turingThread != null && turingThread.isAlive()) {
                    turingThread.stop();
                }
            }

        });
        return stop;
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
                JSlider slider = new JSlider(JSlider.HORIZONTAL, minDelay, 2000, 1000);
                slider.addChangeListener(MaxMaschineView.this);
                slider.setMajorTickSpacing(200);
                slider.setMinorTickSpacing(50);
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

    private void starteMaschine(final TuringMachine m) {
        this.turingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                steps = new AtomicInteger();
                m.doAllSteps();
            }
        });
        this.turingThread.start();
    }

    private TuringMachine fakultaet() {
        String eingabe = JOptionPane.showInputDialog("Geben Sie eine Zahl ein: ");
        try {
            this.infoLabel.setText(String.format("Rechne %s!", eingabe.trim()));
           
            firstRWH.clear();
            secondRWH.clear();
            thirdRWH.clear();

            return new FactorialStateControl(Integer.parseInt(eingabe.trim()), firstRWH, secondRWH, thirdRWH, this);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Fehler: " + e.getMessage(),
             "Fehler", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    private TuringMachine multipliziere() {
        String eingabe = JOptionPane.showInputDialog("Geben Sie zwei Zahlen ein (z.B. '13 10'): ");
        try {
            String[] parts = eingabe.split(" ");
            String zahl1 = parts[0].trim();
            String zahl2 = parts[1].trim();

            firstRWH.clear();
            secondRWH.clear();
            thirdRWH.clear();

            this.infoLabel.setText(String.format("Rechne: %s mal %s", zahl1, zahl2));
            return new MultiplicationStateControl(Integer.parseInt(zahl1), Integer.parseInt(zahl2), firstRWH,
                    secondRWH, this);
        } catch (Exception e) {
             JOptionPane.showMessageDialog(this, "Fehler: " + e.getMessage(),
             "Fehler", JOptionPane.ERROR_MESSAGE);
            return null;
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

    @Override
    public void inNeuenZustandGewechselt(String zustand, ReadWriteHead[] tapes, boolean akzeptiert) {
        sollenWirMalPauseMachen();
        debug("Neuer Zustand: " + zustand);

        int steps = MaxMaschineView.steps.incrementAndGet();
        stepsLabel.setText("  Schritte: " + steps);

        String msg = null;
        if (akzeptiert) {
            msg = "Das Resultat ist: " + tapes[0].getResultat();
        }

        infoLabel.setText(msg);

        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            Thread.interrupted();
        }
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
