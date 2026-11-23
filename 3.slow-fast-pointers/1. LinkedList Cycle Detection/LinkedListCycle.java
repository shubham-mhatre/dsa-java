
import java.util.HashSet;
import java.util.Set;

public class LinkedListCycle {

	/*
	 * Given head, the head of a linked list, determine if the linked list has a
	 * cycle in it.
	 * 
	 * There is a cycle in a linked list if there is some node in the list that can
	 * be reached again by continuously following the next pointer. Internally, pos
	 * is used to denote the index of the node that tail's next pointer is connected
	 * to. Note that pos is not passed as a parameter.
	 * 
	 * Return true if there is a cycle in the linked list. Otherwise, return false. 
	 * 
	 * Example 1: 
	 * 
	 * Input: head = [3,2,0,-4], pos = 1 Output: true Explanation: There is a cycle
	 * in the linked list, where the tail connects to the 1st node (0-indexed).
	 * 
	 * Example 2: 
	 * 
	 * Input: head = [1,2], pos = 0 Output: true Explanation: There is a cycle in
	 * the linked list, where the tail connects to the 0th node. 
	 * 
	 * Example 3: 
	 * 
	 * Input: head = [1], pos = -1 Output: false Explanation: There is no cycle in
	 * the linked list.
	 * 
	 * 
	 * Constraints:
	 * 
	 * The number of the nodes in the list is in the range [0, 104]. -105 <=
	 * Node.val <= 105 pos is -1 or a valid index in the linked-list.
	 */

	public static void main(String[] args) {
		ListNode n1 = new ListNode(3);
		ListNode n2 = new ListNode(2);
		ListNode n3 = new ListNode(0);
		ListNode n4 = new ListNode(-4);
		
		n1.next=n2;
		n2.next=n3;
		n3.next=n4;
		n4.next=n2;
		
		boolean result = usingSlowFastPointers(n1);
		System.out.println(" cycle detected "+result);

	}

	private static boolean usingSlowFastPointers(ListNode head) {
		ListNode slow=head;
		ListNode fast=head;
		
		while(fast !=null && fast.next !=null) {
			
			slow=slow.next;
			fast =fast.next.next;
			
			if(slow == fast) {
				return true;
			}
			
		}
		
		return false;
	}
	

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

}

class ListNode {
	int val;
	ListNode next;

	ListNode(int x) {
		val = x;
		next = null;
	}
}
