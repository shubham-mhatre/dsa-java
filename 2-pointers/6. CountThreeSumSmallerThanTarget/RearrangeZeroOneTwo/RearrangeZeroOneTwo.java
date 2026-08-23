
import java.util.Arrays;

public class RearrangeZeroOneTwo {

    public static void main(String[] args) {
        /**
	Given an array arr[] consisting of only 0's, 1's and 2's. 
	Modify the array in-place to segregate 0s onto the left side and 2s onto 
	the right side of the array & 1s in middle.
	
	Examples :
	
	Input: arr[] = [1,0,1,2,0,1,2,0,0,1,2]
	Output: [0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2]
	Explanation:  After segregation, all the 0's are on the left and 2's are on the right
	& 1's stays in middle. 
	Modified array will be [0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2]
	
	Input: arr[] = [1, 1]
	Output: [1, 1]
	Explanation: There are no 0s in the given array, so the modified array is [1, 1]
	
	Constraints:
	1 ≤ arr.size() ≤ 105
	0 ≤ arr[i] ≤ 1

*/
		int a[]= {1,0,1,2,0,1,2,0,0,1,2};
		
		int result[] = usingDutchNationalFlag(a);
		System.out.println(Arrays.toString(result));
    }

    public static int[] usingDutchNationalFlag(int[] a){
        //int a[]= {1,0,1,2,0,1,2,0,0,1,2};

        int start=0;
        int middle=0;
        int last=a.length-1;

        while(middle<=last){

            if(a[middle] == 2){
                swap(a,middle,last);
                last--;
            }else if(a[middle] == 0){
                swap(a,middle,start);
                middle++;
                start++;
            }else{
                //a[middle] == 1
                middle++;
            }

        }


        return a;
    }

    private static void swap(int[] a, int middle, int last) {
        int temp = a[middle];
        a[middle] = a[last];
        a[last] = temp;
    }


    //brute force approach
    public static int[] usingBruteForce(int[] a) {

        int count0 = 0;
        int count1 = 0;
        int count2 = 0;

        // Count 0s, 1s and 2s
        for (int num : a) {
            if (num == 0) {
                count0++;
            } else if (num == 1) {
                count1++;
            } else {
                count2++;
            }
        }

        // Put 0s
        int index = 0;

        while (count0 > 0) {
            a[index++] = 0;
            count0--;
        }

        // Put 1s
        while (count1 > 0) {
            a[index++] = 1;
            count1--;
        }

        // Put 2s
        while (count2 > 0) {
            a[index++] = 2;
            count2--;
        }

        return a;
    }
}
