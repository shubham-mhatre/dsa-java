
public class LongestSubStringAfterReplacement {

	public static void main(String[] args) {

		/*
		 * You are given a string s and an integer k. You can choose any character of
		 * the string and change it to any other uppercase English character. You can
		 * perform this operation at most k times.
		 * 
		 * Return the length of the longest substring containing the same letter you can
		 * get after performing the above operations.
		 * 
		 * Example 1:
		 * 
		 * Input: s = "ABAB", k = 2 Output: 4 Explanation: Replace the two 'A's with two
		 * 'B's or vice versa. Example 2:
		 * 
		 * Input: s = "AABABBA", k = 1 Output: 4 Explanation: Replace the one 'A' in the
		 * middle with 'B' and form "AABBBBA". The substring "BBBB" has the longest
		 * repeating letters, which is 4. There may exists other ways to achieve this
		 * answer too.
		 * 
		 * 
		 * Constraints:
		 * 
		 * 1 <= s.length <= 105 s consists of only uppercase English letters. 0 <= k <=
		 * s.length
		 */

		String s = "AABABBA";
		int k = 1;
		int res = usingSlidingWindow(s, k);
		System.out.println(res);

	}

	private static int usingSlidingWindow(String s, int k) {
		int[] freq = new int[26];
		int low = 0, maxCount = 0, res = 0;
		// String s="AABABBA";
		for (int high = 0; high < s.length(); high++) {
			Character ch = s.charAt(high);
			freq[ch - 'A']++;
			maxCount = Math.max(maxCount, freq[ch - 'A']);
			int length = high - low + 1;
			int diff = length - maxCount;

			while (diff > k) {
				Character lowch = s.charAt(low);
				freq[lowch - 'A']--;
				low++;
				length = high - low + 1;
				diff = length - maxCount;
			}

			res = Math.max(res, length);

		}
		return res;
	}

	private static int bruteforce(String s, int k) {
		int n = s.length();
		int res = 0;

		for (int i = 0; i < n; i++) {
			int[] freq = new int[26];
			int maxCount = 0;

			for (int j = i; j < n; j++) {
				char ch = s.charAt(j);
				freq[ch - 'A']++;

				maxCount = Math.max(maxCount, freq[ch - 'A']);

				int length = j - i + 1;
				int replacements = length - maxCount;

				if (replacements <= k) {
					res = Math.max(res, length);
				}
			}
		}

		return res;
	}

}