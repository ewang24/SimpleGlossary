/**
Evan Wang
*/

package Model;

public class Operation
{
	public static enum operationType{ADD,REMOVE,ADDTEXT,REMOVETEXT};
	
	private operationType operation;
	private Object data;
	
	public Operation(operationType operation_, Object data_)
	{
		operation = operation_;
		data = data_;
	}

	public operationType getOperation()
	{
		return operation;
	}

	public Object getData()
	{
		return data;
	}

	public void setOperation(operationType operation_)
	{
		operation = operation_;
	}

	public void setData(Object data_)
	{
		data = data_;
	}
	
	
}
