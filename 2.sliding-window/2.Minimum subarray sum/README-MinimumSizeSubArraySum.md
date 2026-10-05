# Minimum Size Subarray Sum

## Problem

Given an array of positive integers `nums` and a positive integer `target`,
return the minimal length of a contiguous subarray whose sum is
`>= target`. If no such subarray exists, return `0`.

**Example 1**
```
Input:  target = 7, nums = [2,3,1,2,4,3]
Output: 2
Explanation: The subarray [4,3] has the minimal length.
```

**Example 2**
```
Input:  target = 4, nums = [1,4,4]
Output: 1
```

**Example 3**
```
Input:  target = 11, nums = [1,1,1,1,1,1,1,1]
Output: 0
Explanation: No subarray sums to at least 11.
```

**Constraints**
- `1 <= target <= 10^9`
- `1 <= nums.length <= 10^5`
- `1 <= nums[i] <= 10^4`

---

## Approaches

### 1. Brute Force — O(n²)

**Logic:** For every starting index `low`, grow the window forward,
tracking the running sum. The moment the sum hits `target`, record the
window length as a candidate for the minimum.

```java
private static int usingBruteForce(int[] nums, int target) {
    int minWindow = Integer.MAX_VALUE;

    for (int low = 0; low < nums.length; low++) {
        int sum = 0;

        for (int high = low; high < nums.length; high++) {
            sum += nums[high];

            if (sum >= target) {
                int currentWindow = high - low + 1;
                minWindow = Math.min(minWindow, currentWindow);
            }
        }
    }

    return minWindow == Integer.MAX_VALUE ? 0 : minWindow;
}
```

**Recall trick:** *"Try every starting point, grow until target is hit."*
Correct, but re-walks overlapping ranges for every `low` — O(n²) overall.

---

### 2. Sliding Window (Variable Size) — O(n)

**Logic:** Grow the window by moving `high` forward, adding to `sum`.
Whenever `sum >= target`, the window is valid — record its length, then
**shrink from the left** (subtract `nums[low]`, advance `low`) as long as
it's still valid, since a smaller window is always better once the
condition is already met. This is a variable-size sliding window: it
expands and contracts based on a condition rather than sliding by a fixed
step.

```java
private static int usingSlidingWindow(int[] nums, int target) {
    int minWindow = Integer.MAX_VALUE;
    int sum = 0;
    int low = 0;
    int high = 0;

    while (high < nums.length) {
        sum += nums[high];
        high++;

        while (sum >= target) {
            int currentWindow = high - low;
            minWindow = Math.min(minWindow, currentWindow);

            sum = sum - nums[low];
            low++;
        }
    }

    return minWindow == Integer.MAX_VALUE ? 0 : minWindow;
}
```

**Recall trick:** *"Expand right until valid, then shrink left while still
valid."* Each element is added once (by `high`) and removed once (by
`low`) across the entire run — that's what keeps it O(n) despite the
nested loops.

**Trace** for `nums = [2,3,1,2,4,3]`, `target = 7`:

| high (after ++) | sum | low | shrink? | window recorded | minWindow |
|------------------|-----|-----|---------|----------------------|-----------|
| 1 | 2 | 0 | no (2<7) | — | MAX |
| 2 | 5 | 0 | no (5<7) | — | MAX |
| 3 | 6 | 0 | no (6<7) | — | MAX |
| 4 | 8 | 0→1 | yes (8≥7) | len 4 | 4 |
| 5 | 10 | 1→3 | yes (10≥7→6≥7) | len 4, then len 3 | 3 |
| 6 | 9 | 3→5 | yes (9≥7→6<7 stop at len 2) | len 3, then len 2 | **2** |

Result: `2` ✅ — matches the subarray `[4,3]`.

---

## Comparison

| Approach              | Time  | Space | Key Idea                                                        |
|------------------------|-------|-------|------------------------------------------------------------------|
| Brute Force             | O(n²) | O(1)  | Try every starting point, grow until target is hit               |
| Sliding Window (variable)| O(n)  | O(1)  | Expand right until valid, shrink left while still valid          |

---

## TL;DR (for future me)

- **Best solution:** Variable-size sliding window, O(n) — *"expand right
  until the sum is big enough, then shrink left as long as it stays big
  enough, recording the smallest window each time."*
- **Brute force** is O(n²) — correct but re-walks overlapping ranges for
  every starting index.
- **Underlying pattern:** this is the *variable-size* sliding window
  template, different from the *fixed-size* one (like max-sum-subarray-of-
  size-k). Here the window grows and shrinks based on a condition
  (`sum >= target`) rather than always staying at a fixed width `k`. Each
  pointer (`low`, `high`) only ever moves forward, visiting each index at
  most once — that's the O(n) guarantee despite the nested `while` loops.
