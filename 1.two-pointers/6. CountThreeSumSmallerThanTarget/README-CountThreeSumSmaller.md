# Count Triplets with Sum Smaller Than Target

## Problem

Given an array `arr[]` of distinct integers and a value `sum`, count the
number of triplets `(i, j, k)` with `i < j < k` such that
`arr[i] + arr[j] + arr[k] < sum`.

**Example 1**
```
Input:  sum = 2, arr = [-2, 0, 1, 3]
Output: 2
Explanation: (-2, 0, 1) and (-2, 0, 3) both sum below 2.
```

**Example 2**
```
Input:  sum = 12, arr = [5, 1, 3, 4, 7]
Output: 4
Explanation: (1,3,4), (5,1,3), (1,3,7), (5,1,4) all sum below 12.
```

**Constraints**
- `1 <= sum <= 10^5`
- `3 <= arr.size() <= 10^3`
- `-10^3 <= arr[i] <= 10^3`

---

## Approaches

### 1. Brute Force — O(n³)

**Logic:** Check every triplet directly and count the ones whose sum is
below `targetSum`.

```java
private static int bruteForce(int[] a, int targetSum) {
    int count = 0;

    for (int i = 0; i < a.length - 2; i++) {
        for (int j = i + 1; j < a.length - 1; j++) {
            for (int k = j + 1; k < a.length; k++) {
                if (a[i] + a[j] + a[k] < targetSum) {
                    count++;
                }
            }
        }
    }
    return count;
}
```

**Recall trick:** *"Check every triplet, count the ones under target."*
Correct and simple, but O(n³) — too slow once `arr.length` grows.

---

### 2. Two Pointers — O(n²)

**Logic:** Sort the array. Fix `a[i]`, then use two pointers on the
remaining sorted range. The key trick: if `a[i] + a[left] + a[right] <
targetSum`, then — because the array is sorted — every value strictly
between `left` and `right` paired with the current `left` will also sum
below target (since it's `<= a[right]`). That's exactly `right - left`
valid triplets, counted in one shot. Then advance `left` since all
triplets using this `left` (with any smaller right) are now accounted for.
If the sum is too big, shrink from the right instead.

```java
private static int usingTwoPointers(int a[], int targetSum) {
    Arrays.sort(a);
    int count = 0;

    for (int i = 0; i < a.length - 2; i++) {
        int left = i + 1;
        int right = a.length - 1;

        while (left < right) {
            int sum = a[i] + a[left] + a[right];

            if (sum < targetSum) {
                count = count + (right - left);
                left++;
            } else {
                right--;
            }
        }
    }
    return count;
}
```

**Recall trick:** *"Sum too small → every element between left and right
also works with this left → count them all at once, then move left."*
This "count in bulk" idea is what turns an O(n) inner pair-search into an
O(n) counting pass without checking every pair individually.

**Trace** for `a = [5,1,3,4,7]` → sorted `[1,3,4,5,7]`, `targetSum = 12`:

| i | a[i] | left | right | sum | < target? | action | count after |
|---|------|------|-------|-----|-----------|--------|-------------|
| 0 | 1 | 1 | 4 | 1+3+7=11 | yes | count += (4-1)=3 → left++ | 3 |
| 0 | 1 | 2 | 4 | 1+4+7=12 | no  | right-- | 3 |
| 0 | 1 | 2 | 3 | 1+4+5=10 | yes | count += (3-2)=1 → left++ | 4 |
| 1 | 3 | 2 | 4 | 3+4+7=14 | no  | right-- | 4 |
| 1 | 3 | 2 | 3 | 3+4+5=12 | no  | right-- → left<right false | 4 |
| 2 | 4 | 3 | 4 | 4+5+7=16 | no  | right-- → left<right false | 4 |

Result: `4` ✅

---

## Comparison

| Approach      | Time  | Space | Key Idea                                                              |
|---------------|-------|-------|----------------------------------------------------------------------------|
| Brute Force   | O(n³) | O(1)  | Check every triplet directly                                              |
| Two Pointers  | O(n²) | O(1)  | Sorted array lets you count a whole range of valid triplets in one step   |

---

## TL;DR (for future me)

- **Best solution:** Two pointers, O(n²) — *"if sum < target, everything
  between left and right also works with this left — count them all at
  once (`right - left`), then advance left."*
- **Brute force** is O(n³) — correct but only fine for small inputs.
- **Underlying pattern:** this is a variant of 3Sum/3Sum-Closest, but
  instead of collecting or comparing individual triplets, it exploits the
  sorted order to count a whole batch of valid triplets per pointer move —
  a classic technique whenever the question is "how many pairs/triplets
  satisfy an inequality" rather than "find the exact pairs."
