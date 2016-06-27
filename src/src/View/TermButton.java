/**
Evan Wang
*/

package View;

import javax.swing.JButton;

public class TermButton extends JButton
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6084184633937681824L;
	
	private String storeText;
	public TermButton(String displayText, String storeText)
	{
		super(displayText);
		this.storeText = storeText;
	}
	
	public String getStoredText()
	{
		return storeText;
	}
	
	public void setStoredText(String storeText_)
	{
		storeText = storeText_;
	}
}
