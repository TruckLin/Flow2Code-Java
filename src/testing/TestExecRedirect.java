package testing;

import java.io.*;
import java.util.Scanner;
public class TestExecRedirect {
   public static void main(String[] a) {
      try {
         Process p = Runtime.getRuntime().exec("java Add2Numbers");  // Execute with input/output
 
         // Write into the standard input of the subprocess
         PrintStream pin = new PrintStream(new BufferedOutputStream(p.getOutputStream()));
         // Read from the standard output of the subprocess
         BufferedReader pout = new BufferedReader(new InputStreamReader(p.getInputStream()));
         
         // Read from the standard error of the subprocess
         BufferedReader perr = new BufferedReader(new InputStreamReader(p.getErrorStream()));
         
         // Pump in input
         Scanner sc = new Scanner(System.in);
         pin.print(sc.nextLine());
         pin.close();
 
         // Save the output in a StringBuffer for further processing
         StringBuffer sb = new StringBuffer();
         int ch;
         while ((ch = pout.read()) != -1) {
            sb.append((char)ch);
         }
         System.out.println(sb);
         
         int exitValue = p.waitFor();
         System.out.println("Process Completed with exit value of " + exitValue);
      } catch (IOException ex) {
      } catch (InterruptedException ex) {}
   }
}