package 树;

import java.util.Comparator;

import 树.BinaryTree.Node;

/**
 * 平衡二叉搜索树
 * @author jinru.lu
 *
 */
public class BBSTree<E> extends BSTree<E> {
	public BBSTree() {
		this(null);
	}
	
	public BBSTree(Comparator<E> comparator) {
		super(comparator);
	}
	
	/**
	 * 左旋转
	 * @param node
	 */
	protected void rotateLeft(Node<E> grand) {
		Node<E> parent = grand.right;
		Node<E> child = parent.left;
		grand.right = child;
		parent.left = grand;
		
		afterRotate(grand, parent, child);
	}
	
	/**
	 * 右旋转
	 * @param node
	 */
	protected void rotateRight(Node<E> grand) {
		Node<E> parent = grand.left;
		Node<E> child = parent.right;
		grand.left = child;
		parent.right = grand;
		
		afterRotate(grand, parent, child);
	}
	
	protected void afterRotate(Node<E> grand, Node<E> parent, Node<E> child) {
		//让parent成为子树的根节点
		parent.parent = grand.parent;
		if (grand.isLeftChild()) {
			grand.parent.left = parent;
		} else if (grand.isRightChild()) {
			grand.parent.right = parent;
		} else { // grand是root节点
			root = parent;
		}
		
		//更新child的parent
		if (child != null) {
			child.parent = grand;
		}
		
		//更新grand的parent
		grand.parent = parent;
	}
}
