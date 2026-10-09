# Longest Substring with Exactly K Distinct Characters

## Problem

Given a string `s` of lowercase letters and an integer `k`, return the
length of the longest substring that contains **exactly `k` distinct
characters**. If no such substring exists, return `-1`.

**Example 1**
```
Input:  s = "aabacbebebe", k = 3
Output: 7
Explanation: "cbebebe" has exactly 3 distinct characters: c, b, e.
```

**Example 2**
```
Input:  s = "aaaa", k = 2
Output: -1
Explanation: No substring has 2 distinct characters.
```

**Example 3**
```
Input:  s = "aabaaab", k = 2
Output: 7
Explanation: The whole string has exactly 2 distinct characters (a, b).
```

**Constraints**
- `1 <= s.length() <= 10^5`
- `1 <= k <= 26`

---

## Approaches

> Both snippets use `java.util.Map` and `java.util.HashMap`:
> ```java
> import java.util.HashMap;
> import java.util.Map;
> ```

### 1. Brute Force — O(n²)

**Logic:** For every starting index `i`, extend `j` forward while tracking
character counts in a map. Record the window length whenever the map has
exactly `k` entries, and stop extending early once it exceeds `k`.

```java
private static int bruteForce(String s, int k) {
    int maxLength = -1;

    for (int i = 0; i < s.length(); i++) {
        Map<Character, Integer> map = new HashMap<>();

        for (int j = i; j < s.length(); j++) {
            char c = s.charAt(j);
            map.put(c, map.getOrDefault(c, 0) + 1);

            if (map.size() == k) {
                maxLength = Math.max(maxLength, j - i + 1);
            }

            if (map.size() > k) {
                break;
            }
        }
    }
    return maxLength;
}
```

**Recall trick:** *"Try every start, extend until too many distinct chars,
then stop."* Correct, but restarts the map from scratch for every `i`.

---

### 2. Sliding Window (Variable Size) — O(n)

**Logic:** Expand `high` one character at a time, counting characters in a
map. If the map ever holds **more than `k`** distinct characters, shrink
from `low` — decrement that character's count and **remove it from the map
when its count hits 0** — until the map is back to `k` distinct. After
each step, if the map has **exactly `k`** entries, record `high - low`.

```java
private static int usingSlidingWindow(String s, int k) {
    int high = 0;
    int low = 0;
    int maxLength = -1;
    Map<String, Integer> map = new HashMap<>();

    String[] stringArr = s.split("");

    while (high < stringArr.length) {
        map.put(stringArr[high], map.getOrDefault(stringArr[high], 0) + 1);
        high++;

        while (map.size() > k) {
            map.put(stringArr[low], map.getOrDefault(stringArr[low], 0) - 1);
            if (map.get(stringArr[low]) == 0) {
                map.remove(stringArr[low]);
            }
            low++;
        }

        if (map.size() == k) {
            maxLength = Math.max(maxLength, high - low);
        }
    }
    return maxLength;
}
```

**Recall trick:** *"Expand right; if more than k distinct, shrink left
until it's k again; record only when it's exactly k."* The key detail is
**removing the key when its count reaches 0** — `map.size()` is how we
count distinct characters, so a zero-count key left behind would break the
logic.

**Why "exactly k" works:** adding one character raises the distinct count
by at most 1, so shrinking until `size <= k` always lands on exactly `k`
(never skips past it). If `size < k`, nothing is recorded.

**Trace** for `s = "aabacbebebe"`, `k = 3`:

| high (after ++) | char added | map after shrink | low | action | maxLength |
|------------------|------------|-------------------|-----|--------|-----------|
| 1–4 | a, a, b, a | {a:3, b:1} | 0 | size 2 < 3 → skip | -1 |
| 5 | c | {a:3, b:1, c:1} | 0 | size 3 → record 5−0 = 5 | 5 |
| 6 | b | {a:3, b:2, c:1} | 0 | size 3 → record 6−0 = 6 | 6 |
| 7 | e | {b:1, c:1, e:1} | 0 → 4 | size 4 > 3 → shrink (drops a,a,b,a) → record 7−4 = 3 | 6 |
| 8 | b | {b:2, c:1, e:1} | 4 | record 8−4 = 4 | 6 |
| 9 | e | {b:2, c:1, e:2} | 4 | record 9−4 = 5 | 6 |
| 10 | b | {b:3, c:1, e:2} | 4 | record 10−4 = 6 | 6 |
| 11 | e | {b:3, c:1, e:3} | 4 | record 11−4 = 7 | **7** |

Result: `7` ✅ — the window `"cbebebe"`.

---

## Comparison

| Approach        | Time  | Space | Key Idea                                                            |
|------------------|-------|-------|------------------------------------------------------------------------|
| Brute Force       | O(n²) | O(k)  | Restart a fresh map at every start index, stop early past k distinct   |
| Sliding Window    | O(n)  | O(k)  | Expand right, shrink left when > k distinct, record when exactly k     |

Space is O(k) because the map never holds more than `k + 1` keys (and
`k <= 26`, so effectively O(1)).

---

## TL;DR (for future me)

- **Best solution:** Variable-size sliding window with a frequency map,
  O(n) — *"expand right; too many distinct? shrink left; record the length
  only when the map has exactly k keys."*
- **Brute force** is O(n²) — correct, but rebuilds the map for every start.
- **Must remember:** when a character's count drops to 0, **remove the key**
  from the map — `map.size()` *is* the distinct-character count.
- **Underlying pattern:** the "longest window with at most/exactly k
  distinct elements" template — the same shape shows up in *Longest
  Substring Without Repeating Characters*, *Fruit Into Baskets*, and
  *Longest Substring with At Most K Distinct Characters*. It's the
  frequency-map flavor of the variable-size sliding window.
