package 队列;

/**
 * 循环双端队列
 * @author jinru.lu
 *
 */
public class CircleDeque<E> {
	private static final int DEFAULT_CAPACITY = 10;
	private int size;
	private E[] elements;
	//队头
	private int front;
	
	public CircleDeque() {
		elements = (E[])new Object[DEFAULT_CAPACITY];
	}
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	/**
	 * 从尾部入队
	 * @param element
	 */
	public void enQueueRear(E element) {
		ensureCapacity(size + 1);
		elements[index(size)] = element;
		size++;
	}
	
	/**
	 * 从尾部出队
	 * @return
	 */
	public E deQueueRear() {
		int rearIndex = index(size - 1);
		E rear = elements[rearIndex];
		elements[rearIndex] = null;
		size--;
		return rear;
	}
	
	/**
	 * 从头部入队
	 * @param element
	 */
	public void enQueueFront(E element) {
		ensureCapacity(size + 1);
		front = index(-1);
		elements[front] = element;
		size++;
	}
	
	/**
	 * 从头部出队
	 * @return
	 */
	public E deQueueFront() {
		E frontElements = elements[front];
		elements[front] = null;
		front = index(1);
		size--;
		return frontElements;
	}
	
	public E front() {
		return elements[front];
	}
	
	public E real() {
		return elements[index(size - 1)];
	}
	
	private int index(int index) {
		index += front;
		if (index < 0) {
			return index + elements.length;
		}
		return index % elements.length;
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
