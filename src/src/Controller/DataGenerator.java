/**
Evan Wang
*/

package Controller;

import javax.swing.UIManager;

import Model.UnicodeModeler;
import View.SpecialCharacterChooser;

public class DataGenerator
{

	public static void main(String[] args)
	{
		try
		{
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception e)
		{
			
		}
		
//		System.out.println(Integer.parseInt("00F0",16));
		UnicodeModeler u = new UnicodeModeler();
		SpecialCharacterChooser s = new SpecialCharacterChooser(u);
//		for(int i = 0; i < 3000; i++)
//			System.out.println("entry"+i+":::"+i);
//		System.out.println(Character.getName(0101)+" "+Character.getType(0101)+" "+Character.getNumericValue(0101));
//		System.out.println("\u0101");
//
//		int c = 0;
//		for(int i = 0x1e00; i <= 0x1eff;i++)
//		{
//			System.out.println(Integer.toHexString(i));
//		}
//		System.out.println(c);
//		
//		int h = 0x1E00;
//		h++;
//		System.out.println(h);
//		Latin Extended Additional: U+1E00-U+1EFF
//		Latin Extended-A: U+0100-U+017F
//		Latin Extended-B: U+0180-U+024F
//		Latin Extended-C: U+2C60-U+2C7F
//		Latin Extended-D: U+A720-U+A7FF
//		Latin-1 Supplement: u+00C0-u+00FF
	}

}
