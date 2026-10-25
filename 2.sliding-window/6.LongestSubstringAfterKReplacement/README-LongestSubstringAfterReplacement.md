# Longest Repeating Character Replacement

## Problem

Given a string `s` of uppercase letters and an integer `k`, you may change
**any character to any other uppercase letter** at most `k` times. Return
the length of the longest substring containing the same letter you can get
after those operations.

**Example 1**
```
Input:  s = "ABAB", k = 2
Output: 4
Explanation: Replace the two 'A's with 'B's (or vice versa).
```

**Example 2**
```
Input:  s = "AABABBA", k = 1
Output: 4
Explanation: Replace the one 'A' in the middle to get "AABBBBA";
the substring "BBBB" has length 4.
```

**Constraints**
- `1 <= s.length <= 10^5`
- `s` consists of only uppercase English letters
- `0 <= k <= s.length`

---

## The key formula

For any window, the cheapest way to make it all one letter is to keep the
**most frequent** letter and replace everything else:

```
replacements needed = window length − count of the most frequent letter
window is fixable   ⇔ replacements needed ≤ k
```

So the problem becomes: *find the longest window where
`length − maxFreq ≤ k`.*

---

## Approaches

### 1. Brute Force — O(n²)

**Logic:** For every start `i`, extend `j` to the right while keeping a
26-slot frequency array and a running `maxCount`. Whenever
`length − maxCount ≤ k`, the substring is fixable — record its length.

```java
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
```

**Recall trick:** *"Every start, extend right, track the most frequent
letter, accept the window if `length − maxCount ≤ k`."* Correct, but
restarts the frequency array for every `i`.

---

### 2. Sliding Window (Variable Size) — O(n)

**Logic:** Expand `high` one character at a time, updating `freq` and
`maxCount`. If the window now needs **more than `k`** replacements
(`diff = length − maxCount > k`), drop `s[low]` and advance `low`. Record
the window length after every step.

```java
private static int usingSlidingWindow(String s, int k) {
    int[] freq = new int[26];
    int low = 0, maxCount = 0, res = 0;

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
```

**Recall trick:** *"Add the new letter; if `length − maxCount > k`, drop
the left letter; record the length."*

**Why `maxCount` is never lowered (the subtle part):** after shrinking, the
code does **not** recompute `maxCount`, so it can be *stale* (larger than
the true max inside the window). That's safe:
- The window size **never decreases** — each step either grows it by one or
  slides it (add one, drop one). So the inner `while` runs at most once.
- The answer can only grow when `maxCount` **genuinely grows** to a new
  record. A stale `maxCount` can keep a window sliding at its current
  size, but it can never push `res` above a value that was already valid.

**Trace** for `s = "AABABBA"`, `k = 1`:

| high | char | freq (after step) | maxCount | len | len − max | action | low | res |
|------|------|--------------------|----------|-----|-----------|--------|-----|-----|
| 0 | A | {A:1} | 1 | 1 | 0 ≤ 1 ✓ | expand | 0 | 1 |
| 1 | A | {A:2} | 2 | 2 | 0 ≤ 1 ✓ | expand | 0 | 2 |
| 2 | B | {A:2, B:1} | 2 | 3 | 1 ≤ 1 ✓ | expand | 0 | 3 |
| 3 | A | {A:3, B:1} | 3 | 4 | 1 ≤ 1 ✓ | expand | 0 | **4** |
| 4 | B | {A:2, B:2} | 3 | 5 → 4 | 2 > 1 ✗ → shrink | drop s[0]='A' | 1 | 4 |
| 5 | B | {A:1, B:3} | 3 | 5 → 4 | 2 > 1 ✗ → shrink | drop s[1]='A' | 2 | 4 |
| 6 | A | {A:2, B:2} | 3 | 5 → 4 | 2 > 1 ✗ → shrink | drop s[2]='B' | 3 | 4 |

Result: `4` ✅ — e.g. `"BABB"` (indices 2–5): replace the one `A` → `"BBBB"`.

---

## Comparison

| Approach        | Time  | Space | Key Idea                                                                  |
|------------------|-------|-------|------------------------------------------------------------------------------|
| Brute Force       | O(n²) | O(1)  | Every start, extend right, accept if `length − maxCount ≤ k`                 |
| Sliding Window    | O(n)  | O(1)  | Window stays fixable; drop the left letter when `length − maxCount > k`      |

Space is O(1) because the frequency array has a fixed 26 slots.

---

## TL;DR (for future me)

- **Best solution:** Variable-size sliding window, O(n) — *"keep the window
  fixable: `length − maxCount ≤ k`; if not, drop the left letter."*
- **Core formula:** replacements needed = window length − count of the most
  frequent letter.
- **The trick to remember:** `maxCount` is never lowered after shrinking.
  It's safe because the window never gets smaller and the answer only
  grows when `maxCount` truly grows.
- **Brute force** is O(n²) — correct, but restarts the frequency array for
  every start.
- **Underlying pattern:** "longest window that needs at most `k` fixes."
  Same family as *Max Consecutive Ones III* (flip at most `k` zeros) — that
  one is this problem with just two symbols.
