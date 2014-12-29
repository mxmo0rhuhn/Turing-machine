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

package ch.zhaw.turing.Performance;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Diese Klasse stellt die Konsolenausgabe in einem eigenen Fenster dar. Anweisungen wie System.out.println() nach dem Instanzieren auf diesem Fenster dargestellt.
 *
 * @author Max Schrimpf
 */
@SuppressWarnings("serial") //Wird nicht Serialisiert
public class ConsoleFrame extends JFrame {
    private final JTextArea textArea;
    private final JScrollPane scrollPane;
    private final JPanel panel;

    /**
     * Erstellt ein neues Konsolen-Fenster, welches ab sofort die Konsolenausgabe darstellt.
     */
    public ConsoleFrame() {
        this.textArea = new JTextArea();
        this.textArea.setEditable(false);

        this.scrollPane = new JScrollPane(this.textArea);

        this.panel = new JPanel(new BorderLayout());
        this.panel.add(this.scrollPane, BorderLayout.CENTER);

        add(this.panel);

        redirectSystemStreams();

        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Konsole");
        setSize(new Dimension(500, 300));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Schreibt Text in die Text Area des Frames.
     *
     * @param text Der Text der geschrieben werden soll.
     */
    private void updateTextArea(final String text) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ConsoleFrame.this.textArea.append(text);
            }
        });
    }

    /**
     * Ruft bei Nachrichten auf den Standard Output Streams die Methide updateTextArea auf.
     */
    private void redirectSystemStreams() {
        OutputStream out = new OutputStream() {
            public void write(int b) throws IOException {
                updateTextArea(String.valueOf((char) b));
            }

            public void write(byte[] b, int off, int len) throws IOException {
                updateTextArea(new String(b, off, len));
            }

            public void write(byte[] b) throws IOException {
                write(b, 0, b.length);
            }
        };

        System.setOut(new PrintStream(out, true));
        System.setErr(new PrintStream(out, true));
    }
}