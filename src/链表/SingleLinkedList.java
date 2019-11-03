package 链表;

/**
 * 单向列表
 * @author jinru.lu
 *
 * @param <E>
 */
public class SingleLinkedList<E> extends AbstractList<E> {
	private int size;
	private Node<E> first;
	
	private static class Node<E> {
		@SuppressWarnings("unused")
		E element;
		Node<E> next;
		
		@SuppressWarnings("unused")
		public Node(E element, Node<E> next) {
			this.element = element;
			this.next = next;
		}
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		size = 0;
		first = null;
	}

	@Override
	public void add(int index, E element) {
		// TODO Auto-generated method stub
		rangeCheckForAdd(index);
		if (index == 0) {
			first = new Node<>(element, first);
		} else {
			Node<E> prev = node(index - 1);
			prev.next = new Node<>(element, prev.next);
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
		if (index == 0) {
			first = first.next;
		} else {
			Node<E> prev = node(index - 1);
			node = prev.next;
			prev.next = prev.next.next;
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
		
		Node<E> node = first;
		for(int i = 0; i < index; i++) {
			node = node.next;
		}
		return node;
	}
}
