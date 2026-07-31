# 3Sum Closest

## Problem

Given an integer array `nums` of length `n` and an integer `target`, find
three integers at distinct indices such that their sum is as close as
possible to `target`. Return that sum. Assume exactly one solution exists.

**Example 1**
```
Input:  nums = [-1,2,1,-4], target = 1
Output: 2
Explanation: -1 + 2 + 1 = 2, the closest possible sum to 1.
```

**Example 2**
```
Input:  nums = [0,0,0], target = 1
Output: 0
```

**Constraints**
- `3 <= nums.length <= 500`
- `-1000 <= nums[i] <= 1000`
- `-10^4 <= target <= 10^4`

---

## Approaches

### 1. Brute Force — O(n³)

**Logic:** Try every triplet `(i, j, k)`. Track whichever sum has the
smallest absolute difference from `target`. Return immediately if an exact
match is found.

```java
private static int bruteForce(int[] a, int target) {
    if (a == null || a.length < 3) {
        throw new IllegalArgumentException("Array must contain at least 3 elements");
    }
    int closestSum = a[0] + a[1] + a[2];
    int minDiff = Math.abs(closestSum - target);

    for (int i = 0; i < a.length - 2; i++) {
        for (int j = i + 1; j < a.length - 1; j++) {
            for (int k = j + 1; k < a.length; k++) {
                int sum = a[i] + a[j] + a[k];
                int diff = Math.abs(sum - target);
                if (diff < minDiff) {
                    minDiff = diff;
                    closestSum = sum;
                }
                if (sum == target) {
                    return sum;
                }
            }
        }
    }
    return closestSum;
}
```

**Recall trick:** *"Try everything, keep the best diff, stop early on exact
match."* Straightforward and correct, but O(n³) — too slow for larger inputs.

---

### 2. Two Pointers — O(n²)

**Logic:** Sort the array (done in `main()` before calling this). Fix
`a[i]`, then use two pointers on the remaining sorted range. At every step,
check if the current sum improves on the best difference seen so far.
Move `left` right if the sum is too small, `right` left if too big —
exactly the sorted two-pointer pattern, adapted to track "closest" instead
of "exact".

```java
private static int usingTwoPointers(int[] a, int target) {
    Arrays.sort(a);
    int resultSum = a[0] + a[1] + a[2];
    int minDiff = Integer.MAX_VALUE;

    for (int i = 0; i < a.length - 2; i++) {
        int left = i + 1;
        int right = a.length - 1;

        while (left < right) {
            int sum = a[i] + a[left] + a[right];
            int diffToTarget = Math.abs(target - sum);

            if (diffToTarget < minDiff) {
                resultSum = sum;
                minDiff = diffToTarget;
            }

            if (sum == target) {
                return sum;
            } else if (sum < target) {
                left++;
            } else {
                right--;
            }
        }
    }
    return resultSum;
}
```

**Recall trick:** *"Fix one number, two-pointer the rest, always keep the
best diff."* Same skeleton as 3Sum, but instead of collecting exact-zero
matches, every sum along the way is a candidate — just keep whichever is
closest to `target`. The method now sorts internally, so it's self-contained
and safe to call with any unsorted array.

**Trace** for `a = [-1,0,1,2,-1,-4]` → sorted `[-4,-1,-1,0,1,2]`, `target = 4`:

| i | a[i] | left/right walk | best sum so far |
|---|------|-------------------|--------------------|
| 0 | -4 | sums -3,-3,-2,-1 as left advances (all < target) | -1 (diff 5) |
| 1 | -1 | sums 0, 1, 2 as left advances (all < target) | 2 (diff 2) |
| 2 | -1 | sums 1, 2 (no improvement, diff ties at 2) | 2 (diff 2) |
| 3 | 0  | sum 3 (diff 1) → best so far | **3 (diff 1)** |

Result: `3` ✅ — the maximum possible sum (`0+1+2`) is the closest this
array can get to `target = 4`.

---

## Comparison

| Approach      | Time  | Space | Key Idea                                                          |
|---------------|-------|-------|------------------------------------------------------------------------|
| Brute Force   | O(n³) | O(1)  | Check every triplet, track the smallest diff, early-exit on exact match |
| Two Pointers  | O(n²) | O(1)  | Fix one number, two-pointer the rest, always keep the best diff        |

---

## TL;DR (for future me)

- **Best solution:** Two pointers, O(n²) — *"fix one number, two-pointer
  the rest, always keep the best diff instead of only exact matches."*
- **Brute force** is O(n³) — correct but only fine for small inputs.
- **Underlying pattern:** this is 3Sum's skeleton with the success condition
  swapped — instead of collecting all triplets that hit exactly zero, track
  a running "best so far" and only short-circuit on an exact match.
- **Now self-contained:** `Arrays.sort(a)` was moved inside
  `usingTwoPointers`, so the method no longer relies on the caller
  pre-sorting the array in `main()`.
