package cs131.pa1.filter.concurrent;

public class PrintFilter extends ConcurrentFilter {
	public PrintFilter() {
		super();
	}
	
	public void process() {
		while(!isDone() && catchBug) {
			try {//loop that takes all inputs and prints them until termination is detected or passed on
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
			System.out.println(line);//prints line to console unless it is termination
		} else {//sets catchBug to false if termination is found
			catchBug = false;
		}
		//System.out.println(line);
		return null;
	}
	
	public boolean containsNearbyDuplicate(int[] nums, int k) {
        for(int i = 0; i < nums.length-1; i++) {
        	for(int j = i+1; j < nums.length && j < i+j; j++) {
        		if((nums[i] == nums[j]) && (j - i) <= k) {
        			return true;
        		}
        	}
        }
        return false;
    }
}
