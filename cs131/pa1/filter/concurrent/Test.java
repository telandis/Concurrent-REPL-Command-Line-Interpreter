package cs131.pa1.filter.concurrent;

public class Test {

	public int solution(int K, int L, int M, int N, int P, int Q, int R, int S) {
        // write your code in Java SE 8
		int triaAreaOne;
		int triaAreaTwo;
		int triaAreaIntersect;
		triaAreaIntersect = (Math.min(N, S) - Math.max(L, Q)) * (Math.min(M, R) - Math.max(K, P));
		triaAreaOne = (N - L) * (M - K);
		triaAreaTwo = (S - Q) * (R - P);
		
		
		if(M > P && M < R && L > Q && L < S && S < N && P > K) {
			triaAreaIntersect = (M - P) * (S - L);
		} else if(P > K && P < M && Q > L && Q < N && M < R && N < S) {
			triaAreaIntersect = (M - P) * (N - Q);
		} else if(K > P && K < R && Q > L && Q < N) {
			
		}
    }
	
	
	 public TreeNode sortedArrayToBST(int[] nums) {
	        if(nums.length == 0) {
	            return null;
	        }
	        TreeNode start = new TreeNode(nums[0]);
	        recursiveTreeCreate(nums, start, 0);
	        return start;
	       
	    }
	    
	    public void recursiveTreeCreate(int[] nums, TreeNode current, int counter) {
	        if(2*counter + 1 < nums.length) {
	            current.left = new TreeNode(nums[2*counter + 1]);
	        }
	        if(2*counter + 2 < nums.length) {
	            current.right = new TreeNode(nums[2*counter + 2]);
	        }
	        if(2*(2*counter + 1) + 1 < nums.length) {
	            recursiveTreeCreate(nums, current.left, 2*counter + 1);
	        }
	        if(2*(2*counter + 2) + 1 < nums.length) {
	            recursiveTreeCreate(nums, current.right, 2*counter + 2);
	        }
	    }
	    
	    public TreeNode recursiveTreeCreate(int[] nums, int low, int high, int mid, TreeNode current) {
	    	
	    }
	    
	    public TreeNode recursiveTreeCreate(int[] nums) {
	        if(nums.length > 1) {
	            
	            int low = 0;
	            int high = nums.length - 1;
	            int mid;
	            if(((high + low)%2) > 0) {
	                mid = ((high + low)/2) + 1;
	            } else {
	                mid = ((high + low)/2);
	            }
	            TreeNode current = new TreeNode(nums[mid]);
	            if(low < mid) {
	                int[] lowNums = Arrays.copyOfRange(nums, low, mid-1);
	                current.left = recursiveTreeCreate(lowNums);
	            }
	            if(mid < high) {
	                int[] highNums = Arrays.copyOfRange(nums, mid+1, high);
	                current.right = recursiveTreeCreate(highNums);
	            }
	            return current;
	        } else if(nums.length == 1) {
	            return new TreeNode(nums[0]);
	            
	        }

	        
	        return null;
	    }
}
