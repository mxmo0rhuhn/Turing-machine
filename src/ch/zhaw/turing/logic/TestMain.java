package ch.zhaw.turing.logic;

import java.awt.GridLayout;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;

import sun.rmi.runtime.NewThreadAction;

import ch.zhaw.turing.gui.MaxMaschinePanel;

public class TestMain extends JFrame implements ZustandsUebergansListener{

    public static void main(String[] args) {
        new TestMain();
    }

    
   public TestMain() {

       ReadWriteHead tape1 = new ReadWriteHead();
       ReadWriteHead tape2 = new ReadWriteHead();
       
       MaxMaschinePanel pan1 = new MaxMaschinePanel();
       MaxMaschinePanel pan2 = new MaxMaschinePanel();

       pan1.setRWH(tape1);
       pan2.setRWH(tape2);
       
       tape1.addObserver((Observer) pan1);
       tape1.addObserver((Observer) pan2);
       
       setLayout(new GridLayout(2,0));

       add(pan1);
       add(pan2);
       
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


       setLocation(100, 0);
       setVisible(true);
       setSize(730, 300);
       
       MultiplicationStateControl myMult = new MultiplicationStateControl(3,2, tape1, tape2, this);
       myMult.doAllSteps();
}


@Override
public void inNeuenZustandGewechselt(String zustand, ReadWriteHead[] tapes, boolean akzeptiert) {
    // TODO Auto-generated method stub
    
}
}
