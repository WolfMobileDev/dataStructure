package 树;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

import 树.BinaryTree.Node;

/**
 * 二叉搜索树
 * @author jinru.lu
 *
 */
public class BSTree<E> extends BinaryTree<E> {
	private Comparator<E> comparator;
	
	public BSTree() {
		this(null);
	}
	
	public BSTree(Comparator<E> comparator) {
		this.comparator = comparator;
	}
	
	public void add(E element) {
		elementNotNullCheck(element);
		
		//添加根节点
		if (root == null) {
			root = createNode(element, null);
			size++;
			
			afterAdd(root);
			return;
		}
		
		//找到父节点
		Node<E> node = root;
		//保存最后一次父节点
		Node<E> parent = null;
		int cmp = 0;
		while (node != null) {
			cmp = compare(element, node.element);
			parent = node;
			if (cmp > 0) {
				node = node.right;
			} else if (cmp < 0) {
				node = node.left;
			} else {
				node.element = element;
				return;
			}
		}
		
		//看看插入到父节点的哪个位置
		Node<E> newNode = createNode(element, parent);
		if (cmp > 0) {
			parent.right = newNode;
		} else if (cmp < 0) {
			parent.left = newNode;
		}
		size++;
		
		afterAdd(newNode);
	}
	
	/**
	 * 添加新节点node之后的调整
	 * @param node
	 */
	protected void afterAdd(Node<E> node) {
		
	}
	
	/**
	 * 删除节点node之后的调整
	 * @param node
	 */
	protected void afterRemove(Node<E> node, Node<E> replacement) {
		
	}
	
	public void remove(E element) {
		remove(node(element));
	}
	
	private void remove(Node<E> node) {
		if (node == null) {
			return;
		}
		size--;
		
		//删除度为2的节点
		if (node.hasTwoChildren()) {
			//找到后继节点
			Node<E> s = successor(node);
			//用后继节点的值覆盖度为2的节点的值
			node.element = s.element;
			//删除后继节点
			node = s;
		}
		//删除node节点(node度必然为1或者0)
		Node<E> replacement = node.left != null ? node.left : node.right;
		//node度为1的节点
		if (replacement != null) {
			//更改parent
			replacement.parent = node.parent;
			//更改parent的left、right的指向
			if (node.parent == null) { //node是度为1的节点并且是根节点
				root = replacement;
			} else if (node == node.parent.left) {
				node.parent.left = replacement;
			} else {
				node.parent.right = replacement;
			}
			
			afterRemove(node, null);
		} else if(node.parent == null) { //node为叶子节点并且是根节点
			root = null;
			
			afterRemove(node, null);
		} else { //node为叶子节点并且不是根节点
			if (node == node.parent.left) {
				node.parent.left = null;
			} else {
				node.parent.right = null;
			}
			
			afterRemove(node, null);
		}                                                      
	}
	
	private Node<E> node(E element) {
		Node<E> node = root;
		while (node != null) {
			int cmp = compare(element, node.element);
			if (cmp == 0) {
				return node;
			} else if (cmp > 0) {
				node = node.right;
			} else {
				node = node.left;
			}
		}
		return null;
	}
	
	public boolean contains(E element) {
		return node(element) != null;
	}
	
	/**
	 * 
	 * @param e1
	 * @param e2
	 * @return 返回值等于0,代表e1 = e2; 返回值大于0,代表e1 > e2; 返回值小于0,代表e1 < e2
	 */
	@SuppressWarnings("unchecked")
	private int compare(E e1, E e2) {
		if (comparator != null) {
			return comparator.compare(e1, e2);
		}
		return ((Comparable<E>)e1).compareTo(e2);
	}
	
	private void elementNotNullCheck(E element) {
		if (element == null) {
			throw new IllegalArgumentException("element must not be null");
		}
	}
}
