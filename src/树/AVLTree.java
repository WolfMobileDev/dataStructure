package 树;

import java.util.Comparator;

import 树.BinaryTree.Node;

/**
 * 平衡搜索二叉树
 * @author jinru.lu
 *
 * @param <E>
 */
public class AVLTree<E> extends BBSTree<E> {
	
	public AVLTree() {
		this(null);
	}
	
	public AVLTree(Comparator<E> comparator) {
		super(comparator);
	}
	
	@Override
	protected void afterAdd(Node<E> node) {
		while ((node = node.parent) != null) {
			if (isBalanced(node)) {
				//更新高度
				updateHeight(node);
			} else {
				//恢复平衡
				rebalance(node);
				break;
			}
		}
	}
	
	@Override
	protected void afterRemove(Node<E> node, Node<E> replacement) {
		while ((node = node.parent) != null) {
			if (isBalanced(node)) {
				//更新高度
				updateHeight(node);
			} else {
				//恢复平衡
				rebalance(node);
			}
		}
	}
	
	@Override
	protected Node<E> createNode(E element, Node<E> parent) {
		return new AVLNode<>(element, parent);
	}
	
	/**
	 * 恢复平衡
	 * @param node 高度最低的不平衡节点
	 */
	private void rebalance(Node<E> grand) {
		Node<E> parent = ((AVLNode<E>)grand).tallerChild();
		Node<E> node = ((AVLNode<E>)grand).tallerChild();
		if (parent.isLeftChild()) { //L
			if (node.isLeftChild()) { //LL
				rotateRight(grand);
			} else { //LR
				rotateLeft(parent);
				rotateRight(grand);
			}
		} else { //R
			if (node.isLeftChild()) { //RL
				rotateRight(parent);
				rotateLeft(grand);
			} else { //RL
				rotateLeft(grand);
			}
		}
	}
	
	@Override
	protected void afterRotate(Node<E> grand, Node<E> parent, Node<E> child) {
		// TODO Auto-generated method stub
		super.afterRotate(grand, parent, child);
		
		//更新高度
		updateHeight(grand);
		updateHeight(parent);
	}
	
	private boolean isBalanced(Node<E> node) {
		return Math.abs(((AVLNode<E>)node).balanceFactor()) <= 1;
	}
	
	/**
	 * 更新节点高度
	 * @param node
	 */
	private void updateHeight(Node<E> node) {
		((AVLNode<E>)node).updateHeight();
	}
	
	private static class AVLNode<E> extends Node<E> {
		int height = 1;
		
		public AVLNode(E element, Node<E> parent) {
			super(element, parent);
			// TODO Auto-generated constructor stub
		}
		
		/**
		 * 获取平衡因子(左子树高度 - 右子树高度)
		 * @return
		 */
		public int balanceFactor() {
			int leftHeight = left == null ? 0 :((AVLNode<E>)left).height;
			int rightHeight = right == null ? 0 :((AVLNode<E>)right).height;
			return leftHeight - rightHeight;
		}
		
		private void updateHeight() {
			int leftHeight = left == null ? 0 :((AVLNode<E>)left).height;
			int rightHeight = right == null ? 0 :((AVLNode<E>)right).height;
			height = 1 + Math.max(leftHeight, rightHeight);
		}
		
		public Node<E> tallerChild() {
			int leftHeight = left == null ? 0 :((AVLNode<E>)left).height;
			int rightHeight = right == null ? 0 :((AVLNode<E>)right).height;
			if (leftHeight > rightHeight) {
				return left;
			}
			if (leftHeight < rightHeight) {
				return right;
			}
			return isLeftChild() ? left : right;
		}
	}
}
