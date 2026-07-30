# Squares of a Sorted Array

## Problem

Given an integer array `nums` sorted in non-decreasing order, return an array
of the squares of each number, also sorted in non-decreasing order.

**Example 1**
```
Input:  nums = [-4,-1,0,3,10]
Output: [0,1,9,16,100]
```

**Example 2**
```
Input:  nums = [-7,-3,2,3,11]
Output: [4,9,9,49,121]
```

**Constraints**
- `1 <= nums.length <= 10^4`
- `-10^4 <= nums[i] <= 10^4`
- `nums` is sorted in non-decreasing order.

---

## Approaches

### 1. Brute Force — O(n log n)

**Logic:** Square every element, then sort the resulting array.

```java
private static int[] bruteForceSolution(int[] nums) {
    // square every element
    int[] sqArray = Arrays.stream(nums).map(s -> s * s).toArray();
    // sort
    Arrays.sort(sqArray);
    return sqArray;
}
```

**Recall trick:** *"Square first, sort after."* Simple and correct, but ignores
the fact that the input is already sorted — that's free information being thrown away.

---

### 2. Two Pointers — O(n)

**Logic:** Because the array is sorted, the largest square always comes from
one of the two ends (the most negative or the most positive value). Place one
pointer at the start (`left`) and one at the end (`right`) of the squared
array, and fill the result array **from the back**, always picking the bigger
of the two ends.

```java
private static int[] usingTwoPointers(int[] nums) {
    int[] squareArray = Arrays.stream(nums).map(s -> s * s).toArray();
    int[] res = new int[nums.length];
    int left = 0;
    int right = nums.length - 1;

    for (int i = squareArray.length - 1; i >= 0; i--) {
        if (squareArray[left] >= squareArray[right]) {
            res[i] = squareArray[left];
            left++;
        } else {
            res[i] = squareArray[right];
            right--;
        }
    }
    return res;
}
```

**Recall trick:** *"Sorted array, fill result from the back, always take the
bigger end."* Since squaring is monotonic on each side of zero, whichever end
has the bigger absolute value produces the bigger square — no need to compare
original signed values, just compare the squares directly.

**Trace** for `nums = [-4,-1,0,3,10]`:

| i | left | right | squareArray[left] | squareArray[right] | picked | next |
|---|------|-------|--------------------|----------------------|--------|------|
| 4 | 0 | 4 | 16 | 100 | 100 → res[4] | right-- |
| 3 | 0 | 3 | 16 | 9   | 16 → res[3]  | left++  |
| 2 | 1 | 3 | 1  | 9   | 9 → res[2]   | right-- |
| 1 | 1 | 2 | 1  | 0   | 1 → res[1]   | left++  |
| 0 | 2 | 2 | 0  | 0   | 0 → res[0]   | left++  |

Result: `[0,1,9,16,100]` ✅

---

## Comparison

| Approach      | Time       | Space | Key Idea                                          |
|---------------|------------|-------|-----------------------------------------------------|
| Brute Force   | O(n log n) | O(n)  | Square everything, then sort                        |
| Two Pointers  | O(n)       | O(n)  | Sorted input → biggest square is always at an end   |

---

## TL;DR (for future me)

- **Best solution:** Two pointers, O(n). *"Fill from the back, always take the
  bigger of the two ends."*
- **Brute force** is correct but wastes the fact that the input is already sorted.
- **Underlying pattern:** whenever an array is sorted and you need extremes
  (largest, smallest, or in this case largest square), think two pointers
  converging from both ends before reaching for a full sort.
