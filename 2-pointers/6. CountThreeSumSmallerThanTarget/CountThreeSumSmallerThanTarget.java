import java.util.Arrays;

public class CountThreeSumSmallerThanTarget {

    /*
    Given an array arr[] of distinct integers and a value sum, 
    find the count of triplets (i, j, k), having (i<j<k) with the 
    sum of (arr[i] + arr[j] + arr[k]) smaller than the given value sum.

    Examples :
    Input: sum = 2, arr[] = [-2, 0, 1, 3]
    Output:  2
    Explanation: Triplets with sum less than 2 are (-2, 0, 1) and (-2, 0, 3). 
    
    Input: sum = 12, arr[] = [5, 1, 3, 4, 7]
    Output: 4
    Explanation: Triplets with sum less than 12 are (1, 3, 4), 
    (5, 1, 3), (1, 3, 7) and (5, 1, 4).
    
    Constraints:
    1 ≤ sum ≤ 105
    3 ≤ arr.size() ≤ 103
    -103 ≤ arr[i] ≤ 103
    
    */

    
    public static void main(String[] args) {
        
        int a[] = {5,1,3,4,7};
        int targetSum=12;

        int brute = bruteForce(a, targetSum);
        System.out.println("Brute Force : " + brute);

        int count=usingTwoPointers(a,targetSum);
        System.out.println(count);
    }

    private static int usingTwoPointers(int a[],int targetSum){

        Arrays.sort(a);
        int count=0;

        for(int i=0;i<a.length-2;i++){

            int left = i+1;
            int right = a.length-1;

            while(left<right){
                int sum = a[i] + a[left] + a[right];

                if(sum < targetSum){
                    count = count + (right - left);
                    left++;
                }else {
                    right--;
                }
            }

        }

        return count;
    }


    private static int bruteForce(int[] a, int targetSum) {

        int count = 0;

        for (int i = 0; i < a.length - 2; i++) {

            for (int j = i + 1; j < a.length - 1; j++) {

                for (int k = j + 1; k < a.length; k++) {

                    if (a[i] + a[j] + a[k] < targetSum) {
                        count++;
                    }

                }
            }

        }

        return count;
    }
}
