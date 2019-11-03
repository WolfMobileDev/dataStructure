package 链表;

/**
 * 双向列表
 * @author jinru.lu
 *
 * @param <E>
 */
public class DoubleCircleLinkedList<E> extends AbstractList<E> {
	private int size;
	private Node<E> first;
	private Node<E> last;
	
	private static class Node<E> {
		@SuppressWarnings("unused")
		E element;
		Node<E> prev;
		Node<E> next;
		
		@SuppressWarnings("unused")
		public Node(Node<E> prev, E element, Node<E> next) {
			this.prev = prev;
			this.element = element;
			this.next = next;
		}
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		size = 0;
		first = null;
		last = null;
	}

	@Override
	public void add(int index, E element) {
		// TODO Auto-generated method stub
		rangeCheckForAdd(index);
		
		if (index == size) {
			Node<E> oldLast = last;
			last = new Node<>(oldLast, element, first);
			if (oldLast == null) { //链表添加的第一个元素
				first = last;
				first.next = first;
				first.prev = first;
			} else {
				oldLast.next = last;
				first.prev = last;
			}
		} else {
			Node<E> next = node(index);
			Node<E> prev = next.prev;
			Node<E> node = new Node<>(prev, element, next);
			next.prev = node;
			prev.next = node;
			
			if (index == 0) {
				first = node;
			}
		}
		size++;
	}

	@Override
	public E get(int index) {
		// TODO Auto-generated method stub
		return node(index).element;
	}

	@Override
	public E set(int index, E element) {
		// TODO Auto-generated method stub
		Node<E> node = node(index);
		E oldNode = node.element;
		node.element = element;
		return oldNode;
	}

	@Override
	public E remove(int index) {
		// TODO Auto-generated method stub
		rangeCheck(index);
		
		Node<E> node = first;
		if (size == 1) {
			first = null;
			last = null;
		} else {
			node = node(index);
			Node<E> prev = node.prev;
			Node<E> next = node.next;
			prev.next = next;
			next.prev = prev;
			if (index == 0) { //index == 0
				first = next;
			}
			if (index == size - 1) { //index == size - 1;
				last = prev;
			}
		}
	
		size--;
		
		return node.element;
	}

	@Override
	public int indexOf(E element) {
		// TODO Auto-generated method stub
		Node<E> node = first;
		if (element == null) {
			for(int i = 0; i < size; i++) {
				if (node.element == null) {
					return i;
				}
				node = node.next;
			}
		} else {
			for(int i = 0; i < size; i++) {
				if (element.equals(node.element)) {
					return i;
				}
				node = node.next;
			}
		}
		return ELEMENT_NOT_FOUND;
	}
	
	/**
	 * 查找对应位置的节点
	 * @param index
	 * @return
	 */
	private Node<E> node(int index) {
		rangeCheck(index);
		
		if (index < (size >> 1)) {
			Node<E> node = first;
			for(int i = 0; i < index; i++) {
				node = node.next;
			}
			return node;
		} else {
			Node<E> node = last;
			for(int i = size - 1; i > index; i--) {
				node = node.prev;
			}
			return node;
		}
	}
}
