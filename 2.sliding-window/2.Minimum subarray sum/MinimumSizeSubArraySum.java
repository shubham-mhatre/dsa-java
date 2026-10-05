

public class MinimumSizeSubArraySum {

	public static void main(String[] args) {
		/*
		 * Given an array of positive integers nums and a positive integer target,
		 * return the minimal length of a subarray whose sum is greater than or equal to
		 * target. If there is no such subarray, return 0 instead.
		 * 
		 * Example 1:
		 * 
		 * Input: target = 7, nums = [2,3,1,2,4,3] Output: 2 Explanation: The subarray
		 * [4,3] has the minimal length under the problem constraint.
		 * 
		 * Example 2: Input: target = 4, nums = [1,4,4] Output: 1
		 * 
		 * Example 3: Input: target = 11, nums = [1,1,1,1,1,1,1,1] Output: 0
		 * 
		 * Constraints:
		 * 
		 * 1 <= target <= 109 1 <= nums.length <= 105 1 <= nums[i] <= 104
		 * 
		 */

		int[] nums = { 1, 1, 1, 1, 1, 1, 1 };
		int target = 11;

		int minWindowRes = usingSlidingWindow(nums, target);
		System.out.println("minWindowRes : " + minWindowRes);

	}

	private static int usingSlidingWindow(int[] nums, int target) {
		int minWindow = Integer.MAX_VALUE;
		int sum = 0;
		int low = 0;
		int high = 0;

		while (high < nums.length) {
			sum += nums[high];
			high++;

			while (sum >= target) {

				int currentWindow = high - low;
				minWindow = Math.min(minWindow, currentWindow);

				sum = sum - nums[low];
				low++;

			}
		}

		return minWindow = minWindow == Integer.MAX_VALUE ? 0 : minWindow;
	}
	
	private static int usingBruteForce(int[] nums, int target) {

	    int minWindow = Integer.MAX_VALUE;

	    for (int low = 0; low < nums.length; low++) {

	        int sum = 0;

	        for (int high = low; high < nums.length; high++) {

	            sum += nums[high];

	            if (sum >= target) {
	                int currentWindow = high - low + 1;
	                minWindow = Math.min(minWindow, currentWindow);
	            }
	        }
	    }

	    return minWindow == Integer.MAX_VALUE ? 0 : minWindow;
	}

}