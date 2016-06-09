/**
Evan Wang
Currently supports only Latin characters in the following unicode blocks. More may be added in the future
Latin Extended Additional: U+1E00-U+1EFF
Latin Extended-A: U+0100-U+017F
Latin Extended-B: U+0180-U+024F
Latin Extended-C: U+2C60-U+2C7F
Not Latin Extended-D: U+A720-U+A7FF, currently
Latin-1 Supplement: u+00C0-u+00FF
 */

package Model;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import Controller.diacritic;

public class UnicodeModeler
{

	/**
	 * Unicode characters
	 */
	final String smallAMacron = "\u0101";
	final String largeAMacron = "\u0100";
	final String smallAUmlaut = "\u00E4";
	final String largeAUmlaut = "\u00C4";
	final String smallECircumflex = "\u00EA";
	final String largeECircumflex = "\u00CA";
	final String smallEUmlaut = "\u00EB";
	final String largeEUmlaut = "\u00CB";
	final String smallOUmlaut = "\u00F6";
	final String largeOUmlaut = "\u00D6";
	final String smallIUmlaut = "\u00EF";
	final String largeIUmlaut = "\u00CF";
	final String smallICircumflex = "\u00EE";
	final String largeICircumflex = "\u00CE";
	final String smallICaron = "\u01D0";
	final String largeICaron = "\u01CF";
	final String smallUUmlaut = "\u00FC";
	final String largeUUmlaut = "\u00DC";
	final String smallHCircumflex = "\u0125";
	final String largeHCircumflex = "\u0124";
	final String smallNTilde = "\u00F1";
	final String largeNTilde = "\u00D1";

	/**
	 * Currently supported blocks of unicode characters
	 */
	private final int lExtendedAddLower = 0x1e00;
	private final int lExtendedAddUpper = 0x1eff;

	private final int lExtendedALower = 0x0100;
	private final int lExtendedAUpper = 0x017F;

	private final int lExtendedBLower = 0x0180;
	private final int lExtendedBUpper = 0x024F;

	private final int lExtendedCLower = 0x2C60;
	private final int lExtendedCUpper = 0x2C7F;

	private final int lExtendedDLower = 0xA720;
	private final int lExtendedDUpper = 0xA7FF;
	/**
	 * 
	 */

	/**
	 * Data
	 */
	ArrayList<Integer[]> latinBlockRanges;
	ArrayList<Integer> latinBlockCodes;
	HashMap<String, String> unicodeCharsFavorites;
	char[] alphabet;

	public UnicodeModeler(String[] favorites)
	{

	}

	public UnicodeModeler()
	{

		alphabet = new char[26];

		latinBlockRanges = new ArrayList<Integer[]>();
		latinBlockCodes = new ArrayList<Integer>();
		unicodeCharsFavorites = new HashMap<String, String>();

		createBlocks();
		loadLatinBlocks();
		loadAlphabet();
	}

	/**
	 * From http://stackoverflow.com/questions/17575840/better-way-to-generate-
	 * array-of-all-letters-in-the-alphabet/27735081#27735081
	 */
	private void loadAlphabet()
	{
		int k = 0;
		for (int i = 0; i < 26; i++)
		{
			alphabet[i] = (char) (97 + (k++));
		}
	}

	/**
	 * Set up the ranges for the unicode characters supported and store them in
	 * a list of pairs. The first number in the pair is the lower bound and the
	 * second number is the upper bound
	 */
	private void createBlocks()
	{
		latinBlockRanges.add(new Integer[] { lExtendedAddLower, lExtendedAddUpper });
		latinBlockRanges.add(new Integer[] { lExtendedALower, lExtendedAUpper });
		latinBlockRanges.add(new Integer[] { lExtendedBLower, lExtendedBUpper });
		latinBlockRanges.add(new Integer[] { lExtendedCLower, lExtendedCUpper });
		// latinBlockRanges.add(new Integer[]{lExtendedDLower,lExtendedDUpper});

	}

	private void loadLatinBlocks()
	{
		for (Integer[] e : latinBlockRanges)
		{
			for (int i = e[0]; i <= e[1]; i++)
			{
				// String s = Integer.toHexString(i);
				// System.out.println(s);
				latinBlockCodes.add(i);
			}
		}
	}

	public String getUnicodeCharacter(charCase case_, char base, diacritic modifier)
	{
		return unicodeCharsFavorites.get(case_.toString() + base + modifier.toString());
	}

	public void addUnicodeCharacter(charCase case_, char base, diacritic modifier, String code)
	{
		if (code.charAt(0) != '\\' && code.charAt(1) != 'u')
		{
			code = "\\u" + code;
		}
		else if (code.charAt(0) != '\\' || code.charAt(1) != 'u')
		{
			System.err.println("Error");
		}

		unicodeCharsFavorites.put(case_.toString() + base + modifier.toString(), code);
	}

	public String[] allDescriptors()
	{
		return unicodeCharsFavorites.keySet().toArray(new String[unicodeCharsFavorites.size()]);
	}

	public String[] allCodes()
	{
		return unicodeCharsFavorites.values().toArray(new String[unicodeCharsFavorites.size()]);
	}

	public ArrayList<Integer> getAllCodes()
	{
		return latinBlockCodes;
	}

	public String getBaseCharacterString(String character)
	{
		String n = Normalizer.normalize(character, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
		// System.out.println(Integer.toHexString((int) n.charAt(0)));
		return n;
	}

	private class UnicodeStringComparator implements Comparator<String>
	{

		private boolean isLetter()
		{
			return true;
		}

		@Override
		public int compare(String o1, String o2)
		{
			if ((Character.isAlphabetic(o1.charAt(0)) && Character.isAlphabetic(o2.charAt(0))) || (Character.isLetter(o1.charAt(0)) && Character.isLetter(o2.charAt(0)))
					|| (Character.isDigit(o1.charAt(0)) && Character.isDigit(o2.charAt(0))))
			{
				return getBaseCharacterString(o1).toLowerCase().compareTo(getBaseCharacterString(o2).toLowerCase());
			}
			else if ((Character.isLetter(o1.charAt(0)) || Character.isAlphabetic(o1.charAt(0))))
			{
				return -1;
			}
			else if (Character.isLetter(o1.charAt(0)) || Character.isAlphabetic(o1.charAt(0)))
			{
				return 1;
			}
			else if (Character.isDigit(o1.charAt(0)) && !(Character.isLetter(o2.charAt(0)) || Character.isAlphabetic(o2.charAt(0))))
			{
				return -1;
			}
			else if (Character.isDigit(o2.charAt(0)) && !(Character.isLetter(o1.charAt(0)) || Character.isAlphabetic(o1.charAt(0))))
			{
				return 1;
			}
			else
			{
				return 0;
			}
		}

	}

	public UnicodeStringComparator getUnicodeStringComparator()
	{
		return new UnicodeStringComparator();
	}
}
