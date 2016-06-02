/**
Evan Wang
 */

package Controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JOptionPane;

import Model.Glossary;
import Model.Term;
import View.GlossaryFrame;

public class Controller
{
	private Glossary glossary;
	private GlossaryFrame gf;
	private File glossaryFileToLoad;
	private boolean autoload = true;
	private String fileName;
	private final String AUTOLOAD_PATH = "C:\\Users\\Evan\\Documents\\GitHub\\SimpleGlossary\\src\\glossary.gl";

	public Controller()
	{
		glossary = new Glossary();
		gf = new GlossaryFrame(this);

		if (autoload)
		{
			load(AUTOLOAD_PATH);
		}
	}

	private void load(String location)
	{
		glossaryFileToLoad = new File(location);
		String rawTermString = "";
		try
		{
			Scanner termReader = new Scanner(glossaryFileToLoad);
			while (termReader.hasNextLine())
			{
				rawTermString += termReader.nextLine() + "\n";
			}
			termReader.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}

		String[] termList = rawTermString.split("\n");

		for (int i = 0; i < termList.length; i++)
		{
			glossary.addTerm(
					termList[i].substring(0, termList[i].indexOf(":::")),
					new Term(termList[i].substring(
							termList[i].indexOf(":::") + 3,
							termList[i].length())));
		}

		fileName = glossaryFileToLoad.getName();
		gf.setTitle(fileName);
		gf.displayGlossaryKeys();
	}

	public String[] getGlossaryKeys()
	{
		return glossary.getKeys();
	}

	public Term fetchTermForKey(String key)
	{
		return glossary.get(key);
	}

	public int glossarySize()
	{
		return glossary.getSize();
	}

	public void exit()
	{
		if (glossary.isDirty())
		{
			if (JOptionPane
					.showConfirmDialog(
							null,
							"You have unsaved data.\nDo you really want to exit the program?",
							"Exit?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
				System.exit(0);
			return;
		}
		else
		{
			if (JOptionPane.showConfirmDialog(null,
					"Do you really want to exit the program?", "Exit?",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
				System.exit(0);
		}
	}

	/**
	 * @param key
	 *            be to added
	 * @param term
	 *            : mapped to by key
	 * @return true if entry was sucessfully added. False otherwise.
	 */
	public boolean newEntry(String key, Term term)
	{
		boolean success = glossary.addUnsavedTerm(key, term);
		if (success)
			gf.setTitle("*" + fileName);
		return success;
	}

	public void save()
	{
		String toString = glossary.toString();
		try
		{
			PrintWriter saveWriter = new PrintWriter(new BufferedWriter(
					new FileWriter(glossaryFileToLoad, false)));
			saveWriter.print(toString);
			saveWriter.flush();
			saveWriter.close();
			glossary.clearDirtyList();
			refreshTitle();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Moves the file reference to a new location and proceeds to save there
	 * @param location: the new location of the file
	 */
	public void saveAs(String location)
	{
		glossaryFileToLoad = new File(location);
		fileName = glossaryFileToLoad.getName();
		refreshTitle();
		save();
	}
	public void open(String location)
	{
		clearEverything();
		load(location);
	}
	
	/**
	 * @return: returns true if all data is saved and okay to dump, false otherwise
	 */
	public void newGlossary()
	{
		if(glossary.isDirty())
		{
			if(JOptionPane.showConfirmDialog(gf, "You have unsaved data.\nIs it okay to discard it?")!=JOptionPane.YES_OPTION)
				return;
		}
		clearEverything();
		fileName = "New Glossary";
		refreshTitle();
	}
	
	private void clearEverything()
	{
		glossary.clearGlossary();
		gf.clearAllForOpen();
	}

	public boolean isEmpty()
	{
		return glossary.getSize()==0;
	}
	
	private void refreshTitle()
	{
		gf.setTitle(fileName);
	}
}
