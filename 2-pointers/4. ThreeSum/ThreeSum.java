import java.util.List;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
public class ThreeSum {
    
    public static void main(String[] args) {
        
        /*
        Given an integer array nums, return all the triplets [nums[i], nums[j], nums[k]]
        such that i != j, i != k, and j != k, and nums[i] + nums[j] + nums[k] == 0.

        Notice that the solution set must not contain duplicate triplets.        

        Example 1:

        Input: nums = [-1,0,1,2,-1,-4]
        Output: [[-1,-1,2],[-1,0,1]]
        Explanation: 
        nums[0] + nums[1] + nums[2] = (-1) + 0 + 1 = 0.
        nums[1] + nums[2] + nums[4] = 0 + 1 + (-1) = 0.
        nums[0] + nums[3] + nums[4] = (-1) + 2 + (-1) = 0.
        The distinct triplets are [-1,0,1] and [-1,-1,2].
        Notice that the order of the output and the order of the triplets 
        does not matter.
        
        Example 2:
        Input: nums = [0,1,1]
        Output: []
        Explanation: The only possible triplet does not sum up to 0.
        
        Example 3:

        Input: nums = [0,0,0]
        Output: [[0,0,0]]
        Explanation: The only possible triplet sums up to 0.
        

        Constraints:

        3 <= nums.length <= 3000
        -105 <= nums[i] <= 105
        */

        int[] nums = {-1,0,1,2,-1,-4};

        // List<List<Integer>> output =bruteforceSolution(nums);
        // System.out.println("output : "+output);

        List<List<Integer>> output =usingTwoPointerSolution(nums);
        System.out.println("output : "+output);


    }

    private static List<List<Integer>> usingTwoPointerSolution(int[] nums){
        //int[] nums = {-1,0,1,2,-1,-4};
        List<List<Integer>> output = new ArrayList<>();

        Arrays.sort(nums);


        for(int i=0;i<nums.length - 2;i++){
            int left=i+1;
            int right=nums.length-1;
            if(i > 0 && nums[i] == nums[i-1]){
                continue;
            }

            while (left < right) {

                int sum = nums[i] + nums[left] + nums[right];
                if(sum == 0){

                    output.add(Arrays.asList(nums[i] , nums[left] , nums[right]));
                    left++;
                    right--;
                    
                    while(left<nums.length && nums[left] == nums[left-1]){
                        left++;
                    }

                    while(right>=0 && nums[right] == nums[right+1]){
                        right--;
                    }
                }else if(sum < 0){
                    left++;
                }else{
                    right--;
                }
            }

        }

        return output;
    }

    private static List<List<Integer>> bruteforceSolution(int[] nums){

        List<List<Integer>> output = new ArrayList<>();

        Arrays.sort(nums);

        for(int i=0;i<nums.length - 2;i++){
            if(i>0 && nums[i] == nums[i-1]){
                continue;
            }
            for(int j=i+1;j<nums.length - 1;j++){
                if(j > i+1 && nums[j] == nums[j-1]){
                    continue;
                }
                for(int k=j+1;k<nums.length;k++){
                    if(k>j+1 && nums[k] == nums[k-1]){
                        continue;
                    }
                    int sum=nums[i] + nums[j] + nums[k];
                    if(sum == 0){
                        output.add(Arrays.asList(nums[i],nums[j],nums[k]));
                    }
                }
            }
        }
        return output;
    }
}
