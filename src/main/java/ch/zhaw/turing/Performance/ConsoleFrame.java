package ch.zhaw.turing.Performance;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Diese Klasse stellt die Konsolenausgabe in einem eigenen Fenster dar. Anweisungen wie System.out.println() nach dem Instanzieren auf diesem Fenster dargestellt.
 * 
 * @author Max Schrimpf
 */
@SuppressWarnings("serial") //Wird nicht Serialisiert
public class ConsoleFrame extends JFrame
{
	private final JTextArea textArea;
	private final JScrollPane scrollPane;
	private final JPanel panel;

	/**
	 * Erstellt ein neues Konsolen-Fenster, welches ab sofort die Konsolenausgabe darstellt.
	 */
	public ConsoleFrame()
	{
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
	private void updateTextArea(final String text)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				ConsoleFrame.this.textArea.append(text);
			}
		});
	}

	/**
	 * Ruft bei Nachrichten auf den Standard Output Streams die Methide updateTextArea auf.
	 */
	private void redirectSystemStreams()
	{
		OutputStream out = new OutputStream()
		{
			public void write(int b) throws IOException
			{
				updateTextArea(String.valueOf((char) b));
			}

			public void write(byte[] b, int off, int len) throws IOException
			{
				updateTextArea(new String(b, off, len));
			}

			public void write(byte[] b) throws IOException
			{
				write(b, 0, b.length);
			}
		};

		System.setOut(new PrintStream(out, true));
		System.setErr(new PrintStream(out, true));
	}
}