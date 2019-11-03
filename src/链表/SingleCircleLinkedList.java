package 链表;

/**
 * 单向循环列表
 * @author jinru.lu
 *
 * @param <E>
 */
public class SingleCircleLinkedList<E> extends AbstractList<E> {
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
		size = 0;
		first = null;
	}

	@Override
	public void add(int index, E element) {
		// TODO Auto-generated method stub
		rangeCheckForAdd(index);
		if (index == 0) {
			Node<E> newFirst = new Node<>(element, first);
			Node<E> last = (size == 0) ? newFirst : node(size - 1);
			last.next = first;
			first = newFirst;
		} else {
			Node<E> prev = node(index - 1);
			prev.next = new Node<>(element, prev.next);
		}
		size++;
	}

	@Override
	public E get(int index) {
		return node(index).element;
	}

	@Override
	public E set(int index, E element) {
		Node<E> node = node(index);
		E oldNode = node.element;
		node.element = element;
		return oldNode;
	}

	@Override
	public E remove(int index) {
		rangeCheck(index);
		Node<E> node = first;
		if (index == 0) {
			if (size == 1) {
				first = null;
			} else {
				Node<E> last = node(size - 1);
				first = first.next;
				last.next = first;
			}
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
