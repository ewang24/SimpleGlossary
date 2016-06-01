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
	public Controller()
	{
		glossary = new Glossary();
		load();
		GlossaryFrame gf = new GlossaryFrame(this);
		gf.displayGlossaryKeys();
	}
	
	private void load()
	{
		File f = new File("C:\\Users\\Evan\\Documents\\GitHub\\SimpleGlossary\\src\\glossary.gl");
		String s = "";
		try
		{
			Scanner sc = new Scanner(f);
			while(sc.hasNextLine())
			{
				s += sc.nextLine()+"\n";
			}
			System.out.println(s);
		}
		catch (FileNotFoundException e){e.printStackTrace();}
		
		String [] a = s.split("\n");
		
		for(int i = 0; i < a.length; i++)
		{
			glossary.addTerm(a[i].substring(0, a[i].indexOf(":::")), a[i].substring(a[i].indexOf(":::")+3, a[i].length()));
		}
		
		
	}
	
	public String[] getGlossaryKeys()
	{
		return glossary.getKeys();
	}
	
	public Term fetchTermForKey(String key)
	{
		return glossary.get(key);
	}
	
}
