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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

import Model.AddOperationData;
import Model.EditOperationData;
import Model.Glossary;
import Model.Operation;
import Model.RemoveOperationData;
import Model.Term;
import Model.UnicodeModeler;
import View.GlossaryFrame;
import View.SpecialCharacterChooser;

public class Controller
{
	//
	//Version:
		private final float VERSION_NUMBER = 0.2f;
	//
	//
	
	/**
	 * Models
	 */
	private Stack<Operation> operations;
	UnicodeModeler unicodeModeler;
	private Glossary glossary;

	/**
	 * Components
	 */
	private GlossaryFrame gf;

	/**
	 * Data
	 */
	private File glossaryFileToUse;
	private String fileName = "untitled";
	private final String AUTOLOAD_PATH = "C:\\Users\\Evan\\Documents\\GitHub\\SimpleGlossary\\src\\glossary.gl";
	private boolean newFile = true;

	/**
	 * Configuration information
	 */
	private final File SYS_DIRECTORY = new File("sys");
	private final File SPECIAL_CHARACTER_CONFIG_FILE = new File("sys/sconfig~");
	private final File SYS_CONFIG_FILE = new File("sys/gconfig~");
	private final String LAST_USED_DIRECTORY = "0000";

	private HashMap<String, String> configurationInformation = new HashMap<String, String>();
	char[] alph = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

	// Turn this to false and the program won't autoload the file at
	// AUTOLOAD_PATH. Used for debug purposes
	private boolean autoLoad = !true;

	public Controller()
	{
		unicodeModeler = new UnicodeModeler();
		glossary = new Glossary();
		gf = new GlossaryFrame(this);
		operations = new Stack<Operation>();

		setupDirectories();
		setupConfigurationInformation();

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
			System.out.println(termList[i]);
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
		{
			saveConfigurations();
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
		operations.push(new Operation(Operation.operationType.ADD, new AddOperationData()));
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
		if (!newFile)
		{
			newFile = false;
		}

		String toString = glossary.toString();
		try
		{
			// PrintWriter saveWriter = new PrintWriter(new BufferedWriter(new
			// FileWriter(glossaryFileToUse, false)));
			RandomAccessFile file = new RandomAccessFile(glossaryFileToUse, "rw");
			file.setLength(0);
			file.write(toString.getBytes(), 0, toString.length());
			file.close();

			PrintWriter saveWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(glossaryFileToUse.getAbsolutePath()), "UTF-8"));

			saveWriter.print(toString);
			saveWriter.flush();
			saveWriter.close();
			clearDirtyList();
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
		this.updateLastUsedDirectory(location);
		fileName = glossaryFileToUse.getName();
		refreshTitle();
		save();
	}

	/**
	 * Clears the view and loads the file
	 * 
	 * @param location
	 *            , the file to load
	 */
	public void open(String location)
	{
		if (closeable())
		{
			this.updateLastUsedDirectory(location);
			clearEverything();
			load(location);
			newFile = false;
		}
	}

	/**
	 * Export the glossary as a text file.
	 * 
	 * @param location
	 *            , the location to save the file.
	 */
	public void exportAsText(String location)
	{		
		boolean reachedEnd = false;

		int currLetter = 0;

		System.out.println(location);
		String toString = fileName + "\r\n" + "Number of Entries: " + glossarySize() + "\r\n";

		String[] a = glossary.getKeys(unicodeModeler.getUnicodeStringComparator());

		// Check if we need to add an 'A' letter header
		if (alph[currLetter] == Character.toLowerCase(unicodeModeler.getBaseCharacterString(a[0]).charAt(0)))
			toString += "A\r\n";

		for (int i = 0; i < a.length; i++)
		{

			if (!reachedEnd && alph[currLetter] != Character.toLowerCase(unicodeModeler.getBaseCharacterString(a[i]).charAt(0)))
			{
				while (!reachedEnd && alph[currLetter] != Character.toLowerCase(unicodeModeler.getBaseCharacterString(a[i]).charAt(0)))
				{
					currLetter++;
					if (currLetter >= alph.length)
					{
						reachedEnd = true;
						toString += "\r\nOther:\r\n";
					}
				}

				if (!reachedEnd)
					toString += "\r\n" + Character.toUpperCase(alph[currLetter]) + ":\r\n";
			}

			toString += a[i] + ":\r\n\t" + fetchTermForKey(a[i]).getDefinition() + "\r\n";
		}

		try
		{

			PrintWriter saveWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(location), "UTF-8"));

			saveWriter.print(toString);
			saveWriter.flush();
			saveWriter.close();
			
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			this.updateLastUsedDirectory(location);
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
		if (glossary.removeByKey(key) != (null))
		{
			operations.push(new Operation(Operation.operationType.REMOVE, new RemoveOperationData(null,null)));
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
		if (isClean())
			return true;
		else
			return confirmUnsaved();
	}

	public boolean editEntry(String key, Term definition, String oldKey)
	{
		operations.push(new Operation(Operation.operationType.EDIT, new EditOperationData()));
		setTitleToUnsaved();
		boolean s = (glossary.removeByKey(oldKey) != null);
		glossary.addTerm(key, definition);
		return s && (glossary.get(key) != null);

	}

	private void clearDirtyList()
	{
		operations.clear();
	}

	/**
	 * Creates config files if they do not exist and put in default configurations
	 */
	private void setupDirectories()
	{

		if (!SYS_DIRECTORY.exists())
		{
			SYS_DIRECTORY.mkdirs();
		}

		try
		{
			if (!SPECIAL_CHARACTER_CONFIG_FILE.exists())
			{
				SPECIAL_CHARACTER_CONFIG_FILE.createNewFile();
				PrintWriter saveWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(SPECIAL_CHARACTER_CONFIG_FILE), "UTF-8"));
				saveWriter.write(SpecialCharacterChooser.getDefaultFavorites());
				saveWriter.close();
			}
			if (!SYS_CONFIG_FILE.exists())
			{
				SYS_CONFIG_FILE.createNewFile();
				PrintWriter saveWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(SYS_CONFIG_FILE), "UTF-8"));
				saveWriter.write(LAST_USED_DIRECTORY+":::"+FileSystemView.getFileSystemView().getHomeDirectory()+"\r\n");
				saveWriter.close();

			}
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Reads information from config files. Must be called after
	 * setupDirectories
	 */
	private void setupConfigurationInformation()
	{
		try
		{
			Scanner s = new Scanner(SYS_CONFIG_FILE);
			while(s.hasNextLine())
			{
				String[] a = s.nextLine().split(":::");
				configurationInformation.put(a[0], a[1]);
			}
			s.close();
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void updateLastUsedDirectory(String dir)
	{
		configurationInformation.replace(LAST_USED_DIRECTORY, dir);
	}

	private void saveConfigurations()
	{
		try
		{
			PrintWriter saveWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(SYS_CONFIG_FILE), "UTF-8"));

			Set<String> ks = configurationInformation.keySet();
			Iterator<String> i = ks.iterator();
			
			while (i.hasNext())
			{
				String key = i.next();
				saveWriter.write(key+":::"+configurationInformation.get(key));
			}

			saveWriter.close();
		}
		catch (Exception e)
		{

		}
	}
	
	public File lastDirectory()
	{
		if(configurationInformation.get(LAST_USED_DIRECTORY).equals(""))
		{
			return null;
		}
		return new File(configurationInformation.get(LAST_USED_DIRECTORY));
	}

	public float getVersion()
	{
		return VERSION_NUMBER;
	}
}
