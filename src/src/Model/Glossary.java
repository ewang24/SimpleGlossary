/**
Evan Wang
*/

package Model;

import java.util.HashMap;
import java.util.Set;

public class Glossary
{
	HashMap<String,Term> glossary;
	
	public Glossary()
	{
		glossary = new HashMap<String,Term>();
	}
	
	public void addTerm(String key, String definition){
		glossary.put(key, new Term(definition));
	}
	
	/**
	 * @param key to be removed
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
		Object [] s = glossary.keySet().toArray();
		String [] newS = new String[s.length];
		for(int i = 0; i < s.length; i ++)
		{
			newS[i]=(String)s[i];
			System.out.println(newS[i]);
		}
		
		java.util.Arrays.sort(s);
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
}
