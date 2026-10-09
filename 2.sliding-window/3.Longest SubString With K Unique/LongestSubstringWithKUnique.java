

public class LongestSubstringWithKUnique {

	public static void main(String[] args) {
		/*
		 * You are given a string s consisting only lowercase alphabets and an integer
		 * k. Your task is to find the length of the longest substring that contains
		 * exactly k distinct characters.
		 * 
		 * Note : If no such substring exists, return -1.
		 * 
		 * Examples:
		 * 
		 * Input: s = "aabacbebebe", k = 3 Output: 7 
		 * Explanation: The longest substring with exactly 3 distinct characters 
		 * is "cbebebe", which includes 'c', 'b', and 'e'. 
		 * Input: s = "aaaa", k = 2 Output: -1 Explanation: There's no substring
		 * with 2 distinct characters. Input: s = "aabaaab", k = 2 Output: 7
		 * Explanation: The entire string "aabaaab" has exactly 2 unique characters 'a'
		 * and 'b', making it the longest valid substring. Constraints:
		 * 
		 * 1 ≤ s.size() ≤ 105 1 ≤ k ≤ 26
		 */

		String s = "aabacbebebe";
		int k = 3;
		int result = usingSlidingWindow(s, k);
		System.out.println(result);

	}

	private static int usingSlidingWindow(String s, int k) {

		int high = 0;
		int low = 0;
		int maxLength=-1;
		Map<String, Integer> map = new HashMap<>();

		String[] stringArr = s.split("");

		while (high < stringArr.length) {

			map.put(stringArr[high], map.getOrDefault(stringArr[high], 0) + 1);
			high++;

			while (map.size() > k) {
				map.put(stringArr[low], map.getOrDefault(stringArr[low], 0) - 1);
				if (map.get(stringArr[low]) == 0) {
					map.remove(stringArr[low]);
				}
				low++;
			}
			
			if (map.size() == k) {
	            maxLength = Math.max(maxLength, high - low);
	        }

		}
		return maxLength;

	}
	
	private static int bruteForce(String s, int k) {
	    int maxLength = -1;

	    for (int i = 0; i < s.length(); i++) {

	        Map<Character, Integer> map = new HashMap<>();

	        for (int j = i; j < s.length(); j++) {

	            char c = s.charAt(j);

	            map.put(c, map.getOrDefault(c, 0) + 1);

	            if (map.size() == k) {
	                maxLength = Math.max(maxLength, j - i + 1);
	            }

	            if (map.size() > k) {
	                break;
	            }
	        }
	    }

	    return maxLength;
	}

}
