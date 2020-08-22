package fr.bastion.utiles.programs;
import java.lang.Runtime;

public class ProgramJsonParser {

	public void exeJsonParser() {
		
		try {
			  // create a new array of 2 strings
			  String[] cmdArray = new String[2];
			  
	         // first argument is the program we want to open, the second argument is the script we want to run
			  cmdArray[0] = System.getenv("PYTHON_HOME");
			  cmdArray[1] = System.getenv("PROJECT_HOME") + "\\src\\main\\java\\fr\\bastion\\utiles\\JsonParser.py";
			  
			  // print a message
			  System.out.println("Executing python.exe and opening JsonParser");
			
			  // create a process and execute cmdArray and correct environment
			  @SuppressWarnings("unused")
			  Process process = Runtime.getRuntime().exec(cmdArray);
			  
			  // print another message
			  System.out.println("JsonParser should now open.");
			  
			}
		catch (Exception ex) {
			      ex.printStackTrace();  	      
		}
	}
	
	
	// Verif de Python
	//import sys   ->   version = sys.version   ->   if version[0]=="3": check=True else: check=False
	
	
	
	
}
