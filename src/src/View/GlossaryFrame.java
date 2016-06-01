/**
Evan Wang
*/

package View;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import Controller.Controller;

public class GlossaryFrame extends JFrame
{
	Controller controller;
	GlossaryPanel glossaryPanel;
	public GlossaryFrame(Controller controller)
	{
		try
		{
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception e)
		{
			
		}
		
		glossaryPanel = new GlossaryPanel(controller);
		this.controller = controller;
		this.setTitle("SimpleGlossary");
		this.setSize(new Dimension(300,500));
		this.setContentPane(glossaryPanel);
		this.setVisible(true);
		
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		/**	
		 * puts a check on the close operation
		 */
		this.addWindowListener(new WindowAdapter() 
		{
		    @Override
		    public void windowClosing(WindowEvent windowEvent) 
		    {
		          if (JOptionPane.showConfirmDialog(null, "Do you really want to exit the program?", "Exit?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) 
		        	  System.exit(0);
		    }
		});
		
	}
	
	public void displayGlossaryKeys()
	{
		glossaryPanel.displaySortedKeys(controller.getGlossaryKeys());
		
	}
	
}
