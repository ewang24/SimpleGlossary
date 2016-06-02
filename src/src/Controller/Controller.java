/**
Evan Wang
*/

package Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

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
			glossary.addTerm(termList[i].substring(0, termList[i].indexOf(":::")), termList[i].substring(termList[i].indexOf(":::")+3, termList[i].length()));
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
}
