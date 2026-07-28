import java.util.Arrays;

public class RemoveDuplicatesFromSortedArray {
    
    public static void main(String[] args) {
        /*
        *Given an integer array nums sorted in non-decreasing order, 
        remove the duplicates in-place such that each unique 
        element appears only once. 
        The relative order of the elements should be kept the same.

        Consider the number of unique elements in nums to be k​​​​​​​​​​​​​​. 
        After removing duplicates, return the number of unique elements 
        k.

        The first k elements of nums should contain the unique numbers
        in sorted order. The remaining elements beyond index k - 1 
        can be ignored.

        Custom Judge:

        The judge will test your solution with the following code:

        int[] nums = [...]; // Input array
        int[] expectedNums = [...]; // The expected answer with 
        // correct length

        int k = removeDuplicates(nums); // Calls your implementation

        assert k == expectedNums.length;
        for (int i = 0; i < k; i++) {
            assert nums[i] == expectedNums[i];
        }
        If all assertions pass, then your solution will be accepted.

        

        Example 1:

        Input: nums = [1,1,2]
        Output: 2, nums = [1,2,_]
        Explanation: Your function should return k = 2, with the 
        first two elements of nums being 1 and 2 respectively.
        It does not matter what you leave beyond the 
        returned k (hence they are underscores).

        Example 2:

        Input: nums = [0,0,1,1,1,2,2,3,3,4]
        Output: 5, nums = [0,1,2,3,4,_,_,_,_,_]
        Explanation: Your function should return k = 5, 
        with the first five elements of nums being 0, 1, 2, 3, and 4 
        respectively.
        It does not matter what you leave beyond the 
        returned k (hence they are underscores).
        

        Constraints:

        1 <= nums.length <= 3 * 104
        -100 <= nums[i] <= 100
        nums is sorted in non-decreasing order.
        
        */

        int[] array={0,0,1,1,1,2,2,3,3,4};
        // int noOfUnique=bruteforceSolution(array);
        // System.out.println("no of uniques elements : "+noOfUnique);

        int noOfUnique=usingTwoPointers(array);
        System.out.println("no of uniques elements usingTwoPointers : "+noOfUnique);
    }

    //using usingTwoPointers
    private static int usingTwoPointers(int[] array){
        int result=1;
        int previous=0;
        int current=1;

        while(current<array.length){
            if(array[current] == array[previous]){
                current++;
                continue;
            }
            array[previous+1]=array[current];
            current++;
            previous++;
            result++;
        }
        System.out.println("result array "+Arrays.toString(array));
        return result;
    }

    
    //brute force with extra space.
    private static int bruteforceSolution(int[] array){
        //int[] array={0,0,1,1,1,2,2,3,3,4};
        int tempCounter=0;
        int[] tempArray = new int[array.length];
        tempArray[tempCounter]=array[0];

        for(int i=1;i<array.length;i++){
            if(array[i] !=tempArray[tempCounter]){
                tempCounter++;
                tempArray[tempCounter]=array[i];
            }
        }

        for(int i=0;i<=tempCounter;i++){
            array[i] = tempArray[i];
        }

        System.out.println("output arrat "+Arrays.toString(array));
        return tempCounter+1;
    }

}
