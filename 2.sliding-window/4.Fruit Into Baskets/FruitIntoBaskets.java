
import java.util.HashMap;
import java.util.Map;

public class FruitIntoBaskets {

	public static void main(String[] args) {
		/*
		 * You are visiting a farm that has a single row of fruit trees arranged from
		 * left to right. The trees are represented by an integer array fruits where
		 * fruits[i] is the type of fruit the ith tree produces.
		 * 
		 * You want to collect as much fruit as possible. However, the owner has some
		 * strict rules that you must follow:
		 * 
		 * You only have two baskets, and each basket can only hold a single type of
		 * fruit. There is no limit on the amount of fruit each basket can hold.
		 * Starting from any tree of your choice, you must pick exactly one fruit from
		 * every tree (including the start tree) while moving to the right. The picked
		 * fruits must fit in one of your baskets. Once you reach a tree with fruit that
		 * cannot fit in your baskets, you must stop. Given the integer array fruits,
		 * return the maximum number of fruits you can pick.
		 * 
		 * Example 1:
		 * 
		 * Input: fruits = [1,2,1] Output: 3 Explanation: We can pick from all 3 trees.
		 * 
		 * Example 2:
		 * 
		 * Input: fruits = [0,1,2,2] Output: 3 Explanation: We can pick from trees
		 * [1,2,2]. If we had started at the first tree, we would only pick from trees
		 * [0,1].
		 * 
		 * Example 3:
		 * 
		 * Input: fruits = [1,2,3,2,2] Output: 4 Explanation: We can pick from trees
		 * [2,3,2,2]. If we had started at the first tree, we would only pick from trees
		 * [1,2].
		 */

//		int[] nums= {1,2,3,2,2};
		int[] nums = { 0, 1, 2, 2 };

		int result = usingSlidingWindow(nums);
		System.out.println("max fruits " + result);

	}

	private static int usingSlidingWindow(int[] nums) {
		int high = 0;
		int low = 0;
		int maxLength = Integer.MIN_VALUE;
		int k = 2;
		Map<Integer, Integer> map = new HashMap<>();

		while (high < nums.length) {
			int highElement = nums[high];

			map.put(highElement, map.getOrDefault(highElement, 0) + 1);
			high++;

			while (map.size() > k) {
				int lowElement = nums[low];
				map.put(lowElement, map.getOrDefault(lowElement, 0) - 1);

				if (map.get(lowElement) == 0) {
					map.remove(lowElement);
				}

				low++;

			}
			int currentLength = high - low;
			maxLength = Math.max(maxLength, currentLength);

		}

		return maxLength;
	}

	private static int usingBruteForce(int[] nums) {

		int maxLength = 0;
		int k = 2;

		for (int i = 0; i < nums.length; i++) {

			Map<Integer, Integer> map = new HashMap<>();

			for (int j = i; j < nums.length; j++) {

				int fruit = nums[j];

				map.put(fruit, map.getOrDefault(fruit, 0) + 1);

				if (map.size() > k) {
					break;
				}

				int currentLength = j - i + 1;
				maxLength = Math.max(maxLength, currentLength);
			}
		}

		return maxLength;
	}

}
