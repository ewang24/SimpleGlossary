/**
Evan Wang
*/

package Model;

public class RemoveOperationData extends OperationData
{

	private String removedKey;
	private String removedData;
	
	public RemoveOperationData(String removedKey_, String removedData_)
	{
		removedKey = removedKey_;
		removedData = removedData_;
	}
	
	/**
	 * Returns an array that has the removed key at 0 and the removed data at 1
	 */
	@Override
	public String[] getNewData()
	{
		return new String[]{removedKey,removedData};
	}

	@Override
	public String[] getOldData()
	{
		return new String[]{removedKey,removedData};
	}
	
	

}
