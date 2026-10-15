# Longest Substring Without Repeating Characters

## Problem

Given a string `s`, return the length of the longest **substring** that
contains no duplicate characters.

**Example 1**
```
Input:  s = "abcabcbb"
Output: 3
Explanation: "abc" (also "bca" and "cab") has length 3.
```

**Example 2**
```
Input:  s = "bbbbb"
Output: 1
Explanation: "b" has length 1.
```

**Example 3**
```
Input:  s = "pwwkew"
Output: 3
Explanation: "wke" has length 3. Note "pwke" is a subsequence, not a substring.
```

**Constraints**
- `0 <= s.length <= 10^5`
- `s` consists of English letters, digits, symbols and spaces.

---

## Approaches

### 1. Brute Force — O(n³)

**Logic:** Check every substring `s[i..j]`. For each one, build a fresh
set by scanning `k` from `i` to `j`; if any character is already in the
set, the substring has a duplicate. Track the longest unique one.

```java
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
```

**Recall trick:** *"Pick every start, pick every end, re-scan the middle
for duplicates."* Three nested loops → O(n³). Correct, but it re-checks
the same characters over and over.

---

### 2. Sliding Window (Variable Size) — O(n)

**Logic:** Keep a window `[low, high)` that **never contains a duplicate**,
plus a `Set` of the characters currently inside it. For each new character
`s[high]`:
- While it's **already in the set** (a duplicate), remove `s[low]` from the
  set and advance `low` — shrink from the left until the duplicate is gone.
- Then add `s[high]`, advance `high`, and record `high - low`.

```java
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
```

**Recall trick:** *"Duplicate arrives? Shrink from the left until it's
gone, then add it."* It's a `while`, not an `if`: you may need to evict
several characters before the duplicate itself leaves the window.

**Why a `Set` is enough (no `Map`):** the window is *always* duplicate-free,
so each character appears at most once — presence is all we need, no
counts. (Contrast with the K-distinct problems, where characters repeat
inside the window and a `Map` of counts is required.)

**Trace** for `s = "pwwkew"`:

| high (after ++) | char | action | set | low | window length | maxLength |
|------------------|------|--------|-----|-----|----------------|-----------|
| 1 | p | add | {p} | 0 | 1 | 1 |
| 2 | w | add | {p, w} | 0 | 2 | 2 |
| 3 | w | duplicate → remove p, remove w (low 0 → 2), then add w | {w} | 2 | 1 | 2 |
| 4 | k | add | {w, k} | 2 | 2 | 2 |
| 5 | e | add | {w, k, e} | 2 | 3 | **3** |
| 6 | w | duplicate → remove w (low 2 → 3), then add w | {k, e, w} | 3 | 3 | 3 |

Result: `3` ✅ — the window `"wke"` (and `"kew"` ties it).

---

## Comparison

| Approach        | Time  | Space  | Key Idea                                                         |
|------------------|-------|--------|---------------------------------------------------------------------|
| Brute Force       | O(n³) | O(min(n, m)) | Check every substring, re-scan it with a fresh set            |
| Sliding Window    | O(n)  | O(min(n, m)) | Duplicate-free window; shrink from the left on each duplicate |

`m` is the size of the character set. Each character is added to the set
once and removed at most once, which is why the sliding window is O(n)
despite the nested `while`.

---

## TL;DR (for future me)

- **Best solution:** Variable-size sliding window + `Set`, O(n) —
  *"duplicate arrives? shrink from the left until it's gone, then add it
  and record the length."*
- **Brute force** is O(n³) — every substring, each re-scanned from scratch.
- **Must remember:** the shrink step is a `while` loop, not an `if`.
- **Underlying pattern:** the "longest window satisfying a condition"
  template. A `Set` is enough when the window must have **no repeats**; a
  `Map` of counts is needed when repeats are allowed (K-distinct,
  Fruit Into Baskets).
