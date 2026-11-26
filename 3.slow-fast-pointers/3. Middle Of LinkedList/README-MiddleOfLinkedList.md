# Middle of the Linked List

## Problem

Given the `head` of a singly linked list, return the **middle node**. If
there are two middle nodes, return the **second** one.

**Example 1**
```
Input:  head = [1,2,3,4,5]
Output: [3,4,5]
Explanation: The middle node is node 3.
```

**Example 2**
```
Input:  head = [1,2,3,4,5,6]
Output: [4,5,6]
Explanation: Two middle nodes (3 and 4) — return the second one.
```

**Constraints**
- Number of nodes is in the range `[1, 100]`
- `1 <= Node.val <= 100`

**Node definition used**
```java
class ListNode2 {
    int val;
    ListNode2 next;

    ListNode2(int val) {
        this.val = val;
        this.next = null;
    }

    @Override
    public String toString() {
        return "ListNode2 [val=" + val + ", next=" + next + "]";
    }
}
```

---

## Approaches

### 1. Brute Force (two-pass count) — O(n) time, O(1) space

**Logic:** Pass 1 counts the nodes. Pass 2 walks `count / 2` steps from the
head and returns that node. Integer division gives the **second** middle
when the length is even.

```java
private static ListNode2 usingBruteForce(ListNode2 head) {

    // Step 1: Count total nodes
    int count = 0;
    ListNode2 current = head;

    while (current != null) {
        count++;
        current = current.next;
    }

    // Step 2: Find middle index
    int middleIndex = count / 2;

    // Step 3: Traverse to middle node
    current = head;

    for (int i = 0; i < middleIndex; i++) {
        current = current.next;
    }

    // Step 4: Return middle node
    return current;
}
```

**Recall trick:** *"Count the nodes, then walk count / 2 steps."* Simple and
correct, but it needs **two separate traversals**.

| count | middleIndex = count / 2 | result |
|-------|--------------------------|--------|
| 5 | 2 | node 3 (exact middle) |
| 6 | 3 | node 4 (second middle) |
| 1 | 0 | the head itself |
| 2 | 1 | the second node |

---

### 2. Slow & Fast Pointers — O(n) time, O(1) space, one pass

**Logic:** Start `slow` and `fast` at `head`. Each loop `slow` moves 1 node
and `fast` moves 2. When `fast` can't move any further, `slow` has covered
exactly half the list — it is on the middle node.

```java
private static ListNode2 usingSlowFastPointers(ListNode2 head) {
    ListNode2 slow = head;
    ListNode2 fast = head;

    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
    }

    return slow;
}
```

**Recall trick:** *"Fast moves twice as fast — when fast reaches the end,
slow is at the middle."*

**Odd vs even length (why it returns the second middle):**
- **Odd length:** `fast` stops **on the last node** (`fast.next == null`) →
  `slow` is the exact middle.
- **Even length:** `fast` falls **off the end** (`fast == null`) → `slow`
  lands on the **second** middle node.

The condition `fast != null && fast.next != null` (in that order — the
short-circuit protects `fast.next`) is what selects the *second* middle.

**Trace 1 — odd length** `[1,2,3,4,5]`

| loop | slow | fast | result |
|------|------|------|--------|
| start | 1 | 1 | — |
| 1 | 2 | 3 | `fast.next` (4) isn't null → continue |
| 2 | 3 | 5 | `fast.next == null` → stop → **return 3** |

**Trace 2 — even length** `[1,2,3,4,5,6]`

| loop | slow | fast | result |
|------|------|------|--------|
| start | 1 | 1 | — |
| 1 | 2 | 3 | continue |
| 2 | 3 | 5 | `fast.next` (6) isn't null → continue |
| 3 | 4 | `null` | `fast == null` → stop → **return 4** (second middle) |

**Edge cases:** one node → loop never runs → returns the head. Two nodes
`[1,2]` → one loop → `slow = 2`, `fast = null` → returns the second node.

**Variant (not in your code):** to get the **first** middle on even lengths,
use `while (fast.next != null && fast.next.next != null)` instead.

---

## Comparison

| Approach            | Time | Space | Passes | Key Idea                                              |
|----------------------|------|-------|--------|-----------------------------------------------------------|
| Brute Force (count)  | O(n) | O(1)  | 2      | Count nodes, then walk `count / 2` steps                  |
| Slow & Fast Pointers | O(n) | O(1)  | 1      | `fast` moves 2x → when `fast` ends, `slow` is at the middle |

**Honest note:** both do about the same total pointer movement (~1.5n), so
this isn't a complexity win. The value of slow/fast is that it's a **single
traversal** and it's the reusable building block behind other problems.

---

## TL;DR (for future me)

- **Best solution:** Slow & fast pointers, one pass — *"fast moves 2, slow
  moves 1; when fast can't move, slow is the middle."*
- **Even length → second middle** with `while (fast != null && fast.next != null)`.
- **Brute force:** count nodes, then walk `count / 2` steps (two passes).
- **Gotcha:** keep the condition order `fast != null && fast.next != null`.
- **Underlying pattern:** finding the middle is the first step in
  *Palindrome Linked List* (find middle → reverse second half → compare),
  *Sort List* (split for merge sort), and *Reorder List*.
