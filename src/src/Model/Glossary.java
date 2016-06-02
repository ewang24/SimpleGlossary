/**
Evan Wang
 */

package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Glossary
{
	HashMap<String, Term> glossary;
	ArrayList<String> newKeys;

	public Glossary()
	{
		glossary = new HashMap<String, Term>();
		newKeys = new ArrayList<String>();
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
			newKeys.add(key);
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

	public String[] getKeys()
	{
		Object[] s = glossary.keySet().toArray();
		String[] newS = new String[s.length];
		for (int i = 0; i < s.length; i++)
		{
			newS[i] = (String) s[i];
		}

		java.util.Arrays.sort(newS);
		return newS;
	}

	public Term[] getValues()
	{
		return (Term[]) glossary.values().toArray();
	}

	@Override
	public String toString()
	{
		return glossary.toString();
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
		return !newKeys.isEmpty();
	}
	
	/**
	 * @return true if all of the glossary's data has been saved.
	 */
	public boolean isClean()
	{
		return !isDirty();
	}
}
