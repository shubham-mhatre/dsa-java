
import java.util.HashSet;
import java.util.Set;

public class LongestSubStringWithoutRepeat {

	public static void main(String[] args) {
		/*
		 * Given a string s, find the length of the longest substring without duplicate
		 * characters.
		 * 
		 * Example 1:
		 * 
		 * Input: s = "abcabcbb" Output: 3 Explanation: The answer is "abc", with the
		 * length of 3. Note that "bca" and "cab" are also correct answers.
		 * 
		 * Example 2:
		 * 
		 * Input: s = "bbbbb" Output: 1 Explanation: The answer is "b", with the length
		 * of 1.
		 * 
		 * Example 3:
		 * 
		 * Input: s = "pwwkew" Output: 3 Explanation: The answer is "wke", with the
		 * length of 3. Notice that the answer must be a substring, "pwke" is a
		 * subsequence and not a substring.
		 * 
		 * Constraints:
		 * 
		 * 0 <= s.length <= 105 s consists of English letters, digits, symbols and
		 * spaces.
		 */

		String s = "pwwkew";

		Integer output = usingSlidingWindow(s);
		System.out.println(output);

	}

	private static Integer usingSlidingWindow(String s) {
		HashSet<Character> set = new HashSet<>();
		int high = 0;
		int low = 0;
		int maxLength = 0;

		while (high < s.length()) {

			while (set.contains(s.charAt(high))) {
				set.remove(s.charAt(low));
				low++;
			}

			set.add(s.charAt(high));
			high++;
			int currentLength = high - low;
			maxLength = Math.max(maxLength, currentLength);
		}

		return maxLength;
	}

	private static int usingBruteForce(String s) {
		int maxLength = 0;

		for (int i = 0; i < s.length(); i++) {
			for (int j = i; j < s.length(); j++) {

				Set<Character> set = new HashSet<>();
				boolean isUnique = true;

				for (int k = i; k <= j; k++) {
					char ch = s.charAt(k);

					if (set.contains(ch)) {
						isUnique = false;
						break;
					}

					set.add(ch);
				}

				if (isUnique) {
					maxLength = Math.max(maxLength, j - i + 1);
				}
			}
		}

		return maxLength;
	}

}
