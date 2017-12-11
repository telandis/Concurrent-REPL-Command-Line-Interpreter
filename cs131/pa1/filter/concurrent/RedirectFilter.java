package cs131.pa1.filter.concurrent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

import cs131.pa1.filter.Filter;
import cs131.pa1.filter.Message;
import cs131.pa1.filter.concurrent.ConcurrentREPL;

public class RedirectFilter extends ConcurrentFilter {
	String fileName;
	PrintStream finalFile;
	File f = null;
	
	public RedirectFilter(String line) throws Exception {
		super();
		String[] param = line.split(">");
		if(param.length > 1) {
			if(param[1].trim().equals("")) {
				System.out.printf(Message.REQUIRES_PARAMETER.toString(), line.trim());
				throw new Exception();
			}
			
			try {
				
				f = new File (ConcurrentREPL.currentWorkingDirectory, param[1].trim());//create new file object
				
				if (f.exists()) {//if file exists already delete it
					f.delete();
					f.createNewFile();
				} else {
					f.createNewFile();//else create the new file
				}
				
				finalFile = new PrintStream (f);//open up printstream
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				//java requires catching exception
			} catch (IOException e) {
				//java requires catching exception
				System.out.printf(Message.FILE_NOT_FOUND.toString(), line);	//shouldn't really happen but just in case
				throw new Exception();
			}
		} else {
			System.out.printf(Message.REQUIRES_INPUT.toString(), line);
			throw new Exception();
		}
	}
	
	public void process() {
		while(!isDone() && catchBug) {
			try {
				if(!(input.peek() instanceof String && input.peek() == termination)) {//checks for termination
					processLine(input.take());
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		finalFile.close();//close printstream
	}
	
	public String processLine(String line) {
		if(line != termination) {//check if termination got passed down
			if (!line.equals(f.getName())) {//since the file is created beforehand, don't count it if its read and returned
				finalFile.println(line);//print to file
			}	
		} else {
			catchBug = false;//if so then set catchBug to false
		}
		return null;
	}
}
