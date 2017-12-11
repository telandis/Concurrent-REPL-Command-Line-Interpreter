package cs131.pa1.filter.concurrent;

public class WcFilter extends ConcurrentFilter {
	private int linecount;
	private int wordcount;
	private int charcount;
	
	public WcFilter() {
		super();
	}
	
	public void process() {
		try {
			
			while(!isDone()) {
				String line = input.take();
				if(line == termination) {//if termination string, then end loop
					break;
				}
				processLine(line);
			}
			output.put(processLine(null));
			output.put(termination);

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String processLine(String line) {
		//prints current result if ever passed a null
		if(line == null) {//if empty file or finished processing all lines
			return linecount + " " + wordcount + " " + charcount;
		}
		
		if(isDone()) {//if termination is next input
			String[] wct = line.split(" ");
			wordcount += wct.length;
			charcount += line.length();
			return ++linecount + " " + wordcount + " " + charcount;
		} else {
			linecount++;
			String[] wct = line.split(" ");
			wordcount += wct.length;
			charcount += line.length();
			return null;
		}
	}
}
