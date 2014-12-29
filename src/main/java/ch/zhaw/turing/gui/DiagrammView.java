/*
 * Copyright (c) 2014 - Max Schrimpf
 *
 * Licensed under the EUPL, Version 1.1 or – as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 * https://joinup.ec.europa.eu/software/page/eupl5
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 */

package ch.zhaw.turing.gui;

import ch.zhaw.turing.logic.FactorialStateControl;
import ch.zhaw.turing.logic.MultiplicationStateControl;
import ch.zhaw.turing.logic.TuringMachine;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;

public class DiagrammView extends JFrame implements Observer {

    private static final long serialVersionUID = 4980417373952490906L;

    private static final String DIR_SEP = "/"; // windows jar-separator ist auch forward slash. yay

    private static final String FULL_JAR_FILENAME_MULIPLICATION = String.format("Multiplikation%s%s hervorgehoben.png", DIR_SEP, "%s");
    private static final String FULL_JAR_FILENAME_FACTORIAL = String.format("Fakultaet%s%s.png", DIR_SEP, "%s");

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
