/**
Evan Wang
*/

package Model;

public class Term
{
	String definition;
	private boolean isRemoved = false;
	
	
	public Term(String definition)
	{
		this.definition = definition;
	}
	
	public String getDefinition()
	{
		return definition;
	}
	
	public void setDefinition(String Definition)
	{
		this.definition = definition;
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

