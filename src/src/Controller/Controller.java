/**
Evan Wang
*/

package Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JOptionPane;

import Model.Glossary;
import Model.Term;
import View.GlossaryFrame;

public class Controller
{
	Glossary glossary;
	GlossaryFrame gf;
	public Controller()
	{
		glossary = new Glossary();
		gf = new GlossaryFrame(this);
		
		load();
		gf.displayGlossaryKeys();
	}
	
	private void load()
	{
		File glossaryFileToLoad = new File("C:\\Users\\Evan\\Documents\\GitHub\\SimpleGlossary\\src\\glossary.gl");
		String rawTermString = "";
		try
		{
			Scanner termReader = new Scanner(glossaryFileToLoad);
			while(termReader.hasNextLine())
			{
				rawTermString += termReader.nextLine()+"\n";
			}
			termReader.close();
		}
		catch (FileNotFoundException e){e.printStackTrace();}
		
		String [] termList = rawTermString.split("\n");
		
		for(int i = 0; i < termList.length; i++)
		{
			glossary.addTerm(termList[i].substring(0, termList[i].indexOf(":::")), new Term(termList[i].substring(termList[i].indexOf(":::")+3, termList[i].length())));
		}
		
		gf.setTitle(glossaryFileToLoad.getName());
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
		if(glossary.isDirty())
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
	 * @param key to added
	 * @param term: mapped to by key
	 * @return true if entry was sucessfully added. False otherwise.
	 */
	public boolean newEntry(String key, Term term)
	{
		return glossary.addUnsavedTerm(key, term);
	}
	
}
