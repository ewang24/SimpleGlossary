/**
Evan Wang
 */

package Controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.Writer;
import java.util.Scanner;
import java.util.Stack;

import javax.swing.JOptionPane;

import Model.Glossary;
import Model.Operation;
import Model.Term;
import Model.UnicodeModeler;
import View.GlossaryFrame;

public class Controller
{
	private Glossary glossary;
	private GlossaryFrame gf;
	private File glossaryFileToUse;
	private String fileName = "untitled";
	private final String AUTOLOAD_PATH = "C:\\Users\\Evan\\Documents\\GitHub\\SimpleGlossary\\src\\glossary.gl";
	private boolean newFile = true;
	UnicodeModeler unicodeModeler;
	private Stack<Operation> operations;
	
	//Turn this to false and the program won't autoload the file at AUTOLOAD_PATH. Used for debug purposes
	private boolean autoLoad = !true;

	public Controller()
	{
		unicodeModeler = new UnicodeModeler();
		glossary = new Glossary();
		gf = new GlossaryFrame(this);
		operations = new Stack<Operation>();

		if (autoLoad)
		{
			open(AUTOLOAD_PATH);
		}
	}

	private void load(String location)
	{
		glossaryFileToUse = new File(location);
		String rawTermString = "";
		try
		{

			String currentString = "";
			// Scanner termReader = new Scanner(glossaryFileToUse);
			BufferedReader termReader = new BufferedReader(new InputStreamReader(new FileInputStream(glossaryFileToUse), "UTF-8"));
			while ((currentString = termReader.readLine()) != null)
			{
				rawTermString += currentString + "\n";
			}
			 termReader.close();
		}
		catch (Exception e)
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
		return glossary.getKeys(unicodeModeler.getUnicodeStringComparator());
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
		if (closeable())
			System.exit(0);
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
		operations.push(new Operation(Operation.operationType.ADD,term));
		boolean success = glossary.addUnsavedTerm(key, term);
		if (success)
			setTitleToUnsaved();
		return success;
	}

	/**
	 * Save the glossary to the file specified by glossaryFileToUse
	 */
	public void save()
	{
		if(!newFile)
		{
			newFile = false;
		}
		
		String toString = glossary.toString();
		try
		{
			// PrintWriter saveWriter = new PrintWriter(new BufferedWriter(new
			// FileWriter(glossaryFileToUse, false)));
			RandomAccessFile file = new RandomAccessFile(glossaryFileToUse,"rw");
			file.setLength(0);
			file.write(toString.getBytes(), 0, toString.length());
			file.close();
			
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

	/**
	 * Clears the view and loads the file
	 * @param location, the file to load
	 */
	public void open(String location)
	{
		if(closeable())
		{
			clearEverything();
			load(location);
		}
	}

	/**
	 * Export the glossary as a text file.
	 * @param location, the location to save the file. 
	 */
	public void exportAsText(String location)
	{
		System.out.println(location);
		String toString = glossary.toText();
		try
		{
	
			PrintWriter saveWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(location), "UTF-8"));

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
	 * Creates a new glossary
	 */
	public void newGlossary()
	{
		if (closeable())
		{
			newFile = true;
			clearEverything();
			fileName = "New Glossary";
			refreshTitle();
		}

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
		if( glossary.removeByKey(key)!=(null))
		{
			operations.push(new Operation(Operation.operationType.REMOVE,new Term(key)));
			System.out.println(operations.size());
			setTitleToUnsaved();
			return true;
		}
		else
			return false;
	}

	public UnicodeModeler getUnicodeModeler()
	{
		return unicodeModeler;
	}
	
	public boolean isDirty()
	{
		return !isClean();
	}
	
	
	public boolean isClean()
	{
		return operations.isEmpty();
	}
	
	private void setTitleToUnsaved()
	{
		gf.setTitle("*" + fileName);
	}
	
	/**
	 * @return true if the user doensn't want to save
	 */
	private boolean confirmUnsaved()
	{
		return JOptionPane.showConfirmDialog(gf, "You have unsaved data.\nIs it okay to discard it?") == JOptionPane.YES_OPTION;
	}
	
	public boolean closeable()
	{
		if(isClean())
		{
			return true;
		}
		else
		{
			return confirmUnsaved();
		}
			
	}
}
