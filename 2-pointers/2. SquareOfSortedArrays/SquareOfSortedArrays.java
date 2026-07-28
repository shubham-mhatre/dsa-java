import java.util.Arrays;

public class SquareOfSortedArrays {
    public static void main(String[] args) {
        /*
        Given an integer array nums sorted in non-decreasing order, 
        return an array of the squares of each number sorted in non-decreasing order. 

        Example 1:

        Input: nums = [-4,-1,0,3,10]
        Output: [0,1,9,16,100]
        Explanation: After squaring, the array becomes [16,1,0,9,100].
        After sorting, it becomes [0,1,9,16,100].
        Example 2:

        Input: nums = [-7,-3,2,3,11]
        Output: [4,9,9,49,121]        

        Constraints:

        1 <= nums.length <= 104
        -104 <= nums[i] <= 104
        nums is sorted in non-decreasing order.
        */

        int[] nums = {-4,-1,0,3,10};
        //int[] res = bruteForceSolution(nums);
        //System.out.println("result array using bruteforce solution "+Arrays.toString(res));
    
        int[] res1 = usingTwoPointers(nums);
        System.out.println("result array using two pointers solution "+Arrays.toString(res1));
    
    }

    private static int[] usingTwoPointers(int[] nums){
        //int[] nums = {-4,-1,0,3,10};
        ////int[] sqArray = {16,1,0,9,100};

        int[] squarArray=Arrays.stream(nums).map(s->s*s).toArray();
        int[] res = new int[nums.length];
        int left=0;
        int right=nums.length-1;

        for(int i=squarArray.length-1;i>=0;i--){
            if(squarArray[left] >= squarArray[right]){
                res[i]=squarArray[left];
                left++;
            }else{
                res[i]=squarArray[right];
                right--;
            }
        }

        return res;
    }

    private static int[] bruteForceSolution(int[] nums){
        //sqaure
        int[] sqArray = Arrays.stream(nums).map(s->s*s).toArray();
        //sort
        Arrays.sort(sqArray);
        return sqArray;
    }
}
