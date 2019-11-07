package 队列;

/**
 * 循环队列
 * @author jinru.lu
 *
 * @param <E>
 */
public class CircleQueue<E> {
	private static final int DEFAULT_CAPACITY = 10;
	private int size;
	private E[] elements;
	//队头
	private int front;
	
	public CircleQueue() {
		elements = (E[])new Object[DEFAULT_CAPACITY];
	}
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	/**
	 * 入队
	 * @param element
	 */
	public void enQuue(E element) {
		ensureCapacity(size + 1);
		elements[index(size)] = element;
		size++;
	}
	
	/**
	 * 出队
	 * @return
	 */
	public E deQueue() {
		E frontElements = elements[front];
		elements[front] = null;
		front = index(1);
		size--;
		return frontElements;
	}
	
	public E front() {
		return elements[front];
	}
	
	private int index(int index) {
		return (front + index) % elements.length;
	}
	
	/**
	 * 保证要有capacity容量
	 * @param capacity
	 */
	private void ensureCapacity(int capacity) {
		 int oldCapacity = elements.length;
		 if (oldCapacity >= capacity) {
			 return;
		}
		 //新容量为旧容量的1.5倍
		 int newCapacity = oldCapacity + (oldCapacity >> 1);
		 E[] newElements = (E[]) new Object[newCapacity];
		 for(int i = 0; i < size; i++) {
			 newElements[i] = elements[index(i)];
		 }
		 elements = newElements;
		 
		 //重置front
		 front = 0;
	}
}
