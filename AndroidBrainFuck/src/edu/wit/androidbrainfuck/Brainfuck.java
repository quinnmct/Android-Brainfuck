package edu.wit.androidbrainfuck;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;


//BRAINFUCK Java Class
//Quinn McTiernan, Matt Shrago, Aaron Pereira, Brett Steinburg
//
//This is the class used to parse the brainfuck 
//code given as input by the Android user


public class Brainfuck {

	public BufferedReader readFile; // allows for file reading if a file is used
	public InputStreamReader readConsole; // reading the input file
	//public OutputStream writeConsole; // writing to the console
	
	public byte[] data; //data used to run the program
	public int dataPtr = 0; // pointer used to point to the current index of the data byte array
	public int charPtr = 0; //char pointer pointing to current index of char array
	

	public int lineCount = 0; 
	public int colCount = 0;
	
	public String outputBF="";
	

	//initialize brainfuck object without input file
	public Brainfuck(int cells) {
		this(cells, new PrintStream(System.out), System.in);
	}

	
	public Brainfuck(int cells, OutputStream output, InputStream input) {
		//init global vars
		dataPtr = 0;
		charPtr = 0;
		data = new byte[cells];
		
		//writeConsole = output;
		
		readConsole = new InputStreamReader(input); 
	}
	

	//Interpret bf string
	public String bfInterpret(String s) throws Exception {
		for (; charPtr < s.length(); charPtr++) {
			bfInterpret(s.charAt(charPtr), s.toCharArray());
		}
		//re-init global variables
		data = new byte[data.length];
		dataPtr = 0;
		charPtr = 0;
		
		return outputBF;
	}

	//Interpret single bf character
	protected void bfInterpret(char c, char[] charArr) throws Exception {
		
		switch (c) {
		
			// inc data pointer
			case Command.NEXT:
				if ((dataPtr + 1) > data.length) {
						throw new Exception("Error: " + lineCount + ", col: " + colCount + ":" 
						+ "data pointer (" + dataPtr
						+ ") on postion " + charPtr + " " + "negative.");
					}
				dataPtr++;
				break;
			
			// dec data pointer	
			case Command.PREVIOUS:
				if ((dataPtr - 1) < 0) {
					throw new Exception("Error: " + lineCount + ", col: " + colCount + ":" 
						+ "data pointer (" + dataPtr
						+ ") on postion " + charPtr + " " + "negative.");
				}
				dataPtr--;
				break;
				
			case Command.INC:
	
				// inc byte at the data pointer.
				if ((((int) data[dataPtr]) + 1) > Integer.MAX_VALUE) {
					throw new Exception("Error: " + lineCount + ", col: " + colCount + ":" 
						+ "data pointer (" + dataPtr
						+ ") on postion " + charPtr + " " + "negative.");
				}
				data[dataPtr]++;
				break;
				
			case Command.DEC:
				// dec byte at the data pointer.
				data[dataPtr]--;
				break;
				
			case Command.OP:
				// Output the byte at the current index in a character.
				//writeConsole.write((char) data[dataPtr]);
				outputBF += (char) data[dataPtr];
				break;
				
			case Command.IP:
				// accept one byte of input, storing its value in the byte at the data pointer.
				data[dataPtr] = (byte) readConsole.read();
				break;
				
			case Command.LEFTBRACKET:
				if (data[dataPtr] == 0) {
					int i = 1;
					while (i > 0) {
						char nextChar = charArr[++charPtr];
						if (nextChar == Command.LEFTBRACKET)
							i++;
						else if (nextChar == Command.RIGHTBRACKET)
							i--;
					}
				}
				break;
				
			case Command.RIGHTBRACKET:
				int i = 1;
				while (i > 0) {
					char c2 = charArr[--charPtr];
					if (c2 == Command.LEFTBRACKET)
						i--;
					else if (c2 == Command.RIGHTBRACKET)
						i++;
				}
				charPtr--;
				break;
			}
		
		colCount++;
	}
		
	//command class with all applicable brainfuck commands
	public class Command {
	
		public final static char NEXT = '>';
		public final static char PREVIOUS = '<';
		public final static char INC = '+';
		public final static char DEC = '-';
		public final static char OP = '.';
		public final static char IP = ',';
		public final static char LEFTBRACKET = '[';
		public final static char RIGHTBRACKET = ']';
	}
	
}