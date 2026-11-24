# Linked List Cycle II — Find the Node Where the Cycle Begins

## Problem

Given `head`, the head of a singly linked list, return the **node where the
cycle begins**. If there is no cycle, return `null`. **Do not modify the
list.**

(`pos` in the examples only *describes* which index the tail's `next` points
back to; it is not passed to your function.)

**Example 1**
```
Input:  head = [3,2,0,-4], pos = 1
Output: tail connects to node index 1
```

**Example 2**
```
Input:  head = [1,2], pos = 0
Output: tail connects to node index 0
```

**Example 3**
```
Input:  head = [1], pos = -1
Output: no cycle  (return null)
```

**Constraints**
- Number of nodes is in the range `[0, 10^4]`
- `-10^5 <= Node.val <= 10^5`
- `pos` is `-1` or a valid index in the list

**Node definition used**
```java
class ListNode1 {

    int value;
    ListNode1 next;

    public ListNode1(int value) {
        this.value = value;
        this.next = null;
    }
}
```

**List used in `main()`:** `10 → 0 → 4 → 7 → -3 → (back to 4)`, i.e. the tail
connects to index 2. Expected answer: the node with value **4**.

---

## Approaches

### 1. Brute Force (HashSet of visited nodes) — O(n) time, O(n) space

**Logic:** Walk from `head`, remembering every node in a `HashSet`. The
**first node you reach that is already in the set** is where the cycle
begins (you walked into the loop and came back to its entrance). If you
reach `null`, there is no cycle.

```java
private static ListNode1 usingBruteForce(ListNode1 head) {

    Set<ListNode1> visited = new HashSet<>();

    ListNode1 current = head;

    while (current != null) {

        // Check whether node was already visited
        if (visited.contains(current)) {
            return current;
        }

        // Mark node as visited
        visited.add(current);

        current = current.next;
    }

    return null;
}
```

**Recall trick:** *"The first node seen twice is the cycle entry."* Simple
and correct, but needs **O(n) extra memory**. (Nodes are stored by
reference — `ListNode1` doesn't override `equals`/`hashCode`.)

---

### 2. Slow & Fast Pointers (Floyd's, two phases) — O(n) time, O(1) space

**Logic:**

- **Phase 1 — detect the cycle.** `slow` moves 1 step, `fast` moves 2. If
  `fast` hits `null`, there is no cycle → return `null`. Otherwise they meet
  somewhere **inside** the cycle.
- **Phase 2 — find the entry.** Move `slow` back to `head`; leave `fast` at
  the meeting point. Now move **both one step at a time**. The node where
  they meet again is the cycle's entry.

```java
private static ListNode1 usingSlowFastPointer(ListNode1 head) {
    ListNode1 slow = head;
    ListNode1 fast = head;

    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;

        if (slow == fast) {
            slow = head;
            while (slow != fast) {
                slow = slow.next;
                fast = fast.next;
            }
            return slow;
        }
    }

    return null;
}
```

**Recall trick:** *"Meet inside the cycle. Reset slow to head. Walk both one
step at a time — they meet at the entry."*

**Why phase 2 works (the math):**

Let
- `a` = steps from `head` to the cycle entry
- `b` = steps from the entry to the meeting point (along the cycle)
- `L` = cycle length, and `c = L − b` = steps from the meeting point back to the entry

When they meet in phase 1:
- `slow` has travelled `a + b`
- `fast` has travelled `2(a + b)`, which also equals `a + b + k·L` (it did `k` extra laps)

So `a + b = k·L`, which gives **`a = (k − 1)·L + c`**.

That means: walking `a` steps from `head` lands on the entry, and walking
`a` steps from the meeting point goes `c` steps to the entry plus some whole
laps — which **also lands on the entry**. Both pointers arrive together.

For this problem's `main()` list: `a = 2`, `b = 1`, `L = 3`, `c = 2`, `k = 1`
→ `a = 0·3 + 2 = 2` ✓

**Trace — phase 1** (`10 → 0 → 4 → 7 → -3 → 4 …`)

| loop | slow | fast | slow == fast? |
|------|------|------|----------------|
| start | 10 | 10 | — |
| 1 | 0 | 4 | no |
| 2 | 4 | -3 (4 → 7 → -3) | no |
| 3 | 7 | 7 (-3 → 4 → 7) | **yes → meeting point is 7** |

**Trace — phase 2** (`slow` reset to head, `fast` stays at 7, both move 1 step)

| step | slow | fast | slow == fast? |
|------|------|------|----------------|
| reset | 10 | 7 | no |
| 1 | 0 | -3 | no |
| 2 | 4 | 4 (-3 wraps to 4) | **yes → return node 4** ✅ |

**Edge cases:**
- **Meeting point equals the entry** (e.g. `[1,2]` with `pos = 0`): after
  `slow = head`, `slow == fast` already, so the phase 2 loop never runs and
  the entry is returned immediately.
- **No cycle:** `fast` reaches `null` → the method returns `null`.
- **Empty list:** the loop never runs → `null`.

---

## Comparison

| Approach              | Time | Space | Key Idea                                                              |
|------------------------|------|-------|---------------------------------------------------------------------------|
| Brute Force (HashSet)  | O(n) | O(n)  | First node seen twice is the entry                                        |
| Slow & Fast (2 phases) | O(n) | O(1)  | Meet in the cycle, then walk head and meeting point together to the entry |

Both leave the list unmodified, as the problem requires.

---

## TL;DR (for future me)

- **Best solution:** Floyd's two-phase approach — O(n) time, **O(1)** space.
  *"Phase 1: slow/fast until they meet. Phase 2: reset slow to head, move
  both one step — they meet at the entry."*
- **Brute force:** HashSet of visited nodes; the first node seen twice is the
  entry. O(n) space.
- **Why it works:** `a = (k − 1)·L + c` — distance from head to entry equals
  distance from the meeting point to the entry (plus whole laps).
- **Gotchas:**
  - The **meeting point is not the entry** (here 7 vs 4) — phase 2 is required.
  - Reset **only `slow`**; leave `fast` at the meeting point.
  - In phase 2 **both pointers move 1 step**.
  - No cycle → return `null`. (In `main()`, printing `repeated.value` would
    throw a `NullPointerException` for a list with no cycle — add a null
    check.)
- **Underlying pattern:** extension of *Linked List Cycle* (just detection).
  The same two-phase trick solves *Find the Duplicate Number* by treating
  array values as `next` pointers.
