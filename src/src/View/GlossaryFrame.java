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
	final Dimension PREFERRED_SIZE = new Dimension(300,500); 
	public GlossaryFrame(Controller controller)
	{
		try
		{
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception e)
		{
			
		}
		
		glossaryPanel = new GlossaryPanel(controller,this);
		this.controller = controller;
		this.setTitle("SimpleGlossary");
		this.setSize(PREFERRED_SIZE);
		this.setMinimumSize(PREFERRED_SIZE);
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
		         exit();
		    }
		});
		
	}
	
	private void exit()
	{
		controller.exit();
	}
	public void displayGlossaryKeys()
	{
		glossaryPanel.displaySortedKeys(controller.getGlossaryKeys());
	}
	
	public void clearAllForOpen()
	{
		glossaryPanel = new GlossaryPanel(controller, this);
		this.setContentPane(glossaryPanel);
		this.repaint();
		this.revalidate();
	}
}
