import java.util.Arrays;

public class ThreeSumClosest {    

    public static void main(String[] args) {
/*
    Given an integer array nums of length n and an integer target, 
    find three integers at distinct indices in nums such that the sum is 
    closest to target.

    Return the sum of the three integers.

    You may assume that each input would have exactly one solution.    

    Example 1:
    Input: nums = [-1,2,1,-4], target = 1
    Output: 2
    Explanation: The sum that is closest to the target is 2. (-1 + 2 + 1 = 2).
    
    Example 2:
    Input: nums = [0,0,0], target = 1
    Output: 0
    Explanation: The sum that is closest to the target is 0. (0 + 0 + 0 = 0).
    
    Constraints:
    3 <= nums.length <= 500
    -1000 <= nums[i] <= 1000
    -104 <= target <= 104
*/


        int[] a = {-1,0,1,2,-1,-4};
		
		int target = 4;
		
		int closestSum = usingTwoPointers(a,target);
		System.out.println(closestSum);
    }

    private static int usingTwoPointers(int[] a, int target) {
		Arrays.sort(a);//{-4,-1,-1,0,1,2}
		
		//{-4,-1,-1,0,1,2}
        
        int resultSum = a[0] + a[1] + a[2];
        int minDiff = Integer.MAX_VALUE;

        for(int i=0;i<a.length-2;i++){

            int left = i+1;
            int right = a.length-1;

            while(left<right){


                int sum = a[i] + a[left] + a[right];

                int diffToTarget = Math.abs(target-sum);
                if(diffToTarget < minDiff){
                    resultSum = sum;
                    minDiff=diffToTarget;
                }

                if(sum == target){
                    return sum;
                }else if(sum < target){
                    left++;
                }else {
                    right--;
                }

            }
        }

        return resultSum;
    }


    
    private static int bruteForce(int[] a, int target) {

	    if (a == null || a.length < 3) {
	        throw new IllegalArgumentException("Array must contain at least 3 elements");
	    }

	    int closestSum = a[0] + a[1] + a[2];
	    int minDiff = Math.abs(closestSum - target);

	    for (int i = 0; i < a.length - 2; i++) {

	        for (int j = i + 1; j < a.length - 1; j++) {

	            for (int k = j + 1; k < a.length; k++) {

	                int sum = a[i] + a[j] + a[k];
	                int diff = Math.abs(sum - target);

	                if (diff < minDiff) {
	                    minDiff = diff;
	                    closestSum = sum;
	                }

	                if (sum == target) {
	                    return sum;
	                }
	            }
	        }
	    }

	    return closestSum;
	}
}
