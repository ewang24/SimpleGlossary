/**
Evan Wang
*/

package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.miginfocom.swing.MigLayout;
import Model.UnicodeModeler;

public class SpecialCharacterChooser extends JFrame
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1668531250025520362L;
	
	UnicodeModeler u;
	CharacterPanel characterPanel;
	
	public SpecialCharacterChooser(UnicodeModeler u_)
	{
		u = u_;
		characterPanel = new CharacterPanel();
		this.setSize(new Dimension(400,300));
		this.setPreferredSize(new Dimension(400,300));
		this.setVisible(true);
		this.setResizable(true);
		
		this.setContentPane(characterPanel);
	}
	
	private class CharacterPanel extends JPanel
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = -188562305204210806L;
		JPanel favoritesPanel;
		JPanel characterButtonPanel;
		JScrollPane characterButtonPane;
		JPanel controlPanel;
		
		public CharacterPanel()
		{
			favoritesPanel = new JPanel();
			characterButtonPanel = new JPanel();
			characterButtonPane = new JScrollPane(characterButtonPanel);
			controlPanel = new JPanel();
			
			setupLayout();
		}
		
		private void setupLayout()
		{
			this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
			characterButtonPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			characterButtonPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			controlPanel.setBackground(new Color(255,255,255));
			controlPanel.setMaximumSize(new Dimension(200,40));
			controlPanel.setMinimumSize(new Dimension(200,40));
			GridLayout x = new GridLayout(0,3);
			characterButtonPanel.setLayout(x);
			characterButtonPane.setMaximumSize(new Dimension(400,300));
			characterButtonPanel.setMaximumSize(new Dimension(400,300));
			
			characterButtonPane.getVerticalScrollBar().setUnitIncrement(32);
//			this.add(favoritesPanel);
			this.add(characterButtonPane);
//			this.add(controlPanel);
			addCharacters();
		}
		
		private void addCharacters()
		{
			
			ArrayList<Integer> characters = u.getAllCodes();
			for(int e : characters)
			{
				char[] pair = Character.toChars(e);
				JButton newCharacterButton = new JButton(new String(pair));
				newCharacterButton.setSize(new Dimension(50,50));
				
				characterButtonPanel.add(newCharacterButton);
			}
			
			characterButtonPanel.add(new JButton("\u1e00"));
			characterButtonPanel.repaint();
			characterButtonPanel.revalidate();
			
		}
	}
	
}
