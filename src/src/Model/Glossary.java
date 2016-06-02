/**
Evan Wang
 */

package Model;

import java.util.HashMap;
import java.util.Set;

public class Glossary
{
	HashMap<String, Term> glossary;
	private int size = 0;

	public Glossary()
	{
		glossary = new HashMap<String, Term>();
	}

	public void addTerm(String key, String definition)
	{
		size++;
		glossary.put(key, new Term(definition));
	}

	/**
	 * @param key
	 *            to be removed
	 * @return the term removed
	 */
	public Term removeByKey(String key)
	{
		size--;
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
		System.out.println(size);
		return size;
	}
}
