/**
Evan Wang
*/

package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
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
	Font font = new Font(null);
	
	public SpecialCharacterChooser(UnicodeModeler u_)
	{
//		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		u = u_;
//		try
//		{
//			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\Evan\\Documents\\GitHub\\SimpleGlossary\\src\\src\\View\\Resources\\TIMES.TTF")));
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
		characterPanel = new CharacterPanel();
		this.setSize(new Dimension(400,300));
		this.setPreferredSize(new Dimension(400,300));
		this.setVisible(true);
		this.setResizable(false);
		
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
		JScrollPane favoritesPane;
		JPanel controlPanel;
		
		public CharacterPanel()
		{
			favoritesPanel = new JPanel();
			characterButtonPanel = new JPanel();
			characterButtonPane = new JScrollPane(characterButtonPanel);
			favoritesPane = new JScrollPane(favoritesPanel);
			controlPanel = new JPanel();
			
			setupLayout();
		}
		
		private void setupLayout()
		{
			this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
			characterButtonPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			characterButtonPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			favoritesPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			favoritesPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
			controlPanel.setBackground(new Color(255,255,255));
			controlPanel.setMaximumSize(new Dimension(400,30));
			controlPanel.setMinimumSize(new Dimension(400,30));
			GridLayout x = new GridLayout(0,7);
			characterButtonPanel.setLayout(x);
			characterButtonPane.setMaximumSize(new Dimension(400,300));
			characterButtonPanel.setMaximumSize(new Dimension(400,300));
			
			characterButtonPane.getVerticalScrollBar().setUnitIncrement(32);
			this.add(favoritesPane);
			this.add(characterButtonPane);
			this.add(controlPanel);
			addCharacters();
		}
		
		private void addCharacters()
		{
			
			ArrayList<Integer> characters = u.getAllCodes();
			for(int e : characters)
			{
			
				final CharacterButton newCharacterButton = new CharacterButton(e);
				newCharacterButton.setSize(new Dimension(50,50));
				characterButtonPanel.add(newCharacterButton);
				
				newCharacterButton.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						System.out.println(newCharacterButton.getCharacter());
					}
				});
				
			}
			
			characterButtonPanel.repaint();
			characterButtonPanel.revalidate();
			
		}
	}
	
	private class CharacterButton extends JButton
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = -1664556684449940432L;
		
		int character;
		
		public CharacterButton(int character)
		{
			super(new String(Character.toChars(character)));
			this.character = character;
		}
		
		public int getCharacter()
		{
			return character;
		}
	}
	
}
