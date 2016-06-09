/**
Evan Wang
 */

package Model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

public class Glossary
{
	HashMap<String, Term> glossary;
	ArrayList<String> modifiedKeys;

	public Glossary()
	{
		glossary = new HashMap<String, Term>();
		modifiedKeys = new ArrayList<String>();
	}

	/**
	 * @param key to added
	 * @param definition: mapped to by key
	 * @return true if the entry was sucessfully added. False if the definition is already in the glossary.
	 */
	public boolean addUnsavedTerm(String key, Term definition)
	{
		if(!glossary.containsKey(key))
		{
			modifiedKeys.add(key);
			addTerm(key,definition);
			return true;
		}
		return false;
	}
	
	public void addTerm(String key, Term definition)
	{
		glossary.put(key, definition);
	}

	/**
	 * @param key
	 *            to be removed
	 * @return the term removed
	 */
	public Term removeByKey(String key)
	{
		return glossary.remove(key);
	}

	public Term get(String key)
	{
		return glossary.get(key);
	}

	public String[] getKeys(Comparator comparator)
	{
		Object[] s = glossary.keySet().toArray();
		String[] newS = new String[s.length];
		for (int i = 0; i < s.length; i++)
		{
			newS[i] = (String) s[i];
		}

		java.util.Arrays.sort(newS,comparator);
		return newS;
	}

	public Term[] getValues()
	{
		return (Term[]) glossary.values().toArray();
	}

	@Override
	public String toString()
	{
		
		String toStringString="";
		
		Set<Entry<String,Term>> gs = glossary.entrySet();
		Iterator<Entry<String, Term>> i = gs.iterator();
		
		while(i.hasNext())
		{
			Entry<String, Term> e = i.next();
			toStringString+= e.getKey()+":::"+e.getValue().getDefinition()+"\r\n";
		}
		
		return toStringString;
	}
	
	/**
	 * @return the glossary in the form of an easily readable string. Doesn't include delimeters for saving the file for re-use
	 */
	public String toText()
	{
		
		String toStringString="";
		
		Set<Entry<String,Term>> gs = glossary.entrySet();
		Iterator<Entry<String, Term>> i = gs.iterator();
		
		while(i.hasNext())
		{
			Entry<String, Term> e = i.next();
			toStringString+= e.getKey()+":\r\n\t"+e.getValue().getDefinition()+"\r\n\r\n";
		}
		
		return toStringString;
	}

	public int getSize()
	{
		return glossary.size();
	}
	
	/**
	 * @return true if the glossary is dirty (has unsaved data)
	 */
	public boolean isDirty()
	{
		return !modifiedKeys.isEmpty();
	}
	
	/**
	 * @return true if all of the glossary's data has been saved.
	 */
	public boolean isClean()
	{
		return !isDirty();
	}
	
	public void clearDirtyList()
	{
		modifiedKeys.clear();
	}
	
	public void clearGlossary()
	{
		clearDirtyList();
		glossary.clear();
	}
}
