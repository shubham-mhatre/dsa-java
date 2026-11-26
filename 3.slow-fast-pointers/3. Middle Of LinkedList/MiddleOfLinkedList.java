
public class MiddleOfLinkedList {

	public static void main(String[] args) {
		/*
		 * Given the head of a singly linked list, return the middle node of the linked
		 * list. If there are two middle nodes, return the second middle node.
		 * 
		 * Example 1:
		 * 
		 * Input: head = [1,2,3,4,5] Output: [3,4,5] Explanation: The middle node of the
		 * list is node 3.
		 * 
		 * Example 2:
		 * 
		 * Input: head = [1,2,3,4,5,6] Output: [4,5,6] Explanation: Since the list has
		 * two middle nodes with values 3 and 4, we return the second one.
		 * 
		 * Constraints:
		 * 
		 * The number of nodes in the list is in the range [1, 100]. 1 <= Node.val <=
		 * 100
		 */

		ListNode2 head = new ListNode2(1);
		ListNode2 n1 = new ListNode2(2);
		ListNode2 n2 = new ListNode2(3);
		ListNode2 n3 = new ListNode2(4);
		ListNode2 n4 = new ListNode2(5);

		head.next = n1;
		n1.next = n2;
		n2.next = n3;
		n3.next = n4;
		n4.next = null;

		ListNode2 middleNode = usingSlowFastPointers(head);
		System.out.println(middleNode);
		System.out.println("middleNode val :" + middleNode.val);

	}

	private static ListNode2 usingSlowFastPointers(ListNode2 head) {
		ListNode2 slow = head;
		ListNode2 fast = head;

		while (fast != null && fast.next != null) {
			slow = slow.next;
			fast = fast.next.next;
		}

		return slow;
	}

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

}

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
