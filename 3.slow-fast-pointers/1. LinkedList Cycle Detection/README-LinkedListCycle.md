# Linked List Cycle

## Problem

Given `head`, the head of a singly linked list, determine whether the list
contains a **cycle** — i.e. some node can be reached again by repeatedly
following `next`. Return `true` if there is a cycle, otherwise `false`.

(`pos` in the examples is only used to *describe* where the tail connects
back to; it is not passed to your function.)

**Example 1**
```
Input:  head = [3,2,0,-4], pos = 1
Output: true
Explanation: The tail (-4) connects back to the node at index 1 (value 2).
```

**Example 2**
```
Input:  head = [1,2], pos = 0
Output: true
Explanation: The tail connects back to the node at index 0.
```

**Example 3**
```
Input:  head = [1], pos = -1
Output: false
Explanation: No cycle.
```

**Constraints**
- Number of nodes is in the range `[0, 10^4]`
- `-10^5 <= Node.val <= 10^5`
- `pos` is `-1` or a valid index in the list

**Node definition used**
```java
class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
        next = null;
    }
}
```

---

## Approaches

### 1. Brute Force (HashSet of visited nodes) — O(n) time, O(n) space

**Logic:** Walk the list from `head`, remembering every node you've seen in
a `HashSet`. If you ever reach a node that is **already in the set**, you've
come back around — there's a cycle. If you reach `null`, the list ended —
no cycle.

```java
private static boolean usingBruteForce(ListNode head) {

    Set<ListNode> visited = new HashSet<>();

    ListNode current = head;

    while (current != null) {

        // Check if node is already visited
        if (visited.contains(current)) {
            return true;
        }

        // Store the visited node
        visited.add(current);

        // Move to next node
        current = current.next;
    }

    return false;
}
```

**Recall trick:** *"Remember every node; seeing one twice means a cycle."*
Simple and correct, but it costs **O(n) extra memory**.

**Detail worth remembering:** the set stores node **references**.
`ListNode` doesn't override `equals`/`hashCode`, so two different nodes with
the same `val` are still different entries — exactly what we want.

---

### 2. Slow & Fast Pointers (Floyd's Tortoise and Hare) — O(n) time, O(1) space

**Logic:** Start two pointers at `head`. Each loop, `slow` moves **1** step
and `fast` moves **2** steps.
- If there is **no cycle**, `fast` eventually reaches `null` (or the last
  node) and the loop ends → `false`.
- If there **is a cycle**, both pointers end up inside it, and `fast` closes
  the gap on `slow` by exactly one node per loop, so they must land on the
  same node → `true`.

```java
private static boolean usingSlowFastPointers(ListNode head) {
    ListNode slow = head;
    ListNode fast = head;

    while (fast != null && fast.next != null) {

        slow = slow.next;
        fast = fast.next.next;

        if (slow == fast) {
            return true;
        }
    }

    return false;
}
```

**Recall trick:** *"Tortoise moves 1, hare moves 2. If the hare ever meets
the tortoise → cycle. If the hare falls off the end → no cycle."*

**Two details that matter:**
- The loop condition is `fast != null && fast.next != null` — **in that
  order**. Short-circuit `&&` guarantees `fast.next.next` is never evaluated
  on a null, so no `NullPointerException`.
- Compare with `==` (same node object), never by `val`.

**Why they must meet:** once both pointers are inside the cycle, `fast`
gains exactly **1 node per loop** on `slow`. The distance `fast` still has
to close shrinks by 1 each loop (here: 2 → 1 → 0), so it can never "jump
over" `slow` — it lands on it.

**Trace 1 — cycle:** `[3,2,0,-4]`, tail → index 1

| loop | slow | fast | slow == fast? |
|------|------|------|----------------|
| start | 3 | 3 | — |
| 1 | 2 | 0 | no |
| 2 | 0 | 2 (0 → -4 → 2, wrapped around the cycle) | no |
| 3 | -4 | -4 (2 → 0 → -4) | **yes → `true`** |

**Trace 2 — no cycle:** `[1,2,3,4]`

| loop | slow | fast | result |
|------|------|------|--------|
| start | 1 | 1 | — |
| 1 | 2 | 3 | `fast.next` (4) isn't null → continue |
| 2 | 3 | `null` | `fast == null` → loop ends → **`false`** |

**Edge cases:** empty list → loop never runs → `false`. Single node, no
cycle → `fast.next == null` → `false`. Single node pointing to itself →
`slow` and `fast` both land on it → `true`.

---

## Comparison

| Approach              | Time | Space | Key Idea                                                      |
|------------------------|------|-------|-------------------------------------------------------------------|
| Brute Force (HashSet)  | O(n) | O(n)  | Remember visited nodes; seeing one twice means a cycle            |
| Slow & Fast Pointers   | O(n) | O(1)  | Hare gains 1 node per loop on tortoise inside a cycle → they meet |

Both are linear in time. The whole improvement is **space: O(n) → O(1)**.

---

## TL;DR (for future me)

- **Best solution:** Floyd's slow/fast pointers — O(n) time, **O(1)** space.
  *"Slow moves 1, fast moves 2; meeting means a cycle, fast hitting null
  means no cycle."*
- **Brute force** (HashSet of visited nodes) is also O(n) time but uses
  **O(n)** extra space.
- **Gotcha:** loop condition `fast != null && fast.next != null` — order
  matters, it protects `fast.next.next`.
- **Underlying pattern:** Floyd's tortoise-and-hare. It reappears in
  *Linked List Cycle II* (find where the cycle starts), *Middle of the
  Linked List* (when `fast` ends, `slow` is in the middle), and *Happy
  Number*.
