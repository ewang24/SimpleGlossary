/**
Evan Wang

This is test code
*/

package Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.UIManager;

import Model.UnicodeModeler;
import View.SpecialCharacterChooser;

public class DataGenerator
{

	public static void main(String[] args)
	{
		
		System.out.println(Integer.parseInt("d1",16));
//		PrintWriter saveWriter;
//		try
//		{
//			File f = new File("Test.txt");
////			if(!f.exists())
////				f.mkdirs();
//			
//			System.out.println(f.getAbsolutePath());
//			saveWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(f.getAbsolutePath()), "UTF-8"));
//			saveWriter.print("Test");
//			saveWriter.flush();
//			saveWriter.close();
//		}
//		catch (Exception e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		

		
//		"Ökur","Sëki",
//		UnicodeModeler u = new UnicodeModeler();
//		String [] sa = {"Ö","ë","$","2","@","1","!","#","%","^","&","*","(",")","0"};
//		Arrays.sort(sa,u.getUnicodeStringComparator());
//		for(String e : sa)
//		{
//			System.out.println(e);
////			char h = e.charAt(0);
////			System.out.println("Is letter: "+Character.isLetter(h)+" Is digit: "+Character.isDigit(h)+" Is Alphabetic "+Character.isAlphabetic(h)+" "+e);
//		}
		
//		String s = "Ökur";
//		System.out.println(u.getBaseCharacterString(s));
//		System.out.println(u.getBaseCharacterString("hi"));
		

//		System.out.println((char)-1);
//		System.out.println(Integer.parseInt("00F0",16));
//		SpecialCharacterChooser s = new SpecialCharacterChooser(u);
//		ArrayList<Integer> a = u.getAllCodes();
//		for(int _: a)
//		{
//			System.out.println(Character.getName(_));
//		}
		
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
