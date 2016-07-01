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

import Controller.Controller;

public class Glossary
{
	HashMap<String, Term> glossary;
	HashMap<String, ArrayList<String>> sectionMap;
	ArrayList<String> modifiedKeys;
	UnicodeModeler u;

	public Glossary()
	{
		glossary = new HashMap<String, Term>();
		sectionMap = new HashMap<String, ArrayList<String>>();
		modifiedKeys = new ArrayList<String>();
		u = new UnicodeModeler();
	}

	/**
	 * @param key
	 *            to added
	 * @param definition
	 *            : mapped to by key
	 * @return true if the entry was sucessfully added. False if the definition
	 *         is already in the glossary.
	 */
	public boolean addUnsavedTerm(String key, Term definition)
	{
		if (!glossary.containsKey(key))
		{
			modifiedKeys.add(key);
			addTerm(key, definition);
			return true;
		}
		return false;
	}

	public void addTerm(String key, Term term)
	{
		glossary.put(key, term);
		addTermToSections(key);

	}

	/**
	 * Add an entry that's already in the glossary into a section. Must be
	 * called after the entry to be added to a section has already been added to
	 * the glossary.
	 * 
	 * @param key
	 */
	private void addTermToSections(String key)
	{
		for (String e : get(key).getSectionList())
		{
			if (!sectionMap.containsKey(e))
			{
				sectionMap.put(e, new ArrayList<String>());
			}

			sectionMap.get(e).add(key);
		}
	}

	/**
	 * @param section
	 *            the section to remove
	 * @return true if the section was sucesfully removed, false otherwise.
	 */
	public boolean removeSection(String section)
	{
		return sectionMap.remove(section) != null;
	}

	public boolean addSection(String newSection)
	{
		if (sectionMap.containsKey(newSection))
			return false;

		sectionMap.put(newSection, new ArrayList<String>());
		return sectionMap.containsKey(newSection);

	}

	/**
	 * @return number of sections in the glossary
	 */
	public int getNumberOfSections()
	{
		return sectionMap.size();
	}

	public String[] getAllSections()
	{
		Set<String> s = sectionMap.keySet();
		String[] a = new String[s.size()];
		Iterator<String> i = s.iterator();
		int ii = 0;
		while (i.hasNext())
		{
			a[ii] = i.next();
			ii++;
		}
		return a;
	}

	/**
	 * @param section
	 *            the section desired
	 * @return all of the terms within specified section, null if the section
	 *         does not exist.
	 */
	public boolean hasSection(String section)
	{
		return sectionMap.containsKey(section);
	}

	/**
	 * @param comparator
	 *            the comparator to sort the entries
	 * @param section
	 *            the section to get all entries from
	 * @return a sorted list of all of the keys from one section.
	 */
	public String[] getSortedSection(Comparator comparator, String section)
	{

		if (section.equals(Controller.getDefaultSectionName()))
		{
			return getKeys(comparator);
		}
		if (hasSection(section))
		{
			String[] a = new String[sectionMap.get(section).size()];
			int i = 0;
			for (String t : sectionMap.get(section))
			{
				a[i] = t;
				i++;
			}

			java.util.Arrays.sort(a, comparator);
			return a;
		}
		return new String[0];
	}

	/**
	 * @param key
	 *            to be removed
	 * @return true if the term was removed sucesfully
	 */
	public boolean removeByKey(String key)
	{
		// Must remove from sectionMap first.

		boolean b = removeFromAllSections(key);
		System.out.println(b);
		return b && glossary.remove(key) != null;
	}

	private boolean removeFromAllSections(String key)
	{
		boolean success = true;
		Term tbr = glossary.get(key);
		for (String currentSection : tbr.getSectionList())
		{
			success = removeFromSection(key, currentSection) && success;
		}
		return success;

	}

	public boolean removeFromSection(String key, String section)
	{
		if (sectionMap.containsKey(section))
		{
			return sectionMap.get(section).remove(key);
		}
		return false;
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

		java.util.Arrays.sort(newS, comparator);
		return newS;
	}

	public Term[] getValues()
	{
		return (Term[]) glossary.values().toArray();
	}

	@Override
	public String toString()
	{

		String toStringString = "";

		Set<Entry<String, Term>> gs = glossary.entrySet();
		Iterator<Entry<String, Term>> i = gs.iterator();

		while (i.hasNext())
		{
			Entry<String, Term> e = i.next();
			toStringString += e.getKey() + Controller.getFileDelimiter() + e.getValue().getDefinition() + Controller.getFileDelimiter() + e.getValue().getSeeAlsoListString()
					+ Controller.getFileDelimiter() + e.getValue().getSectionListString() + "\r\n";
		}

		return toStringString;
	}

	/**
	 * @return the glossary in the form of an easily readable string. Doesn't
	 *         include delimeters for saving the file for re-use
	 */
	public String toText()
	{

		String toStringString = "";

		Set<Entry<String, Term>> gs = glossary.entrySet();
		Iterator<Entry<String, Term>> i = gs.iterator();

		while (i.hasNext())
		{
			Entry<String, Term> e = i.next();
			toStringString += e.getKey() + ":\r\n\t" + e.getValue().getDefinition() + "\r\n\r\n";
		}

		return toStringString;
	}

	/**
	 * @return the glossary in the form of an easily readable string, sorted
	 *         alphabetically. Doesn't include delimeters for saving the file
	 *         for re-use
	 */
	public String toSortedText()
	{

		String toStringString = "";

		Set<Entry<String, Term>> gs = glossary.entrySet();
		Iterator<Entry<String, Term>> i = gs.iterator();

		while (i.hasNext())
		{
			Entry<String, Term> e = i.next();
			toStringString += e.getKey() + ":\r\n\t" + e.getValue().getDefinition() + "\r\n\r\n";
		}

		return toStringString;
	}
	
	public String getSectionString()
	{
		String sectionString = "";
		Set<String> s = sectionMap.keySet();
		Iterator<String> i = s.iterator();
		while(i.hasNext())
		{
			sectionString+= i.next()+Controller.getFileSeeAlsoDelimiter();
		}
		return sectionString.substring(0, sectionString.length()-1)+"\r\n";
				
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
