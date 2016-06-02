/**
Evan Wang
 */

package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import Controller.Controller;
import Model.Glossary;
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
	int count=0;
	
	/*
	 * Components
	 */
	JPanel termPanel;
	JTextArea termDetailsArea;
	JScrollPane termScrollPane;
	JScrollPane termDetailsScrollPane;
	ControlPanel cp;
	
	/*
	 * finals
	 */
	final Dimension TERM_DETAILS_AREA_MINIMUM_SIZE = new Dimension(300,124);
	final int SCROLL_INCREMENT = 16;
	
	GlossaryPanel(Controller controller)
	{
		this.controller = controller;
		cp = new ControlPanel();
		termDetailsArea = new JTextArea();
		termPanel = new JPanel();
		
		setupLayout();
	}

	private void setupLayout()
	{
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBackground(new Color(255, 255, 255));
		termDetailsArea.setEditable(false);
		termDetailsArea.setText("Test");
		termDetailsArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		termPanel.setLayout(new MigLayout("fillx"));
		termPanel.setBackground(new Color(236, 233, 216));
		termScrollPane = new JScrollPane(termPanel);
		termScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		termScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		termDetailsScrollPane = new JScrollPane(termDetailsArea);
		termDetailsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		termDetailsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.add(termScrollPane);
		this.add(termDetailsScrollPane);
		this.add(cp);
		cp.updateSize();
		termDetailsScrollPane.setMinimumSize(TERM_DETAILS_AREA_MINIMUM_SIZE);
		termScrollPane.getVerticalScrollBar().setUnitIncrement(SCROLL_INCREMENT);
		this.revalidate();
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

		ControlPanel()
		{
			newTerm = new JButton("New Term");
			removeTerm = new JButton("Remove Term");
			countLabel = new JLabel("Entries: ");
			
			setupLayout();
			setupListeners();
		}
		
		private void setupLayout()
		{
			this.setBackground(new Color(250, 255, 255));
			this.add(countLabel);
			this.add(newTerm);
			this.add(removeTerm);	
		}
		
		private void setupListeners()
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
					removeTerm(null);
				}
			});
		}
		
		public void updateSize()
		{
			countLabel.setText("Entries: "+getGlossarySize());
		}
	}

	public void displaySortedKeys(String[] keys)
	{
		int currLetter = 0;

		if (alph[currLetter] == Character.toLowerCase(keys[0].charAt(0)))
			displayLetterMarker(alph[currLetter]);
		
		for (int i = 0; i < keys.length; i++)
		{
			if (alph[currLetter] != Character.toLowerCase(keys[i].charAt(0)))
			{
				while (alph[currLetter] != Character.toLowerCase(keys[i].charAt(0)))
				{
					currLetter++;
				}

				displayLetterMarker(alph[currLetter]);
			}

			displayKey(keys[i]);
		}

		displayTermDefinition(keys[0]);
		this.repaint();
		this.revalidate();
	}

	private void displayLetterMarker(char c)
	{
		JLabel j = new JLabel("\n"+Character.toUpperCase(c) + ":");
		j.setFont(new Font(j.getFont().getFontName(),j.getFont().BOLD,j.getFont().getSize()));
		termPanel.add(j, "wrap,push");
	}

	private void displayKey(final String key)
	{
		JButton newTermButton = new JButton(key);
		
		newTermButton.setHorizontalAlignment(SwingConstants.LEFT);
		newTermButton.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		newTermButton.setBorderPainted(false);
		newTermButton.setContentAreaFilled(false);
		newTermButton.setFocusPainted(false);
		
		newTermButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				displayTermDefinition(key);
			}
		});

		termPanel.add(newTermButton, "wrap,grow");
	}
	
	private void displayTermDefinition(String key){
		termDetailsArea.setText(" "+key+":\n\n\t"+controller.fetchTermForKey(key).getDefinition());
	}
	
	private int getGlossarySize()
	{
		System.out.println(controller.glossarySize());
		return controller.glossarySize();	
	}
	
	private void removeTerm(String key)
	{
		
	}
	
	private void addTerm()
	{
		
	}
}
