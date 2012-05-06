package ch.zhaw.turing.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observer;

import javax.swing.JButton;
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

import ch.zhaw.turing.logic.FactorialStateControl;
import ch.zhaw.turing.logic.MultiplicationStateControl;
import ch.zhaw.turing.logic.ReadWriteHead;
import ch.zhaw.turing.logic.TuringMachine;
import ch.zhaw.turing.logic.ZustandsUebergansListener;

public class MaschineView extends JFrame implements ActionListener, ZustandsUebergansListener, ChangeListener {

    private static final long serialVersionUID = 603352769158705835L;

    private static final int minDelay = System.getProperty("minDelay") != null ? Integer.parseInt(System
            .getProperty("minDelay")) : 200;
    private static volatile int timeout = 200; // timeout für automatisches rechnen

    private static boolean debug = false;

    // soll die maschine automatisch durchlaufen? d.h. kein step-by-step
    static volatile boolean automatic = false;

    private static final String MULTIPLIZIEREN_MENU_EINTRAG = "Multiplizieren";
    private static final String FAKULTAET_MENU_EINTRAG = "Fakultät";

    private final JLabel infoLabel = new JLabel("Der (Turing) Maschine");
    private final JLabel stepsLabel = new JLabel("");

    private volatile TuringMachine machine;

    // 3 Panel die die einzelnen Lese-Schreib Koepfe ueberwachen
    private MaschinePanel firstRWHPanel;
    private MaschinePanel secondRWHPanel;
    private MaschinePanel thirdRWHPanel;

    private ReadWriteHead firstRWH;
    private ReadWriteHead secondRWH;
    private ReadWriteHead thirdRWH;

    public MaschineView() {
        this.setTitle("Turing Maschine");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setJMenuBar(createMenu());

        buildFrame();

        this.setLocation(100, 0);
        this.setVisible(true);
        this.setSize(730, 500);
    }

    private void buildFrame() {
        setLayout(new BorderLayout());
        add(createNorthPanel(), BorderLayout.NORTH);
        add(createCenterPanel(), BorderLayout.CENTER);
        add(createSouthPanel(), BorderLayout.SOUTH);
    }

    private JPanel createSouthPanel() {
        JPanel panel = new JPanel();
        JButton nxt = new JButton("Nächster Schritt");
        nxt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (machine == null) {
                    return;
                }
                if (!machine.acceptedState()) {
                    machine.doStep();
                    if (machine.acceptedState()) {
                        showResult();
                    } else {
                        stepsLabel.setText("  Schritte: " + machine.getNumberOfSteps());
                    }
                }
            }

        });
        JButton auto = new JButton("Automatisch");
        auto.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (machine == null) {
                    return;
                }
                automatic = true;
                calcAutomatichWithTimeout();
            }
        });
        JButton stop = new JButton("Stop");
        stop.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (machine == null) {
                    return;
                }
                automatic = false;
            }

        });
        panel.add(nxt);
        panel.add(auto);
        panel.add(stop);
        return panel;
    }
    
    private void calcAutomatichWithTimeout() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (automatic && !machine.acceptedState()) {
                    machine.doStep();
                    
                    if (machine.acceptedState()) {
                        showResult();
                    } else {
                        stepsLabel.setText("  Schritte: " + machine.getNumberOfSteps());
                        try {
                            Thread.sleep(timeout);
                        } catch (InterruptedException e1) {
                            Thread.interrupted();
                        }
                    }
                    
                }
            }
            
        }).start();
    }

    private void showResult() {
        infoLabel.setText("Das Resultat ist: " + firstRWH.getResultat());
    }

    private JPanel createCenterPanel() {
        JPanel centerJPanel = new JPanel();
        centerJPanel.setLayout(new GridLayout(3, 0));

        firstRWHPanel = new MaschinePanel();
        secondRWHPanel = new MaschinePanel();
        thirdRWHPanel = new MaschinePanel();

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
        menuBar.add(maschine);
        return menuBar;
    }

    private JMenuItem erstelleTimeoutSliderMenu() {
        JMenuItem timeoutSlider = new JMenuItem("Timeout");
        timeoutSlider.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame f = new JFrame("Timeout in Millisekunden");
                JSlider slider = new JSlider(JSlider.HORIZONTAL, minDelay, 2000, 1000);
                slider.addChangeListener(MaschineView.this);
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

    private TuringMachine fakultaet() {
        String eingabe = JOptionPane.showInputDialog("Geben Sie eine Zahl ein: ");
        try {
            this.infoLabel.setText(String.format("Rechne %s!", eingabe.trim()));

            firstRWH.clear();
            secondRWH.clear();
            thirdRWH.clear();

            return new FactorialStateControl(Integer.parseInt(eingabe.trim()), firstRWH, secondRWH, thirdRWH, this,
                    this);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Fehler: " + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
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
            JOptionPane.showMessageDialog(this, "Fehler: " + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JMenuItem menuItem = (JMenuItem) e.getSource();
        if (MULTIPLIZIEREN_MENU_EINTRAG.equals(menuItem.getText())) {
            machine = multipliziere();
        } else if (FAKULTAET_MENU_EINTRAG.equals(menuItem.getText())) {
            machine = fakultaet();
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

    @Override
    public void inNeuenZustandGewechselt(String zustand, boolean akzeptiert) {
        // TODO Auto-generated method stub
        
    }
}
