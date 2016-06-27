/**
Evan Wang
This is the panel that holds all of the components for the GUI
The three main pieces are:
	-termPanel, displays all of the terms in the glossary
	-termDetailsArea, displays the details of selected ter
	-mainControlPanel, holds the main controls for the program
 */

package View;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
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

	/**
	 * References
	 */
	Controller controller;// Reference to controller object which controls the
							// program
	GlossaryFrame glossaryFrame;// Reference to parent

	/*
	 * Data variables
	 */
	char[] alph = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
	int count = 0;
	HashMap<String, JButton> buttonMap;
	SpecialCharacterChooser scc;
	private String selectedKey = "";
	String[] currentKeys;

	/*
	 * Components
	 */
	JPanel termPanel;
	JTextArea termDetailsArea;
	JScrollPane termScrollPane;
	JScrollPane termDetailsScrollPane;
	JPanel seeAlsoPanel;
	JLabel seeAlsoLabel;
	ControlPanel mainControlPanel;
	JFileChooser glossaryOpener;
	FileNameExtensionFilter openSaveFilter;
	JFileChooser glossaryExporter;
	FileNameExtensionFilter exportFilter;

	/**
	 * MenuBar
	 */
	JMenuBar menuBar;
	JMenu fileMenu;
	JMenu editMenu;
	JMenu aboutMenu;
	JMenuItem newItem;
	JMenuItem saveItem;
	JMenuItem saveAsItem;
	JMenuItem openItem;
	JMenuItem exportItem;
	JMenuItem quitItem;
	JMenuItem undoItem;
	JMenuItem redoItem;
	JMenuItem cutItem;
	JMenuItem copyItem;
	JMenuItem pasteItem;
	JMenuItem aboutItem;
	JMenuItem helpItem;

	/*
	 * finals
	 */
	final Dimension TERM_DETAILS_AREA_MINIMUM_SIZE = new Dimension(300, 124);
	final Dimension MAIN_CONTROL_PANEL_SIZE = new Dimension(300, 30);
	final int TERM_SCROLL_INCREMENT = 16;
	final Color LIGHT_GREY_COLOR = new Color(236, 233, 216);

	GlossaryPanel(Controller controller, GlossaryFrame gf)
	{
		/**
		 * References
		 */
		this.glossaryFrame = gf;
		this.controller = controller;

		/**
		 * Data
		 */
		buttonMap = new HashMap<String, JButton>();
		scc = new SpecialCharacterChooser(controller.getUnicodeModeler());

		/**
		 * Components
		 */
		mainControlPanel = new ControlPanel();
		termDetailsArea = new JTextArea();
		termPanel = new JPanel();
		termScrollPane = new JScrollPane(termPanel);
		termDetailsScrollPane = new JScrollPane(termDetailsArea);
		seeAlsoPanel = new JPanel();
		seeAlsoLabel = new JLabel("See Also:");

		/**
		 * FileChooser
		 */
		glossaryOpener = new JFileChooser();
		openSaveFilter = new FileNameExtensionFilter("Glossary File", "gl");

		glossaryExporter = new JFileChooser();
		exportFilter = new FileNameExtensionFilter("Text File", "txt");

		// MenuBar
		menuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		editMenu = new JMenu("Edit");
		aboutMenu = new JMenu("About");
		newItem = new JMenuItem("New");
		saveItem = new JMenuItem("Save");
		saveAsItem = new JMenuItem("Save As");
		openItem = new JMenuItem("Open");
		exportItem = new JMenuItem("Export As Text");
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
		/**
		 * GlossaryPanel
		 */
		this.setLayout(new MigLayout("fill,ins 0,hidemode 3,debug", "", "[fill]0[fill]"));
		this.setBackground(new Color(255, 255, 255));

		/**
		 * TermDetailsArea
		 */
		termDetailsArea.setEditable(false);
		termDetailsArea.setText("");
		termDetailsArea.setLineWrap(true);
		termDetailsArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		termDetailsArea.setFont(glossaryFrame.getDefaultFont());
		termDetailsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		termDetailsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		/**
		 * TermPanel
		 */
		termPanel.setLayout(new MigLayout("fillx"));
		termPanel.setBackground(LIGHT_GREY_COLOR);
		termScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		termScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		termScrollPane.getVerticalScrollBar().setUnitIncrement(TERM_SCROLL_INCREMENT);
		// Instructions added at the beginning
		termPanel.add(new JLabel("Welcome to SimpleGlossary!"), "wrap");
		termPanel.add(new JLabel("Add terms to get started, or load an old glossary"), "wrap");

		/**
		 * SeeAlsoPanel
		 */
		seeAlsoPanel.setBackground(Color.white);
		seeAlsoPanel.setLayout(new MigLayout("fill", "[grow]", "[grow]"));
		seeAlsoPanel.add(seeAlsoLabel);
		seeAlsoPanel.setVisible(false);

		/**
		 * Menubar
		 */
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(aboutMenu);
		fileMenu.add(newItem);
		fileMenu.add(openItem);
		fileMenu.add(exportItem);
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
		getGlossaryFrame().setJMenuBar(menuBar);

		/**
		 * Misc
		 */
		glossaryOpener.setFileFilter(openSaveFilter);
		glossaryExporter.setFileFilter(exportFilter);
		glossaryExporter.setDialogTitle("Export");

		/**
		 * Add all components
		 */
		this.add(termScrollPane, "h 50%, grow,wrap");
		this.add(termDetailsScrollPane, "h 25%: 44%, push,grow,wrap");
		this.add(seeAlsoPanel, "h 30!,grow,wrap");
		this.add(mainControlPanel, "h 6%, center,grow");

		this.revalidate();
	}

	/**
	 * Set up the listeners for any clickable object that is the responsibility
	 * of GlossaryPanel
	 */
	private void setupGlossaryPanelListeners()
	{
		/**
		 * Menubar listeners
		 */

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

		exportItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				showGlossaryExportDialog();
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
				WindowEvent closingEvent = new WindowEvent(getGlossaryFrame(), WindowEvent.WINDOW_CLOSING);
				Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closingEvent);
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
				JOptionPane.showMessageDialog(null, "About:\nThis is a glossary program\nGitHub.com\\ewang24\nThank you for your support!");
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

	/**
	 * @author Evan Wang Private class used to implement a panel that holds the
	 *         main controls for the program. Functionality outlined by the
	 *         following: -countLabel, number of entries -newTerm, add a new
	 *         term to the glossary -removeTerm, remove term from glosssary
	 */
	private class ControlPanel extends JPanel
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 8258782670474633839L;

		private JButton newTerm;
		private JButton removeTerm;
		private JButton editTerm;
		private JLabel countLabel;

		public ControlPanel()
		{
			newTerm = new JButton("+");
			removeTerm = new JButton("-");
			editTerm = new JButton("~");
			countLabel = new JLabel("Entries: ");

			setupLayout();
			setupControlPanelListeners();
		}

		private void setupLayout()
		{
			this.setBackground(new Color(255, 255, 255));

			newTerm.setToolTipText("Add New Term");
			removeTerm.setToolTipText("Remove Term");
			editTerm.setToolTipText("Edit Term");

			removeTerm.setEnabled(false);
			editTerm.setEnabled(false);

			this.add(countLabel);
			this.add(newTerm);
			this.add(removeTerm);
			this.add(editTerm);
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

			editTerm.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					editTerm();
				}
			});

		}

		/**
		 * Updates the label displaying the number of terms in the glossary by
		 * querying the controller to ask for the size
		 */
		public void updateSizeLabel()
		{
			countLabel.setText("Entries: " + getGlossarySize());
		}

		public void enableEdit()
		{
			editTerm.setEnabled(true);
		}

		public void enableRemove()
		{
			removeTerm.setEnabled(true);
		}
	}

	/**
	 * Display all the terms in a glossary, interspacing letter headers to
	 * separate the sorted list. Ex:
	 * 
	 * A:<-letter header animal B: bug
	 * 
	 * @param keys
	 *            , the list of keys in the glossary
	 */
	public void displaySortedKeys(String[] keys)
	{
		if (keys.length != 0)
		{
			currentKeys = keys;
			boolean reachedEnd = false;

			int currLetter = 0;

			// Check if we need to add an 'A' letter header
			if (alph[currLetter] == Character.toLowerCase(controller.getUnicodeModeler().getBaseCharacterString(keys[0]).charAt(0)))
				displayLetterMarker(alph[currLetter]);

			// Display all keys
			for (int i = 0; i < keys.length; i++)
			{
				// Check if a new letter header needs to be updated and add it
				// if so.
				if (!reachedEnd && alph[currLetter] != Character.toLowerCase(controller.getUnicodeModeler().getBaseCharacterString(keys[i]).charAt(0)))
				{
					while (!reachedEnd && alph[currLetter] != Character.toLowerCase(controller.getUnicodeModeler().getBaseCharacterString(keys[i]).charAt(0)))
					{
						currLetter++;
						if (currLetter >= alph.length)
						{
							reachedEnd = true;
							displayMarker("Other");
						}
					}

					if (!reachedEnd)
						displayLetterMarker(alph[currLetter]);
				}

				displayKey(keys[i]);
			}

			displayTermInformation(keys[0]);
		}
		else
		{
			termDetailsArea.setText("");
		}
		mainControlPanel.updateSizeLabel();
		this.repaint();
		this.revalidate();
	}

	/**
	 * Display a marker with one letter
	 * 
	 * @param c
	 *            the character to display
	 */
	private void displayLetterMarker(char c)
	{
		displayMarker("\n" + Character.toUpperCase(c));
	}

	/**
	 * Display a marker to separate an alphabetically sorted list
	 * 
	 * @param marker
	 *            , the text for the marker
	 */
	private void displayMarker(String marker)
	{
		JLabel j = new JLabel(marker + ":");
		j.setFont(new Font(glossaryFrame.getDefaultFont().getFontName(), glossaryFrame.getDefaultFont().BOLD, glossaryFrame.getDefaultFont().getSize()));
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

		/**
		 * Format new button
		 */
		newTermButton.setHorizontalAlignment(SwingConstants.LEFT);
		newTermButton.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		newTermButton.setBorderPainted(false);
		newTermButton.setContentAreaFilled(false);
		newTermButton.setFocusPainted(false);
		newTermButton.setFont(glossaryFrame.getDefaultFont());

		/**
		 * listener for right click (currently does nothing)
		 */
		newTermButton.addMouseListener(new java.awt.event.MouseAdapter()
		{
			public void mouseClicked(java.awt.event.MouseEvent evt)
			{
				if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3)
				{
					System.out.println("Got right click");
				}
			}
		});

		/*
		 * listener for normal click
		 */
		newTermButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				displayTermInformation(key);
			}
		});

		termPanel.add(newTermButton, "wrap,grow");

		buttonMap.put(key, newTermButton);
	}

	/**
	 * @param key
	 *            , the key to display the details for. The details are found by
	 *            querying the controller and they are then displayed
	 */
	private void displayTermInformation(String key)
	{
		Term t = controller.fetchTermForKey(key);
		mainControlPanel.enableEdit();
		mainControlPanel.enableRemove();
		selectedKey = key;
		termDetailsArea.setText(" " + key + ":\n\n\t" + t.getDefinition());

		displaySeeAlsoList(t.getSeeAlsoList());

		this.repaint();
		this.revalidate();
	}

	/**
	 * Called by displayTermInformation
	 * 
	 * @param a
	 *            the array of see also terms to display
	 */
	private void displaySeeAlsoList(String[] a)
	{
		if (a.length != 0)
		{
			seeAlsoPanel.removeAll();
			seeAlsoPanel.add(seeAlsoLabel);
			for (String e : a)
			{
				/**
				 * Format new button
				 */
				final JButton newSeeAlsoButton = new JButton(e);
				newSeeAlsoButton.setHorizontalAlignment(SwingConstants.LEFT);
				newSeeAlsoButton.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
				newSeeAlsoButton.setBorderPainted(false);
				newSeeAlsoButton.setContentAreaFilled(false);
				newSeeAlsoButton.setFocusPainted(false);
				newSeeAlsoButton.setFont(glossaryFrame.getDefaultFont());

				seeAlsoPanel.add(newSeeAlsoButton);
				newSeeAlsoButton.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e_)
					{
						displayTermInformation(newSeeAlsoButton.getText());
					}
				});
			}
			seeAlsoPanel.setVisible(true);
		}
		else
		{
			seeAlsoPanel.setVisible(false);
		}
	}

	/**
	 * @return queries the controller for the glossary size and returns it
	 */
	private int getGlossarySize()
	{
		return controller.glossarySize();
	}

	/**
	 * Brings up the dialog to remove a term from the glossary and refreshes the
	 * termDetailsArea if the entry displayed was removed.
	 */
	private void removeTerm()
	{
		RemoveFrame removeFrame = new RemoveFrame(this);
		removeFrame.start();
	}

	/**
	 * brings up the dialog to add a term to the glossary
	 */
	private void addTerm()
	{
		NewFrame newFrame = new NewFrame(this,selectedKey);
		newFrame.start();
	}

	/**
	 * brings up the dialog to edit a term in the glossary
	 */
	private void editTerm()
	{
		EditFrame editFrame = new EditFrame(this,selectedKey);
		editFrame.start();
	}

	/**
	 * Add a new term to the display. Calls updateTermDisplay to update the
	 * display so it shows the new term.
	 * 
	 * @param key
	 *            , the key to be displayed for the new term
	 */
	public void updateWithTermToDisplay(String key)
	{
		updateTermDisplay();
		displayTermInformation(key);

	}

	/**
	 * refreshes the term display by removing all terms from the view and adding
	 * them back in
	 */
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
		
		String selectedKeyAdd;
		
		private Set<String> seeAlsoList = new HashSet<String>();

		public NewFrame(GlossaryPanel gp, String selectedKey)
		{
			this.selectedKeyAdd = selectedKey;
			this.gp = gp;
			newPanel = new NewPanel();
		}

		public void start()
		{
			setupLayout();
		}

		private void setupLayout()
		{
			this.setSize(new Dimension(250, 275));
			this.setResizable(false);
			this.setTitle("New Term");
			this.setLocationRelativeTo(gp);
			this.setVisible(true);
			this.setContentPane(newPanel);
		}

		/**
		 * Hides the frame and frees up the memory it was using
		 */
		public void exitFrame()
		{
			this.setVisible(false);
			this.dispose();
		}

		/**
		 * @author Evan Wang private inner class used to hold the components for
		 *         adding a new term
		 */
		private class NewPanel extends JPanel
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = -8510517407098991637L;

			private JTextArea newKeyArea;
			private JTextArea newKeyDetailsArea;
			private JPanel newSeeAlsoTermPanel;
			private JLabel newKeyLabel;
			private JLabel newKeyDetailsLabel;
			private JLabel seeAlsoLabel;
			private JScrollPane newKeyPane;
			private JScrollPane newKeyDetailsPane;
			private JScrollPane seeAlsoPane;
			private JPanel controlPanel;
			private JButton submitButton;
			private JButton cancelButton;
			private JButton specialCharacterButton;
			private JButton addSeeAlsoButton;
			private JComboBox<String> seeAlsoBox;

			public NewPanel()
			{
				newKeyArea = new JTextArea();
				newKeyDetailsArea = new JTextArea();
				newSeeAlsoTermPanel = new JPanel();
				newKeyLabel = new JLabel("Enter new key:");
				newKeyDetailsLabel = new JLabel("Enter details:");
				seeAlsoLabel = new JLabel("See Also:");
				newKeyPane = new JScrollPane(newKeyArea);
				controlPanel = new JPanel();
				submitButton = new JButton("Create");
				cancelButton = new JButton("Cancel");
				specialCharacterButton = new JButton("\u03A0");
				newKeyDetailsPane = new JScrollPane(newKeyDetailsArea);
				seeAlsoPane = new JScrollPane(newSeeAlsoTermPanel);
				addSeeAlsoButton = new JButton("Add");
				seeAlsoBox = new JComboBox<String>();

				setupLayout();
				setupListeners();
				updateSeeAlsoBox();
			}

			private void setupLayout()
			{
				/**
				 * NewPanel
				 */
				this.setBackground(LIGHT_GREY_COLOR);
				this.setLayout(new MigLayout("fill"));

				/**
				 * newKeyDetailsPane
				 */
				newKeyDetailsArea.setFont(glossaryFrame.getDefaultFont());
				newKeyDetailsArea.setText("");
				newKeyDetailsArea.setLineWrap(true);
				newKeyDetailsPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
				newKeyDetailsPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

				/**
				 * newKeyArea
				 */
				newKeyArea.setFont(glossaryFrame.getDefaultFont());
				newKeyArea.setText("");
				newKeyArea.setLineWrap(true);
				newKeyArea.requestFocusInWindow();
				newKeyPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
				newKeyPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				/**
				 * seeAlsoArea
				 */
				newSeeAlsoTermPanel.setToolTipText("Click an entry to remove it");
				newSeeAlsoTermPanel.setBackground(Color.white);
				seeAlsoPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
				seeAlsoPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

				/**
				 * ControlPanel & components
				 */
				specialCharacterButton.setToolTipText("Insert Special Character");
				specialCharacterButton.setFocusable(false);
				submitButton.setFocusable(false);
				cancelButton.setFocusable(false);
				controlPanel.setBackground(LIGHT_GREY_COLOR);
				controlPanel.add(specialCharacterButton);
				controlPanel.add(submitButton);
				controlPanel.add(cancelButton);

				if(controller.isEmpty())
				{
					newSeeAlsoTermPanel.setEnabled(false);
					addSeeAlsoButton.setEnabled(false);
					seeAlsoBox.setEnabled(!true);
				}
				
				/**
				 * Add all components
				 */
				this.add(newKeyLabel, "wrap");
				this.add(newKeyPane, "grow, spanx 2, h 35, wrap");
				this.add(newKeyDetailsLabel, "wrap");
				this.add(newKeyDetailsPane, "grow, spanx 2, h 35, wrap");
				this.add(seeAlsoLabel, "wrap");
				this.add(addSeeAlsoButton, "w 60!");
				this.add(seeAlsoPane, "h 42!, grow,push,wrap");
				this.add(seeAlsoBox, "grow, spanx 2, wrap");
				this.add(controlPanel, "spanx 2, grow");
			}

			/**
			 * Set up listeneres for all components that need one which are
			 * responsibilities of NewPanel
			 */
			private void setupListeners()
			{
				submitButton.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						if (newKeyArea.getText().contains(Controller.getFileDelimiter()) || newKeyDetailsArea.getText().contains(Controller.getFileDelimiter()))
						{
							JOptionPane.showMessageDialog(newPanel, "Term cannot contain the sequence '"+Controller.getFileDelimiter()+"'");
							return;
						}
						if (newKeyArea.getText().trim().equals("") || newKeyArea.getText() == null)
						{
							JOptionPane.showMessageDialog(newPanel, "Key cannot be blank");
							return;
						}
						if (newKeyDetailsArea.getText().trim().equals("") || newKeyDetailsArea.getText() == null)
						{
							JOptionPane.showMessageDialog(newPanel, "Definition cannot be blank");
							return;
						}
						if (newKeyArea.getText().charAt(newKeyArea.getText().length() - 1) == ' ')
						{
							if (JOptionPane.showConfirmDialog(newPanel, "Trailing spaces on keys are removed.\nIs this okay?") != JOptionPane.YES_OPTION)
							{
								return;
							}
						}
						if (newKeyArea.getText().charAt(0) == ' ')
						{
							if (JOptionPane.showConfirmDialog(newPanel, "Leading spaces on keys are removed.\nIs this okay?") != JOptionPane.YES_OPTION)
							{
								return;
							}
						}

						if (submitData())
							closeWindow();
						else
							return;
					}
				});

				cancelButton.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						closeWindow();
					}
				});
				
				addSeeAlsoButton.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						addSeeAlsoTerm();
					}
				});

				specialCharacterButton.addActionListener(new ActionListener()
				{
					/**
					 * Check to make sure that the component with focus is a
					 * JTextArea, since they are the only editable components in
					 * NewPanel
					 */
					public void actionPerformed(ActionEvent e)
					{
						Component focused = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
						if (focused instanceof JTextArea)
						{
							setFocusedToFocused(focused);
							showSpecialCharacterSelect();
						}

					}
				});
			}

			private void updateSeeAlsoBox()
			{
				seeAlsoBox.removeAll();
				seeAlsoBox.addItem("Select an item");
				String[] keys = controller.getGlossaryKeys();
				for (String e : keys)
				{
					seeAlsoBox.addItem(e);
				}
				this.repaint();
				this.revalidate();
			}
			
			private void addSeeAlsoTerm()
			{
				if(seeAlsoBox.getSelectedIndex()!=0)
				{
					
					seeAlsoList.add((String) seeAlsoBox.getSelectedItem());
					System.out.println(seeAlsoList.size());
					refreshSeeAlsoTerms();
					
				}
				return;
			}
			
			private void refreshSeeAlsoTerms()
			{
				System.out.println(seeAlsoList.size());
				newSeeAlsoTermPanel.removeAll();
				for(String e : seeAlsoList)
				{
//					ImageIcon icon = new ImageIcon("Resources/redX.png");
//			        JButton button = new JButton();
//			        TextIcon text = new TextIcon(button, "Maybe");
//			        CompoundIcon compound = new CompoundIcon(CompoundIcon.Axis.X_AXIS, button.getIconTextGap(), icon, text);
					
					final TermButton newSeeAlsoTerm = new TermButton(e,e);
//					newSeeAlsoTerm.setIcon(icon);
					newSeeAlsoTerm.setFocusable(false);
					newSeeAlsoTerm.setHorizontalAlignment(SwingConstants.LEFT);
					newSeeAlsoTerm.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
					newSeeAlsoTerm.setBorderPainted(false);
					newSeeAlsoTerm.setContentAreaFilled(false);
					newSeeAlsoTerm.setFocusPainted(false);
					newSeeAlsoTerm.setFont(glossaryFrame.getDefaultFont());
					
					System.out.println(newSeeAlsoTerm.getSize());
					newSeeAlsoTerm.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							System.out.println(seeAlsoList.remove(newSeeAlsoTerm.getStoredText()) ? "Term removed" : "Term not found");
							refreshSeeAlsoTerms();
						}
					});
					newSeeAlsoTermPanel.add(newSeeAlsoTerm);
				}
				newSeeAlsoTermPanel.repaint();
				newSeeAlsoTermPanel.revalidate();
			}

			/**
			 * Close the NewPanel and free up its memory
			 */
			private void closeWindow()
			{
				((NewFrame) this.getParent().getParent().getParent()).exitFrame();
			}

			/**
			 * submit the data to the controller.
			 * 
			 * @return true if the submit suceeded.
			 */
			private boolean submitData()
			{
				String newKey = newKeyArea.getText().trim().replace("\n", " ");

				
				String[] sal = new String[seeAlsoList.size()];
				if (seeAlsoList.size() != 0)
				{
					int i = 0;
					Iterator<String> sali = seeAlsoList.iterator();
					while(sali.hasNext())
					{
						
						String s = sali.next();
						if (controller.fetchTermForKey(s) == null)
						{
							JOptionPane.showMessageDialog(this, s + " is not in the glossary!");
							return false;
						}
						else
							sal[i]=s;
						i++;
					}
				}
				
				if (controller.newEntry(newKey, new Term(newKeyDetailsArea.getText().replace("\n", " "), sal, null)))
				{
					updateWithTermToDisplay(newKey);
					return true;
				}
				else
				{
					JOptionPane.showMessageDialog(newPanel, "Key is already in glossary");
				}
				return false;
			}
		}
	}

	/**
	 * 
	 * @author Evan Wang Frame created when a user wants to remove a term from
	 *         the glossary. Has a private inner class
	 *
	 */
	private class RemoveFrame extends JFrame
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 5698864117592206163L;

		GlossaryPanel gp;
		RemovePanel removePanel;

		public RemoveFrame(GlossaryPanel gp_)
		{
			gp = gp_;
			removePanel = new RemovePanel();
		}

		private void start()
		{
			setupLayout();
		}

		private void setupLayout()
		{
			this.setTitle("Remove Term");
			this.setLocationRelativeTo(gp);
			this.setVisible(true);
			this.setSize(new Dimension(300, 100));
			this.setResizable(false);
			this.setContentPane(removePanel);
		}

		private class RemovePanel extends JPanel
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 6656192500421094957L;

			private JTextArea customRemoveArea;
			private JComboBox<String> removeList;
			private JButton submitButton;
			private JButton cancelButton;
			private JButton specialCharacterButton;

			public RemovePanel()
			{
				customRemoveArea = new JTextArea("");
				removeList = new JComboBox<String>();
				specialCharacterButton = new JButton("\u03A0");
				submitButton = new JButton("Remove");
				cancelButton = new JButton("Cancel");
				setupLayout();
				setupListeners();
			}

			private void setupLayout()
			{
				this.setLayout(new MigLayout("fill, gap 7px 7px, insets 5"));

				specialCharacterButton.setFocusable(false);
				customRemoveArea.setFont(glossaryFrame.getDefaultFont());
				loadTerms();

				this.add(removeList, "grow");
				this.add(customRemoveArea, "grow");
				this.add(specialCharacterButton, "wrap");
				this.add(submitButton, "grow");
				this.add(cancelButton, "grow");

				customRemoveArea.requestFocus();

			}

			private void setupListeners()
			{
				cancelButton.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						closeWindow();
					}
				});

				submitButton.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						removeTerm();
					}
				});

				specialCharacterButton.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						Component focused = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
						if (focused instanceof JTextArea)
						{
							setFocusedToFocused(focused);
							showSpecialCharacterSelect();
						}
					}
				});
			}

			private void loadTerms()
			{
				removeList.addItem("Select a term");

				if (currentKeys != null && currentKeys.length > 0)
				{
					for (String e : currentKeys)
					{
						removeList.addItem(e);
					}
				}

				this.repaint();
				this.revalidate();
			}

			private void removeTerm()
			{
				if (!customRemoveArea.getText().equals(""))
				{
					if (!controller.remove(customRemoveArea.getText()))
					{
						JOptionPane.showMessageDialog(this, "Term is not in glossary!");
						return;
					}
				}
				else if (removeList.getSelectedIndex() != 0)
				{
					if (!controller.remove(removeList.getSelectedItem().toString()))
					{
						JOptionPane.showMessageDialog(this, "Term is not in glossary!");
						return;
					}

				}
				else
				{
					JOptionPane.showMessageDialog(this, "Must enter term to be removed!");
					return;
				}

				updateTermDisplay();
				closeWindow();
			}
		}

		public void closeWindow()
		{
			this.setVisible(false);
			this.dispose();
		}
	}

	/**
	 * @author Evan Wang Frame created when a user wants to edit a term in the
	 *         glossary. Has a private inncer class.
	 *
	 */
	private class EditFrame extends JFrame
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 6514094780344647846L;

		private GlossaryPanel gp;
		private EditPanel editPanel;
		String selectedKey;
		
		public EditFrame(GlossaryPanel gp, String selectedKey)
		{
			this.selectedKey = selectedKey;
			
			this.gp = gp;
			editPanel = new EditPanel();
		}

		public void start()
		{
			setupLayout();
		}

		private void setupLayout()
		{
			this.setSize(new Dimension(250, 265));
			this.setResizable(false);
			this.setTitle("Edit Term");
			this.setLocationRelativeTo(gp);
			this.setVisible(true);
			this.setContentPane(editPanel);
		}

		/**
		 * Hides the frame and frees up the memory it was using
		 */
		public void exitFrame()
		{
			this.setVisible(false);
			this.dispose();
		}

		/**
		 * @author Evan Wang private inner class used to hold the components for
		 *         adding a new term
		 */
		private class EditPanel extends JPanel
		{
			
			/**
			 * 
			 */
			private static final long serialVersionUID = -8510517407098991637L;

			private JTextArea editKeyArea;
			private JTextArea editKeyDetailsArea;
			private JLabel editKeyLabel;
			private JLabel editKeyDetailsLabel;
			private JScrollPane editKeyPane;
			private JScrollPane editKeyDetailsPane;
			private JPanel controlPanel;
			private JButton submitButton;
			private JButton cancelButton;
			private JButton specialCharacterButton;
			private JPanel newSeeAlsoTermPanel;
			private JScrollPane seeAlsoPane;
			private JButton addSeeAlsoButton;
			private JComboBox<String> seeAlsoBox;
			private JLabel seeAlsoLabel;

			private Set<String> seeAlsoList = new HashSet<String>();
			
			public EditPanel()
			{
				editKeyArea = new JTextArea();
				editKeyDetailsArea = new JTextArea();
				editKeyLabel = new JLabel("Edit key:");
				editKeyDetailsLabel = new JLabel("Edit details:");
				editKeyPane = new JScrollPane(editKeyArea);
				controlPanel = new JPanel();
				submitButton = new JButton("Update");
				cancelButton = new JButton("Cancel");
				specialCharacterButton = new JButton("\u03A0");
				editKeyDetailsPane = new JScrollPane(editKeyDetailsArea);
				newSeeAlsoTermPanel = new JPanel();
				seeAlsoPane = new JScrollPane(newSeeAlsoTermPanel);
				addSeeAlsoButton = new JButton("Add");
				seeAlsoBox = new JComboBox<String>();
				seeAlsoLabel = new JLabel("See Also:");
				
				updateSeeAlsoBox();
				loadSeeAlsoList();
				setupLayout();
				setupListeners();
			}

			private void loadSeeAlsoList()
			{
				String[] s = controller.getSeeAlsoListForKey(selectedKey);
				for(String e : s)
				{
					seeAlsoList.add(e);
				}
			}
			
			private void setupLayout()
			{
				/**
				 * EditPanel
				 */
				this.setBackground(LIGHT_GREY_COLOR);
				this.setLayout(new MigLayout("fill"));

				/**
				 * editKeyDetailsPane
				 */
				editKeyDetailsArea.setFont(glossaryFrame.getDefaultFont());
				editKeyDetailsArea.setText(controller.fetchTermForKey(selectedKey).getDefinition());
				editKeyDetailsArea.setLineWrap(true);
				editKeyDetailsPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
				editKeyDetailsPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

				/**
				 * editKeyArea
				 */
				editKeyArea.setFont(glossaryFrame.getDefaultFont());
				editKeyArea.setText(selectedKey);
				editKeyArea.setLineWrap(true);
				editKeyArea.requestFocusInWindow();
				editKeyPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
				editKeyPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

				/**
				 * ControlPanel & components
				 */
				controlPanel.setBackground(LIGHT_GREY_COLOR);
				specialCharacterButton.setToolTipText("Insert Special Character");
				specialCharacterButton.setFocusable(false);
				submitButton.setFocusable(false);
				cancelButton.setFocusable(false);
				controlPanel.add(specialCharacterButton);
				controlPanel.add(submitButton);
				controlPanel.add(cancelButton);

				/**
				 * newSeeAlsoTermPanel
				 */
				newSeeAlsoTermPanel.setToolTipText("Click an entry to remove it");
				newSeeAlsoTermPanel.setBackground(Color.white);
				seeAlsoPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
				seeAlsoPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

				if(controller.isEmpty())
				{
					newSeeAlsoTermPanel.setEnabled(false);
					seeAlsoBox.setEnabled(false);
					addSeeAlsoButton.setEnabled(false);
				}
				
				/**
				 * Add all components
				 */
				this.add(editKeyLabel,"wrap");
				this.add(editKeyPane,"grow,spanx 2, h 35, wrap");
				this.add(editKeyDetailsLabel,"wrap");
				this.add(editKeyDetailsPane,"wrap,spanx 2, h 35, grow");
				this.add(seeAlsoLabel,"wrap");
				this.add(addSeeAlsoButton, "w 60!");
				this.add(seeAlsoPane, "grow,push,wrap");
				this.add(seeAlsoBox, "grow, spanx 2, wrap");
				this.add(controlPanel,"spanx 2, grow");
			}
			
			private void updateSeeAlsoBox()
			{
				seeAlsoBox.removeAll();
				seeAlsoBox.addItem("Select an item");
				
				String[] keys = controller.getGlossaryKeys();
				for (String e : keys)
				{
					if(!e.equals(selectedKey))
						seeAlsoBox.addItem(e);
				}
				
				if(seeAlsoBox.getItemCount()==1)
				{
					seeAlsoBox.setEnabled(false);
					seeAlsoPanel.setEnabled(false);
					addSeeAlsoButton.setEnabled(false);
				}
				
				this.repaint();
				this.revalidate();
			}
			
			private void addSeeAlsoTerm()
			{
				if(seeAlsoBox.getSelectedIndex()!=0)
				{
					
					seeAlsoList.add((String) seeAlsoBox.getSelectedItem());
					System.out.println(seeAlsoList.size());
					refreshSeeAlsoTerms();
					
				}
				return;
			}
			
			private void refreshSeeAlsoTerms()
			{
				newSeeAlsoTermPanel.removeAll();
				for(String e : seeAlsoList)
				{
					
					final TermButton newSeeAlsoTerm = new TermButton("X "+e,e);
					newSeeAlsoTerm.setFocusable(false);
					newSeeAlsoTerm.setHorizontalAlignment(SwingConstants.LEFT);
					newSeeAlsoTerm.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
					newSeeAlsoTerm.setBorderPainted(false);
					newSeeAlsoTerm.setContentAreaFilled(false);
					newSeeAlsoTerm.setFocusPainted(false);
					newSeeAlsoTerm.setFont(glossaryFrame.getDefaultFont());
					
					newSeeAlsoTerm.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							System.out.println(seeAlsoList.remove(newSeeAlsoTerm.getStoredText()) ? "Term removed" : "Term not found");
							refreshSeeAlsoTerms();
						}
					});
					newSeeAlsoTermPanel.add(newSeeAlsoTerm);
				}
				newSeeAlsoTermPanel.repaint();
				newSeeAlsoTermPanel.revalidate();
			}

			/**
			 * Set up listeneres for all components that need one which are
			 * responsibilities of NewPanel
			 */
			private void setupListeners()
			{
				submitButton.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						if (editKeyArea.getText().contains(Controller.getFileDelimiter()) || editKeyDetailsArea.getText().contains(Controller.getFileDelimiter()))
						{
							JOptionPane.showMessageDialog(editPanel, "Term cannot contain the sequence '"+Controller.getFileDelimiter()+"'");
							return;
						}
						if (editKeyArea.getText().trim().equals("") || editKeyArea.getText() == null)
						{
							JOptionPane.showMessageDialog(editPanel, "Key cannot be blank");
							return;
						}
						if (editKeyDetailsArea.getText().trim().equals("") || editKeyDetailsArea.getText() == null)
						{
							JOptionPane.showMessageDialog(editPanel, "Definition cannot be blank");
							return;
						}
						if (editKeyArea.getText().charAt(editKeyArea.getText().length() - 1) == ' ')
						{
							if (JOptionPane.showConfirmDialog(editPanel, "Trailing spaces on keys are removed.\nIs this okay?") != JOptionPane.YES_OPTION)
							{
								return;
							}
						}
						if (editKeyArea.getText().charAt(0) == ' ')
						{
							if (JOptionPane.showConfirmDialog(editPanel, "Leading spaces on keys are removed.\nIs this okay?") != JOptionPane.YES_OPTION)
							{
								return;
							}
						}
						if (submitData())
							closeWindow();
						else
							return;
					}
				});

				cancelButton.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						closeWindow();
					}
				});

				specialCharacterButton.addActionListener(new ActionListener()
				{
					/**
					 * Check to make sure that the component with focus is a
					 * JTextArea, since they are the only editable components in
					 * NewPanel
					 */
					public void actionPerformed(ActionEvent e)
					{
						Component focused = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
						if (focused instanceof JTextArea)
						{
							setFocusedToFocused(focused);
							showSpecialCharacterSelect();
						}

					}
				});

				addSeeAlsoButton.addActionListener(new ActionListener()
				{
					/**
					 * Check to make sure that the component with focus is a
					 * JTextArea, since they are the only editable components in
					 * NewPanel
					 */
					public void actionPerformed(ActionEvent e)
					{
						addSeeAlsoTerm();
					}
				});
			}

			/**
			 * Close the NewPanel and free up its memory
			 */
			private void closeWindow()
			{
				exitFrame();
			}

			/**
			 * submit the data to the controller.
			 * 
			 * @return true if the submit suceeded.
			 */
			private boolean submitData()
			{
				String[] sal = new String[seeAlsoList.size()];
				if (seeAlsoList.size() != 0)
				{
					int i = 0;
					Iterator<String> sali = seeAlsoList.iterator();
					while(sali.hasNext())
					{
						
						String s = sali.next();
						if (controller.fetchTermForKey(s) == null)
						{
							JOptionPane.showMessageDialog(this, s + " is not in the glossary!");
							return false;
						}
						else
							sal[i]=s;
						i++;
					}
				}
				
				
				String newKey = editKeyArea.getText().trim().replace("\n", " ");
				if (controller.editEntry(newKey, new Term(editKeyDetailsArea.getText().replace("\n", " "), sal, null), selectedKey))
				{
					updateWithTermToDisplay(newKey);
					return true;
				}
				JOptionPane.showMessageDialog(editPanel, "Key is already in glossary");
				return false;
			}
		}
	}

	/**
	 * @return reference to GlossaryFrame, GlossaryPanel's parent
	 */
	private GlossaryFrame getGlossaryFrame()
	{
		return glossaryFrame;
	}

	/**
	 * Ask controller for a new glossary
	 */
	private void showNewGlossaryDialog()
	{
		controller.newGlossary();
	}

	/**
	 * Show dialog for opening a glossary, then give the path for glossary to
	 * controller if it exists. Does nothing if file does not exist
	 */
	private void showGlossaryOpenDialog()
	{
		if (!controller.closeable())
			return;

		glossaryOpener.setCurrentDirectory(controller.lastDirectory());
		glossaryOpener.setDialogTitle("Open Glossary");
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

	/**
	 * Show dialog for exporting a glossary, then give the path for glossary to
	 * controller if it exists. Does nothing if file does not exist
	 */
	private void showGlossaryExportDialog()
	{
		glossaryExporter.setCurrentDirectory(controller.lastDirectory());
		if (glossaryExporter.showSaveDialog(this) == JFileChooser.APPROVE_OPTION)
		{
			String exportString = glossaryExporter.getSelectedFile().getAbsolutePath();
			if (!exportString.endsWith(".txt"))
			{
				exportString += ".txt";
			}

			File exportFile = new File(exportString);

			if (exportFile.exists())
			{
				JOptionPane.showMessageDialog(this, "File already exists");
				return;
			}
			controller.exportAsText(exportString);
		}
	}

	/**
	 * Show dialog for saving a glossary, then give the path for glossary to
	 * controller if it exists. Does nothing if file already exists
	 */
	private void showGlossarySaveAsDialog()
	{
		glossaryOpener.setCurrentDirectory(controller.lastDirectory());
		glossaryOpener.setDialogTitle("Save Glossary");
		if (glossaryOpener.showSaveDialog(this) == JFileChooser.APPROVE_OPTION)
		{
			String save = glossaryOpener.getSelectedFile().getAbsolutePath();
			if (!save.endsWith(".gl"))
			{
				save += ".gl";
			}

			File saveFile = new File(save);

			if (saveFile.exists())
			{
				if (JOptionPane.showConfirmDialog(this, "This file already exists.\nOverwrite?") != JOptionPane.YES_OPTION)
					return;
			}
			controller.saveAs(save);
		}
	}

	/**
	 * Show the SpecialCharacterChooser so the user can input a special
	 * character
	 */
	private void showSpecialCharacterSelect()
	{
		scc.setVisible(true);
	}

	/**
	 * This method is called to tell the SpecialCharacterChooser which component
	 * to append the selected special character to.
	 * 
	 * @param focused
	 *            , the component that should be appended too.
	 */
	private void setFocusedToFocused(Component focused)
	{
		scc.setFocused(focused);
	}

	/**
	 * clear all components that need to be cleared so that a new glossary can
	 * be shown.
	 */
	public void clearAllForOpen()
	{
		termPanel.removeAll();
		termDetailsArea.setText("");
		mainControlPanel.updateSizeLabel();
	}
}
