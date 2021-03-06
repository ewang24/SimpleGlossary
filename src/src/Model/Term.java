/**
Evan Wang
 */

package Model;

import Controller.Controller;

public class Term
{
	String definition;
	String section;
	private boolean isRemoved = false;
	private String[] seeAlsoList;

	public Term(String definition, String[] seeAlsoList_, String section_)
	{
		this.definition = definition;
		seeAlsoList = seeAlsoList_;
		section = section_;
	}

	public String getDefinition()
	{
		return definition;
	}

	public void setDefinition(String definition_)
	{
		definition = definition_;
	}

	public void setToRemoved()
	{
		isRemoved = true;
	}

	public void setToAdded()
	{
		isRemoved = false;
	}

	public boolean isRemoved()
	{
		return isRemoved;
	}

	public String[] getSeeAlsoList()
	{
		return this.seeAlsoList;
	}
	
	public void setSeeAlsoList(String [] seeAlsoList_)
	{
		seeAlsoList = seeAlsoList_;
	}

	public String getSeeAlsoListString()
	{

		if (seeAlsoList.length == 0)
			return " ";
		else
		{
			String s = "";
			
			for (int i = 0; i < seeAlsoList.length - 1; i++)
			{
				s += seeAlsoList[i] + Controller.getFileSeeAlsoDelimiter();
			}

			return s + seeAlsoList[seeAlsoList.length - 1];
		}
	}

	public String getSection()
	{
		return this.section;
	}

}
