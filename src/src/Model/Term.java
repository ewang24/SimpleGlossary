/**
Evan Wang
*/

package Model;

public class Term
{
	String definition;
	String section;
	private boolean isRemoved = false;
	private String[] seeAlsoList;
	
	//, String[] seeAlsoList_, String section_
	public Term(String definition)
	{
		this.definition = definition;
//		seeAlsoList = seeAlsoList_;
//		section = section_;
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
	
	
}

