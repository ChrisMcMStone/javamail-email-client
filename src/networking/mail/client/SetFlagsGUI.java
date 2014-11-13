package networking.mail.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

/**
 * 
 * Class to represent the GUI for for setting the SPAM flag
 * 
 * @author Chris McMahon-Stone
 *
 */
@SuppressWarnings("serial")
public class SetFlagsGUI extends JFrame {

	private JTextField flagNameField;
	private JTextField keywordField;
	private List<String> keyWords;
	private String keywordsPath;

	/**
	 * Constructor for the the GUI to set keywords for the SPAM filter
	 * 
	 * @param keywords
	 *            List of String keywords
	 * @param path
	 *            to the file to write flag keywords
	 */

	public SetFlagsGUI(List<String> keywords, String path) {

		// Call JFrame constructor giving a name for the window

		super("Edit e-mail flags");

		// Add GUI components with absolute positioning.
		// (Mostly machine generated code)

		setBounds(100, 100, 381, 227);
		getContentPane().setLayout(null);

		JLabel lblFlag = new JLabel("Flag name: ");
		lblFlag.setBounds(29, 22, 110, 25);
		getContentPane().add(lblFlag);

		JTextArea lblDesc = new JTextArea("(separated by \n semi-colons)");
		lblDesc.setBackground(UIManager.getColor("Menu.background"));
		lblDesc.setEditable(false);
		lblDesc.setBounds(29, 87, 98, 39);
		getContentPane().add(lblDesc);

		JButton save = new JButton("Save/apply");
		save.setBounds(206, 155, 132, 25);
		getContentPane().add(save);

		flagNameField = new JTextField("SPAM");
		flagNameField.setEditable(false);
		flagNameField.setBounds(138, 22, 200, 25);
		getContentPane().add(flagNameField);
		flagNameField.setColumns(10);

		keywordField = new JTextField();
		keywordField.setColumns(10);
		keywordField.setBounds(138, 59, 200, 72);
		getContentPane().add(keywordField);

		JLabel keywordsText = new JLabel("Keyword(s):");
		keywordsText.setBounds(29, 21, 132, 110);
		getContentPane().add(keywordsText);

		keyWords = keywords;
		keywordsPath = path;

		// If there are any previously saved SPAM words then fill the JTextField
		// with these values.

		if (!(keyWords == null)) {
			StringBuilder sb = new StringBuilder();
			for (String s : keywords) {
				sb.append(s + ";");
			}

			keywordField.setText(sb.toString());

		}

		// Add action listener for the save button
		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Write the entered keywords to the file.
				writeToFile();
				JOptionPane.showMessageDialog(null,
						"Spam keywords saved successfully.");
				dispose();
				onComplete();

			}
		});
	}

	/**
	 * Method that is overridden by the ViewMailGUI to update the keywords list.
	 */
	protected void onComplete() {
		// This is overridden
	}

	/**
	 * Writes the contents of the keywords text field to a text file.
	 */
	protected void writeToFile() {

		Path path = FileSystems.getDefault().getPath(keywordsPath,
				"keywords.text"); // Set the path of the file to save.
		byte[] words = keywordField.getText().toLowerCase()
				.replaceAll(";", "\n").getBytes(); // replace
		// all
		// semi-colons
		// with
		// newlines
		try {
			Files.write(path, words); // write the words to the file, if the
										// file doesn't exist create it.
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
					"Cannot write filter keywords to file."); // catch error
																// message and
																// inform user
																// that the
																// process
																// failed.
		}

	}
}
