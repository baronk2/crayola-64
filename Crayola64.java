/*
Kevin Baron
11/17/12
Crayola 64
*/

import java.awt.*;	//for Graphics and DrawingPanel
import java.util.*;	//for Scanner
import java.io.*;		//for File and FileNotFoundException

public class Crayola64 {

   public static final boolean WHITEOUTLINE = false;	//whether or not there is an outline around the blank color used for space.
   public static final int FONTSIZE = 18;
   public static final int COLUMNWIDTH = 65;	//how many characters wide the screen will be.
   public static final int YMARGIN = 20;
   public static final int XMARGIN = 20;

	//process a message.
	public static void main(String[] args) throws FileNotFoundException {
      processor(getMessage());
   }//eomain
	
	//give an intro and ask for a message. Return the message.
   public static String getMessage() {
      System.out.println("This program takes a line you type and converts it to Crayola-64.");
      System.out.println("What is your message?");
      return new Scanner(System.in).nextLine();
   }//eogetMessage

   //draw the panel and determine where to put each character on the panel.
	public static void processor(String message) throws FileNotFoundException  {
      DrawingPanel panel = new DrawingPanel(panelX(message), panelY(message));
      Graphics g = panel.getGraphics();
   
      int x = XMARGIN;
      int y = YMARGIN;
   
      for (int i = 0; i < message.length(); i++) {
         char ch = message.charAt(i);
      
         if (ch == 40 || ch == 60 || ch == 47 || ch == 91 || ch == 123 || (ch > 64 && ch < 91)) {
            converter(g, '`', x, y);
            if (ch > 64 && ch < 91) {
               ch = (char) (ch + 32);
            }//eoif
            x += FONTSIZE;
            if (x >= XMARGIN + COLUMNWIDTH * FONTSIZE) {
               x = XMARGIN;
               y += FONTSIZE;
            }//eoif
         }//eoif
      
         converter(g, ch, x, y);
         x += FONTSIZE;
         if (x >= XMARGIN + COLUMNWIDTH * FONTSIZE) {
            x = XMARGIN;
            y += FONTSIZE;
         }//eoif
      	
         if (ch == 41 || ch == 62 || ch == 92 || ch == 93 || ch == 125) {
            converter(g, '`', x, y);
            x += FONTSIZE;
            if (x >= XMARGIN + COLUMNWIDTH * FONTSIZE) {
               x = XMARGIN;
               y += FONTSIZE;
            }//eoif
         }//eoif
      }//eofor
   }//eoprocessor

   //scan rgb.txt to determine what RGB values are used for each character. If the character is not found, the default is white.
	public static void converter(Graphics g, char ch, int x, int y) throws FileNotFoundException {
      Scanner input = new Scanner(new File("rgb.txt"));
      int red = 255;
      int green = 255;
      int blue = 255;
      while (input.hasNextLine()) {
         Scanner info = new Scanner(input.nextLine());
         if (info.nextInt() == ch) {
            red = info.nextInt();
            green = info.nextInt();
            blue = info.nextInt();
         }//eoif
      }//eowhile
      g.setColor(new Color(red, green, blue));
      g.fillRect(x, y, FONTSIZE, FONTSIZE);
      if (ch == 32 && WHITEOUTLINE == true) {
         g.setColor(Color.BLACK);
         g.drawRect(x, y, FONTSIZE - 1, FONTSIZE - 1);
      }//eoif
   }//eoconverter
	
	//tells how wide the panel should be given the length of the message
   public static int panelX(String message) {
      if (COLUMNWIDTH < outputActualLength(message)) {
         return (2 * XMARGIN + FONTSIZE * COLUMNWIDTH);
      } else {
         return (2 * XMARGIN + FONTSIZE * outputActualLength(message));
      }//eoifelse
   }//eopanelX
	
	//tells how tall the panel should be given the length of the message
   public static int panelY(String message) {
      return (2 * YMARGIN + FONTSIZE * ((outputActualLength(message) - 1) / COLUMNWIDTH + 1));
   }//eopanelY

	//because some characters require 
   public static int outputActualLength(String message) {
      int numberOfDoubled = 0;
      String list = "ABCDEFGHIJKLMNOPQRSTUVWXYZ()<>[]{}/\\";
      for (int i = 0; i < message.length(); i++) {
         if (list.indexOf(message.charAt(i)) != -1) {
            numberOfDoubled++;
         }//eoif
      }//eofor
      return message.length() + numberOfDoubled;
   }//eooutputActualLength

}//eoclass