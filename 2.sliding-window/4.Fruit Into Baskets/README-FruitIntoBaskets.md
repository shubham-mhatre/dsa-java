# Fruit Into Baskets

## Problem

A farm has a single row of fruit trees. `fruits[i]` is the type of fruit
the `i`-th tree produces. You have **two baskets**, and each basket holds
only **one type** of fruit (unlimited quantity).

Starting from any tree, you must pick exactly one fruit from every tree
while moving right. You stop when you reach a tree whose fruit doesn't fit
in either basket. Return the **maximum number of fruits** you can pick.

**Example 1**
```
Input:  fruits = [1,2,1]
Output: 3
Explanation: Pick from all 3 trees.
```

**Example 2**
```
Input:  fruits = [0,1,2,2]
Output: 3
Explanation: Pick from trees [1,2,2]. Starting at the first tree gives only [0,1].
```

**Example 3**
```
Input:  fruits = [1,2,3,2,2]
Output: 4
Explanation: Pick from trees [2,3,2,2]. Starting at the first tree gives only [1,2].
```

**In plain words:** find the longest contiguous subarray with **at most 2
distinct values**.

---

## Approaches

> Both snippets use `java.util.Map` and `java.util.HashMap`:
> ```java
> import java.util.HashMap;
> import java.util.Map;
> ```

### 1. Brute Force — O(n²)

**Logic:** For every starting tree `i`, walk right while tracking fruit
counts in a map. Stop as soon as a 3rd type appears; otherwise record the
length picked so far.

```java
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
```

**Recall trick:** *"Try every start, keep going until a 3rd fruit type
shows up."* Correct, but rebuilds the map from scratch for every start.

---

### 2. Sliding Window (Variable Size) — O(n)

**Logic:** Expand `high` one tree at a time, counting fruit types in a map.
If the map holds **more than 2** types, shrink from `low` — decrement that
fruit's count and **remove the key when it hits 0** — until only 2 types
remain. After every step the window is valid (at most 2 types), so record
`high - low`.

```java
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
```

**Recall trick:** *"Expand right; more than 2 types? shrink left until
it's 2 again; record the window length every time."*

**"At most k" vs "exactly k":** this problem is *at most* 2 distinct, so
the length is recorded **unconditionally** after the shrink loop. In the
previous problem (*Longest Substring with K Distinct Characters*) it had
to be *exactly* k, so the length was recorded only when
`map.size() == k`. Same skeleton, one different condition.

**Trace** for `fruits = [1,2,3,2,2]`:

| high (after ++) | fruit added | map after shrink | low | window length | maxLength |
|------------------|-------------|-------------------|-----|----------------|-----------|
| 1 | 1 | {1:1} | 0 | 1 | 1 |
| 2 | 2 | {1:1, 2:1} | 0 | 2 | 2 |
| 3 | 3 | {2:1, 3:1} | 0 → 1 | 2 (3 types → dropped fruit 1) | 2 |
| 4 | 2 | {2:2, 3:1} | 1 | 3 | 3 |
| 5 | 2 | {2:3, 3:1} | 1 | 4 | **4** |

Result: `4` ✅ — the trees `[2,3,2,2]`.

---

## Comparison

| Approach        | Time  | Space | Key Idea                                                      |
|------------------|-------|-------|------------------------------------------------------------------|
| Brute Force       | O(n²) | O(1)  | Restart a fresh map at every start, stop at the 3rd fruit type   |
| Sliding Window    | O(n)  | O(1)  | Expand right, shrink left when > 2 types, record every step      |

Space is O(1) because the map never holds more than 3 keys.

---

## TL;DR (for future me)

- **Best solution:** Variable-size sliding window with a frequency map,
  O(n) — *"longest subarray with at most 2 distinct values."*
- **Brute force** is O(n²) — correct, but rebuilds the map for every start.
- **Must remember:** remove a key when its count reaches 0, because
  `map.size()` is the number of distinct fruit types in the window.
- **At most vs exactly:** "at most k" records the length after every step;
  "exactly k" records only when `map.size() == k`.
- **Underlying pattern:** this is *Longest Substring with At Most K
  Distinct Characters* with `k = 2`, dressed up as a story. Once you spot
  the "two baskets = two distinct types" translation, it's the same
  template.
