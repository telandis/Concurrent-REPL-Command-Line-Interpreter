package cs131.pa1.filter.concurrent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import cs131.pa1.filter.Filter;
import cs131.pa1.filter.Message;

public class RedirectFilterHolder extends ConcurrentFilter {
	private FileWriter fw;
	
	public RedirectFilter(String line) throws Exception {
		super();
		String[] param = line.split(">");
		if(param.length > 1) {
			if(param[1].trim().equals("")) {
				System.out.printf(Message.REQUIRES_PARAMETER.toString(), line.trim());
				throw new Exception();
			}
			try {
				fw = new FileWriter(new File(ConcurrentREPL.currentWorkingDirectory + Filter.FILE_SEPARATOR + param[1].trim()));
			} catch (IOException e) {
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
				if(!(input.peek() instanceof String && input.peek() == termination)) {
					processLine(input.take());
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public String processLine(String line) {
		if(line != termination) {
			try {
				fw.append(line + "\n");
				if(isDone()) {
					fw.flush();
					fw.close();
				}
			} catch (IOException e) {
				System.out.printf(Message.FILE_NOT_FOUND.toString(), line);
			}
		} else {
			catchBug = false;
		}
		return null;
	}
	
	public boolean isDone() {
		if(input.peek() instanceof String) {
			return input.peek() == termination;
		} else {
			return false;
		}
	}
}
