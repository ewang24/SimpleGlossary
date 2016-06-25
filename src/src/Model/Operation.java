/**
Evan Wang
*/

package Model;

import Controller.Controller;

public class Operation
{
	public static enum operationType{ADD,REMOVE,EDIT,ADDTEXT,REMOVETEXT};
	
	private operationType operation;
	private OperationData data;
	
	public Operation(operationType operation_, OperationData data_)
	{
		operation = operation_;
		data = data_;
	}

	public operationType getOperation()
	{
		return operation;
	}

	public OperationData getData()
	{
		return data;
	}

	public void setOperation(operationType operation_)
	{
		operation = operation_;
	}

	public void setData(OperationData data_)
	{
		data = data_;
	}
	
//	public abstract boolean undo(Controller controller);
//	
//	public abstract boolean redo(Controller controller);
	
	
}
