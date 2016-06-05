/**
Evan Wang
 */

package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import Controller.Controller;
import Model.Term;
import net.miginfocom.swing.MigLayout;

public class GlossaryPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1627390830118627789L;
	
	
	/*
	 * Data variable
	 */
	Controller controller;
	char[] alph = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
			'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
			'z' };
	int count = 0;
	HashMap<String, JButton> buttonMap;

	/*
	 * Components
	 */
	GlossaryFrame glossaryFrame;// Reference to parent
	JPanel termPanel;
	JTextArea termDetailsArea;
	JScrollPane termScrollPane;
	JScrollPane termDetailsScrollPane;
	ControlPanel cp;
	JFileChooser glossaryOpener;
	FileNameExtensionFilter filter;
	/**
	 * MenuBar
	 */
	JMenuBar mb;
	JMenu fileMenu;
	JMenu editMenu;
	JMenu aboutMenu;
	JMenuItem newItem;
	JMenuItem saveItem;
	JMenuItem saveAsItem;
	JMenuItem openItem;
	JMenuItem quitItem;
	JMenuItem undoItem;
	JMenuItem redoItem;
	JMenuItem cutItem;
	JMenuItem copyItem;
	JMenuItem pasteItem;
	JMenuItem aboutItem;
	JMenuItem helpItem;
	/**
	 * MenuBar
	 */

	/*
	 * finals
	 */
	final Dimension TERM_DETAILS_AREA_MINIMUM_SIZE = new Dimension(300, 124);
	final int SCROLL_INCREMENT = 16;
	final Color LIGHT_GREY_COLOR = new Color(236, 233, 216);

	GlossaryPanel(Controller controller, GlossaryFrame gf)
	{
		this.glossaryFrame = gf;
		this.controller = controller;
		buttonMap = new HashMap<String, JButton>();
		cp = new ControlPanel();
		termDetailsArea = new JTextArea();
		termPanel = new JPanel();

		glossaryOpener = new JFileChooser();
		filter = new FileNameExtensionFilter("Glossary File", "gl");

		// MenuBar
		mb = new JMenuBar();
		fileMenu = new JMenu("File");
		editMenu = new JMenu("Edit");
		aboutMenu = new JMenu("About");
		newItem = new JMenuItem("New");
		saveItem = new JMenuItem("Save");
		saveAsItem = new JMenuItem("Save As");
		openItem = new JMenuItem("Open");
		quitItem = new JMenuItem("Quit");
		undoItem = new JMenuItem("Undo");
		redoItem = new JMenuItem("Redo");
		cutItem = new JMenuItem("Cut");
		copyItem = new JMenuItem("Copy");
		pasteItem = new JMenuItem("Paste");
		aboutItem = new JMenuItem("About");
		helpItem = new JMenuItem("Help");
		// MenuBar

		setupGlossaryPanelLayout();
		setupGlossaryPanelListeners();
	}

	private void setupGlossaryPanelLayout()
	{
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBackground(new Color(255, 255, 255));
		termDetailsArea.setEditable(false);
		termDetailsArea.setText("");
		termDetailsArea.setBorder(BorderFactory
				.createLineBorder(Color.BLACK, 1));
		termPanel.setLayout(new MigLayout("fillx"));
		termPanel.setBackground(LIGHT_GREY_COLOR);
		termScrollPane = new JScrollPane(termPanel);
		termScrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		termScrollPane
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		termDetailsScrollPane = new JScrollPane(termDetailsArea);
		termDetailsScrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		termDetailsScrollPane
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		termDetailsScrollPane.setMinimumSize(TERM_DETAILS_AREA_MINIMUM_SIZE);
//		termScrollPane.setMinimumSize(new Dimension(300, 500));
		cp.setMinimumSize(new Dimension(300, 30));
		termScrollPane.getVerticalScrollBar()
				.setUnitIncrement(SCROLL_INCREMENT);
		this.add(termScrollPane);
		this.add(termDetailsScrollPane);
		this.add(cp);
		mb.add(fileMenu);
		mb.add(editMenu);
		mb.add(aboutMenu);
		fileMenu.add(newItem);
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.add(saveAsItem);
		fileMenu.add(quitItem);
		editMenu.add(undoItem);
		editMenu.add(redoItem);
		editMenu.add(cutItem);
		editMenu.add(copyItem);
		editMenu.add(pasteItem);
		aboutMenu.add(aboutItem);
		aboutMenu.add(helpItem);
		glossaryOpener.setFileFilter(filter);
		getGlossaryFrame().setJMenuBar(mb);
		this.revalidate();
	}

	private void setupGlossaryPanelListeners()
	{
		newItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				showNewGlossaryDialog();
			}
		});

		openItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				showGlossaryOpenDialog();
			}
		});

		saveItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (controller.isNewFile())
					showGlossarySaveAsDialog();
				else
					controller.save();
			}
		});

		saveAsItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				showGlossarySaveAsDialog();
			}
		});

		quitItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				WindowEvent closingEvent = new WindowEvent(getGlossaryFrame(),
						WindowEvent.WINDOW_CLOSING);
				Toolkit.getDefaultToolkit().getSystemEventQueue()
						.postEvent(closingEvent);
			}
		});

		undoItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				JOptionPane.showMessageDialog(null, "Undo");
			}
		});

		redoItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				JOptionPane.showMessageDialog(null, "Redo");
			}
		});

		cutItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				JOptionPane.showMessageDialog(null, "Cut");
			}
		});

		copyItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				JOptionPane.showMessageDialog(null, "Copy");
			}
		});

		pasteItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				JOptionPane.showMessageDialog(null, "Paste");
			}
		});

		aboutItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				JOptionPane
						.showMessageDialog(null,
								"About:\nThis is a glossary program\nGitHub.com\\ewang24\nThank you for your support!");
			}
		});

		helpItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				JOptionPane.showMessageDialog(null, "Help");
			}
		});
	}

	private class ControlPanel extends JPanel
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 8258782670474633839L;

		private JButton newTerm;
		private JButton removeTerm;
		private JLabel countLabel;

		public ControlPanel()
		{
			newTerm = new JButton("+");
			removeTerm = new JButton("-");
			countLabel = new JLabel("Entries: ");

			setupLayout();
			setupControlPanelListeners();
		}

		private void setupLayout()
		{
			newTerm.setToolTipText("Add New Term");
			removeTerm.setToolTipText("Remove Term");
			this.setBackground(new Color(250, 255, 255));
			this.add(countLabel);
			this.add(newTerm);
			this.add(removeTerm);
		}

		private void setupControlPanelListeners()
		{
			newTerm.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					addTerm();
				}
			});

			removeTerm.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					removeTerm();
				}
			});

		}

		public void updateSize()
		{
			countLabel.setText("Entries: " + getGlossarySize());
		}
	}

	public void displaySortedKeys(String[] keys)
	{
		for (int i = 0; i < keys.length; i++)
		{
			System.out.println(keys[i]);
		}
		int currLetter = 0;

		if (alph[currLetter] == Character.toLowerCase(keys[0].charAt(0)))
			displayLetterMarker(alph[currLetter]);

		for (int i = 0; i < keys.length; i++)
		{
			if (alph[currLetter] != Character.toLowerCase(keys[i].charAt(0)))
			{
				while (alph[currLetter] != Character.toLowerCase(keys[i]
						.charAt(0)))
				{
					currLetter++;
				}

				displayLetterMarker(alph[currLetter]);
			}

			displayKey(keys[i]);
		}

		displayTermDefinition(keys[0]);
		cp.updateSize();
		this.repaint();
		this.revalidate();
	}

	private void displayLetterMarker(char c)
	{
		JLabel j = new JLabel("\n" + Character.toUpperCase(c) + ":");
		j.setFont(new Font(j.getFont().getFontName(), j.getFont().BOLD, j
				.getFont().getSize()));
		termPanel.add(j, "wrap,push");
	}

	/**
	 * Called to add a new button to the display. Each button represents one key
	 * 
	 * @param key
	 *            to be added to display as button
	 */
	private void displayKey(final String key)
	{
		JButton newTermButton = new JButton(key);

		newTermButton.setHorizontalAlignment(SwingConstants.LEFT);
		newTermButton.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		newTermButton.setBorderPainted(false);
		newTermButton.setContentAreaFilled(false);
		newTermButton.setFocusPainted(false);

		newTermButton.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		    	if(evt.getButton()==java.awt.event.MouseEvent.BUTTON3)
		    	{
		    		System.out.println("Got right click");
		    	}
		    }
		});
		newTermButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				displayTermDefinition(key);
			}
		});

		termPanel.add(newTermButton, "wrap,grow");

		buttonMap.put(key, newTermButton);
	}

	private void displayTermDefinition(String key)
	{
		termDetailsArea.setText(" " + key + ":\n\n\t"
				+ controller.fetchTermForKey(key).getDefinition());
		this.repaint();
		this.revalidate();
	}

	private int getGlossarySize()
	{
		return controller.glossarySize();
	}

	private void removeTerm()
	{
		String r = JOptionPane.showInputDialog("What term do you want to remove?");
		if(!controller.remove(r))
		{
			JOptionPane.showMessageDialog(null, "Term does not exist!");
		}
		updateTermDisplay();
	}

	private void addTerm()
	{
		NewFrame newFrame = new NewFrame(this);
		newFrame.start();
	}

	public void updateWithNewTerm(String key)
	{
		updateTermDisplay();
		displayTermDefinition(key);
		

	}
	
	public void updateTermDisplay()
	{
		termPanel.removeAll();
		displaySortedKeys(controller.getGlossaryKeys());
		termPanel.repaint();
		termPanel.revalidate();
	}

	/**
	 * 
	 * @author Evan Wang Frame created when a user wants to add a new term to
	 *         the glossary. Has a private panel inner class
	 */
	private class NewFrame extends JFrame
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 6514094780344647846L;
		private GlossaryPanel gp;
		private NewPanel newPanel;

		public NewFrame(GlossaryPanel gp)
		{
			this.gp = gp;
			newPanel = new NewPanel();
		}

		public void start()
		{
			setupLayout();
		}

		private void setupLayout()
		{
			this.setSize(new Dimension(250, 200));
			this.setResizable(false);
			this.setTitle("New Term");
			this.setLocationRelativeTo(gp);
			this.setVisible(true);
			this.setContentPane(newPanel);

		}

		public void exitFrame()
		{
			this.setVisible(false);
			this.dispose();
		}

		private class NewPanel extends JPanel
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = -8510517407098991637L;

			private JTextArea newKeyArea;
			private JTextArea newKeyDetailsArea;
			private JLabel newKeyLabel;
			private JLabel newKeyDetailsLabel;
			private JScrollPane newKeyPane;
			private JScrollPane newKeyDetailsPane;
			private JPanel controlPanel;
			private JButton submitButton;
			private JButton cancelButton;

			public NewPanel()
			{
				newKeyArea = new JTextArea();
				newKeyDetailsArea = new JTextArea();
				newKeyLabel = new JLabel("Enter new key:");
				newKeyDetailsLabel = new JLabel("Enter details:");
				newKeyPane = new JScrollPane(newKeyArea);
				controlPanel = new JPanel();
				submitButton = new JButton("Create");
				cancelButton = new JButton("Cancel");
				setupLayout();
				setupListeners();
			}

			private void setupLayout()
			{
				this.setBackground(LIGHT_GREY_COLOR);
				this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
				newKeyDetailsPane = new JScrollPane(newKeyDetailsArea);				
				newKeyPane
						.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
				newKeyPane
						.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				newKeyDetailsPane
						.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
				newKeyDetailsPane
						.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				newKeyArea.setBorder(BorderFactory.createLineBorder(
						Color.BLACK, 1));
				newKeyDetailsArea.setBorder(BorderFactory.createLineBorder(
						Color.BLACK, 1));
				newKeyArea.setText("");
				newKeyDetailsArea.setText("");

				this.add(newKeyLabel);
				this.add(newKeyPane);
				this.add(newKeyDetailsLabel);
				this.add(newKeyDetailsPane);
				this.add(controlPanel);
				controlPanel.add(submitButton);
				controlPanel.add(cancelButton);
			}

			private void setupListeners()
			{
				submitButton.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						if (newKeyArea.getText().contains(":::")
								|| newKeyDetailsArea.getText().contains(":::"))
						{
							JOptionPane.showMessageDialog(newPanel,
									"Term cannot contain the sequence ':::'");
							return;
						}
						if (newKeyArea.getText().equals("")
								|| newKeyArea.getText() == null)
						{
							JOptionPane.showMessageDialog(newPanel,
									"Key cannot be blank");
							return;
						}
						if (newKeyDetailsArea.getText().equals("")
								|| newKeyDetailsArea.getText() == null)
						{
							JOptionPane.showMessageDialog(newPanel,
									"Definition cannot be blank");
							return;
						}
						if (newKeyArea.getText().charAt(
								newKeyArea.getText().length() - 1) == ' ')
						{
							if (JOptionPane
									.showConfirmDialog(newPanel,
											"Trailing spaces on keys are removed.\nIs this okay?") != JOptionPane.YES_OPTION)
							{
								return;
							}
						}
						if (newKeyArea.getText().charAt(0) == ' ')
						{
							if (JOptionPane
									.showConfirmDialog(newPanel,
											"Leading spaces on keys are removed.\nIs this okay?") != JOptionPane.YES_OPTION)
							{
								return;
							}
						}

						if (submitData())
							closeWindow();
						else
							JOptionPane.showMessageDialog(newPanel,
									"Key is already in glossary");
					}
				});

				cancelButton.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						closeWindow();
					}
				});
			}

			private void closeWindow()
			{
				((NewFrame) this.getParent().getParent().getParent())
						.exitFrame();
			}

			private boolean submitData()
			{
				String newKey = newKeyArea.getText().trim();
				if (controller.newEntry(newKey,
						new Term(newKeyDetailsArea.getText())))
				{
					updateWithNewTerm(newKey);
					return true;
				}
				return false;
			}
		}
	}

	private GlossaryFrame getGlossaryFrame()
	{
		return glossaryFrame;
	}

	private void showNewGlossaryDialog()
	{
		controller.newGlossary();
	}

	private void showGlossaryOpenDialog()
	{
		if (glossaryOpener.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
		{
			if (!glossaryOpener.getSelectedFile().exists())
			{
				JOptionPane.showMessageDialog(this, "File does not exist");
				return;
			}
			controller.open(glossaryOpener.getSelectedFile().getAbsolutePath());
		}
	}

	private void showGlossarySaveAsDialog()
	{
		if (glossaryOpener.showSaveDialog(this) == JFileChooser.APPROVE_OPTION)
		{
			if (glossaryOpener.getSelectedFile().exists())
			{
				if (JOptionPane.showConfirmDialog(this,
						"This file already exists.\nOverwrite?") != JOptionPane.YES_OPTION)
					return;
			}
			String save = glossaryOpener.getSelectedFile().getAbsolutePath();
			if (!save.substring(save.length() - 3, save.length() - 1).equals(
					".gl"))
			{
				save += ".gl";
			}
			controller.saveAs(save);
		}
	}
}
