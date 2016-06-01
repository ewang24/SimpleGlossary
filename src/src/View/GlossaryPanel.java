/**
Evan Wang
 */

package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import Controller.Controller;
import Model.Glossary;
import net.miginfocom.swing.MigLayout;

public class GlossaryPanel extends JPanel
{
	Controller controller;
	JPanel termPanel;
	JTextArea termDetailsArea;
	char [] alph = {'a','b','c','d','e','f','g','h','i','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};

	GlossaryPanel(Controller controller)
	{
		this.controller = controller;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBackground(new Color(255, 255, 255));
		termDetailsArea = new JTextArea();
		termDetailsArea.setEditable(false);
		termDetailsArea.setText("Test");
		termPanel = new JPanel();
		termPanel.setLayout(new MigLayout("fill"));
		termPanel.setPreferredSize(new Dimension(300,300));
		termPanel.setBackground(new Color(236, 233, 216));
		this.add(termPanel);
		this.add(termDetailsArea);
		this.add(new ControlPanel());

		this.revalidate();
	}

	private class ControlPanel extends JPanel
	{

		JButton newTerm;
		JButton removeTerm;

		ControlPanel()
		{
			this.setBackground(new Color(250, 255, 255));
			newTerm = new JButton("New Term");
			removeTerm = new JButton("Remove Term");
			this.add(newTerm);
			this.add(removeTerm);

			newTerm.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{

				}
			});

			removeTerm.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{

				}
			});

		}
	}
	
	public void displaySortedKeys(String [] keys)
	{
		int currLetter = 0;
		for(int i = 0; i < keys.length;i++)
		{
			displayKey(keys[i]);
		}
		
		this.repaint();
		this.revalidate();
	}
	
	public void displayKey(final String key)
	{
		JButton j = new JButton(key);
		j.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				System.out.println(key);
				termDetailsArea.setText(controller.fetchTermForKey(key).getDefinition());
			}
		});

		
		termPanel.add(j,"wrap");
	}
}
