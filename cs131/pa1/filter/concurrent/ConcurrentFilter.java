package cs131.pa1.filter.concurrent;

import java.util.concurrent.LinkedBlockingQueue;

import cs131.pa1.filter.Filter;


public abstract class ConcurrentFilter extends Filter implements Runnable {
	
	protected LinkedBlockingQueue<String> input;
	protected LinkedBlockingQueue<String> output;
	boolean catchBug = true;//used to double check in processLine if termination string accidentally gets processed
	//random string used to check for termination
	protected static String termination = "9032131231231232131232131123124324fdsafdsfasdfdfsafasd";
	
	@Override
	public void setPrevFilter(Filter prevFilter) {
		prevFilter.setNextFilter(this);
	}
	
	@Override
	public void setNextFilter(Filter nextFilter) {
		if (nextFilter instanceof ConcurrentFilter){
			ConcurrentFilter sequentialNext = (ConcurrentFilter) nextFilter;
			this.next = sequentialNext;
			sequentialNext.prev = this;
			if (this.output == null){
				this.output = new LinkedBlockingQueue<String>();
			}
			sequentialNext.input = this.output;
		} else {
			throw new RuntimeException("Should not attempt to link dissimilar filter types.");
		}
	}
	
	public void process(){
		while (!(input.peek() == termination) && catchBug){//checks if termination is next, or has been processed
			String line;
			try {
				//if statement checks to see if a string is in the list, and if that string is not termination then runs
				if(!(input.peek() instanceof String && input.peek() == termination)) {
					line = input.take();
					String processedLine = processLine(line);
					if (processedLine != null){
						output.put(processedLine);
					}
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {//adds termination to output queue after all inputs are processed
			output.put(termination);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run() {
		// TODO Auto-generated method stub
		this.process();
	}
	
	@Override
	public boolean isDone() {//checks if termination is next
		if(input.peek() instanceof String) {
			return input.peek() == termination;
		} else {
			return false;
		}
	}
	
	protected abstract String processLine(String line);
	
}
