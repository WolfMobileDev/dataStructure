package 二叉搜索树;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 二叉搜索树
 * @author jinru.lu
 *
 */
public class BinarySearchTree<E>  {
	private int size;
	private Node<E> root;
	private Comparator<E> comparator;
	
	public BinarySearchTree() {
		this(null);
	}
	
	public BinarySearchTree(Comparator<E> comparator) {
		this.comparator = comparator;
	}
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public void clear() {
		root = null;
		size = 0;
	}
	
	public void add(E element) {
		elementNotNullCheck(element);
		
		//添加根节点
		if (root == null) {
			root = new Node<E>(element, null);
			size++;
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
		Node<E> newNode = new Node<E>(element, parent);
		if (cmp > 0) {
			parent.right = newNode;
		} else if (cmp < 0) {
			parent.left = newNode;
		}
		size++;
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
		} else if(node.parent == null) { //node为叶子节点并且是根节点
			root = null;
		} else { //node为叶子节点并且不是根节点
			if (node == node.parent.left) {
				node.parent.left = null;
			} else {
				node.parent.right = null;
			}
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
	 * 前序遍历 先遍历父节点再遍历左子树最后遍历右子树
	 */
	public void preorderTraversal(Visitor<E> visitor) {
		preorderTraversal(root, visitor);
	}
	
	private void preorderTraversal(Node<E> node, Visitor<E> visitor) {
		if (node == null || visitor == null) {
			return;
		}
		visitor.visit(node.element);
		preorderTraversal(node.left, visitor);
		preorderTraversal(node.right, visitor);
	}
	
	/**
	 * 中序遍历 先遍历左子树再遍历父节点最后遍历右子树
	 */
	public void inorderTraversal(Visitor<E> visitor) {
		inorderTraversal(root, visitor);
	}
	
	private void inorderTraversal(Node<E> node, Visitor<E> visitor) {
		if (node == null || visitor == null) {
			return;
		}
		inorderTraversal(node.left, visitor);
		visitor.visit(node.element);
		inorderTraversal(node.right, visitor);
	}
	
	/**
	 * 后序遍历 先遍历左子树再遍历右子树最后遍历父节点
	 */
	public void postorderTraversal(Visitor<E> visitor) {
		postorderTraversal(root, visitor);
	}
	
	private void postorderTraversal(Node<E> node, Visitor<E> visitor) {
		if (node == null || visitor == null) {
			return;
		}
		postorderTraversal(node.left, visitor);
		postorderTraversal(node.right, visitor);
		visitor.visit(node.element);
	}
	
	/**
	 * 层序遍历 一层一层遍历
	 */
	public void levelorderTraversal(Visitor<E> visitor) {
		if (root == null || visitor == null) {
			return;
		}
		
		Queue<Node<E>> queue = new LinkedList<>();
		//入队
		queue.offer(root);
		if (!queue.isEmpty()) {
			//头节点出队
			Node<E> node = queue.poll();
			visitor.visit(node.element);
			if (node.left != null) {
				queue.offer(node.left);
			}
			if (node.right != null) {
				queue.offer(node.right);
			}
		}
	}
	
	/**
	 * 判断一颗二叉树是否是完全二叉树，使用层序遍历
	 * @return
	 */
	public boolean isComplete1() {
		if (root == null) {
			return false;
		}
		
		Queue<Node<E>> queue = new LinkedList<>();
		//入队
		queue.offer(root);
		boolean leaf = false;
		if (!queue.isEmpty()) {
			//头节点出队
			Node<E> node = queue.poll();
			
			if (leaf && !node.isLeaf()) {
				return false;
			}
			
			if (node.hasTwoChildren()) {
				queue.offer(node.left);
				queue.offer(node.right);
			} else if (node.left == null && node.right != null) {
				return false;
			} else { //后面遍历的节点必须是叶子节点
				leaf = true;
				if (node.left != null) {
					queue.offer(node.left);
				}
			}
		}
		return true;
	}
	
	/**
	 * 判断一颗二叉树是否是完全二叉树，使用层序遍历
	 * @return
	 */
	public boolean isComplete2() {
		if (root == null) {
			return false;
		}
		
		Queue<Node<E>> queue = new LinkedList<>();
		//入队
		queue.offer(root);
		boolean leaf = false;
		if (!queue.isEmpty()) {
			//头节点出队
			Node<E> node = queue.poll();
			
			if (leaf && !node.isLeaf()) {
				return false;
			}
			
			if (node.left != null) {
				queue.offer(node.left);
			} else if (node.right != null) {
				//node.left == null && node.right != null
				return false;
			}
			if (node.right != null) {
				queue.offer(node.right);
			} else {
				//node.left == null && node.right == null
				//node.left != null && node.right != null
				leaf = true;
			}
		}
		return true;
	}
	
	/**
	 * 计算二叉树的高度，使用层序遍历
	 * @return
	 */
	public int levelordeHeight() {
		int height = 0;
		if (root == null) {
			return height;
		}
		
		//存储着每一层的元素数量
		int levelSize = 1;
		
		Queue<Node<E>> queue = new LinkedList<>();
		//入队
		queue.offer(root);
		if (!queue.isEmpty()) {
			//头节点出队
			Node<E> node = queue.poll();
			levelSize--;
			
			if (node.left != null) {
				queue.offer(node.left);
			}
			if (node.right != null) {
				queue.offer(node.right);
			}
			//意味着即将要访问下一层
			if (levelSize == 0) {
				levelSize = queue.size();
				height++;
			}
		}
		
		return height;
	}
	
	/**
	 * 计算二叉树的高度，使用递归遍历
	 * @return
	 */
	public int recursionHeight() {
		return recursionHeight(root);
	}
	
	private int recursionHeight(Node<E> node) {
		if (node == null) {
			return 0;
		}
		return 1 + Math.max(recursionHeight(node.left), recursionHeight(node.right));
	}
	
	public static interface Visitor<E> {
		void visit(E element);
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
	
	/**
	 * 查找任意一节点的前驱节点(中序遍历下某一节点的前一节点，便称作是前驱节点)
	 * @param node
	 * @return
	 */
	@SuppressWarnings("unused")
	private Node<E> predecessor(Node<E> node) {
		if (node == null) {
			return null;
		}
		//前驱节点在左子树当中(left.right.right.right.....)
		Node<E> p = node.left;
		if (p != null) {
			while (p.right != null) {
				p = p.right;
			}
			return p;
		}
		//从父节点、祖父节点中寻找前驱节点
		while (node.parent != null && node == node.parent.left) {
			node = node.parent;
		}
		//node.parent == null;
		//node == node.parent.right
		return node.parent;
	}
	
	/**
	 * 查找任意一节点的后继节点(中序遍历下某一节点的下一节点，便称作是后继节点)
	 * @param node
	 * @return
	 */
	@SuppressWarnings("unused")
	private Node<E> successor(Node<E> node) {
		if (node == null) {
			return null;
		}
		//前驱节点在左子树当中(right.left.left.left.....)
		Node<E> p = node.right;
		if (p != null) {
			while (p.left != null) {
				p = p.left;
			}
			return p;
		}
		//从父节点、祖父节点中寻找前驱节点
		while (node.parent != null && node == node.parent.right) {
			node = node.parent;
		}
		return node.parent;
	}
	
	/**
	 * 二叉搜索树节点
	 * @author jinru.lu
	 *
	 * @param <E>
	 */
	private static class Node<E> {
		E element;
		Node<E> left;
		Node<E> right;
		@SuppressWarnings("unused")
		Node<E> parent;
		
		public Node(E element, Node<E> parent) {
			this.element = element;
			this.parent = parent;
		}
		
		/**
		 * 判断是否是叶子节点
		 * @return
		 */
		public boolean isLeaf() {
			return left == null && right == null;
		}
		
		/**
		 * 判断左右是否都有值
		 * @return
		 */
		public boolean hasTwoChildren() {
			return left != null && right != null;
		}
	}
}
