import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TwoSum {

    public static void main(String args[]) {
        /**
         * Given an array of integers nums and an integer target, 
         * return indices of the  two numbers such that they add up to target.
         * 
         * You may assume that each input would have exactly one solution, 
         * and you may not use the same element twice.
         * 
         * You can return the answer in any order.
         * Example 1:
         * 
         * Input: nums = [2,7,11,15], target = 9
         * Output: [0,1]
         * Explanation: Because nums[0] + nums[1] == 9, we return [0, 1].
         * 
         * Example 2:         * 
         * Input: nums = [3,2,4], target = 6
         * Output: [1,2]
         * 
         * Example 3:
         * 
         * Input: nums = [3,3], target = 6
         * Output: [0,1]
         * Constraints:
         * 
         * 2 <= nums.length <= 104
         * -109 <= nums[i] <= 109
         * -109 <= target <= 109
         * Only one valid answer exists.
         * 
         */

        int[] array = {11,7,2,15};
        int target = 9;
        
        int[] output=bruteforceSolution(array,target);
        //System.out.println("output from bruteforce solution "+Arrays.toString(output));

        int[] output1=usingHashMap(array,target);
        // System.out.println("output from hashmap solution "
        //     +Arrays.toString(output1));

        int[] output2=usingTwoPointers(array,target);
        System.out.println("output from usingTwoPointers solution "
            +Arrays.toString(output2));
    }

    private static int[] usingTwoPointers(int[] array, int target){

        //use two pointers when actual values are asked.
        //as for two pointers we need sorted array
        //desite question being asked for returning index.
        //to display working of two pointers
        //we have return actual values instead of index
        //as we sort the array, actual index will differ.

        Arrays.sort(array);

        int left = 0;
        int right = array.length-1;

        while(left<right){
            int sum = array[left] + array[right];
            if(sum == target){
                return new int[]{array[left] ,array[right]};
            }else if(sum < target){
                left++;
            }else if(sum > target){
                right--;
            }
        }


        return new int[]{-1,-1};
    }

    //o(n)
    private static int[] usingHashMap(int[] array,int target){

        Map<Integer,Integer> hashMap=new HashMap<>();
        for(int i=0;i<array.length;i++){
            //target = a + b
            //target - a = b
            int complement = target - array[i];
            if(hashMap.get(complement) !=null){
                return new int[] {hashMap.get(complement), i};
            }else{
                hashMap.put(array[i],i);
            }
        }
      

        return new int[] {-1,-1};
    }

    //o(n^2)
    private static int[] bruteforceSolution(int[] array,int target){

        for(int i=0;i < array.length;i++){
            for(int j=1;j<array.length;j++){
                int sum=array[i] + array[j];
                if(sum == target){
                    return new int[]{i ,j};
                }
            }
        }
        return new int[]{-1 ,-1};
    }

}
