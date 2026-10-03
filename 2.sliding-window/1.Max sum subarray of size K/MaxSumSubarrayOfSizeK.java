
class MaxSumSubarrayOfSizeK{

    public static void main(String[] args) {
        /**
			Given an array of integers arr[]  and a number k. Return the maximum sum of a subarray of size k.
	
			Note: A subarray is a contiguous part of any given array.
			
			Examples:
			
			Input: arr[] = [100, 200, 300, 400], k = 2
			Output: 700
			Explanation: arr2 + arr3 = 700, which is maximum.
			Input: arr[] = [1, 4, 2, 10, 23, 3, 1, 0, 20], k = 4
			Output: 39
			Explanation: arr1 + arr2 + arr3 + arr4 = 39, which is maximum.
			Input: arr[] = [100, 200, 300, 400], k = 1
			Output: 400
			Explanation: arr3 = 400, which is maximum.
			Constraints:
			1 ≤ arr.size() ≤ 106
			0 ≤ arr[i] ≤ 106
			1 ≤ k ≤ arr.size() 
		 */

        int[] arr = {1, 4, 2, 10, 23, 3, 1, 0, 20};
		int k = 4;

		int maxSum = usingSlidingWindow(arr,k);
		System.out.println("result sum "+maxSum);
    }

    public static int usingSlidingWindow(int[] arr,int k){

        int resultSum=0;
        int currentSum=0;
        int low=0;
        int high=k-1;

        //first window calculation
        for(int i=0;i<k;i++){
            currentSum+=arr[i];
        }

        while(high<arr.length){
            resultSum = Math.max(resultSum, currentSum);
            low++;
            high++;

            if(high==arr.length){
                break;
            }
            currentSum = currentSum - arr[low-1] + arr[high];
        }

        return resultSum;
    }

    public static int bruteForce(int[] arr, int k) {
        int maxSum = 0;

        for (int i = 0; i <= arr.length - k; i++) {
            int currentSum = 0;

            for (int j = i; j < i + k; j++) {
                currentSum += arr[j];
            }

            maxSum = Math.max(maxSum, currentSum);
        }

        return maxSum;
    }

}