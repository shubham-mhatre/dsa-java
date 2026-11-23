Slow and Fast Pointers (Tortoise and Hare Algorithm)
1. Core Concept

The Slow and Fast Pointers technique is a two-pointer algorithm used to identify cycles, recurring behaviour, middle positions, and repeated states efficiently.

Key Idea
Slow pointer: Moves one step at a time.
Fast pointer: Moves two steps at a time.

By moving at different speeds, we can detect cycles or identify specific positions without using extra space.

Important: Do not decide whether to use this technique based only on the data structure. It can be applied to a single variable, arrays, linked lists, strings, or any sequence of states where pointer-like transitions can be defined.

2. When to Apply Slow and Fast Pointers?
Step 1: Identify the Problem Pattern

Ask yourself:

Is the problem about a cycle, loop, repetition, or recurring behaviour?

Examples:

A linked list contains a cycle.
A number repeatedly transforms into another number.
An array contains duplicate values.
A sequence enters a repeating state.
A decimal number has recurring digits.

Examples of recurring decimal behaviour:

3.17171717... → 17 is the recurring sequence.
0.123123123... → 123 is the recurring sequence.

If the problem involves recurring states, consider cycle detection using slow and fast pointers.

Note: Recurring decimals can also be solved using remainder tracking with a HashMap. Slow-fast pointers are an alternative when the process can be represented as a deterministic state transition.

Step 2: Define the Movement
Slow pointer moves one step.
Fast pointer moves two steps.
java
slow = next(slow);
fast = next(next(fast));


The next() operation represents moving to the next state.

Step 3: Check the Objective

Determine what the question wants:

Detect whether a cycle exists.
Find where the cycle starts.
Find the middle element.
Detect repeated states.
Find a duplicate value.
Process both halves of a linked list.
3. Slow-Fast Pointer Flowchart
mermaid
flowchart TD
    A([Start]) --> B[Understand the problem]
    B --> C{Is there a sequence of states<br/>or next-state transitions?}
    C -->|No| Z[Consider another algorithm]
    C -->|Yes| D{Is there a cycle,<br/>repetition, or recurring behaviour?}
    D -->|Yes| E[Consider Floyd's Cycle Detection]
    E --> F[Initialize slow and fast pointers]
    F --> G[Slow moves 1 step<br/>Fast moves 2 steps]
    G --> H{Do they meet?}
    H -->|Yes| I[Cycle detected]
    H -->|No| J{Can fast continue moving?}
    J -->|Yes| G
    J -->|No| K[No cycle]
    D -->|No| L{Is the objective to find<br/>the middle element?}
    L -->|Yes| M[Use slow-fast pointers]
    L -->|No| N[Check other applicable patterns]
    I --> O{What is required?}
    O -->|Cycle entry| P[Reset one pointer to start<br/>Move both one step]
    O -->|Only cycle detection| Q[Return cycle exists]
    O -->|Repeated state| R[Identify the repeated state]
    P --> S([End])
    Q --> S
    R --> S
    M --> S
    K --> S
    N --> S
    Z --> S

4. Important Problem Patterns
Problem pattern	Why slow-fast pointers help
Detect a cycle	Fast eventually catches slow if a cycle exists
Find the middle element	Fast reaches the end when slow reaches the middle
Find the starting point of a cycle	Pointer movement helps identify the cycle entry
Find the Happy Number	Detect cycles in repeated transformations
Find the duplicate number	Treat array values as links and detect a cycle
Check palindrome in linked list	Find the middle and reverse the second half
Detect recurring decimal digits	Repeated remainder transitions can form a cycle
5. Common Applications
1. Find the Middle Element

Problem: Find the middle node of a linked list.

Approach:

Slow moves one node.
Fast moves two nodes.
When fast reaches the end, slow is at the middle.
2. Detect a Cycle in a Linked List

Problem: Determine whether a linked list contains a cycle.

Approach:

Slow moves one node.
Fast moves two nodes.
If both meet, a cycle exists.
If fast reaches null, no cycle exists.
3. Find the Starting Point of a Cycle

Approach:

First, detect the cycle.
Once slow and fast meet, move slow back to the head.
Move both pointers one step at a time.
Their next meeting point is the cycle's starting node.
4. Find the Happy Number

Problem: Determine whether repeatedly summing the squares of a number's digits eventually produces 1.

Approach:

Apply the digit-square transformation repeatedly.
Use slow-fast pointers to detect a cycle.
If the sequence reaches 1, it is a Happy Number.
If it enters a cycle without reaching 1, it is not happy.
5. Find the Duplicate Number

Problem: Find a duplicate in an array containing numbers from 1 to n, with n+1 elements.

Approach:

Treat each array element as the next index.
Use slow and fast pointers to detect a cycle.
Find the cycle entry to identify the duplicate.
6. Check Whether a Linked List Is a Palindrome

Approach:

Find the middle using slow-fast pointers.
Reverse the second half.
Compare the first half with the reversed second half.
Optionally restore the original linked list.
7. Detect Recurring Decimal Digits

Example: 3.17171717...

Approach:

Represent each division step using its remainder.
The next remainder is determined by the current remainder.
If a remainder repeats, the decimal expansion enters a recurring cycle.
Slow-fast pointers can detect this cycle without storing every remainder.
6. Important Conditions and Edge Cases
Cycle Detection
java
while (fast != null && fast.next != null) {
    slow = slow.next;
    fast = fast.next.next;

    if (slow == fast) {
        return true;
    }
}
return false;


Important: Check fast != null and fast.next != null before moving the fast pointer two steps.

Middle Element

For an even-length linked list, the standard implementation above returns the second middle node.

Cycle Detection in General Sequences

Slow-fast pointers work when:

There is a deterministic next-state function.
The process can be continued repeatedly.
The state space is finite, or the sequence eventually repeats.
The transition operation is safe to apply two times for the fast pointer.
7. Complexity Analysis
Operation	Time Complexity	Space Complexity
Find middle	O(n)	O(1)
Detect cycle	O(n)	O(1)
Find cycle entry	O(n)	O(1)
Happy Number	O(n) in terms of visited states	O(1)
Find duplicate number	O(n)	O(1)
8. Interview Recognition Checklist

Whenever you read a problem, ask:

Does the problem involve a cycle or loop?
Is there recurring or repeating behaviour?
Does the process repeatedly move from one state to another?
Can I define a next() operation?
Do I need to find the middle of a sequence?
Can I avoid extra space by detecting a cycle?
Is there a requirement to find the cycle's starting point?

If the answer to one or more of these questions is yes, consider slow-fast pointers.

9. Practice Problems (LeetCode)
Order	Problem	Difficulty
1	Middle of the Linked List	Easy
2	Linked List Cycle	Easy
3	Linked List Cycle II	Medium
4	Palindrome Linked List	Easy
5	Find the Duplicate Number	Medium
6	Happy Number	Easy
10. Final Takeaway

Remember these three important concepts:

Different speeds: Slow moves one step; fast moves two steps.
Cycle detection: If slow and fast meet, a cycle exists (assuming the transition process is deterministic and fast can continue).
Middle detection: When fast reaches the end, slow reaches the middle.

Golden Rule:

Whenever a problem involves cycles, recurring behaviour, repeated states, or finding the middle of a sequence, consider the Slow and Fast Pointers technique.

Don't choose the technique based only on whether the input is an array, linked list, string, or number. Focus on the movement pattern and the objective of the problem.