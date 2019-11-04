package 栈;

import 动态数组.ArrayList;
import 链表.List;

public class Stack<E> {
	private List<E> list = new ArrayList<>();
	
	public int size() {
		return list.size();
	}
	
	public boolean isEmpty() {
		return list.isEmpty();
	}
	
	public void push(E element) {
		list.add(element);
	}
	
	public E pop() {
		return list.remove(list.size() - 1);
	}
	
	public E top() {
		return list.get(size() - 1);
	}
	
	public void clear() {
		list.clear();
	}

}
