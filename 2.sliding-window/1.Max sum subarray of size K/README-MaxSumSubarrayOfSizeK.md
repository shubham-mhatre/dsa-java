# Maximum Sum Subarray of Size K

## Problem

Given an array `arr[]` and a number `k`, return the maximum sum of any
contiguous subarray of size `k`.

**Example 1**
```
Input:  arr = [100, 200, 300, 400], k = 2
Output: 700
Explanation: 300 + 400 = 700
```

**Example 2**
```
Input:  arr = [1, 4, 2, 10, 23, 3, 1, 0, 20], k = 4
Output: 39
Explanation: 4 + 2 + 10 + 23 = 39, the maximum sum among all size-4 windows.
```

**Example 3**
```
Input:  arr = [100, 200, 300, 400], k = 1
Output: 400
```

**Constraints**
- `1 <= arr.size() <= 10^6`
- `0 <= arr[i] <= 10^6`
- `1 <= k <= arr.size()`

---

## Approaches

### 1. Brute Force — O(n·k)

**Logic:** For every possible starting index, sum the next `k` elements
from scratch and track the maximum.

```java
public static int bruteForce(int[] arr, int k) {
    int maxSum = 0;

    for (int i = 0; i <= arr.length - k; i++) {
        int currentSum = 0;

        for (int j = i; j < i + k; j++) {
            currentSum += arr[j];
        }

        maxSum = Math.max(maxSum, currentSum);
    }

    return maxSum;
}
```

**Recall trick:** *"Recompute the whole window sum every time."* Correct,
but re-adds `k` elements at every starting position — wasteful, since
consecutive windows overlap in `k - 1` elements.

---

### 2. Sliding Window — O(n)

**Logic:** Compute the sum of the first window once. Then slide the window
one step at a time: **subtract** the element leaving on the left, **add**
the element entering on the right. This turns each window update into O(1)
instead of recomputing the whole sum.

```java
public static int usingSlidingWindow(int[] arr, int k) {
    int resultSum = 0;
    int currentSum = 0;
    int low = 0;
    int high = k - 1;

    // first window calculation
    for (int i = 0; i < k; i++) {
        currentSum += arr[i];
    }

    while (high < arr.length) {
        resultSum = Math.max(resultSum, currentSum);
        low++;
        high++;

        if (high == arr.length) {
            break;
        }
        currentSum = currentSum - arr[low - 1] + arr[high];
    }

    return resultSum;
}
```

**Recall trick:** *"Slide, don't recompute: drop the left element, add the
right element."* The `if (high == arr.length) break;` guard is the subtle
but essential piece — without it, `arr[high]` would be read out of bounds
right after the last valid window.

**Trace** for `arr = [1,4,2,10,23,3,1,0,20]`, `k = 4`:

| low | high | currentSum (before update) | resultSum after max | next currentSum |
|-----|------|-------------------------------|------------------------|---------------------|
| 0 | 3 | 1+4+2+10 = 17 | 17 | 17 − arr[0] + arr[4] = 17−1+23 = 39 |
| 1 | 4 | 39 | 39 | 39 − arr[1] + arr[5] = 39−4+3 = 38 |
| 2 | 5 | 38 | 39 | 38 − arr[2] + arr[6] = 38−2+1 = 37 |
| 3 | 6 | 37 | 39 | 37 − arr[3] + arr[7] = 37−10+0 = 27 |
| 4 | 7 | 27 | 39 | 27 − arr[4] + arr[8] = 27−23+20 = 24 |
| 5 | 8 | 24 | 39 | `high == arr.length` → break |

Result: `39` ✅

---

## Comparison

| Approach       | Time    | Space | Key Idea                                                        |
|----------------|---------|-------|----------------------------------------------------------------------|
| Brute Force    | O(n·k)  | O(1)  | Recompute the full window sum at every starting index                |
| Sliding Window | O(n)    | O(1)  | Reuse the previous window sum — subtract left, add right             |

---

## TL;DR (for future me)

- **Best solution:** Sliding window, O(n) — *"drop the element leaving on
  the left, add the element entering on the right — never recompute the
  whole sum."*
- **Brute force** is O(n·k) — correct but wasteful, since it ignores the
  overlap between consecutive windows.
- **Watch for:** the `high == arr.length` guard right after sliding the
  window — without it, the next `currentSum` update reads past the end of
  the array on the final iteration.
- **Underlying pattern:** this is the canonical fixed-size sliding window
  template — compute the first window directly, then maintain it
  incrementally as it slides. Reused constantly in problems like "max/min
  sum of size-k subarray," "longest substring with constraints," and
  "average of subarrays of size k."
