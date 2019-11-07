package 队列;

import 链表.DoubleLinkedList;
import 链表.List;

/**
 * 普通队列
 * @author jinru.lu
 *
 * @param <E>
 */
public class Queue<E> {
	private List<E> list = new DoubleLinkedList<>();
	
	public int size() {
		return list.size();
	}
	
	public boolean isEmpty() {
		return list.isEmpty();
	}
	
	public void enQuue(E element) {
		list.add(element);
	}
	
	public E deQueue() {
		return list.remove(0);
	}
	
	public E front() {
		return list.get(0);
	}
}
