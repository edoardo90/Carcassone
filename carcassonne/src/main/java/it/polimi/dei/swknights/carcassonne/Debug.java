package it.polimi.dei.swknights.carcassonne;

import java.io.PrintWriter;

public class Debug
{

	public static void print(String message)
	{
		printer.println(message);
		printer.flush();
	}
	private static PrintWriter printer = new PrintWriter(System.out);
}