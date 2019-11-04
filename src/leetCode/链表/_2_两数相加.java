package leetCode.链表;

/**
 * https://leetcode-cn.com/problems/add-two-numbers/
 * @author jinru.lu
 * 给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
   如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
   您可以假设除了数字 0 之外，这两个数都不会以 0 开头。

   示例：
	 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
	 输出：7 -> 0 -> 8
	 原因：342 + 465 = 807
 */
public class _2_两数相加 {
	
	public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
		ListNode node = new ListNode(0);
		ListNode cussor = node;
		int sum = 0;
		while (l1 != null || l2 != null || sum != 0) {
			int l1Val = (l1 != null ? l1.val : 0);
			int l2Val = (l2 != null ? l2.val : 0);
			int sumVal = l1Val + l2Val + sum;
			
			sum = sumVal / 10;
			ListNode sumNode = new ListNode(sumVal % 10);
			cussor.next = sumNode;
			cussor = sumNode;
			if (l1 != null) {
				l1 = l1.next;
			}
			if (l2 != null) {
				l2 = l2.next;
			}
		}
		return node.next;
	}
	
	public static void main(String[] args) {
		ListNode l1 = new ListNode(2);
		ListNode node2 = new ListNode(4);
		ListNode node3 = new ListNode(3);
		l1.next = node2;
		node2.next = node3;
		node3.next = null;
		
		ListNode l2 = new ListNode(5);
		ListNode node4 = new ListNode(6);
		ListNode node5 = new ListNode(4);
		l2.next = node4;
		node4.next = node5;
		node5.next = null;
		
		ListNode node = addTwoNumbers(l1, l2);
		System.out.println(node.val);
	}
}
