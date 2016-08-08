/**
Evan Wang
 */

package Controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Converter
{
	public static void main(String[] args)
	{

		try
		{
			Scanner s = new Scanner(System.in);
//			saveWriter.print("v2\r\n");
			String currentString = "v2\r\n\r\n";
			String string = "";
			// Scanner termReader = new Scanner(glossaryFileToUse);
			BufferedReader termReader = new BufferedReader(new InputStreamReader(new FileInputStream(s.nextLine()),"UTF-8"));
			while ((string = termReader.readLine()) != null)
			{
				currentString += string + "::: ::: \r\n";
			}
			termReader.close();
			s.close();
			System.out.println(currentString);
			
			
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
