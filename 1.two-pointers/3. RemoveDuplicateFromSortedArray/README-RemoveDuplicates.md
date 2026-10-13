# Remove Duplicates from Sorted Array

## Problem

Given an integer array `nums` sorted in non-decreasing order, remove the
duplicates **in-place** so each unique element appears only once. The
relative order of elements must be preserved. Let `k` be the number of
unique elements — the first `k` elements of `nums` must hold the unique
values in sorted order; anything beyond index `k - 1` is ignored.

**Example 1**
```
Input:  nums = [1,1,2]
Output: 2, nums = [1,2,_]
```

**Example 2**
```
Input:  nums = [0,0,1,1,1,2,2,3,3,4]
Output: 5, nums = [0,1,2,3,4,_,_,_,_,_]
```

**Constraints**
- `1 <= nums.length <= 3 * 10^4`
- `-100 <= nums[i] <= 100`
- `nums` is sorted in non-decreasing order.

---

## Approaches

### 1. Brute Force (Extra Space) — O(n) time, O(n) space

**Logic:** Build a separate `tempArray` that only ever receives a value when
it differs from the last unique value written. Then copy it back over the
original array.

```java
private static int bruteforceSolution(int[] array) {
    int tempCounter = 0;
    int[] tempArray = new int[array.length];
    tempArray[tempCounter] = array[0];

    for (int i = 1; i < array.length; i++) {
        if (array[i] != tempArray[tempCounter]) {
            tempCounter++;
            tempArray[tempCounter] = array[i];
        }
    }

    for (int i = 0; i <= tempCounter; i++) {
        array[i] = tempArray[i];
    }
    return tempCounter + 1;
}
```

**Recall trick:** *"Only write to temp when the value changes."* Correct and
easy to follow, but it uses O(n) extra space — the problem asks for in-place.

---

### 2. Two Pointers (In-Place) — O(n) time, O(1) space

**Logic:** `previous` marks the last position of a confirmed-unique value.
`current` scans ahead. Whenever `current` finds a value different from
`array[previous]`, that value gets written just after `previous`
(`array[previous + 1]`), `previous` advances, and the count goes up.
Matching values are simply skipped by advancing `current` alone.

```java
private static int usingTwoPointers(int[] array) {
    int result = 1;
    int previous = 0;
    int current = 1;

    while (current < array.length) {
        if (array[current] == array[previous]) {
            current++;
            continue;
        }
        array[previous + 1] = array[current];
        current++;
        previous++;
        result++;
    }
    return result;
}
```

**Recall trick:** *"previous = last unique slot, current = scanner."* When
values match, just move the scanner. When they differ, place the new value
right after the last unique one, then advance both. No extra array needed —
duplicates get silently overwritten in place.

**Trace** for `array = [0,0,1,1,1,2,2,3,3,4]`:

| current | array[current] | array[previous] | action | previous after | array state |
|---------|-----------------|-------------------|--------|-----------------|--------------|
| 1 | 0 | 0 | equal → skip | 0 | `[0,0,1,1,1,2,2,3,3,4]` |
| 2 | 1 | 0 | write → array[1]=1 | 1 | `[0,1,1,1,1,2,2,3,3,4]` |
| 3 | 1 | 1 | equal → skip | 1 | same |
| 4 | 1 | 1 | equal → skip | 1 | same |
| 5 | 2 | 1 | write → array[2]=2 | 2 | `[0,1,2,1,1,2,2,3,3,4]` |
| 6 | 2 | 2 | equal → skip | 2 | same |
| 7 | 3 | 2 | write → array[3]=3 | 3 | `[0,1,2,3,1,2,2,3,3,4]` |
| 8 | 3 | 3 | equal → skip | 3 | same |
| 9 | 4 | 3 | write → array[4]=4 | 4 | `[0,1,2,3,4,2,2,3,3,4]` |

Result: `k = 5`, first 5 elements `[0,1,2,3,4]` ✅

---

## Comparison

| Approach      | Time | Space | Key Idea                                              |
|---------------|------|-------|--------------------------------------------------------|
| Brute Force   | O(n) | O(n)  | Build a clean temp array, then copy back               |
| Two Pointers  | O(n) | O(1)  | Overwrite duplicates in place using a slow/fast pointer |

---

## TL;DR (for future me)

- **Best solution:** Two pointers, O(n) time, O(1) space — *"slow pointer
  marks the last unique slot, fast pointer scans and writes new values in."*
- **Brute force** is correct but violates the in-place constraint by using
  a second array.
- **Underlying pattern:** this is the classic "slow/fast pointer" technique
  for in-place array compaction — it reappears in problems like removing a
  specific value, moving zeroes, or deduplicating with at-most-two-copies
  allowed.
