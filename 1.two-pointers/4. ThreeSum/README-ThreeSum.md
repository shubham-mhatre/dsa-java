# 3Sum

## Problem

Given an integer array `nums`, return all unique triplets
`[nums[i], nums[j], nums[k]]` such that `i != j`, `i != k`, `j != k`, and
`nums[i] + nums[j] + nums[k] == 0`. The solution set must not contain
duplicate triplets.

**Example 1**
```
Input:  nums = [-1,0,1,2,-1,-4]
Output: [[-1,-1,2],[-1,0,1]]
```

**Example 2**
```
Input:  nums = [0,1,1]
Output: []
```

**Example 3**
```
Input:  nums = [0,0,0]
Output: [[0,0,0]]
```

**Constraints**
- `3 <= nums.length <= 3000`
- `-10^5 <= nums[i] <= 10^5`

---

## Approaches

### 1. Brute Force — O(n³)

**Logic:** Sort the array (to make duplicate-skipping easy), then try every
triplet `(i, j, k)` with `i < j < k`, skipping any index that repeats the
previous value at the same loop level.

```java
private static List<List<Integer>> bruteforceSolution(int[] nums) {
    List<List<Integer>> output = new ArrayList<>();
    Arrays.sort(nums);

    for (int i = 0; i < nums.length - 2; i++) {
        if (i > 0 && nums[i] == nums[i - 1]) {
            continue;
        }
        for (int j = i + 1; j < nums.length - 1; j++) {
            if (j > i + 1 && nums[j] == nums[j - 1]) {
                continue;
            }
            for (int k = j + 1; k < nums.length; k++) {
                if (k > j + 1 && nums[k] == nums[k - 1]) {
                    continue;
                }
                int sum = nums[i] + nums[j] + nums[k];
                if (sum == 0) {
                    output.add(Arrays.asList(nums[i], nums[j], nums[k]));
                }
            }
        }
    }
    return output;
}
```

**Recall trick:** *"Sort first so duplicates sit next to each other, then
brute-force every triplet while skipping repeats at each level."* Simple,
correct, but O(n³) — too slow for large inputs.

---

### 2. Two Pointers — O(n²)

**Logic:** Sort the array. Fix one element `nums[i]`, then use two pointers
(`left` just after `i`, `right` at the end) to find pairs that make the sum
zero — same core idea as the two-pointer pair-sum pattern, run once per `i`.

```java
private static List<List<Integer>> usingTwoPointerSolution(int[] nums) {
    List<List<Integer>> output = new ArrayList<>();
    Arrays.sort(nums);

    for (int i = 0; i < nums.length - 2; i++) {
        int left = i + 1;
        int right = nums.length - 1;

        if (i > 0 && nums[i] == nums[i - 1]) {
            continue;
        }

        while (left < right) {
            int sum = nums[i] + nums[left] + nums[right];
            if (sum == 0) {
                output.add(Arrays.asList(nums[i], nums[left], nums[right]));
                left++;
                right--;

                while (left < nums.length && nums[left] == nums[left - 1]) {
                    left++;
                }
                while (right >= 0 && nums[right] == nums[right + 1]) {
                    right--;
                }
            } else if (sum < 0) {
                left++;
            } else {
                right--;
            }
        }
    }
    return output;
}
```

**Recall trick:** *"Fix one number, two-pointer the rest."* For each `i`,
the problem collapses into "find two numbers in the remaining sorted range
that sum to `-nums[i]`" — exactly the sorted two-pointer pattern, just
nested inside a loop over `i`.

**Trace** for `nums = [-1,0,1,2,-1,-4]` → sorted `[-4,-1,-1,0,1,2]`:

| i | nums[i] | left/right walk | found | after skip-dup |
|---|---------|------------------|-------|------------------|
| 0 | -4 | (1,5)→-3, (2,5)→-3, (3,5)→-2, (4,5)→-1, all < 0, left keeps moving until left==right | none | — |
| 1 | -1 | (2,5): -1-1+2=0 → **[-1,-1,2]**; (3,4): -1+0+1=0 → **[-1,0,1]** | 2 triplets | left/right converge, loop ends |
| 2 | -1 | `nums[2]==nums[1]` → skipped (duplicate `i`) | — | — |
| 3 | 0 | (4,5): 0+1+2=3 > 0 → right-- → left<right false | none | — |

Result: `[[-1,-1,2],[-1,0,1]]` ✅

---

## Comparison

| Approach      | Time  | Space | Key Idea                                                        |
|---------------|-------|-------|--------------------------------------------------------------------|
| Brute Force   | O(n³) | O(1)* | Sort, then check every triplet, skipping repeats at each level     |
| Two Pointers  | O(n²) | O(1)* | Fix one number, two-pointer the rest of the sorted array           |

\* Ignoring the space used by the output list itself.

---

## TL;DR (for future me)

- **Best solution:** Two pointers, O(n²) — *"fix one number, then run the
  classic two-pointer pair-sum on what's left."*
- **Brute force** is O(n³) — correct but only fine for small inputs.
- **Underlying pattern:** 3Sum is just 2Sum with an extra outer loop. Any
  "kSum" problem generalizes this way — fix `k-2` numbers with nested loops,
  then two-pointer the final two.
- **Watch for:** duplicate triplets. Sorting first makes duplicates sit
  next to each other, so a simple `== previous` check at each pointer level
  is enough to skip them — no need for a `Set` or manual dedup pass.
