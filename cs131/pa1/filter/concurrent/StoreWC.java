package cs131.pa1.filter.concurrent;

public class WcFilter1 extends ConcurrentFilter {
	private int linecount;
	private int wordcount;
	private int charcount;
	
	public WcFilter() {
		super();
	}
	
	public void process() {
		
		while (!(input.peek() == termination) && catchBug){
			String line;
			try {
				
				if(!(input.peek() instanceof String && input.peek() == termination)) {
					line = input.take();
					String processedLine = processLine(line);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			if(isDone()) {
				output.put(processLine(null));
				output.put(termination);
			}
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
