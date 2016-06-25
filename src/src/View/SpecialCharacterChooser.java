/**
Evan Wang
This class represents a GUI allowing a user to select a special unicode character and add it to the focused text component, if any (the focused component must be provided by whatever class is using this one).
Relies on the class UnicodeModler
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

import net.miginfocom.swing.MigLayout;
import Model.UnicodeModeler;

public class SpecialCharacterChooser extends JFrame
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1668531250025520362L;

	/**
	 * Data
	 */
	private final String DEFAULT_FAVORITES = "0101\n0100\n00E4\n00C4\n00EA\n00CA\n00EB\n00CB\n00F6\n00D6\n00EF\n00CF\n00EE\n00CE\n01D0\n01CF\n00FC\n00DC\n0125\n0124\n00F1\n00D1";
	private final String CONFIG_FILE_NAME = "sys/sconfig~";
	private boolean autoLoadFavorites = true;
	ArrayList<Integer> favorites;

	/**
	 * Components
	 */
	CharacterPanel characterPanel;
	Component focused;

	UnicodeModeler u;

	/**
	 * Other
	 */
	final File AUTO_LOAD = new File(CONFIG_FILE_NAME);
	Font font = new Font(null);

	public SpecialCharacterChooser(UnicodeModeler u_)
	{
		this.setTitle("Insert Special Character");
		u = u_;
		favorites = new ArrayList<Integer>();
		characterPanel = new CharacterPanel();
		this.setSize(new Dimension(400, 300));
		this.setPreferredSize(new Dimension(400, 300));
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

		CharacterButton selectedCharacter;

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
			/**
			 * CharacterPanel
			 */
			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

			/**
			 * characterButtonPanel
			 */
			GridLayout x = new GridLayout(0, 7);
			characterButtonPanel.setLayout(x);
			characterButtonPanel.setBackground(Color.WHITE);
			characterButtonPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			characterButtonPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			characterButtonPane.setMaximumSize(new Dimension(400, 300));
			characterButtonPane.getVerticalScrollBar().setUnitIncrement(32);

			/**
			 * favoritesPanel
			 */
			favoritesPanel.setLayout(new MigLayout());
			favoritesPanel.setBackground(Color.WHITE);
			favoritesPanel.add(new JLabel("Favorites"), "wrap");
			favoritesPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			favoritesPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
			favoritesPane.setMinimumSize(new Dimension(400, 70));

			/**
			 * controlPanel
			 */
			controlPanel.setBackground(Color.WHITE);
			controlPanel.setMaximumSize(new Dimension(400, 30));
			controlPanel.setMinimumSize(new Dimension(400, 30));
			submit.setEnabled(false);
			addToFavorites.setEnabled(false);
			removeFromFavorites.setEnabled(false);
			controlPanel.add(current);
			controlPanel.add(addToFavorites);
			controlPanel.add(removeFromFavorites);
			controlPanel.add(submit);
			controlPanel.add(cancel);

			this.add(favoritesPane);
			this.add(characterButtonPane);
			this.add(controlPanel);

			if (autoLoadFavorites)
				loadFavorites(AUTO_LOAD);

			addCharacters();
			displayFavorites();
			setupListeners();
		}

		private void setupListeners()
		{
			addToFavorites.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					addToFavorites(selectedCharacter);
					refreshFavorites();
					swapAddRemove(selectedCharacter);
				}
			});

			removeFromFavorites.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					removeFromFavorites(selectedCharacter);
					refreshFavorites();
					swapAddRemove(selectedCharacter);
				}
			});

			submit.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					append(selectedCharacter.getCharacterAsString());
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

		private void loadFavorites(File location)
		{
			try
			{
				Scanner s;
				if (location.length() == 0)
					s = new Scanner(DEFAULT_FAVORITES);
				else
					s = new Scanner(location);
				while (s.hasNextLine())
				{
					favorites.add(Integer.parseInt(s.nextLine(), 16));
				}
				s.close();
			}
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		private void displayFavorites()
		{
			for (int e : favorites)
			{
				addButton(e, favoritesPanel);
			}
		}

		private void addCharacters()
		{
			ArrayList<Integer> characters = u.getAllCodes();
			for (int e : characters)
			{
				addButton(e, characterButtonPanel);
			}

			characterButtonPanel.repaint();
			characterButtonPanel.revalidate();
		}

		private void addButton(final int e, JPanel container)
		{
			final CharacterButton newCharacterButton = new CharacterButton(e);
			newCharacterButton.setSize(new Dimension(50, 50));
			newCharacterButton.setContentAreaFilled(false);
			container.add(newCharacterButton);

			if (favorites.contains(new Integer(e)))
			{
				newCharacterButton.addToFavorites();
			}

			newCharacterButton.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e_)
				{
					if (newCharacterButton.isFavorite)
						removeFromFavorites.setEnabled(true);
					else
						addToFavorites.setEnabled(true);
					submit.setEnabled(true);
					current.setText("Insert: " + newCharacterButton.getCharacterAsString());
					selectedCharacter = newCharacterButton;
					swapAddRemove(selectedCharacter);
				}
			});

		}

		private void swapAddRemove(CharacterButton cb)
		{
			addToFavorites.setEnabled(!cb.isFavorite());
			removeFromFavorites.setEnabled(cb.isFavorite());
			this.repaint();
		}

		private void refreshFavorites()
		{
			favoritesPanel.removeAll();
			favoritesPanel.add(new JLabel("Favorites"), "wrap");
			displayFavorites();
			favoritesPanel.repaint();
			favoritesPanel.revalidate();
		}

		private void addToFavorites(CharacterButton cb)
		{
			cb.addToFavorites();
			favorites.add(cb.getCharacter());
			saveFavorites();
		}

		private void removeFromFavorites(CharacterButton cb)
		{
			cb.removeFromFavorites();
			favorites.remove(new Integer(cb.getCharacter()));
			saveFavorites();
		}

		private void saveFavorites()
		{
			PrintWriter saveWriter;
			try
			{
				saveWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(AUTO_LOAD.getAbsolutePath()), "UTF-8"));
				saveWriter.print(favoritesString());
				saveWriter.flush();
				saveWriter.close();
			}
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		private String favoritesString()
		{
			String f = "";
			for (int e : favorites)
			{
				f += Integer.toHexString(e) + "\r\n";
			}
			return f;
		}

	}

	private class CharacterButton extends JButton
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = -1664556684449940432L;

		private int character;
		private boolean isFavorite = false;

		/**
		 * @param character
		 *            the codepoint for the character in decimal
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

		public boolean isFavorite()
		{
			return isFavorite;
		}
	}

	public void closeWindow()
	{
		this.setVisible(false);
		this.dispose();
	}

	private void append(String character)
	{
		if (focused == null)
		{
			System.err.println("No focused component!!!");
		}
		if (focused instanceof JTextComponent)
		{
			JTextComponent jtc = (JTextComponent) focused;
			if (jtc.isEditable())
			{
//				jtc.setText(jtc.getText() + character);
				Document d = jtc.getDocument();
				try
				{
					d.insertString(jtc.getCaretPosition(), character, null);
				}
				catch (BadLocationException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				closeWindow();
			}
		}
		else
			System.err.println("Focused component does not support text (FAIL)!!!");
	}

	public void setFocused(Component focused_)
	{
		focused = focused_;
	}
}
