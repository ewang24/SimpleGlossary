/**
Evan Wang
*/

package View;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.text.JTextComponent;

import net.miginfocom.swing.MigLayout;
import Model.UnicodeModeler;

public class SpecialCharacterChooser extends JFrame
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1668531250025520362L;
	
	Component focused;
	UnicodeModeler u;
	CharacterPanel characterPanel;
	Font font = new Font(null);
	private boolean autoLoadFavorites = true;
	final String AUTO_LOAD = "C:\\Users\\Evan\\Documents\\GitHub\\SimpleGlossary\\src\\src\\View\\Resources\\favorites";
	ArrayList<Integer>favorites;
	
	public SpecialCharacterChooser(UnicodeModeler u_)
	{
		this.setTitle("Insert Special Character");
		u = u_;
		favorites = new ArrayList<Integer>();
		characterPanel = new CharacterPanel();
		this.setSize(new Dimension(400,300));
		this.setPreferredSize(new Dimension(400,300));
		this.setVisible(false);
		this.setResizable(false);
		
		this.setContentPane(characterPanel);
	}
	
	private class CharacterPanel extends JPanel
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = -188562305204210806L;
		
		String selected = "";
		
		JPanel favoritesPanel;
		JPanel characterButtonPanel;
		JScrollPane characterButtonPane;
		JScrollPane favoritesPane;
		JPanel controlPanel;
		JButton submit;
		JButton cancel;
		JButton addToFavorites;
		JButton removeFromFavorites;
		JLabel current;
		
		public CharacterPanel()
		{
			favoritesPanel = new JPanel();
			characterButtonPanel = new JPanel();
			characterButtonPane = new JScrollPane(characterButtonPanel);
			favoritesPane = new JScrollPane(favoritesPanel);
			controlPanel = new JPanel();
			submit = new JButton("Insert");
			cancel = new JButton("cancel");
			addToFavorites = new JButton("Favorite");
			removeFromFavorites = new JButton("Un-favorite");
			current = new JLabel();
			
			setupLayout();
		}
		
		private void setupLayout()
		{
			this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
			characterButtonPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			characterButtonPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			favoritesPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			favoritesPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
			controlPanel.setBackground(Color.WHITE);
			controlPanel.setMaximumSize(new Dimension(400,30));
			controlPanel.setMinimumSize(new Dimension(400,30));
			GridLayout x = new GridLayout(0,7);
			characterButtonPanel.setLayout(x);
			characterButtonPanel.setBackground(Color.WHITE);
			characterButtonPane.setMaximumSize(new Dimension(400,300));
//			characterButtonPanel.setMaximumSize(new Dimension(400,300));
			favoritesPane.setMinimumSize(new Dimension(400,70));
			favoritesPanel.setLayout(new MigLayout());
			favoritesPanel.setBackground(Color.WHITE);
			favoritesPanel.add(new JLabel("Favorites"), "wrap");
			
			
			submit.setEnabled(false);
			addToFavorites.setEnabled(false);
			removeFromFavorites.setEnabled(false);
			controlPanel.add(current);
			controlPanel.add(addToFavorites);
			controlPanel.add(removeFromFavorites);
			controlPanel.add(submit);
			controlPanel.add(cancel);
			
			characterButtonPane.getVerticalScrollBar().setUnitIncrement(32);
			this.add(favoritesPane);
			this.add(characterButtonPane);
			this.add(controlPanel);
			addCharacters();
			if(autoLoadFavorites)
				loadFavorites(AUTO_LOAD);
			displayFavorites();
			setupListeners();
		}
		
		private void setupListeners()
		{
			addToFavorites.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					
				}
			});
			
			removeFromFavorites.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					
				}
			});
			
			submit.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					append(selected);
				}
			});
			
			cancel.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					closeWindow();
				}
			});
		}
		
		private void loadFavorites(String location)
		{
			File f = new File(location);
			try
			{
				Scanner s = new Scanner(f);
				while(s.hasNextLine())
				{
					favorites.add(Integer.parseInt(s.nextLine(),16));
				}
				s.close();
			}
			catch (FileNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		private void displayFavorites()
		{
			for(int e : favorites)
			{
				addButton(e,favoritesPanel);
			}
		}
		
		private void addCharacters()
		{
			
			ArrayList<Integer> characters = u.getAllCodes();
			for(int e : characters)
			{
				addButton(e,characterButtonPanel);
			}
			
			characterButtonPanel.repaint();
			characterButtonPanel.revalidate();
			
		}
		

		private void addButton(int e, JPanel container)
		{
			final CharacterButton newCharacterButton = new CharacterButton(e);
			newCharacterButton.setSize(new Dimension(50,50));
			
//			newCharacterButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			newCharacterButton.setContentAreaFilled(false);
//			newCharacterButton.setFocusPainted(false);
			container.add(newCharacterButton);
			
			newCharacterButton.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					System.out.println(newCharacterButton.getCharacterAsString());
					current.setText("Insert: "+newCharacterButton.getCharacterAsString());
					selected = newCharacterButton.getCharacterAsString();
					submit.setEnabled(true);
				}
			});
			
		}
	}
	
	
	private class CharacterButton extends JButton
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = -1664556684449940432L;
		
		int character;
		boolean isFavorite = false;
		
		/**
		 * @param character the codepoint for the character in decimal
		 */
		public CharacterButton(int character)
		{
			super(new String(Character.toChars(character)));
			this.character = character;
		}
		
		public int getCharacter()
		{
			return character;
		}
		
		public String getCharacterAsString()
		{
			return new String(Character.toChars(character));
		}
		
		public void addToFavorites()
		{
			isFavorite = true;
		}
		
		public void removeFromFavorites()
		{
			isFavorite = false;
		}
	}
	
	public void closeWindow()
	{
		this.setVisible(false);
		this.dispose();
	}
	
	private void append(String character)
	{
		if(focused == null)
		{
			System.err.println("No focused component!!!");
		}
		if(focused instanceof JTextComponent)
		{ 
			JTextComponent jtc = (JTextComponent) focused;
			if(jtc.isEditable())
			{
				jtc.setText(jtc.getText()+character);
				closeWindow();
			}
		}
		else
			System.err.println("Focused component does not work with text!!!");
	}
	
	public void setFocused(Component focused_)
	{
		focused = focused_;
	}
}
