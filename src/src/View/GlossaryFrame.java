/**
Evan Wang
*/

package View;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import Controller.Controller;

public class GlossaryFrame extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8486546428689664794L;
	
	private Controller controller;
	private GlossaryPanel glossaryPanel;
	private final Dimension PREFERRED_SIZE = new Dimension(300,500);
	private final Font DEFAULT_FONT = new Font("Tahoma",Font.PLAIN,12);
	
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
		this.setIconImage(new ImageIcon("C:\\Users\\Evan\\Desktop\\Stuff\\Random\\ICO\\Pohatu, v1.ico").getImage());
		this.setSize(PREFERRED_SIZE);
		this.setMinimumSize(PREFERRED_SIZE);
		this.setContentPane(glossaryPanel);
		this.setLocationByPlatform(true);
		this.setVisible(true);
		this.setFont(DEFAULT_FONT);
		
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
	
	public void displaySections(String [] s)
	{
		glossaryPanel.loadSections(s);
	}
	public void clearAllForOpen()
	{
		glossaryPanel.clearAllForOpen();
		this.repaint();
		this.revalidate();
	}
	
	public Font getDefaultFont()
	{
		return DEFAULT_FONT;
	}
}
