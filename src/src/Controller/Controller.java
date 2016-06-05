/**
Evan Wang
 */

package Controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Scanner;

import javax.swing.JOptionPane;

import Model.Glossary;
import Model.Term;
import Model.UnicodeModeler;
import View.GlossaryFrame;

public class Controller
{
	private Glossary glossary;
	private GlossaryFrame gf;
	private File glossaryFileToUse;
	private boolean autoLoad = true;
	private String fileName;
	private final String AUTOLOAD_PATH = "C:\\Users\\Evan\\Documents\\GitHub\\SimpleGlossary\\src\\glossary.gl";
	private boolean newFile = true;
	UnicodeModeler unicodeModeler;

	public Controller()
	{
		unicodeModeler = new UnicodeModeler();
		glossary = new Glossary();
		gf = new GlossaryFrame(this);

		if (autoLoad)
		{
			load(AUTOLOAD_PATH);
		}
	}

	private void load(String location)
	{
		glossaryFileToUse = new File(location);
		String rawTermString = "";
		try
		{
			Scanner termReader = new Scanner(glossaryFileToUse);
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
			glossary.addTerm(termList[i].substring(0, termList[i].indexOf(":::")), new Term(termList[i].substring(termList[i].indexOf(":::") + 3, termList[i].length())));
		}

		fileName = glossaryFileToUse.getName();
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
			if (JOptionPane.showConfirmDialog(null, "You have unsaved data.\nDo you really want to exit the program?", "Exit?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
				System.exit(0);
			return;
		}
		else
		{
			if (JOptionPane.showConfirmDialog(null, "Do you really want to exit the program?", "Exit?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
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

	/**
	 * Save the glossary to the file specified by glossaryFileToUse
	 */
	public void save()
	{
		String toString = glossary.toString();
		try
		{
//			PrintWriter saveWriter = new PrintWriter(new BufferedWriter(new FileWriter(glossaryFileToUse, false)));

			PrintWriter saveWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(glossaryFileToUse.getAbsolutePath()), "UTF-8"));

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
	 * 
	 * @param location
	 *            : the new location of the file
	 */
	public void saveAs(String location)
	{
		newFile = false;
		glossaryFileToUse = new File(location);
		fileName = glossaryFileToUse.getName();
		refreshTitle();
		save();
	}

	public void open(String location)
	{
		clearEverything();
		load(location);
	}

	/**
	 * Creates a new glossary
	 */
	public void newGlossary()
	{
		if (glossary.isDirty())
		{
			if (JOptionPane.showConfirmDialog(gf, "You have unsaved data.\nIs it okay to discard it?") != JOptionPane.YES_OPTION)
				return;
		}

		newFile = true;
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
		return glossary.getSize() == 0;
	}

	private void refreshTitle()
	{
		gf.setTitle(fileName);
	}

	/**
	 * @return true if the file is a new file that does not have a file location
	 *         yet
	 */
	public boolean isNewFile()
	{
		return newFile;
	}

	/**
	 * @param key
	 *            to remove
	 * @return true if the entry was sucessfully removed
	 */
	public boolean remove(String key)
	{
		return !glossary.removeByKey(key).equals(null);
	}

	public UnicodeModeler getUnicodeModeler()
	{
		return unicodeModeler;
	}
}
