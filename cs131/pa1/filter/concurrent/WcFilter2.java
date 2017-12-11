package cs131.pa1.filter.concurrent;

public class WcFilter2222 extends ConcurrentFilter {
	private int linecount;
	private int wordcount;
	private int charcount;
	
	public WcFilter2() {
		super();
	}
	
	public void process() {
		try {
			
			while(!isDone()) {
				String line = input.take();
				String processedLine = processLine(line);
			}
			output.put(processLine(null));
			output.put(termination);
//			if(isDone()) {
//				output.put(processLine(null));
//				output.put(termination);
//			} else {
//				super.process();
//			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String processLine(String line) {
		//prints current result if ever passed a null
		if(line == null) {
			return linecount + " " + wordcount + " " + charcount;
		}
		
		if(isDone()) {
			String[] wct = line.split(" ");
			wordcount += wct.length;
			String[] cct = line.split("|");
			charcount += cct.length;
			return ++linecount + " " + wordcount + " " + charcount;
		} else {
			linecount++;
			String[] wct = line.split(" ");
			wordcount += wct.length;
			String[] cct = line.split("|");
			charcount += cct.length;
			return null;
		}
	}
	
	public boolean isDone() {
		if(input.peek() instanceof String) {
			return input.peek() == termination;
		} else {
			return false;
		}
	}
}
